package com.maple27.fzuyibao.presenter.util;

/**
 * Imported by Maple27 on 2017/12/13.
 */

public class VerifyCodeRequest {

    String base64;
    String trim;
    String whitelist;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getTrim() {
        return trim;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public String getWhitelist() {
        return whitelist;
    }

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist;
    }
}
