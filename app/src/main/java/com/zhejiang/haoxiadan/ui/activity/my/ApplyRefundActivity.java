package com.zhejiang.haoxiadan.ui.activity.my;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.Common.UploadRequester;
import com.zhejiang.haoxiadan.business.request.cart.ApplyRefundOrderListener;
import com.zhejiang.haoxiadan.business.request.cart.GetRefundOrderReasonListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.Reason;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.ApplyRefundImgsAdapter;
import com.zhejiang.haoxiadan.ui.dialog.BottomPhotoDialog;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.KeyBoardUtil;
import com.zhejiang.haoxiadan.util.NumberUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.utils.ImageCaptureManager;
import rx.functions.Action1;

public class ApplyRefundActivity extends BaseActivity {

    @BindView(R.id.common_title)
    CommonTitle commonTitle;
    @BindView(R.id.tv_high_money)
    TextView tvHighMoney;
    @BindView(R.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R.id.edt_select_money)
    EditText edtSelectMoney;
    @BindView(R.id.rl_show1)
    RelativeLayout rlShow1;
    @BindView(R.id.ll_show2)
    LinearLayout llShow2;

    private TextView selectReasonTv;
    private TextView refundMsgTv;
    private GridView gridView;
    private TextView submitTv;

    private ApplyRefundImgsAdapter adapter;
    private List<String> localPaths;
    private List<String> imgUrls;
    private List<String> temp = new ArrayList<>();
    private UploadRequester uploadRequester;
    private BottomPhotoDialog dialog;
    private ImageCaptureManager captureManager;

    @BindView(R.id.rl_goods_icon)
    RelativeLayout rlGoodsIcon;
    @BindView(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.rl_goods_name)
    RelativeLayout rlGoodsName;
    @BindView(R.id.tv_goods_style)
    TextView tvGoodsStyle;
    @BindView(R.id.tv_goods_count)
    TextView tvGoodsCount;
    @BindView(R.id.iv_goods_type)
    ImageView ivGoodsType;
    @BindView(R.id.iv_goods_icon)
    ImageView ivGoodsIcon;

    private OptionsPickerView pickerView;
    private List<Reason> reasons;

    private String orderId;
    private boolean needPic = false;

    private OrderGoods orderGoods;
    private int type;

    private int maxImgNum = 5;//最多几张图片

    private OrderRequester orderRequester;
    private GetRefundOrderReasonListenerImpl getRefundOrderReasonListener;

    private class GetRefundOrderReasonListenerImpl extends DefaultRequestListener implements GetRefundOrderReasonListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onGetRefundOrderReasonSuccess(List<Reason> reasonList) {
            reasons = reasonList;
            pickerView.setPicker(reasons);
        }
    }

    private ApplyRefundOrderListenerImpl applyRefundOrderListener;

    private class ApplyRefundOrderListenerImpl extends DefaultRequestListener implements ApplyRefundOrderListener {

        @Override
        public void onApplyRefundOrderSuccess() {
            EventBus.getDefault().post(Event.REFRESH_ORDER);
            LoadingDialog.dismissDialog();
            finish();
        }

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_refund);
        ButterKnife.bind(this);

        orderGoods = (OrderGoods) getIntent().getSerializableExtra("OrderGoods");
        type = getIntent().getIntExtra("type", 0);

        if (orderGoods == null) {
            finish();
            return;
        }
        orderId = getIntent().getStringExtra("orderId");
        needPic = getIntent().getBooleanExtra("needPic", false);

        orderRequester = new OrderRequester();
        getRefundOrderReasonListener = new GetRefundOrderReasonListenerImpl();
        applyRefundOrderListener = new ApplyRefundOrderListenerImpl();
        uploadRequester = new UploadRequester();

        initView();
        initEvent();
        initData();
    }

    private void initView() {
        commonTitle = (CommonTitle) findViewById(R.id.common_title);
        selectReasonTv = (TextView) findViewById(R.id.tv_select_reason);
        refundMsgTv = (TextView) findViewById(R.id.et_refund_msg);
        submitTv = (TextView) findViewById(R.id.tv_submit);
        gridView = (GridView) findViewById(R.id.gv_imgs);

        localPaths = new ArrayList<>();
        localPaths.add("");

        adapter = new ApplyRefundImgsAdapter(this, localPaths);
        gridView.setAdapter(adapter);

        pickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (reasons != null) {
                    selectReasonTv.setText(reasons.get(options1).getTitle());
                }
            }
        }).setTitleText(getString(R.string.label_choose_refund_reason)).build();

        dialog = new BottomPhotoDialog(this, R.layout.view_bottom_popupwindow);

        ImageLoaderUtil.displayImage(orderGoods.getIcon(), ivGoodsIcon);
        switch (orderGoods.getType()) {
            case STOCK:
                ivGoodsType.setImageResource(R.mipmap.icon_goods);
                break;
            case FUTURES:
                ivGoodsType.setImageResource(R.mipmap.icon_order);
                break;
        }

        tvGoodsName.setText(orderGoods.getTitle());
        tvGoodsPrice.setText(this.getString(R.string.label_money) + NumberUtils.formatToDouble(orderGoods.getPrice()));
        tvGoodsStyle.setText(orderGoods.getStyle());
        tvGoodsCount.setText(this.getString(R.string.label_cheng) + " " + orderGoods.getPer_count());
        final double money = Double.parseDouble(orderGoods.getPrice()) * Integer.parseInt(orderGoods.getPer_count());
        tvHighMoney.setText(this.getString(R.string.label_money) + NumberUtils.formatToDouble("" + money));

        if (type == 1) {
            tvSelectType.setText("退款");
        } else if (type == 2) {
            tvSelectType.setText("退货退款");
        } else if (type==3){
            rlShow1.setVisibility(View.GONE);
            llShow2.setVisibility(View.GONE);
            tvSelectType.setText("换货");
        }

        edtSelectMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (Double.parseDouble(charSequence.toString()) > money) {
                        edtSelectMoney.setText("0");
                        ToastUtil.show(ApplyRefundActivity.this, "不能超过最高退款金额");
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initEvent() {
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        selectReasonTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyBoardUtil.hideKeyboard(ApplyRefundActivity.this, selectReasonTv);
                pickerView.show();
            }
        });
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectReasonTv.getText().toString().trim().length() == 0) {
                    ToastUtil.show(ApplyRefundActivity.this, R.string.label_choose_refund_reason);
                } else if (needPic && localPaths.size() == 1) {
                    ToastUtil.show(ApplyRefundActivity.this, R.string.label_choose_img);
                } else {
                    LoadingDialog.getInstance(ApplyRefundActivity.this).show();
                    imgUrls = new ArrayList<>();
                    //上传图片
                    temp.clear();
                    temp.addAll(localPaths);
                    if (localPaths.size() > 1) {
                        temp.remove(temp.size() - 1);
                        uploadImgs();
                    } else {
                        if (type == 1) {
                            orderRequester.applyRefund(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, edtSelectMoney.getText().toString(), "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
                        } else if (type == 2) {
                            orderRequester.applyRefundChange(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, edtSelectMoney.getText().toString(), "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
                        } else if (type == 3) {
                            orderRequester.applyRefundexchange(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, "", "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
                        }
                    }
                }
            }
        });
        adapter.setSelectImgCallback(new ApplyRefundImgsAdapter.SelectImgCallback() {
            @Override
            public void onSelect() {
                if (localPaths.size() > maxImgNum) {
                    ToastUtil.show(ApplyRefundActivity.this, String.format(getString(R.string.tip_max_img_num), maxImgNum + ""));
                } else {
                    dialog.show();
                }
            }

            @Override
            public void onDelete(int position) {
                localPaths.remove(position);
                adapter.notifyDataSetChanged();
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
    }

    private void initData() {
        requestReason();
    }

    private void requestReason() {
        orderRequester.getRefundOrderReason(this, getRefundOrderReasonListener);
    }

    private void uploadImgs() {
        if (temp.size() == 0) {
            if (type == 1) {
                orderRequester.applyRefund(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, edtSelectMoney.getText().toString(), "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
            } else if (type == 2) {
                orderRequester.applyRefundChange(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, edtSelectMoney.getText().toString(), "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
            } else if (type == 3) {
                orderRequester.applyRefundexchange(ApplyRefundActivity.this, orderId, selectReasonTv.getText().toString(), refundMsgTv.getText().toString(), imgUrls, type, "", "" + orderGoods.getGoodsCartId(), applyRefundOrderListener);
            }
        } else {
            String path = temp.get(temp.size() - 1);
            temp.remove(temp.size() - 1);
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

    private void toTakePhoto() {
        RxPermissions permissions = new RxPermissions(ApplyRefundActivity.this);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    captureManager = new ImageCaptureManager(ApplyRefundActivity.this);
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
        RxPermissions permissions = new RxPermissions(ApplyRefundActivity.this);
        permissions.request(Manifest.permission.CAMERA, Manifest.permission.VIBRATE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    //选择相册
                    PhotoPicker.builder()
                            .setPhotoCount(maxImgNum + 1 - localPaths.size())
                            .setShowCamera(true)
                            .setShowGif(true)
                            .setPreviewEnabled(false)
                            .start(ApplyRefundActivity.this, PhotoPicker.REQUEST_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        String imagePaht = captureManager.getCurrentPhotoPath();
                        localPaths.add(0, imagePaht);
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }

        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                for (String imagePath : photos) {
                    localPaths.add(0, imagePath);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}
