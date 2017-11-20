package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/19.
 */

public class PostBean {


    /**
     * error_code : 0
     * data : {"goods":{"gid":"48","sno":"031502210","goods_name":"星火英语考研单词本","type":"11000","status":"1","time":"2017-11-19 23:15:20","description":"描述","price":"20","nickname":"user031502210","avatar_path":"public/avatars/0315022101510815247.jpg","is_collection":"false","tag":["考研","英语"],"photo":["public/avatars/default.jpg"]}}
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
        /**
         * goods : {"gid":"48","sno":"031502210","goods_name":"星火英语考研单词本","type":"11000","status":"1","time":"2017-11-19 23:15:20","description":"描述","price":"20","nickname":"user031502210","avatar_path":"public/avatars/0315022101510815247.jpg","is_collection":"false","tag":["考研","英语"],"photo":["public/avatars/default.jpg"]}
         */

        private GoodsBean goods;

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class GoodsBean {
            /**
             * gid : 48
             * sno : 031502210
             * goods_name : 星火英语考研单词本
             * type : 11000
             * status : 1
             * time : 2017-11-19 23:15:20
             * description : 描述
             * price : 20
             * nickname : user031502210
             * avatar_path : public/avatars/0315022101510815247.jpg
             * is_collection : false
             * tag : ["考研","英语"]
             * photo : ["public/avatars/default.jpg"]
             */

            private String gid;
            private String sno;
            private String goods_name;
            private String type;
            private String status;
            private String time;
            private String description;
            private String price;
            private String nickname;
            private String avatar_path;
            private String is_collection;
            private List<String> tag;
            private List<String> photo;

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
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

            public String getIs_collection() {
                return is_collection;
            }

            public void setIs_collection(String is_collection) {
                this.is_collection = is_collection;
            }

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
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
