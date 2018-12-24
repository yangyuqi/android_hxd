package com.zhejiang.haoxiadan.model.requestData.out.Chose;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/12.
 */

public class DiscussContentData {
    private int allCount ;
    private String totalPageCount ;
    private String currentPage ;
    private int goodPercent ;
    private int middlePercent ;
    private int feedBackPercent ;
    private List<DiscussContentDataBean> evaluateList ;

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    public String getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(String totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public int getGoodPercent() {
        return goodPercent;
    }

    public void setGoodPercent(int goodPercent) {
        this.goodPercent = goodPercent;
    }

    public int getMiddlePercent() {
        return middlePercent;
    }

    public void setMiddlePercent(int middlePercent) {
        this.middlePercent = middlePercent;
    }

    public int getFeedBackPercent() {
        return feedBackPercent;
    }

    public void setFeedBackPercent(int feedBackPercent) {
        this.feedBackPercent = feedBackPercent;
    }

    public List<DiscussContentDataBean> getEvaluateList() {
        return evaluateList;
    }

    public void setEvaluateList(List<DiscussContentDataBean> evaluateList) {
        this.evaluateList = evaluateList;
    }
}
