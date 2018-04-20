package com.example.mac.back.learn.inner;

/**
 * Created by mac on 2018/4/13.
 */

public class OutClass6 {
    private OnClickListener mClickListener;
    private OutClass6 mOutClass5;

    interface OnClickListener {
        void onClick();
    }

    public OutClass6 setClickListener(final OnClickListener clickListener) {
        mClickListener = clickListener;
        return this;
    }

    public OutClass6 setOutClass5(final OutClass6 outClass5) {
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
