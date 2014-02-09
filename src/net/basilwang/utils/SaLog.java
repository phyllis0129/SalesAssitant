package net.basilwang.utils;

import android.util.Log;

public class SaLog {
	// 10月25日17：58发布版本关闭过log版本号为5
	private static boolean logflag = true;

	// 开启服务CenterActivity 186行
	public static void log(String tag, String info) {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		int i = 1;
		if (logflag) {
			StackTraceElement s = ste[i];
			Log.d(tag,
					String.format("[%s][%s][%s]-%s", s.getClassName(),
							s.getLineNumber(), s.getMethodName(), info));
		}
	}
}