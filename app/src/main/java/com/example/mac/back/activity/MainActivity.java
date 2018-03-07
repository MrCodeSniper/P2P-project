package com.example.mac.back.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mac.back.R;
import com.example.mac.back.adapter.MyFragmentAdapter;
import com.example.mac.back.base.BaseActivity;
import com.example.mac.back.fragment.Fragment_blank;
import com.example.mac.back.fragment.Fragment_home;
import com.example.mac.back.fragment.Fragment_more;
import com.example.mac.back.fragment.Fragment_my;
import com.example.mac.back.fragment.Fragment_product;
import com.example.mac.back.utils.StatusBarUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    private boolean isClickChangeTab=false;
    private FragmentTabHost mTabHost;
    private LayoutInflater layoutInflater;
    private Class fragmentArray[] = { Fragment_home.class, Fragment_home.class,Fragment_home.class,Fragment_home.class,Fragment_home.class };
    private int imageViewArray[] = { R.drawable.tab_home, R.drawable.tab_produce,R.drawable.circle_pre,R.drawable.tab_my,R.drawable.tab_more };
    private String textViewArray[] = { "首页", "分类","sss","xxx","xxx"};
    private List<Fragment> list = new ArrayList<Fragment>();
    private ViewPager vp;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;


    @Override
    protected void initUI() {
        setContentView(R.layout.activity_main);
        initView();//初始化控件
        StatusBarUtils statusBarUtils=new StatusBarUtils(this);
        statusBarUtils.initStatuBar(R.color.maincolor);
    }

    @Override
    protected void initData() {
        initPage();
    }

    @Override
    protected void initListener() {
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    /*初始化Fragment*/
    private void initPage() {
        Fragment_product fragment1 = new Fragment_product();
        Fragment_home fragment2 = new Fragment_home();
        Fragment_my fragment3 = new Fragment_my();
        Fragment_more fragment4 = new Fragment_more();
        Fragment_blank fragment_blank=new Fragment_blank();

        list.add(fragment2);
        list.add(fragment1);
        //list.add(fragment_blank);
        list.add(fragment3);
        list.add(fragment4);

        //绑定Fragment适配器
        vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), list));
        mTabHost.getTabWidget().setDividerDrawable(null);//分割线样式
    }

    //    控件初始化控件
    private void initView() {
        vp = (ViewPager) findViewById(R.id.pager);

        /*实现OnPageChangeListener接口,目的是监听Tab选项卡的变化，然后通知ViewPager适配器切换界面*/
        /*简单来说,是为了让ViewPager滑动的时候能够带着底部菜单联动*/

        vp.addOnPageChangeListener(this);//设置页面切换时的监听器
        layoutInflater = LayoutInflater.from(this);//加载布局管理器

        /*实例化FragmentTabHost对象并进行绑定*/
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);//绑定tahost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.pager);//绑定viewpager

        /*实现setOnTabChangedListener接口,目的是为监听界面切换），然后实现TabHost里面图片文字的选中状态切换*/
        /*简单来说,是为了当点击下面菜单时,上面的ViewPager能滑动到对应的Fragment*/
        mTabHost.setOnTabChangedListener(this);


        int count = textViewArray.length;

        /*新建Tabspec选项卡并设置Tab菜单栏的内容和绑定对应的Fragment*/
        for (int i = 0; i < count; i++) {
            // 给每个Tab按钮设置标签、图标和文字
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(textViewArray[i])
                    .setIndicator(getTabItemView(i));
            // 将Tab按钮添加进Tab选项卡中，并绑定Fragment
            mTabHost.addTab(tabSpec, fragmentArray[i], null);
            mTabHost.setTag(i);
            mTabHost.getTabWidget().getChildAt(i)
                    .setBackgroundResource(R.drawable.selector_tab_background);//设置Tab被选中的时候颜色改变
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        TabWidget widget = mTabHost.getTabWidget();
        int oldFocusability = widget.getDescendantFocusability();
        widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);//设置View覆盖子类控件而直接获得焦点
        if(position>=2){
            mTabHost.setCurrentTab(position+1);//根据位置Postion设置当前的Tab
        }else{
            mTabHost.setCurrentTab(position);//根据位置Postion设置当前的Tab
        }

        widget.setDescendantFocusability(oldFocusability);//设置取消分割线
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabChanged(String s) {
//        Logger.e(isClickChangeTab+"???");

        int position = mTabHost.getCurrentTab();
//        Logger.e(position+"xxx");

            if(position>=2){
                vp.setCurrentItem(position-1);
                isClickChangeTab=true;
            }else{
                vp.setCurrentItem(position);//把选中的Tab的位置赋给适配器，让它控制页面切换
            }






    }





    private View getTabItemView(int i) {
        //将xml布局转换为view对象
        View view = layoutInflater.inflate(R.layout.tab_content, null);
        //利用view对象，找到布局中的组件,并设置内容，然后返回视图
        ImageView mImageView = (ImageView) view
                .findViewById(R.id.tab_imageview);
        TextView mTextView = (TextView) view.findViewById(R.id.tab_textview);
        mImageView.setImageResource(imageViewArray[i]);
        mTextView.setText(textViewArray[i]);
        return view;
    }
}
