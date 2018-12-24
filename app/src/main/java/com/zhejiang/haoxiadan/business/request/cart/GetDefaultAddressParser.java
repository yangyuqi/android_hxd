package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.common.Logistics;
import com.zhejiang.haoxiadan.model.common.LogisticsNode;
import com.zhejiang.haoxiadan.model.requestData.in.GetExpressData;
import com.zhejiang.haoxiadan.model.requestData.in.LogisticsInfoData;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取默认地址
 * Created by KK on 2017/9/15.
 */
public class GetDefaultAddressParser extends AbsBaseParser {

    public GetDefaultAddressParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        CurrentUserAddressData currentUserAddressData = mDataParser.parseObject(data,CurrentUserAddressData.class);

        Address address = null;
        if(currentUserAddressData.getAddressList()!=null && currentUserAddressData.getAddressList().size()==1){
            address = mapped(currentUserAddressData.getAddressList().get(0));
        }

        GetDefaultAddressListener listener = (GetDefaultAddressListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onGetDefaultAddressSuccess(address);
        }
    }

    private Address mapped(CurrentUserAddressBean currentUserAddressBean){
        Address address = new Address();
        address.setId(currentUserAddressBean.getId()+"");
        address.setName(currentUserAddressBean.getTrueName());
        address.setMobile(currentUserAddressBean.getMobile());
        address.setArea(currentUserAddressBean.getProvinceName()+currentUserAddressBean.getCityName()+currentUserAddressBean.getAreaName());
        address.setDetailAddress(currentUserAddressBean.getArea_info());
        return address;
    }

}
