package com.zhejiang.haoxiadan.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.zhejiang.haoxiadan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: special
 * Date: 13-12-10
 * Time: 下午10:44
 * Mail: specialcyci@gmail.com
 */
public class ResideMenu extends LinearLayout implements GestureDetector.OnGestureListener{

    private AnimatorSet up_activity;
    private AnimatorSet down_activity;

    private AnimatorSet up_menu;
    private AnimatorSet down_menu;

    private AnimatorSet up_mask;
    private AnimatorSet down_mask;

    //蒙版
    private View maskView = new View(getContext());

    /** the activity that view attach to */
    private Activity activity;
    /** the decorview of the activity    */
    private ViewGroup view_decor;
    /** the viewgroup of the activity    */
    private ViewGroup view_activity;
    /** the flag of menu open status     */
    private boolean isOpened;
    private GestureDetector gestureDetector;
    /** the view which don't want to intercept touch event */
    private List<View> ignoredViews;
    private DisplayMetrics displayMetrics = new DisplayMetrics();
    private OnMenuListener menuListener;

    public ResideMenu(Context context) {
        super(context);
    }

    private View view;
    public void setView(View view){
        this.view = view;
        removeAllViews();
        addView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int)getResources().getDimension(R.dimen.drawer_width);
        view.setLayoutParams(layoutParams);
    }

    /**
     * use the method to set up the activity which residemenu need to show;
     *
     * @param activity
     */
    public void attachToActivity(Activity activity){
        initValue(activity);
        buildAnimationSet();
    }

    private void initValue(Activity activity){
        this.activity   = activity;
        gestureDetector = new GestureDetector(this);
        ignoredViews    = new ArrayList<View>();
        view_decor      = (ViewGroup)activity.getWindow().getDecorView();
        view_activity   = (ViewGroup) view_decor.getChildAt(0);
    }

    /**
     * if you need to do something on the action of closing or opening
     * menu, set the listener here.
     *
     * @return
     */
    public void setMenuListener(OnMenuListener menuListener) {
        this.menuListener = menuListener;
    }

    /**
     * we need the call the method before the menu show, because the
     * padding of activity can't get at the moment of onCreateView();
     */
    private void setViewPadding(){
        this.setPadding(view_activity.getPaddingLeft(),
                view_activity.getPaddingTop(),
                view_activity.getPaddingRight(),
                view_activity.getPaddingBottom());
    }

    /**
     * show the reside menu;
     */
    public void openMenu(){
        if(!isOpened){
            isOpened = true;
            showOpenMenuRelative();
        }
    }

    /**
     * close the reslide menu;
     */
    public void closeMenu(){
        if(isOpened){
            isOpened = false;
            up_activity.start();
            up_menu.start();
            up_mask.start();
        }
    }

    /**
     * return the flag of menu status;
     *
     * @return
     */
    public boolean isOpened() {
        return isOpened;
    }

    /**
     *  call the method relative to open menu;
     */
    private void showOpenMenuRelative(){
        setViewPadding();
        down_menu.start();
        down_activity.start();
        // remove self if has not remove
        if (getParent() != null) view_decor.removeView(this);
        view_decor.addView(this, 0);

        maskView.setBackgroundColor(getResources().getColor(R.color.text_black));
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        maskView.setLayoutParams(layoutParams);
        maskView.setAlpha(0);
        maskView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        view_decor.addView(maskView);
        down_mask.start();
    }

    private Animator.AnimatorListener animationListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (isOpened){
                if (menuListener != null)
                    menuListener.openMenu();
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            // reset the view;
            if(!isOpened){
                view_decor.removeView(ResideMenu.this);
                view_decor.removeView(maskView);
                if (menuListener != null)
                    menuListener.closeMenu();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private void buildAnimationSet(){
        up_activity = buildDownAnimation(view_activity,0f,0f,1);
        down_activity = buildDownAnimation(view_activity,getResources().getDimension(R.dimen.drawer_width),0f,1);

        up_menu = buildMenuUpAnimation(ResideMenu.this,0f,- getResources().getDimension(R.dimen.drawer_width));
        down_menu = buildMenuUpAnimation(ResideMenu.this,- getResources().getDimension(R.dimen.drawer_width),0f);

        up_mask = buildDownAnimation(maskView,0f,0f,0);
        down_mask = buildDownAnimation(maskView,getResources().getDimension(R.dimen.drawer_width),0f,0.5f);

        up_activity.addListener(animationListener);
    }

    private AnimatorSet buildDownAnimation(View target, float targetTransX, float targetTransY, float alpha){

        // set the pivotX and pivotY to scale;
//        int pivotX = (int) (getScreenWidth() * 1.5);
//        int pivotY = (int) (getScreenHeight() * 0.5);
//        ViewHelper.setPivotX(target, pivotX);
//        ViewHelper.setPivotY(target, pivotY);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.playTogether(
                ObjectAnimator.ofFloat(target, "translationX", targetTransX),
                ObjectAnimator.ofFloat(target, "translationY", targetTransY),
                ObjectAnimator.ofFloat(target,"alpha",alpha)
        );

        scaleDown.setInterpolator(AnimationUtils.loadInterpolator(activity,
                android.R.anim.decelerate_interpolator));
        scaleDown.setDuration(250);
        return scaleDown;
    }

    private AnimatorSet buildMenuUpAnimation(View target, float fromX, float targetX){

        AnimatorSet scaleUp = new AnimatorSet();
        scaleUp.playTogether(
                ObjectAnimator.ofFloat(target, "translationX", fromX,targetX)
        );

        scaleUp.setDuration(250);
        return scaleUp;
    }

    /**
     * if there ware some view you don't want reside menu
     * to intercept their touch event,you can use the method
     * to set.
     *
     * @param v
     */
    public void addIgnoredView(View v){
        ignoredViews.add(v);
    }

    /**
     * remove the view from ignored view list;
     * @param v
     */
    public void removeIgnoredView(View v){
        ignoredViews.remove(v);
    }

    /**
     * clear the ignored view list;
     */
    public void clearIgnoredViewList(){
        ignoredViews.clear();
    }

    /**
     * if the motion evnent was relative to the view
     * which in ignored view list,return true;
     *
     * @param ev
     * @return
     */
    private boolean isInIgnoredView(MotionEvent ev) {
        Rect rect = new Rect();
        for (View v : ignoredViews) {
            v.getGlobalVisibleRect(rect);
            if (rect.contains((int) ev.getX(), (int) ev.getY()))
                return true;
        }
        return false;
    }

    //--------------------------------------------------------------------------
    //
    //  GestureListener
    //
    //--------------------------------------------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {

        if(isInIgnoredView(motionEvent) || isInIgnoredView(motionEvent2))
            return false;

        int distanceX    = (int) (motionEvent2.getX() - motionEvent.getX());
        int distanceY    = (int) (motionEvent2.getY() - motionEvent.getY());
        int screenWidth  = (int) getScreenWidth();

        if(Math.abs(distanceY) > screenWidth * 0.3)
            return false;

        if(Math.abs(distanceX) > screenWidth * 0.3){
            if(distanceX > 0 && !isOpened ){
                // from left to right;
                openMenu();
            }else if(distanceX < 0 && isOpened){
                // from right th left;
                closeMenu();
            }
        }

        return false;
    }

    public int getScreenHeight(){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public int getScreenWidth(){
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public interface OnMenuListener{

        /**
         * the method will call on the finished time of opening menu's animation.
         */
        public void openMenu();

        /**
         * the method will call on the finished time of closing menu's animation  .
         */
        public void closeMenu();
    }

}
