package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchHistortActivity;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.ui.fragment.my.CollectionGoodsFragment;
import com.zhejiang.haoxiadan.ui.fragment.my.CollectionShopFragment;
import com.zhejiang.haoxiadan.util.PublicUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class MyCollectinActivity extends BaseFragmentActivity{

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.view_page)
    ViewPager viewPage;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right_second)
    ImageView ivRightSecond;
    private List<Fragment> list = new ArrayList<>();

    private MyFragmentPagerAdapter adapter;
    private String[] titles = {"商品","店铺"};
    private GlobalTitleMorePopupWindow popupDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collect_layout);
        ButterKnife.bind(this);

        initView();
        tab.post(new Runnable() {
            @Override
            public void run() {
                PublicUtils.setTabLine(tab,60,60);
            }
        });
    }

    private void initView() {
        tvTitle.setText("我的收藏");
        ivRight.setImageResource(R.drawable.btn_more2);
        ivRightSecond.setImageResource(R.mipmap.icon_search);

        list.add(new CollectionGoodsFragment());
        list.add(new CollectionShopFragment());
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),list);
        viewPage.setAdapter(adapter);
        tab.setupWithViewPager(viewPage);

        popupDialog = new GlobalTitleMorePopupWindow(context);
    }

    @OnClick({R.id.iv_left,R.id.iv_right,R.id.iv_right_second})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
            case R.id.iv_right :
                popupDialog.showAsDropDown(ivRight);
                break;
            case R.id.iv_right_second :
                startActivity(new Intent(context, SearchHistortActivity.class));
                break;
        }
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> list ;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list ;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
