package com.zhejiang.haoxiadan.ui.activity.my;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.alipay.AuthResult;
import com.zhejiang.haoxiadan.alipay.PayResult;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ApplyRoleFeeListener;
import com.zhejiang.haoxiadan.business.request.my.ValueAddRequester;
import com.zhejiang.haoxiadan.business.request.pay.ApplyOrderFormPayListener;
import com.zhejiang.haoxiadan.business.request.pay.GetValueAddPayCodeListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.QueryBankListByUserListener;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.common.YearFee;
import com.zhejiang.haoxiadan.ui.NoDoubleClickListener;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.AddBankCardSelectActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.YearFeeAdapter;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.dialog.ValueAddYearPopupWindow;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.SelectBankCardDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValueAddPaySelectActivity extends BaseActivity implements View.OnClickListener{

    private CommonTitle commonTitle;
    private TextView priceTv;
    private CheckBox alipayCb;
    private CheckBox wxpayCb;
    private CheckBox bankpayCb;
    private TextView submitTv;

    private TextView yearTv;
    private ValueAddYearPopupWindow valueAddYearPopupWindow;

    private List<YearFee> yearFees = new ArrayList<>();

    private ValueAddActivity.VALUE_ADD_TYPE type;

    private RelativeLayout cardPayRl;
    private RelativeLayout curCardRl;
    private TextView curCardTv;

    private List<BankCard> bindBankCards = new ArrayList<>();
    private BankCard curBankCard;

    private String applyId;
    private YearFee yearFee;
    private boolean isFirst;


    private ValueAddRequester valueAddRequester;
    private ApplyRoleFeeListenerImpl applyRoleFeeListener;
    private class ApplyRoleFeeListenerImpl extends DefaultRequestListener implements ApplyRoleFeeListener{

        @Override
        public void onApplyRoleFeeSuccess(List<YearFee> yearFeeList) {
            yearFees.clear();
            yearFees.addAll(yearFeeList);
        }

        @Override
        protected void onRequestFail() {

        }
    }
    private PayRequester payRequester;
    private QueryBankListByUserListenerImpl queryBankListByUserListener;
    private class QueryBankListByUserListenerImpl extends DefaultRequestListener implements QueryBankListByUserListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryBankListByUserSuccess(List<BankCard> bankCards) {
            bindBankCards.clear();
            bindBankCards.addAll(bankCards);

            refreshBankCard();
        }
    }
    private GetValueAddPayCodeListenerImpl getValueAddPayCodeListener;
    private class GetValueAddPayCodeListenerImpl extends DefaultRequestListener implements GetValueAddPayCodeListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetValueAddPayCodeSuccess() {

            addActivity(ValueAddPaySelectActivity.this);

            GlobalDataUtil.putObject(ValueAddPaySelectActivity.this, Constants.TEMP_DATA_KEY_VALUE_ADD_PRICE, NumberUtils.formatToDouble(yearFee.getPrice()*yearFee.getYear()+""));
            Intent intent = new Intent(ValueAddPaySelectActivity.this,ValueAddPaySendCodeActivity.class);
            intent.putExtra("applyId",applyId);
            intent.putExtra("feeId",yearFee.getId());
            intent.putExtra("isFirst",isFirst);
            intent.putExtra("bankNo",curBankCard.getBankNo());
            startActivity(intent);
        }
    }
    private ApplyOrderFormPayListenerImpl applyOrderFormPayListener;
    private class ApplyOrderFormPayListenerImpl extends DefaultRequestListener implements ApplyOrderFormPayListener {

        @Override
        public void onApplyOrderFormPaySuccess(Map<String,String> wxpayMap, String alipayInfo) {

            addActivity(ValueAddPaySelectActivity.this);

            GlobalDataUtil.putObject(ValueAddPaySelectActivity.this, Constants.TEMP_DATA_KEY_VALUE_ADD_PRICE, NumberUtils.formatToDouble(yearFee.getPrice()*yearFee.getYear()+""));
            if(alipayInfo != null){
                payAlipay(alipayInfo);
            }else{
                payWxpay(wxpayMap);
            }
        }

        @Override
        protected void onRequestFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_add_pay_select);
        EventBus.getDefault().register(this);

        applyId = getIntent().getStringExtra("applyId");
        type = (ValueAddActivity.VALUE_ADD_TYPE)getIntent().getSerializableExtra("type");
        isFirst = getIntent().getBooleanExtra("isFirst",false);
        if(type == null){
            finish();
            return;
        }
        api = WXAPIFactory.createWXAPI(this, Utils.WX_APP_ID);//微信支付

        payRequester = new PayRequester();
        valueAddRequester = new ValueAddRequester();
        applyRoleFeeListener = new ApplyRoleFeeListenerImpl();
        queryBankListByUserListener = new QueryBankListByUserListenerImpl();
        getValueAddPayCodeListener = new GetValueAddPayCodeListenerImpl();
        applyOrderFormPayListener = new ApplyOrderFormPayListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        priceTv = (TextView)findViewById(R.id.tv_price);
        alipayCb = (CheckBox)findViewById(R.id.iv_choose_alipay);
        wxpayCb = (CheckBox)findViewById(R.id.iv_choose_wxpay);
        bankpayCb = (CheckBox)findViewById(R.id.iv_choose_bank);
        submitTv = (TextView)findViewById(R.id.tv_submit);

        cardPayRl = (RelativeLayout)findViewById(R.id.rl_card_pay);
        curCardRl = (RelativeLayout)findViewById(R.id.rl_current_card);
        curCardTv = (TextView)findViewById(R.id.tv_current_card);

        yearTv = (TextView)findViewById(R.id.tv_year);
        valueAddYearPopupWindow = new ValueAddYearPopupWindow(this);
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
        submitTv.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                if(yearFee == null){
                    ToastUtil.show(ValueAddPaySelectActivity.this,R.string.tip_please_select_buy_year);
                    return;
                }
                if(alipayCb.isChecked()){
                    payRequester.applyRolePay(ValueAddPaySelectActivity.this,getAccessToken(),"alipay",applyId,yearFee.getId(),isFirst,applyOrderFormPayListener);
                }else if(wxpayCb.isChecked()){
                    payRequester.applyRolePay(ValueAddPaySelectActivity.this,getAccessToken(),"wxpay",applyId,yearFee.getId(),isFirst,applyOrderFormPayListener);
                }else if(bankpayCb.isChecked()){
                    if(curBankCard==null){
                        ToastUtil.show(ValueAddPaySelectActivity.this,R.string.tip_please_selsct_bank_card);
                        if(bindBankCards.size() == 0){
                            Intent intent = new Intent(ValueAddPaySelectActivity.this,AddBankCardSelectActivity.class);
                            startActivity(intent);
                        }
                        return;
                    }
                    payRequester.getValueAddPayCode(ValueAddPaySelectActivity.this,getAccessToken(),applyId,yearFee.getId(),curBankCard.getBankNo(),isFirst,getValueAddPayCodeListener);
                }else{
                    ToastUtil.show(ValueAddPaySelectActivity.this,R.string.tip_please_selsct_pay_way);
                }
            }
        });
        alipayCb.setOnClickListener(this);
        wxpayCb.setOnClickListener(this);
        bankpayCb.setOnClickListener(this);

        yearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valueAddYearPopupWindow.setYearFees(yearFees);
                valueAddYearPopupWindow.setWidth(getResources().getDimensionPixelOffset(R.dimen.value_add_year_width));
                valueAddYearPopupWindow.showAsDropDown(yearTv);
            }
        });
        valueAddYearPopupWindow.setYearSelectListener(new ValueAddYearPopupWindow.YearSelectListener() {
            @Override
            public void onYearSelect(YearFee tempyearFee) {
                yearFee = tempyearFee;
                yearTv.setText(yearFee.getYear()+"");
                priceTv.setText(NumberUtils.formatToDouble(yearFee.getPrice()+""));
            }
        });

        cardPayRl.setOnClickListener(this);
        curCardRl.setOnClickListener(this);
    }

    private void initData(){
        valueAddRequester.applyRoleFee(this,getAccessToken(),type,applyRoleFeeListener);
    }

    @Override
    protected void onResume() {
        payRequester.queryBankListByUser(this,getAccessToken(),queryBankListByUserListener);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_choose_alipay:
                if(alipayCb.isChecked()){
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                }
                break;
            case R.id.iv_choose_wxpay:
                if(wxpayCb.isChecked()){
                    alipayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                }
                break;
            case R.id.iv_choose_bank:
                if(bankpayCb.isChecked()){
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                }
                break;
            case R.id.rl_card_pay:
                if(bindBankCards.size() == 0){
                    Intent intent = new Intent(ValueAddPaySelectActivity.this,AddBankCardSelectActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_current_card:
                //选择银行
                SelectBankCardDialog selectBankCardDialog = new SelectBankCardDialog(this,bindBankCards);
                selectBankCardDialog.setSelectBankCardCallback(new SelectBankCardDialog.SelectBankCardCallback() {
                    @Override
                    public void endSelect(BankCard bankCard) {
                        curBankCard = bankCard;
                        curCardTv.setText(curBankCard.getBankName()+"("+curBankCard.getCardNo()+")");
                    }
                });
                selectBankCardDialog.show(curBankCard);
                break;
        }
    }

    private void refreshBankCard(){
        if(bindBankCards.size()>0){
            curBankCard = bindBankCards.get(0);
            curCardTv.setText(curBankCard.getBankName()+"("+curBankCard.getCardNo()+")");
            curCardRl.setVisibility(View.VISIBLE);
        }else{
            curCardRl.setVisibility(View.GONE);
        }
    }


    /***
     * 微信支付业务
     */
    public void payWxpay(Map<String,String> map){
        if(map.get("prepayid") != null) {
            PayReq req = new PayReq();
            //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
            req.appId = map.get("appid");
            req.partnerId = map.get("partnerid");
            req.prepayId = map.get("prepayid");
            req.nonceStr = map.get("noncestr");
            req.timeStamp = map.get("timestamp");
            req.packageValue = map.get("package");
            req.sign = map.get("sign");
            req.extData = "app data"; // optional
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.registerApp(Utils.WX_APP_ID);
            api.sendReq(req);
        }
    }


    /**
     * 支付宝支付业务
     */
    public void payAlipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(ValueAddPaySelectActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private final int SDK_PAY_FLAG = 1;
    private final int SDK_AUTH_FLAG = 2;
    //微信
    private IWXAPI api;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            EventBus.getDefault().post(Event.DIALOG_DISMISS);
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    String memo = payResult.getMemo();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        //Toast.makeText(PaySelectActivity.this, "支付成功", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ValueAddPaySelectActivity.this,ValueAddPaySuccessActivity.class);
                        startActivity(intent);

                        cleanActivity();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ValueAddPaySelectActivity.this, memo, Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(ValueAddPaySelectActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(ValueAddPaySelectActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 接收eventbus事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case WX_PAY_RESULT_ERR: //支付失败
                ToastUtil.show(ValueAddPaySelectActivity.this,R.string.tip_pay_failed);
                break;
            case WX_PAY_RESULT_OK: //支付成功
                Intent intent = new Intent(ValueAddPaySelectActivity.this,ValueAddPaySuccessActivity.class);
                startActivity(intent);

                cleanActivity();
                break;
            case DIALOG_DISMISS:
                LoadingDialog.dismissDialog();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
