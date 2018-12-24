package com.zhejiang.haoxiadan.ui.fragment.my;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormAllListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.ui.adapter.my.OrderListAdapter;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.EmptyView;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单列表
 */
public class OrderListFragment extends BaseFragment {
    private static final String ARG_PARAM_ORDER_STATUS = "ARG_PARAM_ORDER_STATUS";

    private Order.ORDER_STATUS orderStatus;

    private SpringView springView;
    private EmptyView emptyView;
    private NoScrollListView listView;
    private OrderListAdapter adapter;
    private List<Order> orders;

    private int pageNo = 1;
    private int pageSize = 20;

    private OrderRequester orderRequester;
    private SelectOrderFormAllListenerImpl selectOrderFormAllListener;
    private class SelectOrderFormAllListenerImpl extends DefaultRequestListener implements SelectOrderFormAllListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.getInstance(context).dismiss();
            if(orders.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onSelectOrderFormAllSuccess(List<Order> orderList) {
            LoadingDialog.getInstance(context).dismiss();
            if(pageNo ==1){
                orders.clear();
            }
            orders.addAll(orderList);
            adapter.notifyDataSetChanged();
            if(orders.size() == 0){
                emptyView.setVisibility(View.VISIBLE);
            }else{
                emptyView.setVisibility(View.GONE);
            }
            springView.onFinishFreshAndLoad();
        }
    }

    public OrderListFragment() {
        // Required empty public constructor
    }

    public static OrderListFragment newInstance(Order.ORDER_STATUS status) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_ORDER_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderStatus = (Order.ORDER_STATUS)getArguments().getSerializable(ARG_PARAM_ORDER_STATUS);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        orderRequester = new OrderRequester();
        selectOrderFormAllListener = new SelectOrderFormAllListenerImpl();

        initView(view);
        initEvent();
        initData();

        return view;
    }

    private void initView(View view){
        springView = (SpringView)view.findViewById(R.id.spring_view);
        emptyView = (EmptyView)view.findViewById(R.id.empty_view);
        listView = (NoScrollListView)view.findViewById(R.id.lv_orders);

        orders = new ArrayList<>();
        adapter = new OrderListAdapter(getActivity(),orders);
        listView.setAdapter(adapter);
        if(orderStatus == Order.ORDER_STATUS.ALL){
            adapter.setShowStatus(true);
        }
    }

    private void initEvent(){
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
        LoadingDialog.getInstance(context).show();
        orderRequester.selectOrderFormAll(getActivity(),getAccessToken(),orderStatus,pageNo,pageSize,selectOrderFormAllListener);
    }

    /**
     * 接收eventbus事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case REFRESH_ORDER: //刷新订单
                pageNo = 1;
                requestData();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
