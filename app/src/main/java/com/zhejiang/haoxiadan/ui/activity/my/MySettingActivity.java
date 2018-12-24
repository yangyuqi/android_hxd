package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ArticleListener;
import com.zhejiang.haoxiadan.business.request.my.LoginOutListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.business.request.my.VersionManageListener;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.VersionManageData;
import com.zhejiang.haoxiadan.third.apkupdate.DownloadManager;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.H5Activity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.common.SplashActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.FileCacheUtils;
import com.zhejiang.haoxiadan.util.PublicUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Created by qiuweiyu on 2017/8/26.
 */

public class MySettingActivity extends BaseFragmentActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_resolve_pwd)
    RelativeLayout rlResolvePwd;
    @BindView(R.id.rl_my_discuss)
    RelativeLayout rlMyDiscuss;
    @BindView(R.id.tv_get_memery)
    TextView tvGetMemery;
    @BindView(R.id.rl_clear_memery)
    RelativeLayout rlClearMemery;
    @BindView(R.id.rl_advice)
    RelativeLayout rlAdvice;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.tv_one)
    TextView tvOne;
    @BindView(R.id.tv_get_version)
    TextView tvGetVersion;
    @BindView(R.id.rl_check_version)
    RelativeLayout rlCheckVersion;
    @BindView(R.id.tv_loginout)
    TextView tvLoginout;


    private TipDialog tipDialog;
    private VersionManageData mVersionManageData;
    private DownloadManager downloadManager;

    private UserRequester requester = new UserRequester();

    private LoginOutImp imp = new LoginOutImp();
    private class LoginOutImp extends DefaultRequestListener implements LoginOutListener{

        @Override
        public void loginOut() {
            EventBus.getDefault().post(Event.LOGIN_OUT);
            startActivity(new Intent(context, LoginActivity.class));
            SharedPreferencesUtil.remove(context,Constants.accessToken);
            SharedPreferencesUtil.remove(context,Constants.refreshToken);
            SharedPreferencesUtil.remove(context,Constants.userRole);
            SharedPreferencesUtil.remove(context,Constants.user_icon);
            GlobalDataUtil.removeObject(context,Constants.GLOBAL_DATA_KEY_USER_TYPE);
            SharedPreferencesUtil.remove(context, Constants.nickName);
            finish();

        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,errorMsg);
        }
    }

    //极光退出登录
    public void goLogout() {
        //清除推送的别名
        JPushInterface.setAlias(this,"",null);

        UserInfo info = JMessageClient.getMyInfo();
        if (null != info) {
            SharePreferenceManager.setCachedUsername(info.getUserName());
            if (info.getAvatarFile() != null) {
                SharePreferenceManager.setCachedAvatarPath(info.getAvatarFile().getAbsolutePath());
            }
            JMessageClient.logout();
        }
    }

    private ArtImp artImp = new ArtImp();
    private class ArtImp extends DefaultRequestListener implements ArticleListener {

        @Override
        public void onArticleSuccess(String str) {
            Intent intent = new Intent(context, H5Activity.class);
            intent.putExtra("title","关于我们");
            intent.putExtra("content",str);
            startActivity(intent);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_setting_layout);
        ButterKnife.bind(this);

        downloadManager = new DownloadManager(this);

        initView();

    }

    private void initView() {
        tvTitle.setText("设置");
        tvGetVersion.setText("(当前版本v"+ PublicUtils.getAppVersionName(context)+")");
        try {
//            tvGetMemery.setText(FileCacheUtils.getCacheSize(context.getCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();

        }


        tipDialog = new TipDialog(this,getString(R.string.tip),getString(R.string.tip_update_newest_version),new TipDialog.OnTipDialogListener(){
            @Override
            public void onPositiveClick() {
                tipDialog.dismiss();
                downloadManager.download(mVersionManageData.getVersionLinkUrl());
            }

            @Override
            public void onNegativeClick() {
                if(mVersionManageData.getIsForceUpdate() == 1) {//强制更新

                }else{
                    tipDialog.dismiss();
                }
            }
        });
        tipDialog.setAutoDismiss(false);
    }

    @OnClick({R.id.iv_left,R.id.rl_resolve_pwd,R.id.rl_my_discuss,R.id.rl_clear_memery,R.id.rl_advice,R.id.rl_about_us,R.id.rl_check_version,R.id.tv_loginout})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.rl_resolve_pwd :
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, EditNewPwdActivity.class));
                }else {
                   startActivity(new Intent(context,LoginActivity.class));
                    finish();
                }
                break;
            case R.id.rl_my_discuss :
                startActivity(new Intent(context,DiscussAppActivity.class));
                break;
            case R.id.rl_clear_memery :
                final DeleteDialog deleteDialog = new DeleteDialog(context,"提示","清除临时缓存文件吗","确定");
                deleteDialog.show();
                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (isdelete){
                            ToastUtil.show(context,"缓存清除成功");
                            Glide.get(context).clearMemory();
                            new Thread(){
                                @Override
                                public void run() {
                                    Glide.get(context).clearDiskCache();
                                }
                            }.start();
                            deleteDialog.dismiss();
                            try {
//                                tvGetMemery.setText(FileCacheUtils.getCacheSize(context.getCacheDir()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                break;
            case R.id.rl_advice :
                startActivity(new Intent(context,AdviceActivity.class));
                break;
            case R.id.rl_about_us :
                requester.article(context,"关于我们",artImp);
                break;
            case R.id.rl_check_version :
                requester.versionManage(context,"android",PublicUtils.getAppVersionName(context),versionImp);
                break;
            case R.id.tv_loginout :
                final DeleteDialog dialog = new DeleteDialog(context,"提示","是否退出登录","确定");
                dialog.show();
                dialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (isdelete){
                            dialog.dismiss();
                            goLogout();
                            requester.loginOut(context,getAccessToken(),imp);
                        }
                    }
                });
                break;
        }
    }

    private VersionImp versionImp = new VersionImp();

    public class VersionImp extends DefaultRequestListener implements VersionManageListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,getString(R.string.tip_newest_version));
        }

        @Override
        public void onSuccess(VersionManageData versionManageData) {
            if(versionManageData != null){
                mVersionManageData = versionManageData;
                doUpdate();
            }else{
                ToastUtil.show(context,getString(R.string.tip_newest_version));
            }
        }
    }

    private void doUpdate(){
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(false);
        tipDialog.setCancelable(false);
    }
}
