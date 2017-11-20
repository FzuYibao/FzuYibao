package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/19.
 */

public class CommodityBean {


    /**
     * error_code : 0
     * data : {"goods":[{"gid":"48","sno":"031502210","goods_name":"星火英语考研单词本","type":"11000","status":"1","time":"2017-11-19 23:15:20","description":"描述","price":"20","nickname":"user031502210","avatar_path":"public/avatars/0315022101510815247.jpg","tag":["考研","英语"],"photo":["public/avatars/default.jpg"]},{"gid":"47","sno":"031502424","goods_name":"lsy","type":"12340","status":"1","time":"2017-11-18 23:49:58","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"45","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-18 19:25:51","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"44","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-18 19:24:35","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"43","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:58:29","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/goods/03150242415108551091.png","public/goods/03150242415108551092.png"]},{"gid":"42","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:57:58","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"41","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:50:08","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"40","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:49:54","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"39","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:03:23","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":["0"],"photo":["public/avatars/default.jpg"]},{"gid":"38","sno":"031502424","goods_name":"电脑","type":"12340","status":"1","time":"2017-11-17 01:02:11","description":"刚买一年，九成新","price":"2000","nickname":"user031502424","avatar_path":"public/avatars/default.jpg","tag":[],"photo":["public/avatars/default.jpg"]}]}
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
        private List<GoodsBean> goods;

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
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
