package com.zhejiang.haoxiadan.business.request.category;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsClassData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryGoodsClassAllData;
import com.zhejiang.haoxiadan.model.requestData.in.ShowViewClassData;
import com.zhejiang.haoxiadan.model.requestData.in.ViewClassData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 前端展示分类
 * Created by KK on 2017/11/23.
 */
public class ShowViewClassParser extends AbsBaseParser {

    public ShowViewClassParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        ShowViewClassData showViewClassData = mDataParser.parseObject(data,ShowViewClassData.class);


        List<Category> categories = mapped(showViewClassData.getViewClass());
        ShowViewClassListener listener = (ShowViewClassListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onShowViewClassSuccess(categories);
        }
    }

    private List<Category> mapped(List<ViewClassData> viewClassDatas){
        List<Category> categoriesT = new ArrayList<>();
        for(ViewClassData viewClassData : viewClassDatas){
            Category categoryT = new Category();
            categoryT.setId(viewClassData.getClassId()+"");
            categoryT.setName(viewClassData.getName());
            List<Category> categoriesM = new ArrayList<>();
            for (Map<String,String> info : viewClassData.getInfo()){
                Category categoryM = new Category();
//                categoryM.setId(info.get(""));
                categoryM.setName(info.get("keyword"));
                categoryM.setIcon(info.get("img"));
                categoriesM.add(categoryM);
            }
            categoryT.setCates(categoriesM);
            categoriesT.add(categoryT);
        }

        return categoriesT;
    }

}
