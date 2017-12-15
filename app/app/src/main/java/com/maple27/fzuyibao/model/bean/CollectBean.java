package com.maple27.fzuyibao.model.bean;

/**
 * Created by Maple27 on 2017/12/15.
 */

public class CollectBean {


    /**
     * error_code : 0
     * data : {"goods":{"sno":"031502424","gid":"16"}}
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
         * goods : {"sno":"031502424","gid":"16"}
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
             * sno : 031502424
             * gid : 16
             */

            private String sno;
            private String gid;

            public String getSno() {
                return sno;
            }

            public void setSno(String sno) {
                this.sno = sno;
            }

            public String getGid() {
                return gid;
            }

            public void setGid(String gid) {
                this.gid = gid;
            }
        }
    }
}
