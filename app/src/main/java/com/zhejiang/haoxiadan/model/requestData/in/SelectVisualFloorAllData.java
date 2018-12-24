package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/7.
 */
public class SelectVisualFloorAllData {
    private List<VisualFloorListData> VisualFloorList;

    public List<VisualFloorListData> getVisualFloorList() {
        return VisualFloorList;
    }

    public void setVisualFloorList(List<VisualFloorListData> visualFloorList) {
        VisualFloorList = visualFloorList;
    }
}
