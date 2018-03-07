package com.example.mac.back.bean;

/**
 * Created by mac on 2018/3/7.
 */

public class User extends BaseBean {


    /**
     * data : {"credit":true,"id":27,"imageurl":"http://192.168.191.1:8080/P2PInvest/images/tx.png","name":"NoName","password":"","phone":"44444444444"}
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
         * credit : true
         * id : 27
         * imageurl : http://192.168.191.1:8080/P2PInvest/images/tx.png
         * name : NoName
         * password :
         * phone : 44444444444
         */

        private boolean credit;
        private int id;
        private String imageurl;
        private String name;
        private String password;
        private String phone;

        public boolean isCredit() {
            return credit;
        }

        public void setCredit(boolean credit) {
            this.credit = credit;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
