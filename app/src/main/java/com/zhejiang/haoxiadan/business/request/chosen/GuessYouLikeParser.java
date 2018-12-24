package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ForwardItem;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData2;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsLikeListData;
import com.zhejiang.haoxiadan.model.requestData.in.GuessYouLikeData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectVisualFloorAllData;
import com.zhejiang.haoxiadan.model.requestData.in.VisualFloorListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 猜你喜欢
 * Created by KK on 2017/9/9.
 */

public class GuessYouLikeParser extends AbsBaseParser {
    public GuessYouLikeParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GuessYouLikeData guessYouLikeData = mDataParser.parseObject(data,GuessYouLikeData.class);

        List<Goods> goodses = mappedGoods(guessYouLikeData);
        GuessYouLikeListener listener = (GuessYouLikeListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGuessYouLikeSuccess(goodses);
        }
    }

    private List<Goods> mappedGoods(GuessYouLikeData guessYouLikeData){
        List<Goods> goodses = new ArrayList<>();
        for(GoodsLikeListData goodsLikeListData:guessYouLikeData.getGoodsLikeList()){
            Goods goods = new Goods();
            goods.setId(goodsLikeListData.getId()+"");
            goods.setIcon(goodsLikeListData.getPath());
            goods.setTitle(goodsLikeListData.getGoods_name());
            goods.setPrice(goodsLikeListData.getGoods_current_price());
            goods.setMonthSale(goodsLikeListData.getMonthlySales());

            goods.setGoodsNo(goodsLikeListData.getGoodsSerial());

            goodses.add(goods);
        }
        return goodses;
    }
}
