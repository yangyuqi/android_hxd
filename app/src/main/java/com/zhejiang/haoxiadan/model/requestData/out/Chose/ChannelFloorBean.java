package com.zhejiang.haoxiadan.model.requestData.out.Chose;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class ChannelFloorBean {
    private String addTime ;
    private String goodsList ;
    private int id ;
    private String iconPath ;
    private String chName ;
    private TypeTop type ;

    public class TypeTop{
        public String type ;
        public String value ;
    }

    public TypeTop getType() {
        return type;
    }

    public void setType(TypeTop type) {
        this.type = type;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(String goodsList) {
        this.goodsList = goodsList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getChName() {
        return chName;
    }

    public void setChName(String chName) {
        this.chName = chName;
    }
}
