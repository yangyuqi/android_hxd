package com.zhejiang.haoxiadan.ui;

import android.view.View;
import android.widget.AdapterView;

import java.util.Calendar;

/**
 * Created by KK on 2016/10/31.
 */
public abstract class NoDoubleItemClickListener implements AdapterView.OnItemClickListener {
    private final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleItemClick(parent,view,position,id);
        }
    }
    public abstract void onNoDoubleItemClick(AdapterView<?> parent, View view, int position, long id);
}
