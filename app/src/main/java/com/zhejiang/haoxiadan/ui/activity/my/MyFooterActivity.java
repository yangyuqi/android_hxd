package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
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
import com.zhejiang.haoxiadan.business.request.my.DeleteAllFooterListener;
import com.zhejiang.haoxiadan.business.request.my.PersonFootListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.out.GoodsInfoBean;
import com.zhejiang.haoxiadan.model.requestData.out.PersonFooter;
import com.zhejiang.haoxiadan.model.requestData.out.PersonFooterDetails;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchResultGoodsActivity;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialog;
import com.zhejiang.haoxiadan.ui.dialog.DeleteDialogInterface;
import com.zhejiang.haoxiadan.ui.dialog.GlobalTitleMorePopupWindow;
import com.zhejiang.haoxiadan.util.PublicUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class MyFooterActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_right_second)
    ImageView ivRightSecond;
    @BindView(R.id.my_foot_goods_ls)
    ListView myFootGoodsLs;

    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;
    @BindView(R.id.sv)
    SpringView sv;

    private int allCount;
    DecimalFormat df = new DecimalFormat("######0.00");
    private int currentPage = 1;
    private int showCount = 30;
    private int count;

    OpenFlightAloneBean bean ;

    private GlobalTitleMorePopupWindow popupDialog;


    private List<Integer> Ids = new ArrayList<>();

    private UserRequester requester = new UserRequester();
    private MyFooterImp imp = new MyFooterImp();

    private class MyFooterImp extends DefaultRequestListener implements PersonFootListener {

        @Override
        public void getFooter(PersonFooter footerData) {
            sv.onFinishFreshAndLoad();
            Ids.clear();
            if (currentPage<=footerData.getTotalPageCount()){
                if (footerData.getFootPointList().size() > 0) {
                    llHasData.setVisibility(View.VISIBLE);
                    rlNoData.setVisibility(View.GONE);
                    if (currentPage==1) {
                        data.clear();
                    }
                    data.addAll(footerData.getFootPointList());
                    adapter.setData(data);
                    adapter.notifyDataSetChanged();
                    for (int i = 0 ;i<data.size();i++){
                        for (int j = 0;j<data.get(i).getFtList().size();j++){
                            Ids.add(data.get(i).getFtList().get(j).getId());
                        }
                    }
                } else {
                    llHasData.setVisibility(View.GONE);
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }else {
                if (currentPage>footerData.getTotalPageCount()&&data.size()<=0){
                    llHasData.setVisibility(View.GONE);
                    rlNoData.setVisibility(View.VISIBLE);
                }
            }

        }

        @Override
        protected void onRequestFail() {
        }
    }

    private List<PersonFooterDetails> data = new ArrayList<>();
    private CommonAdapter<PersonFooterDetails> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_footer_layout);
        ButterKnife.bind(this);

        initView();
        popupDialog = new GlobalTitleMorePopupWindow(context);
        initEvent();
    }

    private void initEvent() {
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1 ;
                requester.getMyFooter(context, getAccessToken(), currentPage, showCount, imp);
            }

            @Override
            public void onLoadmore() {
                 currentPage++;
                requester.getMyFooter(context, getAccessToken(), currentPage, showCount, imp);
            }
        });
    }

    private void initView() {
        tvTitle.setText("我的足迹");
        ivRight.setImageResource(R.drawable.btn_more2);
        ivRightSecond.setImageResource(R.drawable.btn_trash);

        adapter = new CommonAdapter<PersonFooterDetails>(context, data, R.layout.goods_gv_item) {
            @Override
            public void convert(ViewHolder helper, PersonFooterDetails item) {
                helper.setText(R.id.tv_time, item.getData());
                GridView gridView = helper.getView(R.id.no_gv);
                count = item.getCount();
                CommonAdapter<GoodsInfoBean> com_adapter = new CommonAdapter<GoodsInfoBean>(context, item.getFtList(), R.layout.collections_goods_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final GoodsInfoBean item) {
                        helper.setText(R.id.tv_title, item.getGoodsName() +"  "+item.getGoodsSerial());
                        ImageLoaderUtil.displayImage(item.getPath(),(ImageView) helper.getView(R.id.iv_icon));
                        helper.setText(R.id.tv_price, "￥" + item.getStorePrice());
                        helper.setText(R.id.tv_num, "已售" + item.getGoodsSalenum() + "件");

                        helper.getView(R.id.rl_rltop).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(context, GoodsDetailsActivity.class);
                                intent.putExtra("goodsId", String.valueOf(item.getGoodsId()));
                                bean = new OpenFlightAloneBean("close");
                                startActivity(intent);
                    }
                });

                        if (item.getGoodsNumType()!=null) {
                            if (item.getGoodsNumType().equals("0")) {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                            } else {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                            }
                        }

                        helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //TODO 去商品搜索结果页
                                Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                                intent.putExtra("gcilThird", item.getGcId());
                                intent.putExtra("keyWord", item.getGoodsName());
                                intent.putExtra("type", "catefortType");
                                intent.putExtra("searchType", "goods");
                                mContext.startActivity(intent);
                            }
                        });

                    }

                };
                gridView.setAdapter(com_adapter);

            }
        };
        myFootGoodsLs.setAdapter(adapter);

    }

    @OnClick({R.id.iv_left, R.id.iv_right, R.id.iv_right_second})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.iv_right_second:
                DeleteDialog deleteDialog = new DeleteDialog(context, "提示", "是否清除足迹", "确定");
                deleteDialog.show();
                deleteDialog.OnDeleteBtn(new DeleteDialogInterface() {
                    @Override
                    public void isDelete(boolean isdelete) {
                        if (isdelete) {
                            requester.deleteAllFoot(context, getAccessToken(), Ids, deleteImp);
                        }
                    }
                });
                break;

            case R.id.iv_right:
                popupDialog.showAsDropDown(ivRight);
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        requester.getMyFooter(context, getAccessToken(), currentPage, showCount, imp);
    }

    @Override
    protected void onStop() {
        super.onStop();
        data.clear();
        if (bean != null) {
            EventBus.getDefault().post(bean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    private DeleteImp deleteImp = new DeleteImp();

    private class DeleteImp extends DefaultRequestListener implements DeleteAllFooterListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onSuccess() {
            data.clear();
            requester.getMyFooter(context, getAccessToken(), currentPage, showCount, imp);
        }
    }
}
