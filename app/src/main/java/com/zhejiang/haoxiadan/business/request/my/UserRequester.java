package com.zhejiang.haoxiadan.business.request.my;

import android.content.Context;

import com.zhejiang.haoxiadan.business.BaseRequester;
import com.zhejiang.haoxiadan.business.request.hot.WelcomePhotoListener;
import com.zhejiang.haoxiadan.business.request.hot.WelcomePhotoParser;
import com.zhejiang.haoxiadan.third.network.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by qiuweiyu on 2017/8/31.
 */

public class UserRequester extends BaseRequester {

    public void register(Context context ,String mobile ,String password , String smsCode ,RegisterListener registerListener){
        Map<String ,Object> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("password",password);
        map.put("smsCode",smsCode);
        map.put("type","1");
        post(context, Server.getUrl("user/register"),map,registerListener , new RegisterParser(registerListener));
    }

    public void userIsExist(Context context ,String mobile ,ComListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",mobile);
        post(context, Server.getUrl("user/userExistInfo") , map,listener,new ComParser(listener));
    }

    public void sendCode(Context context ,String mobile , SendCodeListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("mobile",mobile);
        post(context,Server.getUrl("msgSet/sendMsg"),map,listener,new SendCodeParser(listener));
    }

    public void findPwd(Context context , String mobile ,String smsCode ,String newPassword , ComListener l){
        Map<String,Object> map = new HashMap<>();
        map.put("mobile",mobile);
        map.put("smsCode",smsCode);
        map.put("newPassword",newPassword);
        post(context,Server.getUrl("user/getBackPassWord"),map,l,new ComParser(l));
    }

    public void login(Context context ,String mobile ,String password ,String deviceNo ,String deviceType ,LoginListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("userName",mobile);
        map.put("password",password);
        map.put("deviceNo",deviceNo);
        map.put("deviceType",deviceType);
        post(context,Server.getUrl("user/login"),map,listener,new LoginParse(listener));
    }
    public void loginOut(Context context , String accessToken ,LoginOutListener listener){
        Map<String , Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        post(context,Server.getUrl("user/loginOut"),map,listener,new LoginOutParser(listener));
    }

    public void getUserInfo(Context context ,String accessToken ,GetUserListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        post(context,Server.getUrl("valueAdd/selectPersonalInfor"),map,listener,new GetUserInfoParser(listener));
    }

    public void editUserInfo (Context context ,String accessToken , String nickName ,String sex ,String mainIndustryId ,String mainProducts ,String userHeadImg ,EditPersonInfoListener infoListener){
        Map<String , Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("nickName",nickName);
        map.put("sex",sex);
        map.put("mainIndustryId",mainIndustryId);
        map.put("mainProducts",mainProducts);
        map.put("userHeadImg",userHeadImg);
        post(context,Server.getUrl("valueAdd/editPersonalInfor"),map,infoListener,new EditPersonParser(infoListener));
    }

    public void getMyFooter(Context context ,String accessToken ,int currentPage ,int showCount ,PersonFootListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.getUrl("footPrint/findFootPrint"),map,listener,new PersonFooterParser(listener));
    }

    public void myCollectionGoods(Context context ,String accessToken , int currentPage ,int showCount ,CollectionGoodsListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.getUrl("favorite/selectFavoriteAll"),map,listener,new CollectionGoodsParser(listener));
    }

    public void myCollectStoreGoods(Context context ,String accessToken , int currentPage ,int showCount ,CollectStoreListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.getUrl("favorite/selectFavoriteStoreAll"),map,listener,new CollectionStoreParser(listener));
    }

    public void getAllAddress(Context context ,String accessToken ,int currentPage ,int showCount ,GetAllAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("currentPage",currentPage);
        map.put("showCount",showCount);
        post(context,Server.getUrl("address/selectAddressAll"),map,listener,new GetAllAddressParser(listener));
    }

    public void initAddress(Context context ,InitAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        post(context,Server.getUrl("area/selectAreaAll"),"",listener,new InitAddressParser(listener));
    }

    public void addNewAddress(Context context,String trueName ,String areaInfo ,String mobile ,String telephone ,int defaultVal ,String areaId ,String accessToken ,AddNewAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("trueName",trueName);
        map.put("areaInfo",areaInfo);
        map.put("mobile",mobile);
        map.put("telephone",telephone);
        map.put("defaultVal",defaultVal);
        map.put("areaId",areaId);
        map.put("accessToken",accessToken);
        post(context,Server.getUrl("address/addAddress"),map,listener,new AddNewAddressParser(listener));
    }

    public void editCurrentAddress(Context context,String trueName ,String areaInfo ,String mobile ,String telephone ,int defaultVal ,String areaId ,String accessToken ,String id ,EditCurrentAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("trueName",trueName);
        map.put("areaInfo",areaInfo);
        map.put("mobile",mobile);
        map.put("telephone",telephone);
        map.put("defaultVal",defaultVal);
        map.put("areaId",areaId);
        map.put("accessToken",accessToken);
        map.put("id",id);
        post(context,Server.getUrl("address/editAddress"),map,listener,new EditCurrentAddressParser(listener));
    }

    public void deleteCurrentAddress(Context context ,String id ,String accessToken ,DeleteCurrentAddressListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("accessToken",accessToken);
        post(context,Server.getUrl("address/deleteAddress"),map,listener,new DeleteCurrentAddressParser(listener));
    }

    public void changeDefaultAddress(Context context ,String accessToken ,String id ,ChangeDefaultListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("id",id);
        post(context,Server.getUrl("address/editDefault"),map,listener,new ChangeDefaultParser(listener));
    }

    public void alertPassword(Context context ,String accessToken ,String password ,String newPassword,AlertPasswordListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("password",password);
        map.put("newPassword",newPassword);
        post(context,Server.getUrl("user/alertPassWord"),map,listener,new AlertPasswordParser(listener));
    }

    public void editUnickName(Context context ,String accessToken ,String nickName ,EditUnickNameListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("nickName",nickName);
        post(context,Server.getUrl("user/editUnickName"),map,listener,new EditUnickNameParser(listener));
    }

    /***
     * 获取所有收藏
     */
    public void article(Context context,String title,ArticleListener aboutUsListener){
        Map<String, Object> params = new HashMap<>();
        params.put("title",title);
        post(context, "http://" + Server.HOST  + Server.APP +"/api/app/v1"+"/title/article", params, aboutUsListener, new ArticleParser(aboutUsListener));
    }

    public void deleteAllFoot(Context context , String accessToken , List<Integer> Ids ,DeleteAllFooterListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("ids",Ids);
        post(context,Server.getUrl("footPrint/deleteFootPrint"),map,listener,new DeleteAllFooterParser(listener));
    }

    public void deleteCollectStore(Context context ,String accessToken ,List<Integer> id,DeleteCollectStoreListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("accessToken",accessToken);
        map.put("id",id);
        post(context,Server.getUrl("favorite/deleteFavoriteStore"),map,listener,new DeleteCollectStoreParser(listener));
    }

    /***
     * 版本更新
     * @param context
     * @param versionSource
     * @param versionNum
     * @param versionManageListener
     */
    public void versionManage(Context context,String versionSource,String versionNum,VersionManageListener versionManageListener){
        Map<String, Object> params = new HashMap<>();
        params.put("versionSource",versionSource);
        params.put("versionNum",versionNum);
        post(context, Server.getUrl("title/versionManage"), params, versionManageListener, new VersionManageParser(versionManageListener));
    }

    public void userAgreement(Context context ,UserAgreementListener listener){
        Map<String,Object> map = new HashMap<>();
        post(context,Server.getUrl("user/agreement"),map,listener,new UserAgreementParser(listener));
    }


    public void WelcomePhoto(Context context, WelcomePhotoListener listener){
        Map<String,Object> map = new HashMap<>();
        map.put("versionSource","android");
        post(context,Server.getUrl("sysconfig/queryWelcome"),map,listener,new WelcomePhotoParser(listener));
    }
}
