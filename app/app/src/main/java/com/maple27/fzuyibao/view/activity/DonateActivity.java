package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.yanzhenjie.album.Album;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class DonateActivity extends AppCompatActivity {

    private AppCompatActivity activity;
    private EditText name;
    private Button addPicture;
    private String image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_donate);
        init();
    }

    public void init(){
        activity = this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.donate_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
        }
        name = (EditText) findViewById(R.id.donate_name);
        addPicture = (Button) findViewById(R.id.addPicture_donate);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.startAlbum(DonateActivity.this, 777 , 1);
            }
        });
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
                        String name2 = name.getText().toString();
                        NetworkUtil.Donate(activity, name2, image);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 777){
            if(resultCode == RESULT_OK){
                List<String> s = Album.parseResult(data);
                image = s.get(0);
                System.out.println(image + " aaa");
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
