package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.adapter.CommodityAdapter;
import com.maple27.fzuyibao.presenter.adapter.MainAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.view.fragment.CommodityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple27 on 2017/11/12.
 */

public class LMActivity extends AppCompatActivity {

    private Context context;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CommodityAdapter adapter;
    private List<Fragment> list;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_lm);
        init();
    }

    public void init(){
        context = this;
        viewPager = (ViewPager) findViewById(R.id.vp_lm);
        tabLayout = (TabLayout) findViewById(R.id.tab_lm);
        list = new ArrayList<>();
        adapter = new CommodityAdapter(getSupportFragmentManager(), list);

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_lm);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            //here 加返回键图片
        }
        InitUtil.initLMActivity(context, viewPager, tabLayout, adapter, list);
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
}
