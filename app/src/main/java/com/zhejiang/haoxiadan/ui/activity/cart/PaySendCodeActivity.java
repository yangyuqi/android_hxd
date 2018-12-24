package com.zhejiang.haoxiadan.ui.activity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.SendCodeListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.business.request.pay.CollQueryOrderStatusListener;
import com.zhejiang.haoxiadan.business.request.pay.GetOrderPayCodeListener;
import com.zhejiang.haoxiadan.business.request.pay.OrderPayListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.MyCountDownTimer;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class PaySendCodeActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView enterCodeTipTv;
    private EditText codeEt;
    private Button btnSendCode;
    private MyCountDownTimer timer;

    private String mobile;

    private String mainId;
    private String bankNo;

    private TextView verifyInfoTv;

    private PayRequester payRequester;
    private OrderPayListenerImpl orderPayListener;
    private class OrderPayListenerImpl extends DefaultRequestListener implements OrderPayListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg,String data) {
            if(data != null){

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String verifyStatus = jsonObject.getString("verifyStatus");
                    if("10".equals(verifyStatus)){
                        payRequester.collQueryOrderStatus(PaySendCodeActivity.this,getAccessToken(),mainId,collQueryOrderStatusListener);
                    }else{
                        LoadingDialog.dismissDialog();
                        ToastUtil.show(PaySendCodeActivity.this,errorMsg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LoadingDialog.dismissDialog();
                    ToastUtil.show(PaySendCodeActivity.this,errorMsg);
                }
            }else{
                LoadingDialog.dismissDialog();
                ToastUtil.show(PaySendCodeActivity.this,errorMsg);
            }

        }

        @Override
        public void onOrderPaySuccess(String price,String payWay) {
            LoadingDialog.dismissDialog();
            Intent intent = new Intent(PaySendCodeActivity.this,OrderPaySuccessActivity.class);
            intent.putExtra("price",price);
            intent.putExtra("payWay",payWay);
            startActivity(intent);

            cleanActivity();
        }
    }
    private CollQueryOrderStatusListenerImpl collQueryOrderStatusListener;
    private class CollQueryOrderStatusListenerImpl extends DefaultRequestListener implements CollQueryOrderStatusListener {

        @Override
        public void onCollQueryOrderStatusSuccess(String price, String payWay) {
            LoadingDialog.dismissDialog();
            Intent intent = new Intent(PaySendCodeActivity.this,OrderPaySuccessActivity.class);
            intent.putExtra("price",price);
            intent.putExtra("payWay",payWay);
            startActivity(intent);

            cleanActivity();
        }

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            LoadingDialog.dismissDialog();
            ToastUtil.show(PaySendCodeActivity.this,errorMsg);
        }
    }
    private GetOrderPayCodeListenerImpl getOrderPayCodeListener;
    private class GetOrderPayCodeListenerImpl extends DefaultRequestListener implements GetOrderPayCodeListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetOrderPayCodeSuccess(String code) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_send_code);

        addActivity(this);

        mobile = getIntent().getStringExtra("mobile");
        mainId = getIntent().getStringExtra("mainId");
        bankNo = getIntent().getStringExtra("bankNo");

        payRequester = new PayRequester();
        getOrderPayCodeListener = new GetOrderPayCodeListenerImpl();
        orderPayListener = new OrderPayListenerImpl();
        collQueryOrderStatusListener = new CollQueryOrderStatusListenerImpl();

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
                    ToastUtil.show(PaySendCodeActivity.this,R.string.tip_please_enter_code);
                    return;
                }
                LoadingDialog.getInstance(PaySendCodeActivity.this).show();
                payRequester.orderPay(PaySendCodeActivity.this,getAccessToken(),mainId,bankNo,codeEt.getText().toString(),orderPayListener);
            }
        });
    }

    private void initData(){
        enterCodeTipTv.setText(String.format(getString(R.string.label_enter_phone_receive_code),mobile));

        timer.start();
    }

    private void sendCode(){
        payRequester.getOrderPayCode(PaySendCodeActivity.this,getAccessToken(),mainId,bankNo,getOrderPayCodeListener);
        timer.start();
    }

}
