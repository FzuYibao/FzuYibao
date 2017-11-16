package com.example.lenovo_pc.jsontest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String result;
                        OkHttpClient okHttpClient =new OkHttpClient();
                        Response response ;
                        RequestBody requestBody =new FormBody.Builder()
                                .add("sno","031502414")
                                .add("password","092235")
                                .build();
                        Request request = new Request.Builder()
                                .url("https://interface.fty-web.com/auth/login")
                                .post(requestBody)
                                .build();
                        try{
                            response = okHttpClient.newCall(request).execute();
                            result = new String(response.body().bytes());
                            Log.d("login", result);
                            Gson gson = new Gson();
                            loginBean = gson.fromJson(result,new TypeToken<LoginBean>(){}.getType());
                            Log.d("print",loginBean.getData().getUsr().getMajor());
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        });
    }




}
