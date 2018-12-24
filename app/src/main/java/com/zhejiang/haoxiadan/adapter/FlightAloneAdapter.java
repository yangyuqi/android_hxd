package com.zhejiang.haoxiadan.adapter;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemBean;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemData;
import com.zhejiang.haoxiadan.model.requestData.in.PostTiredPriceData;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;
import com.zhejiang.haoxiadan.third.jiguang.chat.adapter.TextWatcherAdapter;
import com.zhejiang.haoxiadan.ui.view.ExtendedEditText;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import static com.zhejiang.haoxiadan.MyApplication.context;

/**
 * Created by qiuweiyu on 2017/9/26.
 */

public class FlightAloneAdapter extends BaseAdapter{

    private List<FlightLsItemBean> mdata = new ArrayList<>();
    private List<FlightLsItemData> new_data = new ArrayList<>();
    private Context mContext ;
    private AllCountInterface anInterface ;
    private PostTiredPriceData tiredPriceData;
    private TabLayout goodsDetailsTl ;
    private List<GetGoodsIntData> all_data ;
    private TextView tv_tv_Price ,tvGetNum ,tvGetPrice ;
    private int allCount = 0 ,oldCurrent = 0 ,goodsNumType;

    private boolean ischange=false;
    private Gson gson ;

    private String goodsType ;
    private int goodsLimit ;

    public FlightAloneAdapter(List<FlightLsItemBean> mdata, Context mContext, PostTiredPriceData tiredPriceData, TabLayout goodsDetailsTl, List<GetGoodsIntData> all_data, TextView tv_tv_Price, TextView tvGetNum, TextView tvGetPrice,List<FlightLsItemData> new_data ,String goodsType ,int goodslimit,int goodsNumType) {
        this.mdata = mdata;
        this.mContext = mContext;
        this.tiredPriceData = tiredPriceData;
        this.goodsDetailsTl = goodsDetailsTl;
        this.all_data = all_data;
        this.tv_tv_Price = tv_tv_Price;
        this.tvGetNum = tvGetNum;
        this.tvGetPrice = tvGetPrice;
        this.new_data = new_data ;
        this.goodsType = goodsType;
        this.goodsNumType = goodsNumType ;
        gson = new Gson();
        for (FlightLsItemData itemData : new_data){
            for (FlightLsItemBean bean :itemData.getSpecpropertyList()){
                allCount = allCount+bean.getCount();
            }
        }

        if (goodsType.equals("0")){
            this.goodsLimit = goodslimit ;
        }

    }

    public void setData(List<FlightLsItemBean> data ){
        mdata = data ;
        notifyDataSetChanged();
    }

    public void getAllData(AllCountInterface allCountInterface){
        anInterface = allCountInterface ;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int i) {
        return mdata.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final FlightLsItemBean item = mdata.get(position);

        final ViewHolder holder  ;
        if (view==null){
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.flight_alone_ls_item,null);
            holder.edt_num = (ExtendedEditText) view.findViewById(R.id.edt_num);
            holder.iv_add = (ImageView) view.findViewById(R.id.iv_add);
            holder.iv_reduce = (ImageView) view.findViewById(R.id.iv_reduce);
            holder.tv_num = (TextView) view.findViewById(R.id.tv_all_num);
            holder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            holder.tv_style = (TextView) view.findViewById(R.id.tv_style);

            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tv_style.setText(item.getSpecpropertyName());
        if (item.getSpecpropertyInventCount()!=null) {
                holder.tv_num.setText("库存：" + NumberUtils.getIntFromString(item.getSpecpropertyInventCount()));
        }

        if (allCount == 0 ){
            holder.edt_num.setText(""+item.getCount());
            holder.tv_price.setText(mContext.getString(R.string.label_money)+ NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size()-1).getPrice()));
            item.setPrice(tiredPriceData.getTired_data_list().get(tiredPriceData.getTired_data_list().size()-1).getPrice());
        }else {
            holder.edt_num.setText(String.valueOf(item.getCount()));
            holder.tv_price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getPrice()));
            item.setPrice(item.getPrice());
        }

        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (NumberUtils.getIntFromString(item.getSpecpropertySmallCount()) <= NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {

                    item.setColor(goodsDetailsTl.getTabAt(goodsDetailsTl.getSelectedTabPosition()).getText().toString());
                    for (GetGoodsIntData intData : all_data) {
                        if (intData.getSpecpropertyName().equals(item.getColor())) {
                            item.setOuterId(intData.getSpecpropertyId());
                        }
                    }
                    int current;

                if (goodsNumType==0){
                    current=NumberUtils.getIntFromString(holder.edt_num.getText().toString())+1;
                    allCount=allCount+current;
                    holder.edt_num.setText(String.valueOf(current));
                    item.setCount(current);
                    for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
                        String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                        if (allCount >= NumberUtils.getIntFromString(sArray[0]) && allCount <= NumberUtils.getIntFromString(sArray[1])) {
                            tv_tv_Price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                            item.setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                            initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), mdata);
                            notifyDataSetChanged();
                        }
                    }
                    return;
                }

                    if (item.getSpecpropertySmallCount() != null) {
                        if (NumberUtils.getIntFromString(holder.edt_num.getText().toString()) == 0) {
                            if (item.getSpecpropertySmallCount().equals("")) {
                                current = NumberUtils.getIntFromString("1");
                            } else {
                                ToastUtil.show(context, "最低起订" + item.getSpecpropertySmallCount() + "件");
                                current = NumberUtils.getIntFromString(item.getSpecpropertySmallCount());
                            }
                            allCount = allCount + current;
                            holder.edt_num.setText(String.valueOf(current));
                            item.setCount(current);
                        } else {
                            current = Integer.parseInt(holder.edt_num.getText().toString()) + 1;
                            //现货
                            if (item.getSpecpropertyInventCount() != null) {
                                if (!item.getSpecpropertyInventCount().equals("")) {
                                    if (NumberUtils.getIntFromString(item.getSpecpropertyInventCount()) > 0) { //现货
                                        if (current <= NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {
                                            allCount = allCount + 1;
                                            holder.edt_num.setText(String.valueOf(current));
                                            item.setCount(current);
                                        } else {
                                            ToastUtil.show(context, mContext.getString(R.string.more_other_num));
                                            holder.edt_num.setText(String.valueOf(item.getSpecpropertyInventCount()));
                                            item.setCount(NumberUtils.getIntFromString(holder.edt_num.getText().toString()));
                                        }
                                    } else { //期货 后台返回数量小于0
                                        allCount = allCount + 1;
                                        holder.edt_num.setText(String.valueOf(current));
                                        item.setCount(current);
                                    }
                                }
                            } else { //期货
                                allCount = allCount + 1;
                                holder.edt_num.setText(String.valueOf(current));
                                item.setCount(current);
                            }
                        }
                    }else {
                        if (NumberUtils.getIntFromString(holder.edt_num.getText().toString()) == 0) {
                            current = NumberUtils.getIntFromString("1");
                            allCount = allCount + current;
                            holder.edt_num.setText(String.valueOf(current));
                            item.setCount(current);
                        }else {
                            current = Integer.parseInt(holder.edt_num.getText().toString()) + 1;
                            if (item.getSpecpropertyInventCount() != null) {
                                if (NumberUtils.getIntFromString(item.getSpecpropertyInventCount()) > 0) {
                                    if (current <= NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {
                                        allCount = allCount + 1;
                                        holder.edt_num.setText(String.valueOf(current));
                                        item.setCount(current);
                                    } else {
                                        ToastUtil.show(context, mContext.getString(R.string.more_other_num));
                                        holder.edt_num.setText(String.valueOf(item.getSpecpropertyInventCount()));
                                        item.setCount(NumberUtils.getIntFromString(holder.edt_num.getText().toString()));
                                    }
                                }
                            }else { //期货
                                allCount = allCount + 1;
                                holder.edt_num.setText(String.valueOf(current));
                                item.setCount(current);
                            }

                        }
                    }

                    for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
                        String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                        if (allCount >= NumberUtils.getIntFromString(sArray[0]) && allCount <= NumberUtils.getIntFromString(sArray[1])) {
                            tv_tv_Price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                            item.setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                            initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), mdata);
                            notifyDataSetChanged();
                        }
                    }

//                } else {
//                    ToastUtil.show(context, "此商品最小购买量大于库存,不能购买");
//                }



            }
        });


        holder.iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                if (NumberUtils.getIntFromString(item.getSpecpropertySmallCount())<=NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {

                    item.setColor(goodsDetailsTl.getTabAt(goodsDetailsTl.getSelectedTabPosition()).getText().toString());
                    for (GetGoodsIntData intData : all_data) {
                        if (intData.getSpecpropertyName().equals(item.getColor())) {
                            item.setOuterId(intData.getSpecpropertyId());
                        }
                    }
                    int current = Integer.parseInt(holder.edt_num.getText().toString()) - 1;

                    if (goodsNumType==0){
                        if (current>0) {
                            allCount = allCount - current;
                            holder.edt_num.setText(String.valueOf(current));
                            item.setCount(current);
                            for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
                                String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                                if (allCount >= NumberUtils.getIntFromString(sArray[0]) && allCount <= NumberUtils.getIntFromString(sArray[1])) {
                                    tv_tv_Price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                                    item.setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                                    initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), mdata);
                                    notifyDataSetChanged();
                                }
                            }
                            return;
                        }
                    }

                    if (current > 0) {
                        if (item.getSpecpropertySmallCount() != null) {
                            if (current < NumberUtils.getIntFromString(item.getSpecpropertySmallCount())) {
                                holder.edt_num.setText("0");
                                item.setCount(0);
                                if (allCount > 0) {
                                    allCount = allCount - NumberUtils.getIntFromString(item.getSpecpropertySmallCount());
                                }
                            } else {
                                holder.edt_num.setText( String.valueOf(current));
                                item.setCount(current);
                                if (allCount > 0) {
                                    allCount = allCount - 1;
                                }
                            }
                        }else {
                            if (current<=0){
                                holder.edt_num.setText("0");
                                item.setCount(0);
                            }else {
                                holder.edt_num.setText( String.valueOf(current));
                                item.setCount(current);
                                if (allCount > 0) {
                                    allCount = allCount - 1;
                                }
                            }
                        }
                    } else {
                        holder.edt_num.setText( String.valueOf(0));
                        item.setCount(0);
                        if (allCount > 0) {
                            allCount = allCount - 1;
                        }
                    }
                    for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
                        String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                        if (allCount >= NumberUtils.getIntFromString(sArray[0]) && allCount <= NumberUtils.getIntFromString(sArray[1])) {
                            tv_tv_Price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                            item.setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                            initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), mdata);
                            notifyDataSetChanged();
                        }
                    }

//                }else {
//                    ToastUtil.show(context,"此商品最小购买量大于库存,不能购买");
//                }

            }
        });



        final int oldCurrent = NumberUtils.getIntFromString(holder.edt_num.getText().toString());
        holder.edt_num.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    if (NumberUtils.getIntFromString(item.getSpecpropertySmallCount()) <= NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {
                        item.setColor(goodsDetailsTl.getTabAt(goodsDetailsTl.getSelectedTabPosition()).getText().toString());
                        for (GetGoodsIntData intData : all_data) {
                            if (intData.getSpecpropertyName().equals(item.getColor())) {
                                item.setOuterId(intData.getSpecpropertyId());
                            }
                        }

                        int current = 0;
                        if (textView.getText().toString().startsWith("0") && textView.getText().toString().length() > 1) {
                            current = NumberUtils.getIntFromString(textView.getText().toString().substring(1));
                        } else {
                            current = NumberUtils.getIntFromString(textView.getText().toString());
                        }

                        if (item.getSpecpropertySmallCount() != null) {
                            if (current < NumberUtils.getIntFromString(item.getSpecpropertySmallCount())) {
                                if (!item.getSpecpropertySmallCount().equals("")) {
                                    ToastUtil.show(context, "最低起订" + item.getSpecpropertySmallCount() + "件");
                                    current = NumberUtils.getIntFromString(item.getSpecpropertySmallCount());
                                }else {
                                    current = NumberUtils.getIntFromString("1");
                                }
                            }
                        }

                        if (item.getSpecpropertyInventCount() != null) {
                            if (!item.getSpecpropertyInventCount().equals("")) {
                                if (NumberUtils.getIntFromString(item.getSpecpropertyInventCount()) > 0) {
                                    if (current > NumberUtils.getIntFromString(item.getSpecpropertyInventCount())) {
                                        ToastUtil.show(context, mContext.getString(R.string.more_other_num));
                                        current = NumberUtils.getIntFromString(item.getSpecpropertyInventCount());
                                    }
                                }
                            }
                        }

                        item.setCount(current);
                        holder.edt_num.setText(String.valueOf(current));

                        if (oldCurrent != current) {
                            if (oldCurrent > current) {
                                allCount = allCount - (oldCurrent - current);
                            } else {
                                allCount = allCount + (current - oldCurrent);
                            }
                        }

                        for (int i = 0; i < tiredPriceData.getTired_data_list().size(); i++) {
                            String[] sArray = tiredPriceData.getTired_data_list().get(i).getCount().split("-");
                            if (allCount >= NumberUtils.getIntFromString(sArray[0]) && allCount <= NumberUtils.getIntFromString(sArray[1])) {
                                tv_tv_Price.setText(mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(tiredPriceData.getTired_data_list().get(i).getPrice()));
                                item.setPrice(tiredPriceData.getTired_data_list().get(i).getPrice());
                                initPrice(tiredPriceData.getTired_data_list().get(i).getPrice(), mdata);
                                notifyDataSetChanged();
                            }
                        }

                    }else {
                       holder.edt_num.setText(String.valueOf(0));
                        ToastUtil.show(context,"此商品最小购买量大于库存,不能购买");
                    }

                    holder.edt_num.setFocusable(false);
                }

                return true;
            }
        });


        tvGetNum.setText(String.valueOf(allCount));
        tvGetPrice.setText(mContext.getString(R.string.label_money)+NumberUtils.formatToDouble(String.valueOf(NumberUtils.getDoubleFromString(tv_tv_Price.getText().toString().substring(1)) * allCount)));
        return view;
    }

    public void initPrice(String price , List<FlightLsItemBean> m_list){
        for (int i = 0 ; i<m_list.size();i++){
            m_list.get(i).setPrice(price);
        }
    }

    public class ViewHolder{
        private TextView tv_style ,tv_price ,tv_num;
        private ImageView iv_add ,iv_reduce ;
        private ExtendedEditText edt_num ;
    }

    public interface AllCountInterface{
        void setAllCount(int allCount);
    }

}
