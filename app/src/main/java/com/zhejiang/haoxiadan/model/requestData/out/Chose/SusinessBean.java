package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import com.zhejiang.haoxiadan.model.requestData.out.HotCategoryBean;

/**
 * Created by qiuweiyu on 2017/9/11.
 */

public class SusinessBean {
    private int id ;
    private String gf_name ;
    private String en_name ;
    private HotCategoryBean wideGoodsList ;

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

    public HotCategoryBean getWideGoodsList() {
        return wideGoodsList;
    }

    public void setWideGoodsList(HotCategoryBean wideGoodsList) {
        this.wideGoodsList = wideGoodsList;
    }
}
