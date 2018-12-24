package com.zhejiang.haoxiadan.ui.adapter.chosen;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.zhejiang.haoxiadan.model.common.ShopFloor;

/**
 * Created by KK on 2017/5/2.
 */

public abstract class AbsShopFloorView extends LinearLayout {

    protected Context context;
    protected ShopFloor floor;

    public AbsShopFloorView(Context context) {
        this(context, null);
    }
    public AbsShopFloorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public AbsShopFloorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        initView();
        initEvent();
        initData();
    }

    abstract protected void initView();

    abstract protected void initEvent();

    abstract protected void initData();

    public void setData(ShopFloor floor){
        this.floor = floor;
        refreshView();
    }

    public ShopFloor getData(){
        return floor;
    }

    //刷新
    abstract protected void refreshView();

}
