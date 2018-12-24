package com.zhejiang.haoxiadan.model.requestData.in;

import java.util.List;
import java.util.Map;

/**
 * Created by KK on 2017/11/23.
 */
public class ViewClassData {
    private String name;
    private int classId;
    private List<Map<String,String>> info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public List<Map<String, String>> getInfo() {
        return info;
    }

    public void setInfo(List<Map<String, String>> info) {
        this.info = info;
    }
}
