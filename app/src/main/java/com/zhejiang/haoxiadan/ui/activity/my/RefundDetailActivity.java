package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.RefundDetailListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.RefundDetail;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.OrderGoodsListAdapter;
import com.zhejiang.haoxiadan.ui.adapter.my.RefundDetailImgsAdapter;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

public class RefundDetailActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView refundStatusTv;
    private ImageView refundStatusIv;
    private RelativeLayout consultHistoryRl;
    private TextView supplierTv;
    private NoScrollListView listView;
    private TextView refundReasonTv;
    private TextView refundMsgTv;
    private TextView refundPriceTv;
    private TextView wuTv;
    private NoScrollGridView gridView;
    private TextView applyTimeTv;
    private TextView refundNoTv;
    private GlobalTitleMorePopupWindow popupDialog ;

    private OrderGoodsListAdapter adapter;
    private List<OrderGoods> orderGoodsList;

    private RefundDetailImgsAdapter imgsAdapter;
    private List<String> imgs;

    private String orderId;

    private RefundDetail mRefundDetail;
    private ArrayList<ConsultHistory> consultHistories = new ArrayList<>();

    private OrderRequester orderRequester;
    private RefundDetailListenerImpl refundDetailListener;
    private class RefundDetailListenerImpl extends DefaultRequestListener implements RefundDetailListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onRefundOrderFormStatusByIdSuccess(RefundDetail refundDetail,List<ConsultHistory> consultHistoryList) {
            mRefundDetail = refundDetail;

            consultHistories.clear();
            consultHistories.addAll(consultHistoryList);
            refreshView();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_detail);

        orderId = getIntent().getStringExtra("orderId");
        if(orderId == null){
            finish();
            return;
        }
        orderRequester = new OrderRequester();
        refundDetailListener = new RefundDetailListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        refundStatusTv = (TextView)findViewById(R.id.tv_status);
        refundStatusIv = (ImageView)findViewById(R.id.iv_status);
        consultHistoryRl = (RelativeLayout)findViewById(R.id.rl_consult_history);
        supplierTv = (TextView)findViewById(R.id.tv_supplier);
        listView = (NoScrollListView)findViewById(R.id.lv_goods);
        refundReasonTv = (TextView)findViewById(R.id.tv_refund_reason);
        refundMsgTv = (TextView)findViewById(R.id.tv_refund_msg);
        refundPriceTv = (TextView)findViewById(R.id.tv_refund_price);
        wuTv = (TextView)findViewById(R.id.tv_wu);
        gridView = (NoScrollGridView)findViewById(R.id.gv_imgs);
        applyTimeTv = (TextView)findViewById(R.id.tv_apply_time);
        refundNoTv = (TextView)findViewById(R.id.tv_refund_no);

        popupDialog = new GlobalTitleMorePopupWindow(this);

        orderGoodsList = new ArrayList<>();
        adapter = new OrderGoodsListAdapter(this,orderGoodsList,1);
        listView.setAdapter(adapter);
        imgs = new ArrayList<>();
        imgsAdapter = new RefundDetailImgsAdapter(this,imgs);
        gridView.setAdapter(imgsAdapter);




    }
    private void initEvent(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                popupDialog.showAsDropDown(commonTitle);
            }
        });
        consultHistoryRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RefundDetailActivity.this,ConsultHistoryActivity.class);
                intent.putExtra("list",consultHistories);
                startActivity(intent);
            }
        });
    }
    private void initData(){
        List<OrderGoods> temp = (ArrayList<OrderGoods>)getIntent().getSerializableExtra("goodsList");
        orderGoodsList.addAll(temp);
        requestData();
    }

    private void requestData(){
        orderRequester.refundDetail(this,orderId,refundDetailListener);
    }

    private void refreshView(){
        switch (mRefundDetail.getRefundStatus()){
            case REFUND_ING:
                refundStatusTv.setText(R.string.label_saler_examine);
                break;
            case REFUND_COMPLETE:
                refundStatusTv.setText(R.string.label_refund_success);
                break;
            case REFUND_FAILED:
                refundStatusTv.setText(R.string.label_refund_failed);
                break;
        }
        supplierTv.setText(mRefundDetail.getSupplier().getCompany());
        adapter.notifyDataSetChanged();
        refundReasonTv.setText(mRefundDetail.getReason());
        refundMsgTv.setText(mRefundDetail.getExplain());
        refundPriceTv.setText(getString(R.string.label_money)+mRefundDetail.getPrice());
        applyTimeTv.setText(mRefundDetail.getApplyTime());
        refundNoTv.setText(mRefundDetail.getRefundNo());

        imgs.clear();
        imgs.addAll(mRefundDetail.getImgs());
        if(imgs.size()>0){
            wuTv.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            imgsAdapter.notifyDataSetChanged();
        }else{
            wuTv.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
    }

}
