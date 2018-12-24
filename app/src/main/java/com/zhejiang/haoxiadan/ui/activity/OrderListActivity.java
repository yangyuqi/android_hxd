package com.zhejiang.haoxiadan.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.ui.fragment.my.OrderListFragment;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;

/**
 * 订单列表
 */
public class OrderListActivity extends BaseFragmentActivity {

    private Order.ORDER_STATUS orderStatus;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private CommonTitle commonTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        orderStatus = (Order.ORDER_STATUS)getIntent().getSerializableExtra("status");
        commonTitle = (CommonTitle)findViewById(R.id.common_title);

        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        switch (orderStatus){
            case AFTER_SALES:
                commonTitle.setTitle(R.string.label_after_sale);
                break;
        }


        fragmentManager = getSupportFragmentManager();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, OrderListFragment.newInstance(orderStatus));
        fragmentTransaction.commit();
    }
}
