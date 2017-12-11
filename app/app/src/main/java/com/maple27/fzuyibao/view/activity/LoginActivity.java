package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.FzuCookie;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.StringUtil;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Maple27 on 2017/11/3.
 */

public class LoginActivity extends AppCompatActivity {

    private ImageView logo;
    private Context context;
    private Elements tableEles;
    private Button login;
    private EditText sno;
    private EditText password;
    private EditText vcode;
    private LoginBean bean;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String token;
    public static String MAINURL = "https://interface.fty-web.com/";

    private static final int SUCCESS = 1;
    private static final int FALL = 2;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                //加载网络成功进行UI的更新,处理得到的图片资源
                case SUCCESS:
                    //通过message，拿到字节数组
                    byte[] Picture = (byte[]) msg.obj;
                    //使用BitmapFactory工厂，把字节数组转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeByteArray(Picture, 0, Picture.length);
                    //通过imageview，设置图片
                    logo.setImageBitmap(bitmap);

                    break;
                //当加载网络失败执行的逻辑代码
                case FALL:
                    Toast.makeText(context, "网络出现了问题", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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
        logo = (ImageView) findViewById(R.id.logo_login);
        vcode = (EditText) findViewById(R.id.vcode);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String str_sno = sno.getText().toString();
                            String str_password = password.getText().toString();
                            String code = vcode.getText().toString();
                            try {
                                String b = NetworkUtil.VCode(handler);
                                Log.i("aaa",b);
                                String a = NetworkUtil.Login1(str_sno, str_password,code);
                                if(a.equals("登录成功")){
                                    String result = NetworkUtil.getCookieHtml("http://59.77.226.35/jcxx/xsxx/StudentInformation.aspx");
                                    Log.d("uu", result);
                                    Document document = Jsoup.parse(result);
                                    tableEles=document.select("table[style=border-collapse: collapse; table-layout:fixed;]");
                                    String sno = tableEles.select("span[id=ContentPlaceHolder1_LB_xh]").text();
                                    String name = tableEles.select("tr").get(0).select("span[id=ContentPlaceHolder1_LB_xm]").text();
                                    String phone = tableEles.select("span[id=ContentPlaceHolder1_LB_lxdh]").text();
                                    String major = tableEles.select("span[id=ContentPlaceHolder1_LB_zymc]").text();
                                    String grade = tableEles.select("span[id=ContentPlaceHolder1_LB_nj").text();
                                    bean = NetworkUtil.Login(sno, name, grade, major, phone);
                                    MessageUtil.loginMessageClient(LoginActivity.this, sno, "123456", afterLoginClientCallBack());
                                    saveUserEntity();
                                }else {
                                    Looper.prepare();
                                    Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            /*if(bean.getError_code()==0){
                                //loginSuccess();
                                saveUserEntity();
                                MessageUtil.loginMessageClient(LoginActivity.this, str_sno, "123456", afterLoginClientCallBack());
                            }else{
                                Looper.prepare();
                                Toast.makeText(context, bean.getMessage(), Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }*/
                            //UserEntity.setJwt("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzbm8iOiIwMzE1MDIyMTAiLCJleHAiOjE1MTIyMDY3MjB9.w5njlN6OsCzKQ4utjMVZKKZMh7uzdE1eHI_gHVj8Ofc");
                        }
                    }).start();
                }
            }
        });
        StatusBarUtil.setStatusBar(this);
    }

    /*public void getInfo(){
        if (!StringUtil.isEmpty(FzuCookie.get().getId())) {
            HttpUtil.Login();
        }
        String result=HttpUtil.getCookieHtml("http://59.77.226.35/jcxx/xsxx/StudentInformation.aspx");
        if (result == null) {
            return false;
        }
        Document document = Jsoup.parse(result);
        Elements tableEles=document.select("table[style=border-collapse: collapse; table-layout:fixed;]");
        String xuehao=tableEles.select("span[id=ContentPlaceHolder1_LB_xh]").text();
        String name=tableEles.select("tr").get(0).select("span[id=ContentPlaceHolder1_LB_xm]").text();
        String sex=tableEles.select("span[id=ContentPlaceHolder1_LB_xb]").text();
        String xymc=tableEles.select("span[id=ContentPlaceHolder1_LB_xymc]").text();
        String zymc=tableEles.select("span[id=ContentPlaceHolder1_LB_zymc]").text();
        String nianji=tableEles.select("span[id=ContentPlaceHolder1_LB_nj").text();
        String banji=tableEles.select("span[id=ContentPlaceHolder1_LB_bh]").text();
        DBManager dbManager = new DBManager(context);
        User user=dbManager.queryUser(xuehao);
        user.setName(name);
        user.setSex(sex);
        user.setXymc(xymc);
        user.setZymc(zymc);
        user.setNianji(nianji);
        user.setBanji(banji);
        DefaultConfig.get().setUserName(name);
        dbManager.updateUser(user);
        return true;
    }*/

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

    public void saveUserEntity(){
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
