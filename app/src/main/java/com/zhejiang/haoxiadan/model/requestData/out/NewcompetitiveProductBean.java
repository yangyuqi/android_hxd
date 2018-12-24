package com.zhejiang.haoxiadan.model.requestData.out;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class NewcompetitiveProductBean {
    private int id ;
    private String gf_name ;
    private String en_name ;
    private String iconPath ;
    private WideGoodsList wideGoodsList ;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGf_name() {
        return gf_name;
    }

    public void setGf_name(String gf_name) {
        this.gf_name = gf_name;
    }

    public String getEn_name() {
        return en_name;
    }

    public void setEn_name(String en_name) {
        this.en_name = en_name;
    }

    public WideGoodsList getWideGoodsList() {
        return wideGoodsList;
    }

    public void setWideGoodsList(WideGoodsList wideGoodsList) {
        this.wideGoodsList = wideGoodsList;
    }
}
