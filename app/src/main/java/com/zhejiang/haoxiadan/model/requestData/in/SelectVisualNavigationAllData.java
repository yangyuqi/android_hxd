package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/7.
 */
public class SelectVisualNavigationAllData {

    private List<VisualNavigationListData> VisualNavigationList;

    public List<VisualNavigationListData> getVisualNavigationList() {
        return VisualNavigationList;
    }

    public void setVisualNavigationList(List<VisualNavigationListData> visualNavigationList) {
        VisualNavigationList = visualNavigationList;
    }
}
