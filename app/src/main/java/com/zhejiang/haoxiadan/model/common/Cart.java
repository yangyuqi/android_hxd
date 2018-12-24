package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车
 * Created by KK on 2017/8/23.
 */
public class Cart implements Serializable {
    private String supplierId;
    //供应商名称
    private String supplierName;
    //商品列表
    private List<CartGoods> cartGoodses;
    //是否选择
    private boolean isChoose;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<CartGoods> getCartGoodses() {
        return cartGoodses;
    }

    public void setCartGoodses(List<CartGoods> cartGoodses) {
        this.cartGoodses = cartGoodses;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
