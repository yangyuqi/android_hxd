package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.widget.ListView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.ConsultHistoryListAdapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;

import java.util.ArrayList;
import java.util.List;

public class ConsultHistoryActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private ListView listView;

    private ConsultHistoryListAdapter adapter;
    private List<ConsultHistory> consultHistories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_history);


        initView();
        initEvent();
        initData();
    }

    private void initView(){
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        listView = (ListView) findViewById(R.id.lv_consult_history);

        consultHistories = new ArrayList<>();
        adapter = new ConsultHistoryListAdapter(this,consultHistories);
        listView.setAdapter(adapter);
    }
    private void initEvent(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
    }
    private void initData(){

//        List<ConsultHistory> temp = (ArrayList<ConsultHistory>)getIntent().getSerializableExtra("list");
        List<ConsultHistory> temp = new ArrayList<>();
        consultHistories.clear();
        consultHistories.addAll(temp);

        adapter.notifyDataSetChanged();
    }

}
