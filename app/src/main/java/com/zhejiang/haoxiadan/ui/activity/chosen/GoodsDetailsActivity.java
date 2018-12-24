package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chat.ChatRequester;
import com.zhejiang.haoxiadan.business.request.chat.GetUserRoleListener;
import com.zhejiang.haoxiadan.business.request.chat.QueryCustomerByStoreIdListener;
import com.zhejiang.haoxiadan.business.request.chosen.FavoriteListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemData;
import com.zhejiang.haoxiadan.model.common.UserRole;
import com.zhejiang.haoxiadan.model.requestData.in.FavoritrBean;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsH5Bean;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsStyleBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostTiredPriceData;
import com.zhejiang.haoxiadan.model.requestData.in.chose.TabSelectModel;
import com.zhejiang.haoxiadan.model.requestData.out.GetDetailsData;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;
import com.zhejiang.haoxiadan.model.requestData.out.TiredPriceData;
import com.zhejiang.haoxiadan.model.requestData.out.chat.Customer;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.ChatActivity;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddApplyActivity;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.fragment.chosen.GoodsDetailsFragment;
import com.zhejiang.haoxiadan.ui.fragment.chosen.GoodsDiscussFragment;
import com.zhejiang.haoxiadan.ui.fragment.chosen.GoodsStyleFragment;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class GoodsDetailsActivity extends BaseFragmentActivity {

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.goods_details_tl)
    TabLayout goodsDetailsTl;
    @BindView(R.id.ib_carts)
    ImageView ibCarts;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.tv_go_shop)
    TextView tvGoShop;
    @BindView(R.id.tv_go_consulte)
    TextView tvGoConsulte;
    @BindView(R.id.tv_go_ping)
    TextView tvGoPing;
    @BindView(R.id.tv_go_cart)
    TextView tvGoCart;
    @BindView(R.id.goods_details_layout_cb)
    CheckBox goodsDetailsLayoutCb;
    private List<Fragment> list = new ArrayList<>();
    private String[] titles = {"商品"};
    private MyFragmentPagerAdapter adapter;

    private TipDialog tipDialog;

    private GlobalTitleMorePopupWindow popupDialog ;


    private String frontImg;
    TabSelectModel tabSelectModel ;
    private String goodsId, price, goodsName, storeId,goods_main_photo,goodsMainPhoto;
    private int isCollect = 0 , goodsNumType ,goodsStatus;
    private long endTime ;
    private String goodsType ,type;

    private ArrayList<FlightLsItemData> new_data = new ArrayList<>();
    private ArrayList<GetGoodsIntData> invent_data_list = new ArrayList<>();
    private ArrayList<TiredPriceData> tired_data_list = new ArrayList<>();
    private GoodsRequester requester = new GoodsRequester();
    private ChatRequester chatRequester = new ChatRequester();
    private FavoriteImp favoriteImp = new FavoriteImp();
    private QueryCustomerByStoreIdListenerImpl queryCustomerByStoreIdImpl = new QueryCustomerByStoreIdListenerImpl();

    private class FavoriteImp extends DefaultRequestListener implements FavoriteListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSuccess() {

        }
    }

    private GetUserRoleListenerImpl getUserRoleListenerImpl = new GetUserRoleListenerImpl();
    private class GetUserRoleListenerImpl extends DefaultRequestListener implements GetUserRoleListener {

        @Override
        protected void onRequestFail() {
            tvGoConsulte.setEnabled(true);
        }

        @Override
        public void onGetUserRoleSuccess(UserRole userRole) {
            tvGoConsulte.setEnabled(true);
            SharedPreferencesUtil.put(context,Constants.userRole ,userRole.getUserRole());
            switch (userRole.getUserRole()){
//                case Constants.USER_REGULAR:
//                    tipDialog.show();
//                    break;
                case Constants.USER_REGULAR:
                case Constants.USER_CUSTOMER:
                case Constants.USER_SELL:
                case Constants.USER_BUY:
                    tvGoConsulte.setEnabled(false);
                    chatRequester.queryCustomerByStoreId(context, storeId,(String) SharedPreferencesUtil.get(context,Constants.userName,""), queryCustomerByStoreIdImpl);
                    break;
//                case Constants.USER_SELL:
//                    ToastUtil.show(GoodsDetailsActivity.this,R.string.tip_only_purchaser_can_chat);
//                    break;
//                case Constants.USER_CUSTOMER:
//                    ToastUtil.show(GoodsDetailsActivity.this,R.string.tip_only_purchaser_can_chat);
//                    break;
            }

        }
    }

    private class QueryCustomerByStoreIdListenerImpl extends DefaultRequestListener implements QueryCustomerByStoreIdListener {

        @Override
        protected void onRequestFail() {
            tvGoConsulte.setEnabled(true);
        }

        @Override
        public void onQueryCustomerSuccess(Customer customer) {
            tvGoConsulte.setEnabled(true);
            Intent intent = new Intent();
            intent.putExtra(Constants.CONV_TITLE, customer.getStoreName());
            intent.putExtra(Constants.TARGET_ID, customer.getAdminId());
            intent.putExtra(Constants.TARGET_APP_KEY, Constants.JPUSH_APPKEY);
            intent.putExtra("storeId", NumberUtils.getIntFromString(customer.getStoreId()) +"");
            String nickName = (String) SharedPreferencesUtil.get(GoodsDetailsActivity.this,Constants.nickName,"");
            if(nickName != null && !"".equals(nickName)) {
                intent.putExtra("buyName", nickName);
            }else{
                intent.putExtra("buyName", (String) SharedPreferencesUtil.get(GoodsDetailsActivity.this,Constants.userName,""));
            }
            intent.putExtra("buyImg", (String) SharedPreferencesUtil.get(GoodsDetailsActivity.this,Constants.user_icon,""));
            intent.putExtra("buyId", (String) SharedPreferencesUtil.get(GoodsDetailsActivity.this,Constants.userName,""));
            intent.putExtra("storeName", customer.getStoreName());
            intent.putExtra("storeLogo", customer.getStoreLogo());
            intent.putExtra("customerPhone", customer.getCustomerPhone());
            intent.putExtra("storePhone", customer.getStorePhone());
            intent.putExtra("goodsId", "12");
            if(
                    (customer.getAdminId() == null || "".equals(customer.getAdminId())) ||
                            (customer.getStoreId() == null || "".equals(customer.getStoreId())) ||
                            (customer.getStorePhone() == null )
                    ){
                ToastUtil.show(GoodsDetailsActivity.this,R.string.chat_no_store_msg);
                return;
            }
            intent.putExtra("goods","{\"id\": "+ goodsId +",\"goods_main_photo\": \""+goods_main_photo+"\",\"goodsName\": \""+ goodsName +"\"}");
            intent.setClass(GoodsDetailsActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_details_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();

    }

    private void initView() {
        popupDialog = new GlobalTitleMorePopupWindow(context);

        type = getIntent().getStringExtra("type");
        goodsId = getIntent().getStringExtra("goodsId");
        GoodsDetailsFragment goods_frg = new GoodsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", goodsId);
        bundle.putString("type",type);
        frontImg = getIntent().getStringExtra("frontImg");
        if(frontImg != null){
            bundle.putString("frontImg",frontImg);
        }
        goods_frg.setArguments(bundle);
        GoodsDiscussFragment discussFragment = new GoodsDiscussFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("id", goodsId);
        discussFragment.setArguments(bundle2);
        list.add(goods_frg);
//        list.add(new GoodsStyleFragment());
//        list.add(discussFragment);

        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), list);
        viewPage.setAdapter(adapter);
        goodsDetailsTl.setupWithViewPager(viewPage);

        tipDialog = new TipDialog(context, getString(R.string.tip), getString(R.string.tip_only_purchaser_can_chat),
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

        tvGoConsulte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!"".equals(getAccessToken())) {
                    String userType2 = (String) SharedPreferencesUtil.get(context,Constants.userRole,"");
                    switch (userType2){
                        case Constants.USER_REGULAR:
                            //查看用户权限
//                            tvGoConsulte.setEnabled(false);
//                            chatRequester.getUserRole(context,getAccessToken(),getUserRoleListenerImpl);
//                            break;
                        case Constants.USER_SELL:
                        case Constants.USER_BUY:
                        case Constants.USER_CUSTOMER:
                            tvGoConsulte.setEnabled(false);
                            chatRequester.queryCustomerByStoreId(context, storeId,(String) SharedPreferencesUtil.get(context,Constants.userName,""), queryCustomerByStoreIdImpl);
                            break;
//                        case Constants.USER_SELL:
//                            ToastUtil.show(GoodsDetailsActivity.this,"商家禁止聊天");
//                            break;
//                        case Constants.USER_CUSTOMER:
//                            ToastUtil.show(GoodsDetailsActivity.this,"客服禁止聊天");
//                            break;
                    }
                }else{
                    startActivity(new Intent(context, LoginActivity.class));
                }
            }
        });
    }


    public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        List<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    @OnClick({R.id.btnBack, R.id.tv_go_ping, R.id.tv_go_cart, R.id.ib_carts, R.id.tv_go_shop,R.id.goods_details_layout_cb})
    public void OnClick(View view) {
        PostGoodsStyleBean stylebean = new PostGoodsStyleBean();
        stylebean.setGoodsInvent(invent_data_list);
        PostTiredPriceData priceData = new PostTiredPriceData();
        priceData.setTired_data_list(tired_data_list);
        Intent intent = new Intent(context, NewFlightBottomActivity.class);
        intent.putExtra("style", stylebean);
        intent.putExtra("goodsName", goodsName);
        intent.putExtra("tiredPrice",priceData);
        intent.putExtra("goodsId",goodsId);
        intent.putExtra("storeId",storeId);
        intent.putExtra("endTime",endTime);
        intent.putExtra("goodsMainPhoto",goods_main_photo);
        intent.putExtra("goodsNumType",goodsNumType);
        intent.putExtra("new_data",new_data);
        intent.putExtra("goodsType",goodsType);
        intent.putExtra("goodsLimit",goodsLimit);
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.tv_go_ping:
                startActivity(intent);
                break;
            case R.id.tv_go_cart:
                startActivity(intent);
                break;
            case R.id.ib_carts:
                popupDialog.showAsDropDown(ibCarts);
                break;
            case R.id.tv_go_shop:
                Intent intents = new Intent(this, ShopActivity.class);
                if (storeId != null) {
                    intents.putExtra("storeId", storeId);
                    startActivity(intents);
                }
                break;
            case R.id.goods_details_layout_cb :
                if (!getAccessToken().equals("")) {
                    FavoritrBean bean = new FavoritrBean("0", goodsId);
                    List<FavoritrBean> m_data = new ArrayList<>();
                    m_data.add(bean);
                    requester.changeFavorite(context, getAccessToken(), m_data, favoriteImp);
                    if (isCollect == 0) {
                        goodsDetailsLayoutCb.setChecked(true);
                        isCollect = 1;
                    } else {
                        isCollect = 0;
                        goodsDetailsLayoutCb.setChecked(false);
                    }
                }else {
                    goodsDetailsLayoutCb.setChecked(false);
                    startActivity(new Intent(context,LoginActivity.class));
                }
                break;
            case R.id.tv_go_consulte://咨询
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Subscribe
    public void onEvent(Event event){
        if (event==Event.GO_TO_CHAT){
            tvGoConsulte.performClick();
        }
        if (event==Event.FINISH_GOODS_ACTIVITY){
            finish();
        }
    }

    @Subscribe
    public void onEvent(OpenFlightAloneBean mbean){
        if (mbean!=null){
            if (mbean.getOpen().equals("open")) {
                if (goodsStatus != 0 || getCurrentTimeindex > 1000) {
                } else {
                    tvGoPing.performClick();
                }
            }
        }
    }

    private long getCurrentTimeindex ;
    private int goodsLimit ;

    @Subscribe
    public void onEvent(GetDetailsData detailsData) {
        goodsStatus = detailsData.getGoods().getGoodsStatus();
        getCurrentTimeindex = detailsData.getCurrentTime()-detailsData.getGoods().getFightGoodsEndTime();
        if (goodsStatus != 0){
            tvGoCart.setEnabled(false);
            tvGoPing.setEnabled(false);
            tvGoPing.setBackgroundResource(R.color.line_gray_cc);
            tvGoCart.setBackgroundResource(R.color.text_gray);
        }
        goodsMainPhoto = detailsData.getGoods().getGoods_main_photo();
        if (detailsData.getGoods().getGoodsType().equals("0")){
            tvGoPing.setText("立即购买");
            goodsLimit = detailsData.getGoods().getGoodsLimit();
        }else if (detailsData.getGoods().getGoodsType().equals("1")){
            tvGoPing.setText("立即拼单");
            if (getCurrentTimeindex>=0){
                tvGoCart.setEnabled(false);
                tvGoPing.setEnabled(false);
                tvGoPing.setBackgroundResource(R.color.line_gray_cc);
                tvGoCart.setBackgroundResource(R.color.text_gray);
            }
        }
        invent_data_list.clear();
        price = "￥" + String.valueOf(detailsData.getGoods().getStorePrice());
        invent_data_list.addAll(detailsData.getGoods().getGoodsInvenDetail());
        goodsName = detailsData.getGoods().getGoodsName();
        goodsNumType = detailsData.getGoods().getGoodsNumType();
        goods_main_photo = detailsData.getGoods().getGoods_main_photo();
        EventBus.getDefault().post(new GoodsH5Bean(detailsData.getGoods().getGoods_details()));
        storeId = String.valueOf(detailsData.getGoods().getStoreId());
        isCollect = detailsData.getGoods().getGoodsWhetherCollect() ;
        endTime = detailsData.getGoods().getFightGoodsEndTime() - detailsData.getCurrentTime();
        if (detailsData.getGoods().getGoodsWhetherCollect() == 0) {
            goodsDetailsLayoutCb.setChecked(false);
        }else {
            goodsDetailsLayoutCb.setChecked(true);
        }
        tired_data_list.addAll(detailsData.getGoods().getTierdPriceAll());
        goodsType = detailsData.getGoods().getGoodsType();
    }

    @Subscribe
    public void onEvent(ArrayList<FlightLsItemData> new_data){
        if (new_data!=null){
            this.new_data = new_data;
        }
    }


}
