package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.List;

/**
 * 购物车的商品规格
 * Created by KK on 2017/8/23.
 */

public class CartGoodsStyleAdapter extends AbsBaseAdapter<CartGoodsStyle> {

    private CartStyleListCallBack callBack;

    private boolean isEdit = false;

    public interface CartStyleListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }

    public CartGoodsStyleAdapter(Context context, List<CartGoodsStyle> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_cart_goods_style_list, null);

            holder.chooseIv = (CheckBox) convertView.findViewById(R.id.iv_choose_style);
            holder.mainStyleNameTv = (TextView) convertView.findViewById(R.id.tv_main_style_name);
            holder.mainStyleTv = (TextView) convertView.findViewById(R.id.tv_main_style);
            holder.secondStyleNameTv = (TextView) convertView.findViewById(R.id.tv_second_style_name);
            holder.secondStyleTv = (TextView) convertView.findViewById(R.id.tv_second_style);
            holder.priceTv = (TextView) convertView.findViewById(R.id.tv_price);
            holder.subTv = (TextView) convertView.findViewById(R.id.tv_sub);
            holder.numEt = (EditText) convertView.findViewById(R.id.et_num);
            holder.addTv = (TextView) convertView.findViewById(R.id.tv_add);
            holder.editNumLayout = (LinearLayout) convertView.findViewById(R.id.ll_edit_num);
            holder.numTv = (TextView) convertView.findViewById(R.id.tv_num);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
//        holder.mainStyleNameTv.setText(mDatas.get(position).getMainStyleName()+":");
        holder.mainStyleTv.setText(mDatas.get(position).getMainStyle());
//        holder.secondStyleNameTv.setText(mDatas.get(position).getSecondStyleName());
        holder.secondStyleTv.setText(mDatas.get(position).getSecondStyle());
        holder.priceTv.setText(mDatas.get(position).getPrice()+"");
        holder.numEt.setText(mDatas.get(position).getBuyNum()+"");
        holder.numTv.setText(mDatas.get(position).getBuyNum()+"");

        holder.chooseIv.setChecked(mDatas.get(position).isChoose());

        if(isEdit){
            holder.editNumLayout.setVisibility(View.GONE);
            holder.numTv.setVisibility(View.VISIBLE);
        }else{
            holder.editNumLayout.setVisibility(View.VISIBLE);
            holder.numTv.setVisibility(View.GONE);
        }

        holder.chooseIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.get(position).setChoose(holder.chooseIv.isChecked());
                if(callBack != null){
                    callBack.onChooseChange();
                }
            }
        });
        TextWatcher textWatcher = (TextWatcher)holder.numEt.getTag();
        if(textWatcher != null){
            holder.numEt.removeTextChangedListener(textWatcher);
        }
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                int num = NumberUtils.getIntFromString(holder.numEt.getText().toString());
                switch (mDatas.get(position).getType()){
                    case FUTURES:
                        if(num<mDatas.get(position).getMinBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),mDatas.get(position).getMinBuyCount()+""));
                            holder.numEt.setText(mDatas.get(position).getMinBuyCount()+"");
                        }
                        if(num<=mDatas.get(position).getMinBuyCount()){
                            holder.subTv.setEnabled(false);
                            holder.subTv.setClickable(false);
                        }else{
                            holder.subTv.setEnabled(true);
                            holder.subTv.setClickable(true);
                        }
                        break;
                    case STOCK:
                        if(num<mDatas.get(position).getMinBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),mDatas.get(position).getMinBuyCount()+""));
                            holder.numEt.setText(mDatas.get(position).getMinBuyCount()+"");
                        }else if(num>mDatas.get(position).getMaxBuyCount()){
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_max_num),mDatas.get(position).getMaxBuyCount()+""));
                            holder.numEt.setText(mDatas.get(position).getMaxBuyCount()+"");
                        }
                        if(num<=mDatas.get(position).getMinBuyCount()){
                            holder.subTv.setEnabled(false);
                            holder.subTv.setClickable(false);
                        }else{
                            holder.subTv.setEnabled(true);
                            holder.subTv.setClickable(true);
                        }
                        if(num>=mDatas.get(position).getMaxBuyCount()){
                            holder.addTv.setEnabled(false);
                            holder.addTv.setClickable(false);
                        }else{
                            holder.addTv.setEnabled(true);
                            holder.addTv.setClickable(true);
                        }
                        break;
                }
                mDatas.get(position).setBuyNum(NumberUtils.getIntFromString(holder.numEt.getText().toString()));
                if(callBack != null){
                    callBack.onNumChange(mDatas.get(position).getCartId());
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        holder.numEt.addTextChangedListener(textWatcher);
        holder.numEt.setTag(textWatcher);
        holder.subTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = NumberUtils.getIntFromString(holder.numEt.getText().toString())-1;
                if(num>0 && num < mDatas.get(position).getMinBuyCount()){
                    holder.numEt.setText(mDatas.get(position).getMinBuyCount()+"");
                    ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_min_num),mDatas.get(position).getMinBuyCount()+""));
                }else if(num >= mDatas.get(position).getMinBuyCount()){
                    holder.numEt.setText(num+"");
                }
            }
        });
        holder.addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = NumberUtils.getIntFromString(holder.numEt.getText().toString())+1;
                switch (mDatas.get(position).getType()){
                    case FUTURES:
                        holder.numEt.setText(num+"");
                        break;
                    case STOCK:
                        if(num>mDatas.get(position).getMaxBuyCount()){
                            holder.numEt.setText(mDatas.get(position).getMaxBuyCount()+"");
                            ToastUtil.show(mContext,String.format(mContext.getString(R.string.tip_goods_buy_max_num),mDatas.get(position).getMaxBuyCount()+""));
                        }else{
                            holder.numEt.setText(num+"");
                        }
                        break;
                }
            }
        });


        return convertView;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public void setCallBack(CartStyleListCallBack callBack) {
        this.callBack = callBack;
    }

    private class ViewHolder{
        CheckBox chooseIv;
        TextView mainStyleNameTv;
        TextView mainStyleTv;
        TextView secondStyleNameTv;
        TextView secondStyleTv;
        TextView priceTv;
        TextView subTv;
        EditText numEt;
        TextView addTv;
        LinearLayout editNumLayout;
        TextView numTv;
    }

}
