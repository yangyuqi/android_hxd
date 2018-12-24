package com.zhejiang.haoxiadan.ui.adapter.my;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;

import java.util.List;

/**
 * 银行卡选择
 * Created by KK on 2017/9/9.
 */

public class BankcardListAdapter extends AbsBaseAdapter<BankCard>{

    private BankCard curBankCard;

    public BankcardListAdapter(Context context, List<BankCard> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_bankcard_list, null);

            holder.nameTv = (TextView) convertView.findViewById(R.id.tv_name);
            holder.rightIv = (ImageView) convertView.findViewById(R.id.iv_right);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.nameTv.setText(mDatas.get(position).getBankName()+mDatas.get(position).getCardNo());
        if(curBankCard != null && curBankCard.getBankNo().equals(mDatas.get(position).getBankNo())){
            holder.rightIv.setVisibility(View.VISIBLE);
        }else{
            holder.rightIv.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder{
        TextView nameTv;
        ImageView rightIv;
    }

    public void setCurBankCard(BankCard bankCard){
        curBankCard = bankCard;
        notifyDataSetChanged();
    }
}
