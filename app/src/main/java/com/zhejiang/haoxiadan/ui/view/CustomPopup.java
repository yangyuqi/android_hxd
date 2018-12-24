package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/8/26.
 */

public class CustomPopup extends PopupWindow {
    //上下文
    private Context mContext;
    // PopupWindow中控件点击事件回调接口
    private IPopuWindowListener mOnClickListener;
    //PopupWindow布局文件中的Button
    private Button alarm_pop_btn ,btn2,btn3;

    private ListView ls ;
    private List<TradeListData> dataList = new ArrayList<>();
    private CommonAdapter<TradeListData> adapter ;

    /**
     * @description 构造方法
     * @author ldm
     * @time  2016/9/30 9:14
     * @param
     */
    public CustomPopup(Context mContext, int width, int height, IPopuWindowListener listener) {
        super(mContext);
        this.mContext = mContext;
        this.mOnClickListener = listener;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.alarm_disopse_pop, null);
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
//        alarm_pop_btn = (Button) getContentView().findViewById(R.id.alarm_pop_btn);
//        btn2 = (Button) getContentView().findViewById(R.id.alarm_pop);
//        btn3 = (Button) getContentView().findViewById(R.id.alarm_child);
//
        goodsRequester = new GoodsRequester();

        ls = (ListView) getContentView().findViewById(R.id.ls);
        adapter = new CommonAdapter<TradeListData>(mContext,dataList,R.layout.home_dialog_ls_item) {
            @Override
            public void convert(ViewHolder helper, TradeListData item) {
                TextView textView = helper.getView(R.id.home_dailog_ls_item_tv);
                textView.setTextColor(mContext.getResources().getColor(R.color.text_black));
                textView.setText(item.getTradeName());
            }
        };

        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (null != mOnClickListener) {
                    mOnClickListener.dispose();
                    mOnClickListener.setName(dataList.get(i).getTradeName());
                }
            }
        });

        initData();

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

    private GetTradeInfoImp imp = new GetTradeInfoImp();
    private class GetTradeInfoImp extends DefaultRequestListener implements GetTradeInfoListener {

        @Override
        protected void onRequestFail() {
        }

        @Override
        public void getTrade(TradeListDataList list) {
            dataList.clear();
            dataList.addAll(list.getTradeList());
            adapter.setData(dataList);
            adapter.notifyDataSetChanged();
        }
    }
    GoodsRequester goodsRequester ;
    private void initData() {
        goodsRequester.getTradeInfo(mContext,imp);
    }
}
