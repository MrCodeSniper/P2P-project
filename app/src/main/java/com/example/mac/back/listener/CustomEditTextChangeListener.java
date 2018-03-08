package com.example.mac.back.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by mac on 2018/3/8.
 */

public abstract class CustomEditTextChangeListener implements TextWatcher {



    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
       onTextChanges(charSequence,i,i1,i2);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    public abstract void onTextChanges(CharSequence charSequence, int i, int i1, int i2);

}
