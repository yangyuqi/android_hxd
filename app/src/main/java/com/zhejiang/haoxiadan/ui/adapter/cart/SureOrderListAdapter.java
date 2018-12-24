package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.LevelPrice;
import com.zhejiang.haoxiadan.model.requestData.in.CartGoodsNew;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 确认订单 订单列表
 * Created by KK on 2017/8/25.
 */

public class SureOrderListAdapter extends BaseAdapter {

    private List<CartGoods> mDatas = new ArrayList<>();
    private Context mContext ;

    Map<TextView,CartCountDownUtil> leftTimeMap = new HashMap<>();

    public SureOrderListAdapter(Context context, List<CartGoods> datas) {
        this.mContext = context ;
        mDatas = datas ;
    }

    public void setData(List<CartGoods> datas){
        mDatas = datas ;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_new_sure_order_list, null);
            holder.orderNoTv = (TextView) convertView.findViewById(R.id.tv_order_no);
//            holder.goodsIconIv = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
//            holder.goodsNameTv = (TextView) convertView.findViewById(R.id.tv_goods_name);
//            holder.endTimeTv = (TextView) convertView.findViewById(R.id.tv_end_time);
            holder.totalShipTv = (TextView) convertView.findViewById(R.id.tv_total_ship);
            holder.messageEt = (EditText) convertView.findViewById(R.id.et_message);
            holder.goodsStyleLV = (NoScrollListView) convertView.findViewById(R.id.lv_new_goods_style);

//            holder.levelOneTv = (TextView) convertView.findViewById(R.id.tv_level_one);
//            holder.levelTwoTv = (TextView) convertView.findViewById(R.id.tv_level_two);
//            holder.levelThreeTv = (TextView) convertView.findViewById(R.id.tv_level_three);
//            holder.goodsTypeIv = (ImageView) convertView.findViewById(R.id.iv_goods_type);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        CartGoods cartGoods = mDatas.get(position);

        CommonAdapter<CartGoodsNew> commonAdapter = new CommonAdapter<CartGoodsNew>(convertView.getContext(),cartGoods.getCartGoodsList(),R.layout.listview_sure_order_list) {
            @Override
            public void convert(com.zhejiang.haoxiadan.adapter.ViewHolder helper, CartGoodsNew item) {
                ImageLoaderUtil.displayImage(item.getIcon(), (ImageView) helper.getView(R.id.iv_goods_icon));
                helper.setText(R.id.tv_goods_name,item.getTitle());
                helper.setText(R.id.tv_end_time,mContext.getString(R.string.label_shenyu_)+TimeUtils.getTimeDiff(item.getTogetherEndMillis()));
                NoScrollListView listView = helper.getView(R.id.lv_goods_style);
                SureOrderGoodsStyleAdapter adapter = new SureOrderGoodsStyleAdapter(mContext,item.getGoodsStyles());
                listView.setAdapter(adapter);

                //设置阶梯价格
                for(int i=0;i<item.getLevelPrices().size();i++){
                    LevelPrice levelPrice = item.getLevelPrices().get(i);
                    if(i == 0){
                        if(i == item.getLevelPrices().size()-1){
                            ((TextView)helper.getView(R.id.tv_level_one)).setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_one)).setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
                        }
                    }else if(i == 1 ){
                        if(i == item.getLevelPrices().size()-1){
                            ((TextView)helper.getView(R.id.tv_level_two)).setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + mContext.getString(R.string.label_jian) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_two)).setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
                        }
                    }else if(i == 2){
                        if(i == item.getLevelPrices().size()-1){
                            ((TextView)helper.getView(R.id.tv_level_three)).setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + mContext.getString(R.string.label_jian) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_three)).setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
                        }
                    }
                }


                int totalCount = 0;
                for(CartGoodsStyle cartGoodsStyle : item.getGoodsStyles()){
                    totalCount = totalCount + cartGoodsStyle.getBuyNum();
                }
                for(LevelPrice levelPrice : item.getLevelPrices()){
                    if(totalCount>=levelPrice.getMinNum() && totalCount <= levelPrice.getMaxNum()){
                        levelPrice.setChoose(true);
                    }
                    if(levelPrice.getMinNum()>=levelPrice.getMaxNum() && totalCount>=levelPrice.getMinNum() && totalCount >= levelPrice.getMaxNum()){
                        levelPrice.setChoose(true);
                    }
                }

                for(int i=0;i<item.getLevelPrices().size();i++){
                    LevelPrice levelPrice = item.getLevelPrices().get(i);
                    if(i == 0){
                        if(levelPrice.isChoose()){
                            ((TextView)helper.getView(R.id.tv_level_one)).setTextColor(mContext.getResources().getColor(R.color.text_red));
                            ((TextView)helper.getView(R.id.tv_level_one)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_one)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_one)).setTextColor(mContext.getResources().getColor(R.color.text_gray));
                            ((TextView)helper.getView(R.id.tv_level_one)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_one)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }else if(i == 1 ){
                        if(levelPrice.isChoose()){
                            ((TextView)helper.getView(R.id.tv_level_two)).setTextColor(mContext.getResources().getColor(R.color.text_red));
                            ((TextView)helper.getView(R.id.tv_level_two)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_two)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_two)).setTextColor(mContext.getResources().getColor(R.color.text_gray));
                            ((TextView)helper.getView(R.id.tv_level_two)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_two)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }else if(i == 2){
                        if(levelPrice.isChoose()){
                            ((TextView)helper.getView(R.id.tv_level_three)).setTextColor(mContext.getResources().getColor(R.color.text_red));
                            ((TextView)helper.getView(R.id.tv_level_three)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_three)).getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        }else{
                            ((TextView)helper.getView(R.id.tv_level_three)).setTextColor(mContext.getResources().getColor(R.color.text_gray));
                            ((TextView)helper.getView(R.id.tv_level_three)).getPaint().setFlags(((TextView)helper.getView(R.id.tv_level_three)).getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }
                    }
                }

                if(item.getGoodsStyles()!=null && item.getGoodsStyles().get(0)!=null && item.getGoodsStyles().size()>0){
                    switch (item.getGoodsStyles().get(0).getType()){
                        case STOCK:
                            ((ImageView)helper.getView(R.id.iv_goods_type)).setImageResource(R.mipmap.icon_goods);
                            break;
                        case FUTURES:
                            ((ImageView)helper.getView(R.id.iv_goods_type)).setImageResource(R.mipmap.icon_order);
                            break;
                    }
                }

                switch (item.getGoodsType()){
                    case "0"://普通
                        helper.getView(R.id.tv_end_time).setVisibility(View.GONE);
                        break;
                    case "1"://拼单
                        helper.getView(R.id.tv_end_time).setVisibility(View.VISIBLE);
                        break;
                }


                CartCountDownUtil tc = leftTimeMap.get((TextView) helper.getView(R.id.tv_end_time));
                if (tc != null) {
                    tc.cancel();
                    tc = null;
                }
                //实例化倒计时类
                CartCountDownUtil cdu = new CartCountDownUtil(item.getTogetherEndMillis(), 1000,(TextView)helper.getView(R.id.tv_end_time),item);
                //开启倒计时
                cdu.start();
                //此处需要map集合将控件和倒计时类关联起来,就是这里
                leftTimeMap.put((TextView) helper.getView(R.id.tv_end_time), cdu);

            }
        };

        holder.goodsStyleLV.setAdapter(commonAdapter);

//        SureOrderGoodsStyleAdapter adapter = new SureOrderGoodsStyleAdapter(mContext,cartGoods.getGoodsStyles());
//        holder.goodsStyleLV.setAdapter(adapter);
        holder.orderNoTv.setText(mContext.getString(R.string.label_order)+(position+1));
//        ImageLoaderUtil.displayImage(cartGoods.getIcon(),holder.goodsIconIv);
//        holder.goodsNameTv.setText(cartGoods.getTitle());
//        holder.endTimeTv.setText(mContext.getString(R.string.label_shenyu_)+TimeUtils.getTimeDiff(cartGoods.getTogetherEndMillis()));
        holder.totalShipTv.setText(mContext.getString(R.string.label_money)+ NumberUtils.formatToDouble(cartGoods.getShipPrice()+""));

        //设置阶梯价格
//        for(int i=0;i<cartGoods.getLevelPrices().size();i++){
//            LevelPrice levelPrice = cartGoods.getLevelPrices().get(i);
//            if(i == 0){
//                if(i == cartGoods.getLevelPrices().size()-1){
//                    holder.levelOneTv.setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
//                }else{
//                    holder.levelOneTv.setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
//                }
//            }else if(i == 1 ){
//                if(i == cartGoods.getLevelPrices().size()-1){
//                    holder.levelTwoTv.setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + mContext.getString(R.string.label_jian) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
//                }else{
//                    holder.levelTwoTv.setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
//                }
//            }else if(i == 2){
//                if(i == cartGoods.getLevelPrices().size()-1){
//                    holder.levelThreeTv.setText(levelPrice.getMinNum()+mContext.getString(R.string.label_jiyishang) + mContext.getString(R.string.label_jian) + ":" + mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(levelPrice.getPrice() + ""));
//                }else{
//                    holder.levelThreeTv.setText(levelPrice.getMinNum()+"-"+levelPrice.getMaxNum()+mContext.getString(R.string.label_jian)+":"+mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(levelPrice.getPrice()+""));
//                }
//            }
//        }

//        int totalCount = 0;
//        for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
//            totalCount = totalCount + cartGoodsStyle.getBuyNum();
//        }
//        for(LevelPrice levelPrice : cartGoods.getLevelPrices()){
//            if(totalCount>=levelPrice.getMinNum() && totalCount <= levelPrice.getMaxNum()){
//                levelPrice.setChoose(true);
//            }
//            if(levelPrice.getMinNum()>=levelPrice.getMaxNum() && totalCount>=levelPrice.getMinNum() && totalCount >= levelPrice.getMaxNum()){
//                levelPrice.setChoose(true);
//            }
//        }

//        for(int i=0;i<cartGoods.getLevelPrices().size();i++){
//            LevelPrice levelPrice = cartGoods.getLevelPrices().get(i);
//            if(i == 0){
//                if(levelPrice.isChoose()){
//                    holder.levelOneTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
//                    holder.levelOneTv.getPaint().setFlags(holder.levelOneTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                }else{
//                    holder.levelOneTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
//                    holder.levelOneTv.getPaint().setFlags(holder.levelOneTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                }
//            }else if(i == 1 ){
//                if(levelPrice.isChoose()){
//                    holder.levelTwoTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
//                    holder.levelTwoTv.getPaint().setFlags(holder.levelTwoTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                }else{
//                    holder.levelTwoTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
//                    holder.levelTwoTv.getPaint().setFlags(holder.levelTwoTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                }
//            }else if(i == 2){
//                if(levelPrice.isChoose()){
//                    holder.levelThreeTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
//                    holder.levelThreeTv.getPaint().setFlags(holder.levelThreeTv.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
//                }else{
//                    holder.levelThreeTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
//                    holder.levelThreeTv.getPaint().setFlags(holder.levelThreeTv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//                }
//            }
//        }
//        if(cartGoods.getGoodsStyles()!=null && cartGoods.getGoodsStyles().get(0)!=null && cartGoods.getGoodsStyles().size()>0){
//            switch (cartGoods.getGoodsStyles().get(0).getType()){
//                case STOCK:
//                    holder.goodsTypeIv.setImageResource(R.mipmap.icon_goods);
//                    break;
//                case FUTURES:
//                    holder.goodsTypeIv.setImageResource(R.mipmap.icon_order);
//                    break;
//            }
//        }
//
//        switch (cartGoods.getGoodsType()){
//            case "0"://普通
//                holder.endTimeTv.setVisibility(View.GONE);
//                break;
//            case "1"://拼单
//                holder.endTimeTv.setVisibility(View.VISIBLE);
//                break;
//        }

        holder.messageEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mDatas.get(position).setMessage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //获取控件对应的倒计时控件是否存在,存在就取消,解决时间重叠问题
//        CartCountDownUtil tc = leftTimeMap.get(holder.endTimeTv);
//        if (tc != null) {
//            tc.cancel();
//            tc = null;
//        }
//        //实例化倒计时类
//        CartCountDownUtil cdu = new CartCountDownUtil(mDatas.get(position).getTogetherEndMillis(), 1000, holder.endTimeTv,mDatas.get(position));
//        //开启倒计时
//        cdu.start();
//        //此处需要map集合将控件和倒计时类关联起来,就是这里
//        leftTimeMap.put(holder.endTimeTv, cdu);

        return convertView;
    }

    private class ViewHolder{
        TextView orderNoTv;
        ImageView goodsIconIv;
        TextView goodsNameTv;
        TextView endTimeTv;
        TextView totalShipTv;
        EditText messageEt;
        NoScrollListView goodsStyleLV;
        TextView levelOneTv;
        TextView levelTwoTv;
        TextView levelThreeTv;
        ImageView goodsTypeIv;
    }


    private class CartCountDownUtil extends CountDownTimer {

        private TextView textView;
        private CartGoodsNew cartGoods;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CartCountDownUtil(long millisInFuture, long countDownInterval,TextView textView,CartGoodsNew cartGoods) {
            super(millisInFuture, countDownInterval);
            this.textView = textView;
            this.cartGoods = cartGoods;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText(TimeUtils.getTimeDiff(cartGoods.getTogetherEndMillis()));
            cartGoods.setTogetherEndMillis(millisUntilFinished);
        }

        @Override
        public void onFinish() {

        }
    }


    //终止所有的
    public void cancelAllTimers() {
        Set<Map.Entry<TextView, CartCountDownUtil>> s = leftTimeMap.entrySet();
        Iterator it = s.iterator();
        while (it.hasNext()) {
            try {
                Map.Entry pairs = (Map.Entry) it.next();
                CartCountDownUtil cdt = (CartCountDownUtil) pairs.getValue();
                cdt.cancel();
                cdt = null;
            } catch (Exception e) {
            }
        }
        it = null;
        s = null;
        leftTimeMap.clear();
    }
}
