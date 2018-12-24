package com.zhejiang.haoxiadan.ui.adapter.category;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.ui.NoDoubleItemClickListener;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchHistortActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchResultGoodsActivity;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;

import java.util.List;

/**
 * 中分类
 * Created by KK on 2017/2/22.
 */

public class CateMAdapter extends AbsBaseAdapter<Category> {

    public CateMAdapter(Context context, List<Category> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_cate_m, null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.cateGridView = (NoScrollGridView) convertView.findViewById(R.id.gv_cate);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.title.setText(mDatas.get(position).getName());
//        if(mDatas.get(position).getCates().size()<=6){
            holder.cateAdapter = new CateAdapter(mContext,mDatas.get(position).getCates());
//        }else{
//            holder.cateAdapter = new CateAdapter(mContext,mDatas.get(position).getCates().subList(0,6));
//        }
        holder.cateGridView.setAdapter(holder.cateAdapter);
        holder.cateGridView.setOnItemClickListener(new NoDoubleItemClickListener() {
            @Override
            public void onNoDoubleItemClick(AdapterView<?> parent, View view, int inposition, long id) {

                //TODO 去商品搜索结果页
                Intent[] intents = new Intent[2];
                Intent intent1 = new Intent(mContext, SearchHistortActivity.class);
                intents[0] = intent1;
                Intent intent2 = new Intent(mContext, SearchResultGoodsActivity.class);
                intent2.putExtra("gcilThird",mDatas.get(position).getCates().get(inposition).getName());
                intent2.putExtra("keyWord",mDatas.get(position).getCates().get(inposition).getName());
                intent2.putExtra("type","catefortType");
                intent2.putExtra("searchType","goods");
                intent2.putExtra("classId",mDatas.get(position).getId());
                intents[1] = intent2 ;
//                mContext.startActivities(intents);
                mContext.startActivity(intent2);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        TextView title;
        NoScrollGridView cateGridView;
        CateAdapter cateAdapter;
    }
}
