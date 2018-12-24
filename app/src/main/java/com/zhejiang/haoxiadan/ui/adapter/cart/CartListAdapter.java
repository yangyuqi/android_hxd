package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Cart;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.List;

/**
 * 购物车
 * Created by KK on 2017/6/7.
 */

public class CartListAdapter extends AbsBaseAdapter<Cart> {

    private CartListCallBack callBack;

    private boolean isEdit = false;

    public interface CartListCallBack {
        void onChooseChange();
        void onNumChange(String cartId);
    }

    public CartListAdapter(Context context, List<Cart> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_cart_list, null);

            holder.chooseHeadCb = (CheckBox) convertView.findViewById(R.id.iv_choose_head);
            holder.supplierNameTv = (TextView) convertView.findViewById(R.id.tv_supplier);
            holder.goodsLV = (NoScrollListView) convertView.findViewById(R.id.lv_goods);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        final CartGoodsListAdapter adapter = new CartGoodsListAdapter(mContext,mDatas.get(position).getCartGoodses());
        holder.goodsLV.setAdapter(adapter);
        holder.supplierNameTv.setText(mDatas.get(position).getSupplierName());
        holder.chooseHeadCb.setChecked(mDatas.get(position).isChoose());

        adapter.setEdit(isEdit);

        adapter.setCallBack(new CartGoodsListAdapter.CartGoodsListCallBack() {
            @Override
            public void onChooseChange() {
                boolean isChecked = true;
                for(CartGoods cartGoods : mDatas.get(position).getCartGoodses()){
                    isChecked = isChecked & cartGoods.isChoose();
                }
                mDatas.get(position).setChoose(isChecked);
                holder.chooseHeadCb.setChecked(isChecked);
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
        holder.chooseHeadCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.chooseHeadCb.isChecked();
                mDatas.get(position).setChoose(isChecked);
                for(CartGoods cartGoods : mDatas.get(position).getCartGoodses()){
                    cartGoods.setChoose(isChecked);
                    for(CartGoodsStyle cartGoodsStyle : cartGoods.getGoodsStyles()){
                        cartGoodsStyle.setChoose(isChecked);
                    }
                }
                adapter.notifyDataSetChanged();
                if(callBack != null){
                    callBack.onChooseChange();
                }
            }
        });

        return convertView;
    }

    public void setEdit(boolean isEdit){
        this.isEdit = isEdit;
    }

    public void setCallBack(CartListCallBack callBack) {
        this.callBack = callBack;
    }

    private class ViewHolder{
        CheckBox chooseHeadCb;
        TextView supplierNameTv;
        NoScrollListView goodsLV;
    }
}
