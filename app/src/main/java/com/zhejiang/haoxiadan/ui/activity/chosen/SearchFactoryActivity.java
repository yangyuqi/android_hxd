package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.SearchGoodsListener;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.choseModel.SearchLuceneListBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.my.AdviceActivity;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.view.CustomPopup;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/25.
 */

public class SearchFactoryActivity extends BaseFragmentActivity implements CustomPopup.IPopuWindowListener{

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_zonghe)
    TextView tvZonghe;
    @BindView(R.id.tv_hangye)
    TextView tvHangye;
    @BindView(R.id.tv_yingye)
    TextView tvYingye;
    @BindView(R.id.ls_factort)
    ListView lsFactort;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.view_line3)
    View viewLine3;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    private List<SearchLuceneListBean> data = new ArrayList<>();
    private CommonAdapter<SearchLuceneListBean> adapter;
    private String searchType, keyWord;
    private CustomPopup alarmPopup;
    private int currentPage = 1;
    private int showCount = 40;
    private SearchImp searchImp = new SearchImp();
    private GoodsRequester goodsRequester = new GoodsRequester();
    private GlobalTitleMorePopupWindow popupDialog ;
    private class SearchImp extends DefaultRequestListener implements SearchGoodsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getData(SearchData searchData) {
            data.clear();
            if (searchData.getLuceneList().size() > 0) {
                lsFactort.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                data.addAll(searchData.getLuceneList());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            } else {
                rlNoData.setVisibility(View.VISIBLE);
                lsFactort.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_factory_layout);
        ButterKnife.bind(this);

        searchType = getIntent().getStringExtra("searchType");
        keyWord = getIntent().getStringExtra("keyWord");
        initView();

        tvSearch.setText(keyWord);

        popupDialog = new GlobalTitleMorePopupWindow(context);
    }

    private void initView() {

        adapter = new CommonAdapter<SearchLuceneListBean>(context, data, R.layout.factory_ls_item) {
            @Override
            public void convert(ViewHolder helper, final SearchLuceneListBean item) {
                helper.setText(R.id.tv_style, "主营业务: " + item.getMainIndustry());
                helper.setText(R.id.tv_product, item.getStoreName());
                ImageLoaderUtil.displayImage(item.getStoreLogoImg(),(ImageView) helper.getView(R.id.iv_icon));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ShopActivity.class);
                        intent.putExtra("storeId", String.valueOf(item.getVo_id()));
                        startActivity(intent);
                    }
                });
            }
        };

        lsFactort.setAdapter(adapter);

        goodsRequester.searchFactort(context, searchType, keyWord, "", currentPage, showCount, "colligate","desc", searchImp);

        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH){
                    if (tvSearch.getText().toString().length()>0){
                        keyWord = tvSearch.getText().toString();
                        if (texs!=null){
                            goodsRequester.searchFactort(context, searchType, keyWord, texs, currentPage, showCount, "", "desc",searchImp);
                        }else {
                            goodsRequester.searchFactort(context, searchType, keyWord, "", currentPage, showCount,type,"desc", searchImp);
                        }
                        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                                .hideSoftInputFromWindow(getCurrentFocus()
                                                .getWindowToken(),
                                        InputMethodManager.HIDE_NOT_ALWAYS);
                    }else {
                        ToastUtil.show(context,"输入搜索内容");
                    }
                }
                return false;
            }
        });
    }

    @OnClick({R.id.tv_hangye, R.id.iv_left, R.id.tv_zonghe, R.id.tv_yingye ,R.id.iv_more})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_hangye:
                alarmPopup = new CustomPopup(context, ViewGroup.LayoutParams.MATCH_PARENT, 300, this);
                defaultIv.setVisibility(View.VISIBLE);
                cb.setVisibility(View.GONE);
                alarmPopup.show(tvHangye);
                tvHangye.setTextColor(getResources().getColor(R.color.main_red));
                viewLine2.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.GONE);
                viewLine3.setVisibility(View.GONE);
                tvZonghe.setTextColor(getResources().getColor(R.color.text_black));
                tvYingye.setTextColor(getResources().getColor(R.color.text_black));
                break;
            case R.id.iv_left:
                finish();
                break;

            case R.id.tv_yingye:
                cb.setVisibility(View.VISIBLE);
                defaultIv.setVisibility(View.GONE);
                tvYingye.setTextColor(getResources().getColor(R.color.main_red));
                tvZonghe.setTextColor(getResources().getColor(R.color.text_black));
                tvHangye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine3.setVisibility(View.VISIBLE);
                viewLine.setVisibility(View.GONE);
                viewLine2.setVisibility(View.GONE);
                tvHangye.setText("主营行业");
                type = "yearSales";
                texs =null;
                if (cb.isChecked()){
                    cb.setChecked(false);
                    goodsRequester.searchFactort(context, searchType, keyWord, "", currentPage, showCount, "yearSales", "asc",searchImp);
                }else {
                    cb.setChecked(true);
                    goodsRequester.searchFactort(context, searchType, keyWord, "", currentPage, showCount, "yearSales", "desc",searchImp);
                }

                break;

            case R.id.tv_zonghe:
                texs = null ;
                type = "colligate";
                defaultIv.setVisibility(View.VISIBLE);
                cb.setVisibility(View.GONE);
                tvHangye.setText("主营行业");
                goodsRequester.searchFactort(context, searchType, keyWord, "", currentPage, showCount, "colligate", "desc",searchImp);
                tvZonghe.setTextColor(getResources().getColor(R.color.main_red));
                tvYingye.setTextColor(getResources().getColor(R.color.text_black));
                tvHangye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine.setVisibility(View.VISIBLE);
                viewLine2.setVisibility(View.GONE);
                viewLine3.setVisibility(View.GONE);
                break;

            case R.id.iv_more :
                popupDialog.showAsDropDown(ivMore);
                break;
        }
    }

    @Override
    public void dispose() {
        if (alarmPopup != null) {
            if (alarmPopup.isShowing()) {
                alarmPopup.dismiss();//关闭PopupWindow
            }
        }
    }

    private String texs ,type = "colligate";

    @Override
    public void setName(String name) {
        if (name.equals("女装")) {
            tvHangye.setText(name);
            texs = name;
            goodsRequester.searchFactort(context, searchType, keyWord, name, currentPage, showCount, "", "desc", searchImp);
        }
    }

}
