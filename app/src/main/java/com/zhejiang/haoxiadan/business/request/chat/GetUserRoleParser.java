package com.zhejiang.haoxiadan.business.request.chat;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.PushInfo;
import com.zhejiang.haoxiadan.model.common.UserRole;

import java.util.List;

/**
 * Created by wqd on 2017/9/7 0006.
 */

public class GetUserRoleParser extends AbsBaseParser {

    public GetUserRoleParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }
    @Override
    protected void parseData(@Nullable String data) {
        UserRole userRole = mDataParser.parseObject(data,UserRole.class);
        GetUserRoleListener getUserRoleListener = (GetUserRoleListener) mOnBaseRequestListener.get();
        if (getUserRoleListener!=null){
            getUserRoleListener.onGetUserRoleSuccess(userRole);
        }
    }
}
