package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Cart;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.Goods;

/**
 * Created by KK on 2017/9/12.
 */
public class CartListView extends LinearLayout {

    private Context mContext;
    private CartListCallBack callBack;

    private boolean isEdit = false;

    public interface CartListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }
    private CheckBox chooseHeadCb;
    private TextView supplierNameTv;
    private LinearLayout goodsLl;

    private Cart cart;

    public CartListView(Context context) {
        this(context, null);
    }
    public CartListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CartListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    public CartListView(Context context,Cart cart,CartListCallBack callBack) {
        super(context, null,0);
        this.cart = cart;
        this.callBack = callBack;
        this.mContext = context;
        init();
    }

    private void init(){
        initView();
        initEvent();
    }

    private void initView(){
        inflate(mContext, R.layout.listview_cart_list, this);

        chooseHeadCb = (CheckBox) findViewById(R.id.iv_choose_head);
        supplierNameTv = (TextView) findViewById(R.id.tv_supplier);
        goodsLl = (LinearLayout) findViewById(R.id.ll_goods);

        refreshView();
    }

    private void initEvent(){
        chooseHeadCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = chooseHeadCb.isChecked();
                cart.setChoose(isChecked);
                for(CartGoods cartGoods : cart.getCartGoodses()){
                    cartGoods.setChoose(isChecked);
                    for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                        cartGoodsStyle.setChoose(isChecked);
                    }
                }
                for(int i=0;i<goodsLl.getChildCount();i++){
                    if(goodsLl.getChildAt(i) instanceof CartGoodsListView){
                        ((CartGoodsListView) goodsLl.getChildAt(i)).refreshChoose();
                    }
                }
                if(callBack != null){
                    callBack.onChooseChange();
                }
            }
        });
    }

    public void refreshChoose(){
        chooseHeadCb.setChecked(cart.isChoose());
        for(int i=0;i<goodsLl.getChildCount();i++){
            if(goodsLl.getChildAt(i) instanceof CartGoodsListView){
                ((CartGoodsListView) goodsLl.getChildAt(i)).refreshChoose();
            }
        }
    }

    private void refreshView(){
        supplierNameTv.setText(cart.getSupplierName());
        chooseHeadCb.setChecked(cart.isChoose());
        for(CartGoods cartGoods : cart.getCartGoodses()){
            goodsLl.addView(new CartGoodsListView(mContext, cartGoods, new CartGoodsListView.CartGoodsListCallBack() {
                @Override
                public void onChooseChange() {
                    boolean isChecked = true;
                    for(CartGoods cartGoods : cart.getCartGoodses()){
                        isChecked = isChecked & cartGoods.isChoose();
                    }
                    cart.setChoose(isChecked);
                    chooseHeadCb.setChecked(isChecked);
                    if(callBack != null){
                        callBack.onChooseChange();
                    }
                }

                @Override
                public void onNumChange(String cartId) {
                    if(callBack != null){
                        callBack.onNumChange(cartId);
                    }
                }
            }));
            goodsLl.addView(getDivider());
        }
    }

    private View getDivider(){
        View divider = new View(mContext);
        LinearLayout.LayoutParams productLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
        divider.setLayoutParams(productLayoutParams);
        divider.setBackgroundResource(R.color.bg_gray);
        return divider;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        for(int i=0;i<goodsLl.getChildCount();i++){
            if(goodsLl.getChildAt(i) instanceof CartGoodsListView){
                ((CartGoodsListView) goodsLl.getChildAt(i)).setEdit(isEdit);
            }
        }
    }

}
