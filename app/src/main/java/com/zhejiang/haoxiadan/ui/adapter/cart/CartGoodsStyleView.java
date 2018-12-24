package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

/**
 * 购物车的商品规格
 * Created by KK on 2017/9/12.
 */
public class CartGoodsStyleView extends RelativeLayout{

    private Context mContext;
    private CartGoodsStyle cartGoodsStyle;
    private boolean isEdit = false;
    private CartStyleListCallBack callBack;

    private CheckBox chooseIv;
    private TextView mainStyleNameTv;
    private TextView mainStyleTv;
    private TextView secondStyleNameTv;
    private TextView secondStyleTv;
    private TextView subTv;
    private EditText numEt;
    private TextView addTv;

    public interface CartStyleListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }

    public CartGoodsStyleView(Context context) {
        this(context, null);
    }
    public CartGoodsStyleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CartGoodsStyleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public CartGoodsStyleView(Context context,CartGoodsStyle cartGoodsStyle,CartStyleListCallBack cartStyleListCallBack){
        super(context, null, 0);
        mContext = context;
        this.cartGoodsStyle = cartGoodsStyle;
        this.callBack = cartStyleListCallBack;
        init();
    }

    private void init(){
        initView();
        initEvent();
    }

    private void initView(){
        inflate(mContext, R.layout.listview_cart_goods_style_list, this);

        chooseIv = (CheckBox) findViewById(R.id.iv_choose_style);
        mainStyleNameTv = (TextView) findViewById(R.id.tv_main_style_name);
        mainStyleTv = (TextView) findViewById(R.id.tv_main_style);
        secondStyleNameTv = (TextView) findViewById(R.id.tv_second_style_name);
        secondStyleTv = (TextView) findViewById(R.id.tv_second_style);
        subTv = (TextView) findViewById(R.id.tv_sub);
        numEt = (EditText) findViewById(R.id.et_num);
        addTv = (TextView) findViewById(R.id.tv_add);

        refreshView();
    }

    private void initEvent(){
        chooseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartGoodsStyle.setChoose(chooseIv.isChecked());
                if(callBack != null){
                    callBack.onChooseChange();
                }
            }
        });
        numEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int num = NumberUtils.getIntFromString(numEt.getText().toString());
                if(num==cartGoodsStyle.getBuyNum()){
                    return;
                }
                int beforeBuyNum = cartGoodsStyle.getBuyNum();
                switch (cartGoodsStyle.getType()){
                    case FUTURES:
                        if(num<cartGoodsStyle.getMinBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),cartGoodsStyle.getMinBuyCount()+""));
//                            numEt.setText(cartGoodsStyle.getMinBuyCount()+"");
                        }
                        if(num<=cartGoodsStyle.getMinBuyCount()){
                            subTv.setEnabled(false);
                            subTv.setClickable(false);
                        }else{
                            subTv.setEnabled(true);
                            subTv.setClickable(true);
                        }
                        break;
                    case STOCK:
                        if(cartGoodsStyle.getMaxBuyCount()<cartGoodsStyle.getMinBuyCount()){
//                            numEt.setText(beforeBuyNum+"");
                            ToastUtil.show(mContext,"库存小于最小起订量");
                            return;
                        }
                        if(num<cartGoodsStyle.getMinBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),cartGoodsStyle.getMinBuyCount()+""));
//                            numEt.setText(cartGoodsStyle.getMinBuyCount()+"");
                        }else if(num>cartGoodsStyle.getMaxBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_max_num),cartGoodsStyle.getMaxBuyCount()+""));
//                            numEt.setText(cartGoodsStyle.getMaxBuyCount()+"");
                        }
                        if(num<=cartGoodsStyle.getMinBuyCount()){
                            subTv.setEnabled(false);
                            subTv.setClickable(false);
                        }else{
                            subTv.setEnabled(true);
                            subTv.setClickable(true);
                        }
                        if(num>=cartGoodsStyle.getMaxBuyCount()){
                            addTv.setEnabled(false);
                            addTv.setClickable(false);
                        }else{
                            addTv.setEnabled(true);
                            addTv.setClickable(true);
                        }
                        break;
                }
                cartGoodsStyle.setBuyNum(NumberUtils.getIntFromString(numEt.getText().toString()));
                if(callBack != null){
                    callBack.onNumChange(cartGoodsStyle.getCartId());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = NumberUtils.getIntFromString(numEt.getText().toString())-1;
                if(num>0 && num < cartGoodsStyle.getMinBuyCount()){
                    numEt.setText(cartGoodsStyle.getMinBuyCount()+"");
                    ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),cartGoodsStyle.getMinBuyCount()+""));
                }else if(num >= cartGoodsStyle.getMinBuyCount()){
                    numEt.setText(num+"");
                }
            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = NumberUtils.getIntFromString(numEt.getText().toString()) + 1;
                switch (cartGoodsStyle.getType()){
                    case FUTURES:
                        numEt.setText(num+"");
                        break;
                    case STOCK:
                        if(num>cartGoodsStyle.getMaxBuyCount()){
                            numEt.setText(cartGoodsStyle.getMaxBuyCount()+"");
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_max_num),cartGoodsStyle.getMaxBuyCount()+""));
                        }else{
                            numEt.setText(num+"");
                        }
                        break;
                }
            }
        });
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        if(isEdit){
            setEditNumEditable(false);
        }else{
            setEditNumEditable(true);
        }
    }

    public void refreshChoose(){
        chooseIv.setChecked(cartGoodsStyle.isChoose());
    }

    private void refreshView(){
        mainStyleTv.setText(cartGoodsStyle.getMainStyle());
        secondStyleTv.setText(cartGoodsStyle.getSecondStyle());
        numEt.setText(cartGoodsStyle.getBuyNum()+"");
        chooseIv.setChecked(cartGoodsStyle.isChoose());
        if(isEdit){
            setEditNumEditable(false);
        }else{
            setEditNumEditable(true);
        }
    }

    private void setEditNumEditable(boolean editAble){
        numEt.setEnabled(editAble);
        numEt.setClickable(editAble);
        subTv.setClickable(editAble);
        subTv.setEnabled(editAble);
        addTv.setClickable(editAble);
        addTv.setEnabled(editAble);
    }

}
