package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.FlightRuleListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CrowdFundRulesBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CrowdFundRulesData;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/9/13.
 */

public class FlightNewRuleActivity extends BaseFragmentActivity {
    @BindView(R.id.rule_ls)
    NoScrollListView ruleLs;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    private List<FlightNewRuleBean> ruleData = new ArrayList<>();
    private CommonAdapter<FlightNewRuleBean> adapter;

    CrowdFundRulesData crowdFundRulesData ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_new_rule_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<FlightNewRuleBean>(this, ruleData, R.layout.flight_rule_ls_item) {
            @Override
            public void convert(ViewHolder helper, FlightNewRuleBean item) {
                if (item.getSpecpropertyName().equals("")) {
                    helper.setText(R.id.tv_rule, String.valueOf(helper.getPosition()+1)+" . 伙拼总量≥" + item.getCrowdfundingCount() + "件");
                }else {
                    helper.setText(R.id.tv_rule, String.valueOf(helper.getPosition()+1)+" . 若存在"+item.getSpecpropertyName()+" , 伙拼总量≥" + item.getCrowdfundingCount() + "件");
                }
                helper.setText(R.id.tv_rule_num, String.valueOf(item.getCheckOutCount()));
            }
        };
        ruleLs.setAdapter(adapter);

        crowdFundRulesData = (CrowdFundRulesData) getIntent().getSerializableExtra("rule");

        filterData(crowdFundRulesData);

        adapter.setData(ruleData);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void filterData(CrowdFundRulesData rulesData) {
        CrowdFundRulesBean rulesBean = rulesData.getCrowdFundRules().get(0);
        FlightNewRuleBean bean = new FlightNewRuleBean(rulesBean.getCrowdfundingCount(),rulesBean.getCheckOutCount(),"");
        ruleData.add(bean);
        for (int i = 0 ; i< rulesBean.getGoodsGsp().size();i++){
            FlightNewRuleBean flightNewRuleBean = new FlightNewRuleBean();
            flightNewRuleBean.setCrowdfundingCount(NumberUtils.getIntFromString(rulesBean.getGoodsGsp().get(i).getSpecpropertyCrowdSum()));
            flightNewRuleBean.setSpecpropertyName(rulesBean.getGoodsGsp().get(i).getSpecpropertyName());
            int count = 0;
            for (int j = 0 ; j<rulesBean.getGoodsGsp().get(i).getSpecpropertyList().size() ; j++){
                count = count + rulesBean.getGoodsGsp().get(i).getSpecpropertyList().get(j).getCheckOutCount();
            }
            flightNewRuleBean.setCheckOutCount(count);
            ruleData.add(flightNewRuleBean);
        }

    }


    protected class FlightNewRuleBean{
        private int crowdfundingCount ;
        private int checkOutCount ;
        private String specpropertyName ;

        public FlightNewRuleBean(){

        }

        public FlightNewRuleBean(int crowdfundingCount, int checkOutCount, String specpropertyName) {
            this.crowdfundingCount = crowdfundingCount;
            this.checkOutCount = checkOutCount;
            this.specpropertyName = specpropertyName;
        }

        public int getCrowdfundingCount() {
            return crowdfundingCount;
        }

        public void setCrowdfundingCount(int crowdfundingCount) {
            this.crowdfundingCount = crowdfundingCount;
        }

        public int getCheckOutCount() {
            return checkOutCount;
        }

        public void setCheckOutCount(int checkOutCount) {
            this.checkOutCount = checkOutCount;
        }

        public String getSpecpropertyName() {
            return specpropertyName;
        }

        public void setSpecpropertyName(String specpropertyName) {
            this.specpropertyName = specpropertyName;
        }
    }

}
