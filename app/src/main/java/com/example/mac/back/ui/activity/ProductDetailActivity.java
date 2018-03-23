package com.example.mac.back.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.Product;
import com.example.mac.back.bean.ProductDetail;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.ProductDetailContract;
import com.example.mac.back.ui.presenter.ProductPresenter;
import com.example.mac.back.utils.DateUtils;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

/**
 * Created by mac on 2018/3/23.
 */

public class ProductDetailActivity extends BaseActivity implements ProductDetailContract.View {
    @Inject
    ProductPresenter mPresenter;
    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_totalrate)
    TextView tvTotalrate;
    @BindView(R.id.tv_rate_consist)
    TextView tvRateConsist;
    @BindView(R.id.rl_unlogined)
    RelativeLayout rlUnlogined;
    @BindView(R.id.tv_min_amount)
    TextView tvMinAmount;
    @BindView(R.id.tv_resisitday)
    TextView tvResisitday;
    @BindView(R.id.tv_totalamount)
    TextView tvTotalamount;
    @BindView(R.id.pb_progress)
    RoundCornerProgressBar pbProgress;
    @BindView(R.id.tv_rentalready)
    TextView tvRentalready;
    @BindView(R.id.tv_restamount)
    TextView tvRestamount;
    @BindView(R.id.tv_beginday)
    TextView tvBeginday;
    @BindView(R.id.tv_endday)
    TextView tvEndday;
    private String product_id;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_productdetail);
    }

    @Override
    protected void initData() {
        product_id = getIntent().getStringExtra("product_id");
        mPresenter = new ProductPresenter(ProductApi.getInstance(new OkHttpClient()));
        mPresenter.attachView(this);
        mPresenter.getProductById(product_id);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void showProducts(Product data) {

    }

    @Override
    public void PullDownRefresh(Product data) {

    }

    @Override
    public void refreshDetail(ProductDetail data) {

        tvTitles.setText(data.getData().getName());

            tvBeginday.setText(data.getData().getBegin_date());
            tvEndday.setText(data.getData().getLoan_deadline());
            Double totalrate=(data.getData().getFirstrate()+data.getData().getSecondrate())*100;
            Double firstrate=change(data.getData().getFirstrate()*100);
            Double secondrate=change(data.getData().getSecondrate()*100);
            Double rest=data.getData().getLoan_amount()*(1-data.getData().getLoan_progress());

            tvTotalrate.setText(change(totalrate)+"%");
            tvTotalamount.setText(data.getData().getLoan_amount()+"万总额");
            tvMinAmount.setText(data.getData().getMin_loan()*10000+"元起投");
            tvRateConsist.setText(change(totalrate)+"%"+" "+"("+firstrate+"%"+"+活动加息"+change(secondrate)+"%)");
            tvRestamount.setText("剩余可投"+change(rest)+"万元");
            tvRentalready.setText("已出借"+change(data.getData().getLoan_progress()*100)+"%");
        if(data.getData().getBegin_date()!=null&&data.getData().getLoan_deadline()!=null){
            Date date2= DateUtils.strToDateLong(data.getData().getBegin_date());
            Date date1=DateUtils.strToDateLong(data.getData().getLoan_deadline());
            long day = (date1.getTime()-date2.getTime())/(24*60*60*1000)>0 ? (date1.getTime()-date2.getTime())/(24*60*60*1000):
                    (date2.getTime()-date1.getTime())/(24*60*60*1000);
            tvResisitday.setText(day+"天期限");
            float progress= (float) (data.getData().getLoan_progress()*100);
            Logger.e(progress+"xx");
            pbProgress.setProgress(progress);
            pbProgress.setSecondaryProgress(progress);
        }








    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



    @OnClick(R.id.iv_quit)
    public void quit(){
        finish();
    }


    private Double change(Double doubles){
        BigDecimal b   =   new   BigDecimal(doubles);
        return  b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}
