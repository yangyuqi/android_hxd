package com.zhejiang.haoxiadan.model.requestData.out;

import com.zhejiang.haoxiadan.model.requestData.out.Chose.BusinessWideGoodsList;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CompetitiveProductBeanData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.SusinessBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class ChoseGoodsFloorDataBean {

     private SusinessBean susiness ;
     private CategoryBean  category ;
    private CategoryBean hotCategory ;
     private List<CompetitiveProductBeanData> competitiveProduct ;
     private NewcompetitiveProductBean applike ;
    private NewcompetitiveProductBean newcompetitiveProduct;

    public NewcompetitiveProductBean getNewcompetitiveProduct() {
        return newcompetitiveProduct;
    }

    public void setNewcompetitiveProduct(NewcompetitiveProductBean newcompetitiveProduct) {
        this.newcompetitiveProduct = newcompetitiveProduct;
    }

    public List<CompetitiveProductBeanData> getCompetitiveProduct() {
        return competitiveProduct;
    }

    public void setCompetitiveProduct(List<CompetitiveProductBeanData> competitiveProduct) {
        this.competitiveProduct = competitiveProduct;
    }

    public CategoryBean getHotCategory() {
        return hotCategory;
    }

    public void setHotCategory(CategoryBean hotCategory) {
        this.hotCategory = hotCategory;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public SusinessBean getSusiness() {
        return susiness;
    }

    public void setSusiness(SusinessBean susiness) {
        this.susiness = susiness;
    }

//    public NewcompetitiveProductBean getCompetitiveProduct() {
//        return competitiveProduct;
//    }
//
//    public void setCompetitiveProduct(NewcompetitiveProductBean competitiveProduct) {
//        this.competitiveProduct = competitiveProduct;
//    }

    public NewcompetitiveProductBean getApplike() {
        return applike;
    }

    public void setApplike(NewcompetitiveProductBean applike) {
        this.applike = applike;
    }

//    public NewcompetitiveProductBean getNewcompetitiveProduct() {
//        return newcompetitiveProduct;
//    }
//
//    public void setNewcompetitiveProduct(NewcompetitiveProductBean newcompetitiveProduct) {
//        this.newcompetitiveProduct = newcompetitiveProduct;
//    }
}
