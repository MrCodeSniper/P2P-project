package com.example.mac.back.ui.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.bean.BaseBean;
import com.example.mac.back.config.AppConfig;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UserContract;
import com.example.mac.back.ui.presenter.ProductPresenter;
import com.example.mac.back.ui.presenter.UserPresenter;
import com.example.mac.back.utils.IntentUtils;
import com.itheima.retrofitutils.ItheimaHttp;
import com.itheima.retrofitutils.Request;
import com.itheima.retrofitutils.listener.HttpResponseListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by mac on 2018/2/28.
 */
//实现presenter的view接口 将具体实现传给控制层
public class LoginActivity extends BaseActivity implements UserContract.View{

    @BindView(R.id.iv_quit)
    ImageView ivQuit;
    @BindView(R.id.et_phone)
    MaterialEditText etPhone;
    @BindView(R.id.btn_next)
    Button btnNext;
    GradientDrawable drawable;
    GradientDrawable anim_drawable;
    float radius = 12;
    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.animationIV)
    ImageView animationIV;
    @Inject
    UserPresenter mPresenter;

    private AnimationDrawable animationDrawable;


    @Override
    protected void initUI() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        etPhone.addTextChangedListener(new MyEditTextChangeListener());
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
        mPresenter=new UserPresenter(ProductApi.getInstance(new OkHttpClient()));
        mPresenter.attachView(this);
    }

    @Override
    protected void initListener() {
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etPhone.setText("");
            }
        });
        ivQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNext.setEnabled(false);
                animationIV.setVisibility(View.VISIBLE);
                animationIV.setImageResource(R.drawable.loading_anim);
                animationDrawable=(AnimationDrawable)animationIV.getDrawable();
                animationDrawable.start();
                mPresenter.checkUserExist(etPhone.getText().toString());
            }
        });
    }

    private void initNext() {
        btnNext.setEnabled(true);
        animationIV.setVisibility(View.GONE);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void goToRegister() {
        initNext();
        String[] params={"phone"};
        String [] values={etPhone.getText().toString()};
        IntentUtils.showIntent(LoginActivity.this,RegisterActivity.class,params,values);
    }

    @Override
    public void goToLogin() {
        initNext();
        String[] params={"phone"};
        String [] values={etPhone.getText().toString()};
        IntentUtils.showIntent(LoginActivity.this,PasswordinputActivity.class,params,values);
    }

    @Override
    public void showError() {
        initNext();
        Toast.makeText(LoginActivity.this,"未知错误，请与管理员联系",Toast.LENGTH_SHORT);
    }

    @Override
    public void complete() {
        initNext();
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
            if (charSequence.length() == 11) {
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

        /**
         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
         */
        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}