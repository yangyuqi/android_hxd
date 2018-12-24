package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Area;

import java.util.List;

/**
 * 采购商/供应商申请
 * Created by KK on 2017/9/4.
 */
public interface ApplyRoleListener extends OnBaseRequestListener {

    void onApplyRoleSuccess(String applyId);

}
