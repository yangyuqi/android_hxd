package com.zhejiang.haoxiadan.model.choseModel;

import java.io.Serializable;

/**
 * Created by qiuweiyu on 2017/11/25.
 */

public class KeyValueModel implements Serializable{
    private String key_name ;
    private String key_value ;

    public KeyValueModel(String key_name, String key_value) {
        this.key_name = key_name;
        this.key_value = key_value;
    }

    public KeyValueModel(){}

    public String getKey_name() {
        return key_name;
    }

    public void setKey_name(String key_name) {
        this.key_name = key_name;
    }

    public String getKey_value() {
        return key_value;
    }

    public void setKey_value(String key_value) {
        this.key_value = key_value;
    }
}
