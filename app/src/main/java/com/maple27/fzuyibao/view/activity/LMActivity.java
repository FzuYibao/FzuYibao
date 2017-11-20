package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.adapter.CommodityAdapter;
import com.maple27.fzuyibao.presenter.adapter.MainAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.view.fragment.CommodityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple27 on 2017/11/12.
 */

public class LMActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CommodityAdapter adapter;
    private List<Fragment> list;
    private FloatingActionButton post;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_lm);
        init();
    }

    public void init(){
        viewPager = (ViewPager) findViewById(R.id.vp_lm);
        tabLayout = (TabLayout) findViewById(R.id.tab_lm);
        post = (FloatingActionButton) findViewById(R.id.lm_post);
        list = new ArrayList<>();
        adapter = new CommodityAdapter(getSupportFragmentManager(), list);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LMActivity.this, PostActivity.class);
                startActivity(intent);
            }
        });

        InitUtil.initLMActivity(viewPager, tabLayout, adapter, list);
        StatusBarUtil.setStatusBar(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
