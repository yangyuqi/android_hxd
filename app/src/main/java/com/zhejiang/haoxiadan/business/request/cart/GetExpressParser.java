package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.Logistics;
import com.zhejiang.haoxiadan.model.common.LogisticsNode;
import com.zhejiang.haoxiadan.model.common.Order;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.GetExpressData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsGspData2;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsInfoListData;
import com.zhejiang.haoxiadan.model.requestData.in.LogisticsInfoData;
import com.zhejiang.haoxiadan.model.requestData.in.OrderFormListData2;
import com.zhejiang.haoxiadan.model.requestData.in.SelectOrderFormByIdsData;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单物流信息接口
 * Created by KK on 2017/9/6.
 */
public class GetExpressParser extends AbsBaseParser {

    public GetExpressParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        GetExpressData getExpressData = mDataParser.parseObject(data,GetExpressData.class);

        Logistics logistics = mapped(getExpressData);
        GetExpressListener listener = (GetExpressListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetExpressSuccess(logistics);
        }
    }

    private Logistics mapped(GetExpressData getExpressData){
        Logistics logistics = new Logistics();
        logistics.setShipCompany(getExpressData.getOrder_express_name());
        logistics.setInfoProvider(getExpressData.getOrder_express_name());
        List<LogisticsNode> logisticsNodes = new ArrayList<>();
        if(getExpressData.getInfo()!=null){
            List<LogisticsInfoData> logisticsInfoDatas = mDataParser.parseList(getExpressData.getInfo(),LogisticsInfoData.class);
            for(LogisticsInfoData logisticsInfoData : logisticsInfoDatas){
                LogisticsNode logisticsNode = new LogisticsNode();
                logisticsNode.setTime(logisticsInfoData.getTime());
                logisticsNode.setContent(logisticsInfoData.getStatus());

                logisticsNodes.add(logisticsNode);
            }
        }
        logistics.setLogisticsNodes(logisticsNodes);

        return logistics;
    }

}
