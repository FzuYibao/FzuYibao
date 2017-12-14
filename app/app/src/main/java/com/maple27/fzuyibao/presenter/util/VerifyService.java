package com.maple27.fzuyibao.presenter.util;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Subscriber;

/**
 * Imported by Maple27 on 2017/12/13.
 */

public interface VerifyService {
    @POST("base64")
    public Call<VerifyCodeDto> getVerifyCode(@Body VerifyCodeRequest request);


    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl, Subscriber subscriber);

}
