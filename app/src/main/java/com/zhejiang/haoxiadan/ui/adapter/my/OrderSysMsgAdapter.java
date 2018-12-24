package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.common.OrderMessage;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.my.OrderDetailActivity;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 系統消息
 * Created by KK wqd 2017/7/7.
 */

public class OrderSysMsgAdapter extends AbsBaseAdapter<OrderMessage> {

    public OrderSysMsgAdapter(Context context, List<OrderMessage> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_order_sysmsg, null);
            holder.img_sysmsg = (ImageView) convertView.findViewById(R.id.img_sysmsg);
            holder.txt_sysmsg_time = (TextView) convertView.findViewById(R.id.txt_sysmsg_time);
            holder.txt_sysmsg_orderstatus = (TextView) convertView.findViewById(R.id.txt_sysmsg_orderstatus);
            holder.txt_sysmsg_name = (TextView) convertView.findViewById(R.id.txt_sysmsg_name);
            holder.txt_sysmsg_orderid = (TextView) convertView.findViewById(R.id.txt_sysmsg_orderid);
            holder.rl_go_order = (RelativeLayout) convertView.findViewById(R.id.rl_go_order);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(holder.img_sysmsg.getTag() == null || !holder.img_sysmsg.getTag().equals(mDatas.get(position).getGoodsMainPhoto())){
            ImageLoaderUtil.displayImageAndDefaultImg(mDatas.get(position).getGoodsMainPhoto(),holder.img_sysmsg, R.mipmap.ic_launcher);
            holder.img_sysmsg.setTag(mDatas.get(position).getGoodsMainPhoto());
        }
        holder.txt_sysmsg_time.setText(mDatas.get(position).getTime());
        holder.txt_sysmsg_name.setText(mDatas.get(position).getGoodsName());
        holder.txt_sysmsg_orderid.setText(mDatas.get(position).getOrderFormId());
        holder.txt_sysmsg_orderstatus.setText(mDatas.get(position).getStatusMessage());

        holder.rl_go_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, OrderDetailActivity.class);
                intent.putExtra("orderId",mDatas.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    private class ViewHolder{
        ImageView img_sysmsg;
        TextView txt_sysmsg_time;
        TextView txt_sysmsg_orderstatus;
        TextView txt_sysmsg_name;
        TextView txt_sysmsg_orderid;
        RelativeLayout rl_go_order;
    }
}
