package net.basilwang.trade;

import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AddCustomerActivity extends Activity implements OnClickListener {

	private RelativeLayout btnBack, btnSure;
	private EditText mName, mTel, mAddress, mDescription;
	private String cName, cTel, cAddress, cDescription;
	private ProgressDialog mProgressDialog;
	private boolean isSuccess = false;
	private Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_customer);
		mContext = this;
		initView();
	}

	private void initView() {
		btnBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		btnBack.setOnClickListener(this);
		btnSure = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
		btnSure.setOnClickListener(this);
		mName = (EditText) findViewById(R.id.customer_name_ed);
		mTel = (EditText) findViewById(R.id.customer_phone_ed);
		mAddress = (EditText) findViewById(R.id.customer_address_ed);
		mDescription = (EditText) findViewById(R.id.customer_other_ed);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customer_title_bar_back:
			finish();
			break;
		case R.id.customer_title_bar_btn_sure:
			cName = mName.getText().toString().trim();
			cTel = mTel.getText().toString().trim();
			cAddress = mAddress.getText().toString().trim();
			cDescription = mDescription.getText().toString().trim();
			if (cName.equals("")) {
				Toast.makeText(this, "请输入客户名称!", Toast.LENGTH_SHORT).show();
				return;
			}
			showDialog();
			break;

		default:
			break;
		}
	}

	protected void showDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认提交吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				addCustomer();
				dialog.dismiss();
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	/**
	 * 提交增加客户请求
	 */
	private void addCustomer() {
		mProgressDialog = ProgressDialog.show(this, null, "正在提交数据，请稍后...",
				false, false);
		AjaxParams params = new AjaxParams();
		params.put("name", cName);
		params.put("tel", cTel);
		params.put("address", cAddress);
		params.put("description", cDescription);
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(this));
		fh.post(StaticParameter.postAddCustomer, params,
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Toast.makeText(getApplicationContext(), "添加失败，请稍后重试!",
								Toast.LENGTH_SHORT).show();
						ReLoginUtils.authorizedFailed(mContext, errorNo);
						SaLog.log("AddCUstomerActivity", "add_fail:" + strMsg);
						mProgressDialog.dismiss();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						mProgressDialog.dismiss();
						try {
							JSONObject jsonObject = new JSONObject(t.toString());
							isSuccess = jsonObject.getBoolean("isSuccess");
							Toast.makeText(getApplicationContext(),
									jsonObject.getString("message"),
									Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (isSuccess) {
							Intent intent = new Intent(AddCustomerActivity.this, CustomerInfoFragment.class);
							AddCustomerActivity.this.setResult(RESULT_OK, intent);
							finish();
						} else {
							Toast.makeText(getApplicationContext(),
									"添加失败，请稍后重试!", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}
}
