package net.basilwang.trade;

import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	private ProgressBar mProgress;
	private EditText loginName, loginPsw;
	private Button loginBtn, cancelBtn;
	private Context mContext;
	private static String TAG = "LoginActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = this;
		initView();

	}

	private void initView() {
		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
		loginName = (EditText) findViewById(R.id.login_name);
		loginPsw = (EditText) findViewById(R.id.login_psw);
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		cancelBtn = (Button) findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// 隐藏软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
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
		String name = loginName.getText().toString().trim();
		String psw = loginPsw.getText().toString().trim();
		if (name.equals("") || psw.equals("")) {
			Toast.makeText(this, "请将登录信息填写完整", Toast.LENGTH_SHORT).show();
		} else if (isNetAvailable()) {
			loginPost(name, psw);
		} else {
			Toast.makeText(this, "网络未连接，请检查！", Toast.LENGTH_SHORT).show();
		}

	}

	private void loginPost(String loginName, String Password) {
		SaLog.log(TAG, "loginName=" + loginName + ";Password=" + Password);
		AjaxParams params = new AjaxParams();
		params.put("loginName", loginName);
		params.put("Password", Password);
		FinalHttp fh = new FinalHttp();
		fh.post("", params, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				Toast.makeText(mContext, "用户名/密码错误" + strMsg,
						Toast.LENGTH_SHORT).show();
				SaLog.log(TAG, "error=" + t.toString() + ";No=" + errorNo
						+ ";msg=" + strMsg);
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				super.onLoading(count, current);
			}

			@Override
			public void onSuccess(Object t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Toast.makeText(mContext, "succedd", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(mContext,
						SalesAssisteantActivity.class);
				startActivity(intent);
				((Activity) mContext).finish();
				SaLog.log(TAG, "success=" + t.toString());
			}

		});
	}

	private Boolean isNetAvailable() {
		return NetworkUtils.isConnect(this) ? true : false;
	}

}
