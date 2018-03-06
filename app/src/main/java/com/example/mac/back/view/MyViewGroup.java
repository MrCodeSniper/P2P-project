package com.example.mac.back.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.orhanobut.logger.Logger;


/**
 * Created by mac on 2018/2/27.
 */

public class MyViewGroup extends ViewGroup{


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count=getChildCount();
        int curHeight=top;
        //将子View逐个摆放
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int height = child.getMeasuredHeight();
            int width = child.getMeasuredWidth();
            //摆放子View，参数分别是子View矩形区域的左、上、右、下边
            child.layout(left, curHeight, left+ width, curHeight + height);
            curHeight += height;
        }


    }

    //当View中所有的子控件均被映射成xml后触发 可以得到子视图宽高
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View childView1 = getChildAt(0);
        View childView2= getChildAt(1);
        int menuWidth = childView1.getLayoutParams().width;//获取到menuview中的宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //1-测量所有子view
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childViewCount=getChildCount();
        Logger.e("viewcount:"+childViewCount);

        if(childViewCount == 0){
            setMeasuredDimension(0, 0);
        }else if(widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(getMaxChildWidth(),getTotleHeight());
        }else if(heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,getTotleHeight());
        }else if(widthMeasureSpec==MeasureSpec.AT_MOST){
            setMeasuredDimension(getMaxChildWidth(),heightSize);
        }




    }

    /***
     * 获取子View中宽度最大的值
     */
    private int getMaxChildWidth() {
        int childCount = getChildCount();
        int maxWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getMeasuredWidth() > maxWidth)
                maxWidth = childView.getMeasuredWidth();

        }

        return maxWidth;
    }

    private int getTotleHeight(){
        int childCount = getChildCount();
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            height += childView.getMeasuredHeight();

        }

        return height;
    }
}
