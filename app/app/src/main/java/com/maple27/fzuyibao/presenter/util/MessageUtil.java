package com.maple27.fzuyibao.presenter.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.maple27.fzuyibao.model.entity.MessageReciverEntity;
import com.maple27.fzuyibao.model.entity.UserEntity;
import com.maple27.fzuyibao.view.activity.MessageListActivity;
import com.maple27.fzuyibao.view.activity.StartMessageEntry;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by IMTB on 2017/11/18.
 */

public class MessageUtil {


    //只有此方法使用后，才能正常使用通讯模块
    public static void loginMessageClient(final Context context, final String account, final String password, final BasicCallback basicCallback){
        //登陆极光im，有异步过程
        RegisterOptionalUserInfo optionalUserInfo = new RegisterOptionalUserInfo();
        optionalUserInfo.setNickname(UserEntity.getNickname());
        optionalUserInfo.setAddress(UserEntity.getAvatar_path());
        Log.i("MessageUtil", "avatar:" + UserEntity.getAvatar_path());
        JMessageClient.register(account,  password, optionalUserInfo, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i == 0){
                    //注册成功
                    Log.i("MessageUtil", "register success:" + "  i:" + i + "  s:" + s);
                    //开始登陆
                    Log.i("MessageUtil", "login success:" + "  i:" + i + "  s:" + s);
                    JMessageClient.login(account, password, basicCallback);

                }else{
                    //注册失败
                    Log.i("MessageUtil", "register fail:" + "  i:" + i + "  s:" + s);
                    if(s.equals("user exist")){
                        Log.i("MessageUtil", "user exist:" + "  i:" + i + "  s:" + s);
                        JMessageClient.login(account, password, basicCallback);
                    }else{
                        Log.i("MessageUtil", "user not exist error:" + "  i:" + i + "  s:" + s);
                        Toast.makeText(context, "用户信息初始化error" + s, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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

    //获得我（发送方）消息处理对象，该对象用于保存通讯模块使用东西
    public static MessageReciverEntity getMessageSenderEntity(){
        UserInfo myUserInfo = JMessageClient.getMyInfo();
        MessageReciverEntity me = new MessageReciverEntity();
        me.setAccount(myUserInfo.getUserName());
        me.setNickname(myUserInfo.getNickname());
        return me;
    }



    //启动聊天窗口（activity）， megetMessageSenderEntity, reciver通过getMessageReciverEntity获得
    public static void startMessageChatActivity(Activity activity, MessageReciverEntity me, MessageReciverEntity reciver){
        MessageListActivity.startMessageListActivity(activity, me, reciver);
    }

    //启动展示个人信息的窗口，
    public static void startMessageEntry(Context context, String sno){
        StartMessageEntry.startMessageUserInfo(context, sno);
    }



}
