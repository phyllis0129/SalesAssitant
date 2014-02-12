/**
 * 
 */
package net.basilwang.utils;

import net.basilwang.trade.LoginActivity;
import net.basilwang.trade.SalesAssisteantActivity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author Basilwang
 * 
 */
public class AuthorizedFailedUtils {

	public static void reLogin(Context context) {
		Toast.makeText(context, "您的账号异常，请重新登录", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		context.startActivity(intent);
		SalesAssisteantActivity.INSTANCE.finish();
		PreferenceUtils.clearData(context);
	}

}
