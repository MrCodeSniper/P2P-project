package com.example.mac.back.learn.inner;

import android.os.Message;

import com.example.mac.back.data.Handler;

/**
 * Created by mac on 2018/4/12.
 */

public class OutClass5 {
    private OnClickListener mClickListener;
    private OutClass5 mOutClass5;

    interface OnClickListener {
        void onClick();
    }

    public OutClass5 setClickListener(final OnClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public OutClass5 setOutClass5(final OutClass5 outClass5) {
        mOutClass5 = outClass5;
        return this;
    }

    public void setClickInfo(final String info, int type) {
        setClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("click " + info);
            }
        });

        setClickListener(new OnClickListener() {
            @Override
            public void onClick() {
                System.out.println("click2 " + info);
            }
        });
    }



}
