package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.SearchShopGoodsListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoods2Adapter;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 店铺查看更多
 */
public class ShopGoodsMoreActivity extends BaseFragmentActivity implements View.OnClickListener {

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    @BindView(R.id.gv_goods)
    GridView gvGoods;
    private CommonTitle commonTitle;
    private TextView multipleTv;
    private TextView salesTv;
    private TextView priceTv;
    private SpringView springView;
    private GridView gridView;

    private CommonGoods2Adapter adapter;
    private List<Goods> goodses;

    private String storeId;
    private String orderBy = "colligate";
    private String orderType = "asc";

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
            if (pageNo == 1) {
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
        setContentView(R.layout.activity_shop_goods_more);
        ButterKnife.bind(this);

        shopRequester = new ShopRequester();
        searchShopGoodsListener = new SearchShopGoodsListenerImpl();

        storeId = getIntent().getStringExtra("storeId");
        if (storeId == null) {
            finish();
            return;
        }

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        springView = (SpringView) findViewById(R.id.spring_view);
        gridView = (GridView) findViewById(R.id.gv_goods);

        multipleTv = (TextView) findViewById(R.id.tv_multiple);
        salesTv = (TextView) findViewById(R.id.tv_sales);
        priceTv = (TextView) findViewById(R.id.tv_price);

        goodses = new ArrayList<>();
        adapter = new CommonGoods2Adapter(this, goodses);
        gridView.setAdapter(adapter);

        multipleTv.setSelected(true);
    }

    private void initEvent() {
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        multipleTv.setOnClickListener(this);
        salesTv.setOnClickListener(this);
        priceTv.setOnClickListener(this);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 1;
                requestData();
            }

            @Override
            public void onLoadmore() {
                pageNo++;
                requestData();
            }
        });

        rlRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultIv.setVisibility(View.GONE);
                cb.setVisibility(View.VISIBLE);
                multipleTv.setSelected(false);
                salesTv.setSelected(false);
                priceTv.setSelected(true);
                priceTv.setTextColor(ShopGoodsMoreActivity.this.getResources().getColor(R.color.text_red));
                if (cb.isChecked()) {
                    orderType = "asc";
                    cb.setChecked(false);
                } else {
                    orderType = "desc";
                    cb.setChecked(true);
                }
                orderBy = "storePrice";
                pageNo = 1;
                requestData();
            }
        });

    }

    private void initData() {
        requestData();
    }

    private void requestData() {
        if (!orderBy.equals("storePrice")) {
            shopRequester.shopGoodsSearch(this, storeId, null, orderBy, pageNo, pageSize, "desc", searchShopGoodsListener);
        } else {
            shopRequester.shopGoodsSearch(this, storeId, null, orderBy, pageNo, pageSize, orderType, searchShopGoodsListener);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_multiple:
                defaultIv.setVisibility(View.VISIBLE);
                cb.setVisibility(View.GONE);
                multipleTv.setSelected(true);
                salesTv.setSelected(false);
                priceTv.setSelected(false);
                priceTv.setTextColor(context.getResources().getColor(R.color.text_black_light));
                orderBy = "colligate";
                pageNo = 1;
                requestData();
                break;
            case R.id.tv_sales:
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                multipleTv.setSelected(false);
                salesTv.setSelected(true);
                priceTv.setSelected(false);
                priceTv.setTextColor(context.getResources().getColor(R.color.text_black_light));
                orderBy = "goodsSalenum";
                pageNo = 1;
                requestData();
                break;
            case R.id.tv_price :
                defaultIv.setVisibility(View.GONE);
                cb.setVisibility(View.VISIBLE);
                multipleTv.setSelected(false);
                salesTv.setSelected(false);
                priceTv.setSelected(true);
                priceTv.setTextColor(ShopGoodsMoreActivity.this.getResources().getColor(R.color.text_red));
                if (cb.isChecked()) {
                    orderType = "asc";
                    cb.setChecked(false);
                } else {
                    orderType = "desc";
                    cb.setChecked(true);
                }
                orderBy = "storePrice";
                pageNo = 1;
                requestData();

                break;
//                multipleTv.setSelected(false);
//                salesTv.setSelected(false);
//                priceTv.setSelected(true);
//
//                orderBy = "storePrice";
//                pageNo = 1;
//                requestData();
        }
    }
}
