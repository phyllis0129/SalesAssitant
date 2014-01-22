package net.basilwang.trade;

import net.basilwang.utils.NetworkUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

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
			Intent intent = new Intent(this, SalesAssisteantActivity.class);
			startActivity(intent);
		} else {
			Toast.makeText(this, "网络未连接，请检查！", Toast.LENGTH_SHORT).show();
		}

	}

	private Boolean isNetAvailable() {
		return NetworkUtils.isConnect(this) ? true : false;
	}

}
