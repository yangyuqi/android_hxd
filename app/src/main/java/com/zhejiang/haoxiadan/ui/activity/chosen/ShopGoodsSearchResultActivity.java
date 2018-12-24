package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.SearchShopGoodsListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoods2Adapter;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;

import java.util.ArrayList;
import java.util.List;

public class ShopGoodsSearchResultActivity extends BaseActivity implements View.OnClickListener {

    private ImageView backIv;
    private TextView keywordTv;
    private ImageView moreIv;
    private TextView multipleTv;
    private TextView salesTv;
    private TextView priceTv;
    private SpringView springView;
    private GridView gridView;

    private CommonGoods2Adapter adapter;
    private List<Goods> goodses;
    private GlobalTitleMorePopupWindow popupDialog ;

    private String key;
    private String storeId;
    private String orderBy = "colligate";

    private int pageNo = 1;
    private int pageSize = 20;
    private ShopRequester shopRequester;
    private SearchShopGoodsListenerImpl searchShopGoodsListener;
    private class SearchShopGoodsListenerImpl extends DefaultRequestListener implements SearchShopGoodsListener {

        @Override
        protected void onRequestFail() {
            springView.onFinishFreshAndLoad();
        }

        @Override
        public void onSearchShopGoodsSuccess(List<Goods> goodsList) {
            if(pageNo == 1){
                goodses.clear();
            }
            goodses.addAll(goodsList);
            adapter.notifyDataSetChanged();
            springView.onFinishFreshAndLoad();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_goods_search_result);

        shopRequester = new ShopRequester();
        searchShopGoodsListener = new SearchShopGoodsListenerImpl();

        storeId = getIntent().getStringExtra("storeId");
        key = getIntent().getStringExtra("key");
        if(storeId == null){
            finish();
            return;
        }

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        backIv = (ImageView)findViewById(R.id.iv_back);
        keywordTv = (TextView)findViewById(R.id.et_keyword);
        moreIv = (ImageView)findViewById(R.id.iv_more);
        springView = (SpringView)findViewById(R.id.spring_view);
        gridView = (GridView)findViewById(R.id.gv_goods);

        multipleTv = (TextView)findViewById(R.id.tv_multiple);
        salesTv = (TextView)findViewById(R.id.tv_sales);
        priceTv = (TextView)findViewById(R.id.tv_price);

        popupDialog = new GlobalTitleMorePopupWindow(this);

        goodses = new ArrayList<>();
        adapter = new CommonGoods2Adapter(this,goodses);
        gridView.setAdapter(adapter);

        multipleTv.setSelected(true);
    }

    private void initEvent(){
        backIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);
        multipleTv.setOnClickListener(this);
        salesTv.setOnClickListener(this);
        priceTv.setOnClickListener(this);
        keywordTv.setOnClickListener(this);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                requestData();
            }

            @Override
            public void onLoadmore() {
                pageNo ++;
                requestData();
            }
        });

    }

    private void initData(){
        requestData();
        keywordTv.setText(key);
    }

    private void requestData(){
        shopRequester.shopGoodsSearch(this,storeId,key,orderBy,pageNo,pageSize,"desc",searchShopGoodsListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.et_keyword:
                finish();
                break;
            case R.id.iv_more:
                popupDialog.showAsDropDown(moreIv);
                break;
            case R.id.tv_multiple:
                multipleTv.setSelected(true);
                salesTv.setSelected(false);
                priceTv.setSelected(false);

                orderBy = "colligate";
                pageNo = 1;
                requestData();
                break;
            case R.id.tv_sales:
                multipleTv.setSelected(false);
                salesTv.setSelected(true);
                priceTv.setSelected(false);

                orderBy = "goodsSalenum";
                pageNo = 1;
                requestData();
                break;
            case R.id.tv_price:
                multipleTv.setSelected(false);
                salesTv.setSelected(false);
                priceTv.setSelected(true);

                orderBy = "storePrice";
                pageNo = 1;
                requestData();
                break;
        }
    }
}
