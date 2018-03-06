package com.example.mac.back.viewholder;

import android.view.ViewGroup;

import com.example.mac.back.bean.Product;

import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;

/**
 * Created by mac on 2018/3/5.
 */

public class MyTypeHolder1 extends BaseRecyclerViewHolder {
    //换成你布局文件中的id


    public MyTypeHolder1(ViewGroup parentView, int itemResId) {
        super(parentView, itemResId);

    }

    /**
     * 绑定数据的方法，在mData获取数据（mData声明在基类中）
     */
    @Override
    public void onBindRealData() {


    }



}