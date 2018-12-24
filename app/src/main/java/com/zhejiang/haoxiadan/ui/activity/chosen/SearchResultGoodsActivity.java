package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.SearchGoodsListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.SearchChannelModel;
import com.zhejiang.haoxiadan.model.choseModel.SearchData;
import com.zhejiang.haoxiadan.model.choseModel.SearchGoodsPropertyBean;
import com.zhejiang.haoxiadan.model.choseModel.SearchLuceneListBean;
import com.zhejiang.haoxiadan.model.choseModel.SendIfBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.my.AdviceActivity;
import com.zhejiang.haoxiadan.ui.dialog.CommonUseDialog;
import com.zhejiang.haoxiadan.ui.dialog.CustomPopupDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CustomPopup;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.my.AdviceActivity;
import com.zhejiang.haoxiadan.ui.dialog.CustomPopupDialog;
import com.zhejiang.haoxiadan.ui.view.CustomPopup;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;

import com.zhejiang.haoxiadan.ui.view.RightSideslipLay;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/25.
 */

public class SearchResultGoodsActivity extends BaseFragmentActivity  {


    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_search)
    EditText tvSearch;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.gv)
    GridView gv;
    @BindView(R.id.spring_view)
    SpringView springView;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_change_bg)
    RelativeLayout rlChangeBg;
    @BindView(R.id.rl_show_bg)
    RelativeLayout rlShowBg;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_xuanze)
    TextView tvXuanze;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    LinearLayout navView;

    RightSideslipLay menuHeaderView;
    @BindView(R.id.tv_zonghe)
    TextView tvZonghe;
    @BindView(R.id.tv_zuixin)
    TextView tvZuixin;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.default_iv)
    ImageView defaultIv;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.iv_right)
    ImageView ivRight;

    private CommonAdapter<SearchLuceneListBean> adapter;
    private List<SearchLuceneListBean> data = new ArrayList<>();

    private int currentPage = 1, position = 0;
    private int showCount = 20;
    private String type = "", gcilThird ,classId;
    private String searchType, keyWord, everyType;
    private ArrayList<String> label = new ArrayList<>();
    private SearchImp searchImp = new SearchImp();
    private GoodsRequester goodsRequester = new GoodsRequester();
    private List<SearchChannelModel> channel = new ArrayList<>();

    ArrayList<SearchGoodsPropertyBean> arrayList = new ArrayList<>();

    Map<String,Object> map = new HashMap<>();

    private class SearchImp extends DefaultRequestListener implements SearchGoodsListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.getInstance(context).dismiss();

        }

        @Override
        public void getData(SearchData searchData) {
            LoadingDialog.getInstance(context).dismiss();

            if (searchData.getLuceneList().size()>0){
                rlNoData.setVisibility(View.GONE);
                llHasData.setVisibility(View.VISIBLE);
                if (currentPage!=1) {
                    if (currentPage<=searchData.getTotalPage()) {
                        data.addAll(searchData.getLuceneList());
                        adapter.setData(data);
                    }
                }else {
                    data = searchData.getLuceneList();
                    adapter.setData(data);
                }
                adapter.notifyDataSetChanged();
            }else {
                if (currentPage>=1) {
                    if (searchData.getTotalPage()==0) {
                        rlNoData.setVisibility(View.VISIBLE);
                        llHasData.setVisibility(View.GONE);
                    }
                }
            }

            if (tabLayout.getTabCount() == 2) {
                if (searchData.getChannel().size() > 0) {
                    channel = searchData.getChannel();
                    for (int i = 0; i < searchData.getChannel().size(); i++) {
                        tabLayout.addTab(tabLayout.newTab().setText(searchData.getChannel().get(i).getAppName()));
                    }
                }
            }

            if (searchData.getGoodsPropertyList() != null) {
                arrayList = searchData.getGoodsPropertyList();
            }
            menuHeaderView = new RightSideslipLay(SearchResultGoodsActivity.this, arrayList);
            navView.addView(menuHeaderView);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.search_result_goods_layout);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        searchType = getIntent().getStringExtra("searchType");
        keyWord = getIntent().getStringExtra("keyWord");
        everyType = getIntent().getStringExtra("type");

        tvSearch.setText(keyWord);


        if (everyType.equals("catefortType")) {
            gcilThird = getIntent().getStringExtra("gcilThird");
            ivRight.setVisibility(View.VISIBLE);
            rlChangeBg.setVisibility(View.VISIBLE);
            rlShowBg.setVisibility(View.GONE);
            tvName.setText(keyWord);
            if (keyWord.equals("找相似")) {
                map.put("gcIdThird", gcilThird);
                ivRight.setVisibility(View.GONE);
                rlChangeBg.setVisibility(View.GONE);
                rlShowBg.setVisibility(View.VISIBLE);
            }else {
                classId = getIntent().getStringExtra("classId");
                map.put("gcIdSecond",classId);
            }
        }

        map.put("searchType",searchType);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);

        initView();

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        adapter = new CommonAdapter<SearchLuceneListBean>(context, data, R.layout.search_goods_gv_item) {
            @Override
            public void convert(ViewHolder helper, final SearchLuceneListBean item) {
                helper.setText(R.id.tv_price, getString(R.string.label_money) + NumberUtils.formatToDouble(String.valueOf(item.getVo_store_price())));
                ImageLoaderUtil.displayImage(item.getVo_main_photo_url(), (ImageView) helper.getView(R.id.iv_icon));
                helper.setText(R.id.tv_title, item.getVo_title() +"  "+item.getVo_goods_serial());
                helper.setText(R.id.tv_num, "已售" + item.getVo_goods_salenum() + "件");

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, GoodsDetailsActivity.class);
                        intent.putExtra("goodsId", String.valueOf(item.getVo_id()));
                        intent.putExtra("frontImg", item.getVo_main_photo_url());
                        startActivity(intent);
                    }
                });


                if (!keyWord.equals("找相似")) {
                    helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent[] intents = new Intent[1];
                            Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                            intent.putExtra("gcilThird", String.valueOf(item.getGcIdThird()));
                            intent.putExtra("keyWord", "找相似");
                            intent.putExtra("type", "catefortType");
                            intent.putExtra("searchType", "goods");
                            intents[0] = intent;
                            mContext.startActivities(intents);
                        }
                    });
                }else {
                    helper.getView(R.id.tv_find_same).setVisibility(View.GONE);
                }
                ImageView ivShowGoodsType = helper.getView(R.id.tv_go_cart);

                try {
                    if (Integer.parseInt(item.getGoods_numType())==1){
                        ivShowGoodsType.setImageResource(R.mipmap.icon_goods2);

                    }else if (Integer.parseInt(item.getGoods_numType())==0){
                        ivShowGoodsType.setImageResource(R.mipmap.icon_order2);
                    }
                }catch (Exception e){}


            }
        };

        gv.setAdapter(adapter);


        tvSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    if (tvSearch.getText().toString().length() > 0) {
                        map.put("keyWord",tvSearch.getText().toString());
                        initData(position);
//                        if (everyType.equals("goodsType")) {
//                            goodsRequester.searchGoodsList(context, searchType, tvSearch.getText().toString(), type, currentPage, showCount, searchImp);
//                        } else if (everyType.equals("brand")) {
//                            goodsRequester.searchBrandList(context, searchType, tvSearch.getText().toString(), type, currentPage, showCount, searchImp);
//                        } else {
//                            goodsRequester.getCategoryList(context, searchType, gcilThird, type, currentPage, showCount, searchImp);
//                        }
//                        getHistoryData();
                    } else {
                        ToastUtil.show(context, "内容不能为空");
                    }
                }
                return false;
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                initData(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        map.put("orderType","desc");
        initData(0);

        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                map.put("currentPage",currentPage);
                initData(position);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                map.put("currentPage",currentPage);
                initData(position);
                springView.onFinishFreshAndLoad();
            }
        });

    }

    private void getHistoryData() {
        label.clear();
        String old_content = (String) SharedPreferencesUtil.get(context, Constants.search_content, "");
        if (old_content.equals("")) {
            label.addAll(new ArrayList<String>());
        } else {
            label.addAll(gson.fromJson(old_content, ArrayList.class));
        }
        if (label.size() > 0) {
            for (int n = 0; n < label.size(); n++) {
                if (label.get(n).equals(tvSearch.getText().toString())) {
                    label.remove(n);
                }
            }
        }
        label.add(tvSearch.getText().toString());
        SharedPreferencesUtil.put(context, Constants.search_content, gson.toJson(label));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initData(int pos) {
        LoadingDialog.getInstance(context).show();
//        if (!keyWord.equals("找相似")) {
            map.put("keyWord", tvSearch.getText().toString());
//        }
        if (pos==1){ //去拼单
            map.put("goodsType","1");
            if (map.containsKey("aGoods")){
                map.remove("aGoods");
            }
        }else {
            if (map.containsKey("goodsType")){
                map.remove("goodsType");
            }
            if (pos!=0){
                String name = tabLayout.getTabAt(pos).getText().toString();
                if (channel.size()>0){
                    for (int i = 0; i<channel.size();i++){
                        if (name.equals(channel.get(i).getAppName())){
                            map.put("aGoods",channel.get(i).getId());
                        }
                    }
                }
            }else {
                if (map.containsKey("aGoods")){
                    map.remove("aGoods");
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                goodsRequester.searchNewGoodslist(context,map,searchImp);
            }
        },200);
    }


    @Subscribe
    public void onEvent(SendIfBean bean) {
        if (bean.getType().equals("close")) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        if (!bean.getLower_price().equals("")){
            map.put("goodsCurrentPriceFloor",bean.getLower_price());
        }else {
            if (map.containsKey("goodsCurrentPriceFloor")){
                map.remove("goodsCurrentPriceFloor");
            }
        }

        if (!bean.getUpper_price().equals("")){
            map.put("goodsCurrentPriceCeiling",bean.getUpper_price());
        }else {
            if (map.containsKey("goodsCurrentPriceCeiling")){
                map.remove("goodsCurrentPriceCeiling");
            }
        }

        if (!bean.getGoodsList().equals("")){
            map.put("goodsPro",bean.getGoodsList());
        }else {
            if (map.containsKey("goodsPro")){
                map.remove("goodsPro");
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoadingDialog.getInstance(context).show();
                goodsRequester.searchNewGoodslist(context,map,searchImp);

            }
        },200);
    }

    @OnClick({R.id.tv_zonghe, R.id.tv_zuixin, R.id.tv_price, R.id.tv_xuanze,R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_zonghe:
                LoadingDialog.getInstance(context).show();
                map.put("orderBy","colligate");
                initColor(tvZonghe, tvZuixin, tvPrice, tvXuanze);
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                goodsRequester.searchNewGoodslist(context,map,searchImp);
                break;
            case R.id.tv_zuixin:
                LoadingDialog.getInstance(context).show();
                map.put("orderBy","addTime");
                initColor(tvZuixin, tvZonghe, tvPrice, tvXuanze);
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                goodsRequester.searchNewGoodslist(context,map,searchImp);
                break;
            case R.id.tv_price:
                LoadingDialog.getInstance(context).show();
                map.put("orderBy","storePrice");
                initColor(tvPrice, tvZonghe, tvZuixin, tvXuanze);
                defaultIv.setVisibility(View.GONE);
                cb.setVisibility(View.VISIBLE);
                if (cb.isChecked()) { 
                    cb.setChecked(false);
                    if (map.get("orderType").equals("desc")){
                        map.remove("orderType");
                        map.put("orderType","asc");
                    }
                } else {
                    cb.setChecked(true);
                    if (map.get("orderType").equals("asc")){
                        map.remove("orderType");
                        map.put("orderType","desc");
                    }
                }
                goodsRequester.searchNewGoodslist(context,map,searchImp);
                break;
            case R.id.tv_xuanze:
                initColor(tvXuanze, tvZonghe, tvZuixin, tvPrice);
                cb.setVisibility(View.GONE);
                defaultIv.setVisibility(View.VISIBLE);
                drawerLayout.openDrawer(GravityCompat.END);
                break;

            case R.id.iv_right:
                CommonUseDialog.showComDialog(context,ivRight);
                break;

        }

    }

    public void initColor(TextView tv1, TextView tv2, TextView tv3, TextView tv4) {
        tv1.setTextColor(getResources().getColor(R.color.main_red));
        tv2.setTextColor(getResources().getColor(R.color.text_black));
        tv3.setTextColor(getResources().getColor(R.color.text_black));
        tv4.setTextColor(getResources().getColor(R.color.text_black));
    }


}
