package com.example.mac.back.view.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mac.back.R;

/**
 * Created by mac on 2018/3/18.
 */

public class CustomDialogBuilder implements Builder {


    private String title;
    private String message;
    private String positiveButtonContent;
    private String negativeButtonContent;
    private Dialog.OnClickListener positiveButtonListener;
    private Dialog.OnClickListener negativeButtonListener;
    private View contentView;

    private static volatile CustomDialogBuilder instance;

    private Context mContext;

    private Custom_dialog mDialog;


    private CustomDialogBuilder(Context context) {
            this.mContext=context;
    }

    public static CustomDialogBuilder getInstance(Context context){
        if(instance==null){
            synchronized (CustomDialogBuilder.class) {
                if (instance == null) {
                    instance = new CustomDialogBuilder(context);
                }
            }
        }
        return instance;
    }





    @Override
    public Builder setTitle(String title) {
        this.title=title;
        return  this;
    }

    @Override
    public Builder setContent(View content) {
        this.contentView=content;
        return  this;
    }

    @Override
    public Builder setMessage(String message) {
        this.message=message;
        return  this;
    }



    @Override
    public Builder setPositiveButton(String text, Dialog.OnClickListener listener) {
            this.positiveButtonContent=text;
            this.positiveButtonListener=listener;
            return  this;
    }

    @Override
    public Builder setNegativeButton(String text, Dialog.OnClickListener listener) {
        this.negativeButtonContent=text;
        this.negativeButtonListener=listener;
        return  this;
    }


    @Override
    public Custom_dialog getFinalDialog(){
        mDialog=new Custom_dialog(mContext, R.style.Dialog);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogLayoutView = inflater.inflate(R.layout.dialog_custom, null);
        mDialog.addContentView(dialogLayoutView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        if (!TextUtils.isEmpty(title)) {
            ((TextView) dialogLayoutView.findViewById(R.id.tv_dialog_title))
                    .setText(title);
        } else {
            Log.w(mContext.getClass().toString(), "未设置对话框标题！");
        }
        if (!TextUtils.isEmpty(message)) {
            ((TextView) dialogLayoutView.findViewById(R.id.dialog_content))
                    .setText(message);
        } else if (contentView != null) {
            ((LinearLayout) dialogLayoutView
                    .findViewById(R.id.dialog_llyout_content))
                    .removeAllViews();
            ((LinearLayout) dialogLayoutView
                    .findViewById(R.id.dialog_llyout_content)).addView(
                    contentView, new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            Log.w(mContext.getClass().toString(), "未设置对话框提示内容！");
        }

        if (!TextUtils.isEmpty(positiveButtonContent)) {
            ((TextView) dialogLayoutView.findViewById(R.id.tv_dialog_pos))
                    .setText(positiveButtonContent);
            if (positiveButtonListener != null) {
                ((TextView) mDialog.findViewById(R.id.tv_dialog_pos))
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                positiveButtonListener.onClick(mDialog, -1);
                            }
                        });

            }
        } else {
            ((TextView) dialogLayoutView.findViewById(R.id.tv_dialog_pos))
                    .setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(negativeButtonContent)) {
            ((TextView) dialogLayoutView.findViewById(R.id.tv_dialog_neg))
                    .setText(negativeButtonContent);
            if (negativeButtonListener != null) {
                ((TextView) dialogLayoutView
                        .findViewById(R.id.tv_dialog_neg))
                        .setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                negativeButtonListener.onClick(mDialog, -2);
                            }
                        });
            }
        } else {
            ((TextView) dialogLayoutView.findViewById(R.id.tv_dialog_neg))
                    .setVisibility(View.GONE);
        }
        /**
         * 将初始化完整的布局添加到dialog中
         */
        mDialog.setContentView(dialogLayoutView);
        /**
         * 禁止点击Dialog以外的区域时Dialog消失
         */
        mDialog.setCanceledOnTouchOutside(false);
        return mDialog;
    }





}
