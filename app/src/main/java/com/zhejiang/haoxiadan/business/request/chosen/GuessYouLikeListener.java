package com.zhejiang.haoxiadan.business.request.chosen;

import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.common.Goods;

import java.util.List;

/**
 * 猜你喜欢
 * Created by KK on 2017/9/9.
 */

public interface GuessYouLikeListener extends OnBaseRequestListener {
    void onGuessYouLikeSuccess(List<Goods> goodses);
}
