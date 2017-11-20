package com.maple27.fzuyibao.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maple27.fzuyibao.R;
import com.maple27.fzuyibao.model.entity.MessageChatViewEntity;
import com.maple27.fzuyibao.model.entity.MessageChooseViewEntity;
import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.presenter.adapter.MessageChooseAdapter;
import com.maple27.fzuyibao.view.activity.MessageChatActivity;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by Maple27 on 2017/11/1.
 */

public class MessageFragment extends Fragment implements AdapterView.OnItemClickListener {

    SmartRefreshLayout mRefresh;
    ListView mListView;
    MessageChooseAdapter mAdapter;
    List<MessageChooseViewEntity> mData = null;

    MessageReciverEntity mMe = null;

    @Override
    public void onDestroyView() {
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //开启api监听收信息
        JMessageClient.registerEventReceiver(this);

        View view = inflater.inflate(R.layout.fragment_message , null);
        initData();
        initView(view, inflater);
        return view;
    }

    private void initData() {
        //生成和我相关的数据
        UserInfo myUserInfo = JMessageClient.getMyInfo();
        mMe = new MessageReciverEntity();
        mMe.setAccount(myUserInfo.getUserName());
        mMe.setNickname(myUserInfo.getNickname());

        //生成相应的列表数据
        mData = new ArrayList<MessageChooseViewEntity>();
        List<Conversation> list = JMessageClient.getConversationList();
        for(int i=0;i<list.size();i++){
            Conversation c = list.get(i);
            //userinfo
            UserInfo who = (UserInfo) c.getTargetInfo();
            MessageReciverEntity reciver =  new MessageReciverEntity();
            reciver.setNickname(who.getNickname());
            reciver.setAccount(who.getUserName());

            //MessageChooseViewEntity
            MessageChooseViewEntity m = new MessageChooseViewEntity();
            m.setReciver(reciver);
            m.setTitle(reciver.getNickname());
            TextContent textContent = (TextContent) c.getLatestMessage().getContent();
            m.setContent("" + textContent.getText());

            //time
            long time = c.getLatestMessage().getCreateTime();
            SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
            Date d1=new Date(time);
            String t1=format.format(d1);
            m.setTime(t1);

            mData.add(m);
        }

    }

    private void initView(View view, LayoutInflater inflater) {
        WaveSwipeHeader waveSwipeHeader = new WaveSwipeHeader(getContext());
        mRefresh = (SmartRefreshLayout) view.findViewById(R.id.refresh_message);
        mRefresh.setRefreshHeader(waveSwipeHeader);
        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                //刷新请求
                initData();
                mAdapter.notifyDataSetChanged();

            }
        });

        mListView = (ListView) view.findViewById(R.id.lv_choose_message);
        mAdapter = new MessageChooseAdapter(getContext(), mData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.lv_choose_message){
            MessageChooseViewEntity data = mData.get(i);
            MessageReciverEntity sender = mMe;
            MessageReciverEntity reciver = data.getReciver();
            MessageChatActivity.startMessageChatActivity(getActivity(), sender, reciver);
        }
    }

    //接受信息
    public void onEvent(MessageEvent event){
        Message msg = event.getMessage();
        switch (msg.getContentType()){
            case text:
                String who = msg.getFromUser().getNickname();
                for(int i=0;i<mData.size();i++){
                    MessageChooseViewEntity m = mData.get(i);
                    if(who.equals(m.getTitle())){
                        //time
                        long time = msg.getCreateTime();
                        SimpleDateFormat format=new SimpleDateFormat("hh:mm:ss");
                        Date d1=new Date(time);
                        String t1=format.format(d1);
                        m.setTime(t1);
                        //content
                        TextContent textContent = (TextContent) msg.getContent();
                        m.setContent(textContent.getText());
                        mAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                break;
        }
    }
}
