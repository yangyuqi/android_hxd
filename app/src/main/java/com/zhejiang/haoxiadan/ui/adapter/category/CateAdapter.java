package com.zhejiang.haoxiadan.ui.adapter.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 小分类
 * Created by KK on 2017/2/22.
 */

public class CateAdapter extends AbsBaseAdapter<Category> {

    public CateAdapter(Context context, List<Category> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_cate, null);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        if(holder.icon.getTag() == null || !holder.icon.getTag().equals(mDatas.get(position).getIcon())){
            ImageLoaderUtil.displayImageAndDefaultImg(mDatas.get(position).getIcon(),holder.icon, R.mipmap.ic_launcher);
            holder.icon.setTag(mDatas.get(position).getIcon());
        }
//        holder.title.setText(StringUtils.subStrEndByLength(mDatas.get(position).getName(),4));
        holder.title.setText(mDatas.get(position).getName());

        return convertView;
    }

    private class ViewHolder{
        ImageView icon;
        TextView title;
    }
}
