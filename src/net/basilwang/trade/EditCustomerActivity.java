package net.basilwang.trade;

import org.json.JSONException;
import org.json.JSONObject;

import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author xuyan
 * 
 *         2014-2-11
 */
public class EditCustomerActivity extends Activity implements OnClickListener {
	private RelativeLayout btnBack, btnSure;
	private EditText mName, mTel, mAddress, mDescription;
	private TextView mTitle;
	private Intent intent;
	private Customer mCustomer;
	private ProgressDialog progressDialog;
	private boolean isSuccess = false;
	private Context mContext;
	private Activity mActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_customer);
		mContext = this;
		mActivity = this;
		intent = getIntent();
		mCustomer = intent.getParcelableExtra("customer");
		findViews();
		init();
	}

	private void findViews() {
		btnBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		btnBack.setOnClickListener(this);
		btnSure = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
		btnSure.setOnClickListener(this);
		mName = (EditText) findViewById(R.id.customer_name_ed);
		mTel = (EditText) findViewById(R.id.customer_phone_ed);
		mAddress = (EditText) findViewById(R.id.customer_address_ed);
		mDescription = (EditText) findViewById(R.id.customer_other_ed);
		mTitle = (TextView) findViewById(R.id.customer_title_tv);
	}

	private void init() {
		mTitle.setText("编辑信息");
		mName.setText(mCustomer.getName());
		mTel.setText(mCustomer.getTel());
		mAddress.setText(mCustomer.getAddress());
		mDescription.setText(mCustomer.getDescription());
	}

	private void edit() {
		progressDialog = ProgressDialog.show(this, "", "数据提交中，请稍候....", true,
				false);
		mCustomer.setName(mName.getText().toString().trim());
		mCustomer.setTel(mTel.getText().toString().trim());
		mCustomer.setAddress(mAddress.getText().toString().trim());
		mCustomer.setDescription(mDescription.getText().toString().trim());
		AjaxParams params = new AjaxParams();
		params.put("name", mCustomer.getName());
		params.put("tel", mCustomer.getTel());
		params.put("address", mCustomer.getAddress());
		params.put("description", mCustomer.getDescription());
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(this));
		fh.post(StaticParameter.postEditCustomer + mCustomer.getId(), params,
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Toast.makeText(mContext, "修改失败，请稍后重试！",
								Toast.LENGTH_SHORT).show();
						SaLog.log("editfffffffffffff", strMsg);
						progressDialog.dismiss();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						SaLog.log("edit", t.toString());
						try {
							JSONObject jsonObject = new JSONObject(t.toString());
							isSuccess = jsonObject.getBoolean("isSuccess");
							Toast.makeText(mContext,
									jsonObject.getString("message"),
									Toast.LENGTH_SHORT).show();
						} catch (JSONException e) {
							e.printStackTrace();
						}
						if (isSuccess) {
							intent.putExtra("customer_edit", mCustomer);
							setResult(RESULT_OK, intent);
							finish();
						}
						progressDialog.dismiss();
					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customer_title_bar_back:
			setResult(RESULT_CANCELED);
			finish();
			break;
		case R.id.customer_title_bar_btn_sure:
			edit();
			break;

		default:
			break;
		}

	}

}
