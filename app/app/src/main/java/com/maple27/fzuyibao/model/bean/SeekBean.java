package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/25.
 */

public class SeekBean {

    /**
     * error_code : 0
     * data : {"wants":[{"wid":"33","sno":"031502216","title":"洗衣机","type":"10","time":"2017-11-16 20:28:07","description":"求购洗衣机","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"32","sno":"031502216","title":"高数书","type":"10","time":"2017-11-16 18:12:44","description":"求大一高数书","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"30","sno":"031502216","title":"电动车","type":"10","time":"2017-11-16 16:08:14","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"28","sno":"031502216","title":"电动车","type":"10","time":"2017-11-15 00:28:58","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"27","sno":"031502216","title":"电动车","type":"10","time":"2017-11-15 00:09:55","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"26","sno":"031502216","title":"电动车","type":"10","time":"2017-11-15 00:05:31","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"25","sno":"031502216","title":"电动车","type":"10","time":"2017-11-15 00:05:10","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"24","sno":"031502216","title":"电动车","type":"10","time":"2017-11-15 00:04:41","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"23","sno":"031502216","title":"电动车","type":"10","time":"2017-11-14 23:55:49","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]},{"wid":"22","sno":"031502216","title":"电动车","type":"10","time":"2017-11-14 23:55:04","description":"求购一辆电动车","price":"15","nickname":"夜色难眠","avatar_path":"public/avatars/default.jpg","tag":["书籍"],"photo":[]}]}
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
        private List<WantsBean> wants;

        public List<WantsBean> getWants() {
            return wants;
        }

        public void setWants(List<WantsBean> wants) {
            this.wants = wants;
        }

        public static class WantsBean {
            /**
             * wid : 33
             * sno : 031502216
             * title : 洗衣机
             * type : 10
             * time : 2017-11-16 20:28:07
             * description : 求购洗衣机
             * price : 15
             * nickname : 夜色难眠
             * avatar_path : public/avatars/default.jpg
             * tag : ["书籍"]
             * photo : []
             */

            private String wid;
            private String sno;
            private String title;
            private String type;
            private String time;
            private String description;
            private String price;
            private String nickname;
            private String avatar_path;
            private List<String> tag;
            private List<?> photo;

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
            }

            public List<?> getPhoto() {
                return photo;
            }

            public void setPhoto(List<?> photo) {
                this.photo = photo;
            }
        }
    }
}
