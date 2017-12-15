package com.maple27.fzuyibao.view.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.GetAvatarBean;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.view.activity.CollectActivity;
import com.maple27.fzuyibao.view.activity.CommodityManageActivity;
import com.maple27.fzuyibao.view.activity.InfoActivity;
import com.maple27.fzuyibao.view.activity.LoginActivity;
import com.maple27.fzuyibao.view.activity.MainActivity;
import com.maple27.fzuyibao.view.activity.SeekManageActivity;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;
import com.yanzhenjie.album.Album;

import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class PersonalFragment extends Fragment {

    public static String MAINURL = "https://interface.fty-web.com/";
    private PersonalFragment fragment;
    private Application app;
    private Activity activity;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;
    private CircleImageView avatar;
    private TextView info;
    private TextView collect;
    private TextView commodity;
    private TextView seek;
    private TextView logoff;

    final Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //初始化数据
            if(msg.what == 45){
                GetAvatarBean bean = (GetAvatarBean) msg.obj;
                if(bean.getError_code()==0){
                    GlideImageLoader imageLoader = new GlideImageLoader();
                    imageLoader.displayImage(context,MAINURL+bean.getData().getAvatar().getAvatar_path(),avatar);
                    Log.d("avatar",MAINURL+bean.getData().getAvatar().getAvatar_path());
                    Toast.makeText(context,"修改头像成功",Toast.LENGTH_SHORT);
                }else Toast.makeText(activity , "获取数据失败" , Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal , null);
        init(view);
        return view;
    }

    public void init(View view){
        fragment = this;
        activity = this.getActivity();
        context = getContext();
        avatar = (CircleImageView) view.findViewById(R.id.avatar);
        info = (TextView) view.findViewById(R.id.personal_info);
        collect = (TextView) view.findViewById(R.id.personal_collect);
        commodity = (TextView) view.findViewById(R.id.personal_commodity);
        seek = (TextView) view.findViewById(R.id.personal_seek);
        logoff = (TextView) view.findViewById(R.id.personal_logoff);
        GlideImageLoader imageLoader = new GlideImageLoader();
        imageLoader.displayImage(context, UserEntity.getAvatar_path(), avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Album.startAlbum(fragment, 100 , 1);
            }
        });
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, InfoActivity.class);
                startActivity(intent);
            }
        });
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CollectActivity.class);
                startActivity(intent);
            }
        });
        commodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, CommodityManageActivity.class);
                startActivity(intent);
            }
        });
        seek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, SeekManageActivity.class);
                startActivity(intent);
            }
        });
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                pref = getContext().getSharedPreferences("userData" , Context.MODE_PRIVATE);
                editor = pref.edit();
                editor.putString("jwt" , null);
                editor.putString("sno" , null);
                editor.commit();
                activity.finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                List<String> s = Album.parseResult(data);
                final String imagePath = s.get(0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        NetworkUtil.PostAvatar(context, imagePath, handler);
                    }
                }).start();
                System.out.println(imagePath + " aaa");
            }else if(resultCode == RESULT_CANCELED){

            }
        }
    }
}
