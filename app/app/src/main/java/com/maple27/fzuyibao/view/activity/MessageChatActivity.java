package com.maple27.fzuyibao.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageChatViewEntity;
import com.maple27.fzuyibao.model.entity.MessageChooseViewEntity;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.presenter.adapter.MessageChatAdapter;
import com.maple27.fzuyibao.presenter.util.ActivityController;
import com.maple27.fzuyibao.presenter.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by IMTB on 2017/11/17.
 */

public class MessageChatActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, View.OnTouchListener {

    final static float TOUCHACTIONTIME = 300;


    final static int MAXINPUTSIZE = 100;

    final static String APPKEY = "976bf293c72d49037901906a";

    final static String KEY_SENDER = "sender";
    final static String KEY_RECIVER = "reciver";

    //for view
    LinearLayout mBack;
    TextView mTvReciver;
    ImageButton mUser;

    ListView mListView;
    MessageChatAdapter mAdapter;

    EditText mContent;
    Button mSend;

    //for data
    List<MessageChatViewEntity> mData;

    MessageReciverEntity mSender;
    MessageReciverEntity mReciver;

    Conversation mConversation;

    //for touch
    float mLastTouchX = 0;
    long mLastTouchTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        //开启api监听收信息
        JMessageClient.registerEventReceiver(this);
        //去除标题栏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_message_chat);
        initData();
        initView();
    }

    private void initView() {
        mBack = (LinearLayout) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTvReciver = (TextView) findViewById(R.id.reciver);
        mTvReciver.setText(mReciver.getNickname());
        mUser = (ImageButton) findViewById(R.id.user);
        mUser.setOnClickListener(this);

        mListView = (ListView) findViewById(R.id.lv_message_chat_windos);
        mListView.setDivider(null);
        mListView.setOnTouchListener(this);
        mAdapter = new MessageChatAdapter(this, mData);
        mListView.setAdapter(mAdapter);
        if(mData.size() > 0){
            mListView.setSelection(mData.size()-1);
        }

        mContent = (EditText) findViewById(R.id.content);
        mContent.addTextChangedListener(this);
        mSend = (Button) findViewById(R.id.send);
        mSend.setOnClickListener(this);
        StatusBarUtil.setStatusBar(this);
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle == null){
            return;
        }
        mSender = (MessageReciverEntity) bundle.get(MessageChatActivity.KEY_SENDER);
        mReciver = (MessageReciverEntity) bundle.get(MessageChatActivity.KEY_RECIVER);

        mData = new ArrayList<MessageChatViewEntity>();



        //初始化会话
        mConversation = JMessageClient.getSingleConversation(mReciver.getAccount(), APPKEY);
        if (mConversation == null) {
            mConversation = Conversation.createSingleConversation(mReciver.getAccount(), APPKEY);
        }

        //从本地中读取历史数据
        List<Message> messages = mConversation.getAllMessage();
        for(int i=0;i<messages.size();i++){
            MessageChatViewEntity m = new MessageChatViewEntity();
            TextContent textContent = (TextContent) messages.get(i).getContent();
            m.setContent(textContent.getText());

            String who = messages.get(i).getFromUser().getNickname();
            m.setName(who);

            if(mReciver.getNickname().equals(who)){
                m.setDirection(MessageChatViewEntity.LEFT);
            }else{
                m.setDirection(MessageChatViewEntity.RIGHT);
            }
            mData.add(m);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);
        ActivityController.removeActivity(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //unable to use it[todo]
        return super.onTouchEvent(event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                quitActivity();
                break;
            case R.id.user:
                Toast.makeText(this, "you press user", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send:
                sendMessage();
                break;
        }
    }

    private void sendMessage() {
        String content = mContent.getText().toString();
        if(content.length() == 0){
            Toast.makeText(this, "input is empty", Toast.LENGTH_SHORT).show();
            return;
        }
        //for data
        TextContent textContent = new TextContent(content);
        Message message = mConversation.createSendMessage(textContent, mSender.getNickname());
        message.setOnSendCompleteCallback(new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    //success
                }else{
                    //failed
                }
            }
        });
        //设置消息发送时的一些控制参数
        MessageSendingOptions options = new MessageSendingOptions();
        options.setNeedReadReceipt(false);//是否需要对方用户发送消息已读回执
        options.setRetainOffline(true);//是否当对方用户不在线时让后台服务区保存这条消息的离线消息
        options.setShowNotification(true);//是否让对方展示sdk默认的通知栏通知
        options.setCustomNotificationEnabled(false);//是否需要自定义对方收到这条消息时sdk默认展示的通知栏中的文字
//        if (false) {
////            options.setNotificationTitle(mEt_customNotifyTitle.getText().toString());//自定义对方收到消息时通知栏展示的title
////            options.setNotificationAtPrefix(mEt_customNotifyAtPrefix.getText().toString());//自定义对方收到消息时通知栏展示的@信息的前缀
////            options.setNotificationText(mEt_customNotifyText.getText().toString());//自定义对方收到消息时通知栏展示的text
//        }
        //发送消息
        JMessageClient.sendMessage(message, options);

        //for view
        MessageChatViewEntity m = new MessageChatViewEntity();
        m.setDirection(MessageChatViewEntity.RIGHT);
        m.setName(mSender.getNickname());
        m.setContent(content);
        mData.add(m);
        mAdapter.notifyDataSetChanged();
        mListView.setSelection(mData.size() - 1);
        mContent.setText("");
    }

    //接受信息
    public void onEvent(MessageEvent event){
        Message msg = event.getMessage();
        switch (msg.getContentType()){
            case text:
                String who = msg.getFromUser().getNickname();
                if(who.equals(mReciver.getNickname())){
                    //for data
                    MessageChatViewEntity m = new MessageChatViewEntity();
                    m.setDirection(MessageChatViewEntity.LEFT);
                    TextContent textContent = (TextContent) msg.getContent();
                    m.setContent(textContent.getText());
                    m.setName(mReciver.getNickname());
                    mData.add(m);
                    //for view
                    mAdapter.notifyDataSetChanged();;
                    mListView.setSelection(mData.size()-1);

                }
                break;
        }
    }

    //利用此方法启动activity
    public static void startMessageChatActivity(Activity activity, MessageReciverEntity sender, MessageReciverEntity reciver){
        Intent intent = new Intent();
        intent.setClass(activity, MessageChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MessageChatActivity.KEY_SENDER, sender);
        bundle.putSerializable(MessageChatActivity.KEY_RECIVER, reciver);
        intent.putExtras(bundle);
        Toast.makeText(activity, "start chat", Toast.LENGTH_SHORT).show();
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
    }

    void quitActivity(){
        finish();
        overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length() >MAXINPUTSIZE){
            mContent.setText(charSequence.subSequence(0,charSequence.length()-1));
            mContent.setSelection(charSequence.length()-1);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //处理键盘收缩
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        switch (motionEvent.getAction()){

            case MotionEvent.ACTION_DOWN:
                mLastTouchX = motionEvent.getX();
                mLastTouchTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float nowX = motionEvent.getX();
                long nowTime = System.currentTimeMillis();
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                float TOUCHACTIONX = wm.getDefaultDisplay().getWidth()/4;
                if(nowTime - mLastTouchTime < TOUCHACTIONTIME){
                    if(nowX - mLastTouchX > TOUCHACTIONX){
                        //满足短时间，移动相应距离，就进行相应
                        quitActivity();
                    }
                }

                break;
        }

        return false;
    }
}
