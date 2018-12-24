package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.EditUnickNameListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class ChangeNewNameActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private String name ;
    private EditUnickImp imp = new EditUnickImp();
    private UserRequester userRequester = new UserRequester();
    private class EditUnickImp extends DefaultRequestListener implements EditUnickNameListener{

        @Override
        public void onSuccess() {
            SharedPreferencesUtil.put(context, Constants.nickName,edtName.getText().toString());
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_new_name_layout);
        ButterKnife.bind(this);
        tvTitle.setText("修改昵称");
        name = getIntent().getStringExtra("nickName");
        edtName.setText(name);
    }

    @OnClick({R.id.iv_left,R.id.iv_delete,R.id.tv_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.iv_delete :
                edtName.setText("");
                break;
            case R.id.tv_login:
                if (!edtName.getText().toString().equals("")&&edtName.getText().toString().length()>=4&&edtName.getText().toString().length()<=25) {
                    userRequester.editUnickName(context, getAccessToken(), edtName.getText().toString(), imp);
                }else {
                    ToastUtil.show(context,"昵称限制在4-25个字符");
                }
                break;
        }
    }
}
