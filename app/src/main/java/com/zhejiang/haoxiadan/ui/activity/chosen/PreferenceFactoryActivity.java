package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.PrefenceFactoryListener;
import com.zhejiang.haoxiadan.business.request.chosen.SearchGoodsListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.PrefenceFactoryBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.PrefenceFactoryData;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.CartActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.UserCenterActivity;
import com.zhejiang.haoxiadan.ui.dialog.ShareDialog;
import com.zhejiang.haoxiadan.ui.popmenu.DropPopMenu;
import com.zhejiang.haoxiadan.ui.popmenu.MenuItem;
import com.zhejiang.haoxiadan.ui.view.CustomPopup;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/9/15.
 */

public class PreferenceFactoryActivity extends BaseFragmentActivity implements CustomPopup.IPopuWindowListener {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_zonghe)
    TextView tvZonghe;
    @BindView(R.id.tv_hangye)
    TextView tvHangye;
    @BindView(R.id.tv_yingye)
    TextView tvYingye;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.ls_factort)
    ListView lsFactort;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.view_line2)
    View viewLine2;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    @BindView(R.id.view_line3)
    View viewLine3;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.view_line)
    View viewLine;

    private CustomPopup alarmPopup;

    private int currentPage = 1;
    private int showCount = 30;
    private List<PrefenceFactoryBean> data = new ArrayList<>();
    private CommonAdapter<PrefenceFactoryBean> adapter;

    private GoodsRequester requester = new GoodsRequester();
    private PrefenceImp imp = new PrefenceImp();

    @Override
    public void dispose() {
        if (alarmPopup != null) {
            if (alarmPopup.isShowing()) {
                alarmPopup.dismiss();//关闭PopupWindow
            }
        }
    }

    @Override
    public void setName(String name) {
        tvHangye.setText(name);
        texs = "2";//女装
        requester.prefenceFactory(context, currentPage, showCount, null,texs,imp);
    }

    private class PrefenceImp extends DefaultRequestListener implements PrefenceFactoryListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getData(PrefenceFactoryData factoryData) {
            sv.onFinishFreshAndLoad();
            if (currentPage <= factoryData.getAllPage()) {
                if (currentPage == 1) {
                    data.clear();
                }
                llHasData.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                data.addAll(factoryData.getStoreList());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }

            if (data.size()<0){
                llHasData.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference_factory_layout);
        ButterKnife.bind(this);
        tvTitle.setText("优选工厂");

        adapter = new CommonAdapter<PrefenceFactoryBean>(context, data, R.layout.factory_ls_item) {
            @Override
            public void convert(ViewHolder helper, final PrefenceFactoryBean item) {
                helper.setText(R.id.tv_style, "主营业务: " + item.getMainIndustry());
                helper.setText(R.id.tv_product, item.getStoreName());
                ImageLoaderUtil.displayImage(item.getStoreLogo(),(ImageView) helper.getView(R.id.iv_icon));

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, ShopActivity.class);
                        intent.putExtra("storeId", String.valueOf(item.getId()));
                        startActivity(intent);
                    }
                });
            }
        };
        lsFactort.setAdapter(adapter);

        requester.prefenceFactory(context, currentPage, showCount,null,null, imp);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                if (type!=null) {
                    requester.prefenceFactory(context, currentPage, showCount, type,null,imp);
                }

                if (texs!=null){
                    requester.prefenceFactory(context, currentPage, showCount, null,texs,imp);
                }

                if (texs==null&&type==null){
                    requester.prefenceFactory(context, currentPage, showCount, null,null,imp);
                }
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                if (type!=null) {
                    requester.prefenceFactory(context, currentPage, showCount, type,null,imp);
                }

                if (texs!=null){
                    requester.prefenceFactory(context, currentPage, showCount, null,texs,imp);
                }

                if (texs==null&&type==null){
                    requester.prefenceFactory(context, currentPage, showCount, null,null,imp);
                }

                sv.onFinishFreshAndLoad();
            }
        });

    }

    @OnClick({R.id.iv_left, R.id.tv_zonghe, R.id.tv_hangye, R.id.tv_yingye})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_hangye:
                type=null;
//                initDialog();
                alarmPopup = new CustomPopup(context, ViewGroup.LayoutParams.MATCH_PARENT, 830, this);
                alarmPopup.show(tvHangye);
                tvZonghe.setTextColor(getResources().getColor(R.color.text_black));
                viewLine.setVisibility(View.GONE);
                tvHangye.setTextColor(getResources().getColor(R.color.main_red));
                viewLine2.setVisibility(View.VISIBLE);
                tvYingye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine3.setVisibility(View.GONE);
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_zonghe :
                type=null;
                texs=null;
                tvHangye.setText("主营行业");
                requester.prefenceFactory(context, currentPage, showCount, null,null,imp);
                tvYingye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine3.setVisibility(View.GONE);
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                tvHangye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine2.setVisibility(View.GONE);
                tvZonghe.setTextColor(getResources().getColor(R.color.main_red));
                viewLine.setVisibility(View.VISIBLE);
                break;

            case R.id.tv_yingye :
                texs=null;
                tvZonghe.setTextColor(getResources().getColor(R.color.text_black));
                viewLine.setVisibility(View.GONE);
                tvHangye.setTextColor(getResources().getColor(R.color.text_black));
                viewLine2.setVisibility(View.GONE);
                tvYingye.setTextColor(getResources().getColor(R.color.main_red));
                viewLine3.setVisibility(View.VISIBLE);
                defaultIv.setVisibility(View.GONE);
                cb.setVisibility(View.VISIBLE);
                if (cb.isChecked()) {
                    cb.setChecked(false);
                    type = "0";
                    requester.prefenceFactory(context, currentPage, showCount,type,null, imp);
                } else {
                    cb.setChecked(true);
                    type = "1";
                    requester.prefenceFactory(context, currentPage, showCount,type,null, imp);
                }

                break;
        }
    }

    private void initDialog() {
        DropPopMenu dropPopMenu = new DropPopMenu(context);
        dropPopMenu.setTriangleIndicatorViewColor(Color.WHITE);
        dropPopMenu.setBackgroundResource(R.drawable.bg_drop_pop_menu_white_shap);
        dropPopMenu.setItemTextColor(Color.BLACK);
        dropPopMenu.setIsShowIcon(true);
        dropPopMenu.setOnItemClickListener(new DropPopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, MenuItem menuItem) {

            }
        });
        dropPopMenu.setMenuList(getIconMenuList());

        dropPopMenu.show(tvHangye);
    }

    private List<MenuItem> getIconMenuList() {
        return null;
    }

    private String texs, type ;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmPopup!=null){
            alarmPopup.dismiss();
            alarmPopup=null;
        }
    }
}
