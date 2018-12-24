package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.adapter.chosen.AbsShopFloorView;

/**
 * 店铺图片带下标题的楼层模板
 * Created by KK on 2017/10/8.
 */

public class ShopFloorImgDownTitleView extends AbsShopFloorView {

    public ShopFloorImgDownTitleView(Context context) {
        this(context, null);
    }
    public ShopFloorImgDownTitleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ShopFloorImgDownTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private TextView titleTV;
    private ImageView imageView;

    @Override
    protected void initView(){
        inflate(context, R.layout.view_shop_floor_img_down_title, this);

        titleTV = (TextView)findViewById(R.id.tv_title);
        imageView = (ImageView)findViewById(R.id.iv_img);
    }

    @Override
    protected void initEvent(){
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (floor.getFloorItem().getForwardType()){
                    case PRODUCT:
                        Intent intentProduct = new Intent(context, GoodsDetailsActivity.class);
                        intentProduct.putExtra("goodsId",floor.getFloorItem().getContentID());
                        context.startActivity(intentProduct);
                        break;
//                    case CATEGORY:
//                        Intent intentCategory = new Intent(context, CategoryDetailActivity.class);
//                        intentCategory.putExtra("cateId",floorItem.getContentID());
//                        intentCategory.putExtra("title",floorItem.getTitle());
//                        context.startActivity(intentCategory);
//                        break;
//                    case CHANNEL:
//                        Channel channel = new Channel();
//                        channel.setId(floorItem.getContentID());
//                        channel.setName(floorItem.getTitle());
//                        Intent intentChannel = new Intent(context, ChannelActivity.class);
//                        intentChannel.putExtra("channel",channel);
//                        context.startActivity(intentChannel);
//                        break;
                }
            }
        });
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
        if(floor.getFloorItem().getTitle()!=null){
            titleTV.setText(floor.getFloorItem().getTitle());
            titleTV.setVisibility(View.VISIBLE);
        }else{
            titleTV.setVisibility(View.GONE);
        }
        ImageLoaderUtil.displayImage(floor.getFloorItem().getImg(),imageView);
    }

}
