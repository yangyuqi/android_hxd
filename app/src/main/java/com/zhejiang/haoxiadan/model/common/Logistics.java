package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息
 * Created by KK on 2017/8/23.
 */
public class Logistics implements Serializable{
    //商品图标
    private String goodsIcon;
    //承运公司
    private String shipCompany;
    //运单编号
    private String logisticsNo;
    //派件员
    private String shipName;
    //派件员电话
    private String shipMobile;
    //信息提供者
    private String infoProvider;
    //物流节点
    private List<LogisticsNode> logisticsNodes;

    public String getGoodsIcon() {
        return goodsIcon;
    }

    public void setGoodsIcon(String goodsIcon) {
        this.goodsIcon = goodsIcon;
    }

    public String getShipCompany() {
        return shipCompany;
    }

    public void setShipCompany(String shipCompany) {
        this.shipCompany = shipCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getShipName() {
        return shipName;
    }

    public void setShipName(String shipName) {
        this.shipName = shipName;
    }

    public String getShipMobile() {
        return shipMobile;
    }

    public void setShipMobile(String shipMobile) {
        this.shipMobile = shipMobile;
    }

    public String getInfoProvider() {
        return infoProvider;
    }

    public void setInfoProvider(String infoProvider) {
        this.infoProvider = infoProvider;
    }

    public List<LogisticsNode> getLogisticsNodes() {
        return logisticsNodes;
    }

    public void setLogisticsNodes(List<LogisticsNode> logisticsNodes) {
        this.logisticsNodes = logisticsNodes;
    }
}
