package com.zhejiang.haoxiadan.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.DrawerData;
import com.zhejiang.haoxiadan.model.common.PushData;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.adapter.CommonFragmentPagerAdapter;
import com.zhejiang.haoxiadan.ui.adapter.common.DrawerAdapter;
import com.zhejiang.haoxiadan.ui.fragment.cart.CartFragment;
import com.zhejiang.haoxiadan.ui.fragment.category.CategoryFragment;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ChosenFragment;
import com.zhejiang.haoxiadan.ui.fragment.my.MyFragment;
import com.zhejiang.haoxiadan.ui.fragment.hot.HotFragment;
import com.zhejiang.haoxiadan.ui.view.NoScrollViewPager;
import com.zhejiang.haoxiadan.ui.view.ResideMenu;
import com.zhejiang.haoxiadan.ui.view.TipDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.OSUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener{


    private ResideMenu resideMenu;

    private NoScrollViewPager mVPContent;
    private List<Fragment> mListFragement;
    private List<View> mListBottemBtn;

    private View view_home ,view_home_hl ,view_home_no_hl;

    private ListView drawerLv;
    private DrawerAdapter drawerAdapter;
    private List<DrawerData> drawerDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initView();
        initEvent();
        initData();

        mListBottemBtn.get(0).performClick();
        mVPContent.setCurrentItem(0);
    }

    private void initView(){
        mVPContent = (NoScrollViewPager) findViewById(R.id.vp_content);
        view_home = findViewById(R.id.footer_chosen);
        view_home_hl = findViewById(R.id.ll_show_home_hl);
        view_home_no_hl = findViewById(R.id.ll_show_home);
        mListBottemBtn = new ArrayList<>();
        mListBottemBtn.add(view_home);
        mListBottemBtn.add(findViewById(R.id.footer_category));
        mListBottemBtn.add(findViewById(R.id.footer_hot));
        mListBottemBtn.add(findViewById(R.id.footer_cart));
        mListBottemBtn.add(findViewById(R.id.footer_my));
        mListFragement = new ArrayList<>();
        mListFragement.add(new ChosenFragment());
        mListFragement.add(new CategoryFragment());
        mListFragement.add(new HotFragment());
        mListFragement.add(new CartFragment());
        mListFragement.add(new MyFragment());

        mVPContent.setAdapter(new CommonFragmentPagerAdapter(getSupportFragmentManager(), mListFragement));
        mVPContent.setOffscreenPageLimit(mListFragement.size());

        View drawView = View.inflate(this, R.layout.main_left_drawer, null);
        drawerLv = (ListView)drawView.findViewById(R.id.lv_drawer);

    }


    private void initEvent(){
        for (int i = 0; i < mListBottemBtn.size(); i++) {
            mListBottemBtn.get(i).setOnClickListener(new FooterClickListen(i));
        }
        mVPContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mListBottemBtn.get(position).setSelected(true);
                if (position == 3){//购物车
                    if(getAccessToken() == null || getAccessToken().equals("")){
                        //去登陆
                        EventBus.getDefault().post(Event.GOTO_LOGIN);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }



    private void initData(){
        drawerDatas = new ArrayList<>();
        drawerAdapter = new DrawerAdapter(this,drawerDatas);
        drawerLv.setAdapter(drawerAdapter);
    }

    private class FooterClickListen implements View.OnClickListener {
        private int index = 0;

        public FooterClickListen(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            mVPContent.setCurrentItem(index, false);
            for (int i = 0; i < mListBottemBtn.size(); i++) {
                    if (i == index) {
                        mListBottemBtn.get(i).setSelected(true);
                        if (i==0){
                            view_home_hl.setVisibility(View.VISIBLE);
                            view_home_no_hl.setVisibility(View.GONE);
                        }
                    } else {
                        mListBottemBtn.get(i).setSelected(false);
                        if (i==0){
                            view_home_no_hl.setVisibility(View.VISIBLE);
                            view_home_hl.setVisibility(View.GONE);
                        }
                    }
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }


    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if(true){
                if ((System.currentTimeMillis() - exitTime) > 2000) {
                    Toast.makeText(getApplicationContext(),
                            R.string.tip_exit, Toast.LENGTH_SHORT).show();
                    exitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe
    public void onEventMainThread(Event event) {
        switch (event) {
            case GOTO_LOGIN:
                Intent[] intents = new Intent[2];
                Intent mainIntent = new Intent(this, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                intents[0] = mainIntent;
                Intent loginIntent = new Intent(this,LoginActivity.class);
                intents[1] = loginIntent;
                startActivities(intents);
                break;
            case TIP_GOTO_LOGIN:
                Object obj = GlobalDataUtil.getObject(context,Constants.PUSH_RECEIVER_TIP_LOGIN_DATA);
                if(obj == null){
                    return;
                }
                String data = (String) obj;
                if(data!=null && !"".equals(data)){
                    try {
                        JSONObject jsonObject = new JSONObject(data);
                        data = jsonObject.getString("data");
                        PushData pushData = gson.fromJson(data,PushData.class);
                        if(pushData==null){
                            return;
                        }
                        Intent tipMainIntent = new Intent(context, MainActivity.class);
                        tipMainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        final Intent[] tipIntents = new Intent[2];
                        tipIntents[0] = tipMainIntent;
                        if(pushData.getType()!=null){
                            switch (pushData.getType()){
                                case "login":
                                    TipDialog tipDialog = new TipDialog(context, context.getString(R.string.tip), getString(R.string.tip_login_other_divice), getString(R.string.tip_login_again), getString(R.string.cancel), new TipDialog.OnTipDialogListener() {
                                        @Override
                                        public void onPositiveClick() {
                                            Intent loginIntent = new Intent(context,LoginActivity.class);
                                            tipIntents[1] = loginIntent;
                                            context.startActivities(tipIntents);
                                        }

                                        @Override
                                        public void onNegativeClick() {

                                        }
                                    });
                                    tipDialog.show();
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case CHAT_CLOSE_ALL:
                Intent iChat = new Intent(this,com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                startActivity(iChat);
                finish();
                break;
            case GOTO_HOME:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                startActivity(homeIntent);
                mListBottemBtn.get(0).performClick();
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe
    public void onEvent(Integer index){
        if (index==0){
            mListBottemBtn.get(index).performClick();
            mVPContent.setCurrentItem(index);
        }
    }
}
