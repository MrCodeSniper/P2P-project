package com.example.mac.back.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.mac.back.R;
import com.example.mac.back.utils.DensityUtil;


public class TagView extends android.support.v7.widget.AppCompatTextView {
    private Drawable deleteIcon;
    private int iconWidth;
    private int iconHeight;
    private boolean showIcon = true;
    private Rect mDelteRect;
    private Rect mAssumeDelteRect;

    public TagView(Context context) {
        super(context);
//        int id = context.getResources().getIdentifier("ic_delete", "drawable", context.getPackageName());
        deleteIcon = context.getResources().getDrawable(R.drawable.ic_delete);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mAssumeDelteRect == null) {
            setDeleteBounds();
        }
        if (showIcon) {
            deleteIcon.draw(canvas);
        }
    }

    private void setDeleteBounds() {
        iconWidth = deleteIcon.getIntrinsicWidth();
        iconHeight = deleteIcon.getIntrinsicHeight();
        int left = getWidth() - iconWidth;
        int top = 0;
        mDelteRect = new Rect(left, top, left + iconWidth, top + iconHeight);
        //padding扩大了icon的点击范围
        int padding = DensityUtil.dip2px(getContext(), 10);
        mAssumeDelteRect = new Rect(mDelteRect.left, mDelteRect.top, mDelteRect.left + iconWidth + padding, mDelteRect.top + iconHeight + padding);
        deleteIcon.setBounds(mDelteRect);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        boolean contains = mAssumeDelteRect.contains(x, y);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (contains && showIcon) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (contains && showIcon) {
                    if (mListener != null) {
                        mListener.onDelete(this);
                    }
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private void log(String msg) {
        Log.e(getClass().getCanonicalName(), msg);
    }

    public void showDeleteIcon(boolean show) {
        showIcon = show;
        invalidate();
    }

    private OnTagDeleteListener mListener;

    public void setOnTagDeleteListener(OnTagDeleteListener listener) {
        mListener = listener;
    }

    public interface OnTagDeleteListener {
        /**
         * Delete view.
         *
         * @param deleteView
         */
        void onDelete(View deleteView);
    }

}
