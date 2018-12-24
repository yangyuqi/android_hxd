package com.zhejiang.haoxiadan.ui.adapter.chosen;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CompetitiveProductBeanData;
import com.zhejiang.haoxiadan.third.jiguang.chat.adapter.TextWatcherAdapter;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class ChoseBaseAdapter extends BaseAdapter {

    List<FlightLsItemBean> datas ;

    public ChoseBaseAdapter(List<FlightLsItemBean> dataList) {
        datas = dataList;
    }

    public void setData(List<FlightLsItemBean> dataList){
        datas = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size()+1;
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null ;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_alone_ls_item,null);
            holder.price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.style = (TextView) convertView.findViewById(R.id.tv_style);
            holder.count = (TextView) convertView.findViewById(R.id.tv_all_num);
            holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            holder.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_reduce);
            holder.edt_current = (EditText) convertView.findViewById(R.id.edt_num);

            holder.edt_current.addTextChangedListener(new TextWatcherAdapter() {
                @Override
                public void afterTextChanged(Editable s) {
                    super.afterTextChanged(s);
                }
            });

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }



        return null;
    }

    public class ViewHolder{
        private TextView style ,price ,count;
        private ImageView iv_add ,iv_reduce ;
        private EditText edt_current ;
    }
}
