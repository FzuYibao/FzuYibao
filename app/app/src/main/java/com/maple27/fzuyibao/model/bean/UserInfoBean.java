package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by IMTB on 2017/11/19.
 */

public class UserInfoBean {
    /**
     * error_code : 0
     * data : {"info":[{"uid":"7","sno":"031502212","user_name":"符天愉","nickname":"hish","phone":"13015767857","major":"计算机科学与技术","grade":"2015","avatar_path":"public/avatars/0315022121510816961.jpg"}]}
     */

    private int error_code;
    private DataBean data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<InfoBean> info;

        public List<InfoBean> getInfo() {
            return info;
        }

        public void setInfo(List<InfoBean> info) {
            this.info = info;
        }

        public static class InfoBean {
            /**
             * uid : 7
             * sno : 031502212
             * user_name : 符天愉
             * nickname : hish
             * phone : 13015767857
             * major : 计算机科学与技术
             * grade : 2015
             * avatar_path : public/avatars/0315022121510816961.jpg
             */

            private String uid;
            private String sno;
            private String user_name;
            private String nickname;
            private String phone;
            private String major;
            private String grade;
            private String avatar_path;

            public String getUid() {
                return uid;
            }

            public void setUid(String uid) {
                this.uid = uid;
            }

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String grade) {
                this.grade = grade;
            }

            public String getAvatar_path() {
                return avatar_path;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }
        }
    }
}
