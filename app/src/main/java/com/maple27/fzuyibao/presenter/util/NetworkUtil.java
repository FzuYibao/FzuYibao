package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.GetAvatarBean;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.bean.PostBean;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.UserEntity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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
    public static String POSTURL = "Goods/add_goods";
    public static String GETCOMMODITYURL = "Goods/show_goods_by_type";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static LoginBean Login(String sno, String password){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("sno" , "031502210")
                .add("password" , "110539")
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

    public static void Post(Context context, String name, String category, String info, String tag, String price, List<String> image){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(image!=null){
            for(int i=0;i<image.size();i++){
                File file = new File(image.get(i));
                if(file != null) {
                    builder.addFormDataPart("photo"+(i+1), file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                }
            }
        }
        builder.addFormDataPart("jwt" , UserEntity.getJwt());
        builder.addFormDataPart("goods_name" , name);
        builder.addFormDataPart("price" , price);
        builder.addFormDataPart("type" , category);
        builder.addFormDataPart("status" , "1");
        builder.addFormDataPart("description" , info);
        builder.addFormDataPart("tag" , tag);
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(MAINURL+POSTURL)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("post", result);
            Gson gson = new Gson();
            Type type = new TypeToken<PostBean>(){}.getType();
            PostBean bean = gson.fromJson(result, type);
            int error_code = bean.getError_code();
            if(error_code==1){
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GetCommodityInfo(String category, Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , category)
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETCOMMODITYURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getCommodity", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(27, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RefreshCommodityInfo(String category, Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , category)
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETCOMMODITYURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getCommodity", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(279, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserInfoBean getUserInfoBean(String sno){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("sno" , sno)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+"user/get_info")
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getUserInfoBean", result);
            Gson gson = new Gson();
            Type type = new TypeToken<UserInfoBean>(){}.getType();
            UserInfoBean bean = gson.fromJson(result, type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
