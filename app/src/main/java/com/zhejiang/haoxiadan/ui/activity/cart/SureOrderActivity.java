package com.zhejiang.haoxiadan.ui.activity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.AddOrderFormListener;
import com.zhejiang.haoxiadan.business.request.cart.CartRequester;
import com.zhejiang.haoxiadan.business.request.cart.GetDefaultAddressListener;
import com.zhejiang.haoxiadan.business.request.cart.GetshipPriceListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectBalanceAllListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.my.LocatianManagerActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddApplyActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.SureOrderListAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 确认订单
 */
public class SureOrderActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private RelativeLayout addressRl;
    private TextView nameTv;
    private TextView mobileTv;
    private TextView addressTv;
    private TextView submitTv;
    private TextView totalLoanTv;
    private TextView totalShipTv;
    private TextView totalPriceTv;
    private NoScrollListView cartLV;
    private ScrollView scrollView;

    private TipDialog tipDialog;

    private SureOrderListAdapter adapter;
    private List<CartGoods> cartGoodses;
    private Address mAddress;
    private TextView addressTipTv;

    private double totalShipPrice = 0;
    private double totalPrice = 0;

    private OrderRequester orderRequester;
    private AddOrderFormListenerImpl addOrderFormListener;
    private class AddOrderFormListenerImpl extends DefaultRequestListener implements AddOrderFormListener{

        @Override
        public void onAddOrderFormSuccess(String orderId) {
            Intent intent = new Intent(SureOrderActivity.this,OrderPaySelectActivity.class);
            intent.putExtra("orderId",orderId);
            startActivity(intent);

            finish();
        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            if(errorMsg != null){
                ToastUtil.show(SureOrderActivity.this,errorMsg);
            }
        }
    }

    private CartRequester cartRequester;
    private SelectBalanceAllListenerImpl selectBalanceAllListener;
    private class SelectBalanceAllListenerImpl extends DefaultRequestListener implements SelectBalanceAllListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectBalanceAllSuccess(Address address, List<CartGoods> cartGoodsList) {
            mAddress = address;

            cartGoodses.clear();
            cartGoodses.addAll(cartGoodsList);

            refreshView();
            adapter.setData(cartGoodses);
            getMoney(cartGoodsList);

//            requestShipPrice();
        }
    }
    private  GetshipPriceListenerImpl getshipPriceListener;
    private class GetshipPriceListenerImpl extends DefaultRequestListener implements GetshipPriceListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetshipPriceSuccess(List<Map<String,String>> singleList, double shipPrice) {
            if (temp != null) {
                if (NumberUtils.getIntFromString(cartGoodses.get(0).getCartGoodsList().get(0).getId())==(NumberUtils.getIntFromString(singleList.get(0).get("goodsId")))) {
                    cartGoodses.get(0).setShipPrice(NumberUtils.getDoubleFromString(singleList.get(0).get("shipPrice")));
                    GlobalDataUtil.putObject(SureOrderActivity.this, Constants.TEMP_DATA_KEY_BUY_GOODS_ID, cartGoodses.get(0).getCartGoodsList().get(0).getId());
                }

//                for(Map<String,String> map : singleList){
//                    for(CartGoods cartGoods : cartGoodses){
//                        if(cartGoods.getCartGoodsList().get(0).getId().equals(map.get("goodsId"))){
//                            cartGoods.setShipPrice(NumberUtils.getDoubleFromString(map.get("shipPrice")));
//                            //记录买的商品，（猜你喜欢用）
//                            GlobalDataUtil.putObject(SureOrderActivity.this, Constants.TEMP_DATA_KEY_BUY_GOODS_ID,cartGoods.getId());
//                        }
//                    }
//                }
//            }
                adapter.setData(cartGoodses);
                totalShipPrice = NumberUtils.getDoubleFromString(singleList.get(0).get("shipPrice"));
                totalShipTv.setText(getString(R.string.label_money)+ NumberUtils.formatToDouble(singleList.get(0).get("shipPrice")+""));
                refreshTotalPrice();
            }
        }
    }
    private GetDefaultAddressListenerImpl getDefaultAddressListener;
    private class GetDefaultAddressListenerImpl extends DefaultRequestListener implements GetDefaultAddressListener{

        @Override
        protected void onRequestFail() {
            refreshView();
        }

        @Override
        public void onGetDefaultAddressSuccess(Address address) {
            mAddress = address;
            refreshView();

            requestShipPrice(cartGoodses);
        }
    }

    private void refreshView(){
        if(mAddress != null){
            nameTv.setText(mAddress.getName());
            mobileTv.setText(mAddress.getMobile());
            addressTv.setText(mAddress.getArea()+mAddress.getDetailAddress());
            addressTipTv.setVisibility(View.GONE);
        }else{
            addressTipTv.setVisibility(View.VISIBLE);
        }
//        adapter.notifyDataSetChanged();
        handler.sendEmptyMessageDelayed(0,500);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_order);
        cartRequester = new CartRequester();
        selectBalanceAllListener = new SelectBalanceAllListenerImpl();
        getshipPriceListener = new GetshipPriceListenerImpl();
        orderRequester = new OrderRequester();
        addOrderFormListener = new AddOrderFormListenerImpl();
        getDefaultAddressListener = new GetDefaultAddressListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        addressRl = (RelativeLayout) findViewById(R.id.rl_address);
        addressTipTv = (TextView)findViewById(R.id.tv_address_tip);
        nameTv = (TextView)findViewById(R.id.tv_name);
        mobileTv = (TextView)findViewById(R.id.tv_mobile);
        addressTv = (TextView)findViewById(R.id.tv_address);
        submitTv = (TextView)findViewById(R.id.tv_submit);
        totalLoanTv = (TextView)findViewById(R.id.tv_total_loan);
        totalShipTv = (TextView)findViewById(R.id.tv_total_ship);
        totalPriceTv = (TextView)findViewById(R.id.tv_total_price);
        cartLV = (NoScrollListView) findViewById(R.id.lv_cartlist);
        scrollView = (ScrollView) findViewById(R.id.scroll_view);

        cartGoodses = new ArrayList<>();
        adapter = new SureOrderListAdapter(this,cartGoodses);
        cartLV.setAdapter(adapter);

        tipDialog = new TipDialog(this, getString(R.string.tip), getString(R.string.tip_only_purchaser_can_buy),
                getString(R.string.tip_update_now), getString(R.string.tip_let_me_think), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                //去增值服务
                Intent intent = new Intent(SureOrderActivity.this, ValueAddApplyActivity.class);
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

            }
        });
        addressRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SureOrderActivity.this, LocatianManagerActivity.class);
                intent.putExtra("type",true);
                startActivityForResult(intent, 1);
            }
        });

        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAddress != null){
                    User.USER_TYPE userType = (User.USER_TYPE)GlobalDataUtil.getObject(SureOrderActivity.this,Constants.GLOBAL_DATA_KEY_USER_TYPE);
                    if(userType == User.USER_TYPE.BUYER){
                        orderRequester.addOrderForm(SureOrderActivity.this,getAccessToken(),mAddress.getId(),cartGoodses,addOrderFormListener);
                    }else{
                        tipDialog.show();
                    }
                }else{
                    ToastUtil.show(SureOrderActivity.this,R.string.tip_please_select_address);
                }
            }
        });
    }
    List<CartGoods> temp ;
    private void initData(){
        temp = (ArrayList<CartGoods>)getIntent().getSerializableExtra("data");
        if(temp != null){
//            mapperNewData(temp);
            cartGoodses.addAll(mapperNewData(temp));
            adapter.setData(cartGoodses);
            cartRequester.getDefaultAddress(this,getAccessToken(),getDefaultAddressListener);
        }else{
            cartRequester.selectBalanceAll(this,getAccessToken(),null,selectBalanceAllListener);
        }
    }

    private List<CartGoods> mapperNewData(List<CartGoods> temp) {
            List<CartGoodsNew> cartGoodsList = new ArrayList<>();

            CartGoodsNew goodsNew = new CartGoodsNew();
            goodsNew.setGoodsStyles(temp.get(0).getGoodsStyles());
            goodsNew.setTitle(temp.get(0).getTitle());
            goodsNew.setLevelPrices(temp.get(0).getLevelPrices());
            goodsNew.setIcon(temp.get(0).getIcon());
            goodsNew.setTogetherEndMillis(temp.get(0).getTogetherEndMillis());
            goodsNew.setId(temp.get(0).getId());
            goodsNew.setGoodsType(temp.get(0).getGoodsType());
            cartGoodsList.add(goodsNew);

           List<CartGoods> goodsList = new ArrayList<>();
            CartGoods goods = new CartGoods();
            goods.setStoreId(temp.get(0).getStoreId());
            goods.setShipPrice(temp.get(0).getShipPrice());
            goods.setGoodsLimit(temp.get(0).getGoodsLimit());
            goods.setTogetherEndMillis(temp.get(0).getTogetherEndMillis());
            goods.setCartGoodsList(cartGoodsList);

           goodsList.add(goods);
        return goodsList ;
    }

    private void requestShipPrice(List<CartGoods> cartGoodsList){
        if(mAddress != null && mAddress.getId() != null){
            cartRequester.getshipPrice(SureOrderActivity.this,getAccessToken(),mAddress.getId(),cartGoodsList,getshipPriceListener);
        }
    }

    private void refreshTotalPrice(){
        double totalLoan = 0;
        if(cartGoodses != null){
            for(CartGoods cartGoods : cartGoodses){
                for (CartGoodsStyle cartGoodsStyle : cartGoods.getCartGoodsList().get(0).getGoodsStyles()){
                    totalLoan += cartGoodsStyle.getBuyNum()*cartGoodsStyle.getPrice();
                }
            }
        }
//        totalShipTv.setText(getString(R.string.label_money)+ NumberUtils.formatToDouble(totalShipPrice+""));
        totalLoanTv.setText(getString(R.string.label_money)+NumberUtils.formatToDouble(totalLoan+""));
        totalPrice = totalShipPrice+totalLoan;
        totalPriceTv.setText(getString(R.string.label_money)+NumberUtils.formatToDouble(totalPrice+""));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == 1){
            mAddress = (Address) data.getSerializableExtra("address");
            refreshView();
//            requestShipPrice();
            if (temp!=null){
                requestShipPrice(cartGoodses);
            }else {
                cartRequester.selectBalanceAll(this, getAccessToken(), mAddress.getId(), selectBalanceAllListener);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if(adapter!=null){
            adapter.cancelAllTimers();
        }
        temp = null ;
        super.onDestroy();
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            scrollView.scrollTo(0,0);
        }
    };

    private void getMoney(List<CartGoods> cartGoodsList){
        double totalLoan = 0;
        double shipPrice = 0 ;
        for (CartGoods cartGoods : cartGoodsList){
            shipPrice = shipPrice+cartGoods.getShipPrice();
            totalLoan = totalLoan+cartGoods.getGoodsPrice();
        }
        totalShipTv.setText(getString(R.string.label_money)+ NumberUtils.formatToDouble(shipPrice+""));
        totalLoanTv.setText(getString(R.string.label_money)+NumberUtils.formatToDouble(totalLoan+""));
        totalPriceTv.setText(getString(R.string.label_money)+NumberUtils.formatToDouble(shipPrice+totalLoan+""));
    }
}
