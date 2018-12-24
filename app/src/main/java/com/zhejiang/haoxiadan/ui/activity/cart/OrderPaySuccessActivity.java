package com.zhejiang.haoxiadan.ui.activity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.GuessYouLikeListener;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.OrderListActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MyOrdersActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoodsAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class OrderPaySuccessActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView payStatusTv;
    private TextView payWayTv;
    private TextView priceTv;
    private TextView reviewOrderTv;
    private TextView backHomeTv;
    private NoScrollGridView gridView;
    private RelativeLayout loanRl;
    private RelativeLayout wayPriceRl;
    private TextView orderTv;


    private CommonGoodsAdapter adapter;
    private List<Goods> goodsList;

    private String price;
    private String payWay;
    //是否是贷款
    private boolean isLoan;

    private GoodsRequester goodsRequester;
    private GuessYouLikeListenerImpl guessYouLikeListener;
    private class GuessYouLikeListenerImpl extends DefaultRequestListener implements GuessYouLikeListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGuessYouLikeSuccess(List<Goods> goodses) {
            goodsList.clear();
            goodsList.addAll(goodses);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_success);

        EventBus.getDefault().post(Event.REFRESH_ORDER);

        price = getIntent().getStringExtra("price");
        payWay = getIntent().getStringExtra("payWay");
        isLoan = getIntent().getBooleanExtra("isLoan",false);

        goodsRequester = new GoodsRequester();
        guessYouLikeListener = new GuessYouLikeListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        payStatusTv = (TextView)findViewById(R.id.tv_pay_status);
        payWayTv = (TextView)findViewById(R.id.tv_pay_way);
        priceTv = (TextView)findViewById(R.id.tv_price);
        reviewOrderTv = (TextView)findViewById(R.id.tv_review_order);
        backHomeTv = (TextView)findViewById(R.id.tv_back_home);
        gridView = (NoScrollGridView)findViewById(R.id.gv_goods);
        loanRl = (RelativeLayout)findViewById(R.id.rl_loan_price);
        wayPriceRl = (RelativeLayout)findViewById(R.id.rl_pay_way_price);
        orderTv = (TextView)findViewById(R.id.tv_loan_price);

        goodsList = new ArrayList<>();
        adapter = new CommonGoodsAdapter(this,goodsList);
        gridView.setAdapter(adapter);

        payWayTv.setText(payWay);
        priceTv.setText(getString(R.string.label_money)+ NumberUtils.formatToDouble(price));
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
        reviewOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_tv_my_need_send = new Intent(OrderPaySuccessActivity.this, MyOrdersActivity.class);
                if(isLoan){
                    intent_tv_my_need_send.putExtra("status", Order.ORDER_STATUS.WAIT_PAY);
                }else{
                    intent_tv_my_need_send.putExtra("status", Order.ORDER_STATUS.WAIT_SHIP);
                }
                startActivity(intent_tv_my_need_send);
                finish();
            }
        });
        backHomeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //首页
                EventBus.getDefault().post(Event.GOTO_HOME);
            }
        });
    }

    private void initData(){
        if(isLoan){
            payStatusTv.setText(R.string.label_apply_loan_success);
            loanRl.setVisibility(View.VISIBLE);
            wayPriceRl.setVisibility(View.GONE);
            orderTv.setText(getString(R.string.label_money)+ NumberUtils.formatToDouble(price));
        }

        String id = (String)GlobalDataUtil.getObject(this, Constants.TEMP_DATA_KEY_BUY_GOODS_ID);
        goodsRequester.guessYouLike(this,id,1,10,guessYouLikeListener);
    }


}
