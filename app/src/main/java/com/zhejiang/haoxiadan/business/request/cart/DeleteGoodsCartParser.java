package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 删除购物车商品
 * Created by KK on 2017/9/1.
 */
public class DeleteGoodsCartParser extends AbsBaseParser {

    public DeleteGoodsCartParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        DeleteGoodsCartListener listener = (DeleteGoodsCartListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onDeleteGoodsCartSuccess();
        }
    }

}
