package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ApplyRoleListener;
import com.zhejiang.haoxiadan.business.request.my.QueryTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.my.SelectAreaAllListener;
import com.zhejiang.haoxiadan.business.request.my.ValueAddRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.KeyBoardUtil;
import com.zhejiang.haoxiadan.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ValueAddApplyActivity extends BaseActivity implements View.OnClickListener{

    private CommonTitle commonTitle;
    private TextView submitTv;
    private EditText et_company;
    private ImageView iv_company;
    private EditText et_license_number;
    private ImageView iv_license_number;
    private EditText et_enterprise_type;
    private ImageView iv_enterprise_type;
    private EditText et_employees_num;
    private ImageView iv_employees_num;
    private EditText et_shop_introduction;
    private ImageView iv_shop_introduction;
    private TextView tv_main_industry;
    private LinearLayout ll_main_industry;
    private TextView tv_product_style;
    private LinearLayout ll_product_style;
    private EditText et_main_products;
    private ImageView iv_main_products;
    private EditText et_main_customergroups;
    private ImageView iv_main_customergroups;
    private EditText et_main_saleschannels;
    private ImageView iv_main_saleschannels;
    private EditText et_month_sales;
    private ImageView iv_month_sales;
    private EditText et_month_production;
    private ImageView iv_month_production;
    private EditText et_year_sales;
    private ImageView iv_year_sales;
    private EditText et_year_export_num;
    private ImageView iv_year_export_num;
    private TextView tv_local;
    private LinearLayout ll_local;
    private EditText et_detail_address;
    private ImageView iv_detail_address;
    private EditText et_business_contacts;
    private ImageView iv_business_contacts;
    private EditText et_contact_phone;
    private ImageView iv_contact_phone;

    private LinearLayout ll_shop_introduction;
    private LinearLayout ll_month_production;
    private LinearLayout ll_year_export_num;
    private LinearLayout ll_main_saleschannels;
    private LinearLayout ll_month_sales;

    private List<EditText> editTexts;
    private List<ImageView> imageViews;

    private OptionsPickerView pickerView ;

    private ValueAddActivity.VALUE_ADD_TYPE type;

    private List<Industry> industries = new ArrayList<>();
    private int curIndustryPosition = 0;

    private List<Area> shengList = new ArrayList<>();
    private List<List<Area>> shiList = new ArrayList<>();
    private List<List<List<Area>>> quList = new ArrayList<>();

    private Purchaser mPurchaser;
    private Supplier mSupplier;
    private String mainIndustryId;
    private String productStyleId;
    private String quId;


    private ValueAddRequester valueAddRequester;
    private QueryTradeInfoListenerImpl queryTradeInfoListener;
    private class QueryTradeInfoListenerImpl extends DefaultRequestListener implements QueryTradeInfoListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryTradeInfoSuccess(List<Industry> industryList,List<Industry> firstFocus,List<Industry> secondFocus) {
            industries.clear();
            industries.addAll(industryList);
        }
    }
    private SelectAreaAllListenerImpl selectAreaAllListener;
    private class SelectAreaAllListenerImpl extends DefaultRequestListener implements SelectAreaAllListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectAreaAllSuccess(List<Area> areas) {
            //保存到本地
            GlobalDataUtil.putObject(ValueAddApplyActivity.this, Constants.GLOBAL_DATA_AREA,areas,true);

            shengList.clear();
            shengList.addAll(areas);
            for(Area sheng : areas){
                shiList.add(sheng.getAreas());
                List<List<Area>> qu = new ArrayList<>();
                for(Area shi : sheng.getAreas()){
                    qu.add(shi.getAreas());
                }
                quList.add(qu);
            }
        }
    }
    private ApplyRoleListenerImpl applyRoleListener;
    private class ApplyRoleListenerImpl extends DefaultRequestListener implements ApplyRoleListener{

        @Override
        public void onApplyRoleSuccess(String applyId) {
            if(applyId == null){
                switch (type){
                    case PURCHASER:
                        applyId = mPurchaser.getId();
                        break;
                    case SUPPLIER:
                        applyId = mSupplier.getId();
                        break;
                }
            }
//            Intent intent = new Intent(ValueAddApplyActivity.this,ValueAddPaySelectActivity.class);
//            intent.putExtra("applyId",applyId);
//            intent.putExtra("type",type);
//            intent.putExtra("isFirst",true);
//            startActivity(intent);
            finish();
        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            if(errorMsg!=null){
                ToastUtil.show(ValueAddApplyActivity.this,errorMsg);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value_add_apply);

        type = (ValueAddActivity.VALUE_ADD_TYPE)getIntent().getSerializableExtra("type");
        if(type == null){
            finish();
        }

        mPurchaser = (Purchaser)getIntent().getSerializableExtra("purchaser");
        mSupplier = (Supplier)getIntent().getSerializableExtra("supplier");

        valueAddRequester = new ValueAddRequester();
        queryTradeInfoListener = new QueryTradeInfoListenerImpl();
        selectAreaAllListener = new SelectAreaAllListenerImpl();
        applyRoleListener = new ApplyRoleListenerImpl();

        initView();
        initEvent();
        initData();
    }


    private void initView(){
        editTexts = new ArrayList<>();
        imageViews = new ArrayList<>();

        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        if (type== ValueAddActivity.VALUE_ADD_TYPE.SUPPLIER){
            commonTitle.setTitle(R.string.title_supplier_apply);
        }
        submitTv = (TextView)findViewById(R.id.tv_submit);
        et_company = (EditText)findViewById(R.id.et_company);
        iv_company = (ImageView)findViewById(R.id.iv_company);
        et_license_number = (EditText)findViewById(R.id.et_license_number);
        iv_license_number = (ImageView)findViewById(R.id.iv_license_number);
        et_enterprise_type = (EditText)findViewById(R.id.et_enterprise_type);
        iv_enterprise_type = (ImageView)findViewById(R.id.iv_enterprise_type);
        et_employees_num = (EditText)findViewById(R.id.et_employees_num);
        iv_employees_num = (ImageView)findViewById(R.id.iv_employees_num);
        et_shop_introduction = (EditText)findViewById(R.id.et_shop_introduction);
        iv_shop_introduction = (ImageView)findViewById(R.id.iv_shop_introduction);
        tv_main_industry = (TextView)findViewById(R.id.tv_main_industry);
        ll_main_industry = (LinearLayout)findViewById(R.id.ll_main_industry);
        tv_product_style = (TextView)findViewById(R.id.tv_product_style);
        ll_product_style = (LinearLayout)findViewById(R.id.ll_product_style);
        et_main_products = (EditText)findViewById(R.id.et_main_products);
        iv_main_products = (ImageView)findViewById(R.id.iv_main_products);
        et_main_customergroups = (EditText)findViewById(R.id.et_main_customergroups);
        iv_main_customergroups = (ImageView)findViewById(R.id.iv_main_customergroups);
        et_main_saleschannels = (EditText)findViewById(R.id.et_main_saleschannels);
        iv_main_saleschannels = (ImageView)findViewById(R.id.iv_main_saleschannels);
        et_month_sales = (EditText)findViewById(R.id.et_month_sales);
        iv_month_sales = (ImageView)findViewById(R.id.iv_month_sales);
        et_month_production = (EditText)findViewById(R.id.et_month_production);
        iv_month_production = (ImageView)findViewById(R.id.iv_month_production);
        et_year_sales = (EditText)findViewById(R.id.et_year_sales);
        iv_year_sales = (ImageView)findViewById(R.id.iv_year_sales);
        et_year_export_num = (EditText)findViewById(R.id.et_year_export_num);
        iv_year_export_num = (ImageView)findViewById(R.id.iv_year_export_num);
        tv_local = (TextView)findViewById(R.id.tv_local);
        ll_local = (LinearLayout)findViewById(R.id.ll_local);
        et_detail_address = (EditText)findViewById(R.id.et_detail_address);
        iv_detail_address = (ImageView)findViewById(R.id.iv_detail_address);
        et_business_contacts = (EditText)findViewById(R.id.et_business_contacts);
        iv_business_contacts = (ImageView)findViewById(R.id.iv_business_contacts);
        et_contact_phone = (EditText)findViewById(R.id.et_contact_phone);
        iv_contact_phone = (ImageView)findViewById(R.id.iv_contact_phone);

        ll_shop_introduction = (LinearLayout)findViewById(R.id.ll_shop_introduction);
        ll_month_production = (LinearLayout)findViewById(R.id.ll_month_production);
        ll_year_export_num = (LinearLayout)findViewById(R.id.ll_year_export_num);
        ll_main_saleschannels = (LinearLayout)findViewById(R.id.ll_main_saleschannels);
        ll_month_sales = (LinearLayout)findViewById(R.id.ll_month_sales);


        editTexts.add(et_company);
        editTexts.add(et_license_number);
        editTexts.add(et_enterprise_type);
        editTexts.add(et_employees_num);
        editTexts.add(et_shop_introduction);
        editTexts.add(et_main_products);
        editTexts.add(et_main_customergroups);
        editTexts.add(et_main_saleschannels);
        editTexts.add(et_month_sales);
        editTexts.add(et_month_production);
        editTexts.add(et_year_sales);
        editTexts.add(et_year_export_num);
        editTexts.add(et_detail_address);
        editTexts.add(et_business_contacts);
        editTexts.add(et_contact_phone);

        imageViews.add(iv_company);
        imageViews.add(iv_license_number);
        imageViews.add(iv_enterprise_type);
        imageViews.add(iv_employees_num);
        imageViews.add(iv_shop_introduction);
        imageViews.add(iv_main_products);
        imageViews.add(iv_main_customergroups);
        imageViews.add(iv_main_saleschannels);
        imageViews.add(iv_month_sales);
        imageViews.add(iv_month_production);
        imageViews.add(iv_year_sales);
        imageViews.add(iv_year_export_num);
        imageViews.add(iv_detail_address);
        imageViews.add(iv_business_contacts);
        imageViews.add(iv_contact_phone);

        for(int i=0;i<editTexts.size();i++){
            editTexts.get(i).addTextChangedListener(new ValueAddTextWatcher(editTexts.get(i)));
            imageViews.get(i).setOnClickListener(this);
        }

        switch (type){
            case PURCHASER:
                ll_shop_introduction.setVisibility(View.GONE);
                ll_month_production.setVisibility(View.GONE);
                ll_year_export_num.setVisibility(View.GONE);
                ll_main_saleschannels.setVisibility(View.VISIBLE);
                ll_month_sales.setVisibility(View.VISIBLE);
                break;
            case SUPPLIER:
                ll_shop_introduction.setVisibility(View.VISIBLE);
                ll_month_production.setVisibility(View.VISIBLE);
                ll_year_export_num.setVisibility(View.VISIBLE);
                ll_main_saleschannels.setVisibility(View.GONE);
                ll_month_sales.setVisibility(View.GONE);
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
        submitTv.setOnClickListener(this);
        ll_main_industry.setOnClickListener(this);
        ll_product_style.setOnClickListener(this);
        ll_local.setOnClickListener(this);
    }

    private void initData(){
        valueAddRequester.queryTradeInfo(this,queryTradeInfoListener);

        List<Area> areas = (ArrayList<Area>)GlobalDataUtil.getObject(ValueAddApplyActivity.this, Constants.GLOBAL_DATA_AREA);
        if(areas != null && areas.size()>0){//本地有数据就从本地取
            shengList.clear();
            shengList.addAll(areas);
            for(Area sheng : areas){
                shiList.add(sheng.getAreas());
                List<List<Area>> qu = new ArrayList<>();
                for(Area shi : sheng.getAreas()){
                    qu.add(shi.getAreas());
                }
                quList.add(qu);
            }
        }else{
            valueAddRequester.selectAreaAll(this,selectAreaAllListener);
        }
        refreshView();
    }

    private void refreshView(){
        switch (type){
            case PURCHASER:
                if(mPurchaser == null){
                    mPurchaser = new Purchaser();
                    return;
                }
                et_company.setText(mPurchaser.getCompany());
                et_license_number.setText(mPurchaser.getLicenseNumber());
                et_enterprise_type.setText(mPurchaser.getEnterpriseType());
                et_employees_num.setText(mPurchaser.getEmployeesNum());
                mainIndustryId = mPurchaser.getMainIndustryId();
                tv_main_industry.setText(mPurchaser.getMainIndustry());
                productStyleId = mPurchaser.getProductStyleId();
                tv_product_style.setText(mPurchaser.getProductStyle());
                et_main_products.setText(mPurchaser.getMainProducts());
                et_main_customergroups.setText(mPurchaser.getMainCustomerGroups());
                et_employees_num.setText(mPurchaser.getEmployeesNum());
                et_year_sales.setText(mPurchaser.getYearSales());
                quId = mPurchaser.getQuId();
                tv_local.setText(mPurchaser.getArea());
                et_detail_address.setText(mPurchaser.getDetailAddress());
                et_business_contacts.setText(mPurchaser.getBusinessContacts());
                et_contact_phone.setText(mPurchaser.getContactPhone());
                et_main_saleschannels.setText(mPurchaser.getMainSalesChannels());
                et_month_sales.setText(mPurchaser.getMonthSales());
                break;
            case SUPPLIER:
                if(mSupplier == null){
                    mSupplier = new Supplier();
                    return;
                }
                et_company.setText(mSupplier.getCompany());
                et_license_number.setText(mSupplier.getLicenseNumber());
                et_enterprise_type.setText(mSupplier.getEnterpriseType());
                et_employees_num.setText(mSupplier.getEmployeesNum());
                mainIndustryId = mSupplier.getMainIndustryId();
                tv_main_industry.setText(mSupplier.getMainIndustry());
                productStyleId = mSupplier.getProductStyleId();
                tv_product_style.setText(mSupplier.getProductStyleId());
                et_main_products.setText(mSupplier.getMainProducts());
                et_main_customergroups.setText(mSupplier.getMainCustomerGroups());
                et_employees_num.setText(mSupplier.getEmployeesNum());
                et_year_sales.setText(mSupplier.getYearSales());
                quId = mSupplier.getQuId();
                tv_local.setText(mSupplier.getArea());
                et_detail_address.setText(mSupplier.getDetailAddress());
                et_business_contacts.setText(mSupplier.getBusinessContacts());
                et_contact_phone.setText(mSupplier.getContactPhone());
                et_shop_introduction.setText(mSupplier.getShopIntroduction());
                et_month_production.setText(mSupplier.getMonthProduction());
                et_year_export_num.setText(mSupplier.getYearExportNum());
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if(imageViews.indexOf(v)<0){
            switch (v.getId()){
                case R.id.tv_submit:
                    //13或15数字字母
                    if(!et_license_number.getText().toString().trim().matches("^[a-zA-Z0-9]{15,18}$")){
                        ToastUtil.show(this,R.string.tip_license_number);
                        return;
                    }
                    if(!checkParams(type)){
                        ToastUtil.show(this,R.string.tip_please_complete_info);
                        return;
                    }
                    if(!StringUtils.checkMobileNumber(et_contact_phone.getText().toString().trim())
                            && !StringUtils.checkPhoneNumber(et_contact_phone.getText().toString().trim())){
                        ToastUtil.show(this,R.string.tip_enter_right_connect_phone);
                        return;
                    }
                    switch (type){
                        case PURCHASER:
                            getPurchaser();
                            valueAddRequester.applyRole(this,getAccessToken(),type,mPurchaser,null,applyRoleListener);
                            break;
                        case SUPPLIER:
                            getSupplier();
                            valueAddRequester.applyRole(this,getAccessToken(),type,null,mSupplier,applyRoleListener);
                            break;
                    }

                    break;
                case R.id.ll_main_industry:
                    KeyBoardUtil.hideKeyboard(this,ll_main_industry);
                    pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            curIndustryPosition = options1;
                            tv_main_industry.setText(industries.get(options1).getTitle());
                            mainIndustryId = industries.get(options1).getId();

                            //选择主营行业后清空产品风格
                            tv_product_style.setText("");
                            productStyleId = null;
                        }
                    }).setTitleText(getString(R.string.label_choose_main_industry)).build();
                    pickerView.setPicker(industries);
                    pickerView.show();
                    break;
                case R.id.ll_product_style:
                    KeyBoardUtil.hideKeyboard(this,ll_product_style);
                    pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_product_style.setText(industries.get(curIndustryPosition).getProductStyles().get(options1).getTitle());
                            productStyleId = industries.get(curIndustryPosition).getProductStyles().get(options1).getId();
                        }
                    }).setTitleText(getString(R.string.label_choose_product_style)).build();
                    pickerView.setPicker(industries.get(curIndustryPosition).getProductStyles());
                    pickerView.show();
                    break;
                case R.id.ll_local:
                    KeyBoardUtil.hideKeyboard(this,ll_local);
                    pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

                        @Override
                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                            tv_local.setText(shengList.get(options1).getName()+shiList.get(options1).get(options2).getName()+quList.get(options1).get(options2).get(options3).getName());
                            quId = quList.get(options1).get(options2).get(options3).getId();
                        }
                    }).setTitleText(getString(R.string.label_choose_local)).build();
                    pickerView.setPicker(shengList,shiList,quList);
                    pickerView.show();
                    break;
            }
        }else{
            editTexts.get(imageViews.indexOf(v)).setText("");
        }
    }

    private class ValueAddTextWatcher implements TextWatcher{

        EditText editText;

        public ValueAddTextWatcher(EditText editText){
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length()>0){
                imageViews.get(editTexts.indexOf(editText)).setVisibility(View.VISIBLE);
            }else{
                imageViews.get(editTexts.indexOf(editText)).setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean checkParams(ValueAddActivity.VALUE_ADD_TYPE type){
        switch (type){
            case PURCHASER:
                if(!"".equals(et_company.getText().toString().trim())
                        && !"".equals(et_license_number.getText().toString().trim())
                        && !"".equals(et_enterprise_type.getText().toString().trim())
                        && !"".equals(et_employees_num.getText().toString().trim())
                        && !"".equals(et_main_products.getText().toString().trim())
                        && !"".equals(et_main_customergroups.getText().toString().trim())
                        && !"".equals(et_employees_num.getText().toString().trim())
                        && !"".equals(et_year_sales.getText().toString().trim())
                        && !"".equals(et_detail_address.getText().toString().trim())
                        && !"".equals(et_business_contacts.getText().toString().trim())
                        && !"".equals(et_contact_phone.getText().toString().trim())
                        && !"".equals(et_main_saleschannels.getText().toString().trim())
                        && !"".equals(et_month_sales.getText().toString().trim())
                        && mainIndustryId != null
                        && productStyleId != null
                        && quId != null
                        ){
                    return true;
                }
                break;
            case SUPPLIER:
                if(!"".equals(et_company.getText().toString().trim())
                        && !"".equals(et_license_number.getText().toString().trim())
                        && !"".equals(et_enterprise_type.getText().toString().trim())
                        && !"".equals(et_employees_num.getText().toString().trim())
                        && !"".equals(et_main_products.getText().toString().trim())
                        && !"".equals(et_main_customergroups.getText().toString().trim())
                        && !"".equals(et_employees_num.getText().toString().trim())
                        && !"".equals(et_year_sales.getText().toString().trim())
                        && !"".equals(et_detail_address.getText().toString().trim())
                        && !"".equals(et_business_contacts.getText().toString().trim())
                        && !"".equals(et_contact_phone.getText().toString().trim())
                        && !"".equals(et_shop_introduction.getText().toString().trim())
                        && !"".equals(et_month_production.getText().toString().trim())
                        && !"".equals(et_year_export_num.getText().toString().trim())
                        && mainIndustryId != null
                        && productStyleId != null
                        && quId != null
                        ){
                    return true;
                }
                break;
        }

        return false;
    }

    private void getPurchaser(){
        mPurchaser.setCompany(et_company.getText().toString());
        mPurchaser.setLicenseNumber(et_license_number.getText().toString());
        mPurchaser.setEnterpriseType(et_enterprise_type.getText().toString());
        mPurchaser.setEmployeesNum(et_employees_num.getText().toString());
        mPurchaser.setMainIndustryId(mainIndustryId);
        mPurchaser.setProductStyleId(productStyleId);
        mPurchaser.setMainProducts(et_main_products.getText().toString());
        mPurchaser.setMainCustomerGroups(et_main_customergroups.getText().toString());
        mPurchaser.setEmployeesNum(et_employees_num.getText().toString());
        mPurchaser.setYearSales(et_year_sales.getText().toString());
        mPurchaser.setQuId(quId);
        mPurchaser.setDetailAddress(et_detail_address.getText().toString());
        mPurchaser.setBusinessContacts(et_business_contacts.getText().toString());
        mPurchaser.setContactPhone(et_contact_phone.getText().toString());
        mPurchaser.setMainSalesChannels(et_main_saleschannels.getText().toString());
        mPurchaser.setMonthSales(et_month_sales.getText().toString());
    }

    private void getSupplier(){
        mSupplier.setCompany(et_company.getText().toString());
        mSupplier.setLicenseNumber(et_license_number.getText().toString());
        mSupplier.setEnterpriseType(et_enterprise_type.getText().toString());
        mSupplier.setEmployeesNum(et_employees_num.getText().toString());
        mSupplier.setMainIndustryId(mainIndustryId);
        mSupplier.setProductStyleId(productStyleId);
        mSupplier.setMainProducts(et_main_products.getText().toString());
        mSupplier.setMainCustomerGroups(et_main_customergroups.getText().toString());
        mSupplier.setEmployeesNum(et_employees_num.getText().toString());
        mSupplier.setYearSales(et_year_sales.getText().toString());
        mSupplier.setQuId(quId);
        mSupplier.setDetailAddress(et_detail_address.getText().toString());
        mSupplier.setBusinessContacts(et_business_contacts.getText().toString());
        mSupplier.setContactPhone(et_contact_phone.getText().toString());
        mSupplier.setShopIntroduction(et_shop_introduction.getText().toString());
        mSupplier.setMonthProduction(et_month_production.getText().toString());
        mSupplier.setYearExportNum(et_year_export_num.getText().toString());
    }
}
