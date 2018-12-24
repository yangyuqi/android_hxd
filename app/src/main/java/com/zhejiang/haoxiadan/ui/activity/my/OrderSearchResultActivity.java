package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormAllListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.OrderListAdapter;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;

import java.util.ArrayList;
import java.util.List;

public class OrderSearchResultActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private SpringView springView;
    private ListView listView;
    private OrderListAdapter adapter;
    private List<Order> orders;
    private GlobalTitleMorePopupWindow popupDialog ;

    private String key;
    private int pageNo = 1;
    private int pageSize = 20;

    private OrderRequester orderRequester;
    private SelectOrderFormAllListenerImpl selectOrderFormAllListener;
    private class SelectOrderFormAllListenerImpl extends DefaultRequestListener implements SelectOrderFormAllListener{

        @Override
        protected void onRequestFail() {
            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onSelectOrderFormAllSuccess(List<Order> orderList) {
            if (pageNo == 1){
                orders.clear();
            }
            orders.addAll(orderList);
            adapter.notifyDataSetChanged();
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search_result);

        key = getIntent().getStringExtra("key");
        if(key == null){
            finish();
        }
        orderRequester = new OrderRequester();
        selectOrderFormAllListener = new SelectOrderFormAllListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        springView = (SpringView)findViewById(R.id.spring_view);
        listView = (ListView)findViewById(R.id.lv_orders);

        orders = new ArrayList<>();
        adapter = new OrderListAdapter(this,orders);
        listView.setAdapter(adapter);

        popupDialog = new GlobalTitleMorePopupWindow(this);
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

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                requestData();
            }

            @Override
            public void onLoadmore() {
                pageNo ++;
                requestData();
            }
        });
    }

    private void initData(){
        requestData();
    }

    private void requestData(){
        orderRequester.searchOrderFormAll(this,getAccessToken(),key,pageNo,pageSize,selectOrderFormAllListener);
    }
}
