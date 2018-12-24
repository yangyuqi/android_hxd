package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.business.request.my.RefundNewDetailsListener;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.requestData.out.my.RefundNewBeanDetail;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.RefundDetailImgsAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefundDetailsNewActivity extends BaseActivity {


    @BindView(R.id.common_title)
    CommonTitle commonTitle;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.iv_status)
    ImageView ivStatus;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.rl_consult_history)
    RelativeLayout rlConsultHistory;
    @BindView(R.id.iv_head_icon)
    ImageView ivHeadIcon;
    @BindView(R.id.tv_supplier)
    TextView tvSupplier;
    @BindView(R.id.iv_goods_icon)
    ImageView ivGoodsIcon;
    @BindView(R.id.iv_goods_type)
    ImageView ivGoodsType;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_style)
    TextView tvGoodsStyle;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.text_refund_status)
    TextView textRefundStatus;
    @BindView(R.id.rl_mm)
    RelativeLayout rlMm;
    @BindView(R.id.tv_refund_reason)
    TextView tvRefundReason;
    @BindView(R.id.tv_refund_msg)
    TextView tvRefundMsg;
    @BindView(R.id.tv_refund_price)
    TextView tvRefundPrice;
    @BindView(R.id.tv_wu)
    TextView tvWu;
    @BindView(R.id.gv_imgs)
    NoScrollGridView gvImgs;
    @BindView(R.id.tv_apply_time)
    TextView tvApplyTime;
    @BindView(R.id.tv_refund_no)
    TextView tvRefundNo;
    private OrderRequester orderRequester;

    private OrderGoods orderGoods;
    private RefundDetailListenerImpl refundDetailListener;

    private class RefundDetailListenerImpl extends DefaultRequestListener implements RefundNewDetailsListener {

        @Override
        protected void onRequestFail() {

        }


        @Override
        public void onRefundOrderFormStatusByIdSuccess(RefundNewBeanDetail refundNewBean) {
            if (refundNewBean!=null){
                tvSupplier.setText(refundNewBean.getStoreName());
                ImageLoaderUtil.displayImage(refundNewBean.getGoodsMainphotoPath(), ivGoodsIcon);

                if (refundNewBean.getRefundStatus()!=null){
                    if (refundNewBean.getRefundStatus()==3){
                        textRefundStatus.setText("已拒绝");
                    }else if (refundNewBean.getRefundStatus()==1){
                        textRefundStatus.setText("退款中");
                    }else if (refundNewBean.getRefundStatus()==2){
                        textRefundStatus.setText("退款成功");
                    }else if (refundNewBean.getRefundStatus()==0){
                        textRefundStatus.setText("退款审核中");
                    }
                }else {
                }
                tvGoodsName.setText(refundNewBean.getGoodName());
                tvGoodsPrice.setText(RefundDetailsNewActivity.this.getString(R.string.label_money) + NumberUtils.formatToDouble(refundNewBean.getSinglePrice()));
                tvGoodsStyle.setText("颜色 :"+refundNewBean.getGoodsSpecContent().get(0)+"   "+"尺码 :"+refundNewBean.getGoodsSpecContent().get(1));
                tvGoodsCount.setText(RefundDetailsNewActivity.this.getString(R.string.label_cheng) + " " + refundNewBean.getCount());
                tvStatus.setText("交易关闭");
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(refundNewBean.getRefundInfo()));
                    tvRefundReason.setText(jsonObject.getString("userApplyReason"));
                    tvRefundMsg.setText("");
                    tvRefundPrice.setText(RefundDetailsNewActivity.this.getString(R.string.label_money)+NumberUtils.formatToDouble(jsonObject.getString("refund_price")));

                    imgs.clear();
//                    imgs.addAll(mRefundDetail.getImgs());
                    if(imgs.size()>0){
                        gvImgs.setVisibility(View.VISIBLE);
                        imgsAdapter.notifyDataSetChanged();
                    }else{
                        gvImgs.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                tvRefundReason.setText(""+refundNewBean.getRefundInfo());
//                switch (refundNewBean.getRefundStatus()){
//                    case "21":
//                        tvStatus.setText(R.string.label_saler_examine);
//                        break;
//                    case REFUND_COMPLETE:
//                        tvStatus.setText(R.string.label_refund_success);
//                        break;
//                    case REFUND_FAILED:
//                        tvStatus.setText(R.string.label_refund_failed);
//                        break;
//                }
            }
        }
    }

    private RefundDetailImgsAdapter imgsAdapter;
    private List<String> imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refund_details_new_layout);
        ButterKnife.bind(this);
        orderGoods = (OrderGoods) getIntent().getSerializableExtra("goods");
        if (orderGoods == null) {
            finish();
        }
        orderRequester = new OrderRequester();
        refundDetailListener = new RefundDetailListenerImpl();

        orderRequester.refundNewDetail(RefundDetailsNewActivity.this, orderGoods.getOrderId(), "" + orderGoods.getGoodsCartId(), refundDetailListener);
        imgsAdapter = new RefundDetailImgsAdapter(this,imgs);
        gvImgs.setAdapter(imgsAdapter);

        rlConsultHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RefundDetailsNewActivity.this,ConsultHistoryActivity.class);
//                intent.putExtra("list",new );
                startActivity(intent);
            }
        });
    }
}
