package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.choseModel.HotCategoryListData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CategoryListBean;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class CategoryBean {
    private int id ;
    private String gf_name ;
    private String iconPath ;
    private CategoryListBean wideGoodsList ;

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public CategoryListBean getWideGoodsList() {
        return wideGoodsList;
    }

    public void setWideGoodsList(CategoryListBean wideGoodsList) {
        this.wideGoodsList = wideGoodsList;
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


}
