package com.zhejiang.haoxiadan.ui.activity.cart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.chinapay.mobilepayment.activity.MainActivity;
import com.chinapay.mobilepayment.bean.OrderInfo;
import com.chinapay.mobilepayment.global.CPGlobalInfo;
import com.chinapay.mobilepayment.global.ResultInfo;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.alipay.AuthResult;
import com.zhejiang.haoxiadan.alipay.PayResult;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormByIdsListener;
import com.zhejiang.haoxiadan.business.request.my.CloudPayListener;
import com.zhejiang.haoxiadan.business.request.my.GetFinanceTextListener;
import com.zhejiang.haoxiadan.business.request.my.GoRegisterFinanceUserListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.business.request.my.UserIsFinanceRegisterListener;
import com.zhejiang.haoxiadan.business.request.pay.ApplayWXListener;
import com.zhejiang.haoxiadan.business.request.pay.ApplyOrderFormPayListener;
import com.zhejiang.haoxiadan.business.request.pay.GetOrderPayCodeListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.QueryBankListByUserListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.CloudData;
import com.zhejiang.haoxiadan.model.choseModel.CloudDataBean;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.requestData.out.my.FinanceTextData;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ApplayFinanceActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MyOrdersActivity;
import com.zhejiang.haoxiadan.ui.activity.my.NeedReadFinanceTextActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.SelectBankCardDialog;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.OSUtils;
import com.zhejiang.haoxiadan.util.PublicUtils;
import com.zhejiang.haoxiadan.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderPaySelectActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_choose_finance)
    CheckBox ivChooseFinance;
    @BindView(R.id.rl_current_finance)
    RelativeLayout rlCurrentFinance;
    @BindView(R.id.iv_choose_huawei)
    CheckBox ivChooseHuawei;
    @BindView(R.id.iv_choose_sanxing)
    CheckBox ivChooseSanxing;
    @BindView(R.id.iv_choose_unionpay)
    CheckBox ivChooseUnionpay;
    @BindView(R.id.rl_current_huawei)
    RelativeLayout rlCurrentHuawei;
    @BindView(R.id.rl_current_sanxing)
    RelativeLayout rlCurrentSanxing;
    @BindView(R.id.rl_current_financer)
    RelativeLayout rlCurrentFinancer;
    private CommonTitle commonTitle;
    private TextView totalPriceTv;
    private CheckBox alipayCb;
    private CheckBox wxpayCb;
    private CheckBox bankpayCb;
    private TextView submitTv;
    private CheckBox iv_choose_financer;

    private RelativeLayout cardPayRl;
    private RelativeLayout curCardRl;
    private TextView curCardTv;

    private String orderId;
    private Order mOrder;
    private List<BankCard> bindBankCards = new ArrayList<>();
    private BankCard curBankCard;
    private int payType;
    private TipDialog outDialog;

    private OrderRequester orderRequester;
    private PayRequester payRequester;
    private String type;

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

    private GetOrderPayCodeListenerImpl getOrderPayCodeListener;

    private class GetOrderPayCodeListenerImpl extends DefaultRequestListener implements GetOrderPayCodeListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onGetOrderPayCodeSuccess(String code) {
            LoadingDialog.dismissDialog();

            addActivity(OrderPaySelectActivity.this);

            Intent intent = new Intent(OrderPaySelectActivity.this, PaySendCodeActivity.class);
            intent.putExtra("mobile", curBankCard.getMobile());
            intent.putExtra("mainId", mOrder.getMainId());
            intent.putExtra("bankNo", curBankCard.getBankNo());
            startActivity(intent);
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(OrderPaySelectActivity.this, errorMsg);
        }
    }

    private SelectOrderFormByIdsListenerImpl selectOrderFormByIdsListener;

    private class SelectOrderFormByIdsListenerImpl extends DefaultRequestListener implements SelectOrderFormByIdsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectOrderFormByIdsSuccess(Order order) {
            mOrder = order;

            if (!String.valueOf(mOrder.getTotalPrice()).equals("0.0")) {
                totalPriceTv.setText(NumberUtils.formatToDouble(mOrder.getTotalPrice() + ""));
            } else {
                double price = mOrder.getTotalGoodsPrice() + mOrder.getShipPrice();
                totalPriceTv.setText(NumberUtils.formatToDouble(price + ""));
            }

        }
    }

    private ApplyOrderFormPayListenerImpl applyOrderFormPayListener;

    private class ApplyOrderFormPayListenerImpl extends DefaultRequestListener implements ApplyOrderFormPayListener {

        @Override
        public void onApplyOrderFormPaySuccess(Map<String, String> wxpayMap, String alipayInfo) {
            addActivity(OrderPaySelectActivity.this);

            if (alipayInfo != null) {
                payAlipay(alipayInfo);
            } else {
                payWxpay(wxpayMap);
            }
        }

        @Override
        protected void onRequestFail() {

        }
    }

    private GetFinanceImp financeImp = new GetFinanceImp();

    private class GetFinanceImp extends DefaultRequestListener implements GetFinanceTextListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void getData(FinanceTextData financeTextData) {
            SharedPreferencesUtil.put(OrderPaySelectActivity.this, Constants.finance_text, gson.toJson(financeTextData));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_select);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        orderId = getIntent().getStringExtra("orderId");
        type = getIntent().getStringExtra("type");
        if (orderId == null) {
            finish();
            return;
        }
        api = WXAPIFactory.createWXAPI(this, Utils.WX_APP_ID);//微信支付

        orderRequester = new OrderRequester();
        payRequester = new PayRequester();
        queryBankListByUserListener = new QueryBankListByUserListenerImpl();
        getOrderPayCodeListener = new GetOrderPayCodeListenerImpl();
        selectOrderFormByIdsListener = new SelectOrderFormByIdsListenerImpl();
        applyOrderFormPayListener = new ApplyOrderFormPayListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        totalPriceTv = (TextView) findViewById(R.id.tv_total_price);
        alipayCb = (CheckBox) findViewById(R.id.iv_choose_alipay);
        wxpayCb = (CheckBox) findViewById(R.id.iv_choose_wxpay);
        bankpayCb = (CheckBox) findViewById(R.id.iv_choose_bank);
        submitTv = (TextView) findViewById(R.id.tv_submit);

        cardPayRl = (RelativeLayout) findViewById(R.id.rl_card_pay);
        curCardRl = (RelativeLayout) findViewById(R.id.rl_current_card);
        curCardTv = (TextView) findViewById(R.id.tv_current_card);
        iv_choose_financer = (CheckBox) findViewById(R.id.iv_choose_financer);

        phone = (String) SharedPreferencesUtil.get(this, Constants.userName, "");

        outDialog = new TipDialog(this, getString(R.string.tip), getString(R.string.tip_sure_out_cash), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                if (type != null) {
                    finish();
                } else {
                    Intent intent_tv_my_need_pay = new Intent(OrderPaySelectActivity.this, MyOrdersActivity.class);
                    intent_tv_my_need_pay.putExtra("status", Order.ORDER_STATUS.WAIT_PAY);
                    startActivity(intent_tv_my_need_pay);
                    finish();
                }
            }

            @Override
            public void onNegativeClick() {

            }
        });

        if (OSUtils.is_HUAWEI()) {
            rlCurrentFinancer.setVisibility(View.GONE);
            rlCurrentSanxing.setVisibility(View.GONE);
        }else if (OSUtils.is_MIUI()) {
            rlCurrentSanxing.setVisibility(View.GONE);
            rlCurrentHuawei.setVisibility(View.GONE);
        }else if (OSUtils.is_SAMSUNG()) {
            rlCurrentFinancer.setVisibility(View.GONE);
            rlCurrentHuawei.setVisibility(View.GONE);
        }else {
            rlCurrentHuawei.setVisibility(View.GONE);
            rlCurrentFinancer.setVisibility(View.GONE);
            rlCurrentSanxing.setVisibility(View.GONE);
        }
    }

    private void initEvent() {
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                sureOut();
            }

            @Override
            public void onRightClick() {

            }
        });
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alipayCb.isChecked()) {
                    return;
//                    LoadingDialog.getInstance(OrderPaySelectActivity.this).show();
//                    payRequester.applyOrderFormPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "alipay", applyOrderFormPayListener);
                } else if (wxpayCb.isChecked()) {
//                    LoadingDialog.getInstance(OrderPaySelectActivity.this).show();
                    payRequester.applayWX(OrderPaySelectActivity.this, getAccessToken(), orderId, applayWXImp);
//                    payRequester.applyOrderFormPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "wxpay", applyOrderFormPayListener);
                } else if (bankpayCb.isChecked()) {
                    if (curBankCard == null) {
                        ToastUtil.show(OrderPaySelectActivity.this, R.string.tip_please_selsct_bank_card);
                        if (bindBankCards.size() == 0) {
                            Intent intent = new Intent(OrderPaySelectActivity.this, AddBankCardSelectActivity.class);
                            startActivity(intent);
                        }
                        return;
                    }
                    LoadingDialog.getInstance(OrderPaySelectActivity.this).show();
                    payRequester.getOrderPayCode(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), curBankCard.getBankNo(), getOrderPayCodeListener);
                } else if (ivChooseFinance.isChecked()) {
                    if (mOrder.getOrderExplain() == null) {
                        startActivityForResult(new Intent(OrderPaySelectActivity.this, NeedReadFinanceTextActivity.class), 1);
                    } else {
                        Toast.makeText(OrderPaySelectActivity.this, "订单处于供应贷款审核中，请等待", Toast.LENGTH_SHORT).show();
                    }
                } else if (iv_choose_financer.isChecked()) {
                    payType = 4;
                    payRequester.cloudPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "XMP", cloudListener);
                } else if (ivChooseHuawei.isChecked()) {
                    payType = 2;
                    payRequester.cloudPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "HWP", cloudListener);
                } else if (ivChooseSanxing.isChecked()) {
                    payType = 3;
                    payRequester.cloudPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "SXP", cloudListener);
                } else if (ivChooseUnionpay.isChecked()) {
                    payType = 1;
                    payRequester.cloudPay(OrderPaySelectActivity.this, getAccessToken(), mOrder.getMainId(), "MUP", cloudListener);
                } else {
                    ToastUtil.show(OrderPaySelectActivity.this, R.string.tip_please_selsct_pay_way);
                }
            }
        });
        alipayCb.setOnClickListener(this);
        wxpayCb.setOnClickListener(this);
        bankpayCb.setOnClickListener(this);
        ivChooseFinance.setOnClickListener(this);
        iv_choose_financer.setOnClickListener(this);
        ivChooseSanxing.setOnClickListener(this);
        ivChooseHuawei.setOnClickListener(this);
        ivChooseUnionpay.setOnClickListener(this);
        cardPayRl.setOnClickListener(this);
        curCardRl.setOnClickListener(this);
    }

    private void initData() {
        orderRequester.selectOrderFormByIds(this, getAccessToken(), orderId, selectOrderFormByIdsListener);
        orderRequester.getFinanceText(this, financeImp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_choose_alipay:
                if (alipayCb.isChecked()) {
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    ivChooseUnionpay.setChecked(false);
                }
                break;
            case R.id.iv_choose_financer:
                if (iv_choose_financer.isChecked()) {
                    bankpayCb.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    ivChooseHuawei.setChecked(false);
                    ivChooseSanxing.setChecked(false);
                }

                break;

            case R.id.iv_choose_wxpay:
                if (wxpayCb.isChecked()) {
                    alipayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    ivChooseFinance.setChecked(false);
                }
                break;
            case R.id.iv_choose_bank:
                if (bankpayCb.isChecked()) {
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    iv_choose_financer.setChecked(false);
                    ivChooseHuawei.setChecked(false);
                    ivChooseSanxing.setChecked(false);
                    ivChooseUnionpay.setChecked(false);
                }
                break;
            case R.id.iv_choose_finance:
                if (ivChooseFinance.isChecked()) {
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    iv_choose_financer.setChecked(false);
                    ivChooseHuawei.setChecked(false);
                    ivChooseSanxing.setChecked(false);
                    ivChooseUnionpay.setChecked(false);
                }
                break;

            case R.id.iv_choose_huawei:
                if (ivChooseHuawei.isChecked()) {
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    iv_choose_financer.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    ivChooseSanxing.setChecked(false);
                    ivChooseUnionpay.setChecked(false);
                }
                break;

            case R.id.iv_choose_sanxing:
                if (ivChooseSanxing.isChecked()) {
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    iv_choose_financer.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    ivChooseHuawei.setChecked(false);
                    ivChooseUnionpay.setChecked(false);
                }
                break;

            case R.id.iv_choose_unionpay:
                if (ivChooseUnionpay.isChecked()) {
                    alipayCb.setChecked(false);
                    wxpayCb.setChecked(false);
                    bankpayCb.setChecked(false);
                    iv_choose_financer.setChecked(false);
                    ivChooseFinance.setChecked(false);
                    ivChooseHuawei.setChecked(false);
                }
                break;

            case R.id.rl_card_pay:
                if (bindBankCards.size() == 0) {
                    Intent intent = new Intent(OrderPaySelectActivity.this, AddBankCardSelectActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_current_card:
                //选择银行
                SelectBankCardDialog selectBankCardDialog = new SelectBankCardDialog(this, bindBankCards);
                selectBankCardDialog.setSelectBankCardCallback(new SelectBankCardDialog.SelectBankCardCallback() {
                    @Override
                    public void endSelect(BankCard bankCard) {
                        curBankCard = bankCard;
                        curCardTv.setText(curBankCard.getBankName() + "(" + curBankCard.getCardNo() + ")");
                    }
                });
                selectBankCardDialog.show(curBankCard);
                break;
        }
    }

    private void refreshBankCard() {
        if (bindBankCards.size() > 0) {
            curBankCard = bindBankCards.get(0);
            curCardTv.setText(curBankCard.getBankName() + "(" + curBankCard.getCardNo() + ")");
            curCardRl.setVisibility(View.VISIBLE);
        } else {
            curCardRl.setVisibility(View.GONE);
        }
    }


    /***
     * 微信支付业务
     */
    public void payWxpay(Map<String, String> map) {
        if (map.get("prepayid") != null) {
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
                PayTask alipay = new PayTask(OrderPaySelectActivity.this);
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

                        Intent intent = new Intent(OrderPaySelectActivity.this, OrderPaySuccessActivity.class);
                        intent.putExtra("price", mOrder.getTotalPrice() + "");
                        intent.putExtra("payWay", getString(R.string.label_alipay));
                        startActivity(intent);

                        cleanActivity();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrderPaySelectActivity.this, memo, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(OrderPaySelectActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(OrderPaySelectActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    private void sureOut() {
        outDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            sureOut();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 接收eventbus事件
     *
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case WX_PAY_RESULT_ERR: //支付失败
                ToastUtil.show(OrderPaySelectActivity.this, R.string.tip_pay_failed);
                LoadingDialog.dismissDialog();
                break;
            case WX_PAY_RESULT_OK: //支付成功
                LoadingDialog.dismissDialog();
                Intent intent = new Intent(OrderPaySelectActivity.this, OrderPaySuccessActivity.class);
                intent.putExtra("price", mOrder.getTotalPrice() + "");
                intent.putExtra("payWay", getString(R.string.label_wxpay));
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

    private String phone;
    private UserIsFinanceRegisterImp isFinanceRegisterImp = new UserIsFinanceRegisterImp();
    private GoRegisterFinanceUserImp goRegisterFinanceUserImp = new GoRegisterFinanceUserImp();

    private class GoRegisterFinanceUserImp extends DefaultRequestListener implements GoRegisterFinanceUserListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onSuccess() {
            LoadingDialog.dismissDialog();

            addActivity(OrderPaySelectActivity.this);

            Intent intent = new Intent(OrderPaySelectActivity.this, ApplayFinanceActivity.class);
            intent.putExtra("orderId", String.valueOf(mOrder.getOrderformMainId()));
            intent.putExtra("orderNo", mOrder.getOrderNo());
            intent.putExtra("price", String.valueOf(mOrder.getTotalPrice()));
            startActivity(intent);
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(OrderPaySelectActivity.this, errorMsg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        payRequester.queryBankListByUser(this, getAccessToken(), queryBankListByUserListener);
        if (com.chinapay.mobilepayment.utils.Utils.getResultInfo() != null) {
            ResultInfo resultInfo = com.chinapay.mobilepayment.utils.Utils.getResultInfo();
            Toast.makeText(OrderPaySelectActivity.this, resultInfo.getRespCode()+resultInfo.getRespDesc(), Toast.LENGTH_SHORT).show();
            Log.e("sssss",resultInfo.getRespCode()+resultInfo.getRespDesc());
            if (resultInfo.getRespCode() != null && !resultInfo.getRespCode().equals("")) {

                if (resultInfo.getRespCode().equals("0000")) {

                    Intent intent = new Intent(OrderPaySelectActivity.this, OrderPaySuccessActivity.class);
                    intent.putExtra("price", mOrder.getTotalPrice() + "");
                    intent.putExtra("payWay", PublicUtils.GetPayType(payType));
                    startActivity(intent);
                    finish();
                    cleanActivity();
                }
            }
            CPGlobalInfo.init();
        }
    }

    private class UserIsFinanceRegisterImp extends DefaultRequestListener implements UserIsFinanceRegisterListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onSuccess() {
            goRegisterFinanceUser();
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);

            addActivity(OrderPaySelectActivity.this);

            Intent intent = new Intent(OrderPaySelectActivity.this, ApplayFinanceActivity.class);
            intent.putExtra("orderId", String.valueOf(mOrder.getOrderformMainId()));
            intent.putExtra("orderNo", mOrder.getOrderNo());
            intent.putExtra("price", String.valueOf(mOrder.getTotalPrice()));
            startActivity(intent);
        }
    }

    private void goRegisterFinanceUser() {
        orderRequester.goRegisterFinanceUser(OrderPaySelectActivity.this, phone, phone, goRegisterFinanceUserImp);
    }

    private void doFinance() {
        orderRequester.userIsFinanceRegister(OrderPaySelectActivity.this, phone, isFinanceRegisterImp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            LoadingDialog.getInstance(OrderPaySelectActivity.this).show();
            doFinance();
        }
    }

    public ApplayWXImp applayWXImp = new ApplayWXImp();

    public class ApplayWXImp extends DefaultRequestListener implements ApplayWXListener {

        @Override
        public void onBack() {

        }

        @Override
        protected void onRequestFail() {

        }
    }

    CloudListener cloudListener = new CloudListener();

    public class CloudListener extends DefaultRequestListener implements CloudPayListener {

        @Override
        protected void onRequestFail() {

        }


        @Override
        public void onSuccsee(String data) {
            if (data != null) {
                CloudData cloudData = gson.fromJson(data, CloudData.class);
                startUnpay(cloudData.getOrderInfo());
            }
        }
    }

    private void startUnpay(CloudDataBean morderInfo) {
        com.chinapay.mobilepayment.utils.Utils.setPackageName(this.getPackageName());
        OrderInfo info = new OrderInfo();
        info.setVersion(morderInfo.getVersion());
        info.setBusiType(morderInfo.getBusiType());
        info.setMerId(morderInfo.getMerId());
        info.setMerBgUrl(morderInfo.getMerBgUrl());
        info.setRemoteAddr(morderInfo.getRemoteAddr());
        info.setMerOrderNo(morderInfo.getMerOrderNo());
        info.setOrderAmt(morderInfo.getOrderAmt());
        info.setTranDate(morderInfo.getTranDate());
        info.setTranTime(morderInfo.getTranTime());
        info.setSignature(morderInfo.getSignature());
        info.setRiskData(morderInfo.getRiskData());
        info.setTranType(morderInfo.getTranType());


        Intent intent = new Intent(OrderPaySelectActivity.this, MainActivity.class);
        intent.putExtra("orderInfo", gson.toJson(morderInfo));
        intent.putExtra("mode", "01");
        // orderInfo为Json字符串。
        // 使用intent跳转至移动认证插件
        startActivity(intent);

    }

}
