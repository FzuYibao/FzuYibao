package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.application.CustomApplication;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.yanzhenjie.album.Album;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/18.
 */

public class PostActivity extends AppCompatActivity {

    private CustomApplication application;
    private Handler handler;
    private AppCompatActivity activity;
    private EditText name;
    private EditText info;
    private EditText tag;
    private EditText price;
    private AppCompatSpinner spinner;
    private Button post;
    private Button addPicture;
    private String category;
    private List<String> image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_post);
        init();
    }

    public void init(){
        application = (CustomApplication) getApplication();
        handler = application.getHandler();
        activity = this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
        }
        name = (EditText) findViewById(R.id.post_name);
        info = (EditText) findViewById(R.id.post_info);
        tag = (EditText) findViewById(R.id.post_tag);
        price = (EditText) findViewById(R.id.post_price);
        spinner = (AppCompatSpinner) findViewById(R.id.post_spinner);
        //post = (Button) findViewById(R.id.doPost);
        addPicture = (Button) findViewById(R.id.addPicture_postNew);
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.startAlbum(PostActivity.this, 666 , 9);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                switch (pos){
                    case 0:category = "11000";break;
                    case 1:category = "12000";break;
                    case 2:category = "13000";break;
                    case 3:category = "31000";break;
                    case 4:category = "33000";break;
                    case 5:category = "32000";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        StatusBarUtil.setStatusBar(this);
        /*post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                    }
                }).start();
                ActivityController.removeActivity(activity);
                activity.finish();
            }
        });*/
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
                        String info2 = info.getText().toString();
                        String tag2 = tag.getText().toString();
                        String price2 = price.getText().toString();
                        if (name2.equals("")){
                            Looper.prepare();
                            Toast.makeText(activity,"请填写商品名称",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if (info2.equals("")){
                            Looper.prepare();
                            Toast.makeText(activity,"介绍一下商品详情吧",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if (tag2.equals("")){
                            Looper.prepare();
                            Toast.makeText(activity,"给商品一个标签吧",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else if (!isNumeric(price2)||price2.equals("")){
                            Looper.prepare();
                            Toast.makeText(activity,"价格请填写数字，单位为RMB",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else{
                            NetworkUtil.Post(handler, activity, name2, category, info2, tag2, price2, image);
                            ActivityController.removeActivity(activity);
                            activity.finish();
                        }
                    }
                }).start();
                break;
            default:
        }
        return true;
    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 666){
            if(resultCode == RESULT_OK){
                List<String> s = Album.parseResult(data);
                final String imagePath = s.get(0);
                image = s;
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
