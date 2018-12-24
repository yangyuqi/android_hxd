package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

/**
 * 修改购物车数量接口
 * Created by KK on 2017/9/1.
 */
public class EditGoodsCartCountParser extends AbsBaseParser {

    public EditGoodsCartCountParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        EditGoodsCartCountListener listener = (EditGoodsCartCountListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onEditGoodsCartCountSuccess();
        }
    }

}
