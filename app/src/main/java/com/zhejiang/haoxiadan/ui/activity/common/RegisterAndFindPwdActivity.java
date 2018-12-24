package com.zhejiang.haoxiadan.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ComListener;
import com.zhejiang.haoxiadan.business.request.my.UserAgreementListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/23.
 */

public class RegisterAndFindPwdActivity extends BaseFragmentActivity {
    @BindView(R.id.edt_input_phone)
    EditText edt_phone;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_xie_yi)
    TextView tvXieyi;
    @BindView(R.id.ll_show_xieyi)
    LinearLayout llShowXieyi;

    private String flag;

    private UserRequester requester = new UserRequester();
    private IsExitUserImp imp = new IsExitUserImp();
    private class IsExitUserImp extends DefaultRequestListener implements ComListener {

       @Override
       public void onResponse() {//用户名不存在
           if (flag.equals("2")) {//注册
               Intent intent = new Intent(context, SendCodeActivity.class);
               intent.putExtra("mobile", edt_phone.getText().toString());
               intent.putExtra("flag", "2");
               startActivity(intent);
           }else if (flag.equals("1")){//找回密码
               ToastUtil.show(context,"用户名不存在");
           }
       }

       @Override
       protected void onRequestFail() {

       }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            if (statusCode.equals("1011")){//用户已存在
                if (flag.equals("2")) {//注册
                    ToastUtil.show(context,errorMsg);
                    //改为去登录
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.putExtra("username",edt_phone.getText().toString());
                    startActivity(intent);
                }else if (flag.equals("1")){//找回密码
                    Intent intent = new Intent(context, SendCodeActivity.class);
                    intent.putExtra("flag", "1");
                    intent.putExtra("mobile", edt_phone.getText().toString());
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_and_find_pwd_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        flag = getIntent().getStringExtra("flag");
        if (flag.equals("1")){
            llShowXieyi.setVisibility(View.GONE);
            tvTitle.setText("找回密码");
        }else if (flag.equals("2")){
            llShowXieyi.setVisibility(View.VISIBLE);
            tvTitle.setText("注册");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.tv_login)
    public void next() {
        if("".equals(edt_phone.getText().toString().trim())){
            ToastUtil.show(context, getString(R.string.tip_mobile_number_cannot_empty));
            return;
        }
        if (StringUtils.checkMobileNumber(edt_phone.getText().toString())) {
            requester.userIsExist(context, edt_phone.getText().toString(), imp);
        }else {
            ToastUtil.show(context, getString(R.string.input_correct_phone));
        }
    }

    @OnClick(R.id.tv_xie_yi)
    public void goH5() {
        requester.userAgreement(context,mentImp);
    }

    private UserAgreeMentImp mentImp = new UserAgreeMentImp();
    private class UserAgreeMentImp extends DefaultRequestListener implements UserAgreementListener{

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

    @OnClick(R.id.iv_left)
    public void close(){
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Integer data){
        if (data==11){
            finish();
        }
    }
}
