package com.zhejiang.haoxiadan.ui.activity.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.GetFinanceTextListener;
import com.zhejiang.haoxiadan.business.request.my.OrderRequester;
import com.zhejiang.haoxiadan.db.SharedPreferencesUtil;
import com.zhejiang.haoxiadan.model.requestData.out.my.FinanceTextData;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;
import com.zhejiang.haoxiadan.util.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/9/22.
 */

public class NeedReadFinanceTextActivity extends BaseFragmentActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ls_details)
    WebView lsDetails;
    @BindView(R.id.tv_disagree)
    TextView tvDisagree;
    @BindView(R.id.tv_agree)
    TextView tvAgree;
    private OrderRequester requester = new OrderRequester();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.need_read_finance_layout);
        ButterKnife.bind(this);
        String text = (String) SharedPreferencesUtil.get(context,Constants.finance_text,"");
        if (text.equals("")) {
            LoadingDialog.getInstance(context).show();
            requester.getFinanceText(context, financeImp);
        }else {
            FinanceTextData financeTextData = gson.fromJson(text,FinanceTextData.class);
            tvTitle.setText(financeTextData.getTitle());
            lsDetails.loadDataWithBaseURL(null, getNewContent(financeTextData.getContent()), "text/html", "utf-8", null);
        }
    }

    private GetFinanceImp financeImp = new GetFinanceImp();

    private class GetFinanceImp extends DefaultRequestListener implements GetFinanceTextListener {

        @Override
        protected void onRequestFail() {
            LoadingDialog.dismissDialog();
        }

        @Override
        public void getData(FinanceTextData financeTextData) {

            if (financeTextData!=null) {
                LoadingDialog.dismissDialog();
                if (financeTextData.getTitle() != null) {
                    tvTitle.setText(financeTextData.getTitle());
                }
                if (financeTextData.getContent() != null) {
                    lsDetails.loadDataWithBaseURL(null, getNewContent(financeTextData.getContent()), "text/html", "utf-8", null);
                }
                SharedPreferencesUtil.put(context,Constants.finance_text,gson.toJson(financeTextData));
            }
        }
    }

    private String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return "";
        }
    }

    @OnClick({R.id.tv_disagree,R.id.tv_agree})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_disagree :
                finish();
                break;
            case R.id.tv_agree :
                Intent intent = new Intent();
                setResult(1);
                finish();
                break;
        }
    }
}

