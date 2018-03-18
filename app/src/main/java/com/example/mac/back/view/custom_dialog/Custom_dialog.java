package com.example.mac.back.view.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by mac on 2018/3/18.
 */

public class Custom_dialog extends Dialog{


    public Custom_dialog(Context context) {
        super(context);
    }

    public Custom_dialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected Custom_dialog(Context context, boolean cancelable,OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }




}
