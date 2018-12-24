package com.zhejiang.haoxiadan.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhejiang.haoxiadan.util.Event;
import com.zhejiang.haoxiadan.util.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "WXPayEntryActivity";
	
    private IWXAPI api;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pay_result);
		EventBus.getDefault().register(this);
        
    	api = WXAPIFactory.createWXAPI(this, Utils.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this);
		super.onDestroy();
	}
	@Subscribe
	public void onEventMainThread(Event event) {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			/*AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("title");
			builder.setMessage(resp.errStr + ";code=" + String.valueOf(resp.errCode));
			builder.show();*/
			switch (resp.errCode) {
				case 0:
					EventBus.getDefault().post(Event.WX_PAY_RESULT_OK);
					finish();
					break;
				case -1:
					EventBus.getDefault().post(Event.WX_PAY_RESULT_ERR);
					finish();
					break;
				case -2:
					EventBus.getDefault().post(Event.WX_PAY_RESULT_ERR);
					finish();
					break;
				default:

					break;
			}

		}
	}
}