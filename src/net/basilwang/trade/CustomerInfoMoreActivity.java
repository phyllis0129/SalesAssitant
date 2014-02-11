package net.basilwang.trade;

import net.basilwang.entity.Customer;
import net.basilwang.utils.TaskResult;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author xuyan
 * 
 *         2014-2-8
 */
public class CustomerInfoMoreActivity extends Activity implements
		OnClickListener {

	private TextView cName;
	private TextView cTel;
	private TextView cAddress;
	private TextView cReceivable;
	private TextView cPaid;
	private TextView cDesc;
	private Button cReturn;
	private RelativeLayout cBack;
	private RelativeLayout cEdit;
	private Intent intent;
	private Customer mCustomer;
	private String mName, mTel, maddress, mReceivable, mPaid, mDes;
	private static final int EDIT_CUSTOMER = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customer_info);
		intent = getIntent();
		mCustomer = intent.getParcelableExtra("customer");

		findViews();
		init();
	}

	private void init() {
		mName = "客户姓名:";
		mTel = "联系电话:";
		maddress = "地址:";
		mDes = "备注信息:";
		mPaid = "实收货款:";
		mReceivable = "应收货款";
		if (mCustomer != null) {
			mName = mName + mCustomer.getName();
			mTel = mTel + mCustomer.getTel();
			maddress = maddress + mCustomer.getAddress();
			mDes = mDes + mCustomer.getDescription();
		}
		cName.setText(mName);
		cTel.setText(mTel);
		Linkify.addLinks(cTel, Linkify.PHONE_NUMBERS);// 增加点击电话号码后，可以拨打电话的功能
		cAddress.setText(maddress);
		cReceivable.setText("应收货款:1000");
		cPaid.setText("实收货款:500");
		cDesc.setText(mDes);
		cBack.setOnClickListener(this);
		cReturn.setOnClickListener(this);
		cEdit.setOnClickListener(this);
	}

	private void findViews() {
		cName = (TextView) findViewById(R.id.customer_name);
		cTel = (TextView) findViewById(R.id.customer_tel);
		cAddress = (TextView) findViewById(R.id.customer_address);
		cReceivable = (TextView) findViewById(R.id.customer_receivable);
		cPaid = (TextView) findViewById(R.id.customer_paid);
		cDesc = (TextView) findViewById(R.id.customer_description);
		cReturn = (Button) findViewById(R.id.customer_returnGoods);
		cBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		cEdit = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customer_title_bar_back:// 返回按钮
			finish();
			break;
		case R.id.customer_returnGoods:// 退货按钮

			break;
		case R.id.customer_title_bar_btn_sure:// 编辑按钮
			intent.setClass(this, EditCustomerActivity.class);
			startActivityForResult(intent, EDIT_CUSTOMER);
			break;

		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case EDIT_CUSTOMER:
			if (resultCode == RESULT_OK) {

			}

			break;

		default:
			break;
		}
	}

}
