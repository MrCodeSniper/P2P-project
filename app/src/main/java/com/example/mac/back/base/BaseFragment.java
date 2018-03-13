package com.example.mac.back.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.back.config.AppConfig;
import com.example.mac.back.bean.MessageEvent;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by mac on 2018/3/6.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity;

    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    private Unbinder binder;

//    //添加fragment
//    protected void addFragment(BaseFragment fragment) {
//        if (null != fragment) {
//            getHoldingActivity().addFragment(fragment);
//        }
//    }
//
//    //移除fragment
//    protected void removeFragment() {
//        getHoldingActivity().removeFragment();
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);

//        if(rootView==null){
//            rootView = inflater.inflate(getLayoutId(), container, false);
//        }
//        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
//        ViewGroup parent = (ViewGroup) rootView.getParent();
//        if (parent != null) {
//            parent.removeView(rootView);
//        }
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        binder=ButterKnife.bind(this, rootView);
        initView(rootView, savedInstanceState);
        return rootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        binder.unbind();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {
        Logger.e("fragment-base");
        //处理逻辑
        if (event.getMessage()== AppConfig.TAG_LOGINED) {
            initLoginedView();
        }else if (event.getMessage()==AppConfig.TAG_UNLOGINED){
            initUnLoginedView();
        }

    }

    protected abstract void initUnLoginedView();

    protected abstract void initLoginedView();

}