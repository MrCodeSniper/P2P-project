package com.example.mac.back.bean;

/**
 * Created by mac on 2018/3/18.
 */

public class ApkInfo extends BaseBean{


    /**
     * data : {"apk_url":"192.168.1.101:8888/P2PInvest/apk_new.apk","id":1,"update_message":"测试更新","versionCode":2,"versionName":1.1}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * apk_url : 192.168.1.101:8888/P2PInvest/apk_new.apk
         * id : 1
         * update_message : 测试更新
         * versionCode : 2
         * versionName : 1.1
         */

        private String apk_url;
        private int id;
        private String update_message;
        private int versionCode;
        private double versionName;

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUpdate_message() {
            return update_message;
        }

        public void setUpdate_message(String update_message) {
            this.update_message = update_message;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public double getVersionName() {
            return versionName;
        }

        public void setVersionName(double versionName) {
            this.versionName = versionName;
        }
    }
}
