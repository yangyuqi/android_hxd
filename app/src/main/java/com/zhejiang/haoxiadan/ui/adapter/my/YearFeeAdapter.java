package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.LogisticsNode;
import com.zhejiang.haoxiadan.model.common.YearFee;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 年费
 * Created by KK on 2017/8/30.
 */

public class YearFeeAdapter extends AbsBaseAdapter<YearFee>{

    public YearFeeAdapter(Context context, List<YearFee> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_yearfee_list, null);

            holder.contentTv = (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.contentTv.setText(mDatas.get(position).getYear()+"");

        return convertView;
    }

    private class ViewHolder{
        TextView contentTv;
    }
}
