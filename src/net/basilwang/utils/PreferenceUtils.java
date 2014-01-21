/**
 * 
 */
package net.basilwang.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * @author phyllis
 * 
 */
public class PreferenceUtils {

	public static Boolean getPreferIsFirstUsed(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("IsFirstUsed", false);
	}
	
	public static void modifyBooleanValueInPreferences(Context context,String key,Boolean value){
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
}
