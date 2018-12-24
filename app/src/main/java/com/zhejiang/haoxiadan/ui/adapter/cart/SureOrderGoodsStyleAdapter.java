package com.zhejiang.haoxiadan.ui.adapter.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.CartGoodsStyle;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.List;

/**
 * 确认订单商品列表的商品规格
 * Created by KK on 2017/8/25.
 */

public class SureOrderGoodsStyleAdapter extends AbsBaseAdapter<CartGoodsStyle> {

    public SureOrderGoodsStyleAdapter(Context context, List<CartGoodsStyle> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_sure_order_goods_style_list, null);

            holder.mainStyleNameTv = (TextView) convertView.findViewById(R.id.tv_main_style_name);
            holder.mainStyleTv = (TextView) convertView.findViewById(R.id.tv_main_style);
            holder.secondStyleNameTv = (TextView) convertView.findViewById(R.id.tv_second_style_name);
            holder.secondStyleTv = (TextView) convertView.findViewById(R.id.tv_second_style);
            holder.numTv = (TextView) convertView.findViewById(R.id.tv_num);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
//        holder.mainStyleNameTv.setText(mDatas.get(position).getMainStyleName()+":");
        holder.mainStyleTv.setText(mDatas.get(position).getMainStyle());
//        holder.secondStyleNameTv.setText(mDatas.get(position).getSecondStyleName()+":");
        holder.secondStyleTv.setText(mDatas.get(position).getSecondStyle());
        holder.numTv.setText(mDatas.get(position).getBuyNum()+mContext.getString(R.string.label_jian));


        return convertView;
    }

    private class ViewHolder{
        TextView mainStyleNameTv;
        TextView mainStyleTv;
        TextView secondStyleNameTv;
        TextView secondStyleTv;
        TextView numTv;
    }
}
