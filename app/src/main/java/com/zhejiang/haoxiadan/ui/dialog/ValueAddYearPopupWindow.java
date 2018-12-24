package com.zhejiang.haoxiadan.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.YearFee;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.YearFeeAdapter;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KK on 2017/11/14.
 */

public class ValueAddYearPopupWindow extends PopupWindow {
    private Context mContext;
    //PopupWindow布局文件中的Button
    private ListView listView;
    private List<YearFee> yearFees;
    private YearFeeAdapter adapter;
    private YearSelectListener yearSelectListener;


    public interface YearSelectListener{
        void onYearSelect(YearFee yearFee);
    }


    public ValueAddYearPopupWindow(Context mContext) {
        super(mContext);
        this.mContext = mContext;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.popup_window_value_add_year, null);
        listView = (ListView)mContentView.findViewById(R.id.lv_year);
        //设置布局
        setContentView(mContentView);
        // 设置弹窗的宽度和高度
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
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
        yearFees = new ArrayList<>();
        adapter = new YearFeeAdapter(mContext,yearFees);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if(yearSelectListener != null){
                    yearSelectListener.onYearSelect(yearFees.get(position));
                }
            }
        });
    }

    public void setYearFees(List<YearFee> yearFees) {
        if(this.yearFees != null){
            this.yearFees.clear();
            this.yearFees.addAll(yearFees);
        }
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    public void setYearSelectListener(YearSelectListener yearSelectListener) {
        this.yearSelectListener = yearSelectListener;
    }
}
