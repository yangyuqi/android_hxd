package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.adapter.CommonFragmentPagerAdapter;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.fragment.my.OrderListFragment;
import com.zhejiang.haoxiadan.ui.view.NoScrollViewPager;
import com.zhejiang.haoxiadan.util.AppManager;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends BaseFragmentActivity implements View.OnClickListener{

    private LinearLayout leftLL;
    private ImageView searchIV;
    private ImageView moreIV;
    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    private List<Fragment> orderFragements;
    private CommonFragmentPagerAdapter ordersAdapter;
    private GlobalTitleMorePopupWindow popupDialog ;

    private Order.ORDER_STATUS curOrderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        curOrderStatus = (Order.ORDER_STATUS)getIntent().getSerializableExtra("status");
        if(curOrderStatus == null){
            curOrderStatus = Order.ORDER_STATUS.ALL;
        }

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        leftLL = (LinearLayout)findViewById(R.id.ll_left);
        searchIV = (ImageView)findViewById(R.id.iv_search);
        moreIV = (ImageView)findViewById(R.id.iv_more);
        tabLayout = (TabLayout) findViewById(R.id.tab_orders);
        viewPager = (NoScrollViewPager) findViewById(R.id.vp_orders);
        viewPager.setScanScroll(true);

        popupDialog = new GlobalTitleMorePopupWindow(this, GlobalTitleMorePopupWindow.TYPE.MY_ORDER);
    }

    private void initEvent(){
        leftLL.setOnClickListener(this);
        searchIV.setOnClickListener(this);
        moreIV.setOnClickListener(this);
    }

    private void initData(){
        orderFragements = new ArrayList<>();
        ordersAdapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), orderFragements);
        viewPager.setAdapter(ordersAdapter);


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        Object[][] status = new Object[][]{
            new Object[]{getString(R.string.label_order_all), Order.ORDER_STATUS.ALL},
            new Object[]{getString(R.string.label_order_wait_pay),Order.ORDER_STATUS.WAIT_PAY},
            new Object[]{getString(R.string.label_order_wait_ship),Order.ORDER_STATUS.WAIT_SHIP},
            new Object[]{getString(R.string.label_order_wait_receive),Order.ORDER_STATUS.WAIT_RECEIVE},
            new Object[]{getString(R.string.label_order_wait_evaluate),Order.ORDER_STATUS.WAIT_EVALUATE}
        };
        for(int i = 0;i<status.length;i++){
            orderFragements.add(OrderListFragment.newInstance((Order.ORDER_STATUS)status[i][1]));
        }
        ordersAdapter.notifyDataSetChanged();

        for(int i=0;i<tabLayout.getTabCount();i++){
            tabLayout.getTabAt(i).setText((String)status[i][0]);
        }

        viewPager.setOffscreenPageLimit(orderFragements.size());

        for(int i = 0;i<status.length;i++){
            if(status[i][1] == curOrderStatus){
                viewPager.setCurrentItem(i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_left:
                finish();
                break;
            case R.id.iv_search:
                Intent intent = new Intent(MyOrdersActivity.this,OrderSearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_more:
                popupDialog.showAsDropDown(moreIV);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
