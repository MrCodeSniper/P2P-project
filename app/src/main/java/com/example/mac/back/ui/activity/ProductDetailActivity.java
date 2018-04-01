package com.example.mac.back.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.Bill;
import com.example.mac.back.bean.Product;
import com.example.mac.back.bean.ProductDetail;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.component.AppComponent;
import com.example.mac.back.ui.component.DaggerProductComponent;
import com.example.mac.back.ui.contract.ProductDetailContract;
import com.example.mac.back.ui.presenter.ProductPresenter;
import com.example.mac.back.utils.DateUtils;
import com.example.mac.back.view.AutoVerticalScrollTextView;
import com.example.mac.back.view.AutoVerticalScrollTextViewSecond;
import com.orhanobut.logger.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @BindView(R.id.tv_auto_roll)
    AutoVerticalScrollTextViewSecond tvAutoRoll;

    private String product_id;
    private int number = 0;
    private boolean isRunning = true;
    private List<String> strings=new ArrayList<>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 199) {
                tvAutoRoll.next();
                number++;
                tvAutoRoll.setText(strings.get(number % strings.size()));
            }
        }
    };

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_productdetail);

    }

    @Override
    protected void initData() {
        product_id = getIntent().getStringExtra("product_id");
       // mPresenter = new ProductPresenter(ProductApi.getInstance(new OkHttpClient()));
        mPresenter.attachView(this);
        mPresenter.getProductById(product_id);
        mPresenter.getBillByProductId(product_id);
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerProductComponent.builder()
                .appComponent(appComponent)//dependecy
                .build()
                .inject(this);
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
        Double totalrate = (data.getData().getFirstrate() + data.getData().getSecondrate()) * 100;
        Double firstrate = change(data.getData().getFirstrate() * 100);
        Double secondrate = change(data.getData().getSecondrate() * 100);
        Double rest = data.getData().getLoan_amount() * (1 - data.getData().getLoan_progress());

        tvTotalrate.setText(change(totalrate) + "%");
        tvTotalamount.setText(data.getData().getLoan_amount() + "万总额");
        tvMinAmount.setText(data.getData().getMin_loan() * 10000 + "元起投");
        tvRateConsist.setText(change(totalrate) + "%" + " " + "(" + firstrate + "%" + "+活动加息" + change(secondrate) + "%)");
        tvRestamount.setText("剩余可投" + change(rest) + "万元");
        tvRentalready.setText("已出借" + change(data.getData().getLoan_progress() * 100) + "%");
        if (data.getData().getBegin_date() != null && data.getData().getLoan_deadline() != null) {
            Date date2 = DateUtils.strToDateLong(data.getData().getBegin_date());
            Date date1 = DateUtils.strToDateLong(data.getData().getLoan_deadline());
            long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) :
                    (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
            tvResisitday.setText(day + "天期限");
            float progress = (float) (data.getData().getLoan_progress() * 100);
            Logger.e(progress + "xx");
            pbProgress.setProgress(progress);
            pbProgress.setSecondaryProgress(progress);
        }


    }

    @Override
    public void showUpAndDownView(Bill data) {
        for (int i=0;i<data.getData().size();i++){
            strings.add(data.getData().get(i).getPhone()+"        "+data.getData().get(i).getBuy_amount()+"万元");
        }
        postTextChange();
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
    public void quit() {
        finish();
    }


    private Double change(Double doubles) {
        BigDecimal b = new BigDecimal(doubles);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    private void postTextChange() {
        new Thread() {
            @Override
            public void run() {
                while (isRunning) {
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning=false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning=false;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


}
