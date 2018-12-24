package com.zhejiang.haoxiadan.third.jiguang.chat.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.ThreadUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by ${chenyn} on 2017/3/13.
 */

public class ConversationListView {
    private ListView mConvListView = null;
    private LinearLayout mHeader;
    private RelativeLayout mLoadingHeader;
    private ImageView mLoadingIv;
    private LinearLayout mLoadingTv;
    private Activity mContext;
    private LinearLayout mNull_conversation;
    private LinearLayout mFooter;

    public ConversationListView(Activity context) {
        this.mContext = context;
    }

    public void initModule() {
        mConvListView = (ListView) mContext.findViewById(R.id.conv_list_view);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHeader = (LinearLayout) inflater.inflate(R.layout.conv_list_head_view, mConvListView, false);
        mLoadingHeader = (RelativeLayout) inflater.inflate(R.layout.jmui_drop_down_list_header, mConvListView, false);
        mFooter = (LinearLayout) inflater.inflate(R.layout.item_sys_msg_list,mConvListView,false);

        mLoadingIv = (ImageView) mLoadingHeader.findViewById(R.id.jmui_loading_img);
        mLoadingTv = (LinearLayout) mLoadingHeader.findViewById(R.id.loading_view);
        mNull_conversation = (LinearLayout) mContext.findViewById(R.id.null_conversation);
        //mConvListView.setEmptyView(mNull_conversation);
        mConvListView.addHeaderView(mLoadingHeader);
        mConvListView.addHeaderView(mHeader);
        if(!Constants.USER_CUSTOMER.equals((String) SharedPreferencesUtil.get(mContext, Constants.userRole, ""))) {
            mConvListView.addFooterView(mFooter);
        }
    }

    public void setConvListAdapter(ListAdapter adapter) {
        mConvListView.setAdapter(adapter);
    }

    public void setItemListeners(AdapterView.OnItemClickListener onClickListener) {
        mConvListView.setOnItemClickListener(onClickListener);
    }

    public void setLongClickListener(AdapterView.OnItemLongClickListener listener) {
        mConvListView.setOnItemLongClickListener(listener);
    }


    public void showHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.VISIBLE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.VISIBLE);
    }

    public void setFooterView(String StatusMessage,String time){
        ((TextView)mFooter.findViewById(R.id.msg_item_date)).setText(time);
        ((TextView)mFooter.findViewById(R.id.msg_item_content)).setText(StatusMessage);
    }

    public void dismissHeaderView() {
        mHeader.findViewById(R.id.network_disconnected_iv).setVisibility(View.GONE);
        mHeader.findViewById(R.id.check_network_hit).setVisibility(View.GONE);
    }


    public void showLoadingHeader() {
        mLoadingIv.setVisibility(View.VISIBLE);
        mLoadingTv.setVisibility(View.VISIBLE);
        AnimationDrawable drawable = (AnimationDrawable) mLoadingIv.getDrawable();
        drawable.start();
    }

    public void dismissLoadingHeader() {
        mLoadingIv.setVisibility(View.GONE);
        mLoadingTv.setVisibility(View.GONE);
    }


    public void setUnReadMsg(final int count) {
        ThreadUtil.runInUiThread(new Runnable() {
            @Override
            public void run() {
                SharePreferenceManager.setCachedChatcount(count);
                EventBus.getDefault().post(Event.CHAT_COUNT);
            }
        });
    }


}
