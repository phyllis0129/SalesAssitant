package net.basilwang.trade;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xuyan
 * 
 *         2014-2-11
 */
public class EditCustomerActivity extends Activity implements OnClickListener {
	private RelativeLayout btnBack, btnSure;
	private EditText mName, mPhone, mAddress, mComment;
	private TextView mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_customer);
		findViews();
		init();
	}

	private void findViews() {
		btnBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		btnBack.setOnClickListener(this);
		btnSure = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
		btnSure.setOnClickListener(this);
		mName = (EditText) findViewById(R.id.customer_name_ed);
		mPhone = (EditText) findViewById(R.id.customer_phone_ed);
		mAddress = (EditText) findViewById(R.id.customer_address_ed);
		mComment = (EditText) findViewById(R.id.customer_other_ed);
		mTitle = (TextView) findViewById(R.id.customer_title_tv);
	}

	private void init() {
		mTitle.setText("编辑信息");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
