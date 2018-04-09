package com.example.mac.back.ui.contract;

import com.example.mac.back.base.BaseContract;
import com.example.mac.back.bean.UserBank;
import com.example.mac.back.bean.UserBank_Kotlin;

/**
 * Created by mac on 2018/4/7.
 */

public interface UserDetailContract {

    interface View extends BaseContract.BaseView {

        void updateUserInfo(UserBank userBank);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {


      //  com.example.mac.back.bean.UserBank_Kotlin getUserBank(String user_id);
           void getUserBank(String user_id);

    }

}
