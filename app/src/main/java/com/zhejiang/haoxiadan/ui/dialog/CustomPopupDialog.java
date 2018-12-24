package com.zhejiang.haoxiadan.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.view.CustomPopup;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class CustomPopupDialog  extends PopupWindow {
    //上下文
    private Context mContext;
    // PopupWindow中控件点击事件回调接口
    private CustomPopup.IPopuWindowListener mOnClickListener;
    //PopupWindow布局文件中的Button
    private TextView alarm_pop_btn ,btn2,btn3;

    private String type ;
    /**
     * @description 构造方法
     * @author ldm
     * @time  2016/9/30 9:14
     * @param
     */
    public CustomPopupDialog(Context mContext, int width, int height, CustomPopup.IPopuWindowListener listener) {
        super(mContext);
        this.mContext = mContext;
        this.mOnClickListener = listener;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.custom_pop_dialog, null);
        //设置布局
        setContentView(mContentView);
        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);
        //设置能否获取到焦点
        setFocusable(false);
        //设置PopupWindow进入和退出时的动画效果
        setAnimationStyle(R.style.popwindow_exit_anim_style);
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
        alarm_pop_btn = (TextView) getContentView().findViewById(R.id.alarm_pop_btn);
        btn2 = (TextView) getContentView().findViewById(R.id.alarm_pop);
        btn3 = (TextView) getContentView().findViewById(R.id.alarm_child);

        if (type!=null){
            btn3.setVisibility(View.VISIBLE);
        }

        //设置按钮点击事件
        alarm_pop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnClickListener) {
                    mOnClickListener.dispose();
                    mOnClickListener.setName(alarm_pop_btn.getText().toString());
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnClickListener) {
                    mOnClickListener.dispose();
                    mOnClickListener.setName(btn2.getText().toString());
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != mOnClickListener) {
                    mOnClickListener.dispose();
                    mOnClickListener.setName(btn3.getText().toString());
                }
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

    /**
     * @param
     * @author ldm
     * @description 点击事件回调处理接口
     * @time 2016/7/29 15:30
     */
    public interface IPopuWindowListener {
        void dispose();
        void setName(String name);
    }

    public CustomPopupDialog(Context mContext, int width, int height, CustomPopup.IPopuWindowListener listener,String type) {
        super(mContext);
        this.mContext = mContext;
        this.mOnClickListener = listener;
        this.type = type ;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.custom_pop_dialog, null);
        //设置布局
        setContentView(mContentView);
        // 设置弹窗的宽度和高度
        setWidth(width);
        setHeight(height);
        //设置能否获取到焦点
        setFocusable(false);
        //设置PopupWindow进入和退出时的动画效果
        setAnimationStyle(R.style.popwindow_exit_anim_style);
        setTouchable(true); // 默认是true，设置为false，所有touch事件无响应，而被PopupWindow覆盖的Activity部分会响应点击
        // 设置弹窗外可点击,此时点击PopupWindow外的范围，Popupwindow不会消失
        setOutsideTouchable(true);
        //外部是否可以点击，设置Drawable原因可以参考：http://blog.csdn.net/harvic880925/article/details/49278705
        setBackgroundDrawable(new BitmapDrawable());
        // 设置弹窗的布局界面
        initUI();
    }


}
