package com.zhejiang.haoxiadan.ui.adapter.category;

/**
 * 分类，顶部tab的模型
 * Created by KK on 2017/11/22.
 */
public class CateTabModel{
    //类型 0标题;1向下的箭头
    private int type;
    //标题
    private String title;
    //是否选中
    private boolean isChoose;

    public CateTabModel(){

    }

    public CateTabModel(int type,String title,boolean isChoose){
        this.type = type;
        this.title = title;
        this.isChoose = isChoose;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}
