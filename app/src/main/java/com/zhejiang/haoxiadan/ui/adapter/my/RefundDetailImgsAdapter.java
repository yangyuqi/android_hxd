package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 退款详情图片的适配器
 * Created by KK on 2017/8/29.
 */

public class RefundDetailImgsAdapter extends AbsBaseAdapter<String>{

    public RefundDetailImgsAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null || convertView.getTag() == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_select_imgs, null);

            holder.imgIv = (ImageView) convertView.findViewById(R.id.iv_img);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        ImageLoaderUtil.displayImage(mDatas.get(position),holder.imgIv);

        return convertView;
    }

    private class ViewHolder{
        ImageView imgIv;
    }
}
