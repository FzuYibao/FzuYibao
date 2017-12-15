package com.maple27.fzuyibao.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.presenter.adapter.CommodityInfoAdapter;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.activity.DetailsActivity;
import com.maple27.fzuyibao.view.activity.LMActivity;
import com.maple27.fzuyibao.view.activity.PostActivity;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

/**
 * Created by Maple27 on 2017/11/18.
 */

public class CommodityFragment extends Fragment {

    private CustomApplication application;
    private String category;
    private Context context;
    private ListView lv;
    private CommodityInfoAdapter adapter;
    private CommodityBean bean;
    private View view;
    private SmartRefreshLayout refresh;
    private FloatingActionButton post;
    private FloatingSearchView searchView;
    private int page = 0;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据和刷新
            if(msg.what == 27){
                bean = (CommodityBean) msg.obj;
                if(bean.getError_code()==0){
                    init(view);
                }else Toast.makeText(getActivity().getBaseContext() , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
            //加载更多
            if(msg.what == 120){
                CommodityBean bean2 = (CommodityBean) msg.obj;
                bean.getData().setGoods(bean2.getData().getGoods());
                adapter.setBean(bean);
                adapter.notifyDataSetChanged();
            }
            if(msg.what == 270){
                bean = (CommodityBean) msg.obj;
                adapter.setBean(bean);
                adapter.notifyDataSetChanged();
            }
        }
    };

    public CommodityFragment(){
        super();
        new Thread(new Runnable() {
            @Override
            public void run() {
                category = getArguments().getString("category");
                NetworkUtil.GetCommodityInfo(0,category,handler);
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_commodity , null);
        //init(view);
        return view;
    }

    public void init(View view){
        application = (CustomApplication) getActivity().getApplication();
        context = getContext();
        post = (FloatingActionButton) view.findViewById(R.id.lm_post);
        refresh = (SmartRefreshLayout) view.findViewById(R.id.refresh_commodity);
        searchView = (FloatingSearchView) view.findViewById(R.id.search_commodity);
        lv = (ListView) view.findViewById(R.id.lv_LM);
        adapter = new CommodityInfoAdapter(context,bean);
        refresh.setPrimaryColors(R.color.colorPrimary);
        refresh.setPrimaryColorsId(R.color.colorPrimary);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetCommodityInfo(page,category,handler);
                    }
                }).start();
            }
        });
        refresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetCommodityInfo(page,category,handler);
                    }
                }).start();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetCommodityInfo(0,category,handler);
                    }
                }).start();
            }
        });
        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(final String currentQuery) {
                page = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.SearchCommodity(handler,context,category,currentQuery,page);
                    }
                }).start();
            }
        });
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", bean.getData().getGoods().get(i).getGid());
                context.startActivity(intent);
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                application.setHandler(handler);
                Intent intent = new Intent(getContext(), PostActivity.class);
                startActivity(intent);
            }
        });

        setListViewHeightBasedOnChildren(lv);
    }

    //用来传递category
    public static CommodityFragment newInstance(String category){
        CommodityFragment fragment = new CommodityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {

            return;

        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

            View listItem = listAdapter.getView(i, null, listView);

            listItem.measure(0, 0); // 计算子项View 的宽高

            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // listView.getDividerHeight()获取子项间分隔符占用的高度

        // params.height最后得到整个ListView完整显示需要的高度

        listView.setLayoutParams(params);

    }
}
