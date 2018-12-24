package com.zhejiang.haoxiadan.ui.dialog;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.ui.activity.MainActivity;
import com.zhejiang.haoxiadan.ui.activity.cart.CartActivity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.my.UserCenterActivity;
import com.zhejiang.haoxiadan.ui.popmenu.DropPopMenu;
import com.zhejiang.haoxiadan.ui.popmenu.MenuItem;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/12/26.
 */

public class CommonUseDialog {

    public static void showDialog(final Context context , View view , final String share){
        DropPopMenu dropPopMenu = new DropPopMenu(context);
        dropPopMenu.setTriangleIndicatorViewColor(Color.WHITE);
        dropPopMenu.setBackgroundResource(R.drawable.bg_drop_pop_menu_white_shap);
        dropPopMenu.setItemTextColor(Color.BLACK);
        dropPopMenu.setIsShowIcon(true);
        dropPopMenu.setOnItemClickListener(new DropPopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case 1 :
                        Intent[] intents = new Intent[2];
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intents[0] = mainIntent;
                        Intent msgIntent = new Intent(context,com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                        intents[1] = msgIntent;
                        context.startActivities(intents);
                        break;
                    case 2 :
                        EventBus.getDefault().post(Event.GOTO_HOME);
                        break;
                    case 3 :
                        String token = (String) SharedPreferencesUtil.get(context, Constants.accessToken,"");
                        if(!token.equals("")){
                            Intent intent = new Intent(context, CartActivity.class);
                            context.startActivity(intent);
                        }else{
                            //去登录
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        break;
                    case 4 :
                        Intent intent = new Intent(context, UserCenterActivity.class);
                        context.startActivity(intent);
                        break;
                    case 5 :
                        ShareDialog shareDialog = new ShareDialog(context,share);
                        shareDialog.show();
                        break;
                }
            }
        });
        dropPopMenu.setMenuList(getIconMenuList());

        dropPopMenu.show(view);
    }

    private static List<MenuItem> getIconMenuList() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem(R.mipmap.icon_pop_message, 1, "消息"));
        list.add(new MenuItem(R.mipmap.icon_pop_home, 2, "首页"));
        list.add(new MenuItem(R.mipmap.icon_pop_car, 3, "购物车"));
        list.add(new MenuItem(R.mipmap.icon_pop_person, 4, "个人中心"));
        list.add(new MenuItem(R.mipmap.icon_share, 5, "分享"));
        return list;
    }


    public static void showComDialog(final Context context , View view ){
        DropPopMenu dropPopMenu = new DropPopMenu(context);
        dropPopMenu.setTriangleIndicatorViewColor(Color.WHITE);
        dropPopMenu.setBackgroundResource(R.drawable.bg_drop_pop_menu_white_shap);
        dropPopMenu.setItemTextColor(Color.BLACK);
        dropPopMenu.setIsShowIcon(true);
        dropPopMenu.setOnItemClickListener(new DropPopMenu.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id, MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case 1 :
                        Intent[] intents = new Intent[2];
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intents[0] = mainIntent;
                        Intent msgIntent = new Intent(context,com.zhejiang.haoxiadan.third.jiguang.chat.activity.MainActivity.class);
                        intents[1] = msgIntent;
                        context.startActivities(intents);
                        break;
                    case 2 :
                        EventBus.getDefault().post(Event.GOTO_HOME);
                        break;
                    case 3 :
                        String token = (String) SharedPreferencesUtil.get(context, Constants.accessToken,"");
                        if(!token.equals("")){
                            Intent intent = new Intent(context, CartActivity.class);
                            context.startActivity(intent);
                        }else{
                            //去登录
                            context.startActivity(new Intent(context, LoginActivity.class));
                        }
                        break;
                    case 4 :
                        Intent intent = new Intent(context, UserCenterActivity.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
        dropPopMenu.setMenuList(getIconMenuComList());

        dropPopMenu.show(view);
    }

    private static List<MenuItem> getIconMenuComList() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem(R.mipmap.icon_pop_message, 1, "消息"));
        list.add(new MenuItem(R.mipmap.icon_pop_home, 2, "首页"));
        list.add(new MenuItem(R.mipmap.icon_pop_car, 3, "购物车"));
        list.add(new MenuItem(R.mipmap.icon_pop_person, 4, "个人中心"));
        return list;
    }
}
