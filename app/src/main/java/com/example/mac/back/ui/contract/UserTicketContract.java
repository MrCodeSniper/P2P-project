package com.example.mac.back.ui.contract;

import com.example.mac.back.base.BaseContract;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.bean.UserBank;
import com.example.mac.back.bean.UserTicket;

/**
 * Created by mac on 2018/4/7.
 */

public interface UserTicketContract {

    interface View extends BaseContract.BaseView {

        void updateUserInfo(UserTicket userTicket);

        void isUserAlreadyGet(BaseBean baseBean);

    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {


           void getUserTicket(String user_id);

           void getNewUserTicket(String user_id);

    }

}
