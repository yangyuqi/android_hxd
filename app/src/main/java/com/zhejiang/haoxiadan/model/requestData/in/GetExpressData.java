package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/6.
 */
public class GetExpressData {
    private String order_express_name;//	快递名称	是	String	申通
    private String info;//	物流在途信息	否	String		有物流信息时有值
    private String express_telephone;//	物流热线	否	String		有物流信息时有值

    public String getOrder_express_name() {
        return order_express_name;
    }

    public void setOrder_express_name(String order_express_name) {
        this.order_express_name = order_express_name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getExpress_telephone() {
        return express_telephone;
    }

    public void setExpress_telephone(String express_telephone) {
        this.express_telephone = express_telephone;
    }
}
