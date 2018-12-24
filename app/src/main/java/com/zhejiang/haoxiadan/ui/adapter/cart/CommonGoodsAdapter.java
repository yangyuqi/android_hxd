package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.List;

/**
 * 通用商品
 * Created by KK on 2017/8/25.
 */

public class CommonGoodsAdapter extends AbsBaseAdapter<Goods>{

    public CommonGoodsAdapter(Context context, List<Goods> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_common_goods, null);

            holder.goodsIconIv = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            holder.goodsNameTv = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsPriceTv = (TextView) convertView.findViewById(R.id.tv_price);
            holder.monthSaleTv = (TextView) convertView.findViewById(R.id.tv_month_sale);
            holder.cartTv = (ImageView) convertView.findViewById(R.id.iv_cart);
            holder.moreTv = (TextView) convertView.findViewById(R.id.tv_more);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ImageLoaderUtil.displayImage(mDatas.get(position).getIcon(),holder.goodsIconIv);
        String title = mDatas.get(position).getTitle();
        if(mDatas.get(position).getGoodsNo()!=null){
            title= title+" "+mDatas.get(position).getGoodsNo();
        }
        holder.goodsNameTv.setText(title);
        holder.goodsPriceTv.setText(mContext.getString(R.string.label_money)+ NumberUtils.getDoubleFromString(""+mDatas.get(position).getPrice()));
        holder.monthSaleTv.setText(String.format(mContext.getString(R.string.label_month_sale_jian),mDatas.get(position).getMonthSale()+""));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", mDatas.get(position).getId());
                mContext.startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView goodsIconIv;
        TextView goodsNameTv;
        TextView goodsPriceTv;
        TextView monthSaleTv;
        ImageView cartTv;
        TextView moreTv;
    }
}
