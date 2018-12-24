package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/1.
 */
public class StoreGoodsCartListData {
    private long storeId;//	商品id	是	String
    private String storeName;//	商家名称	 是	String
    private List<GoodsCartListData> goodsCartList;//	购物车中集合信息	是	String

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public List<GoodsCartListData> getGoodsCartList() {
        return goodsCartList;
    }

    public void setGoodsCartList(List<GoodsCartListData> goodsCartList) {
        this.goodsCartList = goodsCartList;
    }
}
