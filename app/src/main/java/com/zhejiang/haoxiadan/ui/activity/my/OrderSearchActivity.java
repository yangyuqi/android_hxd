package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.MyFlowLayout;
import com.zhejiang.haoxiadan.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class OrderSearchActivity extends BaseActivity {

    private EditText keywordET;
    private TextView searchBtn;
    private ImageView backBtn;
    private ImageView deleteBtn;
    private ImageView cleanBtn;

    private LinearLayout searchHistoryLayout;
    private MyFlowLayout tagsLayout;
    private List<String> historyTags;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_search);


        initView();
        initEvent();
        initData();
    }


    private void initView(){
        keywordET = (EditText) findViewById(R.id.et_keyword);
        searchBtn = (TextView) findViewById(R.id.tv_search);
        backBtn = (ImageView) findViewById(R.id.iv_back);
        deleteBtn = (ImageView) findViewById(R.id.iv_delete);
        cleanBtn = (ImageView) findViewById(R.id.iv_clean_history);

        searchHistoryLayout = (LinearLayout)findViewById(R.id.ll_search_history);
        tagsLayout = (MyFlowLayout)findViewById(R.id.flowlayout_tags);
    }

    private void initEvent(){
        keywordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String temp = keywordET.getText().toString();
                if(temp.equals("")){
                    deleteBtn.setVisibility(View.GONE);
                }else{
                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keywordET.getText().toString().trim();
                if(key.equals("")){
                    return;
                }
                addSearchHistory(key);
                Intent intent = new Intent(OrderSearchActivity.this,OrderSearchResultActivity.class);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keywordET.setText("");
            }
        });

        cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSearchHistory();
            }
        });
    }

    private void initData(){
        historyTags = new ArrayList<>();

        getSearchHistory();
        refreshSearchTags();
    }

    private void refreshSearchTags(){
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams( new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        lp.topMargin = 30;
        lp.rightMargin = 30;

        tagsLayout.removeAllViews();
        for(String tag:historyTags){
            TextView textView = new TextView(this);
            textView.setText(tag);
            textView.setBackgroundResource(R.drawable.bg_solid_gray_roundside);
            textView.setTextAppearance(this,R.style.text_search_hot_tag);
            textView.setPadding(50,10,50,10);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keywordET.setText(((TextView)v).getText().toString());
                    searchBtn.performClick();
                }
            });

            tagsLayout.addView(textView,lp);
        }
        if(historyTags !=null && historyTags.size()>0){
            searchHistoryLayout.setVisibility(View.VISIBLE);
        }else{
            searchHistoryLayout.setVisibility(View.GONE);
        }
    }

    //获得搜索历史
    private void getSearchHistory(){
        List<String> temp = (ArrayList<String>) GlobalDataUtil.getObject(this, Constants.GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY);
        historyTags.clear();
        if(temp !=null){
            historyTags.addAll(temp);
        }
    }
    //增加搜索历史
    private void addSearchHistory(String value){
        List<String> temp = (ArrayList<String>)GlobalDataUtil.getObject(this, Constants.GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY);
        if(temp == null){
            temp = new ArrayList<>();
        }
        if(!temp.contains(value)){
            temp.add(0,value);
            GlobalDataUtil.putObject(this, Constants.GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY,temp,true);
        }
        getSearchHistory();
        refreshSearchTags();
    }
    //清空搜索历史
    private void clearSearchHistory(){
        GlobalDataUtil.removeObject(this, Constants.GLOBAL_DATA_KEY_SEARCH_ORDER_HISTORY);
        getSearchHistory();
        refreshSearchTags();
    }
}
