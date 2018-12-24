package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Button;

import com.zhejiang.haoxiadan.R;

/**
 * Created by yangyuqi on 17-7-25.
 */

public class MyCountDownTimer extends CountDownTimer {

    private Button btn ;
    private Context context ;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(Button button , long millisInFuture, long countDownInterval ,Context mContext) {
        super(millisInFuture, countDownInterval);
        btn = button ;
        context = mContext;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        btn.setClickable(false);
        btn.setText(millisUntilFinished/1000+"秒后重新获取");
        btn.setTextColor(context.getResources().getColor(R.color.gradient_pink_light));
        btn.setBackgroundResource(R.drawable.unenable_btn_bg);
    }

    @Override
    public void onFinish() {
        btn.setText("获取验证码");
        //设置可点击
        btn.setClickable(true);
        btn.setTextColor(context.getResources().getColor(R.color.main_red));
        btn.setBackgroundResource(R.drawable.send_code_bg);
    }
}
