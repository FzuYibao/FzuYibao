package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.DetailsBean;
import com.maple27.fzuyibao.model.bean.GetAvatarBean;
import com.maple27.fzuyibao.model.bean.LibraryBean;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.bean.SeekBean;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.UserEntity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.maple27.fzuyibao.presenter.adapter.LibraryAdapter;

/**
 * Created by Maple27 on 2017/11/13.
 */

public class NetworkUtil {

    private static final String TAG = "HttpUtil";
    public static final String LOGIN = "http://59.77.226.32/logincheck.asp";
    // parameter muser,passwwd,x=0,y=0
    public static final String LOGIN_CHK_XS = "http://59.77.226.35/loginchk_xs.aspx";
    // parameter id num       从logincheck.asp获取
    //返回 id
    public static String MAINURL = "https://interface.fty-web.com/";
    public static String LOGINURL = "login/login";
    public static String POSTAVATARURL = "user/update_avatar";
    public static String POSTURL = "Goods/add_goods";
    public static String POSTNEEDURL = "Wants/insert_wants";
    public static String DONATEURL = "Books/donate_books";
    public static String RENTURL = "Books/rent_books";
    public static String GETCOMMODITYURL = "Goods/show_goods_by_type";
    public static String GETLIBRARYURL = "Books/show_books_by_type";
    public static String GETDETAILSURL = "Goods/show_all_goods";
    public static String GETSEEKURL = "Wants/show_all_wants";
    public static String UPDATEUSERMESSAGEURL = "user/update_user_info";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static String getCookieHtml(String targetUrl){
        String html=null;
        OkHttpClient okHttpClient=new OkHttpClient.Builder().build();
        Log.i(TAG, "id:" + FzuCookie.get().getId());
        Request request=new Request.Builder()
                .url(targetUrl+"?id="+ FzuCookie.get().getId())
                .addHeader("Cookie", FzuCookie.get().getCookie()+"")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Connection","keep-alive")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            String result=new String(response.body().bytes());
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return html;
    }

    public static String VCode(Handler handler){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://59.77.226.32/captcha.asp")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            //得到从网上获取资源，转换成我们想要的类型
            byte[] Picture_bt = response.body().bytes();
            //通过handler更新UI
            Message message = handler.obtainMessage();
            message.obj = Picture_bt;
            message.what = 1;
            handler.sendMessage(message);
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String Login1(String sno, String password,String code) throws Exception{
        Log.i(TAG, "user:" + sno+" pass:"+password);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoginInterceptor()).build();
        FormBody formBody=new FormBody.Builder().add("muser",sno).add("passwd",password).add("Verifycode",code).build();
        Request request=new Request.Builder()
                .url("http://59.77.226.32/logincheck.asp")
                .method("Post",null)
                .post(formBody)
                .addHeader("Referer","http://jwch.fzu.edu.cn/")
                .addHeader("Connection","keep-alive")
                .build();

        Response response=okHttpClient.newCall(request).execute();
        if(!response.message().equals("OK")){
            Log.i(TAG,"网络出错");
            return "网络出错";
        }
        String result = new String(response.body().bytes());
        if (result.contains("charset=gb2312")){
            result=new String(result.getBytes(),"gb2312");
        }
        Log.i(TAG, "Login: "+result);
        Log.i(TAG, result);
        if(result.contains("密码错误，请重新登录，或与学院教学办联系！")||result.contains("用户名错误，请确认是否输入错误，用户名前请不要加字母！！")){
            Log.i(TAG,"密码错误");
            return "密码错误";
        }
        if (result.contains("left.aspx")){
            Log.i(TAG, "登录成功");
            return "登录成功";
        }
        return "登录失败，请检查用户名和密码是否正确!";
    }

    public static LoginBean Login(String sno, String name, String grade, String major, String phone){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("sno" , sno)
                .add("user_name" , name)
                .add("grade",grade)
                .add("major",major)
                .add("phone",phone)
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

    public static void Post(Handler handler, Context context, String name, String category, String info, String tag, String price, List<String> image){
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
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            //handler.obtainMessage(279, bean).sendToTarget();
            int error_code = bean.getError_code();
            if(error_code==0){
                Looper.prepare();
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Donate(Context context, String name, String image){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if(image!=null){
                File file = new File(image);
                if(file != null) {
                    builder.addFormDataPart("photo", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                }

        }
        builder.addFormDataPart("jwt" , UserEntity.getJwt());
        builder.addFormDataPart("books_name" , name);
        builder.addFormDataPart("type" , "21000");
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url(MAINURL+DONATEURL)
                .post(requestBody)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("donate", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LibraryBean>(){}.getType();
            LibraryBean bean = gson.fromJson(result, type);
            int error_code = bean.getError_code();
            if(error_code==0){
                Looper.prepare();
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void PostNeed(Handler handler, Context context, String info){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("title" , "1")
                .add("type" , "1")
                .add("description" , info)
                .add("price" , "1")
                .add("wtag_description" , "1")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+POSTNEEDURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("pn", result);
            Gson gson = new Gson();
            Type type = new TypeToken<SeekBean>(){}.getType();
            SeekBean bean = gson.fromJson(result, type);
            handler.obtainMessage(25, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Rent(Handler handler, String id, LibraryAdapter.ViewHolder viewHolder){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("bid" , id)
                .add("days" , "30")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+RENTURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("rent", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LibraryBean>(){}.getType();
            LibraryBean bean = gson.fromJson(result, type);
            if(bean.getError_code()==0)
                handler.obtainMessage(10, viewHolder).sendToTarget();
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

    public static void GetLibraryInfo(Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , "21000")
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETLIBRARYURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getLibrary", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LibraryBean>(){}.getType();
            LibraryBean bean = gson.fromJson(result, type);
            handler.obtainMessage(8, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GetDetailsInfo(String id, Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("gid" , id)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETDETAILSURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getDetails", result);
            Gson gson = new Gson();
            Type type = new TypeToken<DetailsBean>(){}.getType();
            DetailsBean bean = gson.fromJson(result, type);
            handler.obtainMessage(9, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GetSeekInfo(Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETSEEKURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getDetails", result);
            Gson gson = new Gson();
            Type type = new TypeToken<SeekBean>(){}.getType();
            SeekBean bean = gson.fromJson(result, type);
            handler.obtainMessage(24, bean).sendToTarget();
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

    public static UserInfoBean getUserInfoBean(String jwt, String sno){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt", jwt)
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

    public static LoginBean AlterNickname(Context context, String nickname) {
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt",UserEntity.getJwt())
                .add("nickname",nickname)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL + UPDATEUSERMESSAGEURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("alterPhone", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LoginBean>() {
            }.getType();
            LoginBean bean = gson.fromJson(result,type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static LoginBean AlterPhone(Context context, String newPhone) {
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt",UserEntity.getJwt())
                .add("phone",newPhone)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL + UPDATEUSERMESSAGEURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("alterPhone", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LoginBean>() {
            }.getType();
            LoginBean bean = gson.fromJson(result,type);
            return bean;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
