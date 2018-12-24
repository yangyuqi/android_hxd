package com.zhejiang.haoxiadan.ui.fragment.hot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.hot.GiveUpListener;
import com.zhejiang.haoxiadan.business.request.hot.HotDetailsDataListener;
import com.zhejiang.haoxiadan.business.request.hot.HotDetailsListener;
import com.zhejiang.haoxiadan.business.request.hot.HotListListener;
import com.zhejiang.haoxiadan.business.request.hot.HotRequest;
import com.zhejiang.haoxiadan.model.requestData.out.hot.DataList;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotHotData;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotInfoListBean;
import com.zhejiang.haoxiadan.model.requestData.out.hot.HotspotTypeList;
import com.zhejiang.haoxiadan.model.requestData.out.hot.TypeListBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.common.H5Activity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.common.NewH5Activity;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HotFragment extends BaseFragment {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.view_root)
    RelativeLayout viewRoot;
    @BindView(R.id.goods_details_tl)
    TabLayout goodsDetailsTl;
    @BindView(R.id.fragment_hot_ls)
    ListView fragmentHotLs;
    Unbinder unbinder;
    @BindView(R.id.sv)
    SpringView sv;

    private List<DataList> data = new ArrayList<>();


    private CommonAdapter<DataList> adapter;
    List<TypeListBean> hotspotTypeList = new ArrayList<>();
    private String title;
    private int id_id;
    private HotRequest request = new HotRequest();
    private HotImp imp = new HotImp();
    private HotDetailsImp detailsImp = new HotDetailsImp();
    private HotHotImp hotHotImp = new HotHotImp();
    private GiveImp giveImp = new GiveImp();

    private class HotDetailsImp extends DefaultRequestListener implements HotDetailsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetData(HotspotInfoListBean listBean) {
            data.clear();
            data.addAll(listBean.getDataList());
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    private class GiveImp extends DefaultRequestListener implements GiveUpListener {

        @Override
        public void onSuccess() {
            initNewData(id_id);
        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context, errorMsg);
        }
    }

    private class HotHotImp extends DefaultRequestListener implements HotDetailsDataListener {

        @Override
        public void getString(HotHotData s) {
//            Intent intent = new Intent(context, H5Activity.class);
//            intent.putExtra("title", title);
//            intent.putExtra("content", s.getHotspotInfo());
//            intent.putExtra("url","https://wap.haoxiadan.cn/hotspot/"+108+"?app=app");
//            startActivity(intent);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    private class HotImp extends DefaultRequestListener implements HotListListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetData(HotspotTypeList list) {
            hotspotTypeList.clear();
            hotspotTypeList.addAll(list.getHotspotTypeList());
            for (int i = 0; i < hotspotTypeList.size(); i++) {
                goodsDetailsTl.addTab(goodsDetailsTl.newTab().setText(hotspotTypeList.get(i).getTypeName()));
            }
            initNewData(hotspotTypeList.get(0).getId());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_hot, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initNewData(id_id);
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                sv.onFinishFreshAndLoad();
            }
        });
    }

    private void initView() {
        ivLeft.setVisibility(View.GONE);
        viewRoot.setBackgroundResource(R.color.main_red);
        tvTitle.setTextColor(getResources().getColor(R.color.bg_white));
        tvTitle.setText("热点");
        adapter = new CommonAdapter<DataList>(context, data, R.layout.hot_fragment_ls_item) {
            @Override
            public void convert(final ViewHolder helper, final DataList item) {
                helper.setText(R.id.hot_fragment_ls_item_tv, item.getDate());

                helper.setText(R.id.tv_hot_title, item.getTitle());
                helper.setText(R.id.tv_sub_title, item.getSubtitle());
                ImageLoaderUtil.displayImage(item.getCoverPath(),(ImageView) helper.getView(R.id.iv_hot));
                helper.setText(R.id.tv_num, String.valueOf(item.getGiveUp()));
                String[] strings = item.getDate().split("/");
                helper.setText(R.id.tv_date,strings[0]+"-"+strings[1]+"-"+strings[2]);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        title = item.getTitle();
                        Intent intent = new Intent(context, H5Activity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("url","https://wap.haoxiadan.cn/hotspot/"+item.getId()+"?app=app");
                        startActivity(intent);
                    }
                });

                final CheckBox cb = helper.getView(R.id.goods_details_layout_cb);
                if (item.getType()!=null) {
                    if (item.getType().equals("1")) {
                        cb.setChecked(true);
                    } else {
                        cb.setChecked(false);
                    }
                }

                cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!getAccessToken().equals("")) {
                                request.giveUp(context, getAccessToken(), item.getId(), giveImp);
                            }else {
                                cb.setChecked(false);
                                startActivity(new Intent(context,LoginActivity.class));
                            }
                        }
                    });

            }
        };
        fragmentHotLs.setAdapter(adapter);


        goodsDetailsTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initNewData(hotspotTypeList.get(tab.getPosition()).getId());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initNewData(int id) {
        id_id = id;
        request.getHotDetails(context, getAccessToken(), id, detailsImp);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initData() {
        request.getHotList(context, imp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
