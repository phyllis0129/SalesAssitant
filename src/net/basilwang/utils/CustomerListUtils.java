/**
 * 
 */
package net.basilwang.utils;

import java.util.List;

import net.basilwang.dao.CustomerListAdapter;
import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.trade.LoginActivity;
import net.basilwang.trade.SalesAssisteantActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.sax.StartElementListener;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 * 
 */
public class CustomerListUtils {

	private static Context mContext;
	private static ProgressDialog mProgressDialog;
	private static List<Customer> mCustomers;
	private static CustomerListAdapter mCustomerAdapter;
	private static ListView mListView;

	public static void getCustomerList(Context context,
			List<Customer> customers, CustomerListAdapter customerAdapter,
			ListView listView, final boolean isNeedProgressDialog) {
		mContext = context;
		mCustomers = customers;
		mCustomerAdapter = customerAdapter;
		mListView = listView;
		if (isNeedProgressDialog)
			mProgressDialog = ProgressDialog.show(context, "", "数据加载中，请稍候....",
					true, false);
		AjaxParams params = new AjaxParams();
		// params.put("X-Token", PreferenceUtils.getPreferToken(context));
		SaLog.log("token", PreferenceUtils.getPreferToken(context));
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(context));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				if (errorNo == 401) {
					Toast.makeText(mContext, "您的账号异常，请重新登录", Toast.LENGTH_SHORT)
					.show();
					Intent intent = new Intent();
					intent.setClass(mContext, LoginActivity.class);
					mContext.startActivity(intent);
					SalesAssisteantActivity.INSTANCE.finish();
					PreferenceUtils.clearData(mContext);
				}
				Log.v("error", errorNo + strMsg + t.toString());
				mProgressDialog.dismiss();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				mCustomers = JSON.parseArray(t.toString(), Customer.class);
				Log.v("customer id", mCustomers.get(0).getId());
				for (int i = 0; i < mCustomers.size(); i++) {
				}
				mCustomerAdapter = new CustomerListAdapter(mContext, mCustomers);
				mListView.setAdapter(mCustomerAdapter);
				mProgressDialog.dismiss();
			}

		});

	}

}
