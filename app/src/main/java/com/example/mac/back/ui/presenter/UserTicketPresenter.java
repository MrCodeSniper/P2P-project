package com.example.mac.back.ui.presenter;

import android.util.Log;

import com.example.mac.back.base.RxPresenter;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.bean.UserBank;
import com.example.mac.back.bean.UserTicket;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UserDetailContract;
import com.example.mac.back.ui.contract.UserTicketContract;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 2018/4/7.
 */

public class UserTicketPresenter extends RxPresenter<UserTicketContract.View> implements UserTicketContract.Presenter<UserTicketContract.View>{

    private static final String TAG = "UserTicketPresenter";
    private ProductApi productApi;
    Subscription rxSubscription;

    @Inject
    public UserTicketPresenter(ProductApi productApi) {
        this.productApi = productApi;
    }

    @Override
    public void getUserTicket(String user_id) {
        rxSubscription = productApi.getUserTicket(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserTicket>() {
                    @Override
                    public void onNext(UserTicket data) {
                        if (data.isSuccess()) {
                            mView.updateUserInfo(data);
                        }else {
                            mView.showError();
                        }
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                        Log.e(TAG, "onError: " + e);
                    }});
        addSubscrebe(rxSubscription);
    }

    @Override
    public void getNewUserTicket(String user_id) {
        rxSubscription = productApi.getNewUserTicket(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseBean>() {
                    @Override
                    public void onNext(BaseBean data) {
                            mView.isUserAlreadyGet(data);
                    }

                    @Override
                    public void onCompleted() {
                        mView.complete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError();
                       // Log.e(TAG, "onError: " + e);
                        Logger.e("onError: " + e);
                    }});
        addSubscrebe(rxSubscription);
    }


}
