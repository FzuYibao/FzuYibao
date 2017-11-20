package com.maple27.fzuyibao.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.presenter.adapter.CommodityInfoAdapter;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by Maple27 on 2017/11/18.
 */

public class CommodityFragment extends Fragment {

    private String category;
    private Context context;
    private ListView lv;
    private CommodityInfoAdapter adapter;
    private CommodityBean bean;
    private View view;
    private SmartRefreshLayout refresh;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 27){
                bean = (CommodityBean) msg.obj;
                if(bean.getError_code()==0){
                    init(view);
                }else Toast.makeText(getActivity().getBaseContext() , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
            //获取更多
            if(msg.what == 279){
                bean = (CommodityBean) msg.obj;
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
                NetworkUtil.GetCommodityInfo(category,handler);
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
        context = getContext();
        refresh = (SmartRefreshLayout) view.findViewById(R.id.refresh_commodity);
        lv = (ListView) view.findViewById(R.id.lv_LM);
        adapter = new CommodityInfoAdapter(context,bean);
        lv.setAdapter(adapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.GetCommodityInfo(category,handler);
                    }
                }).start();
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
