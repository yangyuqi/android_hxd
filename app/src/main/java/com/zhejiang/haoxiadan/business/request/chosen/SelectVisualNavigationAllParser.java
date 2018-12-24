package com.zhejiang.haoxiadan.business.request.chosen;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.ShopTab;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData2;
import com.zhejiang.haoxiadan.model.requestData.in.QueryStoreArchivesData;
import com.zhejiang.haoxiadan.model.requestData.in.SelectVisualNavigationAllData;
import com.zhejiang.haoxiadan.model.requestData.in.VisualNavigationListData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺首页接口
 * Created by KK on 2017/9/7.
 */
public class SelectVisualNavigationAllParser extends AbsBaseParser {
    public SelectVisualNavigationAllParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        SelectVisualNavigationAllData selectVisualNavigationAllData = mDataParser.parseObject(data,SelectVisualNavigationAllData.class);

        List<ShopTab> shopTabs = mapped(selectVisualNavigationAllData);

        SelectVisualNavigationAllListener listener = (SelectVisualNavigationAllListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onSelectVisualNavigationAllSuccess(shopTabs);
        }
    }

    private List<ShopTab> mapped(SelectVisualNavigationAllData selectVisualNavigationAllData){
        List<ShopTab> shopTabs = new ArrayList<>();
        for (VisualNavigationListData visualNavigationListData : selectVisualNavigationAllData.getVisualNavigationList()){
            ShopTab shopTab = new ShopTab();
            shopTab.setTitle(visualNavigationListData.getNavigationName());
            ArrayList<Goods> goodses = new ArrayList<>();
            for(GoodsInfoListData2 goodsInfoListData2:visualNavigationListData.getGoodsInfoList()){
                Goods goods = new Goods();
                goods.setId(goodsInfoListData2.getGoodsId()+"");
                goods.setIcon(goodsInfoListData2.getGoodsMainPhotoPath());
                goods.setTitle(goodsInfoListData2.getGoods_name());
                goods.setPrice(goodsInfoListData2.getStorePrice());
                goods.setMonthSale(goodsInfoListData2.getGoodsSalenum());
                goods.setGoodsNumType(goodsInfoListData2.getGoodsNumType());
                goodses.add(goods);
            }
            shopTab.setGoodses(goodses);

            shopTabs.add(shopTab);
        }

        return shopTabs;
    }
}
