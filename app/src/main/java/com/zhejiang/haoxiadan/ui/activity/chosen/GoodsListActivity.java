package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetGoodsListListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.choseModel.GoodsIdListBeanData;
import com.zhejiang.haoxiadan.model.choseModel.GoodsListData;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/9/5.
 */

public class GoodsListActivity extends BaseFragmentActivity {

    @BindView(R.id.collections_goods_gv)
    GridView collectionsGoodsGv;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;

    private String title;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private GoodsRequester requester = new GoodsRequester();
    private GetGoodsListImp listImp = new GetGoodsListImp();

    private CommonAdapter<GoodsIdListBeanData> adapter;
    private List<GoodsIdListBeanData> datas = new ArrayList<>();

    private class GetGoodsListImp extends DefaultRequestListener implements GetGoodsListListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context, errorMsg);
        }

        @Override
        public void getGoodsList(GoodsListData listData) {
            datas.clear();

            if (listData != null && listData.getGoodsList() != null) {
                if (listData.getGoodsList().size() > 0) {
                    llHasData.setVisibility(View.VISIBLE);
                    rlNoData.setVisibility(View.GONE);
                    datas.addAll(listData.getGoodsList());
                    adapter.setData(datas);
                    adapter.notifyDataSetChanged();
                } else {
                    llHasData.setVisibility(View.GONE);
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_list_layout);
        ButterKnife.bind(this);

        initView();

        initEvent();
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                requester.getGoodsList(context, stringArrayList, listImp);
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {

            }
        });
    }

    private void initView() {
        title = getIntent().getStringExtra("title");
        if (title!=null&&!title.equals("")) {
            tvTitle.setText(title);
        }else {
            tvTitle.setText("商品列表");
        }
        stringArrayList = getIntent().getStringArrayListExtra("goodsIdList");

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter = new CommonAdapter<GoodsIdListBeanData>(context, datas, R.layout.search_goods_gv_item) {
            @Override
            public void convert(ViewHolder helper, final GoodsIdListBeanData item) {
                helper.setText(R.id.tv_title, item.getGoodsName() +" "+item.getGoodsSerial());
                helper.setText(R.id.tv_price, getString(R.string.label_money) + NumberUtils.formatToDouble(item.getStorePrise()));
                ImageLoaderUtil.displayImage(item.getPath(),(ImageView) helper.getView(R.id.iv_icon));
                helper.setText(R.id.tv_num, "已售" + item.getGoodsSalenum() + "件");

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GoodsDetailsActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getGoodsId()));
                        startActivity(intent);
                    }
                });

                if (item.getGoodsNumType()==0) {
                    ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                }else {
                    ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                }

                helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent[] intents = new Intent[1];
                        Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                        intent.putExtra("gcilThird", item.getGcId());
                        intent.putExtra("keyWord", "找相似");
                        intent.putExtra("type", "catefortType");
                        intent.putExtra("searchType", "goods");
                        intents[0] = intent;
                        mContext.startActivities(intents);
                    }
                });
            }
        };

        collectionsGoodsGv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        requester.getGoodsList(context, stringArrayList, listImp);
    }
}
