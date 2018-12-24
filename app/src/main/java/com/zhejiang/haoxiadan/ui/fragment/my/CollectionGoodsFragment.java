package com.zhejiang.haoxiadan.ui.fragment.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.CollectionGoodsListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.out.CollectGoodsBean;
import com.zhejiang.haoxiadan.model.requestData.out.CollectGoodsBeanData;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchResultGoodsActivity;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.PublicUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class CollectionGoodsFragment extends BaseFragment {

    @BindView(R.id.collections_goods_gv)
    GridView collectionsGoodsGv;
    Unbinder unbinder;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_has_data)
    LinearLayout rlHasData;
    private View view;
    private CommonAdapter<CollectGoodsBean> adapter;
    private List<CollectGoodsBean> data = new ArrayList<>();
    OpenFlightAloneBean bean;
    private int currentPage = 1;
    private int showCount = 10;

    private UserRequester requester = new UserRequester();
    private CollectionImp collectionImp = new CollectionImp();

    private class CollectionImp extends DefaultRequestListener implements CollectionGoodsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void coollectData(CollectGoodsBeanData mdata) {
            sv.onFinishFreshAndLoad();
            if (currentPage <= mdata.getTotalPage()) {
                if (currentPage == 1) {
                    data.clear();
                }
                if (mdata.getFavoriteList().size() > 0) {
                    rlHasData.setVisibility(View.VISIBLE);
                    rlNoData.setVisibility(View.GONE);
                    data.addAll(mdata.getFavoriteList());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                } else {
                    rlHasData.setVisibility(View.GONE);
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }else {
                if (mdata.getFavoriteList().size()<=0&&data.size()<=0) {
                    rlHasData.setVisibility(View.GONE);
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(context).inflate(R.layout.collection_goods_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CommonAdapter<CollectGoodsBean>(context, data, R.layout.collections_goods_gv_item) {
            @Override
            public void convert(ViewHolder helper, final CollectGoodsBean item) {
                helper.setText(R.id.tv_title, item.getGoodsName() +"  " +item.getGoodsSerial());
                helper.setText(R.id.tv_price, getString(R.string.label_money) + NumberUtils.formatToDouble(String.valueOf(item.getStorePrice())));
                ImageLoaderUtil.displayImage(item.getGoodsPhoto(),(ImageView) helper.getView(R.id.iv_icon));
                if (String.valueOf(item.getGoodsSalenum()) != null) {
                    helper.setText(R.id.tv_num, "已售" + item.getGoodsSalenum() + "件");
                }
                if (item.getMonthlySales()==null){
                    helper.setText(R.id.tv_num, "已售0件");
                }
                helper.getView(R.id.rl_rltop).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GoodsDetailsActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getGoodsId()));
                        bean = new OpenFlightAloneBean("close");
                        startActivity(intent);
                    }
                });

                if (item.getGoodsNumType()!=null) {
                    if (item.getGoodsNumType().equals("0")) {
                        ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                    } else {
                        ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                    }
                }

                helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 去商品搜索结果页
                        Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                        intent.putExtra("gcilThird", item.getGcId());
                        intent.putExtra("keyWord", item.getGoodsName());
                        intent.putExtra("type", "catefortType");
                        intent.putExtra("searchType", "goods");
                        mContext.startActivity(intent);
                    }
                });
            }
        };

        collectionsGoodsGv.setAdapter(adapter);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requester.myCollectionGoods(context, getAccessToken(), currentPage, showCount, collectionImp);
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                requester.myCollectionGoods(context, getAccessToken(), currentPage, showCount, collectionImp);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        super.onStop();
        data.clear();
        if (bean != null) {
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        requester.myCollectionGoods(context, getAccessToken(), currentPage, showCount, collectionImp);
    }
}
