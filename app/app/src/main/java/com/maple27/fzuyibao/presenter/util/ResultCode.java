package com.maple27.fzuyibao.presenter.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maple27 on 2017/12/13.
 */

public class ResultCode {
    public static final int NET_ERROR=-1;
    public static final int LOGIN_ERROR=0;
    public static final int LOGIN_SUCCESS=1;
    public static final int LOGIN_PWD_ERROR=2;
    public static final int LOGIN_VERIFY_ERROR=3;

    public static Map<Integer,String> map=null;

    {

    }

    public static String get(int code){
        if (map == null){
            synchronized (ResultCode.class){
                if (map == null) {
                    map = new HashMap<Integer,String>();
                    map.put(NET_ERROR, "网络异常");
                    map.put(LOGIN_ERROR,"登录失败");
                    map.put(LOGIN_SUCCESS, "登录成功");
                    map.put(LOGIN_PWD_ERROR,"账号或者密码错误");
                    map.put(LOGIN_VERIFY_ERROR,"验证码错误");
                }
            }
        }
        return map.get(code);
    }
}
