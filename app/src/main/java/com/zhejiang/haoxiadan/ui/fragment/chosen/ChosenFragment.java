package com.zhejiang.haoxiadan.ui.fragment.chosen;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ShapeHintView;
import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.ChoseAdapter;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.TypeView.lIstViewItem;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.FloorListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.HasCommondLiatener;
import com.zhejiang.haoxiadan.business.request.chosen.MobileTitleInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.QueryBannerListener;
import com.zhejiang.haoxiadan.business.request.chosen.TopInfoListener;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.CommondGoodsBean;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersListBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.ChannelFloorBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoList;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoListBean;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;
import com.zhejiang.haoxiadan.model.requestData.out.NewChose.NewFloorsItem;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.category.CategoryActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ChannelInfoActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsListActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.PreferenceFactoryActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchFactoryActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchHistortActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchResultGoodsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.TopInfoListActivity;
import com.zhejiang.haoxiadan.ui.dialog.CommondGoodsDialog;
import com.zhejiang.haoxiadan.ui.dialog.HomeDialog;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;
import com.zhejiang.haoxiadan.ui.view.MyChoseGridView;
import com.zhejiang.haoxiadan.ui.view.MyChoseListView;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.ui.view.UPMarqueeView;
import com.zhejiang.haoxiadan.util.Constants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChosenFragment extends BaseFragment implements ChoseAdapter.GetBeanDataInterface {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.view_root)
    RelativeLayout viewRoot;
    Unbinder unbinder;
    @BindView(R.id.title_icon)
    ImageView titleIcon;
    @BindView(R.id.up_mv)
    UPMarqueeView upMv;

    List<TopInfoListBean> data = new ArrayList<>();
    List<View> views = new ArrayList<>();
    @BindView(R.id.tv_search)
    TextView tvSearch;

    @BindView(R.id.roll_pv)
    RollPagerView rollPv;

    @BindView(R.id.sv)
    SpringView sv;


    @BindView(R.id.iv_icon_test)
    ImageView ivIconTest;

    @BindView(R.id.no_ls)
    MyChoseListView noLs;
    @BindView(R.id.top_gv)
    MyChoseGridView topGv;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    private int tradeId;

    OpenFlightAloneBean bean;
    private BannerNormalAdapter normalAdapter;
    private ArrayList<BannersListBean> normal_lsit = new ArrayList<>();
    private ChoseAdapter choseAdapter;
    private GoodsRequester requester = new GoodsRequester();
    private FloorImp imp = new FloorImp();
    private TopInfoImp topInfoImp = new TopInfoImp();

    @Override
    public void setData(OpenFlightAloneBean aloneBean) {
        if (aloneBean != null) {
            bean = aloneBean;
        }
    }


    private class FloorImp extends DefaultRequestListener implements FloorListener {

        @Override
        protected void onRequestFail() {
            if (sv != null) {
                sv.onFinishFreshAndLoad();
            }
        }

        @Override
        public void getHomeData(final ChoseGoodsFloorData homeData) {
            if (sv != null) {
                sv.onFinishFreshAndLoad();
            }
            if (homeData.getFloors() != null && homeData.getFloors().size() > 0) {
                ArrayList<lIstViewItem> listDatas = new ArrayList<>();
                for (int i = 0; i < homeData.getFloors().size(); i++) {
                    lIstViewItem lIstViewItem = null;
                    NewFloorsItem item = homeData.getFloors().get(i);
                    if (item.getWide_template().equals("susiness")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("0", item);
                        lIstViewItem = new lIstViewItem(0, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("category")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("1", item);
                        lIstViewItem = new lIstViewItem(1, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("hotCategory")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("2", item);
                        lIstViewItem = new lIstViewItem(2, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("newcompetitiveProduct")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("3", item);
                        lIstViewItem = new lIstViewItem(3, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("competitiveProduct")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("4", item);
                        lIstViewItem = new lIstViewItem(4, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("applike")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("5", item);
                        lIstViewItem = new lIstViewItem(5, map);
                        listDatas.add(lIstViewItem);
                    } else if (item.getWide_template().equals("app_hotitems")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("6", item);
                        lIstViewItem = new lIstViewItem(6, map);
                        listDatas.add(lIstViewItem);
                    }
                }
                choseAdapter = new ChoseAdapter(context, listDatas, ChosenFragment.this);
                noLs.setAdapter(choseAdapter);
                noLs.setFocusable(false);
            }
        }
    }

    private GetTradeInfoImp getTradeInfoImp = new GetTradeInfoImp();

    private class GetTradeInfoImp extends DefaultRequestListener implements GetTradeInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getTrade(TradeListDataList list) {
            //TODO 暂时写死
            if (list.getTradeList() != null) {
                for (TradeListData tradeListData : list.getTradeList()) {
                    if (tradeListData.getId() == 2) { //女装
                        SharedPreferencesUtil.put(context, Constants.tradeId, tradeListData.getId());
//                        EventBus.getDefault().post(tradeListData);
                        requester.getGoodsFloor(context, getAccessToken(), String.valueOf(tradeListData.getId()), imp);
                    }
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chosen, container, false);

        unbinder = ButterKnife.bind(this, view);
        initView();
        rollPv.setPlayDelay(6000);
        rollPv.setHintView(new MyIconHintView(context, R.mipmap.select_rv_bg, R.mipmap.two_pg));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEvent();
        initData();
    }

    private class MyIconHintView extends ShapeHintView {
        private int focusResId;
        private int normalResId;
        private int pointWidth;
        private int pointHeight;

        public MyIconHintView(Context context, @DrawableRes int focusResId, @DrawableRes int normalResId) {
            super(context);
            this.focusResId = focusResId;
            this.normalResId = normalResId;
            pointWidth = context.getResources().getDimensionPixelOffset(R.dimen.banner_point_width);
            pointHeight = context.getResources().getDimensionPixelOffset(R.dimen.banner_point_height);
        }


        @Override
        public Drawable makeFocusDrawable() {
            Drawable drawable = getContext().getResources().getDrawable(focusResId);
            drawable = zoomDrawable(drawable, pointWidth, pointHeight);
            return drawable;
        }

        @Override
        public Drawable makeNormalDrawable() {
            Drawable drawable = getContext().getResources().getDrawable(normalResId);
            drawable = zoomDrawable(drawable, pointHeight, pointHeight);
            return drawable;
        }


        private Drawable zoomDrawable(Drawable drawable, int w, int h) {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap oldbmp = drawableToBitmap(drawable);
            Matrix matrix = new Matrix();
            float scaleWidth = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidth, scaleHeight);
            Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                    matrix, true);
            return new BitmapDrawable(null, newbmp);
        }

        private Bitmap drawableToBitmap(Drawable drawable) {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(width, height, config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
            return bitmap;
        }
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadmore() {
                sv.onFinishFreshAndLoad();
            }
        });


        rollPv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                goOtherPage(normal_lsit.get(position).getType(), normal_lsit.get(position).getValue());

            }
        });
    }

    public void initDialog() {
        HomeDialog dialog = new HomeDialog(context, requester);
        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();
    }



    private int oldY = 0;

    private void initView() {
        viewRoot.setBackgroundResource(R.color.gradient_red_light);
        ivLeft.setImageResource(R.mipmap.btn_sidebar);
        ivLeft.setVisibility(View.GONE);
        titleIcon.setVisibility(View.VISIBLE);
        ivRight.setImageResource(R.mipmap.btn_message);

    }

    @Subscribe
    public void onEventMainThread(TradeListData event) {
//        tradeId = event.getId();
//        SharedPreferencesUtil.put(context, Constants.tradeId, String.valueOf(tradeId));
//        requester.getGoodsFloor(context, getAccessToken(), String.valueOf(tradeId), imp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_search, R.id.iv_left, R.id.iv_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                startActivity(new Intent(context, SearchHistortActivity.class));
                break;
            case R.id.iv_left:
//                initDialog();
                break;
            case R.id.iv_right:
                Intent msgIntent = new Intent(getActivity(), MainActivity.class);
                startActivity(msgIntent);
                break;

            case R.id.tv_factory:
                startActivity(new Intent(context, PreferenceFactoryActivity.class));
                break;
        }
    }

    private class BannerNormalAdapter extends StaticPagerAdapter {

        private List<BannersListBean> banner_date;

        public BannerNormalAdapter(List<BannersListBean> entity) {
            banner_date = entity;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            View new_view = LayoutInflater.from(context).inflate(R.layout.image_new_layout,null);
            ImageView view = (ImageView) new_view.findViewById(R.id.iv_new);
            Glide.with(context).load(banner_date.get(position).getPath()).placeholder(R.mipmap.ic_launcher).into(view);
//            ImageLoaderUtil.displayImage(banner_date.get(position).getPath(), view);
//            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return new_view;
        }


        @Override
        public int getCount() {
            return banner_date.size();
        }
    }

    public void initData() {
//        String trade = (String) SharedPreferencesUtil.get(context, Constants.tradeId, "");
//        if (trade.equals("")) {
//            initDialog();
//            requester.getTradeInfo(context,getTradeInfoImp);
//        } else {
        requester.topInfo(context, getAccessToken(), topInfoImp);
        requester.getTradeInfo(context, getTradeInfoImp);
//            requester.getGoodsFloor(context, getAccessToken(), trade, imp);
        requester.mobileTitleInfo(context, titleImp);
        requester.queryBanner(context, queryBannerImp);
//            initTopInfo();
//        }
    }

    private class TopInfoImp extends DefaultRequestListener implements TopInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getTopInfo(TopInfoList list) {
            data.clear();
            if (list.getTopInfoList().size() > 0) {
                data.addAll(list.getTopInfoList());
                setView();
                upMv.setViews(views);
            }
        }
    }

    private void setView() {


        for (int i = 0; i < this.data.size(); i = i + 2) {
            final int position = i;
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_view, null);
            //初始化布局的控件
            TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) moreView.findViewById(R.id.tv2);

            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TopInfoListActivity.class);
                    startActivity(intent);
                }
            });
            /**
             * 设置监听
             */
            moreView.findViewById(R.id.rl2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TopInfoListActivity.class);
                    startActivity(intent);
                }
            });
            //进行对控件赋值
            tv1.setText(this.data.get(i).getTitle());
            if (this.data.size() > i + 1) {
                tv2.setText(this.data.get(i + 1).getTitle());
            } else {
                moreView.findViewById(R.id.rl2).setVisibility(View.GONE);
            }
            //添加到循环滚动数组里面去
            views.add(moreView);
        }
    }

    private MobileTitleInfoImp titleImp = new MobileTitleInfoImp();

    private CommonAdapter<ChannelFloorBean> top_adapter;

    private class MobileTitleInfoImp extends DefaultRequestListener implements MobileTitleInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getChannel(List<ChannelFloorBean> ChannelFloor) {
            if (ChannelFloor.size() > 0) {
                top_adapter = new CommonAdapter<ChannelFloorBean>(context, ChannelFloor, R.layout.top_chose_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final ChannelFloorBean item) {
                        helper.setText(R.id.tv_title, item.getChName());
                        Glide.with(context).load(item.getIconPath()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_icon));

                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (item.getType().type.equals("storeList")) {
                                    startActivity(new Intent(context, PreferenceFactoryActivity.class));
                                } else if (item.getType().type.equals("typeList")) {
                                    startActivity(new Intent(context, CategoryActivity.class));
                                } else {
                                    Intent intent = new Intent(context, ChannelInfoActivity.class);
                                    intent.putExtra("chId", String.valueOf(item.getId()));
                                    intent.putExtra("title", item.getChName());
                                    startActivity(intent);
                                }
                            }
                        });


                    }
                };
                topGv.setAdapter(top_adapter);
            }
        }
    }

    private QueryBannerImp queryBannerImp = new QueryBannerImp();

    public class QueryBannerImp extends DefaultRequestListener implements QueryBannerListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getList(BannersData bannersData) {
            normal_lsit.clear();
            normal_lsit.addAll(bannersData.getBanners());
            if (normal_lsit.size() > 0) {
                normalAdapter = new BannerNormalAdapter(normal_lsit);
                rollPv.setAdapter(normalAdapter);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (bean != null) {
            EventBus.getDefault().post(bean);
        }
    }

    public void goOtherPage(String type, String value) {
        Intent intent = null;
        if (type == null || value == null) {
            return;
        }
        switch (type) {
            case "goodsInfo":
                intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", value);
                bean = new OpenFlightAloneBean("close");
                context.startActivity(intent);
                break;
            case "store":
                intent = new Intent(context, ShopActivity.class);
                intent.putExtra("storeId", value);
                context.startActivity(intent);
                break;
            case "goodsList":
                ArrayList<String> stringArrayList = new ArrayList<String>();
                String[] d = new String[]{};
                if (value.indexOf(",") > 0) {
                    d = value.split(",");
                } else {
                    d = new String[]{value};
                }
                for (int i = 0; i < d.length; i++) {
                    if (!d[i].equals("")) {
                        stringArrayList.add(d[i]);
                    }
                }
                intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("title", "商品列表");
                intent.putStringArrayListExtra("goodsIdList", stringArrayList);
                context.startActivity(intent);
                break;
            case "keyword":
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType", "goods");
                intent.putExtra("type", "goodsType");
                context.startActivity(intent);
                break;
            case "storeSearch":
                intent = new Intent(context, SearchFactoryActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType", "store");
                context.startActivity(intent);
                break;
            case "brandSearch":
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("type", "brand");
                intent.putExtra("searchType", "goods");
                context.startActivity(intent);
                break;
            case "channel":
                intent = new Intent(context, ChannelInfoActivity.class);
                intent.putExtra("chId", value);
                intent.putExtra("title", "频道");
                context.startActivity(intent);
                break;
        }
    }

}
