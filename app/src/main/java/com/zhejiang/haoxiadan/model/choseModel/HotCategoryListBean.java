package com.zhejiang.haoxiadan.model.choseModel;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class HotCategoryListBean {
    private String img ;
    private String CategoryName ;
    private String seq ;
    private List<GoodsIdlistData> goodsIdlist ;

    public List<GoodsIdlistData> getGoodsIdlist() {
        return goodsIdlist;
    }

    public void setGoodsIdlist(List<GoodsIdlistData> goodsIdlist) {
        this.goodsIdlist = goodsIdlist;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }
}
