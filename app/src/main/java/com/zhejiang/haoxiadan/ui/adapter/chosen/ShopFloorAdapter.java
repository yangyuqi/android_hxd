package com.zhejiang.haoxiadan.ui.adapter.chosen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.ui.view.ShopFloorGoodsListView;
import com.zhejiang.haoxiadan.ui.view.ShopFloorImgDownTitleView;
import com.zhejiang.haoxiadan.ui.view.ShopFloorRectImgView;

import java.util.List;

/**
 * 店铺首页构建楼层的适配器
 */
public class ShopFloorAdapter extends RecyclerView.Adapter<ShopFloorAdapter.MyViewHolder> {
    private List<ShopFloor> list;
    private Context context;

    public ShopFloorAdapter(Context context, List<ShopFloor> dataList) {
        this.context = context;
        list = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = null;
        switch (ShopFloor.FLOOR_TYPE.getType(viewType)){
            case FLOOR_IMG_DOWN_TITLE:
                holder = new MyViewHolder(new ShopFloorImgDownTitleView(context));
                break;
            case FLOOR_IMG_RECT:
                holder = new MyViewHolder(new ShopFloorRectImgView(context));
                break;
            case FLOOR_GOODSLIST:
                holder = new MyViewHolder(new ShopFloorGoodsListView(context));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.view.setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getType().getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AbsShopFloorView view;


        public MyViewHolder(AbsShopFloorView itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
