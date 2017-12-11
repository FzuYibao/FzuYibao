package com.maple27.fzuyibao.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.CommodityBean;
import com.maple27.fzuyibao.model.bean.DetailsBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;
import com.youth.banner.Banner;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maple27 on 2017/11/21.
 */

public class DetailsActivity extends AppCompatActivity {

    public static String MAINURL = "https://interface.fty-web.com/";
    private AppCompatActivity activity;
    private Banner banner;
    private TextView name;
    private TextView info;
    private TextView price;
    private TextView userName;
    private TextView time;
    private CircleImageView avatar;
    private Button connect;
    private DetailsBean bean;
    private String id;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 9){
                bean = (DetailsBean) msg.obj;
                if(bean.getError_code()==0){
                    init();
                }else Toast.makeText(activity , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        getData();
    }

    public void init(){
        activity = this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setSubtitle("");
            actionBar.setTitle("");
        }
        banner = (Banner) findViewById(R.id.details_banner);
        name = (TextView) findViewById(R.id.details_name);
        info = (TextView) findViewById(R.id.details_info);
        price = (TextView) findViewById(R.id.details_price);
        userName = (TextView) findViewById(R.id.details_userName);
        time = (TextView) findViewById(R.id.details_time);
        avatar = (CircleImageView) findViewById(R.id.details_avatar);
        connect = (Button) findViewById(R.id.details_connect);
        banner.setImageLoader(new GlideImageLoader());
        List<String> list = new ArrayList<>();
        if(bean.getData().getGoods().getPhoto()!=null){
            for(int i=0;i<bean.getData().getGoods().getPhoto().size();i++){
                list.add(MAINURL+bean.getData().getGoods().getPhoto().get(i));
            }
        }
        //轮播图测试
        banner.setImages(list);
        banner.start();
        name.setText(bean.getData().getGoods().getGoods_name());
        info.setText(bean.getData().getGoods().getDescription());
        price.setText("¥"+bean.getData().getGoods().getPrice());
        userName.setText(bean.getData().getGoods().getNickname());
        time.setText(bean.getData().getGoods().getTime());
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.displayImage(activity, MAINURL+bean.getData().getGoods().getAvatar_path(), avatar);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageUtil.startMessageEntry(activity, bean.getData().getGoods().getSno());
            }
        });
        StatusBarUtil.setStatusBar(this);
    }

    //获取商品详情信息
    public void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkUtil.GetDetailsInfo(id, handler);
            }
        }).start();
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
        }
        return true;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
