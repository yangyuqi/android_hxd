package com.zhejiang.haoxiadan.third.jiguang.chat.controller;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

import com.squareup.picasso.Picasso;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.ChatActivity;
import com.zhejiang.haoxiadan.third.jiguang.chat.adapter.ConversationListAdapter;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.DialogCreator;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SortConvList;
import com.zhejiang.haoxiadan.third.jiguang.chat.view.ConversationListView;
import com.zhejiang.haoxiadan.ui.activity.my.OrderSysMsgActivity;
import com.zhejiang.haoxiadan.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class ConversationListController implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ConversationListView mConvListView;
    private Activity mContext;
    private int mWidth;
    private ConversationListAdapter mListAdapter;
    private List<Conversation> mDatas = new ArrayList<Conversation>();
    private List<Conversation> mConv = new ArrayList<Conversation>();
    private Dialog mDialog;

    public ConversationListController(ConversationListView listView, Activity context,
                                      int width) {
        this.mConvListView = listView;
        this.mContext = context;
        this.mWidth = width;
        initConvListAdapter();
    }

    //得到会话列表
    private void initConvListAdapter() {
        mDatas = JMessageClient.getConversationList();
        if (mDatas != null && mDatas.size() > 0) {
            SortConvList sortConvList = new SortConvList();
            Collections.sort(mDatas, sortConvList);
         /*   for (int i = 0; i < SharePreferenceManager.getTopSize(); i++) {
                ConversationEntry topConversation = ConversationEntry.getTopConversation(i);
                for (int x = 0; x < mDatas.size(); x++) {
                    if (topConversation.targetname.equals(mDatas.get(x).getTargetId())) {
                        mConv.add(mDatas.get(x));
                        mDatas.remove(mDatas.get(x));

                        mDatas.add(i, mConv.get(0));
                        mConv.clear();
                        break;

                    }
                }
            }*/
        }
        mListAdapter = new ConversationListAdapter(mContext, mDatas, mConvListView);
        mConvListView.setConvListAdapter(mListAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mDatas == null){
            return;
        }
        //点击会话条目
        Intent intent = new Intent();
        Conversation conv = null;
        Log.e("wangqdb","position = "+position);
        if (position > 0) {
            //这里-3是减掉添加的三个headView
            if(!Constants.USER_CUSTOMER.equals((String) SharedPreferencesUtil.get(mContext, Constants.userRole, ""))) {
                if (position == (mDatas.size()+2)) {//
                    intent.setClass(mContext, OrderSysMsgActivity.class);
                    mContext.startActivity(intent);
                }
                if(position - 2<mDatas.size()){
                    conv = mDatas.get(position - 2);
                }
            }else{
                if(position - 2<mDatas.size()){
                    conv = mDatas.get(position - 2);
                }
            }
            if(conv == null){
                return;
            }
                intent.putExtra(Constants.CONV_TITLE, conv.getTitle());
                //群聊
                if (conv.getType() == ConversationType.group) {
                    if (mListAdapter.includeAtMsg(conv)) {
                        intent.putExtra("atMsgId", mListAdapter.getAtMsgId(conv));
                    }

                    if (mListAdapter.includeAtAllMsg(conv)) {
                        intent.putExtra("atAllMsgId", mListAdapter.getatAllMsgId(conv));
                    }
                    long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
                    intent.putExtra(Constants.GROUP_ID, groupId);
                    intent.putExtra(Constants.DRAFT, getAdapter().getDraft(conv.getId()));
                    intent.setClass(mContext, ChatActivity.class);

                    mContext.startActivity(intent);
                    return;
                    //单聊
                } else {
                    String targetId = ((UserInfo) conv.getTargetInfo()).getUserName();
                    intent.putExtra(Constants.TARGET_ID, targetId);
                    intent.putExtra(Constants.TARGET_APP_KEY, conv.getTargetAppKey());
                    intent.putExtra(Constants.TARGET_LASTMSGDATE, conv.getLastMsgDate() + "");
                    try {
                        Message msg = conv.getLatestMessage();

                        if (msg != null) {
                            String storeId = msg.getContent().getStringExtra("storeId");
                            String buyName = msg.getContent().getStringExtra("buyName");
                            String buyId = msg.getContent().getStringExtra("buyId");
                            String buyImg = msg.getContent().getStringExtra("buyImg");
                            String storeName = msg.getContent().getStringExtra("storeName");
                            String storeLogo = msg.getContent().getStringExtra("storeLogo");
                            String customerPhone = msg.getContent().getStringExtra("customerPhone");
                            String storePhone = msg.getContent().getStringExtra("storePhone");
                            String userType = msg.getContent().getStringExtra("userType");

                            String userType2 = (String) SharedPreferencesUtil.get(mContext, Constants.userRole, "");

                            if (null != storeId && !"".equals(storeId)) {
                                intent.putExtra("storeId", storeId);
                            }
                            intent.putExtra("buyName", buyName);
                            intent.putExtra("buyId", buyId);
                            intent.putExtra("buyImg", buyImg);
                            intent.putExtra("storeName", storeName);
                            intent.putExtra("storeLogo", storeLogo);
                            intent.putExtra("customerPhone", customerPhone);
                            intent.putExtra("storePhone", storePhone);
                            intent.putExtra("userType", userType);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    intent.putExtra(Constants.DRAFT, getAdapter().getDraft(conv.getId()));
                }
                intent.setClass(mContext, ChatActivity.class);
                mContext.startActivity(intent);

        }
    }

    public ConversationListAdapter getAdapter() {
        return mListAdapter;
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final Conversation conv;
        if(!Constants.USER_CUSTOMER.equals((String) SharedPreferencesUtil.get(mContext, Constants.userRole, ""))) {
            if (position == (mDatas.size() + 2)) {//
                Intent intent = new Intent(mContext, OrderSysMsgActivity.class);
                mContext.startActivity(intent);
                return true;
            }
            conv = mDatas.get(position - 2);
        }else{
            conv = mDatas.get(position - 2);
        }


        if (conv != null) {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        //会话置顶
                        /*case R.id.jmui_top_conv_ll:
                            mListAdapter.setConvTop(conv);
                            int topSize = SharePreferenceManager.getTopSize();
                            ConversationEntry entry = new ConversationEntry(conv.getTargetId(), topSize);
                            entry.save();
                            ++topSize;
                            SharePreferenceManager.setTopSize(topSize);
                            mDialog.dismiss();
                            break;*/
                        //删除会话
                        case R.id.jmui_delete_conv_ll:
                            if (conv.getType() == ConversationType.group) {
                                JMessageClient.deleteGroupConversation(((GroupInfo) conv.getTargetInfo()).getGroupID());
                            } else {
                                JMessageClient.deleteSingleConversation(((UserInfo) conv.getTargetInfo()).getUserName());
                            }
                            mDatas.remove(position - 2);
                            mListAdapter.notifyDataSetChanged();
                            mDialog.dismiss();
                            break;
                        default:
                            break;
                    }

                }
            };
            mDialog = DialogCreator.createDelConversationDialog(mContext, listener);
            mDialog.show();
            mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
        }
        return true;
    }
}
