package com.zhejiang.haoxiadan.ui.fragment.cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.cart.BalanceStatusListener;
import com.zhejiang.haoxiadan.business.request.cart.CartRequester;
import com.zhejiang.haoxiadan.business.request.cart.DeleteGoodsCartListener;
import com.zhejiang.haoxiadan.business.request.cart.EditGoodsCartCountListener;
import com.zhejiang.haoxiadan.business.request.cart.SelectGoodsCartAllListener;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.model.common.Cart;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.model.common.User;
import com.zhejiang.haoxiadan.ui.activity.cart.SureOrderActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddApplyActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CartListView;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.EmptyView;
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
import butterknife.Unbinder;

public class CartFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM_SHOW_BACK = "ARG_PARAM_SHOW_BACK";
    TabLayout tabLayout;

    private boolean showBack;

    private CommonTitle commonTitle;
    private EmptyView emptyView;
    private CheckBox chooseAllIv;
    private TextView totalPriceTv;
    private RelativeLayout totalPriceRl;
    private TextView settlementTv;
    private SpringView springView;
    private LinearLayout cartlistLl;
    private RelativeLayout cartFooterRl;
    private List<Cart> carts;

    private int pageNo = 1;
    private int pageSize = 10;
    private String type = "finished";

    private TipDialog tipDialog;
    private TipDialog deleteTipDialog;

    //是否在编辑状态
    private boolean isEdit = false;

    private Gson gson = new Gson();

    private Callback callback;

    private CartRequester cartRequester;
    private SelectGoodsCartAllListenerImpl selectGoodsCartAllListener;

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private class SelectGoodsCartAllListenerImpl extends DefaultRequestListener implements SelectGoodsCartAllListener {

        @Override
        protected void onRequestFail() {
            refreshCart(new ArrayList<Cart>());

            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onSelectGoodsCartAllSuccess(List<Cart> cartList) {
            refreshCart(cartList);

            springView.onFinishFreshAndLoad();
        }
    }

    private DeleteGoodsCartListenerImpl deleteGoodsCartListener;

    private class DeleteGoodsCartListenerImpl extends DefaultRequestListener implements DeleteGoodsCartListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onDeleteGoodsCartSuccess() {
            pageNo = 1;
            sendRequest();
        }
    }

    private BalanceStatusListenerImpl balanceStatusListener;

    private class BalanceStatusListenerImpl extends DefaultRequestListener implements BalanceStatusListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBalanceStatusSuccess() {
            Intent intent = new Intent(getActivity(), SureOrderActivity.class);
            startActivity(intent);
        }
    }

    private EditGoodsCartCountListenerImpl editGoodsCartCountListener;

    private class EditGoodsCartCountListenerImpl extends DefaultRequestListener implements EditGoodsCartCountListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onEditGoodsCartCountSuccess() {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showBack = getArguments().getBoolean(ARG_PARAM_SHOW_BACK);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    public interface Callback {
        void onBackClick();
    }

    public CartFragment() {

    }

    public static CartFragment newInstance(boolean showBack) {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_SHOW_BACK, showBack);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRequester = new CartRequester();
        selectGoodsCartAllListener = new SelectGoodsCartAllListenerImpl();
        editGoodsCartCountListener = new EditGoodsCartCountListenerImpl();
        deleteGoodsCartListener = new DeleteGoodsCartListenerImpl();
        balanceStatusListener = new BalanceStatusListenerImpl();

        initView(view);
        initEvent();
        initData();

        return view;
    }

    private void initView(View view) {
        commonTitle = (CommonTitle) view.findViewById(R.id.common_title);
        emptyView = (EmptyView) view.findViewById(R.id.empty_view);
        chooseAllIv = (CheckBox) view.findViewById(R.id.iv_choose_all);
        totalPriceTv = (TextView) view.findViewById(R.id.tv_total_price);
        totalPriceRl = (RelativeLayout) view.findViewById(R.id.rl_total_price);
        settlementTv = (TextView) view.findViewById(R.id.tv_settlement);
        springView = (SpringView) view.findViewById(R.id.spring_view);
        cartlistLl = (LinearLayout) view.findViewById(R.id.ll_cartlist);
        cartFooterRl = (RelativeLayout) view.findViewById(R.id.rl_cart_footer);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        if (showBack) {
            commonTitle.showLeft(true);
        } else {
            commonTitle.showLeft(false);
        }

        tipDialog = new TipDialog(getActivity(), getString(R.string.tip), getString(R.string.tip_only_purchaser_can_buy),
                getString(R.string.tip_update_now), getString(R.string.tip_let_me_think), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                //去增值服务
                Intent intent = new Intent(getActivity(), ValueAddApplyActivity.class);
                intent.putExtra("type", ValueAddActivity.VALUE_ADD_TYPE.PURCHASER);
                startActivity(intent);
            }

            @Override
            public void onNegativeClick() {
            }
        });
        deleteTipDialog = new TipDialog(getActivity(), getString(R.string.tip), getString(R.string.tip_sure_delete_cart), new TipDialog.OnTipDialogListener() {
            @Override
            public void onPositiveClick() {
                cartRequester.deleteGoodsCart(getActivity(), getAccessToken(), carts, deleteGoodsCartListener);
            }

            @Override
            public void onNegativeClick() {
            }
        });

        carts = new ArrayList<>();
    }

    private void initEvent() {
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                if (callback != null) {
                    callback.onBackClick();
                }
            }

            @Override
            public void onRightClick() {
                handleEdit();
            }
        });
        settlementTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit) {
                    boolean hasGoods = false;
                    for (int i = carts.size() - 1; i >= 0; i--) {
                        Cart cart = carts.get(i);
                        for (int j = cart.getCartGoodses().size() - 1; j >= 0; j--) {
                            for (int k = cart.getCartGoodses().get(j).getGoodsStyles().size() - 1; k >= 0; k--) {
                                if (cart.getCartGoodses().get(j).getGoodsStyles().get(k).isChoose()) {
                                    hasGoods = true;
                                }
                            }
                        }
                    }

                    if (!hasGoods) {
                        ToastUtil.show(getActivity(), R.string.tip_select_goods);
                        return;
                    }

                    //删除
                    deleteTipDialog.show();
                } else {
                    //结算
                    //具有购买资格
                    User.USER_TYPE userType = (User.USER_TYPE) GlobalDataUtil.getObject(getActivity(), Constants.GLOBAL_DATA_KEY_USER_TYPE);
                    if (userType == User.USER_TYPE.BUYER) {
                        gotoSureOrder();
                    } else {
                        tipDialog.show();
                    }
                }
            }
        });

        chooseAllIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshAllChoose();
            }
        });

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                sendRequest();
            }

            @Override
            public void onLoadmore() {
                pageNo++;
                sendRequest();
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    type="finished";
                }else {
                    type = "worsted";
                }
                sendRequest();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void sendRequest() {
        cartRequester.selectGoodsCartAll(getActivity(), getAccessToken(), pageNo + "", pageSize + "",type, selectGoodsCartAllListener);
    }

    private void handleEdit() {
        if (isEdit) {//完成
            isEdit = false;
            commonTitle.setRightStr(R.string.btn_manage);
            totalPriceRl.setVisibility(View.VISIBLE);

            settlementTv.setBackgroundResource(R.color.main_red);
            settlementTv.setText(R.string.btn_settlement);
        } else {//编辑
            isEdit = true;
            commonTitle.setRightStr(R.string.btn_complete);
            totalPriceRl.setVisibility(View.GONE);

            settlementTv.setBackgroundResource(R.color.bg_red);
            settlementTv.setText(R.string.btn_delete);
        }
        for (int i = 0; i < cartlistLl.getChildCount(); i++) {
            if (cartlistLl.getChildAt(i) instanceof CartListView) {
                ((CartListView) cartlistLl.getChildAt(i)).setEdit(isEdit);
            }
        }
    }

    private void gotoSureOrder() {
        ArrayList<Cart> tempCarts = new ArrayList<>();
        for (Cart cart : carts) {
            //用gson来切断引用
            tempCarts.add(gson.fromJson(gson.toJson(cart), Cart.class));
        }
        for (int i = tempCarts.size() - 1; i >= 0; i--) {
            Cart cart = tempCarts.get(i);
            for (int j = cart.getCartGoodses().size() - 1; j >= 0; j--) {
                for (int k = cart.getCartGoodses().get(j).getGoodsStyles().size() - 1; k >= 0; k--) {
                    if (!cart.getCartGoodses().get(j).getGoodsStyles().get(k).isChoose()) {
                        cart.getCartGoodses().get(j).getGoodsStyles().remove(k);
                    }
                }
                if (cart.getCartGoodses().get(j).getGoodsStyles().size() == 0) {
                    cart.getCartGoodses().remove(j);
                }
            }
            if (cart.getCartGoodses().size() == 0) {
                tempCarts.remove(i);
            }
        }

        if (tempCarts.size() == 0) {
            ToastUtil.show(getActivity(), R.string.tip_select_goods);
            return;
        }

        NUM_ERROR checkNum = NUM_ERROR.RIGHT;
        for (int i = tempCarts.size() - 1; i >= 0; i--) {
            Cart cart = tempCarts.get(i);
            for (int j = cart.getCartGoodses().size() - 1; j >= 0; j--) {
                int totalSingleGoodsCount = 0;
                for (int k = cart.getCartGoodses().get(j).getGoodsStyles().size() - 1; k >= 0; k--) {
                    CartGoodsStyle goodsStyle = cart.getCartGoodses().get(j).getGoodsStyles().get(k);
                    totalSingleGoodsCount = totalSingleGoodsCount + goodsStyle.getBuyNum();
                    if ("1".equals(cart.getCartGoodses().get(j).getGoodsType())) {//校验拼单商品的最小、最大购买量
                        if (goodsStyle.getType() == CartGoodsStyle.GOODS_TYPE.STOCK) {
                            if (goodsStyle.getBuyNum() > goodsStyle.getMaxBuyCount()) {
                                checkNum = NUM_ERROR.BIGGER;
                                break;
                            }
                        }
                        if (goodsStyle.getBuyNum() < goodsStyle.getMinBuyCount() || goodsStyle.getBuyNum() == 0) {
                            checkNum = NUM_ERROR.SMALL;
                            break;
                        }
                    } else {//校验普通商品的最大购买量
                        if (goodsStyle.getType() == CartGoodsStyle.GOODS_TYPE.STOCK) {
                            if (goodsStyle.getBuyNum() > goodsStyle.getMaxBuyCount()) {
                                checkNum = NUM_ERROR.BIGGER;
                                break;
                            }
                        }
                    }
                }
                //校验普通商品的最小购买量
                if ("0".equals(cart.getCartGoodses().get(j).getGoodsType()) && totalSingleGoodsCount < cart.getCartGoodses().get(j).getGoodsLimit()) {
                    checkNum = NUM_ERROR.SMALL;
                    break;
                }
            }
        }
        switch (checkNum) {
            case BIGGER:
                ToastUtil.show(getActivity(), R.string.tip_buy_num_bigger_stock);
                return;
            case SMALL:
                ToastUtil.show(getActivity(), R.string.tip_buy_num_small_stock);
                return;
        }

        cartRequester.balanceStatus(getActivity(), getAccessToken(), tempCarts, balanceStatusListener);
    }

    private enum NUM_ERROR {
        RIGHT,//正确
        BIGGER,//大于库存量
        SMALL//小于最低购买量
    }


    private void initData() {
    }


    private void refreshCart(List<Cart> cartList) {
        if (pageNo == 1) {
            carts.clear();
            cartlistLl.removeAllViews();
            chooseAllIv.setChecked(false);
        }
        carts.addAll(cartList);
        if (carts.size() == 0) {
            cartFooterRl.setVisibility(View.GONE);
            if (isEdit) {
                commonTitle.setRightStr(R.string.btn_complete);
            } else {
                commonTitle.setRightStr(R.string.btn_manage);
            }
            emptyView.setVisibility(View.VISIBLE);
        } else {
            cartFooterRl.setVisibility(View.VISIBLE);
            if (isEdit) {
                commonTitle.setRightStr(R.string.btn_complete);
            } else {
                commonTitle.setRightStr(R.string.btn_manage);
            }
            emptyView.setVisibility(View.GONE);
        }
        for (Cart cart : cartList) {
            cartlistLl.addView(new CartListView(getActivity(), cart, new CartListView.CartListCallBack() {
                @Override
                public void onChooseChange() {
                    boolean isChecked = true;
                    for (Cart cart : carts) {
                        isChecked = isChecked & cart.isChoose();
                    }
                    chooseAllIv.setChecked(isChecked);
                    refreshTotalPrice();
                }

                @Override
                public void onNumChange(String cartId) {
                    for (Cart cart : carts) {
                        for (CartGoods cartGoods : cart.getCartGoodses())
                            for (CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()) {
                                if (cartId.equals(cartGoodsStyle.getCartId())) {
                                    cartRequester.editGoodsCartCount(getActivity(), getAccessToken(), cartGoods.getId(), cartId, cartGoods.getGoodsType(), cartGoodsStyle.getBuyNum(), cartGoodsStyle.getId(),type ,editGoodsCartCountListener);
                                    refreshTotalPrice();
                                }
                            }
                    }
                }
            }));
            cartlistLl.addView(getDivider());
        }
        refreshTotalPrice();
    }

    @Override
    public void onResume() {
        if (!getAccessToken().equals("")) {
            pageNo = 1;
            cartRequester.selectGoodsCartAll(getActivity(), getAccessToken(), pageNo + "", pageSize + "",type, selectGoodsCartAllListener);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isEdit) {
                handleEdit();
            }
            chooseAllIv.setChecked(false);
            refreshAllChoose();
        }
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    private void refreshAllChoose() {
        boolean isCheck = chooseAllIv.isChecked();
        if (carts != null) {
            for (Cart cart : carts) {
                cart.setChoose(isCheck);
                for (CartGoods cartGoods : cart.getCartGoodses()) {
                    cartGoods.setChoose(isCheck);
                    for (CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()) {
                        cartGoodsStyle.setChoose(isCheck);
                    }
                }
            }
            refreshTotalPrice();
        }

        for (int i = 0; i < cartlistLl.getChildCount(); i++) {
            if (cartlistLl.getChildAt(i) instanceof CartListView) {
                ((CartListView) cartlistLl.getChildAt(i)).refreshChoose();
            }
        }
    }

    private void refreshTotalPrice() {
        double totalPrice = 0;
        for (Cart cart : carts) {
            for (CartGoods cartGoods : cart.getCartGoodses()) {
                int count = 0;
                double price = 0;
                for (CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()) {
                    if (cartGoodsStyle.isChoose()) {
                        count += cartGoodsStyle.getBuyNum();
                    }
                }
                for (LevelPrice levelPrice : cartGoods.getLevelPrices()) {
                    if (count >= levelPrice.getMinNum() && count <= levelPrice.getMaxNum()) {
                        price = levelPrice.getPrice();
                    }
                }
                if (price == 0) {
                    //没有匹配到就取最大的
                    price = cartGoods.getLevelPrices().get(cartGoods.getLevelPrices().size() - 1).getPrice();
                }
                totalPrice += price * count;
            }
        }
        totalPriceTv.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(totalPrice + ""));
    }

    private View getDivider() {
        View divider = new View(getActivity());
        LinearLayout.LayoutParams productLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 10);
        divider.setLayoutParams(productLayoutParams);
        divider.setBackgroundResource(R.color.bg_gray);
        return divider;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
