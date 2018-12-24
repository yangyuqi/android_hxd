package com.zhejiang.haoxiadan.ui.fragment.chosen;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.SelectVisualFloorAllListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.model.common.ForwardItem;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.model.common.ShopFloor;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopGoodsMoreActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoods2Adapter;
import com.zhejiang.haoxiadan.ui.adapter.chosen.ShopFloorAdapter;
import com.zhejiang.haoxiadan.ui.view.DividerItemDecoration;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.ui.view.ShopFloorGoodsListView;
import com.zhejiang.haoxiadan.ui.view.ShopFloorImgDownTitleView;
import com.zhejiang.haoxiadan.ui.view.ShopFloorRectImgView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopHomeFragment extends Fragment {
    private static final String ARG_PARAM_STOREID = "storeId";

    private String storeId;
    private List<ShopFloor> allFloors = new ArrayList<>();//所有楼层

    private LinearLayout rootView;
    private LinearLayout seeMoreLl;

    private ShopRequester shopRequester;
    private SelectVisualFloorAllListenerImpl selectVisualFloorAllListener;
    private class SelectVisualFloorAllListenerImpl extends DefaultRequestListener implements SelectVisualFloorAllListener{

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSelectVisualFloorAllSuccess(List<ShopFloor> floors) {
            allFloors.clear();
            allFloors.addAll(floors);
            refreshView();
        }
    }

    public ShopHomeFragment() {
        // Required empty public constructor
    }
    public static ShopHomeFragment newInstance(String storeId) {
        ShopHomeFragment fragment = new ShopHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_STOREID, storeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeId = getArguments().getString(ARG_PARAM_STOREID);
        }

        shopRequester = new ShopRequester();
        selectVisualFloorAllListener = new SelectVisualFloorAllListenerImpl();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_home, container, false);

        initView(view);
        initEvent();
        initData();

        return view;
    }

    private void initView(View view){
        rootView = (LinearLayout)view.findViewById(R.id.ll_root);
        seeMoreLl = (LinearLayout) view.findViewById(R.id.ll_see_more);

    }

    private void initEvent(){
        seeMoreLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ShopGoodsMoreActivity.class);
                intent.putExtra("storeId",storeId);
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData(){
        shopRequester.selectVisualFloorAll(getActivity(),storeId,selectVisualFloorAllListener);
    }

    private void refreshView(){
        rootView.removeAllViews();
        buildFloors();
    }


    //构建楼层
    private void buildFloors(){
        for(ShopFloor floor: allFloors){
            switch (floor.getType()){
                case FLOOR_IMG_DOWN_TITLE:
                    ShopFloorImgDownTitleView shopFloorImgDownTitleView = new ShopFloorImgDownTitleView(getActivity());
                    shopFloorImgDownTitleView.setData(floor);
                    rootView.addView(shopFloorImgDownTitleView);
                    rootView.addView(getDivider());
                    break;
                case FLOOR_IMG_RECT:
                    ShopFloorRectImgView shopFloorRectImgView = new ShopFloorRectImgView(getActivity());
                    shopFloorRectImgView.setData(floor);
                    rootView.addView(shopFloorRectImgView);
                    rootView.addView(getDivider());
                    break;
                case FLOOR_GOODSLIST:
                    ShopFloorGoodsListView shopFloorGoodsListView = new ShopFloorGoodsListView(getActivity());
                    shopFloorGoodsListView.setData(floor);
                    rootView.addView(shopFloorGoodsListView);
                    rootView.addView(getDivider());
                    break;
            }
        }
    }
    private View getDivider(){
        View divider = new View(getActivity());
        LinearLayout.LayoutParams productLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,30);
        divider.setLayoutParams(productLayoutParams);
        divider.setBackgroundResource(R.color.bg_gray);
        return divider;
    }
}
