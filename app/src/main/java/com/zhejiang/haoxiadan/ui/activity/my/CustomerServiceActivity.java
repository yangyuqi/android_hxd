package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormAllListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.fragment.my.OrderListFragment;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerServiceActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.lv_orders)
    ListView lvOrders;
    @BindView(R.id.spring_view)
    SpringView springView;
    private OrderRequester orderRequester;
    private SelectOrderFormAllListenerImpl selectOrderFormAllListener;
    private int pageNo = 1;
    private int pageSize = 100;
    private Order.ORDER_STATUS orderStatus;

    private CommonAdapter<OrderGoods> adapter ;
    private List<OrderGoods> data = new ArrayList<>();

    private class SelectOrderFormAllListenerImpl extends DefaultRequestListener implements SelectOrderFormAllListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.getInstance(CustomerServiceActivity.this).dismiss();

            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onSelectOrderFormAllSuccess(List<Order> orderList) {
            LoadingDialog.getInstance(CustomerServiceActivity.this).dismiss();
            for (Order order : orderList){
                data.addAll(order.getGoodsList());
            }
            adapter.setData(data);
            adapter.notifyDataSetChanged();
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_service_layout);
        ButterKnife.bind(this);
        orderStatus = (Order.ORDER_STATUS) getIntent().getSerializableExtra("status");
        orderRequester = new OrderRequester();
        selectOrderFormAllListener = new SelectOrderFormAllListenerImpl();
        initView();
        requestData();
    }

    private void initView() {
        tvTitle.setText("售后管理");
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new CommonAdapter<OrderGoods>(this,data,R.layout.customer_service_ls_item) {
            @Override
            public void convert(ViewHolder helper, final OrderGoods item) {
                helper.setText(R.id.tv_supplier,item.getStoreName());
                ImageLoaderUtil.displayImage(item.getIcon(), (ImageView) helper.getView(R.id.iv_goods_icon));
                switch (item.getType()) {
                    case STOCK:
                        ((ImageView)helper.getView(R.id.iv_goods_type)).setImageResource(R.mipmap.icon_goods);
                        break;
                    case FUTURES:
                        ((ImageView)helper.getView(R.id.iv_goods_type)).setImageResource(R.mipmap.icon_order);
                        break;
                }
                helper.setText(R.id.text_refund_status,item.getRefundStatus());
                helper.setText(R.id.tv_goods_name,item.getTitle());
                helper.setText(R.id.tv_goods_price,CustomerServiceActivity.this.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getPrice()));
                helper.setText(R.id.tv_goods_style,item.getStyle());
                helper.setText(R.id.tv_goods_count,CustomerServiceActivity.this.getString(R.string.label_cheng) + " " + item.getPer_count());

                helper.getView(R.id.rl_mm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext,RefundDetailsNewActivity.class);
                        intent.putExtra("goods",item);
                        startActivity(intent);
                    }
                });
            }
        };
        lvOrders.setAdapter(adapter);
    }

    private void requestData() {
        LoadingDialog.getInstance(this).show();
        orderRequester.selectOrderFormAll(this,getAccessToken(),orderStatus,pageNo,pageSize,selectOrderFormAllListener);
    }
}
