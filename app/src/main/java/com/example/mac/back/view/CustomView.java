package com.example.mac.back.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.mac.back.R;
import com.orhanobut.logger.Logger;

/**
 * Created by mac on 2018/2/27.
 */

public class CustomView extends View{

    private int default_size;
    private int default_color;


    public CustomView(Context context){
        super(context);
    }


    public CustomView(Context context, AttributeSet attrs){
        super(context,attrs);

        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.CustomView);//得到名字为。。。的样式属性列表
        default_size=a.getDimensionPixelSize(R.styleable.CustomView_default_size,100);//第二哥为默认
        default_color=a.getColor(R.styleable.CustomView_default_color,Color.GREEN);



        a.recycle();


    }
    //自定义长宽
    //首先测量宽高
    //参数包含  宽度测量信息 高度测量信息 （测量模式(int 中的前2bit用来表示)，尺寸大小(int 中的后30bit用来表示)）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //通过工具类查出大小和测量模式
        int withMode=MeasureSpec.getMode(widthMeasureSpec);
        int withSize=MeasureSpec.getSize(widthMeasureSpec);
        //测量模式分为 UNSPECIFIED（父容器没有限制，可以任意取），EXACTLY（当前的尺寸就是当前View应该取的尺寸），AT_MOST（当前尺寸是当前View能取的最大尺寸）
        int width=getMySize(100,widthMeasureSpec);
//        Logger.e( "width"+width);
        int height=getMySize(100,heightMeasureSpec);


        if (width < height) {
            height = width;
        } else {
            width = height;
        }

        setMeasuredDimension(width, height);



    }



    //用画笔画
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius=default_size;
        int centerx=getLeft()+radius;
        int centery=getTop()+radius;

        Paint paint=new Paint();
        paint.setColor(default_color);

        canvas.drawCircle(centerx,centery,radius,paint);


    }

    private int getMySize(int defaultSize, int measureSpec){

       int mySize=defaultSize;

        int Mode=MeasureSpec.getMode(measureSpec);
        int Size=MeasureSpec.getSize(measureSpec);




        switch (Mode){
            case MeasureSpec.EXACTLY://如果是固定的大小，那就不要去改变它 match_parent 100dp
//                Logger.e( "0");
                mySize= Size;
                break;
            case MeasureSpec.AT_MOST://如果测量模式是最大取值为size  wrap_content
//                Logger.e( "1");
                mySize=Size;
                break;
            case MeasureSpec.UNSPECIFIED://如果没有指定大小，就设置为默认大小
//                Logger.e("2");
                mySize=defaultSize;
                break;
        }


        return mySize;


    }

    //View和Window绑定时就会调用这个函数
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
