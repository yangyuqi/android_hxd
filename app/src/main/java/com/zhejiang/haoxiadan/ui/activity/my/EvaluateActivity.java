package com.zhejiang.haoxiadan.ui.activity.my;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.Common.UploadRequester;
import com.zhejiang.haoxiadan.business.request.cart.AddEvaluateListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.ApplyRefundImgsAdapter;
import com.zhejiang.haoxiadan.ui.dialog.BottomPhotoDialog;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Event;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

/**
 * 评论
 */
public class EvaluateActivity extends BaseActivity {

    private LinearLayout leftLl;
    private TextView releaseTv;
    private ImageView goodsIconIv;
    private RatingBar orderRb;
    private EditText evaluateEt;
    private GridView gridView;
    private CheckBox anonymousCb;
    private RatingBar logisticsRb;
    private RatingBar serviceRb;

    private ApplyRefundImgsAdapter adapter;
    private List<String> localPaths;
    private List<String> imgUrls;
    private List<String> temp = new ArrayList<>();

    private String goodsIcon;
    private String goodsId;
    private String orderId;

    private BottomPhotoDialog dialog ;
    private ImageCaptureManager captureManager;

    private int maxImgNum = 5;//最多几张图片

    private OrderRequester orderRequester;
    private AddEvaluateListenerImpl addEvaluateListener;
    private class AddEvaluateListenerImpl extends DefaultRequestListener implements AddEvaluateListener{

        @Override
        public void onAddEvaluateSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
            LoadingDialog.dismissDialog();
            finish();
        }

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }
    }
    private UploadRequester uploadRequester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);

        goodsIcon = getIntent().getStringExtra("goodsIcon");
        goodsId = getIntent().getStringExtra("goodsId");
        orderId = getIntent().getStringExtra("orderId");
        if(orderId == null || goodsId == null){
            finish();
            return;
        }
        orderRequester = new OrderRequester();
        addEvaluateListener = new AddEvaluateListenerImpl();
        uploadRequester = new UploadRequester();

        initView();
        initEvent();
        initData();
    }

    private void initView(){
        leftLl = (LinearLayout) findViewById(R.id.ll_left);
        releaseTv = (TextView) findViewById(R.id.tv_right);
        goodsIconIv = (ImageView) findViewById(R.id.iv_goods_icon);
        orderRb = (RatingBar) findViewById(R.id.rb_order_stars);
        evaluateEt = (EditText) findViewById(R.id.et_evaluate);
        gridView = (GridView) findViewById(R.id.gv_imgs);
        anonymousCb = (CheckBox) findViewById(R.id.cb_anonymous);
        logisticsRb = (RatingBar) findViewById(R.id.rb_logistics_stars);
        serviceRb = (RatingBar) findViewById(R.id.rb_service_stars);

        localPaths = new ArrayList<>();
        localPaths.add("");

        adapter = new ApplyRefundImgsAdapter(this,localPaths);
        gridView.setAdapter(adapter);

        dialog = new BottomPhotoDialog(this, R.layout.view_bottom_popupwindow);
    }

    private void initEvent(){
        leftLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        releaseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog.getInstance(EvaluateActivity.this).show();
                imgUrls = new ArrayList<>();
                //上传图片
                imgUrls.clear();
                temp.clear();
                temp.addAll(localPaths);
                if(localPaths.size()>1){
                    temp.remove(temp.size()-1);
                    uploadImgs();
                }else{
                    //发布
                    orderRequester.addEvaluate(EvaluateActivity.this,orderId,goodsId,
                            orderRb.getNumStars(),
                            serviceRb.getNumStars(),
                            logisticsRb.getNumStars(),
                            evaluateEt.getText().toString(),
                            imgUrls,
                            anonymousCb.isChecked(),
                            addEvaluateListener);
                }
            }
        });
        dialog.setImgCallback(new BottomPhotoDialog.ImgCallback() {
            @Override
            public void takePhoto() {
                toTakePhoto();
            }

            @Override
            public void selectPhoto() {
                toSelectPhoto();
            }
        });
        adapter.setSelectImgCallback(new ApplyRefundImgsAdapter.SelectImgCallback() {
            @Override
            public void onSelect() {
                if(localPaths.size()>maxImgNum){
                    ToastUtil.show(EvaluateActivity.this,String.format(getString(R.string.tip_max_img_num),maxImgNum+""));
                }else{
                    dialog.show();
                }
            }

            @Override
            public void onDelete(int position) {
                localPaths.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void uploadImgs(){
            if(temp.size()==0){
                //发布
                orderRequester.addEvaluate(EvaluateActivity.this,orderId,goodsId,
                        orderRb.getNumStars(),
                        serviceRb.getNumStars(),
                        logisticsRb.getNumStars(),
                        evaluateEt.getText().toString(),
                        imgUrls,
                        anonymousCb.isChecked(),
                        addEvaluateListener);
            }else{
                String path = temp.get(temp.size()-1);
                temp.remove(temp.size()-1);
                uploadRequester.uploadImg(path, new UploadRequester.UploadListener() {
                    @Override
                    public void onUploadFailed() {
                        uploadImgs();
                    }

                    @Override
                    public void onUploadSuccess(String url) {
                        imgUrls.add(url);
                        uploadImgs();
                    }
                });
            }

    }

    private void initData(){
        ImageLoaderUtil.displayImage(goodsIcon,goodsIconIv);
    }

    private void toTakePhoto() {
        RxPermissions permissions = new RxPermissions(EvaluateActivity.this);
        permissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    captureManager = new ImageCaptureManager(EvaluateActivity.this);
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
    }

    private void toSelectPhoto() {
        RxPermissions permissions = new RxPermissions(EvaluateActivity.this);
        permissions.request(Manifest.permission.CAMERA,Manifest.permission.VIBRATE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean){
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(maxImgNum+1-localPaths.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(EvaluateActivity.this ,PhotoPicker.REQUEST_CODE);
                }
            }
        });
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
                        localPaths.add(0,imagePaht);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE){
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for(String imagePath : photos){
                    localPaths.add(0,imagePath);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
