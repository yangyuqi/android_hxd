package com.zhejiang.haoxiadan.ui.dialog;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.choseModel.CommondGoodsBean;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qiuweiyu on 2017/12/25.
 */

public class CommondGoodsDialog extends Dialog {

    private Context context ;
    private CommondGoodsBean commondGoodsBean ;
    private ImageView iv_goods ,iv_close ;
    private TextView tv_price , tv_name ,tv_go ;


    public CommondGoodsDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public CommondGoodsDialog(@NonNull Context context ,CommondGoodsBean bean ) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        commondGoodsBean = bean ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.commond_goods_dialog_layout, null);
        setContentView(view);
        iv_goods = (ImageView) view.findViewById(R.id.commond_iv);
        iv_close = (ImageView) view.findViewById(R.id.iv_close);
        tv_price = (TextView) view.findViewById(R.id.commond_tv_price);
        tv_name = (TextView) view.findViewById(R.id.tv_commond_tv_name);
        tv_go = (TextView) view.findViewById(R.id.tv_go);

        Glide.with(context).load(commondGoodsBean.getGoodsMainPhotoPath()).placeholder(R.mipmap.ic_launcher).into(iv_goods);
        tv_price.setText(context.getString(R.string.label_money)+commondGoodsBean.getPrice());
        tv_name.setText(commondGoodsBean.getGoodsName());

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.75); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isGoodsDetailsActivityTop()) {
                    EventBus.getDefault().post(Event.FINISH_GOODS_ACTIVITY);
                }
                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", String.valueOf(commondGoodsBean.getGoodsId()));
                context.startActivity(intent);
                dismiss();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


    private boolean isGoodsDetailsActivityTop(){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String name = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        return name.equals(GoodsDetailsActivity.class.getName());
    }
}
