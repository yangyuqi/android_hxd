package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.chosen.GetSystemTimeListener;
import com.zhejiang.haoxiadan.business.request.chosen.GoodsRequester;
import com.zhejiang.haoxiadan.business.request.chosen.TopInfoDetailsListener;
import com.zhejiang.haoxiadan.business.request.chosen.TopInfoListener;
import com.zhejiang.haoxiadan.business.request.hot.GiveUpListener;
import com.zhejiang.haoxiadan.business.request.hot.HotRequest;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoData;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoList;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.TopInfoListBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.H5Activity;
import com.zhejiang.haoxiadan.ui.activity.common.LoginActivity;
import com.zhejiang.haoxiadan.ui.activity.common.NewH5Activity;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/9/8.
 */

public class TopInfoListActivity extends BaseFragmentActivity {
    @BindView(R.id.fragment_hot_ls)
    ListView fragmentHotLs;

    List<TopInfoListBean> data = new ArrayList<>();
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_day)
    TextView tvDay;
    @BindView(R.id.tv_date)
    TextView tvDate;
    private String title;
    private TopInfoImp topInfoImp = new TopInfoImp();
    private GoodsRequester goodsRequester = new GoodsRequester();
    private HotRequest hotRequest = new HotRequest();
    private CommonAdapter<TopInfoListBean> adapter;
    private TopDetailsImp detailsImp = new TopDetailsImp();
    private GiveImp giveImp = new GiveImp();

    private class TopDetailsImp extends DefaultRequestListener implements TopInfoDetailsListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getData(TopInfoData data) {
            Intent intent = new Intent(context, H5Activity.class);
            intent.putExtra("title", title);
            intent.putExtra("content", data.getTopInfo());
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_info_list_layout);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        adapter = new CommonAdapter<TopInfoListBean>(context, data, R.layout.hot_fragment_ls_item) {
            @Override
            public void convert(ViewHolder helper, final TopInfoListBean item) {
//                if (helper.getPosition()== 0){
//                    helper.getView(R.id.hot_fragment_ls_item_tv).setVisibility(View.GONE);
//                    tvTitle.setText(item.getTitle());
//                    tvDate.setText(item.getDate());
//                }else {
                    helper.getView(R.id.hot_fragment_ls_item_tv).setVisibility(View.GONE);
                    helper.setText(R.id.hot_fragment_ls_item_tv, item.getDate());
//                }
                helper.setText(R.id.tv_hot_title, item.getTitle());
                helper.setText(R.id.tv_sub_title, item.getSubtitle());
                ImageLoaderUtil.displayImage(item.getCoverPath(),(ImageView) helper.getView(R.id.iv_hot));
                helper.setText(R.id.tv_num, String.valueOf(item.getGiveUp()));
                String[] strings = item.getDate().split("/");
                helper.setText(R.id.tv_date,strings[0]+"-"+strings[1]+"-"+strings[2]);
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        title = item.getTitle();
//                        goodsRequester.topInfoDetails(context, getAccessToken(), item.getTopId(), detailsImp);
                        Intent intent = new Intent(context, H5Activity.class);
                        intent.putExtra("title", title);
                        intent.putExtra("url","https://wap.haoxiadan.cn/headline/"+item.getTopId()+"?app=app");
                        startActivity(intent);
                    }
                });

                final CheckBox cb = helper.getView(R.id.goods_details_layout_cb);
                if (item.getType() != null) {
                    if (item.getType().equals("1")) {
                        cb.setChecked(true);
                    } else {
                        cb.setChecked(false);
                    }
                }
                    cb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!getAccessToken().equals("")) {
                                hotRequest.giveUpTop(context, getAccessToken(), item.getTopId(), giveImp);
                            }else {
                                cb.setChecked(false);
                                startActivity(new Intent(context,LoginActivity.class));
                            }
                        }
                    });

//                if (helper.getPosition()==0){
//                    helper.getView(R.id.hot_fragment_ls_item_tv).setVisibility(View.GONE);
//                }else {
//                    helper.getView(R.id.hot_fragment_ls_item_tv).setVisibility(View.VISIBLE);
//                }
            }
        };
        fragmentHotLs.setAdapter(adapter);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        goodsRequester.topInfo(context, getAccessToken(), topInfoImp);
        goodsRequester.getSystemTime(context,systemImp);
    }

    private class TopInfoImp extends DefaultRequestListener implements TopInfoListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getTopInfo(TopInfoList list) {
            data.clear();
            if (list.getTopInfoList().size() > 0) {
                data.addAll(list.getTopInfoList());
                adapter.setData(data);
                adapter.notifyDataSetChanged();

//                tvTitle.setText(data.get(0).getTitle());
//                tvDate.setText(data.get(0).getDate());
//                String[] strings = data.get(0).getDate().split("/");
//                String new_day = "20"+strings[0]+"-"+strings[1]+"-"+strings[2];
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Date datet = null ;
//                try {
//                    datet = dateFormat.parse(new_day);
//                    tvDay.setText(TimeUtils.getWeekOfDate(datet));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    private class GiveImp extends DefaultRequestListener implements GiveUpListener {

        @Override
        public void onSuccess() {
            goodsRequester.topInfo(context, getAccessToken(), topInfoImp);
        }

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onBusinessFail(String statusCode, String errorMsg) {
            super.onBusinessFail(statusCode, errorMsg);
            ToastUtil.show(context, errorMsg);
        }
    }
    public GetSystemImp systemImp = new GetSystemImp();
    public class GetSystemImp extends DefaultRequestListener implements GetSystemTimeListener{

        @Override
        public void ongetTime(String data) {
            if (data!=null){
                try {
                    JSONObject object = new JSONObject(data);
                    long ltime = object.getLong("now")*1000;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String string = dateFormat.format(new Date(Long.parseLong(String.valueOf(ltime))));
                    String[] strings = string.split("-") ;
                    String year = strings[0].substring(2);
                    tvDate.setText(year+"/"+strings[1]+"/"+strings[2]);
                    Date datet = null;
                    try {
                        datet = dateFormat.parse(string);
                        tvDay.setText(TimeUtils.getWeekOfDate(datet));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onRequestFail() {

        }
    }
}
