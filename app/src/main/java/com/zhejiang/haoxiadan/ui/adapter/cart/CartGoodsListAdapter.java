package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.CountDownTextView;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 购物车
 * Created by KK on 2017/6/7.
 */

public class CartGoodsListAdapter extends AbsBaseAdapter<CartGoods>{

    Map<TextView,CartCountDownUtil> leftTimeMap = new HashMap<>();

    private CartGoodsListCallBack callBack;

    private boolean isEdit = false;

    public interface CartGoodsListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }

    public CartGoodsListAdapter(Context context, List<CartGoods> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_cart_goods_list, null);

            holder.chooseGoodsCb = (CheckBox) convertView.findViewById(R.id.iv_choose_goods);
            holder.goodsIconIv = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            holder.goodsNameTv = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.endTimeTv = (CountDownTextView) convertView.findViewById(R.id.tv_end_time);
            holder.styleLV = (NoScrollListView) convertView.findViewById(R.id.lv_goods_style);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final CartGoodsStyleAdapter adapter = new CartGoodsStyleAdapter(mContext,mDatas.get(position).getGoodsStyles());
        holder.styleLV.setAdapter(adapter);
        ImageLoaderUtil.displayImage(mDatas.get(position).getIcon(),holder.goodsIconIv);
        holder.goodsNameTv.setText(mDatas.get(position).getTitle());
        holder.endTimeTv.setText(TimeUtils.getTimeDiff(mDatas.get(position).getTogetherEndMillis()));

        holder.chooseGoodsCb.setChecked(mDatas.get(position).isChoose());

        adapter.setEdit(isEdit);

        adapter.setCallBack(new CartGoodsStyleAdapter.CartStyleListCallBack() {
            @Override
            public void onChooseChange() {
                boolean isChecked = true;
                for(CartGoodsStyle cartGoodsStyle : mDatas.get(position).getGoodsStyles()){
                    isChecked = isChecked & cartGoodsStyle.isChoose();
                }
                mDatas.get(position).setChoose(isChecked);
                holder.chooseGoodsCb.setChecked(isChecked);
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

        });
        holder.chooseGoodsCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.chooseGoodsCb.isChecked();
                mDatas.get(position).setChoose(isChecked);
                if(callBack != null){
                    callBack.onChooseChange();
                }
                for(CartGoodsStyle cartGoodsStyle : mDatas.get(position).getGoodsStyles()){
                    cartGoodsStyle.setChoose(isChecked);
                }
                adapter.notifyDataSetChanged();
            }
        });


        //获取控件对应的倒计时控件是否存在,存在就取消,解决时间重叠问题
        CartCountDownUtil tc = leftTimeMap.get(holder.endTimeTv);
        if (tc != null) {
            tc.cancel();
            tc = null;
        }
        //实例化倒计时类
        CartCountDownUtil cdu = new CartCountDownUtil(mDatas.get(position).getTogetherEndMillis(), 1000, holder.endTimeTv,mDatas.get(position));
        //开启倒计时
//        cdu.start();
        //此处需要map集合将控件和倒计时类关联起来,就是这里
        leftTimeMap.put(holder.endTimeTv, cdu);

        return convertView;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public void setCallBack(CartGoodsListCallBack callBack) {
        this.callBack = callBack;
    }

    private class ViewHolder{
        CheckBox chooseGoodsCb;
        ImageView goodsIconIv;
        TextView goodsNameTv;
        CountDownTextView endTimeTv;
        NoScrollListView styleLV;
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

    private class CartCountDownUtil extends CountDownTimer {

        private TextView textView;
        private CartGoods cartGoods;

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public CartCountDownUtil(long millisInFuture, long countDownInterval,TextView textView,CartGoods cartGoods) {
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
}
