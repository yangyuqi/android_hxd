package com.zhejiang.haoxiadan.model.requestData.out.my;

import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class CurrentUserAddressData {
    private List<CurrentUserAddressBean> addressList ;

    public List<CurrentUserAddressBean> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<CurrentUserAddressBean> addressList) {
        this.addressList = addressList;
    }
}
