package com.zhejiang.haoxiadan.business;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * 所有接口返回数据解析器的父类。
 * Created by KK on 2017/2/17.
 */
public abstract class AbsBaseParser {

    protected GsonDataParser mDataParser;
    protected WeakReference<OnBaseRequestListener> mOnBaseRequestListener;

    public AbsBaseParser(OnBaseRequestListener onBaseRequestListener) {
        this.mOnBaseRequestListener = new WeakReference<>(onBaseRequestListener);
        mDataParser = new GsonDataParser();
    }

    public void parse(String data) {
        if (mOnBaseRequestListener.get() == null) {
            return;
        }

            ResponseResult responseResult = null;
            try {
                responseResult = mDataParser.parseObject(data, ResponseResult.class);
            } catch (Exception e) {
                e.printStackTrace();
                parseError("9999");
            }

            switch (responseResult.getCode()) {
                case "200"://成功
                    try {
                        parseData(responseResult.getData() == null ? null : mDataParser.toDataStr(responseResult.getData()));
                    } catch (Exception e) {
                        e.printStackTrace();
                        parseError("9999");

                    }
                    break;
                case "9999"://系统错误
                    businessError(responseResult.getCode(), responseResult.getMsg(),mDataParser.toDataStr(responseResult.getData()));
                    break;
                default:
                    businessError(responseResult.getCode(), responseResult.getMsg(),mDataParser.toDataStr(responseResult.getData()));
                    break;
            }

    }

    protected abstract void parseData(@Nullable String data);

    protected void parseError(@Nullable String errorCode) {
        OnBaseRequestListener onBaseRequestListener = mOnBaseRequestListener.get();
        if(onBaseRequestListener != null){
            onBaseRequestListener.onBusinessFail(errorCode,"解析出错");
        }
    }

    protected void businessError(@Nullable String errorCode,@Nullable String msg,String data){
        OnBaseRequestListener onBaseRequestListener = mOnBaseRequestListener.get();
        if(onBaseRequestListener != null){
            onBaseRequestListener.onBusinessFail(errorCode,msg,data);
        }
    }
}
