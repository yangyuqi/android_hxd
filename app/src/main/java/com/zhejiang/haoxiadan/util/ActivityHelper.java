package com.zhejiang.haoxiadan.util;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.zhejiang.haoxiadan.R;

public class ActivityHelper {
	
	public static Intent getIntent(Context context, Class<?> target) {
		return new Intent(context, target);
	}
	
	public static void startActivity(Activity activity, Class<?> target) {
		Intent it = getIntent(activity, target);
		startActivity(activity, it);
	}
	
	public static void startActivityForResult(Activity activity, Class<?> target, int requestCode) {
		Intent it = getIntent(activity, target);
		activity.startActivityForResult(it, requestCode);
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void startActivityForResult(Activity activity, Intent it, int requestCode) {
		activity.startActivityForResult(it, requestCode);
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void startActivity(Activity activity, Intent intent) {
		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
	}
	
	public static void finishActivity(Activity activity) {
		activity.finish();
		activity.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}
}
