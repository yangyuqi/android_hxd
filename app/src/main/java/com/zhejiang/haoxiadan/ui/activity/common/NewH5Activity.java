package com.zhejiang.haoxiadan.ui.activity.common;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.dialog.LoadingDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/11/30.
 */

public class NewH5Activity extends BaseFragmentActivity {

    WebView lsDetails;
    ImageView ivLeft;
    TextView tvTitle;

    private String url;
    private String title ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_h5_activity);

        lsDetails = (WebView) findViewById(R.id.ls_details);
        ivLeft = (ImageView) findViewById(R.id.iv_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");


        WebSettings webSettings = lsDetails.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        lsDetails.setWebChromeClient(new WebChromeClient());
        lsDetails.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);

                return true;

            }
        });
        lsDetails.loadUrl(url);

        Log.e("ssssssssss",url);
//        new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Document doc = null;
//                    try {
//                        doc = Jsoup.connect("https://www.baidu.com/").get();
//                        final String text = doc.html();
//                        Log.e("ssssssssss","https://www.baidu.com/");
//                        Log.e("sssssss",text);
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                lsDetails.getSettings().setJavaScriptEnabled(true);
//                                lsDetails.loadDataWithBaseURL(null,getNewContent(text),"text/html","utf-8",null);
//                            }
//                        });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }).start();


        tvTitle.setText(title);

        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        }catch (Exception e){
            return "";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lsDetails.destroy();
    }
}
