package com.maple27.fzuyibao.model.entity;

import java.io.Serializable;

/**
 * Created by IMTB on 2017/11/16.
 */

public class MessageReciverEntity implements Serializable {

    String account;
    String nickname;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
