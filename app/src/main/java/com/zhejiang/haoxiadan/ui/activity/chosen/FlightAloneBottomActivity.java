package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.FlightAloneAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
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
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/30.
 */

public class FlightAloneBottomActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.goods_details_tl)
    TabLayout goodsDetailsTl;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.flight_ls)
    ListView flightLs;
    @BindView(R.id.tv_go_ping)
    TextView tvGoPing;
    @BindView(R.id.tv_go_cart)
    TextView tvGoCart;
    @BindView(R.id.tv_price)
    TextView tv_tv_Price;
    @BindView(R.id.tv_get_num)
    TextView tvGetNum;
    @BindView(R.id.tv_get_price)
    TextView tvGetPrice;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;

    private int position = 0;

    private List<FlightLsItemBean> data = new ArrayList<>();
    private CommonAdapter<FlightLsItemBean> adapter;

    private List<GetGoodsIntData> all_data = new ArrayList<>();

    private ArrayList<FlightLsItemData> new_data = new ArrayList<>();

    private String goodsName, outerId,  oldId = "", goodsId, storeId ,goodsMainPhoto;
    private PostGoodsStyleBean styleBean;
    private PostTiredPriceData tiredPriceData;
    ArrayList<String> arrayList = new ArrayList<String>();

    private int allCount = 0 ,goodsNumType ,showOldCount = 0;
    private long endTime;
    private String goodsType;
    private int goodsLimit;

    private FlightAloneAdapter aloneAdapter ;

    private List<String> outerIdData = new ArrayList<>();
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
        setContentView(R.layout.flight_alone_bottom_layout);
        ButterKnife.bind(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EventBus.getDefault().register(this);

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

        aloneAdapter = new FlightAloneAdapter(data,context,tiredPriceData,goodsDetailsTl,all_data ,tv_tv_Price,tvGetNum,tvGetPrice,new_data,goodsType,goodsLimit,goodsNumType);
        flightLs.setAdapter(aloneAdapter);

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


    @Override
    protected void onResume() {
        super.onResume();
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

    private void initData(int position) {

        for (int i = 0; i < new_data.size(); i++) {
            if (i == position) {
                ImageLoaderUtil.displayImage(all_data.get(i).getSpecpropertyImg(),ivIcon);

                outerId = String.valueOf(all_data.get(i).getSpecpropertyId());
                data.clear();
                data.addAll(new_data.get(i).getSpecpropertyList());

                aloneAdapter.setData(data);
            }

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

    @Subscribe
    public void onEvent(PostGoodsStyleBean Bean) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

    }

}
