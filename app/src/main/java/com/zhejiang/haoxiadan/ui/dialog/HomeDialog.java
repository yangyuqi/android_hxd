package com.zhejiang.haoxiadan.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ChosenFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/9/2.
 */

public class HomeDialog extends Dialog {
    GoodsRequester goodsRequester ;
    private Context context;
    private ListView ls ;
    private List<TradeListData> dataList = new ArrayList<>();
    private CommonAdapter<TradeListData> adapter ;

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

    public HomeDialog(@NonNull Context context , GoodsRequester  requester) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        goodsRequester = requester ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.home_dialog, null);
        setContentView(view);
        ls = (ListView) view.findViewById(R.id.home_dialog_ls);

        adapter = new CommonAdapter<TradeListData>(context,dataList,R.layout.home_dialog_ls_item) {
            @Override
            public void convert(ViewHolder helper, TradeListData item) {
                TextView textView = helper.getView(R.id.home_dailog_ls_item_tv);
                textView.setText(item.getTradeName());
                       if (item.getId()==1){ //男装
                           textView.setBackgroundResource(R.drawable.text_blue_bg);
                       }else if (item.getId() == 2){ //女装
                           textView.setBackgroundResource(R.drawable.text_pink_bg);
                       }else if (item.getId() == 3){ //童装
                           textView.setBackgroundResource(R.drawable.text_yellow_light_bg);
                       }
            }
        };

        ls.setAdapter(adapter);

        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EventBus.getDefault().post(dataList.get(i));
                dismiss();
            }
        });

        initData();
    }

    private void initData() {
        goodsRequester.getTradeInfo(context,imp);
    }
}
