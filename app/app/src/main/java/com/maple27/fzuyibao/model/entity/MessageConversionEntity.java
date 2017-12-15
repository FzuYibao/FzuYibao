package com.maple27.fzuyibao.model.entity;

/**
 * Created by IMTB on 2017/11/16.
 */

public class MessageConversionEntity {
    //for view
    String avatar;
    String title;
    String content;
    String time;
    int newMessageNum;

    //for data
    MessageReciverEntity reciver;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getNewMessageNum() {
        return newMessageNum;
    }

    public void setNewMessageNum(int newMessageNum) {
        this.newMessageNum = newMessageNum;
    }

    public void addNewNum(){
        this.newMessageNum ++;
    }

    public MessageReciverEntity getReciver() {
        return reciver;
    }

    public void setReciver(MessageReciverEntity reciver) {
        this.reciver = reciver;
    }
}
