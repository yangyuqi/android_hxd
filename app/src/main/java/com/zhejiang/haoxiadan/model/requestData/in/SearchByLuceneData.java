package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/13.
 */
public class SearchByLuceneData {
    private Object goodsPropertyList;//
    private List<LuceneListData> luceneList;//
    private int totalPage;//
    private int currentPage;//
    private int totalCount;//

    public Object getGoodsPropertyList() {
        return goodsPropertyList;
    }

    public void setGoodsPropertyList(Object goodsPropertyList) {
        this.goodsPropertyList = goodsPropertyList;
    }

    public List<LuceneListData> getLuceneList() {
        return luceneList;
    }

    public void setLuceneList(List<LuceneListData> luceneList) {
        this.luceneList = luceneList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
