package com.zhejiang.haoxiadan.ui.activity.common;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.LoginListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.third.jiguang.chat.database.UserEntry;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.my.UserCenterActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.Md5Util;
import com.zhejiang.haoxiadan.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import rx.functions.Action1;


/**
 * Created by qiuweiyu on 2017/8/23.
 */

public class LoginActivity extends BaseFragmentActivity {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.edt_input_phone)
    EditText edtInputPhone;
    @BindView(R.id.edt_input_pwd)
    EditText edtInputPwd;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;

    private UserRequester requester = new UserRequester();
    private LoginImp imp = new LoginImp();
    private class LoginImp extends DefaultRequestListener implements LoginListener{

        @Override
        public void getUser(User user) {
            SharedPreferencesUtil.put(context,Constants.accessToken,user.getAccessToken());
            SharedPreferencesUtil.put(context,Constants.refreshToken ,user.getRefreshToken());
            SharedPreferencesUtil.put(context,Constants.userName ,user.getUserName());
            SharedPreferencesUtil.put(context,Constants.userRole ,user.getUserRole());
            GlobalDataUtil.putObject(context,Constants.GLOBAL_DATA_KEY_USER_TYPE ,User.USER_TYPE.BUYER,true);
            if(user.getNickName() == null){
                user.setNickName(user.getUserName());
            }
            SharedPreferencesUtil.put(context, Constants.nickName,user.getNickName());

            if(null == user.getHeadIcon()){
                SharedPreferencesUtil.put(context, Constants.user_icon,"");
            }else{
                SharedPreferencesUtil.put(context, Constants.user_icon,user.getHeadIcon());
            }

            goChatRegister(user.getUserName(),user.getNickName());
            //注册到极光
            JPushInterface.setAlias(LoginActivity.this,StringUtils.getPhotoImEi(context),null);
        }

        @Override
        protected void onRequestFail() {

        }

//        @Override
//        public void onBusinessFail(String statusCode, String errorMsg) {
//            super.onBusinessFail(statusCode, errorMsg);
//            ToastUtil.show(context,errorMsg);
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvRight.setText("注册");
    }

    @OnClick(R.id.iv_left)
    public void closeActivity(){
        finish();
    }

    @OnClick(R.id.tv_forget_pwd)
    public void goForgetView(){
        Intent intent = new Intent(context,RegisterAndFindPwdActivity.class);
        intent.putExtra("flag","1");
        startActivity(intent);
    }

    @OnClick(R.id.tv_right)
    public void goRegisterView(){
        Intent intent = new Intent(context,RegisterAndFindPwdActivity.class);
        intent.putExtra("flag","2");
        startActivity(intent);
    }

    @OnClick(R.id.tv_login)
    public void doLogin(){
        if (StringUtils.checkMobileNumber(edtInputPhone.getText().toString())) {
            if (!edtInputPwd.getText().toString().equals("")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    RxPermissions permissions = new RxPermissions(LoginActivity.this);
                    permissions.request(Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {
                            if (aBoolean) {
                                requester.login(context, edtInputPhone.getText().toString(), Md5Util.md5(edtInputPwd.getText().toString()), StringUtils.getPhotoImEi(context), "android", imp);
                            } else {
                                ToastUtil.show(context, "缺少权限");
                            }
                        }
                    });
                }else {
                    requester.login(context, edtInputPhone.getText().toString(), Md5Util.md5(edtInputPwd.getText().toString()), StringUtils.getPhotoImEi(context), "android", imp);
                }
            }else {
                ToastUtil.show(context,"密码不能为空");
            }
        }else {
            ToastUtil.show(context,getString(R.string.input_correct_phone));
        }
    }

    /****
     * 登录极光im
     * @param userId 手机号
     * @param nickName 昵称
     */
    private void goChatLogin(final String userId, final String nickName){
        JMessageClient.login(userId, userId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {//注册成功
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
                    UserInfo myUserInfo = JMessageClient.getMyInfo();
                    if (myUserInfo != null) {
                        if(null == nickName || "".equals(nickName)){
                            myUserInfo.setNickname(userId);
                        }else{
                            myUserInfo.setNickname(nickName);
                        }
                    }

                }

                //跳转首页
                String userType2 = (String) SharedPreferencesUtil.get(context, Constants.userRole, "");
                if(Constants.USER_CUSTOMER.equals(userType2)){
                EventBus.getDefault().post(Event.CHAT_CLOSE_ALL);
                    finish();
                }else {
                    Intent intent = new Intent(context, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    finish();
                }
            }

        });


    }


    /***
     * 注册极光im
     * @param userId
     */
    private void goChatRegister(final String userId,final String nickName){
        JMessageClient.register(userId, userId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setRegisterUsername(userId);
                    JMessageClient.login(userId, userId, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {//注册极光成功
                                Constants.registerOrLogin = 1;
                                String username = JMessageClient.getMyInfo().getUserName();
                                String appKey = JMessageClient.getMyInfo().getAppKey();
                                UserEntry user = UserEntry.getUser(username, appKey);
                                if (null == user) {
                                    user = new UserEntry(username, appKey);
                                    user.save();
                                }

                                UserInfo myUserInfo = JMessageClient.getMyInfo();
                                if (myUserInfo != null) {
                                    myUserInfo.setNickname(userId);
                                }
                                goChatLogin(userId,nickName);
                            }else{
                                goChatLogin(userId,nickName);
                            }
                        }
                    });
                }else{
                    goChatLogin(userId,nickName);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        String username = getIntent().getStringExtra("");
        if(username == null){
            username = (String) SharedPreferencesUtil.get(context,Constants.userName,"");
        }
        edtInputPhone.setText(username);
    }
}
