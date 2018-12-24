package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.AddToCartListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemBean;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemData;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsStyleBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostTiredPriceData;
import com.zhejiang.haoxiadan.model.requestData.in.chose.AddToCartBean;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.third.network.OkHttpClientManager;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.SureOrderActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.ui.view.ExtendedEditText;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhejiang.haoxiadan.MyApplication.context;

/**
 * Created by qiuweiyu on 2018/6/22.
 */

public class NewFlightBottomActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.tv_price)
    TextView tv_tv_Price;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.goods_details_tl)
    TabLayout goodsDetailsTl;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.rl_tab)
    RelativeLayout rlTab;
    @BindView(R.id.flight_ls)
    LinearLayout flightLs;
    @BindView(R.id.sv)
    ScrollView sv;
    @BindView(R.id.tv_get_num)
    TextView tvGetNum;
    @BindView(R.id.tv_get_price)
    TextView tvGetPrice;
    @BindView(R.id.rl_rl_test)
    RelativeLayout rlRlTest;
    @BindView(R.id.tv_go_ping)
    TextView tvGoPing;
    @BindView(R.id.tv_go_cart)
    TextView tvGoCart;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;

    private String goodsName,   oldId = "", goodsId, storeId ,goodsMainPhoto;
    private PostGoodsStyleBean styleBean;
    private PostTiredPriceData tiredPriceData;
    ArrayList<String> arrayList = new ArrayList<String>();
    private  int goodsNumType ,outerId ,goodsLimit,position = 0;
    private long endTime;
    private String goodsType;
    private List<FlightLsItemData> new_data = new ArrayList<>();
    private List<GetGoodsIntData> all_data = new ArrayList<>();
    private List<FlightLsItemBean> data = new ArrayList<>();
    private List<AddToCartBean> post_data = new ArrayList<>();

    private GoodsRequester requester = new GoodsRequester();
    private AddToCartImp cartImp = new AddToCartImp();

    private class AddToCartImp extends DefaultRequestListener implements AddToCartListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context, errorMsg);
        }

        @Override
        public void success() {
            ToastUtil.show(context, getString(R.string.add_to_cart_success));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.new_flight_bottom_layout);
        ButterKnife.bind(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        initView();
        initEvent();
    }

    private void initView() {

        data.clear();
        all_data.clear();

        goodsMainPhoto = getIntent().getStringExtra("goodsMainPhoto");
        goodsName = getIntent().getStringExtra("goodsName");
        styleBean = (PostGoodsStyleBean) getIntent().getSerializableExtra("style");
        tiredPriceData = (PostTiredPriceData) getIntent().getSerializableExtra("tiredPrice");
        goodsId = getIntent().getStringExtra("goodsId");
        storeId = getIntent().getStringExtra("storeId");
        goodsNumType = getIntent().getIntExtra("goodsNumType",0);
        endTime = getIntent().getLongExtra("endTime", 0);
        new_data = (ArrayList<FlightLsItemData>) getIntent().getSerializableExtra("new_data");
        goodsType = getIntent().getStringExtra("goodsType");

        if (goodsType!=null) {
            if (goodsType.equals("0")) {
                tvGoPing.setText("立即购买");
                goodsLimit = getIntent().getIntExtra("goodsLimit", 0);
            } else if (goodsType.equals("1")) {
                tvGoPing.setText("立即拼单");
            }
        }

        try {
            tv_tv_Price.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size() - 1).getPrice()));
        } catch (Exception e) {
            return;
        }
        tvContentTitle.setText(goodsName);
        all_data.addAll(styleBean.getGoodsInvent());

        if (new_data.size()<=0) {
            if (all_data.size() > 0) {
                filterData();
            }
        }
        for (int i = 0; i < new_data.size(); i++) {
            goodsDetailsTl.addTab(goodsDetailsTl.newTab().setText(new_data.get(i).getSpecpropertyName()));
        }
        arrayList.add("顔色");
        arrayList.add("尺码");

        initData(0);

    }

    @OnClick({R.id.iv_close, R.id.iv_left, R.id.iv_right, R.id.tv_go_cart, R.id.tv_go_ping})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                EventBus.getDefault().post(new_data);
                finish();
                break;
            case R.id.iv_left:
                if (position == 0) {
                    return;
                } else {
                    position = position - 1;
                    goodsDetailsTl.getTabAt(position).select();
                }

                break;
            case R.id.iv_right:
                if (position >= 0 && position < goodsDetailsTl.getTabCount()) {
                    goodsDetailsTl.getTabAt(position).select();
                    position = position + 1;
                }
                break;
            case R.id.tv_go_cart:
                if (new_data.size()>0) {
                    if (goodsType.equals("0")) {
                        if (NumberUtils.getIntFromString(tvGetNum.getText().toString()) >= goodsLimit) {
                            filterData(new_data, "1");
                        } else {
                            ToastUtil.show(context, "此商品最小起订量" + goodsLimit+"件");
                        }
                    }else {
                        filterData(new_data, "1");
                    }
                }else {
                    ToastUtil.show(context,getString(R.string.add_to_cart_failed));
                }
                break;
            case R.id.tv_go_ping:
                if (new_data.size()>0) {
                    if (goodsType.equals("0")) {
                        if (NumberUtils.getIntFromString(tvGetNum.getText().toString()) >= goodsLimit) {
                            filterData(new_data, "2");
                        } else {
                            ToastUtil.show(context, "此商品最小起订量" + goodsLimit+"件");
                        }
                    }else {
                        filterData(new_data, "2");
                    }
                }else {
                    ToastUtil.show(context,getString(R.string.go_ping_cart_failed));
                }
                break;
        }
    }


    public void filterData(){
        new_data.clear();
        for (int i = 0; i < all_data.size(); i++) {
            FlightLsItemData itemData = new FlightLsItemData();
            itemData.setSpecpropertyName(all_data.get(i).getSpecpropertyName());
            itemData.setSpecpropertyId(all_data.get(i).getSpecpropertyId());
            itemData.setSpecpropertyCrowdSum(all_data.get(i).getSpecpropertyCrowdSum());
            itemData.setSpecpropertyImg(all_data.get(i).getSpecpropertyImg());
            List<FlightLsItemBean> flightLsItemBeanList = new ArrayList<>();
            try {
                for (int j = 0; j < all_data.get(i).getSpecpropertyList().size(); j++) {
                    FlightLsItemBean itemBean = new FlightLsItemBean();
                    itemBean.setSpecpropertyName(all_data.get(i).getSpecpropertyList().get(j).getSpecpropertyName());
                    itemBean.setSpecpropertyId(all_data.get(i).getSpecpropertyList().get(j).getSpecpropertyId());
                    itemBean.setSpecpropertyInventCount(all_data.get(i).getSpecpropertyList().get(j).getSpecpropertyInventCount());
                    itemBean.setSpecpropertySmallCount(all_data.get(i).getSpecpropertyList().get(j).getSpecpropertySmallCount());
                    itemBean.setPrice(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size()-1).getPrice());
                    itemBean.setCheckOutCount(all_data.get(i).getSpecpropertyList().get(j).getCheckOutCount());
                    flightLsItemBeanList.add(itemBean);
                }
                itemData.setSpecpropertyList(flightLsItemBeanList);
                new_data.add(itemData);
            }catch (Exception e){

            }
        }

    }

    private void filterData(List<FlightLsItemData> new_new_data,String type) {
        post_data.clear();
        if (type.equals("1")) {
            for (int i = 0; i < new_new_data.size(); i++) {
                for (int j = 0; j < new_new_data.get(i).getSpecpropertyList().size(); j++) {
                    if (new_new_data.get(i).getSpecpropertyList().get(j).getCount()>0) {
                        AddToCartBean cartBean = new AddToCartBean();
                        cartBean.setCount(new_new_data.get(i).getSpecpropertyList().get(j).getCount());
                        cartBean.setCartGsp(new_new_data.get(i).getSpecpropertyList().get(j).getOuterId() + "," + new_new_data.get(i).getSpecpropertyList().get(j).getSpecpropertyId());
                        cartBean.setGoodsId(goodsId);
                        cartBean.setGoodsNumType(goodsNumType);
                        List<String> goodsSpecContent = new ArrayList<String>();
                        goodsSpecContent.add(new_new_data.get(i).getSpecpropertyList().get(j).getColor());
                        goodsSpecContent.add(new_new_data.get(i).getSpecpropertyList().get(j).getSpecpropertyName());
                        cartBean.setGoodsSpecContent(goodsSpecContent);
                        cartBean.setGoodsSpecName(arrayList);
                        post_data.add(cartBean);
                    }
                }
            }
            if (post_data.size()>0) {
                if (!getAccessToken().equals("")) {
                    User.USER_TYPE userType = (User.USER_TYPE) GlobalDataUtil.getObject(context, Constants.GLOBAL_DATA_KEY_USER_TYPE);
                    if (userType == User.USER_TYPE.BUYER) {
                        requester.addToCart(context, getAccessToken(), storeId, goodsType,post_data, cartImp);
                    } else if (userType == User.USER_TYPE.COMMON){
                        final DeleteDialog dialog = new DeleteDialog(context,"提示",getString(R.string.tip_only_purchaser_can_buy),"立即升级");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                dialog.dismiss();
                                startActivity(new Intent(context, ValueAddActivity.class));

                            }
                        });
                    }else {
                        ToastUtil.show(context,getString(R.string.sorry_not_buy));
                    }
                } else {
                    ToastUtil.show(context, getString(R.string.no_login_string));
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }else {
                ToastUtil.show(context,getString(R.string.select_goods_style_string));
            }
        }else {
            CartGoods cartGoods = new CartGoods();
            cartGoods.setId(goodsId);
            cartGoods.setIcon(goodsMainPhoto);
            cartGoods.setTitle(goodsName);
            cartGoods.setStoreId(storeId);
            cartGoods.setTogetherEndMillis(endTime);
            ArrayList<LevelPrice> levelPrices = new ArrayList<>();
            for (int i = 0 ;i<tiredPriceData.getTired_data_list().size();i++){
                LevelPrice levelPrice = new LevelPrice();
                String[] strings = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                levelPrice.setMinNum(NumberUtils.getIntFromString(strings[0]));
                levelPrice.setMaxNum(NumberUtils.getIntFromString(strings[1]));
                levelPrice.setPrice(NumberUtils.getDoubleFromString(tiredPriceData.getTired_data_list().get(i).getPrice()));
                levelPrices.add(levelPrice);
            }
            cartGoods.setLevelPrices(levelPrices);
            cartGoods.setGoodsType(goodsType);
            List<CartGoodsStyle> cartGoodsStyleList = new ArrayList<>();
            for (int i = 0; i < new_new_data.size(); i++) {
                for (int j = 0; j < new_new_data.get(i).getSpecpropertyList().size(); j++) {
                    if (new_new_data.get(i).getSpecpropertyList().get(j).getCount() > 0) {
                        CartGoodsStyle style = new CartGoodsStyle();
                        style.setGoodsGsp(outerId + "," + new_new_data.get(i).getSpecpropertyList().get(j).getSpecpropertyId());
                        style.setMainStyle("颜色 :" + new_new_data.get(i).getSpecpropertyList().get(j).getColor());
                        style.setSecondStyle("尺码" + new_new_data.get(i).getSpecpropertyList().get(j).getSpecpropertyName());
                        style.setBuyNum(new_new_data.get(i).getSpecpropertyList().get(j).getCount());
                        style.setPrice(NumberUtils.getDoubleFromString(tv_tv_Price.getText().toString().substring(1)));
                        style.setGoodsSpecName(arrayList);
                        ArrayList<String> stringArrayList = new ArrayList<>();
                        stringArrayList.add(new_new_data.get(i).getSpecpropertyList().get(j).getColor());
                        stringArrayList.add(new_new_data.get(i).getSpecpropertyList().get(j).getSpecpropertyName());
                        style.setGoodsSpecContent(stringArrayList);
                        if(goodsNumType == 0){
                            style.setType(CartGoodsStyle.GOODS_TYPE.FUTURES);
                        }else if(goodsNumType == 1){
                            style.setType(CartGoodsStyle.GOODS_TYPE.STOCK);
                        }
                        cartGoodsStyleList.add(style);
                        cartGoods.setGoodsStyles(cartGoodsStyleList);
                    }
                }
            }
            ArrayList<CartGoods> cartGoodsArrayList = new ArrayList<>();
            cartGoodsArrayList.add(cartGoods);
            if (gson.toJson(cartGoodsArrayList).indexOf("goodsStyles")>0) {
                if (!getAccessToken().equals("")) {
                    User.USER_TYPE userType = (User.USER_TYPE) GlobalDataUtil.getObject(context, Constants.GLOBAL_DATA_KEY_USER_TYPE);
                    if (userType == User.USER_TYPE.BUYER) {
                        Intent intent = new Intent(context, SureOrderActivity.class);
                        intent.putExtra("data", cartGoodsArrayList);
                        startActivity(intent);
                        finish();
                    } else if (userType == User.USER_TYPE.COMMON){
                        final DeleteDialog dialog = new DeleteDialog(context,"提示",getString(R.string.tip_only_purchaser_can_buy),"立即升级");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                dialog.dismiss();
                                startActivity(new Intent(context, ValueAddActivity.class));

                            }
                        });
                    }else {
                        ToastUtil.show(context,getString(R.string.sorry_not_buy));
                    }
                } else {
                    ToastUtil.show(context, getString(R.string.no_login_string));
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
            }else {
                ToastUtil.show(context,getString(R.string.select_goods_style_string));
            }
        }
    }

    private void initEvent() {
        goodsDetailsTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                initData(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initData(final int position) {
        this.position = position ;
        int allCount = 0 ;
        for (int i = 0; i < new_data.size(); i++) {
            if (i == position) {
                ImageLoaderUtil.displayImage(all_data.get(i).getSpecpropertyImg(),ivIcon);
                outerId = all_data.get(i).getSpecpropertyId();
                data.clear();
                data.addAll(new_data.get(i).getSpecpropertyList());
            }
            for (int m = 0 ;m<new_data.get(i).getSpecpropertyList().size();m++){
                allCount = allCount + new_data.get(i).getSpecpropertyList().get(m).getCount();
            }
        }
        flightLs.removeAllViews();
        for ( int j = 0 ;j<data.size();j++){
            View view = LayoutInflater.from(context).inflate(R.layout.new_flight_alone_ls_item,null);
            final EditText edt_num = (EditText) view.findViewById(R.id.edt_num);
            ImageView iv_add = (ImageView) view.findViewById(R.id.iv_add);
            ImageView iv_reduce = (ImageView) view.findViewById(R.id.iv_reduce);
            TextView tv_num = (TextView) view.findViewById(R.id.tv_all_num);
            TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
            TextView tv_style = (TextView) view.findViewById(R.id.tv_style);

            if (goodsType.equals("0")&&NumberUtils.getIntFromString(data.get(j).getSpecpropertyInventCount())>0){
                tv_num.setText("库存：" + NumberUtils.getIntFromString(data.get(j).getSpecpropertyInventCount()));
            }
            if (goodsType.equals("1")){
                tv_num.setText("已拼：" + data.get(j).getCheckOutCount());
            }

            tv_style.setText(data.get(j).getSpecpropertyName());
            if (allCount == 0 ){
                edt_num.setText(""+data.get(j).getCount());
                tv_price.setText(context.getString(R.string.label_money)+ NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size()-1).getPrice()));
                data.get(j).setPrice(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size()-1).getPrice());
            }else {
                edt_num.setText(String.valueOf(data.get(j).getCount()));
                tv_price.setText(context.getString(R.string.label_money) + NumberUtils.formatToDouble(data.get(j).getPrice()));
                data.get(j).setPrice(data.get(j).getPrice());
            }
            tvGetNum.setText(""+allCount);tvGetPrice.setText(""+NumberUtils.formatToDouble(""+allCount*Double.parseDouble(data.get(j).getPrice())));

            flightLs.addView(view);

            final int finalJ = j ;
            iv_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = NumberUtils.getIntFromString(edt_num.getText().toString());
                    if (goodsType.equals("0")){ //普通商品
                        current = current +1;
                        if (goodsNumType!=0) {
                            if (current <= NumberUtils.getIntFromString(data.get(finalJ).getSpecpropertyInventCount())) {

                            } else {
                                current = NumberUtils.getIntFromString(data.get(finalJ).getSpecpropertyInventCount());
                                Toast.makeText(context, "超过库存", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else { //拼单商品
                        if (NumberUtils.getIntFromString(data.get(finalJ).getSpecpropertySmallCount())>0){
                            if (current<NumberUtils.getIntFromString(data.get(finalJ).getSpecpropertySmallCount())){
                                current = NumberUtils.getIntFromString(data.get(finalJ).getSpecpropertySmallCount());
                            }else {
                                current = current+1;
                            }
                        }
                    }
                    data.get(finalJ).setColor(goodsDetailsTl.getTabAt(goodsDetailsTl.getSelectedTabPosition()).getText().toString());
                    data.get(finalJ).setOuterId(outerId);
                    data.get(finalJ).setCount(current);
                    edt_num.setText(""+data.get(finalJ).getCount());

                    doTriedPrice(finalJ,data);

                    initData(position);
                }
            });

            iv_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current = NumberUtils.getIntFromString(edt_num.getText().toString());
                    current = current -1 ;
                    if (current<0){
                        current=0;
                    }
                    data.get(finalJ).setColor(goodsDetailsTl.getTabAt(goodsDetailsTl.getSelectedTabPosition()).getText().toString());
                    data.get(finalJ).setOuterId(outerId);
                    data.get(finalJ).setCount(current);
                    edt_num.setText(""+data.get(finalJ).getCount());
                    doTriedPrice(finalJ,data);

                    initData(position);
                }
            });
        }

    }

    public int getAllCount(){
        int allCount = 0 ;
        for (int i = 0; i < new_data.size(); i++) {
            for (int m = 0 ;m<new_data.get(i).getSpecpropertyList().size();m++){
                allCount = allCount + new_data.get(i).getSpecpropertyList().get(m).getCount();
            }
        }
        return allCount;
    }
    public void initPrice(String price , List<FlightLsItemData> m_list){
        for (int i = 0 ; i<m_list.size();i++){
            for (int m = 0 ;m<new_data.get(i).getSpecpropertyList().size();m++) {
                m_list.get(i).getSpecpropertyList().get(m).setPrice(price);
            }
        }
    }

    public void doTriedPrice(int finalJ, List<FlightLsItemBean> mdata){
        for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
            String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
            if (sArray.length>0) {
                if (sArray[1].indexOf("以上")<0) {
                    if (getAllCount() >= NumberUtils.getIntFromString(sArray[0]) && getAllCount() <= NumberUtils.getIntFromString(sArray[1])) {
                        tv_tv_Price.setText(context.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                        mdata.get(finalJ).setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                        initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), new_data);
                    }
                }else {
                    if (getAllCount()>= NumberUtils.getIntFromString(sArray[0])){
                        tv_tv_Price.setText(context.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                        mdata.get(finalJ).setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                        initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), new_data);
                    }
                }
            }
        }
    }

}
