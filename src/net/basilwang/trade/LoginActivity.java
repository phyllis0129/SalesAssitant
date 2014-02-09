package net.basilwang.trade;

import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText edloginName, loginPsw;
	private Button loginBtn, cancelBtn;
	private Context mContext;
	private static String TAG = "LoginActivity";

	private String loginName, Password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initView();

	}

	private void initView() {
		edloginName = (EditText) findViewById(R.id.login_name);
		loginPsw = (EditText) findViewById(R.id.login_psw);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 隐藏软键
		// InputMethodManager imm = (InputMethodManager)
		// getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		switch (v.getId()) {
		case R.id.login_btn:
			checkLogin();
			break;

		case R.id.cancel_btn:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void checkLogin() {
		loginName = edloginName.getText().toString().trim();
		Password = loginPsw.getText().toString().trim();
		if (loginName.equals("") || Password.equals("")) {
			Toast.makeText(this, "请将登录信息填写完整", Toast.LENGTH_SHORT).show();
		}
		if (isNetAvailable()) {
			loginNow(mContext, loginName, Password);
		} else {
			Toast.makeText(this, "网络未连接，请检查！", Toast.LENGTH_SHORT).show();
		}

	}

	public void loginNow(final Context context, final String name, String psd) {
		SaLog.log(TAG, "loginName=" + name + ";Password=" + psd);
		final ProgressDialog mProgressDialog = ProgressDialog.show(context,
				null, "正在登录");
		AjaxParams params = new AjaxParams();
		params.put("loginName", name);
		params.put("Password", psd);
		FinalHttp fh = new FinalHttp();
		fh.post(StaticParameter.loginUrl, params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				mProgressDialog.dismiss();
				Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				mProgressDialog.dismiss();
				try {
					JSONObject json = new JSONObject(t.toString());
					if (json.getBoolean("isSuccess")) {
						// 保存token信息和用户名
						PreferenceUtils.modifyStringValueInPreference(context,
								"token", json.getString("token"));
						PreferenceUtils.modifyStringValueInPreference(context,
								"loginName", name);
						Intent intent = new Intent(mContext,
								SalesAssisteantActivity.class);
						startActivity(intent);
						finish();
					} else {
						Toast.makeText(mContext, json.getString("message"),
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

		});
	}

	private Boolean isNetAvailable() {
		return NetworkUtils.isConnect(this) ? true : false;
	}
}
