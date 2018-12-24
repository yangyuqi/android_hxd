package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;
import com.zhejiang.haoxiadan.model.common.Industry;

import java.util.List;

/**
 * 获取所有区域接口
 * Created by KK on 2017/9/4.
 */
public interface SelectAreaAllListener extends OnBaseRequestListener {

    void onSelectAreaAllSuccess(List<Area> areas);

}
