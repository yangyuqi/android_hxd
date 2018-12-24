package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.my.AlertPasswordListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Md5Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class EditNewPwdActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_input_current_pwd)
    EditText edtInputCurrentPwd;
    @BindView(R.id.edt_input_new_pwd)
    EditText edtInputNewPwd;
    @BindView(R.id.tv_commit)
    TextView tvCommit;

    private UserRequester requester = new UserRequester();
    private AlertImp alertImp = new AlertImp();
    private class AlertImp extends DefaultRequestListener implements AlertPasswordListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,errorMsg);
        }

        @Override
        public void onSuccess() {

            SharedPreferencesUtil.remove(context,Constants.accessToken);
            SharedPreferencesUtil.remove(context,Constants.refreshToken);
            SharedPreferencesUtil.remove(context,Constants.userRole);
            SharedPreferencesUtil.remove(context,Constants.user_icon);
            GlobalDataUtil.removeObject(context,Constants.GLOBAL_DATA_KEY_USER_TYPE);
            SharedPreferencesUtil.remove(context, Constants.nickName);

            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_new_pwd_layout);
        ButterKnife.bind(this);
        tvTitle.setText("修改密码");
    }

    @OnClick({R.id.iv_left,R.id.tv_commit})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.tv_commit :
                if (edtInputCurrentPwd.getText().toString().length()!=0 || edtInputNewPwd.getText().toString().length()!=0){
                    if (edtInputCurrentPwd.getText().toString().length()>=6&&edtInputCurrentPwd.getText().toString().length()<=16&&edtInputNewPwd.getText().toString().length()>=6&&edtInputNewPwd.getText().toString().length()<=16) {
                        requester.alertPassword(context, getAccessToken(), Md5Util.md5(edtInputCurrentPwd.getText().toString()), Md5Util.md5(edtInputNewPwd.getText().toString()), alertImp);
                    }else {
                        ToastUtil.show(context,"请输入6-16位数字密码组合");
                    }
                }else {
                    ToastUtil.show(context,"密码不能为空");
                }
                break;
        }
    }

}
