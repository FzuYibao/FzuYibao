package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class LibraryBean {


    /**
     * error_code : 0
     * data : {"books":[{"bid":"1","books_name":"白夜行","description":"村上春树","sno":"031502424","rent_times":"1","status":"1","type":"21000","deadline":"--:--","nickname":"哈哈哈哈","avatar_path":"public/avatars/0315024241511457286.jpg","photo":["public/books/03150242415115344031.PNG"]},{"bid":"2","books_name":"三体","description":"暂无图书描述！","sno":"031502424","rent_times":"0","status":"1","type":"23000","deadline":"--:--","nickname":"哈哈哈哈","avatar_path":"public/avatars/0315024241511457286.jpg","photo":["public/books/03150242415115365421.png"]},{"bid":"3","books_name":"白夜行","description":"村上春树","sno":"031502424","rent_times":"0","status":"1","type":"21000","deadline":"--:--","nickname":"哈哈哈哈","avatar_path":"public/avatars/0315024241511457286.jpg","photo":["public/goods/default.jpg"]}]}
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
             * bid : 1
             * books_name : 白夜行
             * description : 村上春树
             * sno : 031502424
             * rent_times : 1
             * status : 1
             * type : 21000
             * deadline : --:--
             * nickname : 哈哈哈哈
             * avatar_path : public/avatars/0315024241511457286.jpg
             * photo : ["public/books/03150242415115344031.PNG"]
             */

            private String bid;
            private String books_name;
            private String description;
            private String sno;
            private String rent_times;
            private String status;
            private String type;
            private String deadline;
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

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
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
