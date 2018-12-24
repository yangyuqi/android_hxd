package com.zhejiang.haoxiadan.ui.activity.cart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.pay.PayRequester;
import com.zhejiang.haoxiadan.business.request.pay.QueryBankListListener;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.ui.activity.BaseActivity;
import com.zhejiang.haoxiadan.ui.view.CommonTitle;
import com.zhejiang.haoxiadan.ui.view.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class AddBankCardSelectActivity extends BaseActivity {

    private CommonTitle commonTitle;
    private TextView cardTypeTv;
    private TextView bankTv;
    private TextView nextTv;

    private BankCard.CARD_TYPE type;
    private String bankNo;
    private String bankName;

    private OptionsPickerView pickerView ;
    private List<BankCard> bankCards;
    private List<String> types;

    private PayRequester payRequester;
    private QueryBankListListenerImpl queryBankListListener;
    private class QueryBankListListenerImpl extends DefaultRequestListener implements QueryBankListListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void onQueryBankListSuccess(List<BankCard> bankCardList) {

            bankCards = bankCardList;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_card_select);

        payRequester = new PayRequester();
        queryBankListListener = new QueryBankListListenerImpl();

        addActivity(this);

        initView();
        initEvent();
        initData();
    }
    private void initView(){
        commonTitle = (CommonTitle)findViewById(R.id.common_title);
        cardTypeTv = (TextView)findViewById(R.id.tv_card_type);
        bankTv = (TextView)findViewById(R.id.tv_select_bank);
        nextTv = (TextView)findViewById(R.id.tv_next);
    }

    private void initEvent(){
        commonTitle.setOnTitleClickListener(new CommonTitle.OnTitleClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {

            }
        });
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == null || bankNo == null || bankName == null){
                    ToastUtil.show(AddBankCardSelectActivity.this,R.string.tip_please_complete_info);
                    return;
                }
                Intent intent = new Intent(AddBankCardSelectActivity.this,AddBankCardActivity.class);
                intent.putExtra("type",type);
                intent.putExtra("bankNo",bankNo);
                intent.putExtra("bankName",bankName);
                startActivity(intent);
            }
        });
        cardTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView = new OptionsPickerView.Builder(AddBankCardSelectActivity.this, new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if(types != null){
                            cardTypeTv.setText(types.get(options1));
                            switch (options1){
                                case 0:
                                    type = BankCard.CARD_TYPE.COMMON;
                                    break;
                                case 1:
                                    type = BankCard.CARD_TYPE.CREDIT;
                                    break;

                            }
                        }
                    }
                }).setTitleText(getString(R.string.label_please_select)).build();
                pickerView.setPicker(types);
                pickerView.show();
            }
        });
        bankTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView = new OptionsPickerView.Builder(AddBankCardSelectActivity.this, new OptionsPickerView.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        if(bankCards != null){
                            bankTv.setText(bankCards.get(options1).getBankName());
                            bankNo = bankCards.get(options1).getBankNo();
                            bankName = bankCards.get(options1).getBankName();
                        }
                    }
                }).setTitleText(getString(R.string.label_please_select)).build();
                pickerView.setPicker(bankCards);
                pickerView.show();
            }
        });
    }

    private void initData(){
        types = new ArrayList<>();
        types.add(getString(R.string.label_card_jieji));
        types.add(getString(R.string.label_card_daiji));
        payRequester.queryBankList(AddBankCardSelectActivity.this,queryBankListListener);
    }



}
