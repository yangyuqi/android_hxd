package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.Logistics;
import com.zhejiang.haoxiadan.model.common.LogisticsNode;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 物流信息
 * Created by KK on 2017/8/30.
 */

public class LogisticsListAdapter extends AbsBaseAdapter<LogisticsNode>{

    public LogisticsListAdapter(Context context, List<LogisticsNode> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_logistics_list, null);

            holder.divider = convertView.findViewById(R.id.view_divider);
            holder.lineTop = convertView.findViewById(R.id.view_line_top);
            holder.pointIv = (ImageView) convertView.findViewById(R.id.iv_point);
            holder.contentTv = (TextView) convertView.findViewById(R.id.tv_content);
            holder.timeTv = (TextView) convertView.findViewById(R.id.tv_time);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(position == 0){//最新的
            holder.divider.setVisibility(View.GONE);
            holder.lineTop.setVisibility(View.INVISIBLE);
            holder.pointIv.setImageResource(R.mipmap.icon_green);
            holder.contentTv.setTextColor(mContext.getResources().getColor(R.color.text_green));
        }else{
            holder.divider.setVisibility(View.VISIBLE);
            holder.lineTop.setVisibility(View.VISIBLE);
            holder.pointIv.setImageResource(R.mipmap.icon_gray);
            holder.contentTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
        }
        holder.contentTv.setText(mDatas.get(position).getContent());
        holder.timeTv.setText(mDatas.get(position).getTime());

        return convertView;
    }

    private class ViewHolder{
        View divider;
        View lineTop;
        ImageView pointIv;
        TextView contentTv;
        TextView timeTv;
    }
}
