package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoods2Adapter;
import com.zhejiang.haoxiadan.ui.adapter.chosen.AbsShopFloorView;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺正方形图片的楼层模板
 * Created by KK on 2017/10/8.
 */

public class ShopFloorGoodsListView extends AbsShopFloorView {

    public ShopFloorGoodsListView(Context context) {
        this(context, null);
    }
    public ShopFloorGoodsListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ShopFloorGoodsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private TextView titleTV;
    private NoScrollGridView gridView;
    private CommonGoods2Adapter adapter;
    private List<Goods> goodses;

    @Override
    protected void initView(){
        inflate(context, R.layout.view_shop_floor_goodslist, this);

        titleTV = (TextView)findViewById(R.id.tv_title);
        gridView = (NoScrollGridView)findViewById(R.id.gv_goods);

        goodses = new ArrayList<>();
        adapter = new CommonGoods2Adapter(context,goodses);
        gridView.setAdapter(adapter);
    }

    @Override
    protected void initEvent(){
    }

    @Override
    public void setData(ShopFloor floor) {
        super.setData(floor);
        initEvent();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void refreshView(){
        titleTV.setText(floor.getName());

        goodses.clear();
        goodses.addAll(floor.getGoodsList());
        adapter.notifyDataSetChanged();

    }

}
