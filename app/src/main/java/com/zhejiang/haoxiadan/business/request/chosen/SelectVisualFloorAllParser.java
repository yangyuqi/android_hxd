package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ForwardItem;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.model.common.ShopFloorItem;
import com.zhejiang.haoxiadan.model.common.ShopTab;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData2;
import com.zhejiang.haoxiadan.model.requestData.in.SelectVisualFloorAllData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectVisualNavigationAllData;
import com.zhejiang.haoxiadan.model.requestData.in.VisualFloorListData;
import com.zhejiang.haoxiadan.model.requestData.in.VisualNavigationListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺首页楼层
 * Created by KK on 2017/9/7.
 */
public class SelectVisualFloorAllParser extends AbsBaseParser {
    public SelectVisualFloorAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SelectVisualFloorAllData selectVisualFloorAllData = mDataParser.parseObject(data,SelectVisualFloorAllData.class);

        List<ShopFloor> shopFloors = new ArrayList<>();
        for(VisualFloorListData visualFloorListData : selectVisualFloorAllData.getVisualFloorList()){
            ShopFloor shopFloor;
            switch (visualFloorListData.getFloorType()){
                case "adv1f":
                    shopFloor = new ShopFloor();
                    shopFloor.setType(ShopFloor.FLOOR_TYPE.FLOOR_IMG_DOWN_TITLE);
                    shopFloor.setFloorItem(mappedFloor(visualFloorListData));
                    shopFloors.add(shopFloor);
                    break;
                case "adv2f":
                    shopFloor = new ShopFloor();
                    shopFloor.setType(ShopFloor.FLOOR_TYPE.FLOOR_IMG_DOWN_TITLE);
                    shopFloor.setFloorItem(mappedFloor(visualFloorListData));
                    shopFloors.add(shopFloor);
                    break;
                case "adv3f":
                    shopFloor = new ShopFloor();
                    shopFloor.setType(ShopFloor.FLOOR_TYPE.FLOOR_IMG_RECT);
                    shopFloor.setFloorItem(mappedFloor(visualFloorListData));
                    shopFloors.add(shopFloor);
                    break;
                case "adv4f":
                    shopFloor = new ShopFloor();
                    shopFloor.setType(ShopFloor.FLOOR_TYPE.FLOOR_IMG_DOWN_TITLE);
                    shopFloor.setFloorItem(mappedFloor(visualFloorListData));
                    shopFloors.add(shopFloor);
                    break;
                case "adv5f":
                    shopFloor = new ShopFloor();
                    shopFloor.setType(ShopFloor.FLOOR_TYPE.FLOOR_GOODSLIST);
                    shopFloor.setName(visualFloorListData.getFloorName());
                    shopFloor.setGoodsList(mappedGoods(visualFloorListData));

                    shopFloors.add(shopFloor);
                    break;
            }
        }



        SelectVisualFloorAllListener listener = (SelectVisualFloorAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectVisualFloorAllSuccess(shopFloors);
        }
    }

    private List<Goods> mappedGoods(VisualFloorListData visualFloorListData){
        List<Goods> goodses = new ArrayList<>();
        for(GoodsInfoListData2 goodsInfoListData2:visualFloorListData.getGoodsInfoList()){
            Goods goods = new Goods();
            goods.setId(goodsInfoListData2.getGoodsId()+"");
            goods.setIcon(goodsInfoListData2.getGoodsMainPhoto());
            goods.setTitle(goodsInfoListData2.getGoods_name());
            goods.setPrice(goodsInfoListData2.getStore_price());
            goods.setMonthSale(goodsInfoListData2.getGoodsSalenum());
            goods.setGoodsNumType(goodsInfoListData2.getGoodsNumType());
            goodses.add(goods);
        }
        return goodses;
    }

    private ShopFloorItem mappedFloor(VisualFloorListData visualFloorListData){
        ShopFloorItem forwardItem = new ShopFloorItem();
        forwardItem.setForwardType(ShopFloorItem.FLOOR_ITEM_FORWARD_TYPE.PRODUCT);
        forwardItem.setImg(visualFloorListData.getFloorPhoto());
        forwardItem.setContentID(visualFloorListData.getGoodsInfoList().get(0).getGoodsId()+"");
        switch (visualFloorListData.getDisplayMode()){
            case "1":
                break;
            case "2":
                forwardItem.setTitle(visualFloorListData.getFloorName());
                break;
        }

        return forwardItem;
    }
}
