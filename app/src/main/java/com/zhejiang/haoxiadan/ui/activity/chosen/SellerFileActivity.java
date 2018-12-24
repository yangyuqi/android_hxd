package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chat.ChatRequester;
import com.zhejiang.haoxiadan.business.request.chat.GetUserRoleListener;
import com.zhejiang.haoxiadan.business.request.chat.QueryCustomerByStoreIdListener;
import com.zhejiang.haoxiadan.business.request.chosen.QueryStoreArchivesListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.common.UserRole;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.ChatActivity;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddApplyActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;

/**
 * 公司档案
 */
public class SellerFileActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView tv_company;
    private TextView tv_license_number;
    private TextView et_enterprise_type;
    private TextView et_employees_num;
    private TextView tv_shop_introduction;
    private TextView tv_main_industry;
    private TextView tv_product_style;
    private TextView tv_main_products;
    private TextView tv_main_customergroups;
    private TextView tv_month_production;
    private TextView tv_year_sales;
    private TextView tv_year_export_num;

    private String storeId;
    private Supplier mSupplier;

    private TipDialog tipDialog;

    private ChatRequester chatRequester = new ChatRequester();
    private ShopRequester shopRequester;
    private QueryStoreArchivesListenerImpl queryStoreArchivesListener;
    private class QueryStoreArchivesListenerImpl extends DefaultRequestListener implements QueryStoreArchivesListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryStoreArchivesSuccess(Supplier supplier) {
            mSupplier = supplier;
            refreshView();
        }
    }


    private GetUserRoleListenerImpl getUserRoleListenerImpl = new GetUserRoleListenerImpl();
    private class GetUserRoleListenerImpl extends DefaultRequestListener implements GetUserRoleListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void onGetUserRoleSuccess(UserRole userRole) {
            SharedPreferencesUtil.put(SellerFileActivity.this,Constants.userRole ,userRole.getUserRole());
            switch (userRole.getUserRole()){
                case Constants.USER_REGULAR:
                    tipDialog.show();
                    break;
                case Constants.USER_BUY:
                    chatRequester.queryCustomerByStoreId(SellerFileActivity.this, storeId,(String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.userName,""), queryCustomerByStoreIdListenerImpl);
                    break;
                case Constants.USER_SELL:
                    ToastUtil.show(SellerFileActivity.this,R.string.tip_only_purchaser_can_chat);
                    break;
                case Constants.USER_CUSTOMER:
                    ToastUtil.show(SellerFileActivity.this,R.string.tip_only_purchaser_can_chat);
                    break;
            }

        }
    }

    private QueryCustomerByStoreIdListenerImpl queryCustomerByStoreIdListenerImpl = new QueryCustomerByStoreIdListenerImpl();
    private class QueryCustomerByStoreIdListenerImpl extends DefaultRequestListener implements QueryCustomerByStoreIdListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void onQueryCustomerSuccess(Customer customer) {
            Intent intent = new Intent();
            intent.putExtra(Constants.CONV_TITLE, customer.getStoreName());
            intent.putExtra(Constants.TARGET_ID, customer.getAdminId());
            intent.putExtra(Constants.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
            intent.putExtra("storeId", NumberUtils.getIntFromString(customer.getStoreId()) +"");
            String nickName = (String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.nickName,"");
            if(nickName != null && !"".equals(nickName)) {
                intent.putExtra("buyName", nickName);
            }else{
                intent.putExtra("buyName", (String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.userName,""));
            }
            intent.putExtra("buyImg", (String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.user_icon,""));
            intent.putExtra("buyId", (String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.userName,""));
            intent.putExtra("storeName", customer.getStoreName());
            intent.putExtra("storeLogo", customer.getStoreLogo());
            intent.putExtra("customerPhone", customer.getCustomerPhone());
            intent.putExtra("storePhone", customer.getStorePhone());
            if(
                    (customer.getAdminId() == null || "".equals(customer.getAdminId())) ||
                            (customer.getStoreId() == null || "".equals(customer.getStoreId())) ||
                            (customer.getCustomerPhone() == null || "".equals(customer.getCustomerPhone())) ||
                            (customer.getStorePhone() == null )
                    ){
                ToastUtil.show(SellerFileActivity.this,R.string.chat_no_store_msg);
                return;
            }
            intent.setClass(SellerFileActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_file);

        storeId = getIntent().getStringExtra("storeId");
        if (storeId == null){
            finish();
            return;
        }
        shopRequester = new ShopRequester();
        queryStoreArchivesListener = new QueryStoreArchivesListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        tv_company = (TextView)findViewById(R.id.tv_company);
        tv_license_number = (TextView)findViewById(R.id.tv_license_number);
        et_enterprise_type = (TextView)findViewById(R.id.et_enterprise_type);
        et_employees_num = (TextView)findViewById(R.id.et_employees_num);
        tv_shop_introduction = (TextView)findViewById(R.id.tv_shop_introduction);
        tv_main_industry = (TextView)findViewById(R.id.tv_main_industry);
        tv_product_style = (TextView)findViewById(R.id.tv_product_style);
        tv_main_products = (TextView)findViewById(R.id.tv_main_products);
        tv_main_customergroups = (TextView)findViewById(R.id.tv_main_customergroups);
        tv_month_production = (TextView)findViewById(R.id.tv_month_production);
        tv_year_sales = (TextView)findViewById(R.id.tv_year_sales);
        tv_year_export_num = (TextView)findViewById(R.id.tv_year_export_num);


        tipDialog = new TipDialog(SellerFileActivity.this, getString(R.string.tip), getString(R.string.tip_only_purchaser_can_watch_file),
                getString(R.string.tip_update_now), getString(R.string.tip_let_me_think), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                //去增值服务
                Intent intent = new Intent(SellerFileActivity.this, ValueAddApplyActivity.class);
                intent.putExtra("type", ValueAddActivity.VALUE_ADD_TYPE.PURCHASER);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {
            }
        });
    }

    private void initEvent(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                if(!"".equals(getAccessToken())) {
                    String userType2 = (String) SharedPreferencesUtil.get(SellerFileActivity.this, Constants.userRole,"");
                    switch (userType2){
                        case Constants.USER_REGULAR:
                            //查看用户权限
                            chatRequester.getUserRole(SellerFileActivity.this,getAccessToken(),getUserRoleListenerImpl);
                            break;
                        case Constants.USER_BUY:
                            chatRequester.queryCustomerByStoreId(SellerFileActivity.this, storeId,(String) SharedPreferencesUtil.get(SellerFileActivity.this,Constants.userName,""), queryCustomerByStoreIdListenerImpl);
                            break;
                        case Constants.USER_SELL:
                            ToastUtil.show(SellerFileActivity.this,R.string.tip_only_purchaser_can_chat);
                            break;
                        case Constants.USER_CUSTOMER:
                            ToastUtil.show(SellerFileActivity.this,R.string.tip_only_purchaser_can_chat);
                            break;
                    }
                }else{
                    startActivity(new Intent(SellerFileActivity.this, LoginActivity.class));
                }
            }
        });
    }
    private void initData(){
        shopRequester.queryStoreArchives(this,getAccessToken(),storeId,queryStoreArchivesListener);
    }

    private void refreshView(){
        tv_company.setText(mSupplier.getCompany());
        tv_license_number.setText(mSupplier.getLicenseNumber());
        et_enterprise_type.setText(mSupplier.getEnterpriseType());
        et_employees_num.setText(mSupplier.getEmployeesNum());
        tv_shop_introduction.setText(mSupplier.getShopIntroduction());
        tv_main_industry.setText(mSupplier.getMainIndustry());
        tv_product_style.setText(mSupplier.getProductStyle());
        tv_main_products.setText(mSupplier.getMainProducts());
        tv_main_customergroups.setText(mSupplier.getMainCustomerGroups());
        tv_month_production.setText(mSupplier.getMonthProduction());
        tv_year_sales.setText(mSupplier.getYearSales());
        tv_year_export_num.setText(mSupplier.getYearExportNum());
    }
}
