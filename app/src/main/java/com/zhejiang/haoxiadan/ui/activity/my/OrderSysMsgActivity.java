package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chat.ChatRequester;
import com.zhejiang.haoxiadan.business.request.chat.OrderFormAllForMessageListener;
import com.zhejiang.haoxiadan.business.request.chat.SendMessageListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.OrderMessage;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.OrderSysMsgAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class OrderSysMsgActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private SpringView springView;
    private ListView lvMsglist;
    private LinearLayout nullConversation;

    private OrderSysMsgAdapter adapter;

    private List<OrderMessage> list;

    private int pageNo = 1;
    private int pageSize = 10;
    private ChatRequester requester = new ChatRequester();
    private OrderFormAllForMessageListenerImpl orderFormAllForMessageListenerImpl = new OrderFormAllForMessageListenerImpl();

    private class OrderFormAllForMessageListenerImpl extends DefaultRequestListener implements OrderFormAllForMessageListener {

        @Override
        protected void onRequestFail() {
            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onGetPushInfoSuccess(List<OrderMessage> pushList) {
            springView.onFinishFreshAndLoad();
            if(pageNo == 1){
                list.clear();
            }
            list.addAll(pushList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_sys_msg);

        EventBus.getDefault().register(this);

        initViews();
        initDatas();
        initNets();
        initEvents();
    }

    private void initViews(){
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        springView = (SpringView) findViewById(R.id.spring_view);
        lvMsglist = (ListView) findViewById(R.id.lv_msglist);
        nullConversation = (LinearLayout) findViewById(R.id.null_conversation);
        lvMsglist.setEmptyView(nullConversation);
    }

    private void initDatas(){
        list = new ArrayList<>();
        adapter = new OrderSysMsgAdapter(this,list);
        lvMsglist.setAdapter(adapter);


    }

    private void initNets(){
        requester.OrderFormAllForMessage(OrderSysMsgActivity.this, (String) SharedPreferencesUtil.get(this, Constants.accessToken,""),pageNo+"",pageSize+"",orderFormAllForMessageListenerImpl);
    }

    private void initEvents(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                initNets();
            }

            @Override
            public void onLoadmore() {
                ++pageNo;
                initNets();
            }
        });
    }

    /**
     * 接收eventbus事件
     * @param event
     */
    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case REFRESH_ORDER: //刷新
                pageNo = 1;
                initNets();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
