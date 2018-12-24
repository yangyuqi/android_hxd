package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsRuleBean;
import com.zhejiang.haoxiadan.model.requestData.out.TiredPriceData;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/30.
 */

public class FlighRuleActivity extends BaseFragmentActivity {
    @BindView(R.id.rule_ls)
    NoScrollListView ruleLs;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    CommonAdapter<TiredPriceData> adapter ;

    private List<TiredPriceData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_rule_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        PostGoodsRuleBean ruleBean = gson.fromJson(getIntent().getStringExtra("rule"),PostGoodsRuleBean.class);
        data.addAll(ruleBean.getList());

        adapter = new CommonAdapter<TiredPriceData>(context,data,R.layout.flight_price_ls_item) {
            @Override
            public void convert(ViewHolder helper, TiredPriceData item) {
                helper.setText(R.id.tv_price,context.getString(R.string.label_money)+ NumberUtils.formatToDouble(item.getPrice()));
                helper.setText(R.id.tv_num,item.getCount()+"件");
            }
        };

        ruleLs.setAdapter(adapter);
    }

    @OnClick({R.id.tv_finish})
    public void OnClick(View view){
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
