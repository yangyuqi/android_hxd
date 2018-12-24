package com.zhejiang.haoxiadan.ui.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.TimeUtils;

/**
 * Created by KK on 2017/9/12.
 */
public class CountDownTextView extends TextView{
    private boolean mAttached;

    private CartGoods cartGoods;

    public CountDownTextView(Context context) {

        this(context, null);

    }

    public CountDownTextView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);

    }

    public CountDownTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
    }

//注册广播

    @Override

    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        if (!mAttached) {

            mAttached = true;

            IntentFilter filter = new IntentFilter();

            filter.addAction(Constants.BROADCAST_RECEIVER_ACTION_COUNTDOWN);

            getContext().registerReceiver(mIntentReceiver, filter, null, getHandler());

        }

    }

    //销毁广播
    @Override

    protected void onDetachedFromWindow() {

        super.onDetachedFromWindow();

        if (mAttached) {

            getContext().unregisterReceiver(mIntentReceiver);

            mAttached = false;

        }

    }

    //创建广播
    private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Constants.BROADCAST_RECEIVER_ACTION_COUNTDOWN.equals(action)) {
                if(cartGoods!=null){
                    setText(TimeUtils.getTimeDiff(cartGoods.getTogetherEndMillis()));
                    cartGoods.setTogetherEndMillis(cartGoods.getTogetherEndMillis()-1000);
                }
            }

        }

    };

    public void setCartGoods(CartGoods cartGoods) {
        this.cartGoods = cartGoods;
    }
}
