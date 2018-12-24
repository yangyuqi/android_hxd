package com.zhejiang.haoxiadan.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.CartActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.AdviceActivity;
import com.zhejiang.haoxiadan.ui.activity.my.UserCenterActivity;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by KK on 2017/9/11.
 */

public class GlobalTitleMorePopupWindow extends PopupWindow {
    private Context mContext;
    //PopupWindow布局文件中的Button
    private TextView messageBtn, homeBtn,cartBtn,personBtn,feedbackBtn;

    private TYPE type = TYPE.COMMON;

    public enum TYPE{
        COMMON,
        MY_ORDER,//我的订单
    }


    public GlobalTitleMorePopupWindow(Context mContext) {
        this(mContext,TYPE.COMMON);
    }

    public GlobalTitleMorePopupWindow(Context mContext,TYPE type) {
        super(mContext);
        this.mContext = mContext;
        this.type = type;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.popup_window_global_title_more, null);
        //设置布局
        setContentView(mContentView);
        // 设置弹窗的宽度和高度
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置能否获取到焦点
        setFocusable(true);
        //设置PopupWindow进入和退出时的动画效果
//        setAnimationStyle(R.style.popwindow_exit_anim_style);
        setTouchable(true); // 默认是true，设置为false，所有touch事件无响应，而被PopupWindow覆盖的Activity部分会响应点击
        // 设置弹窗外可点击,此时点击PopupWindow外的范围，Popupwindow不会消失
        setOutsideTouchable(true);
        //外部是否可以点击，设置Drawable原因可以参考：http://blog.csdn.net/harvic880925/article/details/49278705
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹窗的布局界面
        initUI();
    }

    /**
     * 初始化弹窗列表
     */
    private void initUI() {
        //获取到按钮
        messageBtn = (TextView) getContentView().findViewById(R.id.tv_msg);
        homeBtn = (TextView) getContentView().findViewById(R.id.tv_home);
        cartBtn = (TextView) getContentView().findViewById(R.id.tv_cart);
        personBtn = (TextView) getContentView().findViewById(R.id.tv_person);
        feedbackBtn = (TextView) getContentView().findViewById(R.id.tv_feedback);

        //根据类型控制显示隐藏
        switch (type){
            case COMMON:
                messageBtn.setVisibility(View.VISIBLE);
                homeBtn.setVisibility(View.VISIBLE);
                cartBtn.setVisibility(View.VISIBLE);
                personBtn.setVisibility(View.VISIBLE);
                feedbackBtn.setVisibility(View.GONE);
                break;
            case MY_ORDER:
                messageBtn.setVisibility(View.VISIBLE);
                homeBtn.setVisibility(View.VISIBLE);
                cartBtn.setVisibility(View.GONE);
                personBtn.setVisibility(View.GONE);
                feedbackBtn.setVisibility(View.VISIBLE);
                break;
        }

        //设置按钮点击事件
        messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //去消息
                Intent[] intents = new Intent[2];
                Intent mainIntent = new Intent(mContext, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intents[0] = mainIntent;
                Intent msgIntent = new Intent(mContext,com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                intents[1] = msgIntent;
                mContext.startActivities(intents);
                dismiss();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                //首页
                EventBus.getDefault().post(Event.GOTO_HOME);
                dismiss();
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = (String) SharedPreferencesUtil.get(mContext, Constants.accessToken,"");
                if(!token.equals("")){
                    Intent intent = new Intent(mContext, CartActivity.class);
                    mContext.startActivity(intent);
                }else{
                    //去登录
                    mContext.startActivity(new Intent(mContext, LoginActivity.class));
                }
                dismiss();
            }
        });
        personBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, UserCenterActivity.class);
                mContext.startActivity(intent);
                dismiss();
            }
        });
        feedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AdviceActivity.class);
                mContext.startActivity(intent);
                dismiss();
            }
        });
    }

    /**
     * 显示弹窗列表界面
     */
    public void show(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //Gravity.BOTTOM设置在view下方，还可以根据location来设置PopupWindowj显示的位置
        showAsDropDown(view);
    }


}
