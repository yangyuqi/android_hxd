package com.zhejiang.haoxiadan.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.model.Message;

/**
 * Created by KK on 2017/2/17.
 */

public class Constants {
    public static boolean isDebug = true;

    public static String clearClipBoard = "clearClipBoard";

    public static String accessToken = "accessToken";
    public static String refreshToken = "refreshToken";
    public static String userName = "userName";
    public static String phone = "phone";
    public static String user_icon = "user_icon";
    public static String nickName = "nickName";
    public static String search_content = "search_content";
    public static String tradeId = "tradeId";
    public static String userRole = "userRole";//用户类型
    public static String finance_text = "finance_text" ;//贷款协议书

    //用户类型
    public static final String GLOBAL_DATA_KEY_USER_TYPE = "GLOBAL_DATA_KEY_USER_TYPE";

    //是否使用过此app
    public static final String SHAREDPREFERENCES_KEY_IS_USED = "SHAREDPREFERENCES_KEY_IS_USED";

    //搜索历史
    public static final String GLOBAL_DATA_KEY_SEARCH_HISTORY = "GLOBAL_DATA_KEY_SEARCH_HISTORY";
    public static final String GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY = "GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY";
    public static final String GLOBAL_DATA_KEY_SHOP_SEARCH_GOODS_HISTORY = "GLOBAL_DATA_KEY_SHOP_SEARCH_GOODS_HISTORY";

    public static final String GLOBAL_DATA_KEY_PHONE = "GLOBAL_DATA_KEY_PHONE";
    public static final String GLOBAL_DATA_KEY_TRUE_NAME = "GLOBAL_DATA_KEY_TRUE_NAME";
    public static final String GLOBAL_DATA_KEY_EMAIL = "GLOBAL_DATA_KEY_EMAIL";
    public static final String GLOBAL_DATA_KEY_LEVELNAME = "GLOBAL_DATA_KEY_LEVELNAME";

    //广播
    public static final String BROADCAST_RECEIVER_ACTION_COUNTDOWN = "BROADCAST_RECEIVER_ACTION_COUNTDOWN";
    public static final String PUSH_RECEIVER_TIP_LOGIN_DATA = "PUSH_RECEIVER_TIP_LOGIN_DATA";

    //地区选择
    public static final String GLOBAL_DATA_AREA = "GLOBAL_DATA_AREA";

    //用户角色
    public static final String USER_REGULAR="REGULAR";
    public static final String USER_BUY = "BUYER";
    public static final String USER_SELL = "SELLER";
    public static final String USER_CUSTOMER = "CUSTOMER";

    //jpush im
    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;

    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();
    public static List<Message> forwardMsg = new ArrayList<>();
    public static List<Message> addForwardMsg = new ArrayList<>();

    public static long registerOrLogin = 1;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;

    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;

    public static final String DRAFT = "draft";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String NAME = "name";
    public static final String ATALL = "atall";

    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    public static final String JCHAT_CONFIGS = "JChat_configs";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String TARGET_LASTMSGDATE = "targetLastMsgDate";

    public static final String JPUSH_APPKEY = "43fe9b8de1d5f3934eebf713";
    public static int maxImgCount;               //允许选择图片最大数
    //jpush im

    /**
     * 临时保存的数据
     */
    //购买的商品id，（支付成功后猜你喜欢用）
    public static final String TEMP_DATA_KEY_BUY_GOODS_ID = "TEMP_DATA_KEY_BUY_GOODS_ID";
    //增值服务的价格，（支付成功后用）
    public static final String TEMP_DATA_KEY_VALUE_ADD_PRICE = "TEMP_DATA_KEY_VALUE_ADD_PRICE";
    /**
     *
     */

}
