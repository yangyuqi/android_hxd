package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/5.
 */
public class IconAccData {
    private String id;//			String		图片id
    private String name;// String		图片名称
    private String path;//			String		图片路径
    private String ext;// String		后缀
    private String width;//			String		宽度

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
