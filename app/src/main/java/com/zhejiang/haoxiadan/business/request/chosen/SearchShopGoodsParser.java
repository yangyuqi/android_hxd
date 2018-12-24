package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.requestData.in.LuceneListData;
import com.zhejiang.haoxiadan.model.requestData.in.SearchByLuceneData;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺内搜索商品
 * Created by KK on 2017/9/13.
 */
public class SearchShopGoodsParser extends AbsBaseParser {
    public SearchShopGoodsParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        SearchByLuceneData searchByLuceneData = mDataParser.parseObject(data,SearchByLuceneData.class);
        List<Goods> goodses = mapped(searchByLuceneData.getLuceneList());

        SearchShopGoodsListener listener = (SearchShopGoodsListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSearchShopGoodsSuccess(goodses);
        }
    }

    private List<Goods> mapped(List<LuceneListData> luceneListDatas){
        List<Goods> goodses = new ArrayList<>();
        for(LuceneListData luceneListData : luceneListDatas){
            Goods goods = new Goods();
            goods.setId(luceneListData.getVo_id()+"");
            goods.setIcon(luceneListData.getVo_main_photo_url());
            goods.setTitle(luceneListData.getVo_title());
            goods.setPrice(luceneListData.getVo_store_price());
            goods.setMonthSale(luceneListData.getVo_goods_salenum());
            goods.setGoodsNumType(Integer.parseInt(luceneListData.getGoods_numType()));
            goodses.add(goods);
        }

        return goodses;
    }

}
