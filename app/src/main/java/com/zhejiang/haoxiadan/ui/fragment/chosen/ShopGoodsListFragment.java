package com.zhejiang.haoxiadan.ui.fragment.chosen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.Goods;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.adapter.cart.CommonGoods2Adapter;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopGoodsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopGoodsListFragment extends BaseFragment {

    private SpringView springView;
    private GridView gridView;
    private CommonGoods2Adapter adapter;
    private List<Goods> goodses = new ArrayList<>();


    private static final String ARG_PARAM_TYPE = "TYPE";

    public ShopGoodsListFragment() {
        // Required empty public constructor
    }

    public static ShopGoodsListFragment newInstance(ArrayList<Goods> goodsList) {
        ShopGoodsListFragment fragment = new ShopGoodsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_TYPE, goodsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            List<Goods> goodsList = (ArrayList<Goods>)getArguments().getSerializable(ARG_PARAM_TYPE);
            goodses.clear();
            goodses.addAll(goodsList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_goods_list, container, false);
        initView(view);
        initEvent();
        initData();

        return view;
    }

    private void initView(View view){
        springView = (SpringView)view.findViewById(R.id.spring_view);
        gridView = (GridView)view.findViewById(R.id.gv_goods);

        adapter = new CommonGoods2Adapter(getActivity(),goodses);
        gridView.setAdapter(adapter);
    }

    private void initEvent(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                intent.putExtra("goodsId",goodses.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initData(){
        adapter.notifyDataSetChanged();
    }

}
