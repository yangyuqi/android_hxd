package com.zhejiang.haoxiadan.ui.fragment.category;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.ui.adapter.category.CateMAdapter;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

public class CategoryListFragment extends Fragment {

    private static final String ARG_PARAM_CATET = "cateT";
    private Category category;

    private NoScrollListView cateMListView;
    private CateMAdapter cateMAdapter;

    public CategoryListFragment() {
    }

    public static CategoryListFragment newInstance(Category category) {
        CategoryListFragment fragment = new CategoryListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CATET, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = (Category)getArguments().getSerializable(ARG_PARAM_CATET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_category_list, container, false);

        initView(view);
        initEvent();
        initData();

        return view;
    }
    private void initView(View view){

        cateMListView = (NoScrollListView)view.findViewById(R.id.lv_cate_m);
    }
    private void initEvent(){
        cateMListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("K_K",category.getCates().get(firstVisibleItem).getName());
            }
        });

    }
    private void initData(){
        cateMAdapter = new CateMAdapter(getActivity(), category.getCates());
        cateMListView.setAdapter(cateMAdapter);
    }

}
