package com.zhejiang.haoxiadan.ui.fragment.my;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectOrderFormCountListener;
import com.zhejiang.haoxiadan.business.request.my.GetUserListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.database.UserEntry;
import com.zhejiang.haoxiadan.third.jiguang.chat.utils.SharePreferenceManager;
import com.zhejiang.haoxiadan.ui.activity.OrderListActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.common.RegisterAndFindPwdActivity;
import com.zhejiang.haoxiadan.ui.activity.my.CustomerServiceActivity;
import com.zhejiang.haoxiadan.ui.activity.my.LocatianManagerActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MyCollectinActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MyFooterActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MyOrdersActivity;
import com.zhejiang.haoxiadan.ui.activity.my.MySettingActivity;
import com.zhejiang.haoxiadan.ui.activity.my.UserCenterActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.CircleImageView;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/****
 * 我的界面
 */
public class MyFragment extends BaseFragment {


    @BindView(R.id.civ_user_icon)
    CircleImageView civUserIcon;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.ll_no_login)
    LinearLayout llNoLogin;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ll_has_login)
    LinearLayout llHasLogin;
    @BindView(R.id.rl_my_order)
    RelativeLayout rlMyOrder;
    @BindView(R.id.tv_my_need_pay)
    TextView tvMyNeedPay;
    @BindView(R.id.tv_my_need_send)
    TextView tvMyNeedSend;
    @BindView(R.id.tv_my_need_save)
    TextView tvMyNeedSave;
    @BindView(R.id.tv_my_need_appraise)
    TextView tvMyNeedAppraise;
    @BindView(R.id.tv_my_need_manage)
    TextView tvMyNeedManage;
    @BindView(R.id.rl_my_service)
    RelativeLayout rlMyService;
    @BindView(R.id.rl_my_collect)
    RelativeLayout rlMyCollect;
    @BindView(R.id.rl_my_address)
    RelativeLayout rlMyAddress;
    @BindView(R.id.rl_my_foot)
    RelativeLayout rlMyFoot;
    @BindView(R.id.rl_my_kefu)
    RelativeLayout rlMyKefu;
    Unbinder unbinder;

    //待付款
    @BindView(R.id.tv_my_need_pay_num)
    TextView tvMyNeedPayNum;
    //待发货
    @BindView(R.id.tv_my_need_send_num)
    TextView tvMyNeedSendNum;
    //待收货
    @BindView(R.id.tv_my_need_save_num)
    TextView tvMyNeedSaveNum;
    //待评价
    @BindView(R.id.tv_my_need_appraise_num)
    TextView tvMyNeedAppraiseNum;
    //售后管理
    @BindView(R.id.tv_my_need_manage_num)
    TextView tvMyNeedManageNum;

    @BindView(R.id.rl_my_chat)
    RelativeLayout rlMyChat;
    @BindView(R.id.all_unread_number)
    TextView mAllUnReadMsg;

    private OrderRequester orderRequester;
    private UserRequester requester ;
    private SelectOrderFormCountListenerImpl selectOrderFormCountListener;

    private class SelectOrderFormCountListenerImpl extends DefaultRequestListener implements SelectOrderFormCountListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectOrderFormCountSuccess(int waitPayNum, int waitSendNum, int waitReceiveNum, int waitEvaluateNum, int afterSaleNum) {
            if (waitPayNum > 0) {
                String temp;
                if (waitPayNum > 9) {
                    temp = "9+";
                } else {
                    temp = waitPayNum + "";
                }
                tvMyNeedPayNum.setText(temp);
                tvMyNeedPayNum.setVisibility(View.VISIBLE);
            } else {
                tvMyNeedPayNum.setVisibility(View.GONE);
            }
            if (waitSendNum > 0) {
                String temp;
                if (waitSendNum > 9) {
                    temp = "9+";
                } else {
                    temp = waitSendNum + "";
                }
                tvMyNeedSendNum.setText(temp);
                tvMyNeedSendNum.setVisibility(View.VISIBLE);
            } else {
                tvMyNeedSendNum.setVisibility(View.GONE);
            }
            if (waitReceiveNum > 0) {
                String temp;
                if (waitReceiveNum > 9) {
                    temp = "9+";
                } else {
                    temp = waitReceiveNum + "";
                }
                tvMyNeedSaveNum.setText(temp);
                tvMyNeedSaveNum.setVisibility(View.VISIBLE);
            } else {
                tvMyNeedSaveNum.setVisibility(View.GONE);
            }
            if (waitEvaluateNum > 0) {
                String temp;
                if (waitEvaluateNum > 9) {
                    temp = "9+";
                } else {
                    temp = waitEvaluateNum + "";
                }
                tvMyNeedAppraiseNum.setText(temp);
                tvMyNeedAppraiseNum.setVisibility(View.VISIBLE);
            } else {
                tvMyNeedAppraiseNum.setVisibility(View.GONE);
            }
            if (afterSaleNum > 0) {
                String temp;
                if (afterSaleNum > 9) {
                    temp = "9+";
                } else {
                    temp = afterSaleNum + "";
                }
                tvMyNeedManageNum.setText(temp);
                tvMyNeedManageNum.setVisibility(View.VISIBLE);
            } else {
                tvMyNeedManageNum.setVisibility(View.GONE);
            }
        }
    }

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case CHAT_COUNT:
                int count = SharePreferenceManager.getCachedChatcount();
                if (count > 0) {
                    mAllUnReadMsg.setVisibility(View.VISIBLE);
                    if (count < 100) {
                        mAllUnReadMsg.setText(count + "");
                    } else {
                        mAllUnReadMsg.setText("99+");
                    }
                } else {
                    mAllUnReadMsg.setVisibility(View.GONE);
                }
                break;
            case LOGIN_OUT:
                tvMyNeedPayNum.setVisibility(View.GONE);
                tvMyNeedSendNum.setVisibility(View.GONE);
                tvMyNeedSaveNum.setVisibility(View.GONE);
                tvMyNeedAppraiseNum.setVisibility(View.GONE);
                tvMyNeedManageNum.setVisibility(View.GONE);
                ImageLoaderUtil.displayImageAndDefaultImg("",civUserIcon,R.mipmap.ic_launcher);
                break;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        unbinder = ButterKnife.bind(this, view);

        orderRequester = new OrderRequester();
        selectOrderFormCountListener = new SelectOrderFormCountListenerImpl();
        requester = new UserRequester();
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.civ_user_icon, R.id.iv_setting, R.id.tv_login, R.id.tv_register, R.id.rl_my_order, R.id.tv_my_need_pay, R.id.tv_my_need_send, R.id.tv_my_need_save, R.id.tv_my_need_appraise, R.id.tv_my_need_manage, R.id.rl_my_service, R.id.rl_my_collect, R.id.rl_my_address, R.id.rl_my_foot, R.id.rl_my_kefu, R.id.rl_my_chat})
    public void OnClick(View view) {
        Intent allIntent = new Intent(context, LoginActivity.class);
        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(new Intent(context, LoginActivity.class));
                break;
            case R.id.tv_register:
                Intent intent = new Intent(context, RegisterAndFindPwdActivity.class);
                intent.putExtra("flag", "2");
                startActivity(intent);
                break;
            case R.id.iv_setting:
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, MySettingActivity.class));
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.civ_user_icon:
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, UserCenterActivity.class));
                } else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_address:
                if (!getAccessToken().equals("")) {
                    Intent intent1 = new Intent(context, LocatianManagerActivity.class);
                    intent1.putExtra("type", false);
                    startActivity(intent1);
                } else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_kefu:
                final DeleteDialog deleteDialog = new DeleteDialog(context, "拨号", "4009269598", "确定");
                deleteDialog.show();
                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (isdelete) {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "4009269598"));
                            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            context.startActivity(intent);
                            deleteDialog.dismiss();
                        }
                    }
                });
                break;
            case R.id.rl_my_order :
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, MyOrdersActivity.class));
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.tv_my_need_pay :
                if (!getAccessToken().equals("")) {
                    Intent intent_tv_my_need_pay = new Intent(context, MyOrdersActivity.class);
                    intent_tv_my_need_pay.putExtra("status", Order.ORDER_STATUS.WAIT_PAY);
                    startActivity(intent_tv_my_need_pay);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.tv_my_need_send :
                if (!getAccessToken().equals("")) {
                    Intent intent_tv_my_need_send = new Intent(context, MyOrdersActivity.class);
                    intent_tv_my_need_send.putExtra("status", Order.ORDER_STATUS.WAIT_SHIP);
                    startActivity(intent_tv_my_need_send);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.tv_my_need_save :
                if (!getAccessToken().equals("")) {
                    Intent intent_tv_my_need_save = new Intent(context, MyOrdersActivity.class);
                    intent_tv_my_need_save.putExtra("status", Order.ORDER_STATUS.WAIT_RECEIVE);
                    startActivity(intent_tv_my_need_save);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.tv_my_need_appraise :
                if (!getAccessToken().equals("")) {
                    Intent intent_tv_my_need_appraise = new Intent(context, MyOrdersActivity.class);
                    intent_tv_my_need_appraise.putExtra("status", Order.ORDER_STATUS.WAIT_EVALUATE);
                    startActivity(intent_tv_my_need_appraise);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.tv_my_need_manage :
                if (!getAccessToken().equals("")) {
//                    Intent intent_tv_my_need_manage = new Intent(context, OrderListActivity.class);
                    Intent intent_tv_my_need_manage = new Intent(context, CustomerServiceActivity.class);
                    intent_tv_my_need_manage.putExtra("status", Order.ORDER_STATUS.AFTER_SALES);
                    startActivity(intent_tv_my_need_manage);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_service:
                if (!getAccessToken().equals("")) {
                    Intent intent_rl_my_service = new Intent(context, ValueAddActivity.class);
                    startActivity(intent_rl_my_service);
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_collect :
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, MyCollectinActivity.class));
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_foot :
                if (!getAccessToken().equals("")) {
                    startActivity(new Intent(context, MyFooterActivity.class));
                }else {
                    startActivity(allIntent);
                }
                break;
            case R.id.rl_my_chat://我的沟通
                if (!getAccessToken().equals("")) {
                    //goLogout();
                    //SharedPreferencesUtil.put(context,Constants.userName ,"18551704501");
                    //SharedPreferencesUtil.put(context,Constants.userRole ,Constants.USER_CUSTOMER);
                    String mobile = (String) SharedPreferencesUtil.get(context, Constants.userName, "");
                    //if(null != mobile && !"".equals(mobile) && !"".equals(getAccessToken())) {
                    if (null != mobile && !"".equals(mobile)) {
                        //检测账号是否登录
                        UserInfo myInfo = JMessageClient.getMyInfo();
                        if (myInfo == null) {
                            //登录
                            goChatLogin(mobile);
                            //注册
                            //goChatRegister();

                        } else {//已经登录 跳转到我的聊天列表
                            Intent intentchat = new Intent(context, com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                            startActivity(intentchat);
                        }
                    } else {
                        startActivity(new Intent(context, LoginActivity.class));
                    }
                }else {
                    startActivity(allIntent);
                }

                break;
        }
    }

    private void goChatLogin(final String userId){
        JMessageClient.login(userId, userId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw(userId);
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    Intent intentchat = new Intent(context, com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                    startActivity(intentchat);
                }
            }
        });
    }


    private void goChatRegister(){
        final String userId = "15190411498";
        JMessageClient.register(userId, userId, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setRegisterUsername(userId);
                    JMessageClient.login(userId, userId, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                Constants.registerOrLogin = 1;
                                String username = JMessageClient.getMyInfo().getUserName();
                                String appKey = JMessageClient.getMyInfo().getAppKey();
                                UserEntry user = UserEntry.getUser(username, appKey);
                                if (null == user) {
                                    user = new UserEntry(username, appKey);
                                    user.save();
                                }

                                UserInfo myUserInfo = JMessageClient.getMyInfo();
                                if (myUserInfo != null) {
                                    myUserInfo.setNickname(userId);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {
        if (!getAccessToken().equals("")){
            llNoLogin.setVisibility(View.GONE);
            llHasLogin.setVisibility(View.VISIBLE);
            requestData();
        }else {
            llNoLogin.setVisibility(View.VISIBLE);
            llHasLogin.setVisibility(View.GONE);
        }

        String nickeName = (String) SharedPreferencesUtil.get(context, Constants.nickName,"");
        if (nickeName.length()>15){
            tvPhone.setText(nickeName.substring(0,15)+"...");
        }else {
            tvPhone.setText(nickeName);
        }
        String role = (String) SharedPreferencesUtil.get(context,Constants.userRole,"");
        if (!role.equals("")) {
            if (role.equals(Constants.USER_CUSTOMER)) {
                tvName.setText(R.string.label_common_member);
            } else if (role.equals(Constants.USER_SELL)) {
                tvName.setText("供应商");
            } else if (role.equals(Constants.USER_BUY)) {
                tvName.setText("采购商");
            }
        }
    }

    private void requestData(){
        orderRequester.selectOrderFormCount(getActivity(),getAccessToken(),selectOrderFormCountListener);
        requester.getUserInfo(context,getAccessToken(),imp);
    }

    private UserInfoImp imp = new UserInfoImp();
    private class UserInfoImp extends DefaultRequestListener implements GetUserListener {

        @Override
        public void getUserInfo(com.zhejiang.haoxiadan.model.common.UserInfo userInfo) {
                ImageLoaderUtil.displayImageAndDefaultImg(userInfo.getUserHeadImg(),civUserIcon,R.mipmap.ic_launcher);
        }

        @Override
        protected void onRequestFail() {

        }
    }
}
