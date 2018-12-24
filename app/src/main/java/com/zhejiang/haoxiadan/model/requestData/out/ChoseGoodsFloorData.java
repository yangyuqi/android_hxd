package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersListBean;
import com.zhejiang.haoxiadan.model.requestData.out.NewChose.NewFloorsItem;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class ChoseGoodsFloorData {
//    private List<ChoseGoodsFloorDataBean> floors ;
//
//    public List<ChoseGoodsFloorDataBean> getFloors() {
//        return floors;
//    }
//
//    public void setFloors(List<ChoseGoodsFloorDataBean> floors) {
//        this.floors = floors;
//    }

    private List<NewFloorsItem> floors ;
    private List<NewFloorsItem> ChannelFloor ;
    private List<BannersListBean> bannerFloor ;

    public List<BannersListBean> getBannerFloor() {
        return bannerFloor;
    }

    public void setBannerFloor(List<BannersListBean> bannerFloor) {
        this.bannerFloor = bannerFloor;
    }

    public List<NewFloorsItem> getChannelFloor() {
        return ChannelFloor;
    }

    public void setChannelFloor(List<NewFloorsItem> channelFloor) {
        ChannelFloor = channelFloor;
    }

    public List<NewFloorsItem> getFloors() {
        return floors;
    }

    public void setFloors(List<NewFloorsItem> floors) {
        this.floors = floors;
    }
}
