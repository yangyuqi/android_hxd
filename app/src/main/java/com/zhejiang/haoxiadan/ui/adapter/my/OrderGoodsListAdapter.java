package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoods;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.my.SelectServiceActivity;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.adapter.cart.CartGoodsStyleAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import java.util.List;

/**
 * 订单里的商品列表
 * Created by KK on 2017/8/28.
 */

public class OrderGoodsListAdapter extends AbsBaseAdapter<OrderGoods>{

    private int type ;

    public OrderGoodsListAdapter(Context context, List<OrderGoods> datas ,int type) {
        super(context, datas);
        this.type = type ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_order_goods_list, null);
            holder.btn_refund = (Button) convertView.findViewById(R.id.btn_refund);
            holder.goodsIconIv = (ImageView) convertView.findViewById(R.id.iv_goods_icon);
            holder.goodsTypeIv = (ImageView) convertView.findViewById(R.id.iv_goods_type);
            holder.goodsNameTv = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.goodsPriceTv = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.goodsStyleTv = (TextView) convertView.findViewById(R.id.tv_goods_style);
            holder.goodsCountTv = (TextView) convertView.findViewById(R.id.tv_goods_count);
            holder.text_refund_status = (TextView) convertView.findViewById(R.id.text_refund_status);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        ImageLoaderUtil.displayImage(mDatas.get(position).getIcon(),holder.goodsIconIv);
        holder.goodsNameTv.setText(mDatas.get(position).getTitle());

        if (type==1) {
            holder.goodsPriceTv.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(mDatas.get(position).getPrice()));
            holder.goodsCountTv.setText(mContext.getString(R.string.label_cheng)+" "+mDatas.get(position).getPer_count());

//            if (mDatas.get(position).getOrderStatus().equals("20")||mDatas.get(position).getOrderStatus().equals("30")){
//                holder.btn_refund.setVisibility(View.VISIBLE);
//            }

        }else if (type==2){
            double goodsPrice =  (Double.parseDouble(mDatas.get(position).getPrice())/Double.parseDouble(mDatas.get(position).getCount()));
            holder.goodsPriceTv.setText(mContext.getString(R.string.label_money) + goodsPrice);
            holder.goodsCountTv.setText(mContext.getString(R.string.label_cheng)+" "+mDatas.get(position).getCount());
        }
        holder.goodsStyleTv.setText(mDatas.get(position).getStyle());

        if (mDatas.get(position).getRefundStatus()!=null){
            holder.btn_refund.setVisibility(View.GONE);
            holder.text_refund_status.setVisibility(View.VISIBLE);
            holder.text_refund_status.setText(mDatas.get(position).getRefundStatus());
        }else {
            holder.text_refund_status.setVisibility(View.GONE);
            if (type==1) {
                if (!mDatas.get(position).getOrderStatus().equals("40")&&!mDatas.get(position).getOrderStatus().equals("10")) {
                    holder.btn_refund.setVisibility(View.VISIBLE);
                }
            }
        }

        holder.btn_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,SelectServiceActivity.class);
                intent.putExtra("goods",mDatas.get(position));
                mContext.startActivity(intent);
            }
        });

        switch (mDatas.get(position).getType()){
            case STOCK:
                holder.goodsTypeIv.setImageResource(R.mipmap.icon_goods);
                break;
            case FUTURES:
                holder.goodsTypeIv.setImageResource(R.mipmap.icon_order);
                break;
        }

        return convertView;
    }

    private class ViewHolder{
        ImageView goodsIconIv;
        ImageView goodsTypeIv;
        TextView goodsNameTv;
        TextView goodsPriceTv;
        TextView goodsStyleTv;
        TextView goodsCountTv;
        TextView text_refund_status ;
        Button btn_refund ;
    }
}
