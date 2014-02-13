/**
 * 
 */
package net.basilwang.utils;

import net.basilwang.trade.LoginActivity;
import net.basilwang.trade.SalesAssisteantActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class ReLoginUtils {

	public static void authorizedFailed(Context context, int errorNo) {
		if (errorNo == 401) {
			Toast.makeText(context, "您的账号异常，请重新登录", Toast.LENGTH_SHORT).show();
			logout(context);
		}
	}

	public static void logout(Context context){
		Intent intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		context.startActivity(intent);
		((Activity) context).finish();
		SalesAssisteantActivity.INSTANCE.finish();
		PreferenceUtils.clearData(context);
	}
}
