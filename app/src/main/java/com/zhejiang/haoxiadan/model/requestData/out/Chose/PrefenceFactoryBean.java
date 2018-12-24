package com.zhejiang.haoxiadan.model.requestData.out.Chose;

/**
 * Created by qiuweiyu on 2017/9/15.
 */

public class PrefenceFactoryBean {
    private int salesVolume ;
    private String mainIndustry ;
    private String storeName ;
    private int id ;
    private String storeLogo ;

    public PrefenceFactoryBean(int salesVolume, String mainIndustry, String storeName, int id, String storeLogo) {
        this.salesVolume = salesVolume;
        this.mainIndustry = mainIndustry;
        this.storeName = storeName;
        this.id = id;
        this.storeLogo = storeLogo;
    }

    public String getStoreLogo() {
        return storeLogo;
    }

    public void setStoreLogo(String storeLogo) {
        this.storeLogo = storeLogo;
    }

    public int getSalesVolume() {
        return salesVolume;
    }

    public void setSalesVolume(int salesVolume) {
        this.salesVolume = salesVolume;
    }

    public String getMainIndustry() {
        return mainIndustry;
    }

    public void setMainIndustry(String mainIndustry) {
        this.mainIndustry = mainIndustry;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
