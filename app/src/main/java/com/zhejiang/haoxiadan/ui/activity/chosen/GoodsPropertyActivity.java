package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.model.choseModel.KeyValueModel;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/11/25.
 */

public class GoodsPropertyActivity extends BaseFragmentActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.ls_property)
    NoScrollListView lsProperty;
    @BindView(R.id.tv_close)
    TextView tvClose;

    private ArrayList<KeyValueModel> list = new ArrayList<>();
    private CommonAdapter<KeyValueModel> adapter;
    private String type ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_property_layout);
        ButterKnife.bind(this);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        type = getIntent().getStringExtra("type");
        if (type.equals("1")){
            tvName.setText("产品属性");
        }else {
            tvName.setText("订购说明");
        }
        list = (ArrayList<KeyValueModel>) getIntent().getSerializableExtra("value");

        adapter = new CommonAdapter<KeyValueModel>(context,list,R.layout.goods_property_ls_item) {
            @Override
            public void convert(ViewHolder helper, KeyValueModel item) {
                helper.setText(R.id.tv_key_name,item.getKey_name());
                helper.setText(R.id.tv_key_value,item.getKey_value());
            }
        };

        lsProperty.setAdapter(adapter);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
