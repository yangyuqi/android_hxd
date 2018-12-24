package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.ChannelFloorBean;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public interface MobileTitleInfoListener extends OnBaseRequestListener{
    void getChannel(List<ChannelFloorBean> ChannelFloor );
}
