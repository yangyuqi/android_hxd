package com.zhejiang.haoxiadan.business.request.pay;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.business.request.my.CloudPayListener;
import com.zhejiang.haoxiadan.business.request.my.CloudPayParser;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 付款相关
 * Created by KK on 2017/9/8.
 */
public class PayRequester extends BaseRequester {

    public void queryBankList(Context context, QueryBankListListener listener){
        Map<String, Object> params = new HashMap<>();
        post(context, Server.getUrl("pay/queryBankList"),params,listener,new QueryBankListParser(listener));
    }

    /**
     *绑定银行卡发短信
     * @param context
     * @param params
     * @param listener
     */
    public void bankInfoSave(Context context,Map<String, Object> params, BankInfoSaveListener listener){
        post(context, Server.getUrl("pay/bankInfoSave"),params,listener,new BankInfoSaveParser(listener));
    }

    /**
     * 验证绑定短信验证码
     */
    public void verifySBindingSMS(Context context,String accessToken,String userBankId,String code, VerifySBindingSMSListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("userBankId",userBankId);
        params.put("SMSValidationCode",code);
        post(context, Server.getUrl("pay/verifySBindingSMS"),params,listener,new VerifySBindingSMSParser(listener));
    }

    /**
     * 获取用户已绑定银行卡列表
     * @param context
     * @param accessToken
     * @param listener
     */
    public void queryBankListByUser(Context context,String accessToken, QueryBankListByUserListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        post(context, Server.getUrl("pay/queryBankListByUser"),params,listener,new QueryBankListByUserParser(listener));
    }


    /**
     * 获得订单中金银行卡支付code
     * @param context
     * @param accessToken
     * @param orderId
     * @param userBankId
     * @param listener
     */
    public void getOrderPayCode(Context context,String accessToken,String orderId,String userBankId, GetOrderPayCodeListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("txCode","1375");
        params.put("id",orderId);
        params.put("userBankId",userBankId);
        post(context, Server.getUrl("pay/cpcnpay"),params,listener,new GetOrderPayCodeParser(listener));
    }

    public void cloudPay(Context context , String accessToken , String orderId , String type , CloudPayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("tranType","0005");
        params.put("accessToken",accessToken);
        params.put("payMode",type);
        params.put("phoneType","android");
        params.put("deviceMerId","");
        List<Integer> data = new ArrayList<>();
        data.add(Integer.parseInt(orderId));
        params.put("ids",data);
        post(context,"http://apiweb.hxd.ughen.cn/TermireMall/chinapay/pay",params,listener,new CloudPayParser(listener));
    }

    /**
     * 订单中金银行卡支付
     * @param context
     * @param accessToken
     * @param orderId
     * @param userBankId
     * @param code
     * @param listener
     */
    public void orderPay(Context context,String accessToken,String orderId,String userBankId,String code, OrderPayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("txCode","1376");
        params.put("id",orderId);
        params.put("userBankId",userBankId);
        params.put("SMSValidationCode",code);
        post(context, Server.getUrl("pay/cpcnpay"),params,listener,new OrderPayParser(listener));
    }

    /**
     * 中金快捷支付状态处理中接口
     * @param context
     * @param accessToken
     * @param orderId
     * @param listener
     */
    public void collQueryOrderStatus(Context context,String accessToken,String orderId, CollQueryOrderStatusListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("order_id",orderId);
        params.put("type","10");
        post(context, Server.getUrl("order/collQueryOrderStatus"),params,listener,new CollQueryOrderStatusParser(listener));
    }

    /**
     * 获得增值服务中金银行卡支付code
     * @param context
     * @param accessToken
     * @param valueAddId
     * @param feeId
     * @param userBankId
     * @param isFirst
     * @param listener
     */
    public void getValueAddPayCode(Context context,String accessToken,String valueAddId,String feeId,String userBankId,boolean isFirst, GetValueAddPayCodeListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("txCode","1375");
        params.put("id",valueAddId);
        params.put("feeId",feeId);
        params.put("userBankId",userBankId);
        if(isFirst){
            params.put("status","pay");
        }else{
            params.put("status","其他值");
        }
        post(context, Server.getUrl("pay/incrementPay"),params,listener,new GetValueAddPayCodeParser(listener));
    }

    /**
     * 增值服务中金银行卡支付
     * @param context
     * @param accessToken
     * @param valueAddId
     * @param feeId
     * @param userBankId
     * @param isFirst
     * @param code
     * @param listener
     */
    public void valueAddPay(Context context,String accessToken,String valueAddId,String feeId,String userBankId,boolean isFirst,String code, ValueAddPayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("txCode","1376");
        params.put("id",valueAddId);
        params.put("feeId",feeId);
        params.put("userBankId",userBankId);
        if(isFirst){
            params.put("status","pay");
        }else{
            params.put("status","其他值");
        }
        params.put("SMSValidationCode",code);
        post(context, Server.getUrl("pay/incrementPay"),params,listener,new ValueAddPayParser(listener));
    }

    /**
     * App支付宝微信订单支付
     * @param context
     * @param accessToken
     * @param orderMainId
     * @param type
     * @param listener
     */
    public void applyOrderFormPay(Context context,String accessToken,String orderMainId,String type, ApplyOrderFormPayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("id",orderMainId);
        params.put("type",type);
        post(context, Server.getUrl("valueAdd/applyOrderFormPay"),params,listener,new ApplyOrderFormPayParser(listener));
    }

    public void applayWX(Context context ,String accessToken,String orderId,ApplayWXListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("txCode","1411");
        params.put("id",orderId);
        params.put("paymentWay","13");
        post(context,Server.getUrl("pay/cpcnpay"),params,listener,new ApplayWXParser(listener));
    }

    /**
     * 微信和支付宝增值服务支付
     * @param context
     * @param accessToken
     * @param type
     * @param applyId
     * @param feeId
     * @param isFirst
     * @param listener
     */
    public void applyRolePay(Context context,String accessToken,String type,String applyId,String feeId,boolean isFirst, ApplyOrderFormPayListener listener){
        Map<String, Object> params = new HashMap<>();
        params.put("accessToken",accessToken);
        params.put("type",type);
        params.put("id",applyId);
        params.put("feeId",feeId);
        if (isFirst){
            params.put("status","pay");
        }else{
            params.put("status","其他值");
        }
        post(context, Server.getUrl("valueAdd/applyRolePay"),params,listener,new ApplyOrderFormPayParser(listener));
    }
}
