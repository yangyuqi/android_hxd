package com.zhejiang.haoxiadan.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.chinapay.mobilepayment.activity.MainActivity;
import com.chinapay.mobilepayment.bean.OrderInfo;
import com.chinapay.mobilepayment.global.AsyGlobalInfo;
import com.chinapay.mobilepayment.global.CPGlobalInfo;
import com.chinapay.mobilepayment.utils.LogUtils;
import com.chinapay.mobilepayment.utils.UIUtils;
import com.chinapay.mobilepayment.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @ClassName: AsyncEncMsg类.
 * @Description: 远程加密异步类
 */
public class AsyncMPOrder extends AsyncTask<Integer, Integer, String> {
	/** .*/
	Activity context;
	/** .*/
	OrderInfo orderInfo;
	/** .*/
	String mode;
	/** .*/
	HashMap<String, String> hashmap;
	/** .*/
	private ProgressDialog mLoadingDialog;
	/**
	 * 构造函数.
	 * @param context 上下文
	 * @param orderInfo 订单信息
	 * @param mode 环境字段   
	 * @param loadingDialog 进度条对话框
	 **/
	public AsyncMPOrder(Activity context, OrderInfo orderInfo, String mode, ProgressDialog loadingDialog) {
		this.context = context;
		this.orderInfo = orderInfo;
		this.mode = mode;
		this.mLoadingDialog = loadingDialog;
	}

	/**
	 * 异步方法.
	 *
	 * @param params 无
	 * @return CodeData 无
	 */
	@Override
	protected String doInBackground(Integer... params) {
		String resp = null;
		OkHttpClient okHttpClient = new OkHttpClient();
		FormBody body = new FormBody.Builder()
				.add("Version",orderInfo.getVersion())
				.add("BusiType",orderInfo.getBusiType())
				.add("MerId",orderInfo.getMerId())
				.add("MerBgUrl",orderInfo.getMerBgUrl())
				.add("RemoteAddr",orderInfo.getRemoteAddr())
				.add("MerOrderNo",orderInfo.getMerOrderNo())
				.add("OrderAmt",orderInfo.getOrderAmt())
				.add("TranDate",orderInfo.getTranDate())
				.add("TranTime",orderInfo.getTranTime())
				.add("AccessType",orderInfo.getAccessType())
				.add("InstuId",orderInfo.getInstuId())
				.add("AcqCode",orderInfo.getAcqCode())
				.add("TranType",orderInfo.getTranType())
				.add("CurryNo",orderInfo.getCurryNo())
				.add("SplitType",orderInfo.getSplitType())
				.add("SplitMethod",orderInfo.getSplitMethod())
				.add("MerSplitMsg",orderInfo.getMerSplitMsg())
				.add("BankInstNo",orderInfo.getBankInstNo())
				.add("MerPageUrl",orderInfo.getMerPageUrl())
				.add("CommodityMsg",orderInfo.getCommodityMsg())
				.add("MerResv",orderInfo.getMerResv())
				.add("TranReserved",orderInfo.getTranReserved())
				.add("CardTranData",orderInfo.getCardTranData())
				.add("PayTimeOut",orderInfo.getPayTimeOut())
				.add("TimeStamp",orderInfo.getTimeStamp())
				.add("RiskData",orderInfo.getRiskData())
				.add("Signature",orderInfo.getSignature())
				.build();
		Request request = new Request.Builder()
				.url("")//"http://131.252.83.233:9080/CPPayWebDemo/MPOrder"
				.post(body)
				.build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			resp= response.body().string();
			LogUtils.i("返回应答resp=["+resp+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 处理返回结果.
	 *
	 * @param result 返回结果
	 */
	@Override
	protected void onPostExecute(String result) {
		if (mLoadingDialog.isShowing()) {
			mLoadingDialog.dismiss();
		}
		if (result == null) {
			if (!context.isFinishing()) {
				LogUtils.i("网络连接超时"+result);
				Utils.showDialogNoExit(context, AsyGlobalInfo.CODE_TIME_OUT, "网络连接超时", "");
			}
		} else {
			LogUtils.i("服务器端返回的result=["+result+"]");
			Pattern p = null;
			p = Pattern.compile("CardTranData=(.*)&RiskData=(.*)&Signature=(.*)");
			Matcher m = p.matcher(result);
			hashmap = new HashMap<String, String>();
			while (m.find()) {
				hashmap.put("CardTranData", m.group(1));
				hashmap.put("RiskData", m.group(2));
				hashmap.put("Signature", m.group(3));
			}
			//CPGlobalInfo.riskData = hashmap.get("riskData");
			//返回RiskData和CardTranData  base64+rsa加密过
			orderInfo.setRiskData(hashmap.get("RiskData"));
			orderInfo.setSignature(hashmap.get("Signature"));
			orderInfo.setCardTranData(hashmap.get("CardTranData"));
			//往sdk传送时，transreserved传明文
			orderInfo.setTranReserved(CPGlobalInfo.tranreserved);


			// ps:这一步开始真正调起银联标准支付插件cppaysdk
			final Intent intent = new Intent(context, MainActivity.class);
			final UIUtils.CustomDialog customDialog = UIUtils.genCustomDialog(context);
			final EditText et_property1 = customDialog.getEt_property1();
			final EditText et_property2 = customDialog.getEt_property2();
			et_property1.setText(hashmap.get("RiskData"));
			et_property2.setText(hashmap.get("Signature"));
			customDialog.setOnPositiveListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					orderInfo.setRiskData(et_property1.getText().toString().trim());
					orderInfo.setSignature(et_property2.getText().toString().trim());
					String orderInfoJson = toJson().toString();
					intent.putExtra("orderInfo", orderInfoJson);
					intent.putExtra("mode", mode);
					context.startActivity(intent);
					customDialog.dismiss();
				}
			});
			customDialog.setOnNegativeListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					context.startActivity(intent);
					String orderInfoJson = toJson().toString();
					intent.putExtra("orderInfo", orderInfoJson);
					intent.putExtra("mode", mode);
					customDialog.dismiss();
				}
			});
			customDialog.show();
		}
	}

	/**
	 * 转为Json.
	 *
	 * @return  json
	 */
	private JSONObject toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("Version",orderInfo.getVersion());
			json.put("BusiType",orderInfo.getBusiType());
			json.put("MerId",orderInfo.getMerId());
			json.put("MerBgUrl",orderInfo.getMerBgUrl());
			json.put("RemoteAddr",orderInfo.getRemoteAddr());
			json.put("MerOrderNo",orderInfo.getMerOrderNo());
			json.put("OrderAmt",orderInfo.getOrderAmt());
			json.put("TranDate",orderInfo.getTranDate());
			json.put("TranTime",orderInfo.getTranTime());
			json.put("AccessType",orderInfo.getAccessType());
			json.put("InstuId",orderInfo.getInstuId());
			json.put("AcqCode",orderInfo.getAcqCode());
			json.put("TranType",orderInfo.getTranType());
			json.put("CurryNo",orderInfo.getCurryNo());
			json.put("SplitType",orderInfo.getSplitType());
			json.put("SplitMethod",orderInfo.getSplitMethod());
			json.put("MerSplitMsg",orderInfo.getMerSplitMsg());
			json.put("BankInstNo",orderInfo.getBankInstNo());
			json.put("MerPageUrl",orderInfo.getMerPageUrl());
			json.put("CommodityMsg",orderInfo.getCommodityMsg());
			json.put("MerResv",orderInfo.getMerResv());
			json.put("TranReserved",orderInfo.getTranReserved());
			json.put("CardTranData",orderInfo.getCardTranData());
			json.put("PayTimeOut",orderInfo.getPayTimeOut());
			json.put("TimeStamp",orderInfo.getTimeStamp());
			json.put("RiskData",orderInfo.getRiskData());
			json.put("Signature",orderInfo.getSignature());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}