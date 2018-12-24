package com.zhejiang.haoxiadan.ui.adapter.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 选择行业
 * Created by KK on 2017/11/23.
 */

public class IndustryAdapter extends AbsBaseAdapter<Industry> {

    public IndustryAdapter(Context context, List<Industry> datas) {
        super(context, datas);
    }

    private IndustryClickListener listener;

    private boolean isEdit;

    public interface IndustryClickListener{
        void onIndustryClick(int position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_industry, null);
            holder.tag = (ImageView) convertView.findViewById(R.id.iv_tag);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(mDatas.get(position).getTitle());

        if(isEdit){
            holder.tag.setVisibility(View.VISIBLE);
        }else{
            holder.tag.setVisibility(View.INVISIBLE);
        }
        if(mDatas.get(position).isChoose()){
            if(!mDatas.get(position).isDisable()){
                holder.tag.setImageResource(R.mipmap.btn_industry_delete);
            }else{
                holder.tag.setImageResource(R.mipmap.btn_right_grey);
            }
        }else{
            holder.tag.setImageResource(R.mipmap.btn_industry_add);
        }

        holder.tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDatas.get(position).isDisable() && listener!=null){
                    listener.onIndustryClick(position);
                }
            }
        });
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDatas.get(position).isDisable() && listener!=null){
                    listener.onIndustryClick(position);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView tag;
        TextView title;
    }

    public void setListener(IndustryClickListener listener) {
        this.listener = listener;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
