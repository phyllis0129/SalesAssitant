package net.basilwang.trade;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.basilwang.entity.ValidateResult;
import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.PreferenceUtils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class LoginActivity extends Activity implements OnClickListener {

	private ProgressBar mProgress;
	private EditText loginName, loginPsw;
	private Button loginBtn, cancelBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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
			Toast.makeText(this, "succedd", Toast.LENGTH_SHORT).show();
			LoginTask loginTask = new LoginTask();
			loginTask.execute(name,psw);
			this.finish();
		} else {
			Toast.makeText(this, "网络未连接，请检查！", Toast.LENGTH_SHORT).show();
		}

	}

	private Boolean isNetAvailable() {
		return NetworkUtils.isConnect(this) ? true : false;
	}

	// private class loginThread implements Runnable{
	//
	// @Override
	// public void run() {
	//
	// }
	//
	// }

	class LoginTask extends AsyncTask<Object, Object, ValidateResult> {

		@Override
		protected void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);
			mProgress.setVisibility(View.VISIBLE);
		}

		private String name, psw;

		@Override
		protected void onPostExecute(ValidateResult result) {
			mProgress.setVisibility(View.INVISIBLE);
			if (result.getSuccess().equals("true")) {
				LoginActivity.this.finish();
				PreferenceUtils.modifyStringValueInPreference(
						LoginActivity.this, "userToken", result.getToken());
				Intent intent = new Intent(LoginActivity.this,SalesAssisteantActivity.class);
				startActivity(intent);
			} else {
				String toast = result.getMessage().replace("<br/>", "\n");
				Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_LONG)
						.show();
			}
			super.onPostExecute(result);
		}

		@Override
		protected ValidateResult doInBackground(Object... params) {
			name = (String) params[0];
			psw = (String) params[1];
			publishProgress();
			ValidateResult v_result = new ValidateResult();
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(
					"http://www.ruguozhai.me/api/account/login");
			List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("loginName", name));
			postParameters.add(new BasicNameValuePair("password", psw));
			try {
				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						postParameters, HTTP.UTF_8);
				post.setEntity(formEntity);
				HttpResponse response = client.execute(post);
				Log.v("result", "code="
						+ response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = filter(EntityUtils.toString(response
							.getEntity()));
					Log.v("result", "result=" + result);
					v_result = jsonData(result);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				Log.v("UnsupportedEncodingException 1", e.toString());
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				Log.v("ClientProtocolException 2", e.toString());
			} catch (IOException e) {
				e.printStackTrace();
				Log.v("IOException 3", e.toString());
			}
			return v_result;
		}

		public String filter(String s) {
			String s1 = s.replace("\\r", "");
			String s2 = s1.replace("\\n", "");
			String s3 = s2.substring(1, s2.length() - 1);
			String s4 = s3.replace(" ", "");
			String result = s4.replace("\\", "");
			return result;
		}

		public ValidateResult jsonData(String str) {
			ValidateResult result = JSON.parseObject(str,
					new TypeReference<ValidateResult>() {
					});
			return result;
		}
	}
}
