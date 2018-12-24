package com.zhejiang.haoxiadan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhejiang.haoxiadan.R;
import com.zhejiang.haoxiadan.adapter.TypeView.lIstViewItem;
import com.zhejiang.haoxiadan.model.choseModel.ApphotitemsBeanModel;
import com.zhejiang.haoxiadan.model.choseModel.HotCategoryListData;
import com.zhejiang.haoxiadan.model.requestData.in.OpenFlightAloneBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.CategoryListBean;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.ChoseFloorStyle;
import com.zhejiang.haoxiadan.model.requestData.out.Chose.StoreListBean;
import com.zhejiang.haoxiadan.model.requestData.out.HotCategoryBean;
import com.zhejiang.haoxiadan.model.requestData.out.NewChose.NewFloorsItem;
import com.zhejiang.haoxiadan.model.requestData.out.WideGoodsList;
import com.zhejiang.haoxiadan.model.requestData.out.WideGoodsListBean;
import com.zhejiang.haoxiadan.third.imageload.ImageLoaderUtil;
import com.zhejiang.haoxiadan.ui.activity.chosen.ChannelInfoActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsDetailsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.GoodsListActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchFactoryActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchHistortActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.SearchResultGoodsActivity;
import com.zhejiang.haoxiadan.ui.activity.chosen.ShopActivity;
import com.zhejiang.haoxiadan.ui.view.NoScrollGridView;
import com.zhejiang.haoxiadan.ui.view.NoScrollListView;
import com.zhejiang.haoxiadan.util.NumberUtils;
import com.zhejiang.haoxiadan.util.PublicUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuweiyu on 2017/10/8.
 */

public class ChoseAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;
    private Context context;
    private ArrayList<lIstViewItem> listDatas;
    OpenFlightAloneBean bean;
    private Gson gson ;
    GetBeanDataInterface beanDataInterface ;

    public ChoseAdapter(Context context, ArrayList<lIstViewItem> listDatas , GetBeanDataInterface dataInterface ) {
        this.context = context;
        this.listDatas = listDatas;
        gson = new Gson();
        beanDataInterface = dataInterface ;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return listDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        lIstViewItem listItem = listDatas.get(position);
        int Type = getItemViewType(position);
        switch (Type){
            case 0 :
                NewFloorsItem newFloorsItem = (NewFloorsItem) listItem.map.get("0");
                final HotCategoryBean hotCategoryBean = gson.fromJson(gson.toJson(newFloorsItem.getWideGoodsList()),HotCategoryBean.class);
                convertView = mLayoutInflater.inflate(R.layout.view_holder_frist_type_layout,null);
                final ImageView imageView = (ImageView) convertView.findViewById(R.id.store_iv_logo);
                Glide.with(context).load(hotCategoryBean.getImg()).placeholder(R.mipmap.ic_launcher).into(imageView);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goOtherPage(hotCategoryBean.getType(),hotCategoryBean.getValue(),hotCategoryBean.getTitleName());
                    }
                });
                break;
            case 1 :
                NewFloorsItem newFloorsItem2 = (NewFloorsItem) listItem.map.get("1");
                CategoryListBean categoryListBean = gson.fromJson(gson.toJson(newFloorsItem2.getWideGoodsList()),CategoryListBean.class);
                convertView = mLayoutInflater.inflate(R.layout.view_holder_second_type_layout,null);
                NoScrollGridView noScrollGridView = (NoScrollGridView) convertView.findViewById(R.id.no_gv_gv_category);
                CommonAdapter<HotCategoryListData> adapter = new CommonAdapter<HotCategoryListData>(context, categoryListBean.getCategoryList(), R.layout.view_holder_eight_type_layout) {
                    @Override
                    public void convert(ViewHolder helper, final HotCategoryListData item) {
                        Glide.with(context).load(item.getImg()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.rl_bg));
                        Glide.with(context).load(item.getLogoImg()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_icon));
                        helper.setText(R.id.tv_up_name,item.getTitleName());
                        helper.setText(R.id.tv_down_name,item.getSubtitleName());
                        try {
                            ((TextView) helper.getView(R.id.tv_up_name)).setTextColor(Color.parseColor("#" + item.getTitleColor()));
                            ((TextView) helper.getView(R.id.tv_down_name)).setTextColor(Color.parseColor("#" + item.getSubtitleColor()));
                        }catch (Exception e){

                        }

                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goOtherPage(item.getType(),item.getValue(),item.getCategoryName());
                            }
                        });
                    }
                };
                noScrollGridView.setAdapter(adapter);
                noScrollGridView.setFocusable(false);
                break;

            case 2 :
                convertView = mLayoutInflater.inflate(R.layout.view_holder_thrid_type_layout,null);
                ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon0);
                TextView tv_content_title = (TextView) convertView.findViewById(R.id.tv_content_title0);
                NoScrollGridView noGvThrid = (NoScrollGridView) convertView.findViewById(R.id.no_gv_gv_hot);
                View view_one = convertView.findViewById(R.id.view_one);
                View view_two = convertView.findViewById(R.id.view_two);
                NewFloorsItem newFloorsItem3 = (NewFloorsItem) listItem.map.get("2");
                tv_content_title.setText(newFloorsItem3.getGf_name());
                Glide.with(context).load(newFloorsItem3.getIconPath()).into(iv_icon);
                if (newFloorsItem3.getGf_css()!=null) {
                    tv_content_title.setTextColor(Color.parseColor("#" + newFloorsItem3.getGf_css()));
                    view_one.setBackgroundColor(Color.parseColor("#" + newFloorsItem3.getGf_css()));
                    view_two.setBackgroundColor(Color.parseColor("#" + newFloorsItem3.getGf_css()));
                }
                CategoryListBean hotCategoryBean3 = gson.fromJson(gson.toJson(newFloorsItem3.getWideGoodsList()),CategoryListBean.class);
                CommonAdapter<HotCategoryListData> hot_adapter = new CommonAdapter<HotCategoryListData>(context, hotCategoryBean3.getCategoryList(), R.layout.chose_hot_cat_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final HotCategoryListData item) {
                        ImageLoaderUtil.displayImage(item.getImg(), (ImageView) helper.getView(R.id.iv_icon));
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goOtherPage(item.getType(),item.getValue(),item.getTitleName());
                            }
                        });
                    }
                };
                noGvThrid.setAdapter(hot_adapter);
                noGvThrid.setFocusable(false);
                break;

            case 3 :
                convertView = mLayoutInflater.inflate(R.layout.view_holder_four_type_layout,null);
                ImageView iv_icon2 = (ImageView) convertView.findViewById(R.id.iv_icon);
                NoScrollGridView noGvFour = (NoScrollGridView) convertView.findViewById(R.id.fragment_no_gv);
                TextView tv_content_title2 = (TextView) convertView.findViewById(R.id.tv_content_title);
                View view_ = convertView.findViewById(R.id.view_one);
                View view__ = convertView.findViewById(R.id.view_two);
                NewFloorsItem newFloorsItem4 = (NewFloorsItem) listItem.map.get("3");
                Glide.with(context).load(newFloorsItem4.getIconPath()).into(iv_icon2);
                tv_content_title2.setText(newFloorsItem4.getGf_name());
                if (newFloorsItem4.getGf_css()!=null) {
                    tv_content_title2.setTextColor(Color.parseColor("#" + newFloorsItem4.getGf_css()));
                    view_.setBackgroundColor(Color.parseColor("#" + newFloorsItem4.getGf_css()));
                    view__.setBackgroundColor(Color.parseColor("#" + newFloorsItem4.getGf_css()));
                }
                WideGoodsList goodsList = gson.fromJson(gson.toJson(newFloorsItem4.getWideGoodsList()),WideGoodsList.class);
                CommonAdapter<WideGoodsListBean> wide_adapter = new CommonAdapter<WideGoodsListBean>(context, goodsList.getGoodsList(), R.layout.collections_goods_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final WideGoodsListBean item) {

                        helper.setText(R.id.tv_title, item.getGoodsName() +" " +item.getGoodsSerial());
                        Glide.with(context).load(item.getImg()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_icon));
                        try {
                            helper.setText(R.id.tv_price, mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getGoodsPrice()));
                        }catch (Exception e){

                        }

                        if (String.valueOf(item.getGoodsSalenum()).equals("0")){
                            helper.setText(R.id.tv_num, "已售" + item.getGoodsSaleNum() + "件");
                        }else {
                            helper.setText(R.id.tv_num, "已售" + item.getGoodsSalenum() + "件");
                        }

                        helper.getView(R.id.rl_rltop).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goOtherPage(item.getType(),item.getValue(),item.getTitleName());
                            }
                        });

                        helper.getView(R.id.tv_go_cart).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                                bean = new OpenFlightAloneBean("open");
//                                beanDataInterface.setData(bean);
//                                PublicUtils.goCart(context, String.valueOf(item.getValue()));
                            }
                        });

                        try {
                            if (item.getGoodsNumType().equals("0")) {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                            } else {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                            }
                        }catch (Exception e){

                        }



                        helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (item.getGcId() != null) {
                                    //TODO 去商品搜索结果页

                                    Intent[] intents = new Intent[1];
                                    Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                                    intent.putExtra("gcilThird", item.getGcId());
                                    intent.putExtra("keyWord", "找相似");
                                    intent.putExtra("type", "catefortType");
                                    intent.putExtra("searchType", "goods");
                                    intents[0] = intent;
                                    mContext.startActivities(intents);
                                }
                            }
                        });
                    }
                };
                noGvFour.setAdapter(wide_adapter);
                noGvFour.setFocusable(false);
                break;

            case 4 :
                convertView = mLayoutInflater.inflate(R.layout.view_holder_five_type_layout,null);
                ImageView iv_icon5 = (ImageView) convertView.findViewById(R.id.iv_icon);
                NoScrollGridView noGvfive = (NoScrollGridView) convertView.findViewById(R.id.fragment_no_gv);
                TextView tv_content_title5 = (TextView) convertView.findViewById(R.id.tv_content_title);
                View view_f= convertView.findViewById(R.id.view_one);
                View view__4 = convertView.findViewById(R.id.view_two);
                NewFloorsItem newFloorsItem5 = (NewFloorsItem) listItem.map.get("4");
                Glide.with(context).load(newFloorsItem5.getIconPath()).into(iv_icon5);
                tv_content_title5.setText(newFloorsItem5.getGf_name());
                if (newFloorsItem5.getGf_css()!=null) {
                    tv_content_title5.setTextColor(Color.parseColor("#" + newFloorsItem5.getGf_css()));
                    view_f.setBackgroundColor(Color.parseColor("#" + newFloorsItem5.getGf_css()));
                    view__4.setBackgroundColor(Color.parseColor("#" + newFloorsItem5.getGf_css()));
                }
                WideGoodsList wideGoodsList = gson.fromJson(gson.toJson(newFloorsItem5.getWideGoodsList()),WideGoodsList.class);
                List<ChoseFloorStyle> styleList = new ArrayList<>();
                try {
                    for (int i = 0; i < wideGoodsList.getGoodsList().size(); i++) {
                        WideGoodsListBean bean = wideGoodsList.getGoodsList().get(i);
                        ChoseFloorStyle style = new ChoseFloorStyle(bean.getImg(), bean.getGoodsId(), bean.getSeq(), bean.getGoodsPrice(), bean.getCollCount(), bean.getGoodsName(), bean.getGoodsSerial(), "1", "", "", bean.getMonthlySales(), bean.getGcId(),bean.getType(),bean.getValue(),bean.getGoodsNumType(),bean.getGoodsSaleNum());
                        styleList.add(style);
                    }
                    for (int j = 0; j < wideGoodsList.getStoreList().size(); j++) {
                        StoreListBean bean = wideGoodsList.getStoreList().get(j);
                        ChoseFloorStyle style = new ChoseFloorStyle(bean.getImg(), 0, 0, "", "", "", "", "2", bean.getStoreName(), bean.getStoreId(), "", "",bean.getType(),bean.getValue(),"",1);
                        styleList.add(1, style);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                CommonAdapter<ChoseFloorStyle> com_adapter = new CommonAdapter<ChoseFloorStyle>(context, styleList, R.layout.collections_goods_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final ChoseFloorStyle item2) {
                        if (item2.getStyle().equals("1")) {
                            ImageLoaderUtil.displayImage(item2.getImg(), (ImageView) helper.getView(R.id.iv_icon));
                            helper.setText(R.id.tv_price, mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(item2.getGoodsPrice()));
                            if (String.valueOf(item2.getGoodsSalenum()).equals("0")){
                                helper.setText(R.id.tv_num, "已售" + item2.getGoodsSaleNum() + "件");
                            }else {
                                helper.setText(R.id.tv_num, "已售" + item2.getGoodsSalenum() + "件");
                            }
                            helper.setText(R.id.tv_title, item2.getGoodsName() +"  " +item2.getGoodsSerial());
                            helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (item2.getGcId() != null) {
                                        //TODO 去商品搜索结果页
                                        Intent[] intents = new Intent[1];
                                        Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                                        intent.putExtra("gcilThird", item2.getGcId());
                                        intent.putExtra("keyWord", "找相似");
                                        intent.putExtra("type", "catefortType");
                                        intent.putExtra("searchType", "goods");
                                        intents[0] = intent;
                                        mContext.startActivities(intents);
                                    }
                                }
                            });

                            helper.getView(R.id.rl_rltop).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    goOtherPage(item2.getType(),item2.getValue(),item2.getTitleName());
                                }
                            });

                            helper.getView(R.id.tv_go_cart).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    bean = new OpenFlightAloneBean("open");
//                                    beanDataInterface.setData(bean);
//                                    PublicUtils.goCart(context, String.valueOf(item2.getValue()));
                                }
                            });

                        } else {
                            Glide.with(context).load(item2.getImg()).placeholder(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.iv_icon_store));
                            helper.getView(R.id.iv_icon_store).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                     goOtherPage(item2.getType(),item2.getValue(),item2.getTitleName());
                                }
                            });
                        }

                        try {
                            if(item2.getGoodsNumType().equals("0")){
                                ((ImageView)helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                            }else {
                                ((ImageView)helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                            }
                        }catch (Exception e){}


                    }
                };
                noGvfive.setAdapter(com_adapter);
                noGvfive.setFocusable(false);
                break;

            case 5:
                convertView = mLayoutInflater.inflate(R.layout.view_holder_six_type_layout,null);
                ImageView iv_icon3 = (ImageView) convertView.findViewById(R.id.iv_icon1);
                NoScrollGridView noGvSix = (NoScrollGridView) convertView.findViewById(R.id.fragment_no_gv4);
                TextView tv_content_title3 = (TextView) convertView.findViewById(R.id.tv_content_title2);
                View view_five= convertView.findViewById(R.id.view_one);
                View view__five = convertView.findViewById(R.id.view_two);
                NewFloorsItem newFloorsItem6 = (NewFloorsItem) listItem.map.get("5");
                Glide.with(context).load(newFloorsItem6.getIconPath()).into(iv_icon3);

                tv_content_title3.setText(newFloorsItem6.getGf_name());
                if (newFloorsItem6.getGf_css()!=null) {
                    tv_content_title3.setTextColor(Color.parseColor("#" + newFloorsItem6.getGf_css()));
                    view_five.setBackgroundColor(Color.parseColor("#" + newFloorsItem6.getGf_css()));
                    view__five.setBackgroundColor(Color.parseColor("#" + newFloorsItem6.getGf_css()));
                }
                WideGoodsList wideGoodsList1 = gson.fromJson(gson.toJson(newFloorsItem6.getWideGoodsList()),WideGoodsList.class);
                CommonAdapter<WideGoodsListBean> beanCommonAdapter = new CommonAdapter<WideGoodsListBean>(context, wideGoodsList1.getGoodsList(), R.layout.collections_goods_gv_item) {
                    @Override
                    public void convert(ViewHolder helper, final WideGoodsListBean item) {

                        helper.setText(R.id.tv_title, item.getGoodsName() +"  " +item.getGoodsSerial());
                        ImageLoaderUtil.displayImage(item.getImg(), (ImageView) helper.getView(R.id.iv_icon));
                        helper.setText(R.id.tv_price, mContext.getString(R.string.label_money) + NumberUtils.formatToDouble(item.getGoodsPrice()));

                        if (String.valueOf(item.getGoodsSalenum()).equals("0")){
                            helper.setText(R.id.tv_num, "已售" + item.getGoodsSaleNum() + "件");
                        }else {
                            helper.setText(R.id.tv_num, "已售" + item.getGoodsSalenum() + "件");
                        }

                        helper.getView(R.id.rl_rltop).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                goOtherPage(item.getType(),item.getValue(),item.getTitleName());
                            }
                        });


                        try {
                            if (item.getGoodsNumType().equals("0")) {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_order2);
                            } else {
                                ((ImageView) helper.getView(R.id.tv_go_cart)).setImageResource(R.mipmap.icon_goods2);
                            }
                        }catch (Exception e){}

                        helper.getView(R.id.tv_find_same).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (item.getGcId() != null) {
                                    //TODO 去商品搜索结果页
                                    Intent[] intents = new Intent[1];
                                    Intent intent = new Intent(mContext, SearchResultGoodsActivity.class);
                                    intent.putExtra("gcilThird", item.getGcId());
                                    intent.putExtra("keyWord", "找相似");
                                    intent.putExtra("type", "catefortType");
                                    intent.putExtra("searchType", "goods");
                                    intents[0] = intent;
                                    mContext.startActivities(intents);
                                }
                            }
                        });
                    }
                };
                noGvSix.setAdapter(beanCommonAdapter);
                noGvSix.setFocusable(false);
                break;

            case 6 :
                convertView = mLayoutInflater.inflate(R.layout.view_holder_seven_type_layout,null);
                ImageView left_iv = (ImageView) convertView.findViewById(R.id.left_iv);
                ImageView right_up_iv = (ImageView) convertView.findViewById(R.id.iv_seven);
                ImageView right_down_iv = (ImageView) convertView.findViewById(R.id.iv2_seven);
                TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                TextView up_tv_one = (TextView) convertView.findViewById(R.id.tv_up_one);
                TextView up_tv_two = (TextView) convertView.findViewById(R.id.tv_up_two);
                TextView down_tv_one = (TextView) convertView.findViewById(R.id.tv_down_one);
                TextView down_tv_two = (TextView) convertView.findViewById(R.id.tv_down_two);
                NewFloorsItem floor6 = (NewFloorsItem) listItem.map.get("6");
                tv_name.setText(floor6.getGf_name());
                final ApphotitemsBeanModel model = gson.fromJson(gson.toJson(floor6.getWideGoodsList()),ApphotitemsBeanModel.class);
                ImageLoaderUtil.displayImage(model.getLeft().getImg(),left_iv);
                ImageLoaderUtil.displayImage(model.getRightUp().getImg(),right_up_iv);
                ImageLoaderUtil.displayImage(model.getRightDown().getImg(),right_down_iv);
                up_tv_one.setText(model.getRightUp().getTitleName());
                up_tv_two.setText(model.getRightUp().getSubtitleName());
                down_tv_one.setText(model.getRightDown().getTitleName());
                down_tv_two.setText(model.getRightDown().getSubtitleName());
                try {
                    tv_name.setTextColor(Color.parseColor("#"+floor6.getGf_css()));
                    up_tv_one.setTextColor(Color.parseColor("#" + model.getRightUp().getTitleCol()));
                    up_tv_two.setTextColor(Color.parseColor("#" + model.getRightUp().getSubtitleCol()));
                    down_tv_one.setTextColor(Color.parseColor("#" + model.getRightDown().getTitleCol()));
                    down_tv_two.setTextColor(Color.parseColor("#" + model.getRightDown().getSubtitleCol()));
                }catch (Exception e){

                }
                convertView.findViewById(R.id.rl_right_up).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goOtherPage(model.getRightUp().getType(),model.getRightUp().getValue(),model.getRightUp().getTitleName());
                    }
                });

                convertView.findViewById(R.id.rl_right_down).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goOtherPage(model.getRightDown().getType(),model.getRightDown().getValue(),model.getRightDown().getTitleName());
                    }
                });

                convertView.findViewById(R.id.left_iv).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        goOtherPage(model.getLeft().getType(),model.getLeft().getValue(),model.getLeft().getTitleName());
                    }
                });

                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return listDatas.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 7;
    }


    public void goOtherPage(String type, String value,String title){
        Intent intent = null ;
        if(type == null || value == null){
            return;
        }
        switch (type){
            case "goodsInfo" :
                intent = new Intent(context, GoodsDetailsActivity.class);
                intent.putExtra("goodsId", value);
                bean = new OpenFlightAloneBean("close");
                beanDataInterface.setData(bean);
                context.startActivity(intent);
                break;
            case "store" :
                intent = new Intent(context,ShopActivity.class);
                intent.putExtra("storeId",value);
                context.startActivity(intent);
                break;
            case "goodsList":
                ArrayList<String> stringArrayList = new ArrayList<String>();
                String[] d = new String[]{};
                    if (value.indexOf(",")>0) {
                         d = value.split(",");
                    }else {
                        d = new String[]{value};
                    }
                    for (int i = 0; i < d.length; i++) {
                        if (!d[i].equals("")) {
                            stringArrayList.add(d[i]);
                        }
                    }
                intent = new Intent(context, GoodsListActivity.class);
                intent.putExtra("title", title);
                intent.putStringArrayListExtra("goodsIdList", stringArrayList);
                context.startActivity(intent);
                break;
            case "keyword" :
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType","goods");
                intent.putExtra("type","goodsType");
                context.startActivity(intent);
                break;
            case "storeSearch" :
                intent = new Intent(context, SearchFactoryActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("searchType","store");
                context.startActivity(intent);
                break;
            case "brandSearch" :
                intent = new Intent(context, SearchResultGoodsActivity.class);
                intent.putExtra("keyWord", value);
                intent.putExtra("type","brand");
                intent.putExtra("searchType","goods");
                context.startActivity(intent);
                break;
            case "channel" :
                intent = new Intent(context, ChannelInfoActivity.class);
                intent.putExtra("chId",value);
                intent.putExtra("title",title);
                context.startActivity(intent);
                break;
        }
    }

    public interface GetBeanDataInterface{
        void setData(OpenFlightAloneBean aloneBean);
    }
}
