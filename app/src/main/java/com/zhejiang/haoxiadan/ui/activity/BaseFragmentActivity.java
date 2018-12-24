package com.zhejiang.haoxiadan.ui.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.MyApplication;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.HasCommondLiatener;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.choseModel.CommondGoodsBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.dialog.CommondGoodsDialog;
import com.zhejiang.haoxiadan.ui.fragment.chosen.ChosenFragment;
import com.zhejiang.haoxiadan.util.Constants;

public class BaseFragmentActivity extends AppCompatActivity {

    public Context context ;
    public Gson gson ;
    private GoodsRequester requester = new GoodsRequester();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        gson = new Gson();
        GangUpInvite();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        GangUpInvite();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void GangUpInvite() {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        String data = (String) SharedPreferencesUtil.get(context,Constants.clearClipBoard,"");
        if (!clipboard.hasText()){
            return;
        }
        if (!data.equals("")){
            SharedPreferencesUtil.remove(context,Constants.clearClipBoard);
            if (!clipboard.getText().toString().equals(data)){
                requester.getHasCommond(context, clipboard.getText().toString(), commondInterface);
            }else {
                if (clipboard.getText().toString().indexOf(context.getString(R.string.label_money))>0){
                    clipboard.setText(null);
                }
            }
        }else {
            requester.getHasCommond(context, clipboard.getText().toString(), commondInterface);
        }
    }

    private HasCommondInterface commondInterface = new HasCommondInterface();
    public class HasCommondInterface extends DefaultRequestListener implements HasCommondLiatener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetData(CommondGoodsBean goodsBean) {
            if (goodsBean!=null){
                CommondGoodsDialog commondGoodsDialog = new CommondGoodsDialog(context,goodsBean);
                commondGoodsDialog.show();
                ClipboardManager manager =  (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                manager.setText(null);
//                manager.setPrimaryClip(ClipData.newPlainText(newPlainTextull,null));
            }
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
        }
    }

    /***
     * 获取accesstoken
     * @return
     */
    public String getAccessToken(){
        return (String) SharedPreferencesUtil.get(context,Constants.accessToken,"");
    }

    /***
     * 获取用户名
     * @return
     */
    public String getUserName(){
        return (String) GlobalDataUtil.getObject(BaseFragmentActivity.this, Constants.GLOBAL_DATA_KEY_TRUE_NAME);
    }

    /***
     * 等级名称
     * @return
     */
    public String getLevelName(){
        return (String) GlobalDataUtil.getObject(BaseFragmentActivity.this, Constants.GLOBAL_DATA_KEY_LEVELNAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageLoaderUtil.clearImageCache();
    }


    /**
     * 添加Activity到维护的堆栈
     */
    public void addActivity(Activity activity) {
        MyApplication.appManager.addActivity(activity);
    }

    /**
     * 清空维护的堆栈
     */
    public void cleanActivity() {
        MyApplication.appManager.finishAllActivity();
    }
}
