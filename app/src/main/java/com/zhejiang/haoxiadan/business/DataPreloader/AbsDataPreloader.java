package com.zhejiang.haoxiadan.business.DataPreloader;

import android.content.Context;

/**
 * Created by KK on 2017/3/30.
 */

public abstract class AbsDataPreloader {

    protected Context context;
    protected PreloaderListener preloaderListener;

    public AbsDataPreloader(Context context,PreloaderListener preloaderListener){
        this.context = context;
        this.preloaderListener = preloaderListener;
    }

    abstract public void load();

    public void setPreloaderListener(PreloaderListener preloaderListener) {
        this.preloaderListener = preloaderListener;
    }
}
