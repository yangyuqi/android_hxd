package com.zhejiang.haoxiadan.third.network;

/**
 * Created by KK on 2017/2/17.
 */
public class Server {

    public static final int CONNECT_TIMEOUT = 30;//网络超时时间

    //测试
//    //接口
    public static final String IMG_HOST = "apiWeb.hxd.ughen.cn";
    public static final String HOST = "apiWeb.hxd.ughen.cn";
    public static final String APP = "/TermireMall";
    public static final String HOST_VERSION = "/api/app/v2";

    public static final String HOST_VERSION3 = "/api/app/v3";
    public static final String HOST_VERSION1 = "api/app/v1";
    //搜索接口
    public static final String SEARCH_HOST = "192.168.2.24:8081";
    public static final String SEARCH_APP = "/MallLucene/api/app/v2";
//    供应链

    public static final String FINANCE_HOST = "218.94.47.138:8905";
    public static final String FINANCE_APP = "/FinanceChain/api";

    //正式
    //接口
//    public static final String IMG_HOST = "218.94.47.138:8905";
//    public static final String HOST = "47.96.95.209:8100";
//    public static final String APP = "/MallApi";
//    public static final String HOST_VERSION = "/api/app/v2";
//    //搜索接口
//    public static final String SEARCH_HOST = "47.96.95.209:8100";
//    public static final String SEARCH_APP = "/Lucene/api/app/v2";
////   供应链
//    public static final String FINANCE_HOST = "116.62.197.248:8018";
//    public static final String FINANCE_APP = "/finance/api";


    public static String getUrl(String interfaceName) {
        String myInterfaceName;
        if (!interfaceName.substring(0, 1).equals("/")) {
            myInterfaceName = "/" + interfaceName;
        } else {
            myInterfaceName = interfaceName;
        }
        return "http://" + Server.HOST  + APP + HOST_VERSION +myInterfaceName;
    }

    public static String getUrl1(String interfaceName) {
        String myInterfaceName;
        if (!interfaceName.substring(0, 1).equals("/")) {
            myInterfaceName = "/" + interfaceName;
        } else {
            myInterfaceName = interfaceName;
        }
        return "http://" + Server.HOST  + APP + HOST_VERSION1 +myInterfaceName;
    }

    public static String getUrl3(String interfaceName) {
        String myInterfaceName;
        if (!interfaceName.substring(0, 1).equals("/")) {
            myInterfaceName = "/" + interfaceName;
        } else {
            myInterfaceName = interfaceName;
        }
        return "http://" + Server.HOST  + APP + HOST_VERSION3 +myInterfaceName;
    }

    public static String searchGetUrl(String interfaceName){
        String myInterfaceName;
        if (!interfaceName.substring(0, 1).equals("/")) {
            myInterfaceName = "/" + interfaceName;
        } else {
            myInterfaceName = interfaceName;
        }
        return "http://" + Server.SEARCH_HOST  + SEARCH_APP +myInterfaceName;
    }

    public static String getFinanceUrl(String interfaceName){
        String myInterfaceName;
        if (!interfaceName.substring(0, 1).equals("/")) {
            myInterfaceName = "/" + interfaceName;
        } else {
            myInterfaceName = interfaceName;
        }

        return "http://" + FINANCE_HOST + FINANCE_APP + myInterfaceName;
    }

}
