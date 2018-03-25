package com.example.mac.back.bean;

import java.util.List;

/**
 * Created by mac on 2018/3/24.
 */

public class Bill extends BaseBean {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean {
        /**
         * buy_time : 2018-03-06 00:00:00
         * phone : 15168264355
         * id : 1
         * buy_amount : 4.4
         */

        private String buy_time;
        private String phone;
        private int id;
        private double buy_amount;

        public String getBuy_time() {
            return buy_time;
        }

        public void setBuy_time(String buy_time) {
            this.buy_time = buy_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getBuy_amount() {
            return buy_amount;
        }

        public void setBuy_amount(double buy_amount) {
            this.buy_amount = buy_amount;
        }
    }
}
