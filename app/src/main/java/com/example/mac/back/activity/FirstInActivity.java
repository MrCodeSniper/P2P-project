package com.example.mac.back.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mac.back.R;
import com.example.mac.back.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mac on 2018/2/28.
 */

public class FirstInActivity extends Activity {

    //ViwePager
    private ViewPager viewPager;
    //存放三个灰色圆点的线性布局
    private LinearLayout ll;
    private List<ImageView> imageViews;
    //导航页资源
    private int[] images = new int[]{
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstin_layout);
        viewPager = (ViewPager)   findViewById(R.id.viewPager);
        imageViews = new ArrayList<ImageView>();
        //初始化导航页面
        for (int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(FirstInActivity.this);
            iv.setImageResource(images[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(iv);
        }
        ll = (LinearLayout) findViewById(R.id.ll);

        viewPager.setAdapter(new MyAdapter());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == images.length-1){
                    ll.setVisibility(View.VISIBLE);
                }else {
                    ll.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    //PagerAdapter有四个方法
    class MyAdapter extends PagerAdapter {
        //返回导航页的个数
        @Override
        public int getCount() {
            return images.length;
        }
        //判断是否由对象生成
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        //加载页面
        //ViewGroup:父控件指ViewPager
        //position:当前子控件在父控件中的位置
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = imageViews.get(position);
            container.addView(iv);
            return iv;
        }
        //移除页面
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }



    public void sendMessage(View v){
        startActivity(new Intent(FirstInActivity.this, MainActivity.class));
        SharedPreferencesUtils.putBoolean("cat","isLogin",true);
        finish();
    }


}
