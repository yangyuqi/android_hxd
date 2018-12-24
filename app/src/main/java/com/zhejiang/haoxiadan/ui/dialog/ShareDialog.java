package com.zhejiang.haoxiadan.ui.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.util.Constants;

/**
 * Created by qiuweiyu on 2017/12/21.
 */

public class ShareDialog extends Dialog {
    private Context context;
    private String strContent ;
    private TextView textContent ;
    private TextView tv_ok,tv_no;

    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;

    public ShareDialog(@NonNull Context context ,String text ) {
        super(context, R.style.DeleteDialogStyle);
        this.context = context;
        strContent = text;
        mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    }

    public ShareDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.share_dialog, null);
        setContentView(view);
        textContent = (TextView) view.findViewById(R.id.tv_date);
        tv_ok=  (TextView) view.findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new clickListener());
        tv_no = (TextView) view.findViewById(R.id.tv_no);
        tv_no.setOnClickListener(new clickListener());

        textContent.setText(strContent);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.65); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.tv_no:
                    dismiss();
                    break;
                case R.id.tv_ok:
//                    mClipData = ClipData.newPlainText(null, strContent);
//                    mClipboardManager.setPrimaryClip(mClipData);
                    mClipboardManager.setText(strContent);
                    SharedPreferencesUtil.put(context, Constants.clearClipBoard,strContent);
                    dismiss();
                    break;
            }
        }
    };
}
