package com.maple27.fzuyibao.model.bean;

/**
 * Created by Maple27 on 2017/11/16.
 */

public class GetAvatarBean {


    /**
     * error_code : 0
     * data : {"avatar":{"avatar_path":"public/avatars/0315022101510815110.jpg"}}
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
         * avatar : {"avatar_path":"public/avatars/0315022101510815110.jpg"}
         */

        private AvatarBean avatar;

        public AvatarBean getAvatar() {
            return avatar;
        }

        public void setAvatar(AvatarBean avatar) {
            this.avatar = avatar;
        }

        public static class AvatarBean {
            /**
             * avatar_path : public/avatars/0315022101510815110.jpg
             */

            private String avatar_path;

            public String getAvatar_path() {
                return avatar_path;
            }

            public void setAvatar_path(String avatar_path) {
                this.avatar_path = avatar_path;
            }
        }
    }
}
