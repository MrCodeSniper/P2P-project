package com.example.mac.back.bean;

import java.util.List;

/**
 * Created by mac on 2018/3/1.
 */

public class Product extends BaseBean{


    /**
     * code : 200
     * data : [{"begin_date":"2018-03-05 00:00:00","category":"????","firstrate":0.065,"id":1,"is_active":true,"loan_deadline":"2018-04-05 00:00:00","loan_progress":0.44,"name":"????1?","secondrate":0.005}]
     */


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * begin_date : 2018-03-05 00:00:00
         * category : ????
         * firstrate : 0.065
         * id : 1
         * is_active : true
         * loan_deadline : 2018-04-05 00:00:00
         * loan_progress : 0.44
         * name : ????1?
         * secondrate : 0.005
         */
        private String begin_date;
        private String category;
        private double firstrate;
        private int id;
        private boolean is_active;
        private String loan_deadline;
        private double loan_progress;
        private String name;
        private double secondrate;

        public String getBegin_date() {
            return begin_date;
        }

        public void setBegin_date(String begin_date) {
            this.begin_date = begin_date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public double getFirstrate() {
            return firstrate;
        }

        public void setFirstrate(double firstrate) {
            this.firstrate = firstrate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isIs_active() {
            return is_active;
        }

        public void setIs_active(boolean is_active) {
            this.is_active = is_active;
        }

        public String getLoan_deadline() {
            return loan_deadline;
        }

        public void setLoan_deadline(String loan_deadline) {
            this.loan_deadline = loan_deadline;
        }

        public double getLoan_progress() {
            return loan_progress;
        }

        public void setLoan_progress(double loan_progress) {
            this.loan_progress = loan_progress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getSecondrate() {
            return secondrate;
        }

        public void setSecondrate(double secondrate) {
            this.secondrate = secondrate;
        }


        @Override
        public String toString() {
            return "DataBean{" +
                    "begin_date='" + begin_date + '\'' +
                    ", category='" + category + '\'' +
                    ", firstrate=" + firstrate +
                    ", id=" + id +
                    ", is_active=" + is_active +
                    ", loan_deadline='" + loan_deadline + '\'' +
                    ", loan_progress=" + loan_progress +
                    ", name='" + name + '\'' +
                    ", secondrate=" + secondrate +
                    '}';
        }
    }



}
