package com.example.mac.back.ui.presenter;

import android.util.Log;

import com.example.mac.back.base.RxPresenter;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.bean.UserBank;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UserContract;
import com.example.mac.back.ui.contract.UserDetailContract;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 2018/4/7.
 */

public class UserBankPresenter extends RxPresenter<UserDetailContract.View> implements UserDetailContract.Presenter<UserDetailContract.View>{

    private static final String TAG = "UserBankPresenter";
    private ProductApi productApi;
    Subscription rxSubscription;

    @Inject
    public UserBankPresenter(ProductApi productApi) {
        this.productApi = productApi;
    }

    @Override
    public void getUserBank(String user_id) {
        rxSubscription = productApi.getUserBank(user_id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBank>() {
                    @Override
                    public void onNext(UserBank data) {
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



}
