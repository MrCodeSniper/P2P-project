package com.example.mac.back.ui.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.mac.back.base.RxPresenter;
import com.example.mac.back.bean.ApkInfo;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UpdateContract;
import com.example.mac.back.ui.contract.UserContract;
import com.example.mac.back.utils.ApkVersionCodeUtils;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * T 为view 以便绑定视图时拿到view
 *
 *
 * Created by mac on 2018/3/18.
 */

public class UpdatePresenter extends RxPresenter<UpdateContract.View>  implements UpdateContract.Presenter<UpdateContract.View> {

    private static final String TAG = "UpdatePresenter";
    private ProductApi productApi;
    Subscription rxSubscription;
    private Context mcontext;

    @Inject
    public UpdatePresenter(ProductApi productApi,Context context) {
        this.productApi = productApi;
        this.mcontext=context;
    }

    @Override
    public void getNewestAPKVersion() {

        rxSubscription = productApi.getNewestAPKVersion().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApkInfo>() {
                    @Override
                    public void onNext(ApkInfo data) {
                        if (data.isSuccess()) {
                            String newest_version=data.getData().getVersionName()+"";
                            Logger.e(data.getData().getApk_url()+"xxx");
                            if(TextUtils.equals(newest_version, ApkVersionCodeUtils.getVerName(mcontext))){
                                Logger.e("已经是最新版本");
                            }else {
                                Logger.e("需要更新");
                                mView.showUpdateDialog();
                            }
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
    public void getNewestPatch() {

    }

    @Override
    public void old_to_newAPK() {

    }


}
