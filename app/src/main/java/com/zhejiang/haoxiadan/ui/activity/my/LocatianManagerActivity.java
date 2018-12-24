package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ChangeDefaultListener;
import com.zhejiang.haoxiadan.business.request.my.DeleteCurrentAddressListener;
import com.zhejiang.haoxiadan.business.request.my.GetAllAddressListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.model.common.Address;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class LocatianManagerActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.location_manager_ls)
    ListView locationManagerLs;
    @BindView(R.id.location_manager_tv_add)
    TextView locationManagerTvAdd;
    @BindView(R.id.rl_rl)
    RelativeLayout locationManagerTvAddRl;

    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.title_icon)
    ImageView titleIcon;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.rl_has_data)
    LinearLayout rlHasData;

    private int currentPage = 1;
    private int showCount = 30;

    private List<CurrentUserAddressBean> data = new ArrayList<>();
    private CommonAdapter<CurrentUserAddressBean> adapter;

    private DeleteAddressImp deleteAddressImp = new DeleteAddressImp();
    private UserRequester requester = new UserRequester();
    private GetAddressImp imp = new GetAddressImp();
    private ChangeAddressDefaultImp defaultImp = new ChangeAddressDefaultImp();

    private Boolean type;

    private class GetAddressImp extends DefaultRequestListener implements GetAllAddressListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getUserAddress(List<CurrentUserAddressBean> beanList) {
            sv.onFinishFreshAndLoad();
            if (beanList.size() > 0) {
                rlNoData.setVisibility(View.GONE);
                rlHasData.setVisibility(View.VISIBLE);
                data.clear();
                data.addAll(beanList);
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            } else {
                rlHasData.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_manager_layout);
        ButterKnife.bind(this);
        tvTitle.setText("管理地址");

        type = getIntent().getBooleanExtra("type", false);

        adapter = new CommonAdapter<CurrentUserAddressBean>(context, data, R.layout.location_manage_ls_item) {
            @Override
            public void convert(ViewHolder helper, final CurrentUserAddressBean item) {
                helper.setText(R.id.tv_phone, item.getMobile());
                helper.setText(R.id.tv_address, item.getProvinceName() + item.getCityName() + item.getAreaName() + item.getArea_info());
                helper.setText(R.id.tv_name, item.getTrueName());
                ImageView cb = helper.getView(R.id.cart_rb_q);
                if (item.getDefault_val() == 1) {
                    cb.setImageResource(R.mipmap.btn_address_choose_hl);
                } else {
                    cb.setImageResource(R.mipmap.btn_address_choose);
                }
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type) {
                            Address address = new Address();
                            address.setId(item.getId() + "");
                            address.setName(item.getTrueName());
                            address.setMobile(item.getMobile());
                            address.setArea(item.getProvinceName() + item.getCityName() + item.getAreaName());
                            address.setDetailAddress(item.getArea_info());
                            Intent mIntent = new Intent();
                            mIntent.putExtra("address", address);
                            setResult(1, mIntent);
                            finish();
                        }
                    }
                });
                helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, AddAndEditLocationActivity.class);
                        intent.putExtra("flag", "2");
                        intent.putExtra("details", item);
                        startActivity(intent);
                    }
                });

                helper.getView(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DeleteDialog deleteDialog = new DeleteDialog(context, "提示", "是否删除此地址", "确定");
                        deleteDialog.show();
                        deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                            @Override
                            public void isDelete(boolean isdelete) {
                                requester.deleteCurrentAddress(context, String.valueOf(item.getId()), getAccessToken(), deleteAddressImp);
                                deleteDialog.dismiss();
                            }
                        });
                    }
                });

                cb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requester.changeDefaultAddress(context, getAccessToken(), String.valueOf(item.getId()), defaultImp);

                    }
                });


            }
        };

        locationManagerLs.setAdapter(adapter);

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                requester.getAllAddress(context, getAccessToken(), currentPage, showCount, imp);
            }

            @Override
            public void onLoadmore() {
                currentPage++;
                requester.getAllAddress(context, getAccessToken(), currentPage, showCount, imp);
            }
        });
    }

    @OnClick({R.id.iv_left, R.id.rl_rl})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.rl_rl:
                Intent intent = new Intent(context, AddAndEditLocationActivity.class);
                intent.putExtra("flag", "1");
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requester.getAllAddress(context, getAccessToken(), currentPage, showCount, imp);
    }

    private class DeleteAddressImp extends DefaultRequestListener implements DeleteCurrentAddressListener {

        @Override
        public void onSuccess() {
            requester.getAllAddress(context, getAccessToken(), currentPage, showCount, imp);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    private class ChangeAddressDefaultImp extends DefaultRequestListener implements ChangeDefaultListener {

        @Override
        public void onSuccess() {
            requester.getAllAddress(context, getAccessToken(), currentPage, showCount, imp);
        }

        @Override
        protected void onRequestFail() {

        }
    }
}
