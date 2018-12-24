package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.model.common.ProductStyle;
import com.zhejiang.haoxiadan.model.common.Purchaser;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.ApplyRoleInfoData;
import com.zhejiang.haoxiadan.model.requestData.in.RoleApplyData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListStyleBean;
import com.zhejiang.haoxiadan.ui.activity.my.ValueAddActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 行业表
 * Created by KK on 2017/9/4.
 */
public class QueryTradeInfoParser extends AbsBaseParser {

    public QueryTradeInfoParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        QueryTradeInfoData queryTradeInfoData = mDataParser.parseObject(data,QueryTradeInfoData.class);

        List<Industry> industryList = mapped(queryTradeInfoData.getTradeList());
        List<Industry> firstFocus = mapped(queryTradeInfoData.getMainBuyTradeList());
        List<Industry> secondFocus = mapped(queryTradeInfoData.getFollowTradeList());

        QueryTradeInfoListener listener = (QueryTradeInfoListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryTradeInfoSuccess(industryList,firstFocus,secondFocus);
        }
    }

    private List<Industry> mapped(List<TradeListData> tradeList){
        List<Industry> industryList = new ArrayList<>();
        for(TradeListData tradeListData : tradeList){
            Industry industry = new Industry();
            industry.setId(tradeListData.getTradeId()+"");
            industry.setTitle(tradeListData.getTradeName());
            if(tradeListData.getStyles() != null){
                List<ProductStyle> productStyles = new ArrayList<>();
                for(TradeListStyleBean tradeListStyleBean:tradeListData.getStyles()){
                    ProductStyle productStyle = new ProductStyle();
                    productStyle.setId(tradeListStyleBean.getId());
                    productStyle.setTitle(tradeListStyleBean.getTitle());
                    productStyles.add(productStyle);
                }
                industry.setProductStyles(productStyles);
            }
            industryList.add(industry);
        }

        return industryList;
    }

}
