package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class PersonFooter implements Serializable{
    private int totalPageCount ;
    private List<PersonFooterDetails> footPointList ;

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public List<PersonFooterDetails> getFootPointList() {
        return footPointList;
    }

    public void setFootPointList(List<PersonFooterDetails> footPointList) {
        this.footPointList = footPointList;
    }
}
