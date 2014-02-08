/**
 * 
 */
package net.basilwang.trade;

import net.basilwang.entity.Customer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * @author phyllis
 * 
 */
public class AddCustomerActivity extends Activity implements OnClickListener {

	private RelativeLayout btnBack, btnSure;
	private EditText mName, mPhone, mAddress, mComment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_customer);
		initView();
	}

	private void initView() {
		btnBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		btnBack.setOnClickListener(this);
		btnSure = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
		btnSure.setOnClickListener(this);
		mName = (EditText) findViewById(R.id.customer_name_ed);
		mPhone = (EditText) findViewById(R.id.customer_phone_ed);
		mAddress = (EditText) findViewById(R.id.customer_address_ed);
		mComment = (EditText) findViewById(R.id.customer_other_ed);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customer_title_bar_back:
			this.finish();
			break;
		case R.id.customer_title_bar_btn_sure:
			Intent intent = new Intent(this, CustomerInfoFragment.class);
			Customer customer = new Customer(mName.getText().toString().trim(),
					mPhone.getText().toString().trim(), 
					mAddress.getText().toString().trim(), 
					mComment.getText().toString().trim());
			
			intent.putExtra("newCustomer", customer);
			this.setResult(RESULT_OK, intent);
			this.finish();
			break;

		default:
			break;
		}
	}

}
