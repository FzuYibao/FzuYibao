package com.maple27.fzuyibao.model.bean;

import java.util.List;

/**
 * Created by Maple27 on 2017/11/19.
 */

public class CommodityBean {


    /**
     * error_code : 0
     * data : {"goods":[{"gid":"98","sno":"031502332","goods_name":"大学之路","type":"13000","status":"1","time":"2017-11-24 01:29:51","description":"吴军的作品","price":"15","nickname":"user031502332","avatar_path":"public/avatars/0315023321511456498.jpg","is_collection":"false","tag":["tag"],"photo":["public/goods/03150233215114581911.jpg"]},{"gid":"94","sno":"031502424","goods_name":"白夜行","type":"13000","status":"1","time":"2017-11-22 00:03:07","description":"book","price":"20","nickname":"哈哈哈哈","avatar_path":"public/avatars/0315024241511457286.jpg","is_collection":"false","tag":[],"photo":["public/goods/default.jpg"]}]}
     */

    private int error_code;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
             * gid : 98
             * sno : 031502332
             * goods_name : 大学之路
             * type : 13000
             * status : 1
             * time : 2017-11-24 01:29:51
             * description : 吴军的作品
             * price : 15
             * nickname : user031502332
             * avatar_path : public/avatars/0315023321511456498.jpg
             * is_collection : false
             * tag : ["tag"]
             * photo : ["public/goods/03150233215114581911.jpg"]
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
