package com.zhejiang.haoxiadan.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;

/**
 * 强制更新时显示的loading框
 */
public class DownApkDialog extends Dialog {

    private long FLIP_ANIMATION_DURATION = 1000;
    private float rotateAngle = 359;

    public DownApkDialog(Context context) {
        super(context, R.style.style_dialog_tip);
        setContentView(R.layout.dialog_down_apk);

        setCancelable(false);

        RotateAnimation mRotateAnimation = new RotateAnimation(0, rotateAngle , Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setDuration(FLIP_ANIMATION_DURATION );
        mRotateAnimation.setFillAfter(true);
        mRotateAnimation.setRepeatMode(Animation.RESTART);
        mRotateAnimation.setRepeatCount(Animation.INFINITE);

        ProgressBar progressBar = (ProgressBar)findViewById(R.id.progress_loading);
        progressBar.startAnimation(mRotateAnimation);
    }

}
