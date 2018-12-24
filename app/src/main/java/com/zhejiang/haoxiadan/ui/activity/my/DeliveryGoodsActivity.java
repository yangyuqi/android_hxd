package com.zhejiang.haoxiadan.ui.activity.my;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.OrderSureReceiveListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.requestData.out.DeliveryMessageBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryGoodsActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.ls_delivery)
    ListView lsDelivery;
    private OrderRequester orderRequester;
    private ArrayList<OrderGoods> orderGoodsArrayList = new ArrayList<>();
    private List<OrderGoods> data = new ArrayList<>();
    private CommonAdapter<OrderGoods> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delivery_goods_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        tvTitle.setText("发货信息");
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        orderGoodsArrayList = (ArrayList<OrderGoods>) getIntent().getSerializableExtra("goods");
        orderRequester = new OrderRequester();
        orderSureReceiveListener = new OrderSureReceiveListenerImpl();
        if (orderGoodsArrayList.size()>0){
            data = orderGoodsArrayList ;
        }
        adapter = new CommonAdapter<OrderGoods>(this,data,R.layout.delivery_ls_item) {
            @Override
            public void convert(ViewHolder helper, final OrderGoods item) {
                ImageLoaderUtil.displayImage(item.getIcon(), (ImageView) helper.getView(R.id.iv_goods_icon));
                helper.setText(R.id.tv_goods_name,item.getTitle());
                helper.setText(R.id.tv_goods_price,mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getPrice()));
                helper.setText(R.id.tv_goods_style,item.getStyle());
                helper.setText(R.id.tv_goods_count,mContext.getString(R.string.label_cheng)+" "+item.getPer_count());
                NoScrollListView listView = helper.getView(R.id.no_ls);
                if (item.getDelivery()!=null) {

                    CommonAdapter<DeliveryMessageBean> com_adapter = new CommonAdapter<DeliveryMessageBean>(DeliveryGoodsActivity.this,item.getDelivery(), R.layout.delivery_no_ls_item) {
                        @Override
                        public void convert(final ViewHolder helper, final DeliveryMessageBean bean) {
                            helper.setText(R.id.tv_number, "发货编号 :" + bean.getOrderNo());
                            if (bean.getConfirmDate() != null) {
                                helper.setText(R.id.tv_time, bean.getConfirmDate());
                            }
                            helper.setText(R.id.tv_count, "" + bean.getDeliveryCount() + "件");
                            if (bean.getStatus()!=null){
                                if (bean.getStatus()==0){
                                    helper.setText(R.id.tv_status,"待收货");
                                }else if (bean.getStatus()==1){
                                    helper.setText(R.id.tv_status,"已收货");
                                    helper.getView(R.id.tv_pay).setVisibility(View.GONE);
                                }
                            }

                            helper.getView(R.id.tv_remind_ship).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (orderId!=null) {
                                        Intent intent = new Intent(DeliveryGoodsActivity.this, LogisticsActivity.class);
                                        intent.putExtra("goodsIcon", item.getIcon());
                                        intent.putExtra("shipNo", bean.getOrderNo());
                                        intent.putExtra("orderId", orderId);
                                        DeliveryGoodsActivity.this.startActivity(intent);
                                    }
                                }
                            });

                            helper.getView(R.id.tv_pay).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    TipDialog tipDialog = new TipDialog(DeliveryGoodsActivity.this, getString(R.string.tip), getString(R.string.tip_is_sure_receive), new TipDialog.OnTipDialogListener() {
                                        @Override
                                        public void onPositiveClick() {
                                            //确认收货
                                            if (orderId!=null) {
                                                orderRequester.orderSureReceive2(DeliveryGoodsActivity.this, orderId, bean.getOrderNo(), orderSureReceiveListener);
                                                helper.setText(R.id.tv_status,"已收货");
                                                helper.getView(R.id.tv_pay).setVisibility(View.GONE);
                                            }
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                    tipDialog.show();
                                }
                            });
                        }
                    };

                    listView.setAdapter(com_adapter);
                }
                switch (item.getType()){
                    case STOCK:
                        helper.setImageResource(R.id.iv_goods_type,R.mipmap.icon_goods);
                        break;
                    case FUTURES:
                        helper.setImageResource(R.id.iv_goods_type,R.mipmap.icon_order);
                        break;
                }
                if (item.getRefundStatus()!=null) {
                    helper.setText(R.id.text_refund_status ,item.getRefundStatus());
                }
            }
        };
        lsDelivery.setAdapter(adapter);
    }

    private OrderSureReceiveListenerImpl orderSureReceiveListener;

    private class OrderSureReceiveListenerImpl extends DefaultRequestListener implements OrderSureReceiveListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onOrderSureReceiveSuccess() {
//            EventBus.getDefault().post(Event.REFRESH_ORDER);
            EventBus.getDefault().post(Event.REFRESH_ORDER);
        }
    }

    private String orderId ;

    @Subscribe
    public void onEvent(String orderId){
        if (orderId!=null){
            this.orderId = orderId;
        }
    }

}
