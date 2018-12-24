package com.zhejiang.haoxiadan.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;

/**
 * 空数据的页面
 * Created by KK on 2017/4/11.
 */

public class EmptyView extends RelativeLayout {


    private ImageView imgIcon;
    private TextView txtEmpty;
    private Button btnNetworkRefresh;

    private EMPTY_VIEW_TYPE type = EMPTY_VIEW_TYPE.DATA_EMPTY;
    private EmptyViewCallback callback;

    public enum EMPTY_VIEW_TYPE{
        DATA_EMPTY,     //数据空
        LOAD_ERROR      //加载失败
    }

    public interface EmptyViewCallback{
        void retry();
    }

    public EmptyView(Context context, EMPTY_VIEW_TYPE type) {
        super(context);
        this.type = type;
        init(context, null);
    }

    public EmptyView(Context context) {
        this(context, null, 0);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

    private void init(Context context, AttributeSet attrs){
        inflate(context, R.layout.view_data_empty, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmptyView);
        int emptyType = typedArray.getInt(R.styleable.EmptyView_emptyType, 0);
        switch (emptyType){
            case 0:
                type = EMPTY_VIEW_TYPE.DATA_EMPTY;
                break;
            case 1:
                type = EMPTY_VIEW_TYPE.LOAD_ERROR;
                break;
        }

        imgIcon = (ImageView) findViewById(R.id.img_icon);
        txtEmpty = (TextView) findViewById(R.id.txt_empty);
        btnNetworkRefresh = (Button) findViewById(R.id.btn_network_refresh);

        btnNetworkRefresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.retry();
                }
            }
        });

        initView();
    }
    private void initView(){
        switch (type){
            case DATA_EMPTY:
                imgIcon.setImageResource(R.mipmap.pg_null);
                txtEmpty.setText(R.string.network_not_data);
                txtEmpty.setVisibility(GONE);
                btnNetworkRefresh.setVisibility(View.GONE);
                break;
            case LOAD_ERROR:
                imgIcon.setImageResource(R.mipmap.pg_icon);
                txtEmpty.setText(R.string.network_err2);
                txtEmpty.setVisibility(VISIBLE);
                btnNetworkRefresh.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void hiddenView(){
        imgIcon.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.GONE);
    }
    public void showView(){
        imgIcon.setVisibility(View.VISIBLE);
        txtEmpty.setVisibility(View.VISIBLE);
    }

    public void setType(EMPTY_VIEW_TYPE type) {
        this.type = type;
        initView();
    }

    public void setCallback(EmptyViewCallback callback) {
        this.callback = callback;
    }
}
