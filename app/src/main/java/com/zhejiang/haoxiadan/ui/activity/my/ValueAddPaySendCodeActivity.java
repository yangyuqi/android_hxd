package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.SendCodeListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.business.request.pay.GetValueAddPayCodeListener;
import com.zhejiang.haoxiadan.business.request.pay.OrderPayListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.ValueAddPayListener;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.OrderPaySuccessActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.MyCountDownTimer;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

public class ValueAddPaySendCodeActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView enterCodeTipTv;
    private EditText codeEt;
    private Button btnSendCode;
    private MyCountDownTimer timer;

    private String mobile;

    private String applyId;
    private String feeId;
    private boolean isFirst;
    private String bankNo;

    private TextView verifyInfoTv;

    private PayRequester payRequester;
    private ValueAddPayListenerImpl valueAddPayListener;
    private class ValueAddPayListenerImpl extends DefaultRequestListener implements ValueAddPayListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onValueAddPaySuccess() {
            LoadingDialog.dismissDialog();
            Intent intent = new Intent(ValueAddPaySendCodeActivity.this,ValueAddPaySuccessActivity.class);
            startActivity(intent);

            cleanActivity();
        }
    }
    private GetValueAddPayCodeListenerImpl getValueAddPayCodeListener;
    private class GetValueAddPayCodeListenerImpl extends DefaultRequestListener implements GetValueAddPayCodeListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetValueAddPayCodeSuccess() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_add_pay_send_code);

        addActivity(this);

        mobile = getIntent().getStringExtra("mobile");
        applyId = getIntent().getStringExtra("applyId");
        feeId = getIntent().getStringExtra("feeId");
        isFirst = getIntent().getBooleanExtra("isFirst",false);
        bankNo = getIntent().getStringExtra("bankNo");

        payRequester = new PayRequester();
        getValueAddPayCodeListener = new GetValueAddPayCodeListenerImpl();
        valueAddPayListener = new ValueAddPayListenerImpl();

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
                sendCode();
            }
        });
        verifyInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codeEt.getText().toString().trim().equals("")){
                    ToastUtil.show(ValueAddPaySendCodeActivity.this,R.string.tip_please_enter_code);
                    return;
                }
                payRequester.valueAddPay(ValueAddPaySendCodeActivity.this,getAccessToken(),applyId,feeId,bankNo,isFirst,codeEt.getText().toString(),valueAddPayListener);
                LoadingDialog.getInstance(ValueAddPaySendCodeActivity.this).show();
            }
        });
    }

    private void initData(){
        enterCodeTipTv.setText(String.format(getString(R.string.label_enter_phone_receive_code),mobile));

        sendCode();
    }

    private void sendCode(){
        payRequester.getValueAddPayCode(ValueAddPaySendCodeActivity.this,getAccessToken(),applyId,feeId,bankNo,isFirst,getValueAddPayCodeListener);
        timer.start();
    }
}
