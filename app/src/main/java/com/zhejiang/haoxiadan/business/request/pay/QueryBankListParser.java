package com.zhejiang.haoxiadan.business.request.pay;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.business.request.category.QueryGoodsClassAllListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.requestData.in.BankListData;
import com.zhejiang.haoxiadan.model.requestData.in.GoodsClassData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryBankListData;
import com.zhejiang.haoxiadan.model.requestData.in.QueryGoodsClassAllData;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取支持的银行卡列表
 * Created by KK on 2017/9/8.
 */
public class QueryBankListParser extends AbsBaseParser {

    public QueryBankListParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        QueryBankListData queryBankListData = mDataParser.parseObject(data,QueryBankListData.class);


        List<BankCard> bankCards = mapped(queryBankListData.getBankList());
        QueryBankListListener listener = (QueryBankListListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onQueryBankListSuccess(bankCards);
        }
    }

    private List<BankCard> mapped(List<BankListData> bankListDatas){
        List<BankCard> bankCards = new ArrayList<>();
        for(BankListData bankListData:bankListDatas){
            BankCard bankCard = new BankCard();
            bankCard.setId(bankListData.getId()+"");
            bankCard.setBankNo(bankListData.getBankId());
            bankCard.setBankName(bankListData.getBankName());

            bankCards.add(bankCard);
        }

        return bankCards;
    }

}
