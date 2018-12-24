package com.zhejiang.haoxiadan.ui.fragment.my;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.CollectStoreListener;
import com.zhejiang.haoxiadan.business.request.my.DeleteCollectStoreListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.model.requestData.out.my.CollectStoreData;
import com.zhejiang.haoxiadan.model.requestData.out.my.CollectStoreDataBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class CollectionShopFragment extends BaseFragment {

    @BindView(R.id.ls_factory)
    ListView lsFactory;
    Unbinder unbinder;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;
    private View view;
    private List<CollectStoreDataBean> data = new ArrayList<>();
    private CommonAdapter<CollectStoreDataBean> adapter;

    private int currentPage = 1;
    private int showCount = 30;

    private UserRequester requester = new UserRequester();
    private CollectStoreImp imp = new CollectStoreImp();

    private class CollectStoreImp extends DefaultRequestListener implements CollectStoreListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getData(CollectStoreData collectStoreData) {
            sv.onFinishFreshAndLoad();
            if (collectStoreData.getFavoriteList().size()>0) {
                llHasData.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                data.clear();
                data.addAll(collectStoreData.getFavoriteList());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }else {
                llHasData.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
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
        view = LayoutInflater.from(context).inflate(R.layout.collection_shop_layout, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new CommonAdapter<CollectStoreDataBean>(context, data, R.layout.collect_shop_ls_item) {
            @Override
            public void convert(ViewHolder helper, final CollectStoreDataBean item) {
                helper.setText(R.id.tv_style, item.getMainIndustry());
                helper.setText(R.id.tv_store, item.getStoreName());
                ImageLoaderUtil.displayImage(item.getStorePhoto(),(ImageView) helper.getView(R.id.iv_icon));

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ShopActivity.class);
                        intent.putExtra("storeId", String.valueOf(item.getStoreId()));
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DeleteDialog dialog = new DeleteDialog(context, "提示", "删除此店铺", "确定");
                        dialog.show();
                        dialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                dialog.dismiss();
                                List<Integer> da = new ArrayList<Integer>();
                                da.add(item.getId());
                                requester.deleteCollectStore(context, getAccessToken(), da, deleteStoreImp);
                            }
                        });
                    }
                });
            }
        };

        lsFactory.setAdapter(adapter);


        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requester.myCollectStoreGoods(context, getAccessToken(), currentPage, showCount, imp);
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                requester.myCollectStoreGoods(context, getAccessToken(), currentPage, showCount, imp);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        requester.myCollectStoreGoods(context, getAccessToken(), currentPage, showCount, imp);
    }

    private DeleteStoreImp deleteStoreImp = new DeleteStoreImp();

    private class DeleteStoreImp extends DefaultRequestListener implements DeleteCollectStoreListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSuccess() {
            requester.myCollectStoreGoods(context, getAccessToken(), currentPage, showCount, imp);
        }
    }


}

