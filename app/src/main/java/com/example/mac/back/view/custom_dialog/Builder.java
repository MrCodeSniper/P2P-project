package com.example.mac.back.view.custom_dialog;

import android.app.Dialog;
import android.view.View;

/**
 * Created by mac on 2018/3/18.
 */

public interface Builder {


    public Builder setTitle(String title);


    public Builder setContent(View content);

    public Builder setMessage(String message);



    public Builder setPositiveButton(String text, Dialog.OnClickListener listener);

    public Builder setNegativeButton(String text, Dialog.OnClickListener listener);


    public Custom_dialog getFinalDialog();





}
