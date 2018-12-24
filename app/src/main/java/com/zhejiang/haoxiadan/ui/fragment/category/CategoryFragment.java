package com.zhejiang.haoxiadan.ui.fragment.category;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.category.CategoryRequester;
import com.zhejiang.haoxiadan.business.request.category.QueryGoodsClassAllListener;
import com.zhejiang.haoxiadan.business.request.category.ShowViewClassListener;
import com.zhejiang.haoxiadan.business.request.my.QueryTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.my.ValueAddRequester;
import com.zhejiang.haoxiadan.model.common.Category;
import com.zhejiang.haoxiadan.model.common.Industry;
import com.zhejiang.haoxiadan.ui.adapter.category.CateMAdapter;
import com.zhejiang.haoxiadan.ui.adapter.category.CateTabAdapter;
import com.zhejiang.haoxiadan.ui.adapter.category.CateTabModel;
import com.zhejiang.haoxiadan.ui.dialog.CommonUseDialog;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.dialog.IndustryPopupWindow;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends BaseFragment {
    private static final String ARG_PARAM_SHOW_BACK = "ARG_PARAM_SHOW_BACK";
    private boolean showBack;

    private CommonTitle commonTitle;
    private RecyclerView tabRecyclerView;
    private CateTabAdapter cateTabAdapter;

    private boolean isClick = false;

    private ListView listView;
    private CateMAdapter cateMAdapter;
    private List<Category> cates;
    private List<CateTabModel> cateTabModels;

    private IndustryPopupWindow industryPopupWindow;
    private List<Industry> industries;
    private List<Industry> firstFocus;
    private List<Industry> secondFocus;

    private GlobalTitleMorePopupWindow popupDialog ;

    private Callback callback;

    private CategoryRequester categoryRequester;
    private ShowViewClassListenerImpl showViewClassListenerImpl;
    private class ShowViewClassListenerImpl extends DefaultRequestListener implements ShowViewClassListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onShowViewClassSuccess(List<Category> categories) {
            refreshView(categories);
        }
    }

    private ValueAddRequester valueAddRequester;
    private QueryTradeInfoListenerImpl queryTradeInfoListener;
    private class QueryTradeInfoListenerImpl extends DefaultRequestListener implements QueryTradeInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryTradeInfoSuccess(List<Industry> industryList,List<Industry> firstFocusList,List<Industry> secondFocusList) {
            industries.clear();
            industries.addAll(industryList);
            firstFocus.clear();
            firstFocus.addAll(firstFocusList);
            secondFocus.clear();
            secondFocus.addAll(secondFocusList);
        }
    }

    public interface Callback{
        void onBackClick();
    }

    public CategoryFragment() {
    }

    public static CategoryFragment newInstance(boolean showBack) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_SHOW_BACK, showBack);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            showBack = getArguments().getBoolean(ARG_PARAM_SHOW_BACK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        categoryRequester = new CategoryRequester();
        showViewClassListenerImpl = new ShowViewClassListenerImpl();
        valueAddRequester = new ValueAddRequester();
        queryTradeInfoListener = new QueryTradeInfoListenerImpl();

        initView(view);
        initEvent();
        initData();


        return view;
    }

    private void initView(View view){
        commonTitle = (CommonTitle) view.findViewById(R.id.common_title);
        tabRecyclerView = (RecyclerView) view.findViewById(R.id.rv_tab);
        listView = (ListView)view.findViewById(R.id.listView);

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        tabRecyclerView.setLayoutManager(linearLayoutManager);

        industries = new ArrayList<>();
        firstFocus = new ArrayList<>();
        secondFocus = new ArrayList<>();
        industryPopupWindow = new IndustryPopupWindow(getActivity(),industries,firstFocus,secondFocus);

        popupDialog = new GlobalTitleMorePopupWindow(getActivity());
    }

    private void initEvent(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if(!isClick && cateTabModels!=null && cateTabModels.size()>0){
                            for(int i=0;i<cateTabModels.size();i++){
                                if(i == listView.getFirstVisiblePosition()){
                                    cateTabModels.get(i).setChoose(true);
                                }else{
                                    cateTabModels.get(i).setChoose(false);
                                }
                            }
                            cateTabAdapter.notifyDataSetChanged();
                        }

                        if(scrollState == SCROLL_STATE_IDLE){
                            isClick = false;
                        }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        industryPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //设置箭头的显示
                if(cateTabModels!=null && cateTabModels.size()>0){
                    cateTabModels.get(cateTabModels.size()-1).setChoose(false);
                    cateTabAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initData(){
        cates = new ArrayList<>();
        cateTabModels = new ArrayList<>();
        cateTabAdapter = new CateTabAdapter(getActivity(), cateTabModels);
        tabRecyclerView.setAdapter(cateTabAdapter);
        cateMAdapter = new CateMAdapter(getActivity(),cates);
        listView.setAdapter(cateMAdapter);

        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        cateTabAdapter.setItemWidth(d.widthPixels/5);//五等分

        if(showBack){
            commonTitle.showLeft(true);
        }else{
            commonTitle.showLeft(false);
        }
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                if(callback != null){
                    callback.onBackClick();
                }
            }

            @Override
            public void onRightClick() {
                popupDialog.showAsDropDown(commonTitle);
//                CommonUseDialog.showComDialog(context,commonTitle.getmIvRightIcon());
            }
        });

        cateTabAdapter.setItemClickListener(new CateTabAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(cateTabModels!=null && cateTabModels.size()>0){
                    for(int i=0;i<cateTabModels.size();i++){
                        if(i == position){
                            cateTabModels.get(i).setChoose(true);
                        }else{
                            cateTabModels.get(i).setChoose(false);
                        }
                    }
                    cateTabAdapter.notifyDataSetChanged();
                }

                isClick = true;
                listView.smoothScrollToPosition(position);

            }

            @Override
            public void onArrowClick(int position) {
                if(!industryPopupWindow.isShowing()){
                    industryPopupWindow.showAsDropDown(tabRecyclerView);
                }
                //设置箭头的显示
                if(cateTabModels!=null && cateTabModels.size()>0){
                    cateTabModels.get(cateTabModels.size()-1).setChoose(industryPopupWindow.isShowing());
                    cateTabAdapter.notifyDataSetChanged();
                }
            }
        });
        categoryRequester.showViewClass(getActivity(),showViewClassListenerImpl);
        valueAddRequester.queryTradeInfo(getActivity(),queryTradeInfoListener);
    }

    private void refreshView(List<Category> categories){
        cates.clear();
        cates.addAll(categories);
        cateTabModels.clear();
        for(Category category : cates){
            cateTabModels.add(new CateTabModel(0,category.getName(),false));
        }
        cateTabModels.add(new CateTabModel(1,"",false));
        cateTabModels.get(0).setChoose(true);//默认第一个选中

        cateTabAdapter.notifyDataSetChanged();
        cateMAdapter.notifyDataSetChanged();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}
