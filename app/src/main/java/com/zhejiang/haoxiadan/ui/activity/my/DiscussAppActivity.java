package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.ArticleListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.common.H5Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class DiscussAppActivity extends BaseFragmentActivity {

    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_discuss_pay)
    RelativeLayout rlDiscussPay;
    @BindView(R.id.rl_discuss_size)
    RelativeLayout rlDiscussSize;
    @BindView(R.id.rl_discuss_ticket)
    RelativeLayout rlDiscussTicket;
    @BindView(R.id.rl_discuss_issue)
    RelativeLayout rlDiscussIssue;
    @BindView(R.id.rl_discuss_advice)
    RelativeLayout rlDiscussAdvice;
    @BindView(R.id.rl_discuss_refund_style)
    RelativeLayout rlDiscussRefundStyle;
    @BindView(R.id.rl_discuss_path)
    RelativeLayout rlDiscussPath;
    @BindView(R.id.rl_discuss_policy)
    RelativeLayout rlDiscussPolicy;
    @BindView(R.id.rl_discuss_goods)
    RelativeLayout rlDiscussGoods;
    @BindView(R.id.rl_discuss_express)
    RelativeLayout rlDiscussExpress;
    @BindView(R.id.rl_discuss_range)
    RelativeLayout rlDiscussRange;
    @BindView(R.id.rl_discuss_time)
    RelativeLayout rlDiscussTime;

    private String title = null;
    private UserRequester requester = new UserRequester();
    private ArtImp artImp = new ArtImp();
    private class ArtImp extends DefaultRequestListener implements ArticleListener{

        @Override
        public void onArticleSuccess(String str) {
            Intent intent = new Intent(context, H5Activity.class);
            intent.putExtra("title",title);
            intent.putExtra("content",str);
            startActivity(intent);
        }

        @Override
        protected void onRequestFail() {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discuss_app_layout);
        ButterKnife.bind(this);
        tvTitle.setText("购物咨询");

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick({R.id.iv_left,R.id.rl_discuss_pay,R.id.rl_discuss_size,R.id.rl_discuss_ticket,R.id.rl_discuss_issue,R.id.rl_discuss_advice,R.id.rl_discuss_refund_style,R.id.rl_discuss_path,R.id.rl_discuss_policy,R.id.rl_discuss_goods,R.id.rl_discuss_express,R.id.rl_discuss_range,R.id.rl_discuss_time})
    public void OnClick(View view){
        switch (view.getId()){

            case R.id.rl_discuss_pay :
                title = "付款方式";
                break;
            case R.id.rl_discuss_size :
                title = "尺码选择";
                break;
            case R.id.rl_discuss_ticket :
                title = "发票问题";
                break;
            case R.id.rl_discuss_issue :
                title = "商品咨询";
                break;
            case R.id.rl_discuss_advice :
                title = "投诉与建议";
                break;
            case R.id.rl_discuss_refund_style :
                title = "退款方式与时效";
                break;
            case R.id.rl_discuss_path :
                title = "退换货流程";
                break;
            case R.id.rl_discuss_policy :
                title = "退换货政策";
                break;
            case R.id.rl_discuss_goods :
                title = "验货和签收";
                break;
            case R.id.rl_discuss_express :
                title = "顺丰速递";
                break;
            case R.id.rl_discuss_range :
                title = "配送范围";
                break;
            case R.id.rl_discuss_time :
                title = "配送时间";
                break;
        }
        requester.article(context,title,artImp);
    }
}
