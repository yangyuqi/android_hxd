package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.GetAllInstitutionNamesListener;
import com.zhejiang.haoxiadan.business.request.my.LoanApplyListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.business.request.pay.EditOfFinaListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.requestData.out.my.NamesBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.NamesListBean;
import com.zhejiang.haoxiadan.third.network.OkHttpClientManager;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.OrderPaySelectActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.OrderPaySuccessActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.UploadImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by qiuweiyu on 2017/9/19.
 */

public class ApplayFinanceActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.title_icon)
    ImageView titleIcon;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_order_phone)
    TextView tvOrderPhone;
    @BindView(R.id.edt_order_name)
    EditText edtOrderName;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_applay_if)
    TextView tvOrderApplayIf;
    @BindView(R.id.tv_order_co)
    TextView tvOrderCo;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_time)
    RelativeLayout rlTime;
    @BindView(R.id.rl_if)
    RelativeLayout rlIf;
    @BindView(R.id.rl_co)
    RelativeLayout rlCo;
    private String orderId, price ,phone ,institution_name ,orderNO;

    private OptionsPickerView pvCustomTime ;
    private List<String> m_date = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applay_finance_layout);
        ButterKnife.bind(this);

        addActivity(ApplayFinanceActivity.this);

        orderId = getIntent().getStringExtra("orderId");
        price = getIntent().getStringExtra("price");
        phone = (String) SharedPreferencesUtil.get(context, Constants.userName,"");
        orderNO = getIntent().getStringExtra("orderNo");

        getAllInstitutionNamesListener = new GetAllInstitutionNamesListenerImpl();

        tvOrderPhone.setText(phone);
        tvOrderNum.setText(orderNO);
        tvOrderPrice.setText(NumberUtils.formatToDouble(price)+"元");
        tvTitle.setText("贷款申请");

        getData();
    }

    @OnClick({R.id.rl_time,R.id.rl_if,R.id.rl_co,R.id.iv_left,R.id.tv_login})
    public void onClick(View view){
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus()
                                .getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.rl_time :
                m_date.clear();
                m_date.add("1月");
                m_date.add("2月");m_date.add("3月");m_date.add("4月");m_date.add("5月");m_date.add("6月");m_date.add("7月");m_date.add("8月");m_date.add("9月");m_date.add("10月");m_date.add("11月");m_date.add("12月");
                initPickView(m_date ,"选择贷款期限");
                pvCustomTime.show();
                break;
            case R.id.rl_if :
                m_date.clear();
                m_date.add("是");m_date.add("否");
                initPickView2(m_date,"是否贷款抵押");
                pvCustomTime.show();
                break;
            case R.id.rl_co :
                m_date.clear();
                for (NamesBean bean : beanList){
                    m_date.add(bean.getNstitution_name());
                }
                initPickView3(m_date,"选择贷款机构");
                pvCustomTime.show();
                break;
            case R.id.tv_login :
                if (edtOrderName.getText().toString().equals("")){
                    ToastUtil.show(context,"请输入联系人姓名");
                }else if (tvOrderTime.getText().toString().equals("")){
                    ToastUtil.show(context,"请选择贷款期限");
                }else if (tvOrderCo.getText().toString().equals("")){
                    ToastUtil.show(context,"请选择贷款机构");
                }else {
                    requester.loanApply(context, orderId, loan_mortgage, phone, edtOrderName.getText().toString(), "好下单", price, NumberUtils.getIntFromString(loan_term), String.valueOf(financeId), institution_name, loadImp);
                }
                break;
        }
    }

    private OrderRequester requester = new OrderRequester();
    private LoadImp loadImp = new LoadImp();
    private EditImp editImp = new EditImp();
    private class EditImp extends DefaultRequestListener implements EditOfFinaListener{

        @Override
        public void onSuccess() {
            cleanActivity();
            Intent intent = new Intent(context, OrderPaySuccessActivity.class);
            intent.putExtra("price",price);
            intent.putExtra("isLoan",true);
            startActivity(intent);
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

    private class LoadImp extends DefaultRequestListener implements LoanApplyListener{

        @Override
        public void onSucccess() {
            requester.editOfFinal(context,getAccessToken(),orderId,editImp);
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
    private GetAllInstitutionNamesListenerImpl getAllInstitutionNamesListener;
    private class GetAllInstitutionNamesListenerImpl extends DefaultRequestListener implements GetAllInstitutionNamesListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSuccess(List<NamesBean> beans) {
            beanList.clear();
            beanList.addAll(beans);
        }
    }

    private void getData() {
        requester.getAllInstitutionNames(this, getAllInstitutionNamesListener);
    }

    private int financeId ;

    private void initPickView3(final List<String> stringList, final String s) {
        pvCustomTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str =  stringList.get(options1);
                tvOrderCo.setText(str);
                institution_name = str;
                for (NamesBean bean : beanList){
                    if (bean.getNstitution_name().equals(str)){
                        financeId = bean.getId();
                    }
                }
            }
        }).setTitleText(s).build();
        pvCustomTime.setPicker(stringList);
    }

    private String loan_mortgage = "1";
    private void initPickView2(final List<String> m__date, String s) {
        pvCustomTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str =  m__date.get(options1);
                tvOrderApplayIf.setText(str);
                if (str.equals("是")){
                    loan_mortgage = "1";
                }else {
                    loan_mortgage = "2";
                }
            }
        }).setTitleText(s).build();
        pvCustomTime.setPicker(m__date);
    }

    private String loan_term ;
    private void initPickView(final List<String> mDate, final String s) {
        pvCustomTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String str =  mDate.get(options1);
                loan_term = str.substring(0,str.length()-1) ;
                tvOrderTime.setText(str);
            }
        }).setTitleText(s).build();
        pvCustomTime.setPicker(mDate);
    }

    private List<NamesBean> beanList = new ArrayList<>();

}
