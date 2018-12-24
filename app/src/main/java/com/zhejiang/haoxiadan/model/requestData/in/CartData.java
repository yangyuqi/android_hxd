package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/8/31.
 */
public class CartData {
    private List<StoreGoodsCartListData> storeGoodsCartList;//	购物车列表相关信息
    private String nowDate;//	系统当前日期
    private int currentPage;//	当前页
    private int totalPageCount;//	总页数
    private int fCount ;
    private int wCount ;

    public List<StoreGoodsCartListData> getStoreGoodsCartList() {
        return storeGoodsCartList;
    }

    public void setStoreGoodsCartList(List<StoreGoodsCartListData> storeGoodsCartList) {
        this.storeGoodsCartList = storeGoodsCartList;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getfCount() {
        return fCount;
    }

    public void setfCount(int fCount) {
        this.fCount = fCount;
    }

    public int getwCount() {
        return wCount;
    }

    public void setwCount(int wCount) {
        this.wCount = wCount;
    }
}
