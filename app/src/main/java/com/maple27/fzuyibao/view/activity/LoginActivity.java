package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.presenter.util.MessageUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by Maple27 on 2017/11/3.
 */

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private Button login;
    private EditText sno;
    private EditText password;
    private LoginBean bean;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String token;
    public static String MAINURL = "https://interface.fty-web.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    public void init(){
        context = this;
        login = (Button) findViewById(R.id.login);
        sno = (EditText) findViewById(R.id.sno_login);
        password = (EditText) findViewById(R.id.password_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(true/*valid()*/){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str_sno = sno.getText().toString();
                            String str_password = password.getText().toString();
                            bean = NetworkUtil.Login(str_sno, str_password);
                            if(bean.getError_code()==0){
                                savaUserEntity();
                                MessageUtil.loginMessageClient(LoginActivity.this, str_sno, "123456", afterLoginClientCallBack());
                            }else{
                                Looper.prepare();
                                Toast.makeText(context, bean.getMessage(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                }
            }
        });
        StatusBarUtil.setStatusBar(this);
    }

    //输入合法性检验
    public boolean valid(){
        String str_sno = sno.getText().toString();
        String str_password = password.getText().toString();
        if(str_sno.equals("")){
            Toast.makeText(context, getResources().getString(R.string.scanSno), Toast.LENGTH_SHORT).show();
            return false;
        }else if(str_password.equals("")){
            Toast.makeText(context, getResources().getString(R.string.scanPassword), Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public void savaUserEntity(){
        UserEntity.setJwt(bean.getData().getUser().getJwt());
        UserEntity.setSno(bean.getData().getUser().getSno());
        UserEntity.setName(bean.getData().getUser().getUser_name());
        UserEntity.setNickname(bean.getData().getUser().getNickname());
        UserEntity.setPhone(bean.getData().getUser().getPhone());
        UserEntity.setMajor(bean.getData().getUser().getMajor());
        UserEntity.setGrade(bean.getData().getUser().getGrade());
        UserEntity.setAvatar_path(MAINURL+bean.getData().getUser().getAvatar_path());
    }


    public void loginSuccess(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public boolean TokenExist(){
        pref = getSharedPreferences("userData" , MODE_PRIVATE);
        token = pref.getString("token" , null);
        if(token == null) return false;
        else return true;
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
                    Toast.makeText(LoginActivity.this, "登陆失败" + s, Toast.LENGTH_SHORT).show();
                }
            }
        };
        return callback;
    }

}
