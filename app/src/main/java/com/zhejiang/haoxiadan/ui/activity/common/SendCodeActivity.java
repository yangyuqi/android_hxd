package com.zhejiang.haoxiadan.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ComListener;
import com.zhejiang.haoxiadan.business.request.my.RegisterListener;
import com.zhejiang.haoxiadan.business.request.my.SendCodeListener;
import com.zhejiang.haoxiadan.business.request.my.UserAgreementListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.third.jiguang.chat.database.UserEntry;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.MyCountDownTimer;

import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.Md5Util;
import com.zhejiang.haoxiadan.util.StringUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by qiuweiyu on 2017/8/23.
 */

public class SendCodeActivity extends BaseFragmentActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_xie_yi)
    TextView tvXieYi;
    @BindView(R.id.edt_input_code)
    EditText edtInputCode;
    @BindView(R.id.edt_input_pwd)
    EditText edtInputPwd;
    @BindView(R.id.tv_post)
    TextView tvPost;
    MyCountDownTimer timer;
    @BindView(R.id.btn_send_code_message)
    Button btnSendCode;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.iv_show_pwd)
    CheckBox ivShowPwd;
    @BindView(R.id.ll_show_xieyi)
    LinearLayout llShowXieyi;

    private String flag;//1忘记密码，2注册
    private String code ,mobile;

    private UserRequester requester = new UserRequester();
    private CodeImp imp = new CodeImp();
    private RegisterImp registerImp = new RegisterImp();
    private FindImp findImp = new FindImp();
    private class CodeImp extends DefaultRequestListener implements SendCodeListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getCode() {

        }

        @Override
        public void getError(String msg) {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,errorMsg);
        }
    }

    private class FindImp extends DefaultRequestListener implements ComListener{

        @Override
        public void onResponse() {
            EventBus.getDefault().post(11);
            startActivity(new Intent(context,LoginActivity.class));
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

    private class RegisterImp extends DefaultRequestListener implements RegisterListener{

        @Override
        public void onResponse() {
            goChatRegister(mobile);
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,errorMsg);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    /***
     * 注册极光im
     * @param userId
     */
    private void goChatRegister(final String userId){
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
                            }
                        }
                    });
                }

            }
        });
        //成功后跳转登录
        EventBus.getDefault().post(11);
        startActivity(new Intent(context,LoginActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_code_layout);
        ButterKnife.bind(this);
        flag = getIntent().getStringExtra("flag");
        mobile = getIntent().getStringExtra("mobile");
        tvPhone.setText(mobile);
        timer = new MyCountDownTimer(btnSendCode, 60000, 1000, this);
        initView();
        requester.sendCode(context,mobile,imp);
        timer.start();
    }

    private void initView() {
        if ("1".equals(flag)) {
            tvTitle.setText("找回密码");
            llShowXieyi.setVisibility(View.GONE);
        } else if ("2".equals(flag)) {
            tvTitle.setText("注册");
            llShowXieyi.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_left)
    public void close() {
        finish();
    }

    @OnClick(R.id.btn_send_code_message)
    public void sendCode() {
        requester.sendCode(context,mobile,imp);
        timer.start();
    }

    @OnClick(R.id.tv_post)
    public void post() {
        if (!edtInputCode.getText().toString().equals("")){
            if (edtInputPwd.getText().toString().length()>=6&&edtInputPwd.getText().toString().length()<=16) {
                if ("2".equals(flag)) {
                    requester.register(context, mobile, Md5Util.md5(edtInputPwd.getText().toString()), edtInputCode.getText().toString().trim(), registerImp);
                } else {
                    requester.findPwd(context, mobile, edtInputCode.getText().toString().trim(), Md5Util.md5(edtInputPwd.getText().toString()), findImp);
                }
            }else {
                ToastUtil.show(context,"请输入6-16字母数字字符组合密码");
            }
        }else {
            ToastUtil.show(context,"验证码不能为空");
        }


    }

    @OnClick(R.id.tv_xie_yi)
    public void goH5() {
        requester.userAgreement(context,mentImp);
    }

    @OnCheckedChanged(R.id.iv_show_pwd)
    public void onChecked(boolean isChecked) {
        if (isChecked) {
            edtInputPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            edtInputPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private UserAgreeMentImp mentImp = new UserAgreeMentImp();
    private class UserAgreeMentImp extends DefaultRequestListener implements UserAgreementListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onArticleSuccess(String str) {
            Intent intent = new Intent(context, H5Activity.class);
            intent.putExtra("title","用户注册协议");
            intent.putExtra("content",str);
            startActivity(intent);
        }
    }


}

