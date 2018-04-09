package com.example.mac.back.bean;

/**
 * Created by mac on 2018/4/7.
 */

public class UserBank extends BaseBean{


    /**
     * data : {"accounts":4.401832,"cat_food":10,"id":1,"recharge":18.32,"relate_userid":null,"reward_coin":20}
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
         * accounts : 4.401832
         * cat_food : 10
         * id : 1
         * recharge : 18.32
         * relate_userid : null
         * reward_coin : 20
         */

        private double accounts;
        private int cat_food;
        private int id;
        private double recharge;
        private Object relate_userid;
        private int reward_coin;

        public double getAccounts() {
            return accounts;
        }

        public void setAccounts(double accounts) {
            this.accounts = accounts;
        }

        public int getCat_food() {
            return cat_food;
        }

        public void setCat_food(int cat_food) {
            this.cat_food = cat_food;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getRecharge() {
            return recharge;
        }

        public void setRecharge(double recharge) {
            this.recharge = recharge;
        }

        public Object getRelate_userid() {
            return relate_userid;
        }

        public void setRelate_userid(Object relate_userid) {
            this.relate_userid = relate_userid;
        }

        public int getReward_coin() {
            return reward_coin;
        }

        public void setReward_coin(int reward_coin) {
            this.reward_coin = reward_coin;
        }
    }
}
