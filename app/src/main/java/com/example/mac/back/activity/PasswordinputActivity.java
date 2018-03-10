package com.example.mac.back.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.bean.User;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.event.MessageEvent;
import com.example.mac.back.listener.CustomEditTextChangeListener;
import com.example.mac.back.utils.IntentUtils;
import com.example.mac.back.utils.Md5Utils;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.example.mac.back.utils.ToastUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by mac on 2018/3/8.
 */

public class PasswordinputActivity extends BaseActivity {


    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.tv_titles)
    TextView tvTitles;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindString(R.string.password_title)
    String title;
    @BindView(R.id.animationIV)
    ImageView animationIV;

    Request request;

    private AnimationDrawable animationDrawable;
    GradientDrawable drawable;
    GradientDrawable anim_drawable;

    String phone;
    String password;

    float radius = 12;
    @Override
    protected void initUI() {
        setContentView(R.layout.activity_logins);
        ButterKnife.bind(this);
        tvTitles.setText(title);
        drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        anim_drawable = new GradientDrawable();
        anim_drawable.setCornerRadius(radius);
        anim_drawable.setColor(getResources().getColor(R.color.anim));
        animationIV.setBackground(anim_drawable);
        etPhone.setPrimaryColor(getResources().getColor(R.color.maincolor));
    }

    @Override
    protected void initData() {
        request = ItheimaHttp.newPostRequest(AppConfig.LOGIN);//apiUrl格式："xxx/xxxxx"
    }

    @Override
    protected void initListener() {
        etPhone.addTextChangedListener(new CustomEditTextChangeListener() {
            @Override
            public void onTextChanges(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >0&& !TextUtils.equals(charSequence,"")) {
                    drawable.setColor(getResources().getColor(R.color.maincolor));
                    btnNext.setTextColor(getResources().getColor(R.color.white));
                    btnNext.setBackground(drawable);
                    btnNext.setEnabled(true);
                } else {
                    drawable.setColor(getResources().getColor(R.color.btn_next_back_color));
                    btnNext.setTextColor(getResources().getColor(R.color.btn_next_text_color));
                    btnNext.setBackground(drawable);
                    btnNext.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.iv_quit)
    public void quit() {
        finish();
    }

    @OnClick(R.id.iv_clear)
    public void clear() {
        etPhone.setText("");
    }

    @OnClick(R.id.btn_next)
    public void login() {
       phone=getIntent().getStringExtra("phone");
       password= Md5Utils.HEXAndMd5(etPhone.getText().toString());
       initanim();
       initnet();



    }

    private void initnet() {
        request.putParams("phone",phone);
        request.putParams("password",password);
        Call call = ItheimaHttp.send(request, new HttpResponseListener<User>() {
            @Override
            public void onResponse(User baseBean, Headers headers) {
                btnNext.setEnabled(true);
                animationIV.setVisibility(View.GONE);
                if(baseBean.isSuccess()){
                    EventBus.getDefault().post(new MessageEvent("登陆成功",baseBean));
                    ToastUtils.getInstanc(PasswordinputActivity.this).showToast("登陆成功");
                    SharedPreferencesUtils.putBoolean("cat","logined",true);
                    SharedPreferencesUtils.putString("cat","username",baseBean.getData().getName());
                    SharedPreferencesUtils.putString("cat","imgurl",baseBean.getData().getImageurl());
                    SharedPreferencesUtils.putString("cat","phone",baseBean.getData().getPhone());
                    finishAll();
                }else {
                    Logger.e("xxx");
                    ToastUtils.getInstanc(PasswordinputActivity.this).showToast("登陆失败");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable e) {
                btnNext.setEnabled(true);
                Logger.e("xxxqq"+e.getMessage());
                animationIV.setVisibility(View.GONE);
                ToastUtils.getInstanc(PasswordinputActivity.this).showToast("登陆失败"+e.getMessage());
            }
        });
    }

    private void initanim() {
        animationIV.setVisibility(View.VISIBLE);
        animationIV.setImageResource(R.drawable.loading_anim);
        animationDrawable=(AnimationDrawable)animationIV.getDrawable();
        animationDrawable.start();



    }


}
