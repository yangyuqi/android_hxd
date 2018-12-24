package com.zhejiang.haoxiadan.ui.adapter.category;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.ui.adapter.AbsBaseAdapter;
import com.zhejiang.haoxiadan.ui.adapter.chosen.AbsShopFloorView;

import java.util.List;

/**
 * 顶层分类
 * Created by KK on 2017/2/22.
 */

public class CateTabAdapter extends RecyclerView.Adapter<CateTabAdapter.MyViewHolder> {

    private List<CateTabModel> list;
    private Context context;
    private ItemClickListener itemClickListener;

    private int itemWidth;

    public CateTabAdapter(Context context, List<CateTabModel> dataList) {
        this.context = context;
        list = dataList;
    }

    public interface ItemClickListener{
        void onItemClick(int position);
        void onArrowClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.listview_cate_t, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.title.setSelected(list.get(position).isChoose());
        holder.arrow.setSelected(list.get(position).isChoose());

        if(itemWidth>0){//手动设置宽度
            holder.title.setWidth(itemWidth);
            ViewGroup.LayoutParams lp = holder.arrow.getLayoutParams();
            lp.width = itemWidth;
        }

        switch (list.get(position).getType()){
            case 0:
                holder.title.setVisibility(View.VISIBLE);
                holder.arrow.setVisibility(View.GONE);
                break;
            case 1:
                holder.title.setVisibility(View.GONE);
                holder.arrow.setVisibility(View.VISIBLE);
                break;
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onItemClick(position);
                }
            }
        });
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.onArrowClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null?0:list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title;
        ImageView arrow;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView)itemView.findViewById(R.id.tv_title);
            arrow = (ImageView)itemView.findViewById(R.id.iv_arrow);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemWidth(int itemWidth){
        this.itemWidth = itemWidth;
        notifyDataSetChanged();
    }
}
