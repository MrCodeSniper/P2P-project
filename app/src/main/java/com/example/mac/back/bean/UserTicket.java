package com.example.mac.back.bean;

import java.util.List;

/**
 * Created by mac on 2018/4/19.
 */

public class UserTicket extends BaseBean{


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * end_date : 2018-05-24 00:00:00
         * offset_account : null
         * begin_date : 2018-04-19 00:00:00
         * interest_rate : 0.002
         * id : 29
         * ticket_id : 1
         * type : 0
         */

        private String end_date;
        private double offset_account;
        private String begin_date;
        private double interest_rate;
        private int id;
        private int ticket_id;
        private int type;

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public double getOffset_account() {
            return offset_account;
        }

        public void setOffset_account(double offset_account) {
            this.offset_account = offset_account;
        }

        public String getBegin_date() {
            return begin_date;
        }

        public void setBegin_date(String begin_date) {
            this.begin_date = begin_date;
        }

        public double getInterest_rate() {
            return interest_rate;
        }

        public void setInterest_rate(double interest_rate) {
            this.interest_rate = interest_rate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTicket_id() {
            return ticket_id;
        }

        public void setTicket_id(int ticket_id) {
            this.ticket_id = ticket_id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
