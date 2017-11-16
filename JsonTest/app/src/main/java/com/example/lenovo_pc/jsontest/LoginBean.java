package com.example.lenovo_pc.jsontest;

/**
 * Created by lenovo-pc on 2017/11/16.
 */

public class LoginBean {
    private int error_code;
    private databean data;

    public databean getData() {
        return data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setData(databean data) {
        this.data = data;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public class databean{
        private userbean usr;

        public void setUsr(userbean usr) {
            this.usr = usr;
        }

        public userbean getUsr() {
            return usr;
        }


        public class userbean{
            private String sno;
            private String user_name;
            private String nickname;
            private String phone;
            private String major;
            private String grade;
            private String avatar_path;
            private String jwt;

            public String getUser_name() {
                return user_name;
            }

            public String getSno() {
                return sno;
            }

            public String getPhone() {
                return phone;
            }

            public String getNickname() {
                return nickname;
            }

            public String getMajor() {
                return major;
            }

            public String getJwt() {
                return jwt;
            }

            public String getGrade() {
                return grade;
            }

            public String getAvatar_path() {
                return avatar_path;
            }


            public void setGrade(String grade) {
                this.grade = grade;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setJwt(String jwt) {
                this.jwt = jwt;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public void setMajor(String major) {
                this.major = major;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }

        }


    }





}
