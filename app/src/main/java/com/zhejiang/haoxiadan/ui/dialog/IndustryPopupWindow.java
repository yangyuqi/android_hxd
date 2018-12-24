package com.zhejiang.haoxiadan.ui.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.GsonDataParser;
import com.zhejiang.haoxiadan.business.request.my.InsertUserTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.my.ValueAddRequester;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.ui.adapter.category.IndustryAdapter;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类里点击箭头，编辑行业的弹出框
 * Created by KK on 2017/11/22.
 */

public class IndustryPopupWindow extends PopupWindow {
    private Context mContext;

    private TextView mainIndustryTv;
    private TextView mainEditTv;
    private TextView secondIndustryTv;
    private TextView secondEditTv;
    private GridView gridView;

    private IndustryAdapter adapter;
    private List<Industry> industries = new ArrayList<>();
    private List<Industry> firstFocus = new ArrayList<>();
    private List<Industry> secondFocus = new ArrayList<>();

    //用于备份
    private GsonDataParser mDataParser = new GsonDataParser();
    private String oldFirstStr = "";
    private String oldSecondStr = "";

    //编辑状态，0无编辑，1编辑主采购行业，2编辑还关注行业
    private int editStatus = 0;


    private ValueAddRequester valueAddRequester;
    private InsertUserTradeInfoListenerImpl insertUserTradeInfoListener;
    private class InsertUserTradeInfoListenerImpl extends DefaultRequestListener implements InsertUserTradeInfoListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onInsertUserTradeInfoSuccess() {

        }
    }

    public IndustryPopupWindow(Context mContext,List<Industry> industries,List<Industry> firstFocus,List<Industry> secondFocus) {
        super(mContext);
        this.mContext = mContext;
        this.industries = industries;
        this.firstFocus = firstFocus;
        this.secondFocus = secondFocus;
        //获取布局文件
        View mContentView = LayoutInflater.from(mContext).inflate(R.layout.popup_window_industry, null);
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
        init(mContentView);
    }

    /**
     * 初始化弹窗
     */
    private void init(View view) {
        mainIndustryTv = (TextView) view.findViewById(R.id.tv_main_buy_industry);
        mainEditTv = (TextView) view.findViewById(R.id.tv_main_edit);
        secondIndustryTv = (TextView) view.findViewById(R.id.tv_second_buy_industry);
        secondEditTv = (TextView) view.findViewById(R.id.tv_second_edit);
        gridView = (GridView) view.findViewById(R.id.gv_industry);

        valueAddRequester = new ValueAddRequester();
        insertUserTradeInfoListener = new InsertUserTradeInfoListenerImpl();

        initData();

        initEvent();
    }

    public void initData(){
        adapter = new IndustryAdapter(mContext,industries);
        gridView.setAdapter(adapter);
    }

    private void initEvent(){
        mainEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editStatus == 0){
                    editStatus = 1;
                    mainEditTv.setText(R.string.btn_save);
                    secondEditTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                    secondEditTv.setClickable(false);
                    adapter.setEdit(true);
                    for(int i=0;i<industries.size();i++){
                        if(secondFocus.contains(industries.get(i))){
                            industries.get(i).setDisable(true);
                        }else{
                            industries.get(i).setDisable(false);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    //备份
                    oldFirstStr = mDataParser.toDataStr(firstFocus);
                }else if(editStatus == 1){
                    if(firstFocus.size()==0){
                        ToastUtil.show(mContext,R.string.label_please_select_industry);
                        return;
                    }else{
                        editStatus = 0;
                        mainEditTv.setText(R.string.btn_edit);
                        secondEditTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
                        secondEditTv.setClickable(true);
                        adapter.setEdit(false);
                        adapter.notifyDataSetChanged();

                        //保存
                        List<String> ids = new ArrayList<>();
                        for(Industry industry:firstFocus){
                            ids.add(industry.getId());
                        }
                        valueAddRequester.insertUserTradeInfo(mContext,ids,1,insertUserTradeInfoListener);
                    }
                }
            }
        });

        secondEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editStatus == 0){
                    editStatus = 2;
                    secondEditTv.setText(R.string.btn_save);
                    mainEditTv.setTextColor(mContext.getResources().getColor(R.color.text_gray));
                    mainEditTv.setClickable(false);
                    adapter.setEdit(true);
                    for(int i=0;i<industries.size();i++){
                        if(firstFocus.contains(industries.get(i))){
                            industries.get(i).setDisable(true);
                        }else{
                            industries.get(i).setDisable(false);
                        }
                    }
                    adapter.notifyDataSetChanged();

                    //备份
                    oldSecondStr = mDataParser.toDataStr(secondFocus);
                }else if(editStatus == 2){
                    if(secondFocus.size()==0){
                        ToastUtil.show(mContext,R.string.label_please_select_industry);
                        return;
                    }else{
                        editStatus = 0;
                        secondEditTv.setText(R.string.btn_edit);
                        mainEditTv.setTextColor(mContext.getResources().getColor(R.color.text_red));
                        mainEditTv.setClickable(true);
                        adapter.setEdit(false);
                        adapter.notifyDataSetChanged();


                        //保存
                        List<String> ids = new ArrayList<>();
                        for(Industry industry:secondFocus){
                            ids.add(industry.getId());
                        }
                        valueAddRequester.insertUserTradeInfo(mContext,ids,2,insertUserTradeInfoListener);
                    }
                }
            }
        });

        adapter.setListener(new IndustryAdapter.IndustryClickListener() {
            @Override
            public void onIndustryClick(int position) {
                if(!industries.get(position).isChoose()){
                    switch (editStatus){
                        case 1:
                            if(firstFocus!=null && firstFocus.size()>0){//主行业最多1个
                                ToastUtil.show(mContext,R.string.tip_main_buy_industry);
                                return;
                            }
                            if(!firstFocus.contains(industries.get(position))){
                                industries.get(position).setChoose(true);
                                firstFocus.add(industries.get(position));
                            }
                            break;
                        case 2:
                            if(secondFocus!=null && firstFocus.size()>2){//次行业最多3个
                                ToastUtil.show(mContext,R.string.tip_second_buy_industry);
                                return;
                            }
                            if(!secondFocus.contains(industries.get(position))){
                                industries.get(position).setChoose(true);
                                secondFocus.add(industries.get(position));
                            }
                            break;
                    }
                }else{
                    switch (editStatus){
                        case 1:
                            if(firstFocus.contains(industries.get(position))){
                                industries.get(position).setChoose(false);
                                firstFocus.remove(industries.get(position));
                            }
                            break;
                        case 2:
                            if(secondFocus.contains(industries.get(position))){
                                industries.get(position).setChoose(false);
                                secondFocus.remove(industries.get(position));
                            }
                            break;
                    }
                }

                refreshIndustry();
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);

        refreshIndustry();
    }

    @Override
    public void dismiss() {
        //恢复备份
        switch (editStatus){
            case 1:
                firstFocus = mDataParser.parseList(oldFirstStr,Industry.class);
                editStatus = 0;
                break;
            case 2:
                secondFocus = mDataParser.parseList(oldSecondStr,Industry.class);
                editStatus = 0;
                break;
        }
        for(Industry industry:industries){
            industry.setChoose(false);
        }

        super.dismiss();
    }

    private void refreshIndustry() {
        if(firstFocus != null && firstFocus.size()>0){
            for(int i = 0;i<firstFocus.size();i++){
                for(int j = 0;j<industries.size();j++){
                    if(firstFocus.get(i).getId().equals(industries.get(j).getId())){
                        industries.get(j).setChoose(true);
                        firstFocus.remove(i);
                        firstFocus.add(i,industries.get(j));
                    }
                }
            }
        }

        if(secondFocus != null && secondFocus.size()>0){
            for(int i = 0;i<secondFocus.size();i++){
                for(int j = 0;j<industries.size();j++){
                    if(secondFocus.get(i).getId().equals(industries.get(j).getId())){
                        industries.get(j).setChoose(true);
                        secondFocus.remove(i);
                        secondFocus.add(i,industries.get(j));
                    }
                }
            }
        }

        String firstStr = "";
        for(Industry industry:firstFocus){
            firstStr = firstStr +","+ industry.getTitle();
        }
        if(!"".equals(firstStr)){
            firstStr = firstStr.substring(1,firstStr.length());
            mainIndustryTv.setText(firstStr);
        }else{
            mainIndustryTv.setText(R.string.label_please_select_industry);
        }
        String secondStr = "";
        for(Industry industry:secondFocus){
            secondStr = secondStr +","+ industry.getTitle();
        }
        if(!"".equals(secondStr)){
            secondStr = secondStr.substring(1,secondStr.length());
            secondIndustryTv.setText(secondStr);
        }else{
            secondIndustryTv.setText(R.string.label_please_select_industry);
        }
        adapter.notifyDataSetChanged();
    }
}
