package com.zhejiang.haoxiadan.business;

/**
 * 约定的接口请求返回的错误码
 * Created by KK on 2017/3/6.
 */

public class ErrorCode {

    //access令牌不存在
    public static final String ERR_ACCESS_TOKEN_NOT_EXIST = "1005";
    //refresh令牌不存在
    public static final String ERR_REFRESH_TOKEN_NOT_EXIST = "1006";
    //access_token失效
    public static final String ERR_ACCESS_TOKEN_INVALID = "1007";
    //refresh_token失效
    public static final String ERR_REFRESH_TOKEN_INVALID = "1008";
    //access令牌不能为空
    public static final String ERR_ACCESS_TOKEN_EMPTY = "1009";
    //access令牌不能为空
    public static final String ERR_REFRESH_TOKEN_EMPTY = "1010";
}
