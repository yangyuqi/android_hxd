package com.zhejiang.haoxiadan.business;

import com.zhejiang.haoxiadan.MyApplication;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

/**
 * Created by KK on 2017/2/20.
 */

public abstract class DefaultRequestListener implements OnBaseRequestListener {
    @Override
    public void onNetworkError(String errorMsg) {
        ToastUtil.show(MyApplication.getContext(),errorMsg);
        onRequestFail();
    }

    @Override
    public void onHttpFail(int statusCode, String errorMsg) {
        onRequestFail();
    }

    @Override
    public void onBusinessFail(String statusCode, String errorMsg) {
        onRequestFail();
    }

    @Override
    public void onBusinessFail(String statusCode, String errorMsg, String data) {
        onBusinessFail(statusCode,errorMsg);
    }

    protected abstract void onRequestFail();
}
