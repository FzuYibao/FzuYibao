package com.maple27.fzuyibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageConversionEntity;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.presenter.adapter.MessageConversionAdapter;
import com.maple27.fzuyibao.presenter.util.MessageUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.content.VoiceContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {

    //view
    ListView mListView;
    LinearLayout mLinearLayout;
    MessageConversionAdapter mAdapter;

    //flag
    boolean isShowListview = false;

    //data
    MessageReciverEntity mSender;
    List<MessageConversionEntity> mData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //开启api监听收信息
        Log.i("MessageFragment", "onCreateView->init JMessageClient");
        JMessageClient.registerEventReceiver(this);
        View view = inflater.inflate(R.layout.fragment_conversion, null);
        initData();
        initView(view);
        return view;
    }

    //for init view
    private void initView(View view) {
        Log.i("MessageFragment", "initView->init view");
        mListView = (ListView) view.findViewById(R.id.lv_choose_conversion);
        mLinearLayout = (LinearLayout) view.findViewById(R.id.ll__no_conversion_background);

        Log.i("MessageFragment", "initView->isShowListview：" + isShowListview);
        if(isShowListview){
            mListView.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.INVISIBLE);

            //set adapter
            mAdapter = new MessageConversionAdapter(getContext(), mData);
            mListView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

            mListView.setOnItemClickListener(this);
        }

    }

    //for init data
    private void initData() {
        Log.i("MessageFragment", "initData->init data");
        //获得me的信息
        UserInfo userInfo = JMessageClient.getMyInfo();
        mSender = new MessageReciverEntity();
        mSender.setAccount(userInfo.getUserName());
        mSender.setNickname(userInfo.getNickname());
        mSender.setAvatar(userInfo.getAddress());

        Log.i("MessageFragment", "initData->userinfo: username:" + userInfo.getUserName() + " nickname:" + userInfo.getNickname()
                + "  avatar:" + userInfo.getAddress());

        //读取本地conversion列表，确定isShowListview
        mData = new ArrayList<MessageConversionEntity>();
        List<Conversation> temp_list = JMessageClient.getConversationList();
        if(temp_list.size() > 0){
            isShowListview = true;
        }
        for(int i=0;i<temp_list.size();i++){
            Conversation temp_conversation = temp_list.get(i);
            UserInfo reciverUserInfo = (UserInfo) temp_conversation.getTargetInfo();

            //MessageReciverEntity
            MessageReciverEntity reciver = new MessageReciverEntity();
            reciver.setAvatar(reciverUserInfo.getAddress());
            reciver.setAccount(reciverUserInfo.getUserName());
            reciver.setNickname(reciverUserInfo.getNickname());

            //MessageConversionEntity
            MessageConversionEntity m = new MessageConversionEntity();
            m.setReciver(reciver);
            m.setAvatar(reciverUserInfo.getAddress());

            //message content
            TextContent temp_textcontent = (TextContent) temp_conversation.getLatestMessage().getContent();
            m.setContent(temp_textcontent.getText());

            m.setTitle(reciverUserInfo.getNickname());

            long time = temp_conversation.getLatestMessage().getCreateTime();
            SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
            Date d1=new Date(time);
            String t1=format.format(d1);
            m.setTime(t1);

            mData.add(m);
            Log.i("MessageFragment", "initData->conversion: reciver:" + reciverUserInfo.getUserName());
        }

    }

    @Override
    public void onDestroyView() {
        Log.i("MessageFragment", "onDestroyView->finish JMessageClient");
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroyView();
    }

    //接受信息
    public void onEvent(MessageEvent event){
        Message msg = event.getMessage();
        UserInfo userInfo = msg.getFromUser();
        int index = findUser(msg);
        Log.i("MessageFragment", "onEvent->" + "from:" + userInfo.getUserName() + "  messageType:" + msg.getContentType());
        Log.i("MessageFragment", "onEvent->" + "user index:" + index);
        switch (msg.getContentType()){
            case text:
                //处理文字消息
                if(index == -1){
                    //之前没有这个对话
                    Log.i("MessageFragment", "create a new text conversation");
                    MessageReciverEntity reciver = new MessageReciverEntity();
                    reciver.setAvatar(userInfo.getAddress());
                    reciver.setAccount(userInfo.getUserName());
                    reciver.setNickname(userInfo.getNickname());

                    MessageConversionEntity m = new MessageConversionEntity();
                    m.setReciver(reciver);
                    m.setAvatar(userInfo.getAddress());

                    //message content
                    TextContent temp_textcontent = (TextContent) msg.getContent();
                    m.setContent(temp_textcontent.getText());

                    m.setTitle(userInfo.getNickname());

                    long time = msg.getCreateTime();
                    SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
                    Date d1=new Date(time);
                    String t1=format.format(d1);
                    m.setTime(t1);

                    mData.add(m);
                    mListView.setAdapter( new MessageConversionAdapter(getContext(), mData));
                }else{
                    Log.i("MessageFragment", "update a new text conversation");
                    //更新对话
                    MessageConversionEntity m = mData.get(index);
                    m.setTitle(userInfo.getNickname());
                    //message content
                    TextContent temp_textcontent = (TextContent) msg.getContent();
                    m.setContent(temp_textcontent.getText());

                    long time = msg.getCreateTime();
                    SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
                    Date d1=new Date(time);
                    String t1=format.format(d1);
                    m.setTime(t1);

                    mListView.setAdapter( new MessageConversionAdapter(getContext(), mData));
                }
                break;
            case image:
                //处理图片消息
                ImageContent imageContent = (ImageContent) msg.getContent();
                imageContent.getLocalPath();//图片本地地址
                imageContent.getLocalThumbnailPath();//图片对应缩略图的本地地址
                break;
            case voice:
                //处理语音消息
                VoiceContent voiceContent = (VoiceContent) msg.getContent();
                voiceContent.getLocalPath();//语音文件本地地址
                voiceContent.getDuration();//语音文件时长
                break;
            case custom:
                break;
        }
    }

    private int findUser(Message msg) {
        String who = msg.getFromUser().getUserName();
        for(int i=0;i<mData.size();i++){
            MessageConversionEntity m = mData.get(i);
            MessageReciverEntity reciverEntity = m.getReciver();
            if(reciverEntity.getAccount().equals(who)){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView == mListView){
            MessageConversionEntity m = mData.get(i);
            Log.i("MessageFragment", "onItemClick->click：" + m.getReciver().getAccount());
            MessageUtil.startMessageChatActivity(getActivity(), mSender, m.getReciver());
        }
    }
}
