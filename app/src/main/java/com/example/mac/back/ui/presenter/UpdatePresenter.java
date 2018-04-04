package com.example.mac.back.ui.presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.base.RxPresenter;
import com.example.mac.back.bean.ApkInfo;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UpdateContract;
import com.example.mac.back.utils.ApkUtils;
import com.example.mac.back.utils.ApkVersionCodeUtils;
import com.example.mac.back.utils.PatchUtils;
import com.example.mac.back.utils.download.DownloadUtils;
import com.example.mac.back.utils.download.JsDownloadListener;
import com.orhanobut.logger.Logger;

import java.io.File;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
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



    private static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String mNewApk = PATH + "/new.apk";
    private static final String TAG = "UpdatePresenter";
    private ProductApi productApi;
    Subscription rxSubscription;
    private Context mcontext;


    // 成功
    private static final int WHAT_SUCCESS = 1;

    // 本地安装的微博MD5不正确
    private static final int WHAT_FAIL_OLD_MD5 = -1;

    // 新生成的微博MD5不正确
    private static final int WHAT_FAIL_GEN_MD5 = -2;

    // 合成失败
    private static final int WHAT_FAIL_PATCH = -3;

    // 获取源文件失败
    private static final int WHAT_FAIL_GET_SOURCE = -4;

    // 未知错误
    private static final int WHAT_FAIL_UNKNOWN = -5;

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
                                mView.showUpdateDialog(data.getData().getUpdate_message());
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
        final  JsDownloadListener listener=new JsDownloadListener() {
            @Override
            public void onStartDownload() {
                Logger.e("开始下载");
                mView.showDownloadDialog();
            }

            @Override
            public void onProgress(int progress) {
                mView.changeProgress(progress);
            }

            @Override
            public void onFinishDownload() {
                Logger.e("下载完成");
                mView.dowloadFinish();
                //合并  开始安装
            }

            @Override
            public void onFail(String errorInfo) {
                Logger.e("下载失败"+errorInfo);
                mView.dowloadError();
            }
        };


        DownloadUtils downloadUtils = new DownloadUtils(AppConfig.BASEURL, listener,mcontext);

        Logger.e(Environment.getExternalStorageDirectory().getAbsolutePath()+"/old_to_new.patch");

        downloadUtils.download(AppConfig.PATCH_FILE, "patchpackage", new Subscriber() {
            @Override
            public void onCompleted() {
                listener.onFinishDownload();
            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.toString());
            }

            @Override
            public void onNext(Object o) {

            }
        });


    }

    @Override
    public void old_to_newAPK() {


        File patchFile = new File(AppConfig.PATCHPATH);
        //子线程

        if (!ApkUtils.isInstalled(mcontext, AppConfig.PACKNAME)) {
            Toast.makeText(mcontext, mcontext.getString(R.string.demo_info1), Toast.LENGTH_LONG).show();
        } else if (!patchFile.exists()) {
            Toast.makeText(mcontext, mcontext.getString(R.string.demo_info2), Toast.LENGTH_LONG).show();
        } else {
            new PatchApkTask().execute();
        }


    }

    private class PatchApkTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showMergeDialog();
        }

        @Override
        protected Integer doInBackground(String... params) {
            PackageInfo packageInfo = ApkUtils.getInstalledApkPackageInfo(mcontext, AppConfig.PACKNAME);
            if (packageInfo != null) {
                String oldApkSource = ApkUtils.getSourceApkPath(mcontext, AppConfig.PACKNAME);
                if (!TextUtils.isEmpty(oldApkSource)) {
                        int patchResult = PatchUtils.patch(oldApkSource, AppConfig.NEWAPKPATH, AppConfig.PATCHPATH);
                        if (patchResult == 0) {
                            return  WHAT_SUCCESS;
                        } else {
                            return WHAT_FAIL_PATCH;
                        }
                } else {
                    return WHAT_FAIL_GET_SOURCE;
                }
            } else {
                return WHAT_FAIL_UNKNOWN;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);


            switch (result) {
                case WHAT_SUCCESS: {

//                    String text = "新apk已合成成功：" + Constants.NEW_APK_PATH;
//                    showToast(text);
                    mView.mergeSuccess();
                   // ApkUtils.installApk(MainActivity.this, Constants.NEW_APK_PATH);
                    break;
                }
                case WHAT_FAIL_OLD_MD5: {
                    mView.mergeFailed();
                    break;
                }
                case WHAT_FAIL_GEN_MD5: {
                    mView.mergeFailed();
                    break;
                }
                case WHAT_FAIL_PATCH: {
                    mView.mergeFailed();
                    break;
                }
                case WHAT_FAIL_GET_SOURCE: {
                    mView.mergeFailed();
                    break;
                }
            }
        }
    }










}
