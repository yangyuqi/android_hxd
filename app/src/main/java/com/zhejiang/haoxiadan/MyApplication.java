package com.zhejiang.haoxiadan;

import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.zhejiang.haoxiadan.manager.MyCrashHandler;
import com.zhejiang.haoxiadan.third.jiguang.chat.entity.NotificationClickEventReceiver;
import com.zhejiang.haoxiadan.third.jiguang.chat.pickerimage.utils.StorageUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.imagepicker.GlideImageLoader;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.imagepicker.ImagePicker;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.imagepicker.view.CropImageView;
import com.zhejiang.haoxiadan.util.AppManager;
import com.zhejiang.haoxiadan.util.Constants;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by KK on 2017/2/17.
 */

public class MyApplication extends com.activeandroid.app.Application {

    public static Context context;

    public static AppManager appManager ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if(!Constants.isDebug){
            //捕获程序崩溃
            Thread.setDefaultUncaughtExceptionHandler(new MyCrashHandler(this));
        }

        appManager = AppManager.getAppManager();

        initPush();

        StorageUtil.init(context, null);

        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        SharePreferenceManager.init(getApplicationContext(), Constants.JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
        initImagePicker();
    }

    public static Context getContext() {
        return context;
    }

    //初始化推送
    private void initPush(){
        JPushInterface.setDebugMode(Constants.isDebug);
        JPushInterface.init(this);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(Constants.maxImgCount);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
