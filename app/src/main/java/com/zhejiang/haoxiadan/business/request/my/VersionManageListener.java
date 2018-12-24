package com.zhejiang.haoxiadan.business.request.my;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.VersionManageData;

/**
 * Created by qiuweiyu on 2017/9/18.
 */

public interface VersionManageListener extends OnBaseRequestListener {
    void onSuccess(VersionManageData versionManageData);
}
