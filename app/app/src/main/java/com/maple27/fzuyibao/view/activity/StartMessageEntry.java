package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.util.GlideImageLoader;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;
import com.maple27.fzuyibao.view.custom_view.CircleImageView;

/**
 * Created by IMTB on 2017/11/19.
 */

public class StartMessageEntry extends AppCompatActivity implements View.OnClickListener {

    public final static String STARTKEY = "startkey";
    public static String MAINURL = "https://interface.fty-web.com/";

    LinearLayout mBack;
    GlideImageLoader imageLoader;

    CircleImageView avatar;
    TextView mUser;
    TextView mNum;
    TextView mPhone;
    TextView mMajor;
    TextView mYear;

    Button button;

    UserInfoBean mBean;

    Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmessage);
        imageLoader = new GlideImageLoader();
        mBack = (LinearLayout) findViewById(R.id.start_message_back);
        mBack.setOnClickListener(this);
        initData();
    }

    private void initData() {

        mHandler = new Handler();
        Intent intent = getIntent();
        final String sno = intent.getStringExtra(StartMessageEntry.STARTKEY);
        final String jwt = UserEntity.getJwt();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBean = NetworkUtil.getUserInfoBean(jwt, sno);
                Log.i("StartMessageEntry", "" + mBean.getError_code());
                if(mBean.getError_code() != 0){
                    Log.i("StartMessageEntry", "occur a error");
                }else{
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            updateView();
                        }
                    });
                }
            }
        }).start();
        StatusBarUtil.setStatusBar(this);
    }

    private void updateView() {

        avatar = (CircleImageView) findViewById(R.id.avatar_message_entry);
        //imageLoader.displayImage(this,mBean.getData().getInfo().get,avatar);
        mNum = (TextView) findViewById(R.id.start_message_num);
        mNum.setText(mBean.getData().getInfo().get(0).getSno());
        mUser = (TextView) findViewById(R.id.start_message_username);
        mUser.setText(mBean.getData().getInfo().get(0).getUser_name());
        mPhone = (TextView) findViewById(R.id.start_message_phone);
        mPhone.setText(mBean.getData().getInfo().get(0).getPhone());
        mMajor = (TextView) findViewById(R.id.start_message_major);
        mMajor.setText(mBean.getData().getInfo().get(0).getMajor());
        mYear = (TextView) findViewById(R.id.start_message_year);
        mYear.setText(mBean.getData().getInfo().get(0).getGrade());

        button = (Button) findViewById(R.id.start_message);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_message:
                String reciver_account = mBean.getData().getInfo().get(0).getSno();
                String reciver_nickname = mBean.getData().getInfo().get(0).getNickname();
                MessageReciverEntity reciver = MessageUtil.getMessageReciverEntity(reciver_account, reciver_nickname);

                MessageReciverEntity sender = MessageUtil.getMessageSenderEntity();

                MessageUtil.startMessageChatActivity(this, sender, reciver);
                break;
            case R.id.start_message_back:
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
                break;
        }
    }

    public static void startMessageUserInfo(Context context, String sno){
        Intent intent = new Intent();
        intent.putExtra(StartMessageEntry.STARTKEY, sno);
        intent.setClass(context, StartMessageEntry.class);
        context.startActivity(intent);
    }

}
