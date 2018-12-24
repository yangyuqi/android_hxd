package com.zhejiang.haoxiadan.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;

/**
 * 通用的提示对话框
 */
public class TipDialog extends Dialog {

    private final String TAG = "TipDialog";

    private TextView mTitleTv;
    private TextView mContentTv;
    private TextView mNegativeBtn;
    private TextView mPositiveBtn;

    private String mTitle;
    private String mPositive;
    private String mNegative;
    private String mContent;

    private OnTipDialogListener mOnTipDialogListener;

    private boolean showContent = false;

    //点击确认取消是否自动消失
    private boolean autoDismiss = true;

    private Context mContext;

    /**
     * TipDialog点击事件回调对象
     */
    public interface OnTipDialogListener {
        /**
         * 确认按钮点击事件回调方法
         */
        void onPositiveClick();

        /**
         * 取消按钮点击事件回调方法
         */
        void onNegativeClick();
    }

    public TipDialog(Context context, String title, String positive, String negative) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mPositive = positive;
        this.mNegative = negative;
        init();
    }


    public TipDialog(Context context, String title, String positive, String negative, String content, boolean show) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mPositive = positive;
        this.mNegative = negative;
        this.mContent = content;
        this.showContent = show;
        init();
    }

    public TipDialog(Context context, String title) {
        this(context);
        mContext = context;
        this.mTitle = title;
        init();
    }

    public TipDialog(Context context, int titleStrId) {
        this(context);
        mContext = context;
        this.mTitle = context.getString(titleStrId);
        init();
    }

    public TipDialog(Context context, String title, OnTipDialogListener onTipDialogListener) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mOnTipDialogListener = onTipDialogListener;
        init();
    }

    public TipDialog(Context context, int titleStrId, OnTipDialogListener onTipDialogListener) {
        this(context);
        mContext = context;
        this.mTitle = context.getString(titleStrId);
        this.mOnTipDialogListener = onTipDialogListener;
        init();
    }

    public TipDialog(Context context, String title, String positive, String negative, OnTipDialogListener onTipDialogListener) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mPositive = positive;
        this.mNegative = negative;
        this.mOnTipDialogListener = onTipDialogListener;
        init();
    }

    public TipDialog(Context context, String title, String content, OnTipDialogListener onTipDialogListener) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mContent = content;
        this.showContent = true;
        this.mOnTipDialogListener = onTipDialogListener;
        init();
    }
    public TipDialog(Context context, String title, String content, String positive, String negative, OnTipDialogListener onTipDialogListener) {
        this(context);
        mContext = context;
        this.mTitle = title;
        this.mContent = content;
        this.showContent = true;
        this.mPositive = positive;
        this.mNegative = negative;
        this.mOnTipDialogListener = onTipDialogListener;
        init();
    }

    private TipDialog(Context context) {
        super(context, R.style.style_dialog_tip);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitleTv.setText(title);
    }

    public void setOnTipDialogListener(OnTipDialogListener onTipDialogListener) {
        this.mOnTipDialogListener = onTipDialogListener;
    }

    private void init() {
        setContentView(R.layout.dialog_tip);

        initView();
        initValue();
        initEvent();
    }


    private void initView() {
        mTitleTv = (TextView) findViewById(R.id.tv_title);
        mContentTv = (TextView) findViewById(R.id.tv_content);
        mNegativeBtn = (TextView) findViewById(R.id.btn_negative);
        mPositiveBtn = (TextView) findViewById(R.id.btn_positive);

        if(showContent){
            mContentTv.setVisibility(View.VISIBLE);
            mNegativeBtn.setTextColor(mContext.getResources().getColor(R.color.text_blue));
            mPositiveBtn.setTextColor(mContext.getResources().getColor(R.color.text_blue));
        }
    }

    private void initValue() {
        Log.d(TAG, "mTitle : " + mTitle);
        mTitleTv.setText(mTitle);
        mContentTv.setText(mContent);
        if(mPositive!=null){
            mPositiveBtn.setText(mPositive);
        }
        if(mNegative!=null){
            mNegativeBtn.setText(mNegative);
        }
    }

    private void initEvent() {
        mNegativeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTipDialogListener != null) {
                    mOnTipDialogListener.onNegativeClick();
                } else {
                    Log.w(TAG, "mOnTipDialogListener == null");
                }
                if(autoDismiss){
                    cancel();
                }
            }
        });
        mPositiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTipDialogListener != null) {
                    mOnTipDialogListener.onPositiveClick();
                } else {
                    Log.w(TAG, "mOnTipDialogListener == null");
                }
                if(autoDismiss){
                    cancel();
                }
            }
        });
    }


    public void setAutoDismiss(boolean autoDismiss){
        this.autoDismiss = autoDismiss;
    }
}
