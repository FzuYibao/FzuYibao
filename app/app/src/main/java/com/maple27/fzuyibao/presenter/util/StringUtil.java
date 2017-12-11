package com.maple27.fzuyibao.presenter.util;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class StringUtil {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }else {
            if (str.length() >= 1) {
                return false;
            }else {
                return true;
            }
        }
    }
}
