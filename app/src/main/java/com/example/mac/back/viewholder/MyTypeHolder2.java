package com.example.mac.back.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dinuscxj.progressbar.CircleProgressBar;
import com.example.mac.back.R;
import com.example.mac.back.bean.Product;
import com.example.mac.back.utils.DateUtils;
import com.orhanobut.logger.Logger;

import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

import java.util.Date;

import butterknife.BindView;

/**
 * Created by mac on 2018/3/5.
 */

public class MyTypeHolder2 extends BaseRecyclerViewHolder {
    //换成你布局文件中的id


    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_category)
    TextView productCategory;
    @BindView(R.id.tv_firstrate)
    TextView tvFirstrate;
    @BindView(R.id.tv_secondrate)
    TextView tvSecondrate;
    @BindView(R.id.ll_rate)
    LinearLayout llRate;
    @BindView(R.id.tv_out_time)
    TextView tvOutTime;
    @BindView(R.id.line_progress)
    CircleProgressBar lineProgress;
    @BindView(R.id.tv_loan_rate)
    TextView tvLoanRate;
    @BindView(R.id.iv_phone)
    FrameLayout ivPhone;
    @BindView(R.id.tv_peryearrate)
    TextView tv_peryearrate;
    @BindView(R.id.product_action)
    TextView product_action;
    @BindView(R.id.ll_category)
    LinearLayout ll_category;

    public MyTypeHolder2(ViewGroup parentView, int itemResId) {
        super(parentView, itemResId);
    }

    /**
     * 绑定数据的方法，在mData获取数据（mData声明在基类中）
     */
    @Override
    public void onBindRealData() {
        Product.DataBean datas = (Product.DataBean) mData;
//        Logger.e("xxx" + datas.getName());
        productName.setText(datas.getCategory());
        productCategory.setText(datas.getName());
        tvFirstrate.setText(datas.getFirstrate()*100+"%");
        tvSecondrate.setText("+"+datas.getSecondrate()*100+"%");
        lineProgress.setProgress((int) (datas.getLoan_progress()*100));
        tvLoanRate.setText((int)(datas.getLoan_progress()*100)+"%");
        if(datas.getBegin_date()!=null&&datas.getLoan_deadline()!=null){
            Date date2=DateUtils.strToDateLong(datas.getBegin_date());
            Date date1=DateUtils.strToDateLong(datas.getLoan_deadline());
            long day = (date1.getTime()-date2.getTime())/(24*60*60*1000)>0 ? (date1.getTime()-date2.getTime())/(24*60*60*1000):
                    (date2.getTime()-date1.getTime())/(24*60*60*1000);
//        Logger.e(day+"sadfasf");
            tvOutTime.setText(day+"天");
        }

       if(!datas.isIs_active()){
            tvLoanRate.setText("售磬");
            tvLoanRate.setTextColor(mContext.getResources().getColor(R.color.btn_next_text_color));
            tvFirstrate.setText("已售完");
            tvSecondrate.setVisibility(View.GONE);
           tv_peryearrate.setVisibility(View.GONE);
           product_action.setVisibility(View.GONE);
           tvFirstrate.setTextColor(mContext.getResources().getColor(R.color.anim));
       }


    }

    public Product.DataBean getData(){
        return (Product.DataBean) mData;
    }

    public void dismissTitle(){
        ll_category.setVisibility(View.GONE);
    }
    public void undismissTitle(){
        ll_category.setVisibility(View.VISIBLE);
    }
}