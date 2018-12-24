package com.zhejiang.haoxiadan.model.requestData.in;

/**
 * Created by KK on 2017/9/4.
 */
public class FeeData {
    private String id;//		是	String
    private String title;//	描述	是	String
    private int year;//	年数	是	int
    private double price;//	单价	是	Double

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
