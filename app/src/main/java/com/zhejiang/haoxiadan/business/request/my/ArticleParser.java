package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;

import org.json.JSONObject;

/**
 * Created by qiuweiyu on 2017/9/9.
 */

public class ArticleParser extends AbsBaseParser {
    public ArticleParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String content = jsonObject.getString("content");
            ArticleListener aboutUsListener = (ArticleListener) mOnBaseRequestListener.get();
            if (aboutUsListener != null) {
                aboutUsListener.onArticleSuccess(content);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
