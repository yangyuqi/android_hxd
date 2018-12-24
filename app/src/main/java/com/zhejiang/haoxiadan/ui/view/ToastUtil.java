package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by KK on 2017/2/20.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void show(Context context, int resId){
        if(mToast == null) {
            mToast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void show(Context context,String res){
        if(mToast == null) {
            mToast = Toast.makeText(context, res, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(res);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showCenter(Context context,int resId){
        Toast toast = Toast.makeText(context, resId,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public static void showCenter(Context context,String res){
        Toast toast = Toast.makeText(context, res,Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
