package com.example.mac.back.fragment;

/**
 * Created by mac on 2018/2/28.
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.activity.LoginActivity;
import com.example.mac.back.adapter.GridViewAdapter;
import com.example.mac.back.event.MessageEvent;
import com.example.mac.back.utils.DensityUtil;
import com.example.mac.back.utils.SharedPreferencesUtils;
import com.huxq17.handygridview.HandyGridView;
import com.huxq17.handygridview.listener.IDrawer;
import com.huxq17.handygridview.listener.OnItemCapturedListener;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.grid_tips)
    HandyGridView mGridView;
    @BindView(R.id.ll_logined)
    LinearLayout llLogined;

    private int[] images = {R.drawable.meee, R.drawable.gifts, R.drawable.diaamond, R.drawable.phonee, R.drawable.choujiang, R.drawable.data};


    private List<String> strList;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private GridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        adapter = new GridViewAdapter(getActivity(), strList, images);
        mGridView.setAdapter(adapter);
        setMode(HandyGridView.MODE.LONG_PRESS);
        mGridView.setAutoOptimize(false);
        //当gridview可以滚动并且被拖动的item位于gridview的顶部或者底部时，设置gridview滚屏的速度，
        // 每秒移动的像素点个数，默认750，可不设置。
        mGridView.setScrollSpeed(750);
//        adapter.notifyDataSetChanged();
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mGridView.isTouchMode() && !mGridView.isNoneMode() && !adapter.isFixed(position)) {//long press enter edit mode.
                    setMode(HandyGridView.MODE.TOUCH);
                    return true;
                }
                return false;
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "click item at " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mGridView.setOnItemCapturedListener(new OnItemCapturedListener() {
            @Override
            public void onItemCaptured(View v, int position) {
                v.setScaleX(1.2f);
                v.setScaleY(1.2f);
            }

            @Override
            public void onItemReleased(View v, int position) {
                v.setScaleX(1f);
                v.setScaleY(1f);
            }

        });
        mGridView.setDrawer(new IDrawer() {
            @Override
            public void onDraw(Canvas canvas, int width, int height) {
                if (!mGridView.isNoneMode()) {
                    int offsetX = -DensityUtil.dip2px(getActivity(), 10);
                    int offsetY = -DensityUtil.dip2px(getActivity(), 10);
                    //文字绘制于gridview的右下角，并向左，向上偏移10dp。
                    drawTips(canvas, width + offsetX, height + offsetY);
                }
            }
        }, false);
        int permission = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(),
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }

        if(SharedPreferencesUtils.getBoolean("cat","logined",false)){
            ivNologin.setVisibility(View.GONE);
            llLogined.setVisibility(View.VISIBLE);
        }else {
            ivNologin.setVisibility(View.VISIBLE);
            llLogined.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    public void initData() {
        strList = new ArrayList<>();
        strList.add("邀请有奖");
        strList.add("我的优惠");
        strList.add("我的特权");
        strList.add("个人设置");
        strList.add("喵乐园");
        strList.add("理财记录");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_nologin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.login_register:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent event) {

        Logger.e("fragment-my");
        //处理逻辑
        if (event.getUser() != null) {
            ivBack.setImageResource(R.drawable.catears_noline);
            ivNologin.setVisibility(View.GONE);
            loginRegister.setVisibility(View.GONE);

        }

    }

    private void setMode(HandyGridView.MODE mode) {
        mGridView.setMode(mode);
//        changeModeTv.setText(mode.toString());
        adapter.setInEditMode(mode == HandyGridView.MODE.TOUCH);
    }

    String paintText = "长按排序或删除";
    int textWidth;
    int textHeight;
    StaticLayout tipsLayout;
    private TextPaint mTextPaint;

    private void drawTips(Canvas canvas, int width, int height) {
        if (mTextPaint == null) {
            mTextPaint = new TextPaint();
            mTextPaint.setColor(Color.parseColor("#cccccc"));
            mTextPaint.setTextSize(DensityUtil.dip2px(getActivity(), 12));
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            textHeight = (int) (fontMetrics.bottom - fontMetrics.top) + 1;
            textWidth = (int) mTextPaint.measureText(paintText) + 1;
        }
        width = width - textWidth;
        height = height - textHeight;
        if (tipsLayout == null) {
            tipsLayout = new StaticLayout(paintText, mTextPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.5f, 0f, false);
        }
        canvas.translate(width, height);
        tipsLayout.draw(canvas);
    }

}