package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.view.CountDownTextView;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车
 * Created by KK on 2017/9/12.
 */
public class CartGoodsListView extends LinearLayout{

    private Context mContext;
    private CartGoods cartGoods;
    private CartGoodsListCallBack callBack;
    private boolean isEdit = false;
    public interface CartGoodsListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }
    private CountDownTimer countDownTimer;

    private CheckBox chooseGoodsCb;
    private ImageView goodsIconIv;
    private ImageView goodsTypeIv;
    private TextView goodsNameTv;
    private CountDownTextView endTimeTv;
    private LinearLayout styleLl;
    private TextView levelOneTv;
    private TextView levelTwoTv;
    private TextView levelThreeTv;
    private List<TextView> levelPriceTvs = new ArrayList<>();

    public CartGoodsListView(Context context) {
        this(context, null);
    }
    public CartGoodsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CartGoodsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }
    public CartGoodsListView(Context context,CartGoods cartGoods,CartGoodsListCallBack callBack) {
        super(context, null,0);
        this.cartGoods = cartGoods;
        this.callBack = callBack;
        this.mContext = context;
        init();
    }

    private void init(){
        initView();
        initEvent();
    }

    private void initView(){
        inflate(mContext, R.layout.listview_cart_goods_list, this);

        chooseGoodsCb = (CheckBox) findViewById(R.id.iv_choose_goods);
        goodsIconIv = (ImageView) findViewById(R.id.iv_goods_icon);
        goodsNameTv = (TextView) findViewById(R.id.tv_goods_name);
        endTimeTv = (CountDownTextView) findViewById(R.id.tv_end_time);
        styleLl = (LinearLayout) findViewById(R.id.ll_goods_style);
        goodsTypeIv = (ImageView) findViewById(R.id.iv_goods_type);
        levelOneTv = (TextView) findViewById(R.id.tv_level_one);
        levelTwoTv = (TextView) findViewById(R.id.tv_level_two);
        levelThreeTv = (TextView) findViewById(R.id.tv_level_three);

        levelPriceTvs.add(levelOneTv);
        levelPriceTvs.add(levelTwoTv);
        levelPriceTvs.add(levelThreeTv);

        refreshView();
    }

    private void initEvent(){
        chooseGoodsCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = chooseGoodsCb.isChecked();
                cartGoods.setChoose(isChecked);
                for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                    cartGoodsStyle.setChoose(isChecked);
                }
                for(int i=0;i<styleLl.getChildCount();i++){
                    if(styleLl.getChildAt(i) instanceof CartGoodsStyleView){
                        ((CartGoodsStyleView) styleLl.getChildAt(i)).refreshChoose();
                    }
                }
                refreshLevelPrice(isChecked);
                if(callBack != null){
                    callBack.onChooseChange();
                }
            }
        });
        countDownTimer = new CountDownTimer(cartGoods.getTogetherEndMillis(),1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                endTimeTv.setText(TimeUtils.getTimeDiff(cartGoods.getTogetherEndMillis()));
                cartGoods.setTogetherEndMillis(millisUntilFinished);
            }

            @Override
            public void onFinish() {

            }
        };
        countDownTimer.start();
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
        for(int i=0;i<styleLl.getChildCount();i++){
            if(styleLl.getChildAt(i) instanceof CartGoodsStyleView){
                ((CartGoodsStyleView) styleLl.getChildAt(i)).setEdit(isEdit);
            }
        }
    }

    public void refreshChoose(){
        chooseGoodsCb.setChecked(cartGoods.isChoose());
        for(int i=0;i<styleLl.getChildCount();i++){
            if(styleLl.getChildAt(i) instanceof CartGoodsStyleView){
                ((CartGoodsStyleView) styleLl.getChildAt(i)).refreshChoose();
            }
        }
        boolean hasOne = false;//是否有一个选中
        for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
            hasOne = hasOne | cartGoodsStyle.isChoose();
        }
        refreshLevelPrice(hasOne);
    }

    private void refreshView(){
        ImageLoaderUtil.displayImage(cartGoods.getIcon(),goodsIconIv);
        goodsNameTv.setText(cartGoods.getTitle()+" "+cartGoods.getGoodsNo());
        endTimeTv.setText(TimeUtils.getTimeDiff(cartGoods.getTogetherEndMillis()));
        chooseGoodsCb.setChecked(cartGoods.isChoose());
        if("1".equals(cartGoods.getGoodsType())){//（0：普通 1：拼单 ）
            endTimeTv.setVisibility(VISIBLE);
        }else{
            endTimeTv.setVisibility(GONE);
        }
        if(cartGoods.getGoodsStyles()!=null && cartGoods.getGoodsStyles().get(0)!=null && cartGoods.getGoodsStyles().size()>0){
            switch (cartGoods.getGoodsStyles().get(0).getType()){
                case STOCK:
                    goodsTypeIv.setImageResource(R.mipmap.icon_goods);
                    break;
                case FUTURES:
                    goodsTypeIv.setImageResource(R.mipmap.icon_order);
                    break;
            }
        }

        //设置阶梯价格
        for(int i=0;i<cartGoods.getLevelPrices().size();i++){
            LevelPrice levelPrice = cartGoods.getLevelPrices().get(i);
            if(i<levelPriceTvs.size()){
                if(i == cartGoods.getLevelPrices().size()-1){
                    levelPriceTvs.get(i).setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
                }else {
                    levelPriceTvs.get(i).setText(levelPrice.getMinNum() + "-" + levelPrice.getMaxNum() + mContext.getString(R.string.label_jian) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
                }
            }
        }

        for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
            styleLl.addView(new CartGoodsStyleView(mContext, cartGoodsStyle, new CartGoodsStyleView.CartStyleListCallBack() {
                @Override
                public void onChooseChange() {
                    boolean isChecked = true;//是否全选中
                    boolean hasOne = false;//是否有一个选中
                    for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                        isChecked = isChecked & cartGoodsStyle.isChoose();
                        hasOne = hasOne | cartGoodsStyle.isChoose();
                    }
                    cartGoods.setChoose(isChecked);
                    chooseGoodsCb.setChecked(isChecked);
                    if(callBack != null){
                        callBack.onChooseChange();
                    }
                    refreshLevelPrice(hasOne);
                }

                @Override
                public void onNumChange(String cartId) {
                    if(callBack != null){
                        callBack.onNumChange(cartId);
                    }
                    boolean hasOne = false;//是否有一个选中
                    for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                        hasOne = hasOne | cartGoodsStyle.isChoose();
                    }
                    refreshLevelPrice(hasOne);
                }
            }));
//            不需要了
//            styleLl.addView(getDivider());
        }

        refreshLevelPrice(false);
    }

    private void refreshLevelPrice(){
        refreshLevelPrice(true);
    }

    /**
     * @param baseOnChoose 是否建立在选中的情况下
     */
    private void refreshLevelPrice(boolean baseOnChoose){
        int count = 0;
        double price = 0;
        for(CartGoodsStyle cartGoodsStyle:cartGoods.getGoodsStyles()){
            if((baseOnChoose && cartGoodsStyle.isChoose()) || !baseOnChoose){//基于选中时要选中，不基于选中时不需要选中
                count+= cartGoodsStyle.getBuyNum();
            }
        }
        for(LevelPrice levelPrice:cartGoods.getLevelPrices()){
            if(count>=levelPrice.getMinNum() && count <=levelPrice.getMaxNum()){
                price = levelPrice.getPrice();
                levelPrice.setChoose(true);
            }else{
                levelPrice.setChoose(false);
            }
        }
        if(price == 0){
            //没有匹配到就取最大的
            LevelPrice levelPrice = cartGoods.getLevelPrices().get(cartGoods.getLevelPrices().size()-1);
            price = levelPrice.getPrice();
            levelPrice.setChoose(true);
        }

        //设置阶梯价格
        for(int i=0;i<cartGoods.getLevelPrices().size();i++){
            LevelPrice levelPrice = cartGoods.getLevelPrices().get(i);
            if(i == 0){
                if(levelPrice.isChoose()){
                    levelOneTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
                    levelOneTv.getPaint().setFlags(levelOneTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    levelOneTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                    levelOneTv.getPaint().setFlags(levelOneTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }else if(i == 1 ){
                if(levelPrice.isChoose()){
                    levelTwoTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
                    levelTwoTv.getPaint().setFlags(levelTwoTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    levelTwoTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                    levelTwoTv.getPaint().setFlags(levelTwoTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }else if(i == 2){
                if(levelPrice.isChoose()){
                    levelThreeTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
                    levelThreeTv.getPaint().setFlags(levelThreeTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }else{
                    levelThreeTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                    levelThreeTv.getPaint().setFlags(levelThreeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
            }
        }
    }

    private View getDivider(){
        View divider = new View(mContext);
        LinearLayout.LayoutParams productLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
        divider.setLayoutParams(productLayoutParams);
        divider.setBackgroundResource(R.color.bg_gray);
        return divider;
    }

    @Override
    protected void onDetachedFromWindow() {
        countDownTimer.cancel();
        super.onDetachedFromWindow();
    }
}
