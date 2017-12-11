package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.adapter.MainAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.InitUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.yanzhenjie.album.Album;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Maple27 on 2017/11/1.
 */

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MainAdapter adapter;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_main);
        initViews();
    }

    public void initViews(){
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        list = new ArrayList<>();
        adapter = new MainAdapter(getSupportFragmentManager(), list);
        //初始化
        InitUtil.initMainActivity(viewPager, tabLayout, adapter, list);
        //修改状态栏
        StatusBarUtil.setStatusBar(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                List<String> s = Album.parseResult(data);
                final String imagePath = s.get(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.PostAvatar(getBaseContext(), imagePath);
                    }
                }).start();
                System.out.println(imagePath + " aaa");
            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

}


