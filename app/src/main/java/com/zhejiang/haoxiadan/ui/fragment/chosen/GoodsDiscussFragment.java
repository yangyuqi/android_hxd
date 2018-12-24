package com.zhejiang.haoxiadan.ui.fragment.chosen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liaoinstan.springview.widget.SpringView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetDiscussListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.model.requestData.in.PostGoodsPhotoBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.DiscussContentDataBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.PreviewPictureActivity;
import com.zhejiang.haoxiadan.ui.fragment.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by qiuweiyu on 2017/8/29.
 */

public class GoodsDiscussFragment extends BaseFragment {

    @BindView(R.id.goods_details_discuss_ls)
    ListView goodsDetailsDiscussLs;
    Unbinder unbinder;
    @BindView(R.id.tv_get_score)
    TextView tvGetScore;
    @BindView(R.id.sv)
    SpringView sv;
    @BindView(R.id.ll_has_data)
    LinearLayout llHasData;
    @BindView(R.id.rl_no_data)
    RelativeLayout rlNoData;
    private View view;

    private List<DiscussContentDataBean> data = new ArrayList<>();
    private CommonAdapter<DiscussContentDataBean> adapter;

    private String goodsId;
    private GoodsRequester requester = new GoodsRequester();

    PostGoodsPhotoBean goodsPhotoBean;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.goods_details_discuss_fragment_layout, null);
        unbinder = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            goodsId = bundle.getString("id");
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDat();

        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                sv.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                sv.onFinishFreshAndLoad();
            }
        });
    }

    private void initDat() {
        requester.getDiscuss(context, goodsId, 1, 30, getDiscussImp);
        data.clear();

        adapter = new CommonAdapter<DiscussContentDataBean>(context, data, R.layout.discuss_ls_item) {
            @Override
            public void convert(ViewHolder helper, DiscussContentDataBean item) {
                helper.setText(R.id.tv_name, item.getUserName());
                ImageLoaderUtil.displayImage(item.getUserHeadImg(),(ImageView) helper.getView(R.id.user_civ));
                helper.setText(R.id.tv_content, item.getEvaluateInfo());
                final GridView gv = helper.getView(R.id.no_gv);
                RatingBar ratingBar = helper.getView(R.id.ratingbar_one);
                ratingBar.setRating(item.getDescriptionEvaluate());
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Long time = item.getAddTtime();
                String d = format.format(time);
                helper.setText(R.id.tv_time, d);
                final ArrayList<String> stringArrayList = item.getEvaluatePhotos();
                CommonAdapter<String> m_adapter = new CommonAdapter<String>(context, stringArrayList, R.layout.image_gv_item) {
                    @Override
                    public void convert(final ViewHolder helper, final String item) {
                        ImageView imageView = helper.getView(R.id.iv_icon);
                        ImageLoaderUtil.displayImage(item.replaceAll(" ", ""),imageView);

                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                goodsPhotoBean = new PostGoodsPhotoBean();
//                                goodsPhotoBean.setPositopn(helper.getPosition());
//                                goodsPhotoBean.setNormal_lsit(stringArrayList);
//                                Intent intent = new Intent(context, PreviewPictureActivity.class);
//                                intent.putExtra("goodsPhotoBean", goodsPhotoBean);
//                                startActivity(intent);
                            }
                        });
                    }
                };
                gv.setAdapter(m_adapter);
            }
        };

        goodsDetailsDiscussLs.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private GetDiscussImp getDiscussImp = new GetDiscussImp();

    private class GetDiscussImp extends DefaultRequestListener implements GetDiscussListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getDiscuss(DiscussContentData contentData) {
            tvGetScore.setText(String.valueOf(contentData.getGoodPercent()) + " 分");
            if (contentData.getEvaluateList().size()>0) {
                llHasData.setVisibility(View.VISIBLE);
                rlNoData.setVisibility(View.GONE);
                data.clear();
                data.addAll(contentData.getEvaluateList());
                adapter.setData(data);
                adapter.notifyDataSetChanged();
            }else {
                llHasData.setVisibility(View.GONE);
                rlNoData.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
//        if (goodsPhotoBean != null) {
//            EventBus.getDefault().post(goodsPhotoBean);
//        }
    }
}
