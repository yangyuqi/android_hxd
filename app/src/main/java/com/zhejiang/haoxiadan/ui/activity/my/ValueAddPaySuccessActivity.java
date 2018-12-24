package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ApplyRoleInfoListener;
import com.zhejiang.haoxiadan.business.request.my.ValueAddRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

public class ValueAddPaySuccessActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView dateTv;
    private TextView priceTv;
    private TextView backHomeTv;

    private String price;


    private ValueAddRequester valueAddRequester;
    private ApplyRoleInfoListenerImpl applyRoleInfoListener;
    private class ApplyRoleInfoListenerImpl extends DefaultRequestListener implements ApplyRoleInfoListener {

        @Override
        public void onApplyRoleInfoSuccess(ValueAddActivity.VALUE_ADD_STATUS status, ValueAddActivity.VALUE_ADD_TYPE type, String validityDate, Purchaser purchaser, Supplier supplier) {
            dateTv.setText(validityDate);

            //更新本地身份
            if(status == ValueAddActivity.VALUE_ADD_STATUS.SUCCESS){
                if(type != null){
                    switch (type){
                        case PURCHASER:
                            SharedPreferencesUtil.put(ValueAddPaySuccessActivity.this,Constants.userRole ,Constants.USER_BUY);
                            GlobalDataUtil.putObject(ValueAddPaySuccessActivity.this,Constants.GLOBAL_DATA_KEY_USER_TYPE , User.USER_TYPE.BUYER,true);
                            break;
                        case SUPPLIER:
                            SharedPreferencesUtil.put(ValueAddPaySuccessActivity.this,Constants.userRole ,Constants.USER_SELL);
                            GlobalDataUtil.putObject(ValueAddPaySuccessActivity.this,Constants.GLOBAL_DATA_KEY_USER_TYPE , User.USER_TYPE.SELLER,true);
                            break;
                    }
                }
            }

        }

        @Override
        protected void onRequestFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_add_pay_success);

        valueAddRequester = new ValueAddRequester();
        applyRoleInfoListener = new ApplyRoleInfoListenerImpl();

        initView();
        initEvent();
        initData();
    }


    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        dateTv = (TextView)findViewById(R.id.tv_date);
        priceTv = (TextView)findViewById(R.id.tv_price);
        backHomeTv = (TextView)findViewById(R.id.tv_submit);
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
        backHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(Event.GOTO_HOME);
            }
        });
    }

    private void initData(){
        price = (String)GlobalDataUtil.getObject(ValueAddPaySuccessActivity.this, Constants.TEMP_DATA_KEY_VALUE_ADD_PRICE);
        valueAddRequester.applyRoleInfo(this,getAccessToken(),applyRoleInfoListener);

        priceTv.setText(price);
    }
}
