package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class LibraryBean {


    /**
     * error_code : 0
     * data : {"books":[{"bid":"19","books_name":"白夜行","sno":"031502212","rent_times":"0","status":"1","type":"21000","deadline":"--:--","rent_sno":"","wait_sno":"","wait_status":"1","nickname":"hish","avatar_path":"public/avatars/default.jpg","photo":["public/goods/default.jpg"]},{"bid":"20","books_name":"白夜行","sno":"031502212","rent_times":"0","status":"1","type":"21000","deadline":"--:--","rent_sno":"","wait_sno":"","wait_status":"1","nickname":"hish","avatar_path":"public/avatars/default.jpg","photo":["public/goods/default.jpg"]}]}
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
        private List<BooksBean> books;

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class BooksBean {
            /**
             * bid : 19
             * books_name : 白夜行
             * sno : 031502212
             * rent_times : 0
             * status : 1
             * type : 21000
             * deadline : --:--
             * rent_sno :
             * wait_sno :
             * wait_status : 1
             * nickname : hish
             * avatar_path : public/avatars/default.jpg
             * photo : ["public/goods/default.jpg"]
             */

            private String bid;
            private String books_name;
            private String sno;
            private String rent_times;
            private String status;
            private String type;
            private String deadline;
            private String rent_sno;
            private String wait_sno;
            private String wait_status;
            private String nickname;
            private String avatar_path;
            private List<String> photo;

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getBooks_name() {
                return books_name;
            }

            public void setBooks_name(String books_name) {
                this.books_name = books_name;
            }

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public String getRent_times() {
                return rent_times;
            }

            public void setRent_times(String rent_times) {
                this.rent_times = rent_times;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDeadline() {
                return deadline;
            }

            public void setDeadline(String deadline) {
                this.deadline = deadline;
            }

            public String getRent_sno() {
                return rent_sno;
            }

            public void setRent_sno(String rent_sno) {
                this.rent_sno = rent_sno;
            }

            public String getWait_sno() {
                return wait_sno;
            }

            public void setWait_sno(String wait_sno) {
                this.wait_sno = wait_sno;
            }

            public String getWait_status() {
                return wait_status;
            }

            public void setWait_status(String wait_status) {
                this.wait_status = wait_status;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar_path() {
                return avatar_path;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }

            public List<String> getPhoto() {
                return photo;
            }

            public void setPhoto(List<String> photo) {
                this.photo = photo;
            }
        }
    }
}
