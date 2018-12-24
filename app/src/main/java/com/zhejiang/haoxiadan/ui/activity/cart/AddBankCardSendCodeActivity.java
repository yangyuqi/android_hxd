package com.zhejiang.haoxiadan.ui.activity.cart;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.SendCodeListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.business.request.pay.BankInfoSaveListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.VerifySBindingSMSListener;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.MyCountDownTimer;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class AddBankCardSendCodeActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView enterCodeTipTv;
    private EditText codeEt;
    private Button btnSendCode;
    private MyCountDownTimer timer;

    private String userBankId;
    private String mobile;
    private Map<String, Object> params;

    private TextView verifyInfoTv;

    private PayRequester payRequester;
    private VerifySBindingSMSListenerImpl verifySBindingSMSListener;
    private class VerifySBindingSMSListenerImpl extends DefaultRequestListener implements VerifySBindingSMSListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onVerifySBindingSMSSuccess() {
            ToastUtil.show(getApplicationContext(),"绑定成功");
            cleanActivity();
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(getBaseContext(),errorMsg);
        }
    }
    private BankInfoSaveListenerImpl bankInfoSaveListener;
    private class BankInfoSaveListenerImpl extends DefaultRequestListener implements BankInfoSaveListener {

        @Override
        public void onBankInfoSaveSuccess(String userBankId) {
            timer.start();
        }

        @Override
        protected void onRequestFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card_send_code);

        addActivity(this);

        userBankId = getIntent().getStringExtra("userBankId");
        mobile = getIntent().getStringExtra("mobile");
        params = (HashMap<String, Object>)getIntent().getSerializableExtra("params");

        payRequester = new PayRequester();
        verifySBindingSMSListener = new VerifySBindingSMSListenerImpl();
        bankInfoSaveListener = new BankInfoSaveListenerImpl();

        initView();
        initEvent();
        initData();
    }
    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        enterCodeTipTv = (TextView)findViewById(R.id.tv_enter_code_tip);
        codeEt = (EditText) findViewById(R.id.et_code);
        btnSendCode = (Button)findViewById(R.id.btn_send_code_message);
        verifyInfoTv = (TextView)findViewById(R.id.tv_verify_info);

        timer = new MyCountDownTimer(btnSendCode, 60000, 1000, this);
        timer.start();
    }

    private void initEvent(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        btnSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            payRequester.bankInfoSave(AddBankCardSendCodeActivity.this,params,bankInfoSaveListener);
            }
        });
        verifyInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codeEt.getText().toString().trim().equals("")){
                    ToastUtil.show(AddBankCardSendCodeActivity.this,R.string.tip_please_enter_code);
                    return;
                }
                payRequester.verifySBindingSMS(AddBankCardSendCodeActivity.this,getAccessToken(),userBankId,codeEt.getText().toString(),verifySBindingSMSListener);
            }
        });
    }

    private void initData(){
        enterCodeTipTv.setText(String.format(getString(R.string.label_enter_phone_receive_code),mobile));
    }
}
