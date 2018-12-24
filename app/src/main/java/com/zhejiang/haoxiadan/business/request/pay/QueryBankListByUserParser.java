package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.requestData.in.BankListData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryBankListByUserData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryBankListData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户已绑定银行卡列表
 * Created by KK on 2017/9/9.
 */
public class QueryBankListByUserParser extends AbsBaseParser {

    public QueryBankListByUserParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        QueryBankListByUserData queryBankListByUserData = mDataParser.parseObject(data,QueryBankListByUserData.class);


        List<BankCard> bankCards = mapped(queryBankListByUserData.getUserBankList());
        QueryBankListByUserListener listener = (QueryBankListByUserListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryBankListByUserSuccess(bankCards);
        }
    }

    private List<BankCard> mapped(List<BankListData> bankListDatas){
        List<BankCard> bankCards = new ArrayList<>();
        for(BankListData bankListData:bankListDatas){
            BankCard bankCard = new BankCard();
            bankCard.setBankNo(bankListData.getId()+"");
            bankCard.setBankName(bankListData.getBankName());
            bankCard.setCardNo(bankListData.getBankNumHide().substring(bankListData.getBankNumHide().length()-4,bankListData.getBankNumHide().length()));
            bankCard.setMobile(bankListData.getUserPhone());

            bankCards.add(bankCard);
        }

        return bankCards;
    }

}
