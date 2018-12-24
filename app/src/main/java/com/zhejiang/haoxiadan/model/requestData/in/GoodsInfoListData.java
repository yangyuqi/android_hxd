package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/6.
 */
public class GoodsInfoListData {
    private String goods_name;//
    private List<GoodsGspData2> goodsGsp;//
    private long goods_id;//
    private long goods_type;//
    private String goods_mainphoto_path;//
    private int goodsTotalcount;//
    private String goodsNumType;

    public String getGoodsNumType() {
        return goodsNumType;
    }

    public void setGoodsNumType(String goodsNumType) {
        this.goodsNumType = goodsNumType;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public List<GoodsGspData2> getGoodsGsp() {
        return goodsGsp;
    }

    public void setGoodsGsp(List<GoodsGspData2> goodsGsp) {
        this.goodsGsp = goodsGsp;
    }

    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }

    public long getGoods_type() {
        return goods_type;
    }

    public void setGoods_type(long goods_type) {
        this.goods_type = goods_type;
    }

    public String getGoods_mainphoto_path() {
        return goods_mainphoto_path;
    }

    public void setGoods_mainphoto_path(String goods_mainphoto_path) {
        this.goods_mainphoto_path = goods_mainphoto_path;
    }

    public int getGoodsTotalcount() {
        return goodsTotalcount;
    }

    public void setGoodsTotalcount(int goodsTotalcount) {
        this.goodsTotalcount = goodsTotalcount;
    }
}
