package com.zhejiang.haoxiadan.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.db.GlobalDataUtil;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.util.Constants;

/**
 * Created by KK on 2017/2/20.
 */

public class BaseFragment extends Fragment {

    public Context context ;
    protected Gson gson ;
    WindowManager wm ;
    protected int win_width ;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context ;
        gson = new Gson();
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        win_width = wm.getDefaultDisplay().getWidth();
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
        return (String) GlobalDataUtil.getObject(getActivity(), Constants.GLOBAL_DATA_KEY_TRUE_NAME);
    }

    /***
     * 等级名称
     * @return
     */
    public String getLevelName(){
        return (String) GlobalDataUtil.getObject(getActivity(), Constants.GLOBAL_DATA_KEY_LEVELNAME);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ImageLoaderUtil.clearImageCache();
    }
}
