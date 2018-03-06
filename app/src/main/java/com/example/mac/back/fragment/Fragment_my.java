package com.example.mac.back.fragment;

/**
 * Created by mac on 2018/2/28.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.back.R;
import com.example.mac.back.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carson_Ho on 16/5/23.
 */
public class Fragment_my extends Fragment implements View.OnClickListener

{
    @BindView(R.id.login_register)
    TextView loginRegister;
    @BindView(R.id.iv_nologin)
    ImageView ivNologin;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        unbinder = ButterKnife.bind(this, view);





        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ivNologin.setOnClickListener(this);
        loginRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_nologin:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
            case R.id.login_register:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
        }
    }
}