package com.maple27.fzuyibao.model.entity;

/**
 * Created by IMTB on 2017/11/17.
 */

public class MessageChatViewEntity {
    public final static int LEFT = 0;
    public final static int RIGHT = 1;

    //for view
    int direction;
    String name;
    String content;

    //for data


    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
