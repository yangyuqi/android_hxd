package com.zhejiang.haoxiadan.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.model.common.BankCard;
import com.zhejiang.haoxiadan.ui.activity.cart.AddBankCardSelectActivity;
import com.zhejiang.haoxiadan.ui.adapter.my.BankcardListAdapter;

import java.util.List;

/**
 * 选择商品规格的弹出框
 * Created by KK on 2017/3/23.
 */

public class SelectBankCardDialog extends Dialog implements View.OnClickListener{

    private TextView cancelTv;
    private ListView bankcardLV;
    private RelativeLayout bottomRl;

    private BankcardListAdapter adapter;
    private List<BankCard> bankCards;
    private BankCard curBankCard;

    private SelectBankCardCallback selectBankCardCallback;

    public SelectBankCardDialog(Context context, List<BankCard> bankCards) {
        super(context, R.style.bottom_dialog);
        setContentView(R.layout.view_select_bankcard_layout);
        this.bankCards = bankCards;


        cancelTv = (TextView)findViewById(R.id.tv_cancel);
        bankcardLV = (ListView)findViewById(R.id.lv_bankcard);
        bottomRl = (RelativeLayout)findViewById(R.id.rl_foot);

        adapter = new BankcardListAdapter(getContext(),this.bankCards);
        bankcardLV.setAdapter(adapter);
        bankcardLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                curBankCard = SelectBankCardDialog.this.bankCards.get(position);
                adapter.setCurBankCard(curBankCard);
                dismiss();
            }
        });
        cancelTv.setOnClickListener(this);
        bottomRl.setOnClickListener(this);
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity= Gravity.BOTTOM;
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height= WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
//        getWindow().getDecorView().setPadding(0, 0, 0, getContext().getResources().getDimensionPixelSize(R.dimen.btn_height_40));
        getWindow().setAttributes(layoutParams);

    }

    public void show(BankCard bankCard){
        curBankCard = bankCard;
        adapter.setCurBankCard(curBankCard);
        show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if(selectBankCardCallback !=null){
            selectBankCardCallback.endSelect(curBankCard);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.rl_foot:
                Intent intent = new Intent(getContext(),AddBankCardSelectActivity.class);
                getContext().startActivity(intent);
                dismiss();
                break;
        }
    }

    public interface SelectBankCardCallback {
        void endSelect(BankCard bankCard);
    }

    public void setSelectBankCardCallback(SelectBankCardCallback selectBankCardCallback) {
        this.selectBankCardCallback = selectBankCardCallback;
    }


}
