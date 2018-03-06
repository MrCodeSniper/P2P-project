package com.example.mac.back.activity;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.utils.IntentUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by mac on 2018/3/6.
 */

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.et_yzm)
    MaterialEditText etYzm;
    @BindView(R.id.iv_clear_yzm)
    ImageView ivClearYzm;
    @BindView(R.id.et_psd)
    MaterialEditText etPsd;
    @BindView(R.id.iv_clear_psd)
    ImageView ivClearPsd;
    @BindView(R.id.btn_register_finish)
    Button btnRegisterFinish;
    @BindView(R.id.btn_yzm)
    Button btnYzm;
    String phone;
    GradientDrawable drawable;
    float radius = 12;
    boolean yzmFlag=false;
    boolean psdFlag=false;

    @Override
    protected void initUI() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        phone=getIntent().getStringExtra("phone");
        //15168264355
        etYzm.setHint("将短信发送至"+phone.substring(0,3)+"****"+phone.substring(7,11));
        etYzm.setPrimaryColor(getResources().getColor(R.color.maincolor));
        etPsd.setPrimaryColor(getResources().getColor(R.color.maincolor));
        drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        etYzm.addTextChangedListener(new MyEditTextChangeListener());
        etPsd.addTextChangedListener(new MyEditTextChangeListener2());
    }


    public class MyEditTextChangeListener implements TextWatcher {
        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >=1) {
                ivClearYzm.setVisibility(View.VISIBLE);
                yzmFlag=true;
            } else {
                ivClearYzm.setVisibility(View.GONE);
                yzmFlag=false;
            }

            changeStateofClick(yzmFlag,psdFlag);
        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }



    @OnClick({R.id.et_yzm})   //给 button1 设置一个点击事件
    public void clearYzm(){
        etYzm.setText("");
    }

    @OnClick({R.id.iv_quit})   //给 button1 设置一个点击事件
    public void quit(){
        finish();
    }

    @OnClick({R.id.btn_yzm})   //给 button1 设置一个点击事件
    public void getYzm(){
        Request request = ItheimaHttp.newGetRequest(AppConfig.SEND_SMS);//apiUrl格式："xxx/xxxxx"
        request.putParams("phoneNumber",phone);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<BaseBean>() {
            @Override
            public void onResponse(BaseBean baseBean, Headers headers) {
                Logger.e("xxx"+baseBean.isSuccess());
                //进行验证
                TimeCount time=new TimeCount(60000,1000);//参数依次为总时长，计时时间间隔
                time.start();
                if(baseBean.isSuccess()){
                    Toast.makeText(RegisterActivity.this,"获取短信成功",Toast.LENGTH_SHORT);
                }else{
                    Toast.makeText(RegisterActivity.this,"获取短信失败",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                Toast.makeText(RegisterActivity.this,"获取短信失败",Toast.LENGTH_SHORT);
            }
        });
    }


    class  TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程显示
        @Override
        public void onTick(long millisUntilFinished) {
           btnYzm.setText(millisUntilFinished / 1000 + "秒后重新发送");
           btnYzm.setEnabled(false);
        }
        //计时完成触发
        @Override
        public void onFinish() {
            btnYzm.setText("获取验证码");
            btnYzm.setEnabled(true);
        }
    }


    public class MyEditTextChangeListener2 implements TextWatcher {
        /**
         * 编辑框的内容发生改变之前的回调方法
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


        }

        /**
         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
         * 我们可以在这里实时地 通过搜索匹配用户的输入
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >=1) {
                psdFlag=true;
            } else {
                psdFlag=false;
            }

            changeStateofClick(yzmFlag,psdFlag);
        }

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private void changeStateofClick(boolean first,boolean second) {
        if(first&&second){
            drawable.setColor(getResources().getColor(R.color.maincolor));
            btnRegisterFinish.setEnabled(true);
            btnRegisterFinish.setTextColor(getResources().getColor(R.color.white));
            btnRegisterFinish.setBackground(drawable);
        }else {
            drawable.setColor(getResources().getColor(R.color.btn_next_back_color));
            btnRegisterFinish.setTextColor(getResources().getColor(R.color.btn_next_text_color));
            btnRegisterFinish.setBackground(drawable);
            btnRegisterFinish.setEnabled(false);
        }
    }

}
