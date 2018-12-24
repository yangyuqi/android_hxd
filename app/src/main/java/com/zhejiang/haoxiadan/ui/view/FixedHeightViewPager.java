package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

/**
 */
public class FixedHeightViewPager extends ViewPager {

    private boolean isCanScroll = false;

    public FixedHeightViewPager(Context context) {
        super(context);
    }

    public FixedHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height)
                height = h;
        }
//
//        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (getChildCount() != 0) {
//
//
//            View child = getChildAt(getcurrentIndex());
//            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//            int h = child.getMeasuredHeight();
//            if (h > height) {
//                height = h;
//            }
//        }
        // Log.i("K_K", "height"+height);
//        Log.i("K_K", "getChildCount()" + getChildCount());


        //利用反射拿当前Fragment的高度
        Fragment currentFragment = null;
        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("mItems");
            f.setAccessible(true);
            List l = (List) f.get(this);
            if (l != null) {
                for (int i = 0; i < l.size(); i++) {

                    Field ffp = l.get(i).getClass().getDeclaredField("position");
                    ffp.setAccessible(true);
                    if (ffp.getInt(l.get(i)) == getCurrentItem()) {
                        Field ff = l.get(i).getClass().getDeclaredField("object");
                        ff.setAccessible(true);
                        currentFragment = Fragment.class.cast(ff.get(l.get(i)));
                        //System.out.println(ff.get(l.get(i)));
                        // System.out.println(Fragment.class.cast(ff.get(l.get(i))));
                        break;
                    }
                }

            } else {
                System.out.println("l = null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (currentFragment != null) {
            View ccc = currentFragment.getView();
            if(ccc !=null){
                height = ccc.getMeasuredHeight();
            }
        }
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }


    //hu获取当前页的序号，三种情况，当前页0，1；  0，当前页1，2；   1当前页，2
    private int getcurrentIndex() {
        if (getChildCount() == 2) {
            if (getCurrentItem() == 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onTouchEvent(ev);
        else
            return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isCanScroll)
            return super.onInterceptTouchEvent(ev);
        else
            return false;
    }

    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

}