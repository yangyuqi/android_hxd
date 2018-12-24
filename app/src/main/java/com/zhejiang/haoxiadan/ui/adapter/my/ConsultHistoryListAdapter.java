package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.ProductStyle;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.CircleImageView;

import java.util.List;

/**
 * 协商历史
 * Created by KK on 2017/8/29.
 */

public class ConsultHistoryListAdapter extends AbsBaseAdapter<ConsultHistory>{

    public ConsultHistoryListAdapter(Context context, List<ConsultHistory> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_consult_history_list, null);

            holder.iconIv = (CircleImageView) convertView.findViewById(R.id.iv_icon);
            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time);
            holder.titleTv = (TextView) convertView.findViewById(R.id.tv_title);
            holder.extraTv = (TextView) convertView.findViewById(R.id.tv_extra);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ImageLoaderUtil.displayImage(mDatas.get(position).getUserIcon(),holder.iconIv);
        holder.nameTv.setText(mDatas.get(position).getUserName());
        holder.timeTv.setText(mDatas.get(position).getTime());
        holder.titleTv.setText(mDatas.get(position).getContent());
        if(mDatas.get(position).getExtra() != null){
            holder.extraTv.setText(mDatas.get(position).getExtra());
            holder.extraTv.setVisibility(View.VISIBLE);
        }else{
            holder.extraTv.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder{
        CircleImageView iconIv;
        TextView nameTv;
        TextView timeTv;
        TextView titleTv;
        TextView extraTv;
    }
}
