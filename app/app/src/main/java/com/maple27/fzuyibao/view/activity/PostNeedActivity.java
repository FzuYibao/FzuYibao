package com.maple27.fzuyibao.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class PostNeedActivity extends AppCompatActivity {

    private AppCompatActivity activity;
    private CustomApplication app;
    private Handler handler;
    private EditText info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_postneed);
        init();
    }

    public void init(){
        activity = this;
        app = (CustomApplication) getApplication();
        handler = app.getHandler();
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.pn_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
        }
        info = (EditText) findViewById(R.id.pn_info);
        StatusBarUtil.setStatusBar(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.alter_information,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.finish();
                break;
            case (R.id.alter_commit):
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String info2 = info.getText().toString();
                        NetworkUtil.PostNeed(handler, activity, info2);
                        ActivityController.removeActivity(activity);
                        activity.finish();
                    }
                }).start();
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
