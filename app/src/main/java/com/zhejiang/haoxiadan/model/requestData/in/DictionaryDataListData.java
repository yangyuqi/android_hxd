package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/7.
 */
public class DictionaryDataListData {
    private long id;//
    private long parentId;//
    private long typeId;//
    private String dataCode;//
    private String dataName;//
    private long dataOrder;//
    private String dataDesc;//


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public long getDataOrder() {
        return dataOrder;
    }

    public void setDataOrder(long dataOrder) {
        this.dataOrder = dataOrder;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }
}
