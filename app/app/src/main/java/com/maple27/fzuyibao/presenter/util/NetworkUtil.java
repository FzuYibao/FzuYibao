package com.maple27.fzuyibao.presenter.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.maple27.fzuyibao.model.bean.CollectBean;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.DetailsBean;
import com.maple27.fzuyibao.model.bean.GetAvatarBean;
import com.maple27.fzuyibao.model.bean.LibraryBean;
import com.maple27.fzuyibao.model.bean.LoginBean;
import com.maple27.fzuyibao.model.bean.SeekBean;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.UserEntity;

import java.io.File;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.maple27.fzuyibao.presenter.adapter.LibraryAdapter;
import com.maple27.fzuyibao.view.activity.LoginActivity;

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
    public static final String MAINURL = "https://interface.fty-web.com/";
    public static final String LOGINURL = "login/login";
    public static final String GETUSERINFO = "user/get_info";
    public static final String VALID_CODE="http://59.77.226.32/captcha.asp";
    public static final String POSTAVATARURL = "user/update_avatar";
    public static final String POSTURL = "Goods/add_goods";
    public static final String POSTNEEDURL = "Wants/insert_wants";
    public static final String DONATEURL = "Books/donate_books";
    public static final String RENTURL = "Books/rent_books";
    public static final String ORDERURL = "Books/order_books";
    public static final String SEARCHURL1 = "Goods/show_goods_by_content";
    public static final String SEARCHURL2 = "Books/show_books_by_content";
    public static final String GETCOMMODITYURL = "Goods/show_goods_by_type";
    public static final String GETLIBRARYURL = "Books/show_books_by_type";
    public static final String GETDETAILSURL = "Goods/show_all_goods";
    public static final String GETSEEKURL = "Wants/show_all_wants";
    public static final String GETMANAGEURL = "Goods/show_goods_by_sno";
    public static final String COLLECTURL = "Goods/add_goods_collection";
    public static final String OFFCOMMODITYURL = "Goods/delete_all_goods";
    public static final String DELETECOLLECTURL = "Goods/delete_goods_collection";
    public static final String DELETESEEKURL = "Wants/delete_wants";
    public static final String UPDATEUSERMESSAGEURL = "user/update_user_info";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final long TIME_OUT=3*1000l;

    public static void LoginByJwt(Handler handler, String jwt, String sno){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , jwt)
                .add("sno" , sno)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETUSERINFO)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("login_by_jwt", result);
            Gson gson = new Gson();
            Type type = new TypeToken<UserInfoBean>(){}.getType();
            UserInfoBean bean = gson.fromJson(result, type);
            handler.obtainMessage(1,bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static Bitmap getVerifyCode() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new LoginInterceptor()).build();
        Request request=new Request.Builder().url(VALID_CODE).build();
        try {
            ResponseBody responseBody = okHttpClient.newCall(request).execute().body();
            InputStream in=responseBody.byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int autoLogin(Context context, String sno, String password){
        int result=loginByVerifyCode(context, sno, password);
        if (result == ResultCode.LOGIN_VERIFY_ERROR || result == ResultCode.LOGIN_ERROR) {
            //result = loginByJwt(context,user.getFzuAccount(), user.getFzuPasssword());
        }
//        int result = loginByJwt(user.getFzuAccount(), user.getFzuPasssword());
        if (result==ResultCode.LOGIN_PWD_ERROR){

        }
        return result;
    }

    public static int loginByVerifyCode(Context context, String sno, String password) {
        int res= ResultCode.LOGIN_ERROR;
        long startTime = System.currentTimeMillis();
        long endTime = System.currentTimeMillis();
        while (res != ResultCode.LOGIN_SUCCESS && endTime-startTime<=TIME_OUT&&res!=ResultCode.LOGIN_PWD_ERROR) {
            try {
                Bitmap bitmap = getVerifyCode();
                if (bitmap != null) {
                    VerifyCodeDto verifyCodeDto = null;
                    verifyCodeDto = postVerifyCode(Base64Util.bitmapToBase64(bitmap));
                    Log.d("vvv",verifyCodeDto.getResult());
                    if (verifyCodeDto != null) {
                        try {
                            res = LoginJWC(sno, password, verifyCodeDto.getResult());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            endTime=System.currentTimeMillis();
        }
        if (res == ResultCode.LOGIN_SUCCESS) {
            return res;
        }
        //密码错误，退回登录界面
        if (res == ResultCode.LOGIN_PWD_ERROR&&!(context instanceof LoginActivity)) {
            Log.i(TAG, "loginByVerifyCode: 密码错误，尝试跳到登录页面");

        }
        return res;
    }

    public static VerifyCodeDto postVerifyCode(String base64) {
        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://soft.hs97.cn:8088/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VerifyService service = retrofit.create(VerifyService.class);
        VerifyCodeRequest verifyCodeRequest = new VerifyCodeRequest();
        verifyCodeRequest.setBase64(base64);
        verifyCodeRequest.setTrim("\n");
        verifyCodeRequest.setWhitelist("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        Call<VerifyCodeDto> call = service.getVerifyCode(verifyCodeRequest);
        try {
            VerifyCodeDto verifyCodeDto = call.execute().body();
            return verifyCodeDto;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int LoginJWC(String sno, String password, String code) throws Exception{
        Log.i(TAG, "user:" + sno+" pass:"+password);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoginInterceptor()).build();
        FormBody formBody=new FormBody.Builder()
                .add("muser",sno)
                .add("passwd",password)
                .add("Verifycode",code)
                .build();
        Request request=new Request.Builder()
                .url("http://59.77.226.32/logincheck.asp")
                .method("Post",null)
                .post(formBody)
                .addHeader("Cookie",FzuCookie.get().getCookie()+"")
                .addHeader("Referer","http://jwch.fzu.edu.cn/")
                .addHeader("Connection","keep-alive")
                .build();
        Response response=okHttpClient.newCall(request).execute();
        if(!response.message().equals("OK")){
            Log.i(TAG,"网络出错");
            return ResultCode.NET_ERROR;
        }
        String result = new String(response.body().bytes());

        String gb2312Result=new String(result.getBytes("GBK"),"GBK");
        String utf8Result=new String(result.getBytes(),"utf-8");
        Log.i(TAG, "result:"+gb2312Result);
        if (result.contains("charset=gb2312")){

        }
        Log.i(TAG, "Login: length:"+result.length());
        String alert=result.replaceAll(".*alert\\((.*)\\).*","$1");
        Log.i(TAG, "Login: regex:"+alert+" length:"+alert.length());
        if (alert.length() == 50) {
            Log.i(TAG, "Login: 验证码错误");
            return ResultCode.LOGIN_VERIFY_ERROR;
        }
        if(alert.length() == 70){
            Log.i(TAG,"密码错误");
            return ResultCode.LOGIN_PWD_ERROR;
        }
        if (result.contains("left.aspx")){
            Log.i(TAG, "登录成功");
            return ResultCode.LOGIN_SUCCESS;
        }
        return ResultCode.LOGIN_ERROR;
    }

    @Nullable
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

    public static String PostAvatar(Context context, String imagePath, Handler handler){
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
            handler.obtainMessage(45, bean).sendToTarget();
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
            handler.obtainMessage(281, bean).sendToTarget();
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

    public static void Order(Handler handler, String id, LibraryAdapter.ViewHolder viewHolder){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("bid" , id)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+ORDERURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("order", result);
            Gson gson = new Gson();
            Type type = new TypeToken<LibraryBean>(){}.getType();
            LibraryBean bean = gson.fromJson(result, type);
            if(bean.getError_code()==0)
                handler.obtainMessage(1000, viewHolder).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void SearchCommodity(Handler handler, Context context, String category, String keywords, int page){
        String result;
        String URL;
        if(category.equals("20000")){
            URL = SEARCHURL2;
        }else URL = SEARCHURL1;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("content" , keywords)
                .add("type" , category)
                .add("page" , page+"")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+URL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("search", result);
            Gson gson = new Gson();
            if(category.equals("20000")){
                Type type = new TypeToken<LibraryBean>(){}.getType();
                LibraryBean bean = gson.fromJson(result, type);
                handler.obtainMessage(69, bean).sendToTarget();
            }else {
                Type type = new TypeToken<CommodityBean>(){}.getType();
                CommodityBean bean = gson.fromJson(result, type);
                handler.obtainMessage(270, bean).sendToTarget();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GetCommodityInfo(int page, String category, Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , category)
                .add("page" , page+"")
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
            if(page==0){
                handler.obtainMessage(27, bean).sendToTarget();
            }else{
                handler.obtainMessage(120, bean).sendToTarget();
            }

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

    public static void GetCollectInfo(Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , "1")
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETMANAGEURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getManage", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(62, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void GetCommodityManageInfo(Handler handler){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("type" , "0")
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+GETMANAGEURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("getManage", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(36, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CollectCommodity(Handler handler, String id){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("gid" , id)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+COLLECTURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("collect", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CollectBean>(){}.getType();
            CollectBean bean = gson.fromJson(result, type);
            handler.obtainMessage(700, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void OffCommodity(Handler handler, String id){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("gid" , id)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+OFFCOMMODITYURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("offCommodity", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(46, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteSeek(Handler handler, String id){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("wid" , id)
                .add("type" , "0")
                .add("page" , "0")
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+DELETESEEKURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("deleteSeek", result);
            Gson gson = new Gson();
            Type type = new TypeToken<SeekBean>(){}.getType();
            SeekBean bean = gson.fromJson(result, type);
            handler.obtainMessage(900, bean).sendToTarget();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DeleteCollect(Handler handler,String id){
        String result;
        OkHttpClient okHttpClient = new OkHttpClient();
        Response response;
        RequestBody requestBody = new FormBody.Builder()
                .add("jwt" , UserEntity.getJwt())
                .add("gid", id)
                .build();
        Request request = new Request.Builder()
                .url(MAINURL+DELETECOLLECTURL)
                .post(requestBody)
                .build();
        try {
            response = okHttpClient.newCall(request).execute();
            result = new String(response.body().bytes());
            Log.d("deleteCollect", result);
            Gson gson = new Gson();
            Type type = new TypeToken<CommodityBean>(){}.getType();
            CommodityBean bean = gson.fromJson(result, type);
            handler.obtainMessage(700, bean).sendToTarget();
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
