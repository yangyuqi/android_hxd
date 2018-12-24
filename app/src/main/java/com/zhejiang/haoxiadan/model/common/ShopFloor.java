package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 店铺楼层
 * Created by KK on 2017/10/8.
 */

public class ShopFloor implements Serializable {
    private String id;
    //楼层类型
    private FLOOR_TYPE type = FLOOR_TYPE.FLOOR_COMMON;
    //名称
    private String name;
    //去向的类型
    private FLOOR_FORWARD_TYPE forwardType = FLOOR_FORWARD_TYPE.NULL;

    //以下是不同楼层的元素
    //图片带下标题的元素
    private ShopFloorItem floorItem;
    //商品列表
    private List<Goods> goodsList;


    public enum FLOOR_TYPE{
        FLOOR_COMMON(0),//通配的楼层
        FLOOR_IMG_DOWN_TITLE(1),//长方图带下标题
        FLOOR_IMG_RECT(2),//方形图片
        FLOOR_GOODSLIST(3);//商品列表带标题

        private int id;

        public int getId(){
            return id;
        }

        FLOOR_TYPE(int id){
            this.id = id;
        }

        public static FLOOR_TYPE getType(int id) {
            int len = FLOOR_TYPE.values().length;
            for(int i = 0; i < len; i++) {
                if(id == FLOOR_TYPE.values()[i].getId()) {
                    return FLOOR_TYPE.values()[i];
                }
            }
            return FLOOR_COMMON;
        }
    }

    //楼层去向的类型
    public enum FLOOR_FORWARD_TYPE{
        NULL,
        PRODUCT,    //商品
        CATEGORY,   //商品类型
        CHANNEL     //频道
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FLOOR_FORWARD_TYPE getForwardType() {
        return forwardType;
    }

    public void setForwardType(FLOOR_FORWARD_TYPE forwardType) {
        this.forwardType = forwardType;
    }

    public FLOOR_TYPE getType() {
        return type;
    }

    public void setType(FLOOR_TYPE type) {
        this.type = type;
    }

    public ShopFloorItem getFloorItem() {
        return floorItem;
    }

    public void setFloorItem(ShopFloorItem floorItem) {
        this.floorItem = floorItem;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }
}
