package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;

/**
 * Created by KK on 2017/9/9.
 */
public class QueryBankListByUserData {
    private List<BankListData> userBankList;

    public List<BankListData> getUserBankList() {
        return userBankList;
    }

    public void setUserBankList(List<BankListData> userBankList) {
        this.userBankList = userBankList;
    }
}
