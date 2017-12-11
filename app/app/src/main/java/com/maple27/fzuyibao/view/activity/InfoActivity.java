package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

public class InfoActivity extends AppCompatActivity {

    private AppCompatActivity activity;
    private TextView nickname;
    private TextView username;
    private TextView major;
    private TextView grade;
    private TextView phone;
    private CircleImageView avatar;
    private Button logoff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_info);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        phone.setText(UserEntity.getPhone());
        nickname.setText(UserEntity.getNickname());
    }

    public void init(){
        activity = this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.info_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
            //here 加返回键图片
        }
        logoff = (Button) findViewById(R.id.logoff);
        avatar = (CircleImageView) findViewById(R.id.info_avatar) ;
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.displayImage(this, UserEntity.getAvatar_path(), avatar);
        nickname = (TextView) findViewById(R.id.info_nickname);
        username = (TextView) findViewById(R.id.info_name);
        major = (TextView) findViewById(R.id.info_major);
        grade = (TextView) findViewById(R.id.info_grade);
        phone = (TextView) findViewById(R.id.info_phone);
        nickname.setText(UserEntity.getNickname());
        username.setText(UserEntity.getName());
        major.setText(UserEntity.getMajor());
        grade.setText(UserEntity.getGrade());
        phone.setText(UserEntity.getPhone());
        nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,AlterNicknameActivity.class);
                startActivity(intent);
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this,AlterPhoneActivity.class);
                startActivity(intent);
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoActivity.this, LoginActivity.class);
                startActivity(intent);
                ActivityController.removeActivity(activity);
                activity.finish();
            }
        });
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
}
