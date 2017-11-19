package com.maple27.fzuyibao.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.bean.UserInfoBean;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.presenter.util.MessageUtil;
import com.maple27.fzuyibao.presenter.util.NetworkUtil;

/**
 * Created by IMTB on 2017/11/19.
 */

public class StartMessageEntry extends AppCompatActivity implements View.OnClickListener {

    public final static String STARTKEY = "startkey";

    TextView mUser;
    TextView mNum;
    TextView mPhone;
    TextView mMajor;
    TextView mYear;

    Button button;

    UserInfoBean mBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startmessage);
        initData();
        initView();
;    }

    private void initData() {
        Intent intent = getIntent();
        String sno = intent.getStringExtra(StartMessageEntry.STARTKEY);
        mBean = NetworkUtil.getUserInfoBean(sno);
        if(mBean == null){
            Log.i("StartMessageEntry", "occur a error");
        }
    }

    private void initView() {
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
                String reciver_account = mBean.getData().getInfo().get(0).getUser_name();
                String reciver_nickname = mBean.getData().getInfo().get(0).getNickname();
                MessageReciverEntity reciver = MessageUtil.getMessageReciverEntity(reciver_account, reciver_nickname);

                MessageReciverEntity sender = MessageUtil.getMessageSenderEntity();

                MessageUtil.startMessageChatActivity(this, sender, reciver);
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
