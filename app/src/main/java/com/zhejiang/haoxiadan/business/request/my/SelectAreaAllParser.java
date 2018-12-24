package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.google.gson.JsonObject;
import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.model.common.ProductStyle;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectAreaAllFatherData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListStyleBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取所有区域接口
 * Created by KK on 2017/9/4.
 */
public class SelectAreaAllParser extends AbsBaseParser {

    public SelectAreaAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SelectAreaAllFatherData selectAreaAllFatherData = mDataParser.parseObject(data,SelectAreaAllFatherData.class);

        List<Area> areas = mapped(selectAreaAllFatherData.getAreaList());
        SelectAreaAllListener listener = (SelectAreaAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectAreaAllSuccess(areas);
        }
    }

    private List<Area> mapped(List<SelectAreaAllData> selectAreaAllDatas){
        List<Area> areas = new ArrayList<>();
        for(SelectAreaAllData shengData : selectAreaAllDatas){
            Area sheng = new Area();
            sheng.setId(shengData.getId());
            sheng.setName(shengData.getAreaName());
            List<Area> shiList = new ArrayList<>();
            for(SelectAreaAllData shiData : shengData.getChilds()){
                Area shi = new Area();
                shi.setId(shiData.getId());
                shi.setName(shiData.getAreaName());
                List<Area> quList = new ArrayList<>();
                for(SelectAreaAllData quData : shiData.getChilds()){
                    Area qu = new Area();
                    qu.setId(quData.getId());
                    qu.setName(quData.getAreaName());
                    quList.add(qu);
                }
                shi.setAreas(quList);
                shiList.add(shi);
            }
            sheng.setAreas(shiList);
            areas.add(sheng);
        }


        return areas;
    }

}
