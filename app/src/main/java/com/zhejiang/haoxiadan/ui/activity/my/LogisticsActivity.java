package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.GetExpressListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.Logistics;
import com.zhejiang.haoxiadan.model.common.LogisticsNode;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.LogisticsListAdapter;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class LogisticsActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private ImageView companyIconIv;
    private TextView companyTv;
    private TextView logisticsNoTv;
    private TextView shipNameTv;
    private ImageView callPhoneIv;
    private TextView infoProviderTv;
    private ListView listView;
    private RelativeLayout shipManRl;
    private GlobalTitleMorePopupWindow popupDialog ;

    private LogisticsListAdapter adapter;
    private List<LogisticsNode> logisticsNodes;

    private Logistics mLogistics;

    private String goodsIcon;
    //运单号
    private String shipNo;
    //订单编号
    private String orderId;

    private OrderRequester orderRequester;
    private GetExpressListenerImpl getExpressListener;
    private class GetExpressListenerImpl extends DefaultRequestListener implements GetExpressListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetExpressSuccess(Logistics logistics) {
            mLogistics = logistics;
            refreshView();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logistics);

        goodsIcon = getIntent().getStringExtra("goodsIcon");
        shipNo = getIntent().getStringExtra("shipNo");
        orderId = getIntent().getStringExtra("orderId");
        if(shipNo == null){
            ToastUtil.show(this,R.string.tip_no_logistics_info);
            finish();
            return;
        }

        orderRequester = new OrderRequester();
        getExpressListener = new GetExpressListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        companyIconIv = (ImageView)findViewById(R.id.iv_company_icon);
        companyTv = (TextView)findViewById(R.id.tv_company);
        logisticsNoTv = (TextView)findViewById(R.id.tv_logistics_no);
        shipNameTv = (TextView)findViewById(R.id.tv_ship_name);
        callPhoneIv = (ImageView)findViewById(R.id.iv_phone);
        infoProviderTv = (TextView)findViewById(R.id.tv_info_provider);
        listView = (ListView)findViewById(R.id.lv_logistics);
        shipManRl = (RelativeLayout)findViewById(R.id.rl_ship_man);
        popupDialog = new GlobalTitleMorePopupWindow(this);

        logisticsNodes = new ArrayList<>();
        adapter = new LogisticsListAdapter(this,logisticsNodes);
        listView.setAdapter(adapter);
        logisticsNoTv.setText(shipNo);
        ImageLoaderUtil.displayImage(goodsIcon,companyIconIv);
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
    }

    private void initData(){
        requestData();
    }

    private void requestData(){
        orderRequester.getExpress(this,shipNo,orderId,getExpressListener);
    }

    private void refreshView(){
        companyTv.setText(mLogistics.getShipCompany());
        infoProviderTv.setText(mLogistics.getInfoProvider());

        if(mLogistics.getShipMobile() != null){
            logisticsNoTv.setText(mLogistics.getLogisticsNo());
            shipNameTv.setText(mLogistics.getShipName());
            shipManRl.setVisibility(View.VISIBLE);
        }else{
            shipManRl.setVisibility(View.GONE);
        }


        logisticsNodes.clear();
        logisticsNodes.addAll(mLogistics.getLogisticsNodes());
        adapter.notifyDataSetChanged();
    }
}
