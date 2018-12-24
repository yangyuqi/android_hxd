package com.zhejiang.haoxiadan.util;

/**
 * Created by wqd on 2017/2/13 0013.
 */
public enum Event {
    WX_PAY_RESULT_OK,
    WX_PAY_RESULT_ERR,
    DIALOG_DISMISS,         //
    TIP_GOTO_LOGIN,      //提示去登陆
    GOTO_LOGIN,      //去登陆
    LOGIN_OUT,       //退出登录
    EXIT,                    //退出应用
    REFRESH_EVALUATION_NUM,  //商品详情刷新评价数量
    CHAT_COUNT,//聊天数量更改
    CHAT_CLOSE_ALL,  //客服聊天关闭其他界面
    REFRESH_ORDER,   //刷新订单列表或订单详情
    GOTO_HOME,        //去首页

    BACK_TO_SHOP,//店铺搜索结果页返回到店铺

    GO_TO_CHAT,
    DOWNLOAD_APK_COMPLATE,//下载升级包完成

    FINISH_GOODS_ACTIVITY
}
