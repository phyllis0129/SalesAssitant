package net.basilwang.libray;

public class StaticParameter {
	public static final int TaskResult_OK = 99;
	public static final int TaskResult_NO = 101;
	public static String http = "http://maibao.ruguozhai.me/";
	public static String loginUrl = http + "api/account/login";
	public static String getCustomer = http + "api/customer/index/";
	public static String getProduct = http + "api/product/area";
	public static String getProductSku = http + "api/product/areasku/";
	public static String postEditCustomer = http + "api/customer/edit/";
	public static String postAddCustomer = http + "api/customer/add";
	public static String getSummayproductsku = http
			+ "api/customer/summayproductsku/";
	public static String getPayment = http + "api/customer/paymentsummary/";
	public static String getTransactionRecords = http + "api/order/index/";
	public static String postOrderAdd = http + "api/order/add";
	public static String postReturnOrderAdd = http + "api/order/return";
	public static String getFilling = http + "api/customer/filling/";

}
