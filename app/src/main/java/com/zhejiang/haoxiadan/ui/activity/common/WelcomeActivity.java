package com.zhejiang.haoxiadan.ui.activity.common;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.hot.WelcomePhotoListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.business.request.my.VersionManageListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.VersionManageData;
import com.zhejiang.haoxiadan.third.apkupdate.DownloadManager;
import com.zhejiang.haoxiadan.third.jiguang.chat.database.UserEntry;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.view.DownApkDialog;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.ActivityHelper;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.PublicUtils;
import com.zhejiang.haoxiadan.util.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import rx.functions.Action1;

public class WelcomeActivity extends BaseActivity {

    private boolean isUsed;//是否使用过本应用

    /**
     * 页面停留时间
     */
    private int WELCOME_TIME = 3000;

    private final int MSG_PRELOAD_TIMEOUT = 0x000123;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_PRELOAD_TIMEOUT:
                break;
            }
        }
    };

    private UserRequester requester;
    private ImageView imageView ;
    private TipDialog tipDialog;
    private DownApkDialog downApkDialog;
    private VersionManageData mVersionManageData;
    private DownloadManager downloadManager;
    private VersionManageListenerImpl versionImp;

    public class VersionManageListenerImpl extends DefaultRequestListener implements VersionManageListener {

        @Override
        protected void onRequestFail() {
            handler.postDelayed(new WelcomeRunnable(),WELCOME_TIME);
        }

        @Override
        public void onSuccess(VersionManageData versionManageData) {

            if(versionManageData != null){
                mVersionManageData = versionManageData;
                doUpdate();
            }else{
                handler.postDelayed(new WelcomeRunnable(),WELCOME_TIME);
            }
        }
    }

    private void doUpdate(){
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(false);
        tipDialog.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        isUsed = (boolean) SharedPreferencesUtil.get(this, Constants.SHAREDPREFERENCES_KEY_IS_USED,false);
        imageView = (ImageView) findViewById(R.id.iv_welcome);

        requester = new UserRequester();
        versionImp = new VersionManageListenerImpl();
        downloadManager = new DownloadManager(this);

        tipDialog = new TipDialog(this,getString(R.string.tip),getString(R.string.tip_update_newest_version),new TipDialog.OnTipDialogListener(){
            @Override
            public void onPositiveClick() {
                tipDialog.dismiss();
                downloadManager.download(mVersionManageData.getVersionLinkUrl());
                if(mVersionManageData.getIsForceUpdate() == 1) {//强制更新
                    downApkDialog.show();
//                    finish();
                }else{
                    handler.postDelayed(new WelcomeRunnable(),WELCOME_TIME);
                }
            }

            @Override
            public void onNegativeClick() {
                tipDialog.dismiss();
                if(mVersionManageData.getIsForceUpdate() == 1) {//强制更新
                    finish();
                }else{
                    handler.postDelayed(new WelcomeRunnable(),WELCOME_TIME);
                }
            }
        });
        tipDialog.setAutoDismiss(false);

        downApkDialog = new DownApkDialog(WelcomeActivity.this);

        requester.versionManage(this,"android", PublicUtils.getAppVersionName(this),versionImp);
    }

    class WelcomeRunnable implements Runnable {
        @Override
        public void run() {
            if(!"".equals(SharedPreferencesUtil.get(WelcomeActivity.this,Constants.refreshToken,""))) {
                //注册到极光
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    RxPermissions permissions = new RxPermissions(WelcomeActivity.this);
                    permissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                JPushInterface.setAlias(WelcomeActivity.this, StringUtils.getPhotoImEi(WelcomeActivity.this), null);
                            } else {
                                ToastUtil.show(WelcomeActivity.this, "缺少权限");
                            }
                        }
                    });
                }
            }

            if(Constants.USER_CUSTOMER.equals((String) SharedPreferencesUtil.get(WelcomeActivity.this, Constants.userRole, ""))){
                goChatLogin((String) SharedPreferencesUtil.get(WelcomeActivity.this, Constants.userName, ""));
            }else{
                gotoAdver();
            }
            finish();
        }
    }

    private void goChatLogin(final String userId){
        JMessageClient.login(userId, userId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw(userId);
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    gotoAdver();
                }else{
                    gotoAdver();
                }
            }
        });
    }

    private void gotoAdver(){
        Intent intent = new Intent(WelcomeActivity.this, AdvertisementActivity.class);
        intent.putExtra("isFirstUse",!isUsed);
        startActivity(intent);
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
