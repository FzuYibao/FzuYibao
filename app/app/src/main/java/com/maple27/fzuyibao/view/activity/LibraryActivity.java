package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.LibraryBean;
import com.maple27.fzuyibao.presenter.adapter.LibraryAdapter;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.w3c.dom.Text;

/**
 * Created by Maple27 on 2017/11/12.
 */

public class LibraryActivity extends AppCompatActivity {

    private CustomApplication application;
    private Context context;
    private ListView lv;
    private LibraryAdapter adapter;
    private LibraryBean bean;
    private FloatingSearchView searchView;
    private SmartRefreshLayout refresh;
    private TextView num;
    private FloatingActionButton post;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 8){
                bean = (LibraryBean) msg.obj;
                if(bean.getError_code()==0){
                    init();
                    num.setText("当前馆藏书量："+bean.getData().getBooks().size());
                }else Toast.makeText(context , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
            //刷新数据
            if(msg.what == 280){
                bean = (LibraryBean) msg.obj;
                adapter.setBean(bean);
                adapter.notifyDataSetChanged();
            }
            if(msg.what == 69){
                bean = (LibraryBean) msg.obj;
                adapter.setBean(bean);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_library);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil.GetLibraryInfo(handler);
            }
        }).start();
    }

    public void init(){
        application = (CustomApplication) getApplication();
        context = this;
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_library);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchView = (FloatingSearchView) findViewById(R.id.search_library);
        refresh = (SmartRefreshLayout) findViewById(R.id.refresh_library);
        num = (TextView) findViewById(R.id.library_num);
        post = (FloatingActionButton) findViewById(R.id.library_post);
        lv = (ListView) findViewById(R.id.lv_library);
        adapter = new LibraryAdapter(context, bean);
        InitUtil.initLibraryActivity(application, handler, context, refresh, searchView, adapter, lv, post);
        setListViewHeightBasedOnChildren(lv);
        StatusBarUtil.setStatusBar(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
