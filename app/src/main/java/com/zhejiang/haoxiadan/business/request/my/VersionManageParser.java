package com.zhejiang.haoxiadan.business.request.my;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.VersionManageData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by qiuweiyu on 2017/9/18.
 */

public class VersionManageParser extends AbsBaseParser {
    public VersionManageParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {
        VersionManageData versionManageData = null;
        if(data != null){
            try {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject version = jsonObject.getJSONObject("version");
                if(version != null){
                    versionManageData = mDataParser.parseObject(version.toString(),VersionManageData.class);
                    if(versionManageData.getVersionLinkUrl()==null || "".equals(versionManageData.getVersionLinkUrl())){
                        versionManageData = null;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        VersionManageListener listener = (VersionManageListener) mOnBaseRequestListener.get();
        if (listener!=null){
            listener.onSuccess(versionManageData);
        }
    }
}
