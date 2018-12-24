package com.zhejiang.haoxiadan.model.choseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/7.
 */

public class SearchData {
    private List<SearchChannelModel> channel ;
    private List<SearchLuceneListBean> luceneList ;
    private int totalPage ;
    private int currentPage ;
    private ArrayList<SearchGoodsPropertyBean> goodsPropertyList ;

    public List<SearchChannelModel> getChannel() {
        return channel;
    }

    public void setChannel(List<SearchChannelModel> channel) {
        this.channel = channel;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public ArrayList<SearchGoodsPropertyBean> getGoodsPropertyList() {
        return goodsPropertyList;
    }

    public void setGoodsPropertyList(ArrayList<SearchGoodsPropertyBean> goodsPropertyList) {
        this.goodsPropertyList = goodsPropertyList;
    }

    public List<SearchLuceneListBean> getLuceneList() {
        return luceneList;
    }

    public void setLuceneList(List<SearchLuceneListBean> luceneList) {
        this.luceneList = luceneList;
    }
}
