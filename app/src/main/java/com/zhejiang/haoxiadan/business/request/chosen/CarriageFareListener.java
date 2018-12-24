package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * Created by qiuweiyu on 2017/11/25.
 */

public interface CarriageFareListener extends OnBaseRequestListener {
    void getShip(String shipPrice);
}
