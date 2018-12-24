package com.zhejiang.haoxiadan.ui.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.hot.WelcomePhotoListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/11/9.
 */

public class AdvertisementActivity extends BaseFragmentActivity {
    @BindView(R.id.iv_advers)
    ImageView ivAdvers;
    @BindView(R.id.tv_advers)
    TextView tvAdvers;
    @BindView(R.id.ll_count_down)
    LinearLayout countDownLayout;
    private UserRequester requester;
    private WelcomePhotoImp photoImp;
    private String photo_url;
    private CountDownTimer timer;

    private int countDownSecond = 4;//倒计时多少秒

    private boolean isFirstUse = false;

    public class WelcomePhotoImp extends DefaultRequestListener implements WelcomePhotoListener {

        @Override
        public void onGetPhoto(String data) {
            try {
                JSONObject object = new JSONObject(data);
                photo_url = object.getString("welcomeImg");
//                ImageLoaderUtil.displayImageAndDefaultImg(photo_url,ivAdvers,R.mipmap.welcome);
                Glide.with(AdvertisementActivity.this).load(photo_url).error(R.mipmap.welcome).into(ivAdvers);
                countDown();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onRequestFail() {
            countDown();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.advertisement_layout);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        isFirstUse = getIntent().getBooleanExtra("isFirstUse",false);

        requester = new UserRequester();
        photoImp = new WelcomePhotoImp();
        requester.WelcomePhoto(this, photoImp);

        countDownLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                gotoMain();
            }
        });
    }

    private void gotoMain(){
        Intent intent;
        if(isFirstUse){
            intent = new Intent(context, SplashActivity.class);
        }else{
            intent = new Intent(context, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }

    private void countDown(){
        tvAdvers.setText(countDownSecond+"");
        timer = new CountDownTimer(countDownSecond*1000, 1000) {
            @Override
            public void onTick(long l) {
                tvAdvers.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish() {
                gotoMain();
            }
        };
        timer.start();
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
