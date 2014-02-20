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
				.getBoolean("IsFirstUsed", true);
	}

	public static String getPreferToken(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString("token", null);
	}

	public static String getPreferLoginName(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString("loginName", null);
	}

	public static void modifyBooleanValueInPreferences(Context context,
			String key, Boolean value) {
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void modifyStringValueInPreference(Context context,
			String key, String value) {
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void clearData(Context context) {
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.clear();
		editor.commit();
	}
}
