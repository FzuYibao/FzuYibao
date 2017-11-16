package com.maple27.fzuyibao.model.bean;

/**
 * Created by Maple27 on 2017/11/3.
 */

public class LoginBean {

    /**
     * error_code : 0
     * data : {"user":{"sno":"031502212","user_name":"符天愉","nickname":"hish","phone":"13015767857","major":"计算机科学与技术","grade":"2015","avatar_path":""}}
     */

    private int error_code;

    private String message;
    private DataBean data;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * user : {"sno":"031502212","user_name":"符天愉","nickname":"hish","phone":"13015767857","major":"计算机科学与技术","grade":"2015","avatar_path":""}
         */

        private UserBean user;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * sno : 031502212
             * user_name : 符天愉
             * nickname : hish
             * phone : 13015767857
             * major : 计算机科学与技术
             * grade : 2015
             * avatar_path :
             */

            private String sno;
            private String user_name;
            private String nickname;
            private String phone;
            private String major;
            private String grade;
            private String avatar_path;
            private String jwt;

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

            public String getJwt() {
                return jwt;
            }

            public void setJwt(String jwt) {
                this.jwt = jwt;
            }

        }
    }
}
