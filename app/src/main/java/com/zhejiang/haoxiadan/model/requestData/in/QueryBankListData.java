package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/8.
 */
public class QueryBankListData {
    private List<BankListData> bankList;

    public List<BankListData> getBankList() {
        return bankList;
    }

    public void setBankList(List<BankListData> bankList) {
        this.bankList = bankList;
    }
}
