package com.zhejiang.haoxiadan.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyBoardUtil {
	
	//隐藏虚拟键盘
    public static void hideKeyboard(Context context, View focusedView)
    {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(focusedView.getApplicationWindowToken(), 0);
        }   
    }

}
