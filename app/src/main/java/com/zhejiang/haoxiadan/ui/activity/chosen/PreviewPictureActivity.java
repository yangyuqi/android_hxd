package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsPhotoBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/30.
 */

public class PreviewPictureActivity extends BaseFragmentActivity {
    @BindView(R.id.roll_pv)
    RollPagerView rollPv;
    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.tv_show_num)
    TextView tvShowNum;

    private ArrayList<String> data = new ArrayList<>();
    private int position ;

    private BannerNormalAdapter adapter ;

    PostGoodsPhotoBean postGoodsPhotoBean ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_picture_layout);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);

        rollPv.setHintView(new ColorPointHintView(context, context.getResources().getColor(R.color.transparent), context.getResources().getColor(R.color.transparent)));

        postGoodsPhotoBean = (PostGoodsPhotoBean) getIntent().getSerializableExtra("goodsPhotoBean");
        if (postGoodsPhotoBean!=null){
            data.clear();
            data.addAll(postGoodsPhotoBean.getNormal_lsit());
            adapter = new BannerNormalAdapter(data);
            rollPv.setAdapter(adapter);
            tvShowNum.setText(String.valueOf(position+1)+"/"+String.valueOf(data.size()));
            position = postGoodsPhotoBean.getPositopn();
            rollPv.getViewPager().setCurrentItem(position);
        }

        initEvent();
    }

    private void initEvent() {
        rollPv.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int mposition, float positionOffset, int positionOffsetPixels) {
                tvShowNum.setText(String.valueOf(mposition+1)+"/"+String.valueOf(data.size()));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_finish})
    public void OnClick() {
        finish();
    }

    private class BannerNormalAdapter extends StaticPagerAdapter {

        private List<String> banner_date = new ArrayList<>();

        public BannerNormalAdapter(List<String> entity) {
            banner_date = entity;
        }


        @Override
        public View getView(ViewGroup container, final int position) {
            ImageView view = new ImageView(container.getContext());
            ImageLoaderUtil.displayImage(banner_date.get(position),view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return banner_date.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(PostGoodsPhotoBean arrayList){
        data.clear();
        data.addAll(arrayList.getNormal_lsit());
        adapter = new BannerNormalAdapter(data);
        rollPv.setAdapter(adapter);
        tvShowNum.setText(String.valueOf(position+1)+"/"+String.valueOf(data.size()));
        position = arrayList.getPositopn();
        rollPv.getViewPager().setCurrentItem(position);
    }
}
