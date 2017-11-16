package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple27.fzuyibao.model.bean.GetAvatarBean;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.entity.UserEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Maple27 on 2017/11/13.
 */

public class NetworkUtil {

    public static String MAINURL = "https://interface.fty-web.com/";
    public static String LOGINURL = "auth/login";
    public static String POSTAVATARURL = "user/update_avatar";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static LoginBean Login(String sno, String password){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("sno" , sno)
                .add("password" , password)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+LOGINURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("login", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LoginBean>(){}.getType();
            LoginBean bean = gson.fromJson(result, type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String PostAvatar(Context context, String imagePath){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(imagePath);
        if(file != null) {
            builder.addFormDataPart("jwt" , UserEntity.getJwt());
            builder.addFormDataPart("avatar", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
        }
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(MAINURL+POSTAVATARURL)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("postAvatar", result);
            Gson gson = new Gson();
            Type type = new TypeToken<GetAvatarBean>(){}.getType();
            GetAvatarBean bean = gson.fromJson(result, type);
            return bean.getData().getAvatar().getAvatar_path();
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

}
