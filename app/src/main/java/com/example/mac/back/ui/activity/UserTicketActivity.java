package com.example.mac.back.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.bean.UserTicket;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.adapter.UserTicketAdapter;
import com.example.mac.back.ui.contract.UserTicketContract;
import com.example.mac.back.ui.presenter.ProductPresenter;
import com.example.mac.back.ui.presenter.UserPresenter;
import com.example.mac.back.ui.presenter.UserTicketPresenter;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/4/19.
 */

public class UserTicketActivity extends BaseActivity implements UserTicketContract.View{


    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Inject
    UserTicketPresenter mPresenter;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_userticket);
        ButterKnife.bind(this);
        tvTitles.setText("我的优惠");
        ivQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

        String id=SharedPreferencesUtils.getString("cat","id","");

        if(id!=null&& !TextUtils.equals(id,"")){
            Logger.e("xxx");
            mPresenter = new UserTicketPresenter(ProductApi.getInstance(new OkHttpClient()));
            mPresenter.attachView(this);
            mPresenter.getUserTicket(id);
        }


    }

    @Override
    protected void initListener() {

    }

    @Override
    public void updateUserInfo(UserTicket userTicket) {
//        Logger.e(userTicket.getData().get(0).getPhone());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        UserTicketAdapter adapter = new UserTicketAdapter(userTicket);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void isUserAlreadyGet(BaseBean baseBean) {
        if(baseBean.getCode()==400&&TextUtils.equals(baseBean.getMsg(),"用户已经领取过优惠")){
            //领取过优惠

        }else {
            //没领取
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }
}
