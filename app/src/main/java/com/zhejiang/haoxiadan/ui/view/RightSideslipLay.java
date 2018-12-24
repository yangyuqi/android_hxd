package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.model.choseModel.SearchGoodsPropertyBean;
import com.zhejiang.haoxiadan.model.choseModel.SendIfBean;
import com.zhejiang.haoxiadan.util.NumberUtils;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/11/21.
 */

public class RightSideslipLay extends RelativeLayout {
    private Context mCtx;
    private ListView selectList;
    private Button resetBrand;
    private Button okBrand;
    private ImageView backBrand;
    private RelativeLayout mRelateLay;
    private ArrayList<SearchGoodsPropertyBean> list ;
    private LinearLayout ll ;

    private EditText edt_lower ,edt_upper ;

    private List<Boolean> booleanList = new ArrayList<>();

    public RightSideslipLay(Context context , ArrayList<SearchGoodsPropertyBean> goodsPropertyBeanArrayList) {
        super(context);
        mCtx = context;
        list = goodsPropertyBeanArrayList;
        inflateView();
    }



    private void inflateView() {
        View.inflate(getContext(), R.layout.include_right_sideslip_layout, this);

        ll = (LinearLayout) findViewById(R.id.ll_bottom_view);
        okBrand = (Button) findViewById(R.id.fram_ok_but);
        resetBrand = (Button) findViewById(R.id.fram_reset_but);
        edt_lower = (EditText) findViewById(R.id.edt_lower_price);
        edt_upper = (EditText) findViewById(R.id.edt_upper_price);

        if (list.size()>0){
            for (int i = 0 ;i < list.size();i++){
                final int j = i ;
                booleanList.add(false);
                View view = LayoutInflater.from(getContext()).inflate(R.layout.right_side_lay_ls_item,null);
                GridView gridView = (NoScrollGridView) view.findViewById(R.id.gv_use);
                TextView textView = (TextView) view.findViewById(R.id.tv_use_name);
                TextView textView_more = (TextView) view.findViewById(R.id.tv_more);
                textView.setText(list.get(j).getName());
                final InnerAdapter ainner_dapter  ;
                final List<String> m_list = new ArrayList<>();
                String[] com = list.get(j).getValue().split(",");
                for (int m= 0 ;m<com.length ;m++){
                    m_list.add(com[m]);
                }
                ainner_dapter = new InnerAdapter(m_list);
                gridView.setAdapter(ainner_dapter);
                if (m_list.size()<=6){
                    textView_more.setVisibility(GONE);
                }else {
                    textView_more.setVisibility(VISIBLE);
                }

                ll.addView(view);

                textView_more.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (m_list.size()>6){

                            if (booleanList.get(j)) {
                                booleanList.set(j,false);
                                ainner_dapter.setShow(true);
                            }else {
                                booleanList.set(j,true);
                                ainner_dapter.setShow(false);
                            }
                        }

                    }
                });
            }
        }


        okBrand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> test = new ArrayList<String>();
                 if (getData().size()>0){
                    for (int i = 0 ;i<getData().size();i++){
                        List<Boolean> list = getData().get(i).booleanList;
                        for (int j = 0 ;j<list.size();j++){
                            if (list.get(j)){
                                for (int m =0 ; m<getData().get(i).useData.size();m++){
                                    if (m==j){
                                        test.add(getData().get(i).useData.get(m));
                                    }
                                }
                            }
                        }
                    }
                }

                String builder = new String();
                if (test.size()>0){
                    builder = test.get(0);
                }
                for (int q = 1 ; q<test.size();q++){
                    if (test.size()>1){
                        builder = builder+","+test.get(q);
                    }
                }

                if (edt_upper.getText().toString().equals("")&&!edt_lower.getText().toString().equals("")){
                    ToastUtil.show(getContext(),"请输入最高价");
                    return;
                }
                if (!edt_upper.getText().toString().equals("")&&edt_lower.getText().toString().equals("")){
                    ToastUtil.show(getContext(),"请输入最低价");
                    return;
                }

                if (NumberUtils.getIntFromString(edt_upper.getText().toString())<NumberUtils.getIntFromString(edt_lower.getText().toString())){
                    ToastUtil.show(getContext(),"输入最高价不能小于最低价");
                    return;
                }

                SendIfBean ifBean = new SendIfBean("close",builder,edt_lower.getText().toString(),edt_upper.getText().toString());
                EventBus.getDefault().post(ifBean);
            }
        });


        resetBrand.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getData().size()>0){
                    for (int i = 0 ;i<getData().size();i++){
                        List<Boolean> list = getData().get(i).booleanList;
                        for (int j = 0 ;j<list.size();j++){
                            if (list.get(j)){
                               getData().get(i).booleanList.set(j,false);
                            }
                            getData().get(i).notifyDataSetChanged();
                        }
                    }
                }
                edt_lower.setText(""); edt_upper.setText("");
                SendIfBean ifBean = new SendIfBean("unclose","",edt_lower.getText().toString(),edt_upper.getText().toString());
                EventBus.getDefault().post(ifBean);
            }
        });

    }




    public List<InnerAdapter> getData(){
        List<InnerAdapter> adapterList = new ArrayList<>();
        int  count = ll.getChildCount();
        for (int i = 0 ;i<count ;i++){
            View child_view = ll.getChildAt(i);
            if (child_view instanceof LinearLayout){
                int j = ((LinearLayout) child_view).getChildCount();
                for (int m =0 ; m<j;m++){
                    View child_child_view = ((LinearLayout) child_view).getChildAt(m);
                    if (child_child_view instanceof NoScrollGridView){
                        GridView gridView = (GridView) child_child_view;
                        InnerAdapter ainner_dapter = (InnerAdapter) gridView.getAdapter();
                        adapterList.add(ainner_dapter);
                    }
                }
            }
        }
        return adapterList ;
    }

    public class InnerAdapter extends BaseAdapter{
        List<String> useData ;
        boolean isShow = false;
        List<Boolean> booleanList = new ArrayList<>();
        public InnerAdapter(List<String> useData) {
            this.useData = useData ;
            if (useData.size()>0){
                for (int i = 0 ; i<useData.size();i++){
                    booleanList.add(false);
                }
            }
        }

        public void setShow(boolean show){
            isShow = show ;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (isShow){
                return useData.size() ;
            }else {
                if (useData.size()>=6){
                    return 6 ;
                }
            }
            return useData.size();
        }

        @Override
        public Object getItem(int i) {
            return useData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            view = LayoutInflater.from(getContext()).inflate(R.layout.right_side_lay_gv_item,null);
            final TextView tv = (TextView) view.findViewById(R.id.tv_gv_name);
            LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll_round);
            if (useData.get(i)!=null&&!useData.get(i).equals("")) {
                tv.setText(useData.get(i));
            }else {
                ll.setVisibility(GONE);
            }

            if (booleanList.get(i)){
                tv.setBackgroundResource(R.drawable.right_tv_side_bg_select);
                tv.setTextColor(getResources().getColor(R.color.main_red));
            }else {
                tv.setBackgroundResource(R.drawable.right_tv_side_bg_normal);
                tv.setTextColor(getResources().getColor(R.color.text_black));
            }

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (booleanList.get(i)){
                        booleanList.set(i,false);
                        tv.setBackgroundResource(R.drawable.right_tv_side_bg_normal);
                        tv.setTextColor(getResources().getColor(R.color.text_black));
                    }else {
                        booleanList.set(i,true);
                        tv.setBackgroundResource(R.drawable.right_tv_side_bg_select);
                        tv.setTextColor(getResources().getColor(R.color.main_red));
                    }
                }
            });

            return view;
        }

    }

}