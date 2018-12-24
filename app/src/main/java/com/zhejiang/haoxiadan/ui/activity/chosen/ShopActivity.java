package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chat.ChatRequester;
import com.zhejiang.haoxiadan.business.request.chat.GetUserRoleListener;
import com.zhejiang.haoxiadan.business.request.chat.QueryCustomerByStoreIdListener;
import com.zhejiang.haoxiadan.business.request.chosen.AddFavoriteStoreListener;
import com.zhejiang.haoxiadan.business.request.chosen.QueryStoreByIdListener;
import com.zhejiang.haoxiadan.business.request.chosen.SelectVisualNavigationAllListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.common.ShopTab;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.model.common.UserRole;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.ChatActivity;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddApplyActivity;
import com.zhejiang.haoxiadan.ui.adapter.CommonFragmentPagerAdapter;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ShopGoodsListFragment;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ShopHomeFragment;
import com.zhejiang.haoxiadan.ui.view.NoScrollViewPager;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺
 */
public class ShopActivity extends BaseFragmentActivity {

    private ImageView backIv;
    private RelativeLayout searchRl;
    private ImageView shopBg;
    private ImageView shopIconIv;
    private TextView shopNameTv;
    private ImageView collectIv;
    private TextView collectTv;
    private LinearLayout sellerFileLl;
    private LinearLayout onlineServiceLl;

    private TabLayout tabLayout;
    private NoScrollViewPager viewPager;
    private List<Fragment> fragments;
    private CommonFragmentPagerAdapter adapter;

    private String storeId;
    private Shop mShop;

    private TipDialog tipDialog;

    private ShopRequester shopRequester;
    private ChatRequester chatRequester = new ChatRequester();
    private QueryStoreByIdListenerImpl queryStoreByIdListener;


    private GetUserRoleListenerImpl getUserRoleListenerImpl = new GetUserRoleListenerImpl();
    private class GetUserRoleListenerImpl extends DefaultRequestListener implements GetUserRoleListener {

        @Override
        protected void onRequestFail() {
            onlineServiceLl.setEnabled(true);
        }

        @Override
        public void onGetUserRoleSuccess(UserRole userRole) {
            onlineServiceLl.setEnabled(true);
            SharedPreferencesUtil.put(context,Constants.userRole ,userRole.getUserRole());
            switch (userRole.getUserRole()){
                case Constants.USER_REGULAR:
                    tipDialog.show();
                    break;
                case Constants.USER_BUY:
                    onlineServiceLl.setEnabled(false);
                    chatRequester.queryCustomerByStoreId(context, storeId,(String) SharedPreferencesUtil.get(context,Constants.userName,""), queryCustomerByStoreIdListenerImpl);
                    break;
                case Constants.USER_SELL:
                    ToastUtil.show(ShopActivity.this,R.string.tip_only_purchaser_can_chat);
                    break;
                case Constants.USER_CUSTOMER:
                    ToastUtil.show(ShopActivity.this,R.string.tip_only_purchaser_can_chat);
                    break;
            }

        }
    }

    private QueryCustomerByStoreIdListenerImpl queryCustomerByStoreIdListenerImpl = new QueryCustomerByStoreIdListenerImpl();
    private class QueryStoreByIdListenerImpl extends DefaultRequestListener implements QueryStoreByIdListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryStoreByIdSuccess(Shop shop) {
            mShop = shop;
            refreshShop();
        }
    }
    private SelectVisualNavigationAllListenerImpl selectVisualNavigationAllListener;
    private class SelectVisualNavigationAllListenerImpl extends DefaultRequestListener implements SelectVisualNavigationAllListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectVisualNavigationAllSuccess(List<ShopTab> shopTabs) {

            List<String> titles = new ArrayList<>();
            titles.add(getString(R.string.label_home));
            fragments.clear();
            fragments.add(ShopHomeFragment.newInstance(storeId));
            for(ShopTab shopTab : shopTabs){
                titles.add(shopTab.getTitle());
                fragments.add(ShopGoodsListFragment.newInstance(shopTab.getGoodses()));
            }

            adapter.notifyDataSetChanged();

            for(int i=0;i<tabLayout.getTabCount();i++){
                tabLayout.getTabAt(i).setText(titles.get(i));
            }

            viewPager.setOffscreenPageLimit(fragments.size());

        }
    }

    private class QueryCustomerByStoreIdListenerImpl extends DefaultRequestListener implements QueryCustomerByStoreIdListener {

        @Override
        protected void onRequestFail() {
            onlineServiceLl.setEnabled(true);
        }

        @Override
        public void onQueryCustomerSuccess(Customer customer) {
            onlineServiceLl.setEnabled(true);
            Intent intent = new Intent();
            intent.putExtra(Constants.CONV_TITLE, customer.getStoreName());
            intent.putExtra(Constants.TARGET_ID, customer.getAdminId());
            intent.putExtra(Constants.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
            intent.putExtra("storeId", NumberUtils.getIntFromString(customer.getStoreId()) +"");
            String nickName = (String) SharedPreferencesUtil.get(ShopActivity.this,Constants.nickName,"");
            if(nickName != null && !"".equals(nickName)) {
                intent.putExtra("buyName", nickName);
            }else{
                intent.putExtra("buyName", (String) SharedPreferencesUtil.get(ShopActivity.this,Constants.userName,""));
            }
            intent.putExtra("buyImg", (String) SharedPreferencesUtil.get(ShopActivity.this,Constants.user_icon,""));
            intent.putExtra("buyId", (String) SharedPreferencesUtil.get(ShopActivity.this,Constants.userName,""));
            intent.putExtra("storeName", customer.getStoreName());
            intent.putExtra("storeLogo", customer.getStoreLogo());
            intent.putExtra("customerPhone", customer.getCustomerPhone());
            intent.putExtra("storePhone", customer.getStorePhone());
            if(
                    (customer.getAdminId() == null || "".equals(customer.getAdminId())) ||
                            (customer.getStoreId() == null || "".equals(customer.getStoreId())) ||
                            (customer.getStorePhone() == null )
                    ){
                ToastUtil.show(ShopActivity.this,R.string.chat_no_store_msg);
                return;
            }
            intent.setClass(ShopActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }
    private AddFavoriteStoreListenerImpl addFavoriteStoreListener;
    private class AddFavoriteStoreListenerImpl extends DefaultRequestListener implements AddFavoriteStoreListener{

        @Override
        public void onAddFavoriteStoreSuccess() {
            mShop.setCollect(!mShop.isCollect());

            if(mShop.isCollect()){
                collectIv.setSelected(true);
                collectTv.setText(R.string.label_has_collect);
            }else{
                collectIv.setSelected(false);
                collectTv.setText(R.string.label_collect);
            }
        }

        @Override
        protected void onRequestFail() {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        storeId = getIntent().getStringExtra("storeId");
        if (storeId == null){
            finish();
            return;
        }

        shopRequester = new ShopRequester();
        queryStoreByIdListener = new QueryStoreByIdListenerImpl();
        selectVisualNavigationAllListener = new SelectVisualNavigationAllListenerImpl();
        addFavoriteStoreListener = new AddFavoriteStoreListenerImpl();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        backIv = (ImageView) findViewById(R.id.iv_back);
        searchRl = (RelativeLayout) findViewById(R.id.rl_search);
        shopBg = (ImageView)findViewById(R.id.iv_shop_bg);
        shopIconIv = (ImageView) findViewById(R.id.iv_shop_icon);
        shopNameTv = (TextView) findViewById(R.id.tv_shop_name);
        collectIv = (ImageView) findViewById(R.id.iv_collect);
        collectTv = (TextView) findViewById(R.id.tv_collect);
        sellerFileLl = (LinearLayout) findViewById(R.id.ll_seller_file);
        onlineServiceLl = (LinearLayout) findViewById(R.id.ll_online_service);
        tabLayout = (TabLayout) findViewById(R.id.tab_shop);
        viewPager = (NoScrollViewPager) findViewById(R.id.vp_shop);
        viewPager.setScanScroll(true);

        tipDialog = new TipDialog(context, getString(R.string.tip), getString(R.string.tip_only_purchaser_can_watch_file),
                getString(R.string.tip_update_now), getString(R.string.tip_let_me_think), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                //去增值服务
                Intent intent = new Intent(context, ValueAddApplyActivity.class);
                intent.putExtra("type", ValueAddActivity.VALUE_ADD_TYPE.PURCHASER);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {
            }
        });
    }

    private void initEvent(){
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collectIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopRequester.addFavoriteStore(ShopActivity.this,getAccessToken(),storeId,addFavoriteStoreListener);
            }
        });
        sellerFileLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(getAccessToken())) {
                    User.USER_TYPE userType = (User.USER_TYPE) GlobalDataUtil.getObject(ShopActivity.this, Constants.GLOBAL_DATA_KEY_USER_TYPE);
                    if(userType == User.USER_TYPE.BUYER){
                        Intent intent = new Intent(ShopActivity.this,SellerFileActivity.class);
                        intent.putExtra("storeId",storeId);
                        startActivity(intent);
                    }else{
                        tipDialog.show();
                    }
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
        searchRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this,ShopGoodsSearchActivity.class);
                intent.putExtra("storeId",storeId);
                startActivity(intent);
            }
        });

        onlineServiceLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(getAccessToken())) {
                    String userType2 = (String) SharedPreferencesUtil.get(context,Constants.userRole,"");
                    switch (userType2){
                        case Constants.USER_REGULAR:
                            //查看用户权限
                            onlineServiceLl.setEnabled(false);
                            chatRequester.getUserRole(context,getAccessToken(),getUserRoleListenerImpl);
                            break;
                        case Constants.USER_BUY:
                            onlineServiceLl.setEnabled(false);
                            chatRequester.queryCustomerByStoreId(context, storeId,(String) SharedPreferencesUtil.get(context,Constants.userName,""), queryCustomerByStoreIdListenerImpl);
                            break;
                        case Constants.USER_SELL:
                            ToastUtil.show(ShopActivity.this,R.string.tip_only_purchaser_can_chat);
                            break;
                        case Constants.USER_CUSTOMER:
                            ToastUtil.show(ShopActivity.this,R.string.tip_only_purchaser_can_chat);
                            break;
                    }
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }

    private void initData(){

        fragments = new ArrayList<>();
        adapter = new CommonFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        requestData();

        requestTab();
    }

    private void requestData(){
        shopRequester.queryStoreById(this,getAccessToken(),storeId,queryStoreByIdListener);
    }

    private void requestTab(){
        shopRequester.selectVisualNavigationAll(this,storeId,selectVisualNavigationAllListener);
    }

    private void refreshShop(){
        ImageLoaderUtil.displayImage(mShop.getIcon(),shopIconIv);
        Glide.with(this).load(mShop.getBgImg()).placeholder(R.mipmap.pg_shop).into(shopBg);
//        ImageLoaderUtil.displayImage(mShop.getBgImg(),shopBg);
        shopNameTv.setText(mShop.getName());
        if(mShop.isCollect()){
            collectIv.setSelected(true);
            collectTv.setText(R.string.label_has_collect);
        }else{
            collectIv.setSelected(false);
            collectTv.setText(R.string.label_collect);
        }
    }
}
