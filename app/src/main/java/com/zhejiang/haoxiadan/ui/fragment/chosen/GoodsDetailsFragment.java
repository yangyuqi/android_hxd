package com.zhejiang.haoxiadan.ui.fragment.chosen;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.AddFavoriteStoreListener;
import com.zhejiang.haoxiadan.business.request.chosen.CarriageFareListener;
import com.zhejiang.haoxiadan.business.request.chosen.FlightRuleListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetDiscussListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsDetailsListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.NewMyFooterListener;
import com.zhejiang.haoxiadan.business.request.chosen.QueryStoreByIdListener;
import com.zhejiang.haoxiadan.business.request.chosen.ShopRequester;
import com.zhejiang.haoxiadan.model.choseModel.CarriageModel;
import com.zhejiang.haoxiadan.model.choseModel.FlightLsItemData;
import com.zhejiang.haoxiadan.model.choseModel.KeyValueModel;
import com.zhejiang.haoxiadan.model.common.Shop;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsPhotoBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsRuleBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsStyleBean;
import com.zhejiang.haoxiadan.model.requestData.in.PostTiredPriceData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CrowdFundRulesData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentDataBean;
import com.zhejiang.haoxiadan.model.requestData.out.GetDetailsData;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsBean;
import com.zhejiang.haoxiadan.model.requestData.out.GetGoodsIntData;
import com.zhejiang.haoxiadan.model.requestData.out.TiredPriceData;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.FlighRuleActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.FlightAloneBottomActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.FlightNewRuleActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.FlightSelectCityBottomActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsPropertyActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.PreviewPictureActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.dialog.CommonUseDialog;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.dialog.ShareDialog;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.popmenu.DropPopMenu;
import com.zhejiang.haoxiadan.ui.popmenu.MenuItem;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.PublicUtils;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class GoodsDetailsFragment extends BaseFragment implements AMapLocationListener {

    @BindView(R.id.roll_pv)
    RollPagerView rollPv;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_sale_num)
    TextView tvGoodsSaleNum;
    @BindView(R.id.ll_show_bottom)
    RelativeLayout llShowBottom;
    @BindView(R.id.ls_get_discuss)
    NoScrollListView lsGetDiscuss;
    @BindView(R.id.tv_see_more_discuss)
    TextView tvSeeMoreDiscuss;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_style)
    TextView tvStyle;
    @BindView(R.id.ll_chat_product)
    LinearLayout llChatProduct;
    @BindView(R.id.ll_go_shop)
    LinearLayout llGoShop;
    Unbinder unbinder;
    @BindView(R.id.scroll_view)
    ScrollView scrollView;
    @BindView(R.id.ll_flight_rule)
    LinearLayout llFlightRule;
    @BindView(R.id.tv_show_current)
    TextView tvShowCurrent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_shop_name)
    TextView tvShopName;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_hour)
    TextView tvHour;
    @BindView(R.id.tv_second)
    TextView tvSecond;
    @BindView(R.id.tv_mile)
    TextView tvMile;
    @BindView(R.id.tv_chat_shop)
    TextView tvChatShop;
    @BindView(R.id.tv_go_shop)
    TextView tvGoShop;
    @BindView(R.id.iv_under_carriage)
    ImageView ivUnderCarriage;
    @BindView(R.id.wb_details)
    WebView wbDetails;
    @BindView(R.id.ll_high)
    LinearLayout llHigh;
    @BindView(R.id.rl_get_width)
    RelativeLayout rlGetWidth;
    @BindView(R.id.goods_details_gv)
    NoScrollGridView goodsDetailsGv;
    @BindView(R.id.tv_city_name)
    TextView tvCityName;
    @BindView(R.id.ll_select_area)
    LinearLayout llSelectArea;
    @BindView(R.id.tv_area_name)
    TextView tvAreaName;
    @BindView(R.id.gv_column)
    NoScrollGridView gvColumn;
    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.ll_time)
    LinearLayout llTime;
    @BindView(R.id.rl_goods_property)
    RelativeLayout rlGoodsProperty;
    @BindView(R.id.rl_goods_explain)
    RelativeLayout rlGoodsExplain;
    @BindView(R.id.tv_sale_price)
    TextView tvSalePrice;
    @BindView(R.id.iv_show_goods_type)
    ImageView ivShowGoodsType;
    @BindView(R.id.iv_collect_shop)
    ImageView ivCollectShop;

    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.goods_details_tl)
    TabLayout goodsDetailsTl;
    @BindView(R.id.ib_carts)
    ImageView ibCarts;
    private View view;

    private CountDownTimer countDownTimer;
    private DropPopMenu mDropPopMenu;

    private String goodsType;
    private int position;
    private BannerNormalAdapter normalAdapter;
    private ArrayList<String> normal_lsit = new ArrayList<>();
    private ArrayList<TiredPriceData> tired_data_list = new ArrayList<>();
    private ArrayList<GetGoodsIntData> invent_data_list = new ArrayList<>();

    private List<DiscussContentDataBean> data = new ArrayList<>();
    private CommonAdapter<DiscussContentDataBean> adapter;

    PostGoodsPhotoBean goodsPhotoBean = new PostGoodsPhotoBean();

    private long endTime;
    private int goodsNumType, goodsStatus;
    private String goodsId, type, storeId, goodsMainPhoto;
    private ArrayList<FlightLsItemData> new_data = new ArrayList<>();
    private String frontImg ,shareContent;

    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    private CommonAdapter<TiredPriceData> price_adapter;
    private GoodsRequester requester = new GoodsRequester();
    private GoodsImp imp = new GoodsImp();
    private MyFooterImp footerImp = new MyFooterImp();
    private CarrageImp carrageImp = new CarrageImp();

    ArrayList<CarriageModel> list = new ArrayList<>();
    GetDetailsData getData;

    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;

    private int goodsLimit;



    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            try {
                JSONObject object = new JSONObject(gson.toJson(aMapLocation));
                if (object.getString("q") != null) {
                    if (!object.getString("q").equals("success")) {
                        ToastUtil.show(context, object.getString("q"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (aMapLocation.getCity() != null) {
                tvCityName.setText(aMapLocation.getProvince() + aMapLocation.getCity());
                mlocationClient.stopLocation();
                requester.getCarriageFare(context, aMapLocation.getCity(), "", list, carrageImp);
            }
        }
    }


    private class MyFooterImp extends DefaultRequestListener implements NewMyFooterListener {

        @Override
        protected void onRequestFail() {

        }
    }

    private class GoodsImp extends DefaultRequestListener implements GoodsDetailsListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.getInstance(context).dismiss();

        }

        @Override
        public void getGoods(final GetDetailsData getDetailsData) {
            LoadingDialog.getInstance(context).dismiss();
            if (getDetailsData != null) {
                EventBus.getDefault().post(getDetailsData);
                getData = getDetailsData;
                goodsType = getDetailsData.getGoods().getGoodsType();
                shareContent = getDetailsData.getHaoCommand();
                goodsStatus = getDetailsData.getGoods().getGoodsStatus();
                goodsMainPhoto = getDetailsData.getGoods().getGoods_main_photo();
                requester.newMyFooter(context, getAccessToken(), String.valueOf(getDetailsData.getGoods().getId()), String.valueOf(getDetailsData.getGoods().getStoreId()), footerImp);
                shopRequester.queryStoreById(context, getAccessToken(), String.valueOf(getDetailsData.getGoods().getStoreId()), queryStoreByIdListener);
                storeId = String.valueOf(getDetailsData.getGoods().getStoreId());
                normal_lsit.clear();
                tired_data_list.clear();
                invent_data_list.clear();
                goodsNumType = getDetailsData.getGoods().getGoodsNumType();
                if (goodsNumType == 0) {
                    ivShowGoodsType.setImageResource(R.mipmap.icon_orderxxhdpi);
                } else if (goodsNumType == 1) {
                    ivShowGoodsType.setImageResource(R.mipmap.icon_goodsxxhdpi);
                }
                tvGoodsName.setText(getDetailsData.getGoods().getGoodsName() + "  " + getDetailsData.getGoods().getGoodsSerial());
                tvGoodsSaleNum.setText(String.valueOf(getDetailsData.getGoods().getGoodsSalenum()));
                tvAddress.setText(getDetailsData.getGoods().getStoreArea());
                normal_lsit.addAll(getDetailsData.getGoods().getGoodsInfoPhotePath());
                if (normal_lsit.size() > 0) {
                    normalAdapter = new BannerNormalAdapter(normal_lsit);
                    rollPv.setAdapter(normalAdapter);
                }
                tired_data_list.addAll(getDetailsData.getGoods().getTierdPriceAll());
                invent_data_list.addAll(getDetailsData.getGoods().getGoodsInvenDetail());
                wbDetails.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            wbDetails.getSettings()
                                    .setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                        }
                    }
                    });
                wbDetails.loadDataWithBaseURL(null, getNewContent(getDetailsData.getGoods().getGoods_details()), "text/html", "utf-8", null);
                tvPrice.setText(getString(R.string.label_money) + NumberUtils.formatToDouble(String.valueOf(getDetailsData.getGoods().getStorePrice())));

                tvAreaName.setText(getDetailsData.getGoods().getStoreArea());

                doWork(getDetailsData.getGoods());
            }

            if (getDetailsData.getGoods().getGoodsType().equals("0")) { //普通商品
                rlBottom.setVisibility(View.GONE);
                goodsLimit = getDetailsData.getGoods().getGoodsLimit();
            } else { //拼单商品
                rlBottom.setVisibility(View.VISIBLE);
                requester.flightRule(context, goodsId, ruleImp);
            }

            initTime(getDetailsData.getGoods().getFightGoodsEndTime() - getDetailsData.getCurrentTime());
            if (getDetailsData.getGoods().getGoodsStatus() != 0) {
                ivUnderCarriage.setVisibility(View.VISIBLE);
            } else {
                    if (getDetailsData.getGoods().getFightGoodsEndTime() - getDetailsData.getCurrentTime()<=0&&!getDetailsData.getGoods().getGoodsType().equals("0")){
                        ivUnderCarriage.setVisibility(View.VISIBLE);
                    }else {
                        ivUnderCarriage.setVisibility(View.GONE);
                    }
            }

            if (tired_data_list.size() > 0) {
                price_adapter = new CommonAdapter<TiredPriceData>(context, tired_data_list, R.layout.goods_details_gv_price_item) {
                    @Override
                    public void convert(ViewHolder helper, TiredPriceData item) {
                        helper.setText(R.id.goods_price_item_tv, context.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getPrice()));
                        if (item.getCount().indexOf("以上") > 0) {
                            String[] m = item.getCount().split("-");
                            helper.setText(R.id.goods_num_item_tv, "≥" + m[0] + "件");
                        } else {
                            helper.setText(R.id.goods_num_item_tv, item.getCount() + "件");
                        }

                    }
                };

                goodsDetailsGv.setAdapter(price_adapter);
            }

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context, errorMsg);
        }
    }

    private class CarrageImp extends DefaultRequestListener implements CarriageFareListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getShip(String shipPrice) {
            tvSalePrice.setText("快递 ：" + getString(R.string.label_money) + shipPrice);
        }
    }

    private void initTime(final long ln) {
        endTime = ln;
        if (ln > 1000) {
            countDownTimer = new CountDownTimer(ln, 1000) {
                @Override
                public void onTick(long l) {
                    String[] strings = TimeUtils.getTimeDiffString(l).split(",");
                    tvDay.setText(strings[0]);
                    tvHour.setText(strings[1]);
                    tvSecond.setText(strings[2]);
                    tvMile.setText(strings[3]);
                }

                @Override
                public void onFinish() {

                }
            };
            countDownTimer.start();
        } else {
        }

        if (goodsStatus != 0) {
            ivUnderCarriage.setVisibility(View.VISIBLE);
        } else {
            ivUnderCarriage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.goods_details_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rollPv.getLayoutParams();
        lp.height = win_width;
        rollPv.setLayoutParams(lp);
        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsId = bundle.getString("id");
            frontImg = bundle.getString("frontImg");
        }

        if (goodsId != null) {
            list.add(new CarriageModel((int) Double.parseDouble(goodsId), 1));
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lsGetDiscuss.setFocusable(false);
        initEvent();
        initData();
        initView();
    }

    private void initView() {
        mlocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions((GoodsDetailsActivity) context);
            permissions.request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        mlocationClient.startLocation();
                    }
                }
            });
        } else {
            mlocationClient.startLocation();
        }


        tvShowCurrent.setText(String.valueOf(1) + "/" + String.valueOf(normal_lsit.size()));
        data.clear();
        adapter = new CommonAdapter<DiscussContentDataBean>(context, data, R.layout.discuss_ls_item) {
            @Override
            public void convert(ViewHolder helper, DiscussContentDataBean item) {
                helper.setText(R.id.tv_name, item.getUserName());
                ImageLoaderUtil.displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.user_civ));
                helper.setText(R.id.tv_content, item.getEvaluateInfo());
                GridView gv = helper.getView(R.id.no_gv);
                RatingBar ratingBar = helper.getView(R.id.ratingbar_one);
                ratingBar.setRating(item.getDescriptionEvaluate());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long time = item.getAddTtime();
                String d = format.format(time);
                helper.setText(R.id.tv_time, d);
                CommonAdapter<String> m_adapter = new CommonAdapter<String>(context, item.getEvaluatePhotos(), R.layout.image_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, String item) {
                        ImageView imageView = helper.getView(R.id.iv_icon);
                        ImageLoaderUtil.displayImage(item.replaceAll(" ", ""), imageView);
                    }
                };
                gv.setAdapter(m_adapter);
            }
        };

        addFavoriteStoreListener = new AddFavoriteStoreListenerImpl();

        lsGetDiscuss.setAdapter(adapter);
        goodsDetailsGv.setFocusable(false);
        gvColumn.setFocusable(false);
        rollPv.setHintView(new ColorPointHintView(context, context.getResources().getColor(R.color.transparent), context.getResources().getColor(R.color.transparent)));

        goodsDetailsTl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    scrollView.smoothScrollTo(0,0);
                }else if (tab.getPosition()==1){
                    scrollView.smoothScrollTo(0,llHigh.getHeight());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            private int lastY = 0;
            private int touchEventId = -9983761;
            Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    View scroller = (View) msg.obj;

                    if (msg.what == touchEventId) {
                        if (lastY == scroller.getScrollY()) {
                            //停止了，此处你的操作业务
                            if (lastY>=0&&lastY<llHigh.getHeight()){
                                goodsDetailsTl.getTabAt(0).select();
                            }else if (lastY>=llHigh.getHeight()) {
                                goodsDetailsTl.getTabAt(1).select();
                            }
                        } else {
                            handler.sendMessageDelayed(handler.obtainMessage(touchEventId, scroller), 1);
                            lastY = scroller.getScrollY();
                        }
                    }
                }
            };


            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int eventAction = motionEvent.getAction();
                switch (eventAction) {
                    case MotionEvent.ACTION_UP:
                        handler.sendMessageDelayed(handler.obtainMessage(touchEventId, view), 5);
                        break;
                    default:
                        break;
                }
                return false;            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
//        goodsId = "3441";
        LoadingDialog.getInstance(context).show();
//        if (type==null) {
            requester.getGoodsDetails(context, getAccessToken(), goodsId, imp);
//            requester.getGoodsDetailsWorsted(context,getAccessToken(),"E4C5884A44A90824CF0756BB0C",imp);
//        }else {
//            requester.getGoodsDetailsWorsted(context,getAccessToken(),goodsId,imp);
//        }
        requester.getDiscuss(context, goodsId, 1, 2, getDiscussImp);
//        requester.flightRule(context, goodsId, ruleImp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mlocationClient.stopLocation();
        unbinder.unbind();
    }

    private class BannerNormalAdapter extends StaticPagerAdapter {

        private List<String> banner_date;

        public BannerNormalAdapter(List<String> entity) {
            banner_date = entity;
        }


        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            ImageLoaderUtil.displayImage(banner_date.get(position), view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return banner_date.size();
        }
    }

    @OnClick({R.id.ll_show_bottom, R.id.ll_flight_rule, R.id.tv_see_more_discuss, R.id.tv_price, R.id.tv_go_shop, R.id.tv_chat_shop, R.id.rl_goods_property, R.id.rl_goods_explain,R.id.btnBack,R.id.ib_carts})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_show_bottom:
                if (goodsStatus == 0) {
                    PostGoodsStyleBean styleBean = new PostGoodsStyleBean();
                    styleBean.setGoodsInvent(invent_data_list);
                    PostTiredPriceData tiredPriceData = new PostTiredPriceData();
                    tiredPriceData.setTired_data_list(tired_data_list);
                    Intent intvent = new Intent(context, FlightAloneBottomActivity.class);
                    intvent.putExtra("style", styleBean);
                    intvent.putExtra("goodsName", tvGoodsName.getText().toString());
                    intvent.putExtra("tiredPrice", tiredPriceData);
                    intvent.putExtra("goodsNumType", goodsNumType);
                    intvent.putExtra("goodsId", goodsId);
                    intvent.putExtra("storeId", storeId);
                    intvent.putExtra("goodsMainPhoto", goodsMainPhoto);
                    intvent.putExtra("endTime", endTime);
                    intvent.putExtra("new_data", new_data);
                    intvent.putExtra("goodsLimit", goodsLimit);
                    intvent.putExtra("goodsType", goodsType);
                    startActivity(intvent);
                }
                break;
            case R.id.ll_flight_rule:
                Intent intent = new Intent(context, FlightNewRuleActivity.class);
                if (rulesData != null) {
                    intent.putExtra("rule", rulesData);
                    startActivity(intent);
                }
                break;
            case R.id.tv_see_more_discuss:
                EventBus.getDefault().post(2);
                break;
            case R.id.tv_price:
                PostGoodsRuleBean priceBean = new PostGoodsRuleBean();
                priceBean.setList(tired_data_list);
                Intent priceBeanIntent = new Intent(context, FlighRuleActivity.class);
                priceBeanIntent.putExtra("rule", gson.toJson(priceBean));
                startActivity(priceBeanIntent);
                break;
            case R.id.tv_go_shop:
                Intent intents = new Intent(context, ShopActivity.class);
                if (storeId != null) {
                    intents.putExtra("storeId", storeId);
                    startActivity(intents);
                }
                break;
            case R.id.tv_chat_shop:
                shopRequester.addFavoriteStore(context, getAccessToken(), storeId, addFavoriteStoreListener);
//                EventBus.getDefault().post(Event.GO_TO_CHAT);
                break;

            case R.id.rl_goods_explain:
                initExplain();
                break;

            case R.id.rl_goods_property:
                initProperty();
                break;

            case R.id.ib_carts :
                    CommonUseDialog.showDialog(context, ibCarts,shareContent);
                break;

            case R.id.btnBack :
                ((GoodsDetailsActivity)context).finish();
                break;
        }
    }

    private void initEvent() {
        rollPv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int mposition) {
                position = mposition;
                goodsPhotoBean.setNormal_lsit(normal_lsit);
                goodsPhotoBean.setPositopn(position);
                Intent intent = new Intent(context, PreviewPictureActivity.class);
                startActivity(intent);
            }
        });

        rollPv.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int mposition, float positionOffset, int positionOffsetPixels) {
                tvShowCurrent.setText(String.valueOf(mposition + 1) + "/" + String.valueOf(normal_lsit.size()));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        llSelectArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FlightSelectCityBottomActivity.class);
                intent.putExtra("save", save);
                startActivityForResult(intent, 0x12);
            }
        });

        tvGoodsName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                DropPopMenu dropPopMenu = new DropPopMenu(context);
                dropPopMenu.setTriangleIndicatorViewColor(Color.WHITE);
                dropPopMenu.setBackgroundResource(R.drawable.bg_drop_pop_menu_white_shap);
                dropPopMenu.setItemTextColor(Color.BLACK);

                dropPopMenu.setOnItemClickListener(new DropPopMenu.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case 1 :
                                mClipData = ClipData.newPlainText("Simple", tvGoodsName.getText().toString());
                                mClipboardManager.setPrimaryClip(mClipData);
                                Toast.makeText(context, "文本已经复制成功！",
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 2 :
                                if (shareContent!=null){
                                    ShareDialog shareDialog = new ShareDialog(context,shareContent);
                                    shareDialog.show();
                                }
                                break;
                        }
                    }
                });
                dropPopMenu.setMenuList(getMenuList());

                dropPopMenu.show(view);

                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().post(goodsPhotoBean);
    }

    private GetDiscussImp getDiscussImp = new GetDiscussImp();

    private class GetDiscussImp extends DefaultRequestListener implements GetDiscussListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getDiscuss(DiscussContentData contentData) {
            data.clear();
            data.addAll(contentData.getEvaluateList());
            adapter.setData(data);
            adapter.notifyDataSetChanged();
        }
    }

    ShopRequester shopRequester = new ShopRequester();
    private QueryStoreByIdListenerImpl queryStoreByIdListener = new QueryStoreByIdListenerImpl();

    private class QueryStoreByIdListenerImpl extends DefaultRequestListener implements QueryStoreByIdListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryStoreByIdSuccess(Shop shop) {
            mshop = shop;
            ImageLoaderUtil.displayImage(shop.getIcon(), ivIcon);
            tvShopName.setText(shop.getName());
            tvStyle.setText("所在地 :" + shop.getStoreArea());
            tvService.setText("由" + shop.getName() + "负责发货,并提供售后服务");

            if (shop.isCollect()) {
                ivCollectShop.setImageResource(R.mipmap.icon_collect2);
            } else {
                ivCollectShop.setImageResource(R.mipmap.icon_collect_grey2);
            }

        }
    }

    private Shop mshop;


    private RuleImp ruleImp = new RuleImp();
    CrowdFundRulesData rulesData;

    private class RuleImp extends DefaultRequestListener implements FlightRuleListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getData(CrowdFundRulesData Data) {
            try {
                if (Data.getCrowdFundRules().size() > 0) {
                    rulesData = Data;
                }
            } catch (Exception e) {
            }

        }
    }

    @Subscribe
    public void onEvent(ArrayList<FlightLsItemData> new_data) {
        if (new_data != null) {
            this.new_data = new_data;
        }
    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
        }
        return doc.toString();
    }


    private String save;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x12 && resultCode == 0x13) {
            ArrayList<CarriageModel> list = new ArrayList<>();
            list.add(new CarriageModel(Integer.parseInt(goodsId), 1));
            String cityName = data.getStringExtra("result");
            save = data.getStringExtra("save");
            tvCityName.setText(data.getStringExtra("procivn") + data.getStringExtra("cityName"));
            requester.getCarriageFare(context, data.getStringExtra("cityName"), cityName, list, carrageImp);
        }
    }

    private void doWork(GetGoodsBean goods) {
        List<String> list = new ArrayList<>();
        if (goods.getReplaceType() != null) {
            if (goods.getReplaceType().equals("1")) {
                list.add("7天包换");
            } else if (goods.getReplaceType().equals("2")) {
                list.add("15天包换");
            } else if (goods.getReplaceType().equals("3")) {
                list.add("30天包换");
            }
        }

        if (goods.getReturnGoods() != null) {
            if (goods.getReturnGoods().equals("1")) {
                list.add("7天无理由包退");
            }
        }

        if (goods.getSaleMode() != 0) {
            if (goods.getSaleMode() == 1) {
                list.add("普通销售");
            } else {
                list.add("批量销售");
            }
        }

        if (goods.getSampleService() != 0) {
            if (goods.getSampleService() == 1) {
                list.add("支持拿样");
            }
        }

        if (goods.getMateral() != 0) {
            if (goods.getMateral() == 1) {
                list.add("材质保障");
            }
        }

        if (goods.getMatched() != 0) {
            if (goods.getMatched() == 1) {
                list.add("无货必赔");
            }
        }

        if (goods.getPictureType() != 0) {
            if (goods.getPictureType() == 1) {
                list.add("模特实拍");
            }
        }

        CommonAdapter<String> commonAdapter = new CommonAdapter<String>(context, list, R.layout.hsv_ls_item) {
            @Override
            public void convert(ViewHolder helper, String item) {
                helper.setText(R.id.tv_id, item);
            }
        };

        gvColumn.setAdapter(commonAdapter);
    }

    private void initProperty() {
        ArrayList<KeyValueModel> list = new ArrayList<>();
        if (getData != null) {
            if (getData.getGoods().getGoodsSerial() != null) {
                KeyValueModel model = new KeyValueModel("货号", getData.getGoods().getGoodsSerial());
                list.add(model);
            }
            if (getData.getGoods().getStoreArea() != null) {
                KeyValueModel model = new KeyValueModel("产地", getData.getGoods().getStoreArea());
                list.add(model);
            }

            if (getData.getGoods().getGoodsNumType() == 0) {
                KeyValueModel model = new KeyValueModel("货源类别", "订货");
                list.add(model);
            } else if (getData.getGoods().getGoodsNumType() == 1) {
                KeyValueModel model = new KeyValueModel("货源类别", "现货");
                list.add(model);
            }

            if (getData.getGoods().getProductParamList() != null) {
                for (int i = 0; i < getData.getGoods().getProductParamList().size(); i++) {
                    if (!getData.getGoods().getProductParamList().get(i).getName().equals("")&&!getData.getGoods().getProductParamList().get(i).getValue().equals("")) {
                        KeyValueModel model = new KeyValueModel(getData.getGoods().getProductParamList().get(i).getName(), getData.getGoods().getProductParamList().get(i).getValue());
                        list.add(model);
                    }
                }
            }

            if (getData.getGoods().getFeatureObj() != null) {
                for (int i = 0; i < getData.getGoods().getFeatureObj().size(); i++) {
                    if (!getData.getGoods().getFeatureObj().get(i).getName().equals("")&&!getData.getGoods().getFeatureObj().get(i).getValue().equals("")) {
                        KeyValueModel model = new KeyValueModel(getData.getGoods().getFeatureObj().get(i).getName(), getData.getGoods().getFeatureObj().get(i).getValue());
                        list.add(model);
                    }
                }
            }

            if (list.size() > 0) {
                Intent intent = new Intent(context, GoodsPropertyActivity.class);
                intent.putExtra("value", list);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        }
    }

    private void initExplain() {
        ArrayList<KeyValueModel> list = new ArrayList<>();
        if (getData != null) {
            if (getData.getGoods().getSupplyMode().equals("0")) {
                KeyValueModel model = new KeyValueModel("供货方式", "订货");
                list.add(model);
            } else if (getData.getGoods().getSupplyMode().equals("1")) {
                KeyValueModel model = new KeyValueModel("供货方式", "现货");
                list.add(model);
            } else if (getData.getGoods().getSupplyMode().equals("3")) {
                KeyValueModel model = new KeyValueModel("供货方式", "现货且支持订货");
                list.add(model);
            }
            if (getData.getGoods().getMachCycle() != 0) {
                KeyValueModel model = new KeyValueModel("加工周期", String.valueOf(getData.getGoods().getMachCycle()));
                list.add(model);
            }
            if (getData.getGoods().getShipTime() != null) {
                KeyValueModel model = new KeyValueModel("最快出货时间", String.valueOf(getData.getGoods().getShipTime()));
                list.add(model);
            }

            if (getData.getGoods().getSaleMode() != 0) {
                if (getData.getGoods().getSaleMode() == 1) {
                    KeyValueModel model = new KeyValueModel("销售方式", "普通销售");
                    list.add(model);
                } else {
                    KeyValueModel model = new KeyValueModel("销售方式", "批量销售");
                    list.add(model);
                }
            }

            if (getData.getGoods().getEditService() != 0) {
                if (getData.getGoods().getEditService() == 1) {
                    KeyValueModel model = new KeyValueModel("版型服务", "可改版");
                    list.add(model);
                } else {
                    KeyValueModel model = new KeyValueModel("版型服务", "不可改版");
                    list.add(model);
                }
            }


            if (getData.getGoods().getSampleService() != 0) {
                if (getData.getGoods().getSampleService() == 1) {
                    KeyValueModel model = new KeyValueModel("样品服务", "支持拿货");
                    list.add(model);
                } else {
                    KeyValueModel model = new KeyValueModel("样品服务", "不支持拿货");
                    list.add(model);
                }
            }

            KeyValueModel model = new KeyValueModel();
            model.setKey_name("买家保障");
            StringBuilder name = new StringBuilder();
            if (getData.getGoods().getReplaceType() != null) {
                if (getData.getGoods().getReplaceType().equals("1")) {
                    name.append("7天包换");
                } else if (getData.getGoods().getReplaceType().equals("2")) {
                    name.append("15天包换");
                } else if (getData.getGoods().getReplaceType().equals("3")) {
                    name.append("30天包换");
                }
            }

            if (getData.getGoods().getReturnGoods() != null) {
                if (!getData.getGoods().getReturnGoods().equals("")) {
                    name.append(" 7天无理由包退");
                }
            }

            if (getData.getGoods().getMateral() != 0) {
                if (getData.getGoods().getMateral() == 1) {
                    name.append(" 材质保障");
                }
            }

            if (getData.getGoods().getMatched() != 0) {
                if (getData.getGoods().getMatched() == 1) {
                    name.append(" 无货必赔");
                }
            }

            model.setKey_value(name.toString());
            if (!name.toString().equals("")) {
                list.add(model);
            }

        }
        if (list.size() > 0) {
            Intent intent = new Intent(context, GoodsPropertyActivity.class);
            intent.putExtra("value", list);
            intent.putExtra("type", "2");
            startActivity(intent);
        }
    }

    private AddFavoriteStoreListenerImpl addFavoriteStoreListener;

    private class AddFavoriteStoreListenerImpl extends DefaultRequestListener implements AddFavoriteStoreListener {

        @Override
        public void onAddFavoriteStoreSuccess() {
            mshop.setCollect(!mshop.isCollect());

            if (mshop.isCollect()) {
                ivCollectShop.setImageResource(R.mipmap.icon_collect2);
            } else {
                ivCollectShop.setImageResource(R.mipmap.icon_collect_grey2);
            }

        }

        @Override
        protected void onRequestFail() {

        }
    }

    private List<MenuItem> getMenuList() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem(1, "复制"));
        list.add(new MenuItem(2, "分享"));
        return list;
    }

}
