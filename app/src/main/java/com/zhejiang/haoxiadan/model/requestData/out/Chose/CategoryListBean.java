package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import com.zhejiang.haoxiadan.model.choseModel.HotCategoryListData;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/11.
 */

public class CategoryListBean {
    private List<HotCategoryListData> CategoryList ;

    public List<HotCategoryListData> getCategoryList() {
        return CategoryList;
    }

    public void setCategoryList(List<HotCategoryListData> categoryList) {
        CategoryList = categoryList;
    }
}
