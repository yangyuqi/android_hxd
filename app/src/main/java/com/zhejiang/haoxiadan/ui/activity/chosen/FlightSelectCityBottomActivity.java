package com.zhejiang.haoxiadan.ui.activity.chosen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.CommonAdapter;
import com.zhejiang.haoxiadan.adapter.ViewHolder;
import com.zhejiang.haoxiadan.business.DefaultRequestListener;
import com.zhejiang.haoxiadan.business.request.my.GetAllAddressListener;
import com.zhejiang.haoxiadan.business.request.my.UserRequester;
import com.zhejiang.haoxiadan.model.requestData.out.my.CurrentUserAddressBean;
import com.zhejiang.haoxiadan.ui.activity.BaseFragmentActivity;
import com.zhejiang.haoxiadan.ui.activity.my.AddAndEditLocationActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by qiuweiyu on 2017/11/23.
 */

public class FlightSelectCityBottomActivity extends BaseFragmentActivity {

    @BindView(R.id.rule_ls)
    ListView ruleLs;
    @BindView(R.id.tv_finish)
    TextView tvFinish;

    private String cityIdName ;

    List<Boolean> list_data = new ArrayList<>();
    private UserRequester requester = new UserRequester();
    private List<CurrentUserAddressBean> list = new ArrayList<>();
    private CommonAdapter<CurrentUserAddressBean> adapter ;
    private GetAddressImp imp ;

    private String cityId ;
    private String cityName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_select_city_layout);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this);

        cityIdName = getIntent().getStringExtra("save");

        adapter = new CommonAdapter<CurrentUserAddressBean>(context,list,R.layout.flight_city_ls_item) {
            @Override
            public void convert(ViewHolder helper, CurrentUserAddressBean item) {
                helper.setText(R.id.tv_area,item.getProvinceName() + item.getCityName() + item.getAreaName()+item.getArea_info());
                ((ImageView)helper.getView(R.id.iv_icon)).setVisibility(View.GONE);
                ((ImageView)helper.getView(R.id.iv_arrow)).setImageResource(R.mipmap.icon_address_grey);
                ((TextView)helper.getView(R.id.tv_area)).setTextColor(getResources().getColor(R.color.text_gray));

                if (String.valueOf(item.getId()).equals(cityIdName)){
                    ((TextView)helper.getView(R.id.tv_area)).setTextColor(getResources().getColor(R.color.main_red));
                    ((ImageView)helper.getView(R.id.iv_arrow)).setImageResource(R.mipmap.icon_address_red);
                    ((ImageView)helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
                }

//                if (list_data.size()>0){
//                    if (list_data.get(helper.getPosition())){
//                        ((TextView)helper.getView(R.id.tv_area)).setTextColor(getResources().getColor(R.color.main_red));
//                        ((ImageView)helper.getView(R.id.iv_arrow)).setImageResource(R.mipmap.icon_address_red);
//                        ((ImageView)helper.getView(R.id.iv_icon)).setVisibility(View.VISIBLE);
//                    }
//                }
            }
        };

        ruleLs.setAdapter(adapter);

        imp = new GetAddressImp();


        tvFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAndEditLocationActivity.class);
                intent.putExtra("flag", "1");
                startActivity(intent);
            }
        });

        ruleLs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapter.getCount()>0){
//                    list_data  = new ArrayList<Boolean>();
//                    for (int j =0 ; j<adapter.getCount();j++){
//                        list_data.add(false);
//                    }
//                    list_data.set(i,true);
//                    adapter.notifyDataSetChanged();
//                }
                cityId = list.get(i).getCityId();
                cityName = list.get(i).getCityName();

                Intent intent = new Intent();
                intent.putExtra("result",cityId);
                intent.putExtra("cityName",cityName);
                intent.putExtra("save",String.valueOf(list.get(i).getId()));
                intent.putExtra("procivn",list.get(i).getProvinceName());
                setResult(0x13,intent);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        requester.getAllAddress(context, getAccessToken(), 1, 30, imp);
    }

    private class GetAddressImp extends DefaultRequestListener implements GetAllAddressListener {

        @Override
        protected void onRequestFail() {

        }

        @Override
        public void getUserAddress(List<CurrentUserAddressBean> beanList) {
            if (beanList.size() > 0) {
                list = beanList;
                adapter.setData(list);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
