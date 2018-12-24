package com.zhejiang.haoxiadan.business.request.category;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ApplyRoleFeeListener;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.common.YearFee;
import com.zhejiang.haoxiadan.model.requestData.in.ApplyRoleFeeData;
import com.zhejiang.haoxiadan.model.requestData.in.FeeData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsClassData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryGoodsClassAllData;

import java.util.ArrayList;
import java.util.List;

/**
 * 普通商品分类菜单
 * Created by KK on 2017/9/5.
 */
public class QueryGoodsClassAllParser extends AbsBaseParser {

    public QueryGoodsClassAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        QueryGoodsClassAllData queryGoodsClassAllData = mDataParser.parseObject(data,QueryGoodsClassAllData.class);


        List<Category> categories = mapped(queryGoodsClassAllData.getGoodsClass());
        QueryGoodsClassAllListener listener = (QueryGoodsClassAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryGoodsClassAllSuccess(categories);
        }
    }

    private List<Category> mapped(List<GoodsClassData> goodsClass){
        List<Category> categoriesT = new ArrayList<>();
        for(GoodsClassData classDataT : goodsClass){
            Category categoryT = new Category();
            categoryT.setId(classDataT.getId()+"");
            categoryT.setName(classDataT.getClassName());
            List<Category> categoriesM = new ArrayList<>();
            for (GoodsClassData classDataM : classDataT.getChilds()){
                Category categoryM = new Category();
                categoryM.setId(classDataM.getId()+"");
                categoryM.setName(classDataM.getClassName());
                List<Category> categories = new ArrayList<>();
                for (GoodsClassData classData : classDataM.getChilds()){
                    Category category = new Category();
                    category.setId(classData.getId()+"");
                    category.setName(classData.getClassName());
                    if(classData.getIcon_acc() != null && classData.getIcon_acc().getPath() != null){
                        category.setIcon(classData.getIcon_acc().getPath());
                    }
                    categories.add(category);
                }
                categoryM.setCates(categories);
                categoriesM.add(categoryM);
            }
            categoryT.setCates(categoriesM);
            categoriesT.add(categoryT);
        }

        return categoriesT;
    }

}
