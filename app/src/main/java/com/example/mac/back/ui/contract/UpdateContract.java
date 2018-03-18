package com.example.mac.back.ui.contract;

import com.example.mac.back.base.BaseContract;
import com.example.mac.back.bean.Product;

/**
 * Created by mac on 2018/3/18.
 */

public interface UpdateContract {


    interface View extends BaseContract.BaseView {//规定需要显示的功能

        void showUpdateDialog();

        void dismissUpdateDialog();

        void showDownloadDialog();

        void retrunToInstall();


    }

    interface Presenter<T> extends BaseContract.BasePresenter<T> {//规定需要出现的业务


        void getNewestAPKVersion();

        void getNewestPatch();

        void old_to_newAPK();//合并成新的apk





    }

}
