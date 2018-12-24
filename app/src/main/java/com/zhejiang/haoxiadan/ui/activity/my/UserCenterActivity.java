package com.zhejiang.haoxiadan.ui.activity.my;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetTradeInfoListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.my.EditPersonInfoListener;
import com.zhejiang.haoxiadan.business.request.my.GetUserListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.common.UserInfo;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListData;
import com.zhejiang.haoxiadan.model.requestData.out.TradeListDataList;
import com.zhejiang.haoxiadan.model.requestData.out.my.UploadImageData;
import com.zhejiang.haoxiadan.model.requestData.out.my.UploadImgeBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.third.network.Server;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.dialog.BottomPhotoDialog;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CircleImageView;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.CompressPhotoUtils;
import com.zhejiang.haoxiadan.util.Constants;
import com.zhejiang.haoxiadan.util.StringUtils;
import com.zhejiang.haoxiadan.util.UploadImageUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Request;
import rx.functions.Action1;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class UserCenterActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_my_name)
    RelativeLayout rlMyName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.rl_my_sex)
    RelativeLayout rlMySex;
    @BindView(R.id.tv_factory)
    TextView tvFactory;
    @BindView(R.id.rl_my_factory)
    RelativeLayout rlMyFactory;
    @BindView(R.id.edt_advice)
    EditText edtAdvice;
    @BindView(R.id.iv_civ)
    CircleImageView ivCiv;
    @BindView(R.id.ll_change_pic)
    LinearLayout llChangePic;

    TextView tvPickPhone;
    TextView tvPickZone;
    TextView tvCancel;
    BottomPhotoDialog dialog ;
    ImageCaptureManager captureManager ;

    private String userIcon = null;
    private String sex ;
    private int mainIndustryId ;
    private String mainIndustry = null ;

    private OptionsPickerView pvCustomTime ;
    private List<String> m_date = new ArrayList<>();

    private UserRequester requester = new UserRequester();
    private UserInfoImp imp = new UserInfoImp();
    private class UserInfoImp extends DefaultRequestListener implements GetUserListener{

        @Override
        public void getUserInfo(UserInfo userInfo) {
            userIcon = userInfo.getUserHeadImg();
            ImageLoaderUtil.displayImageAndDefaultImg(userInfo.getUserHeadImg(),ivCiv,R.mipmap.ic_launcher);
            if (userInfo.getNickname()!=null){
                tvName.setText(userInfo.getNickname());
            }
            if (userInfo.getSex()!=null){
                sex = userInfo.getSex();
                if (userInfo.getSex().equals("1")){
                    tvSex.setText("男");
                }else {
                    tvSex.setText("女");
                }
            }

            if (!String.valueOf(userInfo.getMainIndustryId()).equals("")){
                mainIndustryId = userInfo.getMainIndustryId();
                tvFactory.setText(userInfo.getMainIndustry());
            }

            if (userInfo.getMainIndustry()!=null){
                mainIndustry = userInfo.getMainIndustry();
                edtAdvice.setText(userInfo.getMainProducts());
            }
        }

        @Override
        protected void onRequestFail() {

        }
    }
    private EditUserInfoImp infoImp = new EditUserInfoImp();
    private class EditUserInfoImp extends DefaultRequestListener implements EditPersonInfoListener{

        @Override
        public void onSuccess() {
            LoadingDialog.dismissDialog();
            finish();
        }

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context,errorMsg);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.user_center_layout);
        ButterKnife.bind(this);
        tvTitle.setText("个人中心");
        tvRight.setText("保存");
        tvRight.setTextColor(getResources().getColor(R.color.main_red));
        dialog = new BottomPhotoDialog(context, R.layout.view_bottom_popupwindow);
        requester.getUserInfo(context,getAccessToken(),imp);
        goodsRequester= new GoodsRequester();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickeName = (String) SharedPreferencesUtil.get(context, Constants.nickName,"");
        tvName.setText(nickeName);
    }

    @OnClick({R.id.iv_left, R.id.rl_my_name, R.id.rl_my_sex, R.id.rl_my_factory, R.id.ll_change_pic,R.id.tv_right })
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_change_pic:
                dialog.show();
                initBottomView(dialog.getView());
                break;
            case R.id.rl_my_name :
                Intent intent = new Intent(context,ChangeNewNameActivity.class);
                intent.putExtra("nickName",tvName.getText().toString());
                startActivity(intent);
                break;
            case R.id.rl_my_sex :
                m_date.clear();
                m_date.add("男");
                m_date.add("女");
                initSex(m_date ,"选择性别");
                pvCustomTime.show();
                break;
            case R.id.rl_my_factory :
                goodsRequester.getTradeInfo(context,tradeInfoImp);
                break;
            case R.id.tv_right :
                if (!tvName.getText().toString().equals("")&&!tvSex.getText().toString().equals("")){
                    if (!tvFactory.getText().toString().equals("")) {
                        if (!edtAdvice.getText().toString().replaceAll(" ","").equals("")) {
                            LoadingDialog.getInstance(this).show();
                            requester.editUserInfo(context, getAccessToken(), tvName.getText().toString(), sex, String.valueOf(mainIndustryId), edtAdvice.getText().toString(), userIcon, infoImp);
                        }else {
                            ToastUtil.show(context,"请输入主营产品");

                        }
                    }else {
                        ToastUtil.show(context,"请输入主营行业");
                    }
                }else {
                    ToastUtil.show(context,"请输入完整");
                }
                break;
        }
    }

    private List<TradeListData> dataList = new ArrayList<>();
    private List<String> stringList = new ArrayList<>();
    private GetTradeInfoImp tradeInfoImp = new GetTradeInfoImp();
    private class GetTradeInfoImp extends DefaultRequestListener implements GetTradeInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getTrade(TradeListDataList list) {
            dataList.clear();
            stringList.clear();
            dataList.addAll(list.getTradeList());
            for (TradeListData listData : dataList){
                stringList.add(listData.getTradeName());
            }
            initSex(stringList,"选择主营行业");
            pvCustomTime.show();
        }
    }
    GoodsRequester goodsRequester ;

    private void initSex(final List<String> date ,String title) {
        pvCustomTime = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str =  date.get(options1);
                if (str.equals("男")){
                    sex = "1";
                    tvSex.setText("男");
                }else if (str.equals("女")){
                    sex = "0";
                    tvSex.setText("女");
                }else {
                    tvFactory.setText(str);
                    for (TradeListData listData : dataList){
                        if (listData.getTradeName().equals(str)){
                            mainIndustryId = listData.getId();
                        }
                    }
                }

            }
        }).setTitleText(title).build();
        pvCustomTime.setPicker(date);
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions(UserCenterActivity.this);
            permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        captureManager = new ImageCaptureManager(context);
                        Intent intent = null;
                        try {
                            intent = captureManager.dispatchTakePictureIntent();
                            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else {
            captureManager = new ImageCaptureManager(context);
            Intent intent = null;
            try {
                intent = captureManager.dispatchTakePictureIntent();
                startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectPhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RxPermissions permissions = new RxPermissions(UserCenterActivity.this);
            permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
                @Override
                public void call(Boolean aBoolean) {
                    if (aBoolean) {
                        //选择相册
                        PhotoPicker.builder()
                                .setPhotoCount(1)
                                .setShowCamera(true)
                                .setShowGif(true)
                                .setPreviewEnabled(false)
                                .start(UserCenterActivity.this, PhotoPicker.REQUEST_CODE);
                    }
                }
            });
        }else {
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(true)
                    .setShowGif(true)
                    .setPreviewEnabled(false)
                    .start(UserCenterActivity.this, PhotoPicker.REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if(captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        try {
                            updatePhoto(imagePaht);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String imagePaht = photos.get(0) ;
                try {
                    updatePhoto(imagePaht);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private void initBottomView(View bottomView) {
        tvCancel = (TextView) bottomView.findViewById(R.id.tv_cancel);
        tvPickPhone = (TextView) bottomView.findViewById(R.id.tv_pick_phone);
        tvPickZone = (TextView) bottomView.findViewById(R.id.tv_pick_zone);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvPickPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
                dialog.dismiss();
            }
        });

        tvPickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
                dialog.dismiss();
            }
        });

    }

    public void updatePhoto(String path) throws IOException {
        ImageLoaderUtil.displayFromSDCard(path,ivCiv);
        LoadingDialog.getInstance(this).show();
        if (path!=null) {
            File file = new File(path);
            UploadImageUtils.postAsyn(Server.getUrl("Img/ImgSwfUpload"), new UploadImageUtils.ResultCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    LoadingDialog.dismissDialog();
                }

                @Override
                public void onResponse(String response) {
                    UploadImageData data = gson.fromJson(response,UploadImageData.class);
                        UploadImgeBean imgeBean = gson.fromJson(gson.toJson(data.getData()),UploadImgeBean.class);
                        userIcon = imgeBean.getUrl();
                        Log.e("sssss",userIcon);
                        LoadingDialog.dismissDialog();
                }
            },file,"imgFile");

        }
    }
}
