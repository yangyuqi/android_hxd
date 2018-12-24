package com.zhejiang.haoxiadan.util;

/**
 * Created by wqd on 2017/3/3 0003.
 */
public class Utils {
    //微信appid
    public static final String WX_APP_ID = "wx1dfd546774b77f5a";

    //已取消订单
    public static final String ORDER_TYPE0 = "0";
    //已提交待付款
    public static final String ORDER_TYPE10 = "10";
    //货到付款
    public static final String ORDER_TYPE16 = "16";
    //已付款待发货
    public static final String ORDER_TYPE20 = "20";
    //申请退款
    public static final String ORDER_TYPE21 = "21";
    //正在退款
    public static final String ORDER_TYPE22 = "22";
    //已退款
    public static final String ORDER_TYPE25 = "25";
    //已发货待收货
    public static final String ORDER_TYPE30 = "30";
    //已收货
    public static final String ORDER_TYPE40 = "40";
    //申请退货
    public static final String ORDER_TYPE41 = "41";
    //退货中
    public static final String ORDER_TYPE42 = "42";
    //已退货
    public static final String ORDER_TYPE45 = "45";
    //买家评论完毕
    public static final String ORDER_TYPE50 = "50";
    //已结束
    public static final String ORDER_TYPE60 = "60";
    //订单不可评价，到达设定时间，系统自动关闭订单互相评价功能
    public static final String ORDER_TYPE65 = "65";
    
    //为不能付订金，没到付订金开始时间
    public static final String ORDER_PAYMENT_STATUS_0 = "0";
    //为可以付定金
    public static final String ORDER_PAYMENT_STATUS_1 = "1";
    //已经付订金，等到付尾款开始
    public static final String ORDER_PAYMENT_STATUS_2 = "2";
    //可以付尾款
    public static final String ORDER_PAYMENT_STATUS_3 = "3";
    //收藏没有付订金
    public static final String ORDER_PAYMENT_STATUS_4 = "4";
    //付订金未付尾款
    public static final String ORDER_PAYMENT_STATUS_5 = "5";
}
