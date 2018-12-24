package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.NoDoubleClickListener;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.util.Constants;

/**
 * 增值服务
 */
public class ValueAddActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitle commonTitle;
    private ImageView userIconIv;
    private TextView userNameTv ,tvPurcher;
    private TextView userTypeTv;
    private LinearLayout validityTimeLl;
    private TextView validityTimeTv;
    private LinearLayout applyLl;
    private LinearLayout applyPurchaserLl;
    private LinearLayout applySupplierLl;
    private LinearLayout noPayLl;
    private TextView applyTypeTv;
    private TextView applyStatusTv;
    private TextView applyModifyTv;
    private LinearLayout submitLl;
    private TextView submitTv ,tvReasonType;

    private VALUE_ADD_STATUS mStatus;
    private VALUE_ADD_TYPE mType;
    private String mValidityDate;
    private Purchaser mPurchaser;
    private Supplier mSupplier;

    public enum VALUE_ADD_STATUS{
        NOT_APPLY,//没申请
        NOT_PAY,//未支付
        SUCCESS,
        OVERDUE,//过期
        REVIEW,
        FAILED
    }
    public enum VALUE_ADD_TYPE{
        PURCHASER,
        SUPPLIER//
    }

    private ValueAddRequester valueAddRequester;
    private ApplyRoleInfoListenerImpl applyRoleInfoListener;
    private class ApplyRoleInfoListenerImpl extends DefaultRequestListener implements ApplyRoleInfoListener {

        @Override
        public void onApplyRoleInfoSuccess(VALUE_ADD_STATUS status, VALUE_ADD_TYPE type, String validityDate, Purchaser purchaser, Supplier supplier) {
            mType = type;
            mStatus = status;
            mValidityDate = validityDate;
            mPurchaser = purchaser;
            mSupplier = supplier;
            refreshView();
        }

        @Override
        protected void onRequestFail() {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_add);

        valueAddRequester = new ValueAddRequester();
        applyRoleInfoListener = new ApplyRoleInfoListenerImpl();

        initView();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        userIconIv = (ImageView)findViewById(R.id.iv_user_icon);
        userNameTv = (TextView)findViewById(R.id.tv_name);
        userTypeTv = (TextView)findViewById(R.id.tv_user_type);
        validityTimeLl = (LinearLayout)findViewById(R.id.ll_validity_time);
        validityTimeTv = (TextView)findViewById(R.id.tv_validity_time);
        applyLl = (LinearLayout)findViewById(R.id.ll_apply);
        applyPurchaserLl = (LinearLayout)findViewById(R.id.ll_apply_purchaser);
        applySupplierLl = (LinearLayout)findViewById(R.id.ll_apply_supplier);
        noPayLl = (LinearLayout)findViewById(R.id.ll_no_pay);
        applyTypeTv = (TextView)findViewById(R.id.tv_apply_type);
        applyStatusTv = (TextView)findViewById(R.id.tv_apply_status);
        applyModifyTv = (TextView)findViewById(R.id.tv_apply_modify);
        submitLl = (LinearLayout)findViewById(R.id.ll_submit);
        submitTv = (TextView)findViewById(R.id.tv_submit);
        tvPurcher = (TextView) findViewById(R.id.tv_purcher);
        tvReasonType = (TextView) findViewById(R.id.tv_apply_modify_label);
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
        tvPurcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentP = new Intent(ValueAddActivity.this,ValueAddApplyActivity.class);
                intentP.putExtra("type",VALUE_ADD_TYPE.SUPPLIER);
                startActivity(intentP);
            }
        });
        applyPurchaserLl.setOnClickListener(this);
        applySupplierLl.setOnClickListener(this);
        applyModifyTv.setOnClickListener(this);
        submitTv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {

                if (submitTv.getText().toString().equals("修改资料")){
                    Intent intent = new Intent(ValueAddActivity.this,ValueAddApplyActivity.class);
                    intent.putExtra("type",mType);
                    intent.putExtra("purchaser",mPurchaser);
                    intent.putExtra("supplier",mSupplier);
                    startActivity(intent);
                    return;
                }


                if(mType == null){
                    return;
                }
                Intent intentPay = new Intent(ValueAddActivity.this,ValueAddPaySelectActivity.class);
                intentPay.putExtra("type",mType);
                switch (mType){
                    case PURCHASER:
                        intentPay.putExtra("applyId",mPurchaser.getId());
                        break;
                    case SUPPLIER:
                        intentPay.putExtra("applyId",mSupplier.getId());
                        break;
                }
                switch (mStatus){
                    case NOT_PAY:
                        intentPay.putExtra("isFirst",true);
                        break;
                }
                startActivity(intentPay);
            }
        });
    }

    private void initData(){
        String username = (String)SharedPreferencesUtil.get(this, Constants.nickName,"");
        String role = (String) SharedPreferencesUtil.get(ValueAddActivity.this,Constants.userRole,"");        String usericon = (String)SharedPreferencesUtil.get(this,Constants.user_icon ,"");
        ImageLoaderUtil.displayImage(usericon,userIconIv);
        userNameTv.setText(username);
        if(role!= null){
            switch (role){
                case Constants.USER_CUSTOMER:
                    userTypeTv.setText(R.string.label_common_member);
                    break;
                case Constants.USER_SELL:
                    userTypeTv.setText(R.string.label_supplier);
                    break;
                case Constants.USER_BUY:
                    userTypeTv.setText(R.string.label_purchaser);
                    break;
            }
        }
        requestData();
    }

    private void requestData(){
        valueAddRequester.applyRoleInfo(this,getAccessToken(),applyRoleInfoListener);
    }

    private void refreshView(){
        switch (mStatus){
            case NOT_APPLY:
                tvPurcher.setVisibility(View.VISIBLE);
                applyLl.setVisibility(View.GONE);
                noPayLl.setVisibility(View.GONE);
                submitLl.setVisibility(View.GONE);
                validityTimeLl.setVisibility(View.GONE);
                break;
            case NOT_PAY:
                applyLl.setVisibility(View.GONE);
                noPayLl.setVisibility(View.VISIBLE);
                submitLl.setVisibility(View.VISIBLE);
                submitTv.setText(R.string.btn_go_pay);
                validityTimeLl.setVisibility(View.GONE);
                switch (mType){
                    case PURCHASER:
                        applyTypeTv.setText(R.string.label_purchaser);
                        break;
                    case SUPPLIER:
                        applyTypeTv.setText(R.string.label_supplier);
                        break;
                }
                break;
            case SUCCESS:
                tvPurcher.setVisibility(View.GONE);
                applyLl.setVisibility(View.GONE);
                noPayLl.setVisibility(View.VISIBLE);
                submitLl.setVisibility(View.VISIBLE);
                submitTv.setText("修改资料");
                applyStatusTv.setText("审核通过");
                switch (mType){
                    case PURCHASER:
                        applyTypeTv.setText(R.string.label_purchaser);
                        break;
                    case SUPPLIER:
                        applyTypeTv.setText(R.string.label_supplier);
                        break;
                }
                break;
            case OVERDUE:
                applyLl.setVisibility(View.GONE);
                noPayLl.setVisibility(View.GONE);
                submitLl.setVisibility(View.VISIBLE);
                submitTv.setText(R.string.btn_renew);
                validityTimeLl.setVisibility(View.VISIBLE);
                validityTimeTv.setText(mValidityDate);
                break;

            case REVIEW:
                tvPurcher.setVisibility(View.GONE);
                applyLl.setVisibility(View.GONE);
                noPayLl.setVisibility(View.VISIBLE);
                submitLl.setVisibility(View.VISIBLE);
                submitTv.setText("修改资料");
                applyStatusTv.setText("审核中");
                switch (mType){
                    case PURCHASER:
                        applyTypeTv.setText(R.string.label_purchaser);
                        break;
                    case SUPPLIER:
                        applyTypeTv.setText(R.string.label_supplier);
                        break;
                }
                break;

            case FAILED:
                tvPurcher.setVisibility(View.GONE);
                noPayLl.setVisibility(View.VISIBLE);
                submitLl.setVisibility(View.VISIBLE);
                submitTv.setText("修改资料");
                applyStatusTv.setText("审核不通过");
                tvReasonType.setVisibility(View.VISIBLE);
                tvReasonType.setText("原因 :");
                applyModifyTv.setText(" 资料填写不正确");
                applyModifyTv.setEnabled(false);
                applyModifyTv.setVisibility(View.VISIBLE);
                switch (mType){
                    case PURCHASER:
                        applyTypeTv.setText(R.string.label_purchaser);
                        break;
                    case SUPPLIER:
                        applyTypeTv.setText(R.string.label_supplier);
                        break;
                }
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_apply_purchaser:
                Intent intentP = new Intent(ValueAddActivity.this,ValueAddApplyActivity.class);
                intentP.putExtra("type",VALUE_ADD_TYPE.PURCHASER);
                startActivity(intentP);
                break;
            case R.id.ll_apply_supplier:
                Intent intentS = new Intent(ValueAddActivity.this,ValueAddApplyActivity.class);
                intentS.putExtra("type",VALUE_ADD_TYPE.SUPPLIER);
                startActivity(intentS);
                break;
            case R.id.tv_apply_modify:
                Intent intent = new Intent(ValueAddActivity.this,ValueAddApplyActivity.class);
                intent.putExtra("type",mType);
                intent.putExtra("purchaser",mPurchaser);
                intent.putExtra("supplier",mSupplier);
                startActivity(intent);
                break;
        }
    }

}
