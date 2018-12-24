package com.zhejiang.haoxiadan.business.request.Common;

import com.google.gson.Gson;
import com.zhejiang.haoxiadan.model.requestData.out.my.UploadImageData;
import com.zhejiang.haoxiadan.model.requestData.out.my.UploadImgeBean;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.util.UploadImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * Created by KK on 2017/9/9.
 */
public class UploadRequester {

    public Gson gson = new Gson();

    public interface UploadListener{
        void onUploadFailed();
        void onUploadSuccess(String url);
    }

    /**
     * 上传图片
     * @param path
     * @param listener
     */
    public void uploadImg(String path,final UploadListener listener){
        if (path!=null) {
            File file = new File(path);
            try {
                UploadImageUtils.postAsyn(Server.getUrl("Img/ImgSwfUpload"), new UploadImageUtils.ResultCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        if(listener!= null){
                            listener.onUploadFailed();
                        }
                    }

                    @Override
                    public void onResponse(String response) {
                        UploadImageData data = gson.fromJson(response,UploadImageData.class);
                        UploadImgeBean imgeBean = gson.fromJson(gson.toJson(data.getData()),UploadImgeBean.class);
                        if(listener!= null){
                            listener.onUploadSuccess(imgeBean.getUrl());
                        }
                    }
                },file,"imgFile");
            } catch (IOException e) {
                e.printStackTrace();
                if(listener!= null){
                    listener.onUploadFailed();
                }
            }

        }
    }
}
