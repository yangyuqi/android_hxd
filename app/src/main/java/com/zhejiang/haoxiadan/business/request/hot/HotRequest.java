package com.zhejiang.haoxiadan.business.request.hot;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiuweiyu on 2017/9/4.
 */

public class HotRequest extends BaseRequester {

    public void getHotList(Context context ,HotListListener listener){
        Map<String,Object> map = new HashMap<>();
        post(context, Server.getUrl("hotsport/selectHotspotType"),map,listener,new HotListParser(listener));
    }

    public void getHotDetails(Context context ,String accessToken ,int id ,HotDetailsListener listener ){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("id",id);
        post(context,Server.getUrl("hotsport/selectHotspotInfo"),map,listener,new HotDetailsParser(listener));
    }

    public void getHotHotDetails(Context context ,String accessToken ,int id ,HotDetailsDataListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("id",id);
        post(context,Server.getUrl("hotsport/selectHotspotInfoContent"),map,listener,new HotDetailsDataParser(listener));
    }

    public void giveUp(Context context ,String accessToken ,int id ,GiveUpListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("type","1");
        map.put("docType","hot");
        map.put("id",id);
        post(context,Server.getUrl("hotsport/giveUp"),map,listener,new GiveUpParser(listener));
    }

    public void giveUpTop(Context context ,String accessToken ,int id ,GiveUpListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("type","2");
        map.put("docType","top");
        map.put("topId",id);
        post(context,Server.getUrl("hotsport/giveUp"),map,listener,new GiveUpParser(listener));
    }
}
