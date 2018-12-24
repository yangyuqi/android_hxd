package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;
import android.util.Log;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.User;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class LoginParse extends AbsBaseParser {
    public LoginParse(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        User user = mDataParser.parseObject(data,User.class);
        LoginListener loginListener = (LoginListener) mOnBaseRequestListener.get();
        if (loginListener!=null){

            if (user.getUserRole()==null){
                user.setUserType(User.USER_TYPE.BUYER);
                user.setUserRole("");
            }else {
                switch (user.getUserRole()) {
                    case "REGULAR"://普通用户
//                    user.setUserType(User.USER_TYPE.COMMON);
                        user.setUserType(User.USER_TYPE.BUYER);
                        break;
                    case "SELLER"://卖家
                        user.setUserType(User.USER_TYPE.BUYER);
                        break;
                    case "BUYER"://买家
                        user.setUserType(User.USER_TYPE.BUYER);
                        break;
                    case "CUSTOMER"://平台客服
                        user.setUserType(User.USER_TYPE.SERVICE);
                        break;
                    default:
                        user.setUserType(User.USER_TYPE.COMMON);
                        break;
                }
            }
            loginListener.getUser(user);
        }
    }
}
