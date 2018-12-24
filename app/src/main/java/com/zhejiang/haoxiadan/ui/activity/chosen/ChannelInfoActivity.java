package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ShapeHintView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.ChoseAdapter;
import com.zhejiang.haoxiadan.adapter.TypeView.lIstViewItem;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.ChannelInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.BannersListBean;
import com.zhejiang.haoxiadan.model.requestData.out.ChoseGoodsFloorData;
import com.zhejiang.haoxiadan.model.requestData.out.NewChose.NewFloorsItem;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ChosenFragment;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/10/8.
 */

public class ChannelInfoActivity extends BaseFragmentActivity implements ChoseAdapter.GetBeanDataInterface {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.no_ls)
    NoScrollListView noLs;
    @BindView(R.id.roll_pv)
    RollPagerView rollPv;

    private BannerNormalAdapter normalAdapter;
    private ArrayList<BannersListBean> normal_lsit = new ArrayList<>();

    private ChoseAdapter choseAdapter;

    private GoodsRequester requester;
    private OpenFlightAloneBean bean ;

    private String child ,title;
    private channelInterface anInterface;

    @Override
    public void setData(OpenFlightAloneBean aloneBean) {

    }

    private class channelInterface extends DefaultRequestListener implements ChannelInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getFloor(ChoseGoodsFloorData floorData) {
            if (floorData.getChannelFloor() != null && floorData.getChannelFloor().size() > 0) {
                ArrayList<lIstViewItem> listDatas = new ArrayList<>();
                for (int i = 0; i < floorData.getChannelFloor().size(); i++) {
                    lIstViewItem lIstViewItem = null;
                    NewFloorsItem item = floorData.getChannelFloor().get(i);
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
                    }else if (item.getWide_template().equals("app_hotitems")) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("6", item);
                        lIstViewItem = new lIstViewItem(6, map);
                        listDatas.add(lIstViewItem);
                    }
                }
                choseAdapter = new ChoseAdapter(context, listDatas, ChannelInfoActivity.this);
                noLs.setAdapter(choseAdapter);
                noLs.setFocusable(false);
            }

            if (floorData.getBannerFloor().size() > 0) {
                normal_lsit.clear();
                normal_lsit.addAll(floorData.getBannerFloor());
                normalAdapter = new BannerNormalAdapter(normal_lsit);
                rollPv.setAdapter(normalAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_info_layout);
        ButterKnife.bind(this);
        rollPv.setPlayDelay(6000);
        rollPv.setHintView(new MyIconHintView(context, R.mipmap.select_rv_bg, R.mipmap.two_pg));
        child = getIntent().getStringExtra("chId");
        title = getIntent().getStringExtra("title");
        tvTitle.setText(title);
        requester = new GoodsRequester();
        anInterface = new channelInterface();
        requester.channelInfo(context, child, anInterface);

        rollPv.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            goOtherPage(normal_lsit.get(position).getType(),normal_lsit.get(position).getValue());
            }
        });

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private class BannerNormalAdapter extends StaticPagerAdapter {

        private List<BannersListBean> banner_date;

        public BannerNormalAdapter(List<BannersListBean> entity) {
            banner_date = entity;
        }

        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            ImageLoaderUtil.displayImage(banner_date.get(position).getPath(), view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return banner_date.size();
        }
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

    public void goOtherPage(String type, String value){
        Intent intent = null ;
        switch (type){
            case "goodsInfo" :
                intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", value);
                bean = new OpenFlightAloneBean("close");
                this.setData(bean);
                context.startActivity(intent);
                break;
            case "store" :
                intent = new Intent(context,ShopActivity.class);
                intent.putExtra("storeId",value);
                context.startActivity(intent);
                break;
            case "goodsList":
                ArrayList<String> stringArrayList = new ArrayList<String>();
                String[] d = new String[]{};
                if (value.indexOf(",")>0) {
                    d = value.split(",");
                }else {
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
            case "keyword" :
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType","goods");
                intent.putExtra("type","goodsType");
                context.startActivity(intent);
                break;
            case "storeSearch" :
                intent = new Intent(context, SearchFactoryActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType","store");
                context.startActivity(intent);
                break;
            case "brandSearch" :
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("type","brand");
                intent.putExtra("searchType","goods");
                context.startActivity(intent);
                break;
            case "channel" :
                intent = new Intent(context, ChannelInfoActivity.class);
                intent.putExtra("chId",value);
                intent.putExtra("title","频道");
                context.startActivity(intent);
                break;
        }
    }
}
