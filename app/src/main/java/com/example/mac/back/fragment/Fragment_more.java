package com.example.mac.back.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mac.back.R;
import com.example.mac.back.activity.LoginActivity;
import com.example.mac.back.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by mac on 2018/3/4.
 */

public class Fragment_more extends BaseFragment {

    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.ll_action_center)
    LinearLayout llActionCenter;
    @BindView(R.id.ll_helpcenter)
    LinearLayout llHelpcenter;
    @BindView(R.id.ll_pharse_explain)
    LinearLayout llPharseExplain;
    @BindView(R.id.ll_media)
    LinearLayout llMedia;
    @BindView(R.id.ll_aboutus)
    LinearLayout llAboutus;
    @BindView(R.id.btn_login)
    Button btnLogin;
    Unbinder unbinder;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.login_register,R.id.btn_login})   //给 button1 设置一个点击事件
    public void login(){
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

}
