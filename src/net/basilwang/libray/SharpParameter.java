package net.basilwang.libray;

public class SharpParameter {
	public static final int TaskResult_OK = 99;
	public static final int TaskResult_NO = 101;
	public static final int TIMEINT = 60 * 1000;
	public static String http = "http://maibao.ruguozhai.me";
	public static String loginUrl = http + "/api/account/login";

	public static String dataBase = "SharedPreferences";
	public static String APP_SESSION_ID_NAME = "APPSESSIONID";
	public static String avatar = "";
	public static String nickname = "";

	public static String desKey = "1563##$*ni@*#456#$%122";
	public static SharpDES sharpDES = new SharpDES(desKey);
	public static String token = null;
	public static long nowDate = System.currentTimeMillis();
}
