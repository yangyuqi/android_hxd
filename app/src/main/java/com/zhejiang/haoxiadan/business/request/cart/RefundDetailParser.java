package com.zhejiang.haoxiadan.business.request.cart;

import android.support.annotation.Nullable;

import com.zhejiang.haoxiadan.business.AbsBaseParser;
import com.zhejiang.haoxiadan.business.OnBaseRequestListener;
import com.zhejiang.haoxiadan.model.common.ConsultHistory;
import com.zhejiang.haoxiadan.model.common.OrderGoods;
import com.zhejiang.haoxiadan.model.common.RefundDetail;
import com.zhejiang.haoxiadan.model.common.Supplier;
import com.zhejiang.haoxiadan.model.requestData.in.RefundApplyFormListData;
import com.zhejiang.haoxiadan.model.requestData.in.RefundOrderFormStatusByIdData;
import com.zhejiang.haoxiadan.util.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据订单id，查询退款状态
 * Created by KK on 2017/9/7.
 */
public class RefundDetailParser extends AbsBaseParser {

    public RefundDetailParser(OnBaseRequestListener onBaseRequestListener) {
        super(onBaseRequestListener);
    }

    @Override
    protected void parseData(@Nullable String data) {

        RefundOrderFormStatusByIdData refundOrderFormStatusByIdData = mDataParser.parseObject(data,RefundOrderFormStatusByIdData.class);




        RefundDetail refundDetail = mapped(refundOrderFormStatusByIdData);
        List<ConsultHistory> consultHistories = mappedHistoryList(refundOrderFormStatusByIdData);

        RefundDetailListener listener = (RefundDetailListener)mOnBaseRequestListener.get();
        if(listener!=null){
            listener.onRefundOrderFormStatusByIdSuccess(refundDetail,consultHistories);
        }
    }

    private RefundDetail mapped(RefundOrderFormStatusByIdData refundOrderFormStatusByIdData){
        RefundDetail refundDetail = new RefundDetail();
        if(refundOrderFormStatusByIdData.getRefundApplyFormList()==null || refundOrderFormStatusByIdData.getRefundApplyFormList().size()==0){
            return refundDetail;
        }

        RefundApplyFormListData refundApplyFormListData = refundOrderFormStatusByIdData.getRefundApplyFormList().get(0);

        Supplier supplier = new Supplier();
        supplier.setCompany(refundApplyFormListData.getStoreName());

        List<String> imgList = new ArrayList<>();
//        String[] userApplyImgs = refundApplyFormListData.getUserApplyImg().split(",");
//        if (userApplyImgs.length>0){
//            for(int i=0;i<userApplyImgs.length;i++){
//                if(!"".equals(userApplyImgs[i].trim())){
//                    imgList.add(userApplyImgs[i].trim());
//                }
//            }
//        }
        if (refundApplyFormListData.getUserApplyImg().size()>0){
            imgList = refundApplyFormListData.getUserApplyImg();
        }

        switch (refundApplyFormListData.getStatus()){
            case 0://0待审核
                refundDetail.setRefundStatus(RefundDetail.REFUND_STATUS.REFUND_ING);
                break;
            case 1://1 审核通过
                refundDetail.setRefundStatus(RefundDetail.REFUND_STATUS.REFUND_COMPLETE);
                break;
            case 2://2 拒绝
                refundDetail.setRefundStatus(RefundDetail.REFUND_STATUS.REFUND_FAILED);
                break;
        }
        refundDetail.setSupplier(supplier);
        refundDetail.setReason(refundApplyFormListData.getUserApplyReason());
        refundDetail.setExplain(refundApplyFormListData.getUserApplyExplain());
        refundDetail.setPrice(refundApplyFormListData.getRefundPrice()+"");
        refundDetail.setImgs(imgList);
        refundDetail.setApplyTime(refundApplyFormListData.getAddTime()+"");
        refundDetail.setRefundNo(refundApplyFormListData.getRefundTradeNo());

        return refundDetail;
    }

    private List<ConsultHistory> mappedHistoryList(RefundOrderFormStatusByIdData refundOrderFormStatusByIdData){
        List<ConsultHistory> historyList = new ArrayList<>();
        for(RefundApplyFormListData refundApplyFormListData : refundOrderFormStatusByIdData.getRefundApplyFormList()){
            ConsultHistory consultHistoryOne = new ConsultHistory();
            ConsultHistory consultHistoryTwo = new ConsultHistory();

            if(refundApplyFormListData.getStatus()!=0){
                consultHistoryTwo.setUserIcon(refundApplyFormListData.getStoreLogo());
                consultHistoryTwo.setUserName(refundApplyFormListData.getStoreName());
                consultHistoryTwo.setTime(TimeUtils.getDateTimeFromMills(refundApplyFormListData.getAuditDate()+""));
                String contentTwo = "";
                switch (refundApplyFormListData.getStatus()){
                    case 1:
                        contentTwo = "标题：卖家同意退款";
                        break;
                    case 2:
                        contentTwo = "标题：卖家拒绝退款";
                        break;
                }
                consultHistoryTwo.setContent(contentTwo);
                consultHistoryTwo.setExtra("备注："+refundApplyFormListData.getAuditFailReason());

                historyList.add(consultHistoryTwo);
            }

            consultHistoryOne.setUserIcon(refundApplyFormListData.getUserHeadImg());
            consultHistoryOne.setUserName(refundApplyFormListData.getUserName());
            consultHistoryOne.setTime(TimeUtils.getDateTimeFromMills(refundApplyFormListData.getAddTime()+""));
            String contentOne = "买家"+refundApplyFormListData.getUserName()+"于"+TimeUtils.getDateTimeFromMills(refundApplyFormListData.getAddTime()+"")+"发起退款申请";
            consultHistoryOne.setContent(contentOne);
            historyList.add(consultHistoryOne);

        }
        return historyList;
    }


}
