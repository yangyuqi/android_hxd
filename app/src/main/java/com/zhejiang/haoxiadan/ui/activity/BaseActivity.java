package com.zhejiang.haoxiadan.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.MyApplication;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.util.Constants;

import java.util.LinkedList;

public class BaseActivity extends Activity {

    public Gson gson ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new Gson();
    }

    /***
     * 获取accesstoken
     * @return
     */
    public String getAccessToken(){
        return (String) SharedPreferencesUtil.get(BaseActivity.this,Constants.accessToken,"");
    }

    /***
     * 获取用户名
     * @return
     */
    public String getUserName(){
        return (String) GlobalDataUtil.getObject(BaseActivity.this, Constants.GLOBAL_DATA_KEY_TRUE_NAME);
    }
    /***
     * 获取邮箱
     */
    public String getUserEmail(){
        return (String) GlobalDataUtil.getObject(BaseActivity.this, Constants.GLOBAL_DATA_KEY_EMAIL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtil.clearImageCache();
    }


    /**
     * 添加Activity到维护的堆栈
     */
    public void addActivity(Activity activity) {
        MyApplication.appManager.addActivity(activity);
    }

    /**
     * 清空维护的堆栈
     */
    public void cleanActivity() {
        MyApplication.appManager.finishAllActivity();
    }
}
