package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectServiceActivity extends BaseActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_goods_icon)
    RelativeLayout rlGoodsIcon;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.rl_goods_name)
    RelativeLayout rlGoodsName;
    @BindView(R.id.tv_goods_style)
    TextView tvGoodsStyle;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.iv_goods_type)
    ImageView ivGoodsType;
    @BindView(R.id.iv_goods_icon)
    ImageView ivGoodsIcon;
    @BindView(R.id.rl_refund)
    RelativeLayout rlRefund;
    @BindView(R.id.rl_refund_goods)
    RelativeLayout rlRefundGoods;
    @BindView(R.id.rl_change_goods)
    RelativeLayout rlChangeGoods;

    private OrderGoods orderGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_service_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        orderGoods = (OrderGoods) getIntent().getSerializableExtra("goods");
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private String orderId ;

    @Subscribe
    public void onEvent(String orderId){
        if (orderId!=null){
            this.orderId = orderId;
        }
    }

    private void initView() {
        tvTitle.setText("选择服务类型");
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (orderGoods != null) {
            ImageLoaderUtil.displayImage(orderGoods.getIcon(), ivGoodsIcon);
            switch (orderGoods.getType()) {
                case STOCK:
                    ivGoodsType.setImageResource(R.mipmap.icon_goods);
                    break;
                case FUTURES:
                    ivGoodsType.setImageResource(R.mipmap.icon_order);
                    break;
            }

            tvGoodsName.setText(orderGoods.getTitle());
            tvGoodsPrice.setText(this.getString(R.string.label_money) + orderGoods.getPrice());
            tvGoodsStyle.setText(orderGoods.getStyle());
            tvGoodsCount.setText(this.getString(R.string.label_cheng) + " " + orderGoods.getPer_count());

            rlRefund.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SelectServiceActivity.this,ApplyRefundActivity.class);
                    intent.putExtra("type",1);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("OrderGoods",orderGoods);
                    startActivity(intent);
                    finish();
                }
            });
            rlRefundGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SelectServiceActivity.this,ApplyRefundActivity.class);
                    intent.putExtra("type",2);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("OrderGoods",orderGoods);
                    startActivity(intent);
                    finish();
                }
            });

            rlChangeGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SelectServiceActivity.this,ApplyRefundActivity.class);
                    intent.putExtra("type",3);
                    intent.putExtra("orderId",orderId);
                    intent.putExtra("OrderGoods",orderGoods);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
