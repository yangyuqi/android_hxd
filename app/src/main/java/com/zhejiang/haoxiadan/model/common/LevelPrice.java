package com.zhejiang.haoxiadan.model.common;

import java.io.Serializable;

/**
 * 阶梯价格
 * Created by KK on 2017/9/11.
 */
public class LevelPrice implements Serializable {
    private int minNum;
    private int maxNum;
    private double price;

    private boolean isChoose;

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }

    public int getMinNum() {
        return minNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
