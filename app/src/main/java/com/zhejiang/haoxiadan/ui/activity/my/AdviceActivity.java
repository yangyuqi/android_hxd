package com.zhejiang.haoxiadan.ui.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiuweiyu on 2017/8/28.
 */

public class AdviceActivity extends BaseFragmentActivity {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.edt_advice)
    EditText edtAdvice;
    @BindView(R.id.iv_select_picture)
    ImageView ivSelectPicture;
    @BindView(R.id.tv_num)
    TextView tvNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice_layout);
        ButterKnife.bind(this);
        tvTitle.setText("意见反馈");
        tvRight.setText("提交");
        tvRight.setTextColor(getResources().getColor(R.color.main_red));
    }

    @OnClick({R.id.iv_left,R.id.tv_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_left :
                finish();
                break;
        }
    }
}
