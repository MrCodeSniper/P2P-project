package com.example.mac.back.ui.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.data.ProductApi;
import com.example.mac.back.ui.contract.UserContract;
import com.example.mac.back.ui.presenter.UserPresenter;
import com.example.mac.back.utils.IntentUtils;
import com.example.mac.back.utils.NotifyUtils;
import com.orhanobut.logger.Logger;
import com.rengwuxian.materialedittext.MaterialEditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

import static android.app.PendingIntent.FLAG_CANCEL_CURRENT;

/**
 * Created by mac on 2018/2/28.
 */
//实现presenter的view接口 将具体实现传给控制层
public class LoginActivity extends BaseActivity implements UserContract.View {

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
    @BindView(R.id.btn_notify)
    Button btnNotify;
    private int notifyID=100;
    private AnimationDrawable animationDrawable;
    private Notification.Builder builder;
    private NotificationChannel channel;
    private NotifyUtils notifyUtils;
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
        mPresenter = new UserPresenter(ProductApi.getInstance(new OkHttpClient()));
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
                animationDrawable = (AnimationDrawable) animationIV.getDrawable();
                animationDrawable.start();
                mPresenter.checkUserExist(etPhone.getText().toString());
            }
        });

        initNotify();
    }

    private void initNotify() {

//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            channel = new NotificationChannel("1",
//                    "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
//            channel.enableLights(true); //是否在桌面icon右上角展示小红点
//            channel.setLightColor(Color.GREEN); //小红点颜色
//            channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
//            mNotificationManager.createNotificationChannel(channel);
//            builder =new Notification.Builder(this,"1");
//        }else {
//            //第一步：实例化通知栏构造器Notification.Builder：
//            builder =new Notification.Builder(this);//实例化通知栏构造器Notification.Builder，参数必填（Context类型），为创建实例的上下文
//        }
//
//
//
//        //第三步：设置通知栏PendingIntent（点击动作事件等都包含在这里）:
//        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/itachi85/"));
//        //PendingIntent可以看做是对Intent的包装，通过名称可以看出PendingIntent用于处理即将发生的意图，而Intent用来用来处理马上发生的意图
//        //本程序用来响应点击通知的打开应用，第二个参数非常重要，点击notification 进入到activity, 使用到pendingIntent类方法，PengdingIntent.getActivity()的第二个参数，即请求参数，实际上是通过该参数来区别不同的Intent的，如果id相同，就会覆盖掉之前的Intent了
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
//        builder.setContentIntent(pendingIntent);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
//        builder.setAutoCancel(true);
//        builder.setContentTitle("悬挂式通知");
//
//        //设置点击跳转
//        Intent hangIntent = new Intent();
//        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        hangIntent.setClass(this, MainActivity.class);
//        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
//        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setFullScreenIntent(hangPendingIntent, true);


    //   notifyUtils=new NotifyUtils(this,"");





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
        String[] params = {"phone"};
        String[] values = {etPhone.getText().toString()};
        IntentUtils.showIntent(LoginActivity.this, RegisterActivity.class, params, values);
    }

    @Override
    public void goToLogin() {
        initNext();
        String[] params = {"phone"};
        String[] values = {etPhone.getText().toString()};
        IntentUtils.showIntent(LoginActivity.this, PasswordinputActivity.class, params, values);
    }

    @Override
    public void showError() {
        initNext();
        Toast.makeText(LoginActivity.this, "未知错误，请与管理员联系", Toast.LENGTH_SHORT);
    }

    @Override
    public void complete() {
        initNext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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




    @OnClick(R.id.btn_notify)
    public void notifys(){
        //第五步：发送通知请求：
        Logger.e("fasf");
//        mNotificationManager.notify(1,builder.build());//发送通知请求
        notifyUtils.show();
    }

}
