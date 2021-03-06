package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;

import cn.jpush.im.api.BasicCallback;

/**
 * Created by Maple27 on 2017/12/15.
 */

public class StartupHomePage extends AppCompatActivity {

    private Context context;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String jwt;
    private String sno;
    private UserInfoBean bean;
    private int time = 2;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 1){
                bean = (UserInfoBean) msg.obj;
                if(bean.getError_code()==0){
                    saveUserEntity();
                }else{
                    Toast.makeText(context , "登录信息已过期" , Toast.LENGTH_SHORT).show();
                    time = -1;
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        context = this;
        pref = getSharedPreferences("userData" , MODE_PRIVATE);
        jwt = pref.getString("jwt" , null);
        sno = pref.getString("sno" , null);
        if(jwt != null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    NetworkUtil.LoginByJwt(handler, jwt, sno);
                    //登陆极光
                    MessageUtil.loginMessageClient(StartupHomePage.this, sno, "123456", afterLoginClientCallBack());
                }
            }).start();
        }else{
            time = -1;
        }
        handler.postDelayed(runnable,3000);
        StatusBarUtil.setStatusBar(this);
    }

    //处理登陆极光im的逻辑写在这
    public BasicCallback afterLoginClientCallBack(){
        BasicCallback callback = new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i == 0){
                    //成功
                    Log.i("LoginActivity", "afterLoginClientCallBack success:" + "  i:" + i + "  s:" + s);
                    loginSuccess();
                }else{
                    //失败
                    Log.i("LoginActivity", "afterLoginClientCallBack fail:" + "  i:" + i + "  s:" + s);
                    Toast.makeText(StartupHomePage.this, "im登陆失败" + s, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return callback;
    }

    private void loginSuccess() {
        //登陆极光成功后
    }

    public void saveUserEntity(){
        UserEntity.setJwt(jwt);
        UserEntity.setSno(sno);
        UserEntity.setName(bean.getData().getInfo().get(0).getUser_name());
        UserEntity.setNickname(bean.getData().getInfo().get(0).getNickname());
        UserEntity.setPhone(bean.getData().getInfo().get(0).getPhone());
        UserEntity.setMajor(bean.getData().getInfo().get(0).getMajor());
        UserEntity.setGrade(bean.getData().getInfo().get(0).getGrade());
        UserEntity.setAvatar_path(NetworkUtil.MAINURL+bean.getData().getInfo().get(0).getAvatar_path());
    }

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            time--;
            handler.postDelayed(this,2000);

            if(time == 0){
                Intent intent=new Intent(StartupHomePage.this,MainActivity.class);
                startActivity(intent);
                handler.removeCallbacks(runnable);
                finish();
            }else if (time < 0) {
                Intent intent=new Intent(StartupHomePage.this,LoginActivity.class);
                startActivity(intent);
                //结束线程
                handler.removeCallbacks(runnable);
                finish();
            }
        }
    };
}
