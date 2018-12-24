package com.zhejiang.haoxiadan.ui.activity.my;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.AddNewAddressListener;
import com.zhejiang.haoxiadan.business.request.my.EditCurrentAddressListener;
import com.zhejiang.haoxiadan.business.request.my.InitAddressListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.requestData.in.AreaInfoBean;
import com.zhejiang.haoxiadan.model.requestData.in.AreaInfoChildBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class AddAndEditLocationActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_call)
    EditText edtCall;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_select_address)
    RelativeLayout rlSelectAddress;
    @BindView(R.id.edt_details_addr)
    EditText edtDetailsAddr;
    @BindView(R.id.cart_rb_q)
    CheckBox cartRbQ;
    @BindView(R.id.tv_save)
    TextView tvSave;


    private String flag ;
    private int defaultVal = 0 ;
    private String areaId ,id ;
    ProgressDialog dialog2 ;
    private CurrentUserAddressBean bean ;

    private List<AreaInfoChildBean> options1Items = new ArrayList<>();
    private List<List<AreaInfoChildBean>> options2Items = new ArrayList<>();
    private List<List<List<AreaInfoChildBean>>> options3Items = new ArrayList<>();

    private List<String> list_data = new ArrayList<>();
    private List<List<String>> list_data_two = new ArrayList<>();
    private List<List<List<String>>> list_data_third = new ArrayList<>();

    private UserRequester requester = new UserRequester();
    private InitAddressImp initAddressImp = new InitAddressImp();
    private AddNewAddressImp newAddressImp = new AddNewAddressImp();
    private EditCurrentAddressImp editCurrentAddressImp = new EditCurrentAddressImp();
    private class InitAddressImp extends DefaultRequestListener implements InitAddressListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getAddress(String bean) {
            initAddress(bean);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_and_edit_location_layout);
        ButterKnife.bind(this);

        flag = getIntent().getStringExtra("flag");
        initView();

        initArea();
    }

    private void initView() {
        if (flag.equals("1")) {
            tvTitle.setText("新增地址");
        }else if (flag.equals("2")){
            tvTitle.setText("编辑地址");
            bean = (CurrentUserAddressBean) getIntent().getSerializableExtra("details");
            edtName.setText(bean.getTrueName());
            edtPhone.setText(bean.getMobile());
            edtDetailsAddr.setText(bean.getArea_info());
            tvAddress.setText(bean.getProvinceName()+bean.getCityName()+bean.getAreaName());
            areaId = bean.getAreaId();
            id = String.valueOf(bean.getId());
            if (bean.getDefault_val()==1){
                cartRbQ.setChecked(true);
                defaultVal = 1 ;
            }else {
                cartRbQ.setChecked(false);
                defaultVal = 0 ;
            }
        }

        cartRbQ.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    defaultVal = 1 ;
                }else {
                    defaultVal = 0;
                }
            }
        });
    }

    @OnClick({R.id.iv_left,R.id.tv_save,R.id.rl_select_address})
    public void onclick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.rl_select_address :
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(getCurrentFocus()
                                        .getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                initCityDialog();
                break;
            case R.id.tv_save :

                if(!checkEmpty()){
                    return;
                }

                if (flag.equals("1")){
                    requester.addNewAddress(context,edtName.getText().toString(),edtDetailsAddr.getText().toString(),edtPhone.getText().toString(),edtCall.getText().toString(),defaultVal,areaId,getAccessToken(),newAddressImp);
                }else if (flag.equals("2")){
                    requester.editCurrentAddress(context,edtName.getText().toString(),edtDetailsAddr.getText().toString(),edtPhone.getText().toString(),edtCall.getText().toString(),defaultVal,areaId,getAccessToken(),id,editCurrentAddressImp);
                }
                break;
        }
    }

    private boolean checkEmpty() {
        if (edtName.getText().toString().length() == 0) {
            Toast.makeText(context,"收货人不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!StringUtils.checkChar_CEN50(edtName.getText().toString())){
            Toast.makeText(context,"收货人只可是中文、字母、数字",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtPhone.getText().toString().length() == 0) {
            Toast.makeText(context,"手机号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!StringUtils.checkMobileNumber(edtPhone.getText().toString())){
            Toast.makeText(context,"手机号格式错误",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtDetailsAddr.getText().toString().length() == 0) {
            Toast.makeText(context,"地址不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (edtDetailsAddr.getText().toString().length()<5) {
            Toast.makeText(context,"收货人地址长度不能大于50个字符,小于5个字符",Toast.LENGTH_SHORT).show();
            return false;
        }

        if(areaId == null || "".equals(areaId)){
            Toast.makeText(context,"请选择所在地区",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    private void initCityDialog() {
        OptionsPickerView pickerView = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx = options1Items.get(options1).getAreaName()+
                        options2Items.get(options1).get(options2).getAreaName()+
                        options3Items.get(options1).get(options2).get(options3).getAreaName();

                areaId = options3Items.get(options1).get(options2).get(options3).getId();

                if (tx!=null&&!tx.equals(""))
                {
                    tvAddress.setText(tx);

                }
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pickerView.setPicker(list_data, list_data_two,list_data_third);//三级选择器
        pickerView.show();
    }
    private void initAddress(String areaall) {
        dialog2.dismiss();
//        String areaall = (String) SharedPreferencesUtil.get(this, Constants.GLOBAL_DATA_AREA,"");
        AreaInfoBean bean = gson.fromJson(areaall,AreaInfoBean.class);
        options1Items.addAll(bean.getData());
        Log.d("HTTP__",gson.toJson(options1Items));
            for (int q = 0 ; q<options1Items.size();q++){
                list_data.add(options1Items.get(q).getAreaName());
            }


            for (int i = 0 ;i<options1Items.size();i++){
                List<AreaInfoChildBean> CityList = new ArrayList<>();//该省的城市列表（第二级）
                List<List<AreaInfoChildBean>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

                List<String> c_city = new ArrayList<>();
                List<List<String>> p_city = new ArrayList<>();


                for (int c = 0 ;c<options1Items.get(i).getChilds().size();c++) {
                    AreaInfoChildBean childBean = options1Items.get(i).getChilds().get(c);
                    CityList.add(childBean);//添加城市

                    c_city.add(options1Items.get(i).getChilds().get(c).getAreaName());


                    List<AreaInfoChildBean> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                    List<String> c_list = new ArrayList<>();

                    //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                    if (options1Items.get(i).getChilds().get(c).getAreaName() == null
                            || options1Items.get(i).getChilds().get(c).getChilds().size() == 0) {
//                        City_AreaList.add(new AreaInfoChildBean("", "", null));
//                        c_list.add("");
                    } else {
                        for (int d = 0; d < options1Items.get(i).getChilds().get(c).getChilds().size(); d++) {//该城市对应地区所有数据
                            AreaInfoChildBean AreaName = options1Items.get(i).getChilds().get(c).getChilds().get(d);

                            City_AreaList.add(AreaName);//添加该城市所有地区数据
                            c_list.add(AreaName.getAreaName());
                        }
                        Province_AreaList.add(City_AreaList);//添加该省所有地区数据
                        p_city.add(c_list);
                    }
                }

                /**
                 * 添加城市数据
                 */
                options2Items.add(CityList);

                /**
                 * 添加地区数据
                 */
                options3Items.add(Province_AreaList);

                list_data_two.add(c_city);
                list_data_third.add(p_city);
            }

            if (options2Items.size()>0&&options3Items.size()>0&&options1Items.size()>0){
                SharedPreferencesUtil.put(context,Constants.GLOBAL_DATA_AREA,areaall);
            }
        }

    private void initArea(){
        String address = (String) SharedPreferencesUtil.get(context,Constants.GLOBAL_DATA_AREA,"");
        dialog2 = ProgressDialog.show(this, "提示", "正在加载数据");
        if (address.equals("")) {
            requester.initAddress(context, initAddressImp);
        }else {
            initAddress(address);
        }
    }

    private class AddNewAddressImp extends DefaultRequestListener implements AddNewAddressListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSuccess() {
            ToastUtil.show(context,"成功");
            finish();
        }
    }

    private class EditCurrentAddressImp extends DefaultRequestListener implements EditCurrentAddressListener{

        @Override
        public void onSuccess() {
            finish();
        }

        @Override
        protected void onRequestFail() {

        }
    }
}
