package com.maple27.fzuyibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.presenter.adapter.CommodityManageAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;

/**
 * Created by Maple27 on 2017/12/15.
 */

public class SeekManageActivity extends AppCompatActivity {
    private AppCompatActivity activity;
    private ListView listView;
    private CommodityManageAdapter adapter;
    private CommodityBean bean;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 36){
                bean = (CommodityBean) msg.obj;
                if(bean.getError_code()==0){
                    init();
                }else Toast.makeText(activity , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }else if(msg.what == 46){
                bean = (CommodityBean) msg.obj;
                if(bean.getError_code()==0){
                    adapter.setBean(bean);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(activity , "商品下架成功" , Toast.LENGTH_SHORT).show();
                }else Toast.makeText(activity , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
        }
    };

    public SeekManageActivity(){
        super();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil.GetCommodityManageInfo(handler);
            }
        }).start();*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_seekmanage);
        init();
    }

    public void init(){
        activity = this;
        listView = (ListView) findViewById(R.id.lv_sm);
        adapter = new CommodityManageAdapter(activity, bean, handler);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);
        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.sm_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
        }

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
                activity.finish();
                break;
            default:
        }
        return true;
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
