package com.zhejiang.haoxiadan.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;


/**
 * Created by Administrator on 2017/6/27.
 */

public class BottomPhotoDialog extends Dialog {
    private TextView tvPickPhone;
    private TextView tvPickZone;
    private TextView tvCancel;


    private ImgCallback imgCallback;
    public interface ImgCallback {
        void takePhoto();
        void selectPhoto();
    }

    private Context context;

    private int layout ;
    View view ;

    public BottomPhotoDialog(Context context , int mlayout) {
        super( context , R.style.DeleteDialogStyle);
        this.context = context;
        layout = mlayout ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();

    }

    public View getView() {
        return view;
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        view =inflater.inflate(layout, null);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        params.width = d.widthPixels;
        params.height = d.heightPixels;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);

        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvPickPhone = (TextView) findViewById(R.id.tv_pick_phone);
        tvPickZone = (TextView) findViewById(R.id.tv_pick_zone);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvPickPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgCallback != null){
                    imgCallback.takePhoto();
                }
                dismiss();
            }
        });

        tvPickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgCallback != null){
                    imgCallback.selectPhoto();
                }
                dismiss();
            }
        });
    }

    public void setImgCallback(ImgCallback callback){
        this.imgCallback = callback;
    }
}
