package com.zhejiang.haoxiadan.model.requestData.out.my;

public class UploadImageData {
    private String code ;
    private String msg ;
    private UploadImgeBean data ;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UploadImgeBean getData() {
        return data;
    }

    public void setData(UploadImgeBean data) {
        this.data = data;
    }
}
