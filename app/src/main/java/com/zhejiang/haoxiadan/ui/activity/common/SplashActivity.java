/**
 * 欢迎页面
 */
package com.zhejiang.haoxiadan.ui.activity.common;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.util.Constants;

public class SplashActivity extends BaseActivity {
	
	private ViewPager mViewPager;

	private ImageView pointOneIv;
	private ImageView pointTwoIv;
	private ImageView pointThreeIv;

	private ImageView[] imageViews;
	private TypedArray mPictures;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		this.setContentView(R.layout.activity_splash);
		getWindow().setBackgroundDrawable(null);


		initView();
		initAction();
	}
	
	private void initView() {
		mViewPager = (ViewPager)findViewById(R.id.splash_viewpager);
		pointOneIv = (ImageView)findViewById(R.id.iv_point_one);
		pointTwoIv = (ImageView)findViewById(R.id.iv_point_two);
		pointThreeIv = (ImageView)findViewById(R.id.iv_point_three);
	}
	
	private void initAction() {
		initViewPager();
	}
	
	private void initViewPager() {
		mPictures = this.getResources().obtainTypedArray(R.array.array_splash_pictures);
		imageViews = new ImageView[mPictures.length()];
		mViewPager.removeAllViews();
		for (int i = 0; i < imageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setImageResource(mPictures.getResourceId(i, 0));

			imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			imageViews[i] = imageView;
		}
		mPictures.recycle();
		ViewPagerAdapter adapter = new ViewPagerAdapter();
		mViewPager.setAdapter(adapter);

		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position){
					case 0:
						pointOneIv.setImageResource(R.mipmap.splash_point_focus);
						pointTwoIv.setImageResource(R.mipmap.splash_point);
						pointThreeIv.setImageResource(R.mipmap.splash_point);
						pointOneIv.setVisibility(View.VISIBLE);
						pointTwoIv.setVisibility(View.VISIBLE);
						pointThreeIv.setVisibility(View.VISIBLE);
						break;
					case 1:
						pointOneIv.setImageResource(R.mipmap.splash_point);
						pointTwoIv.setImageResource(R.mipmap.splash_point_focus);
						pointThreeIv.setImageResource(R.mipmap.splash_point);
						pointOneIv.setVisibility(View.VISIBLE);
						pointTwoIv.setVisibility(View.VISIBLE);
						pointThreeIv.setVisibility(View.VISIBLE);
						break;
					case 2:
						pointOneIv.setVisibility(View.GONE);
						pointTwoIv.setVisibility(View.GONE);
						pointThreeIv.setVisibility(View.GONE);
						break;

				}

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
        
		mViewPager.setCurrentItem(0);
	}
	
	private class ViewPagerAdapter extends PagerAdapter {

		// 获取要滑动的控件的数量，在这里我们以滑动的广告栏为例，那么这里就应该是展示的广告图片的ImageView数量
		@Override
		public int getCount() {
			return imageViews.length;
		}

		// 来判断显示的是否是同一张图片，这里我们将两个参数相比较返回即可
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		// PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁
		@Override
		public void destroyItem(ViewGroup view, int position, Object object) {
			view.removeView(imageViews[position]);
		}

		// 当要显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，我们将要显示的ImageView加入到ViewGroup中，然后作为返回值返回即可
		@Override
		public Object instantiateItem(ViewGroup view, int position) {
			if(position == imageViews.length-1){
				RelativeLayout endPage = (RelativeLayout)LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_splash_end_page, null);
				ImageView mEnterBtn = (ImageView)endPage.findViewById(R.id.btn_end_page_enter);
				mEnterBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						SharedPreferencesUtil.put(SplashActivity.this, Constants.SHAREDPREFERENCES_KEY_IS_USED, true);
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
			            SplashActivity.this.startActivity(intent);
			            SplashActivity.this.finish();
					}
				});
				view.addView(endPage, 0);
				return endPage;
			}else{
				view.addView(imageViews[position], 0);
				return imageViews[position];
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
