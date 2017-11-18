package com.maple27.fzuyibao.presenter.util;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.view.activity.LoginActivity;
import com.maple27.fzuyibao.view.activity.MessageChatActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by IMTB on 2017/11/18.
 */

public class MessageUtil {


    //只有此方法使用后，才能正常使用通讯模块
    public static void loginMessageClient(String account, String password, BasicCallback basicCallback){
        //登陆极光im，有异步过程
        JMessageClient.login(account, password, basicCallback);
    }

    public static void registerMessageClient(String account, String password, BasicCallback basicCallback){
        //注册极光im，有异步过
        JMessageClient.register(account, password, basicCallback);
    }

    //获得消息处理对象，该对象用于保存通讯模块使用东西
    public static MessageReciverEntity getMessageReciverEntity(String account, String nickname){
        MessageReciverEntity m = new MessageReciverEntity();
        m.setAccount(account);
        m.setNickname(nickname);
        return m;
    }

    //启动聊天窗口（activity）， me和reciver通过getMessageReciverEntity获得
    public static void startMessageChatActivity(Activity activity, MessageReciverEntity me, MessageReciverEntity reciver){
        MessageChatActivity.startMessageChatActivity(activity, me, reciver);
    }


}
