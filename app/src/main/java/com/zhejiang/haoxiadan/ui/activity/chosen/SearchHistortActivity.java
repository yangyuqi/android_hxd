package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.PublicUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/25.
 */

public class SearchHistortActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.labelView)
    LabelsView labelView;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.iv_search_delete)
    ImageView ivSearchDelete;
    private ArrayList<String> label = new ArrayList<>();
    ArrayList<String> delList = new ArrayList();//用来装需要删除的元素

    int m_position = 0, selectposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_history_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {

        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (label.size() > 0) {
                        for (int n = 0; n < label.size(); n++) {
                            if (label.get(n).equals(tvSearch.getText().toString())) {
                                label.remove(n);
                            }
                        }
                    }
                    label.add(tvSearch.getText().toString());
                    SharedPreferencesUtil.put(context, Constants.search_content, gson.toJson(label));
                    labelView.setLabels(label);

                    if (selectposition == 0) {
                        Intent intent = new Intent(context, SearchResultGoodsActivity.class);
                        intent.putExtra("keyWord", tvSearch.getText().toString());
                        intent.putExtra("searchType", "goods");
                        intent.putExtra("type", "goodsType");
                        startActivity(intent);
//                    }else if (selectposition == 1) {
//                        Intent intent = new Intent(context, SearchResultGoodsActivity.class);
//                        intent.putExtra("keyWord", tvSearch.getText().toString());
//                        intent.putExtra("type","brand");
//                        intent.putExtra("searchType","goods");
//                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, SearchFactoryActivity.class);
                        intent.putExtra("keyWord", tvSearch.getText().toString());
                        intent.putExtra("searchType", "store");
                        startActivity(intent);
                    }

                }
                return false;
            }
        });

        labelView.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(View label, String labelText, int position) {
                tvSearch.setText(labelText);
                m_position = position;

                if (selectposition == 0) {
                    Intent intent = new Intent(context, SearchResultGoodsActivity.class);
                    intent.putExtra("keyWord", tvSearch.getText().toString());
                    intent.putExtra("searchType", "goods");
                    intent.putExtra("type", "goodsType");
                    startActivity(intent);
//                }else if (selectposition == 1) {
//                    Intent intent = new Intent(context, SearchResultGoodsActivity.class);
//                    intent.putExtra("keyWord", tvSearch.getText().toString());
//                    intent.putExtra("searchType","goods");
//                    intent.putExtra("type","brand");
//                    startActivity(intent);
                } else {
                    Intent intent = new Intent(context, SearchFactoryActivity.class);
                    intent.putExtra("keyWord", tvSearch.getText().toString());
                    intent.putExtra("searchType", "store");
                    startActivity(intent);
                }
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectposition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ivSearchDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvSearch.setText("");
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        getHistoryData();
    }

    private void getHistoryData() {
        label.clear();
        String old_content = (String) SharedPreferencesUtil.get(context, Constants.search_content, "");
        if (old_content.equals("")) {
            ivDelete.setVisibility(View.GONE);
            label.addAll(new ArrayList<String>());
        } else {
            ivDelete.setVisibility(View.VISIBLE);
            label.addAll(gson.fromJson(old_content, ArrayList.class));
        }
        labelView.setLabels(label);
        if (label.size() > 0) {
            if (m_position != -1) {
//                labelView.setSelects(m_position);
            }
        }
    }

    private void initView() {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                PublicUtils.setTabLine(tabLayout, 65, 65);
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.iv_delete})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_delete:
                final DeleteDialog deleteDialog = new DeleteDialog(context, "提示", "是否清除历史搜索记录", "确定");
                deleteDialog.show();
                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        deleteDialog.dismiss();
                        SharedPreferencesUtil.remove(context, Constants.search_content);
                        getHistoryData();
                    }
                });
                break;
        }
    }


}
