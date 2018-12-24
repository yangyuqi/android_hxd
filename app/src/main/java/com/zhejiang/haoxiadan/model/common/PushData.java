package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 推送数据的模型
 * Created by KK on 2017/5/4.
 */

public class PushData implements Serializable {

    private String type;//login异地登录,order 订单
    private String data_id;//

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData_id() {
        return data_id;
    }

    public void setData_id(String data_id) {
        this.data_id = data_id;
    }
}
