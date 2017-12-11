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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.SeekBean;
import com.maple27.fzuyibao.presenter.adapter.SeekAdapter;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by Maple27 on 2017/11/12.
 */

public class SeekActivity extends AppCompatActivity {

    private CustomApplication application;
    private Context context;
    private ListView lv;
    private SeekAdapter adapter;
    private SeekBean bean;
    private FloatingSearchView searchView;
    private SmartRefreshLayout refresh;
    private FloatingActionButton post;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 24){
                bean = (SeekBean) msg.obj;
                if(bean.getError_code()==0){
                    init();
                }else Toast.makeText(context , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
            if(msg.what == 25){
                bean = (SeekBean) msg.obj;
                adapter.setBean(bean);
                adapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_seek);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil.GetSeekInfo(handler);
            }
        }).start();
    }

    public void init(){
        application = (CustomApplication) getApplication();
        context = this;
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_seek);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        searchView = (FloatingSearchView) findViewById(R.id.search_seek);
        refresh = (SmartRefreshLayout) findViewById(R.id.refresh_seek);
        post = (FloatingActionButton) findViewById(R.id.seek_post);
        lv = (ListView) findViewById(R.id.lv_seek);
        adapter = new SeekAdapter(context, bean);
        InitUtil.initSeekActivity(application, handler, context, refresh, searchView, adapter, lv, post);
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
