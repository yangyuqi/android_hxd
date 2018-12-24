package com.zhejiang.haoxiadan.ui.activity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.pay.BankInfoSaveListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.QueryBankListByUserListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBankCardActivity extends BaseActivity {

    private BankCard.CARD_TYPE type;
    private String bankNo;
    private String bankName;

    private CommonTitle commonTitle;
    private EditText accountNameEt;
    private TextView identTypeTv;
    private EditText identNumberEt;
    private EditText cardNumberEt;
    private EditText cardDateEt;
    private LinearLayout cardDateRl;
    private EditText cardCvnEt;
    private LinearLayout cardCvnRl;
    private EditText mobileNumberEt;
    private TextView verifyInfoTv;

    HashMap<String, Object> params = new HashMap<>();

    private PayRequester payRequester;
    private BankInfoSaveListenerImpl bankInfoSaveListener;
    private class BankInfoSaveListenerImpl extends DefaultRequestListener implements BankInfoSaveListener{

        @Override
        public void onBankInfoSaveSuccess(String userBankId) {
            Intent intent = new Intent(AddBankCardActivity.this,AddBankCardSendCodeActivity.class);
            intent.putExtra("userBankId",userBankId);
            intent.putExtra("mobile",mobileNumberEt.getText().toString());
            intent.putExtra("params",params);
            startActivity(intent);
        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(getBaseContext(),errorMsg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card);

        addActivity(this);

        type = (BankCard.CARD_TYPE)getIntent().getSerializableExtra("type");
        bankNo = getIntent().getStringExtra("bankNo");
        bankName = getIntent().getStringExtra("bankName");

        payRequester = new PayRequester();
        bankInfoSaveListener = new BankInfoSaveListenerImpl();

        initView();
        initEvent();
        initData();
    }
    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        accountNameEt = (EditText) findViewById(R.id.et_account_name);
        identTypeTv = (TextView) findViewById(R.id.tv_ident_type);
        identNumberEt = (EditText) findViewById(R.id.et_ident_number);
        cardNumberEt = (EditText) findViewById(R.id.et_card_number);
        cardDateEt = (EditText) findViewById(R.id.et_card_date);
        cardCvnEt = (EditText) findViewById(R.id.et_card_cvn);
        mobileNumberEt = (EditText) findViewById(R.id.et_mobile_number);
        verifyInfoTv = (TextView)findViewById(R.id.tv_verify_info);
        cardDateRl = (LinearLayout) findViewById(R.id.ll_card_date);
        cardCvnRl = (LinearLayout) findViewById(R.id.ll_card_cvn);

        switch (type){
            case COMMON:
                cardDateRl.setVisibility(View.GONE);
                cardCvnRl.setVisibility(View.GONE);
                break;
            case CREDIT:
                break;
        }
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
        verifyInfoTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                params.put("accessToken",getAccessToken());
                params.put("bankId",bankNo);
                params.put("userName",accountNameEt.getText().toString());
                params.put("bankNum",cardNumberEt.getText().toString());
                params.put("cardsType","0");
                params.put("cardsNum",identNumberEt.getText().toString());
                params.put("userPhone",mobileNumberEt.getText().toString());
                switch (type){
                    case COMMON:
                        params.put("bankType","10");
                        break;
                    case CREDIT:
                        params.put("bankType","20");
                        params.put("validDate",cardDateEt.getText().toString());
                        params.put("cvn2",cardCvnEt.getText().toString());
                        break;
                }
                params.put("bankName",bankName);
                payRequester.bankInfoSave(AddBankCardActivity.this,params,bankInfoSaveListener);

            }
        });
    }

    private void initData(){

    }
}
