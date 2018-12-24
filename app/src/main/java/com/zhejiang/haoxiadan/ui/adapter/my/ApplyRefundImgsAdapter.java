package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.List;
import java.util.Map;

/**
 * 申请退款选择图片的适配器
 * Created by KK on 2017/8/29.
 */

public class ApplyRefundImgsAdapter extends AbsBaseAdapter<String>{

    private SelectImgCallback selectImgCallback;

    public interface SelectImgCallback{
        void onSelect();
        void onDelete(int position);
    }

    public ApplyRefundImgsAdapter(Context context, List<String> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(position == mDatas.size()-1){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_select_img, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.show(mContext,"添加图片");
                }
            });
        }else{

            if (convertView == null || convertView.getTag() == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_select_imgs, null);

                holder.imgIv = (ImageView) convertView.findViewById(R.id.iv_img);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            ImageLoaderUtil.displayFromSDCard(mDatas.get(position),holder.imgIv);

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == mDatas.size()-1){
                    if (selectImgCallback!=null){
                        selectImgCallback.onSelect();
                    }
                }
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position != mDatas.size()-1){
                    if (selectImgCallback!=null){
                        selectImgCallback.onDelete(position);
                    }
                }
                return true;
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView imgIv;
    }

    public void setSelectImgCallback(SelectImgCallback selectImgCallback) {
        this.selectImgCallback = selectImgCallback;
    }
}
