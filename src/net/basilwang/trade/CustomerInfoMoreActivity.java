package net.basilwang.trade;

import android.app.Activity;
import android.os.Bundle;
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
	private Button cReturn;
	private RelativeLayout cBack;
	private RelativeLayout cEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customer_info);
		findViews();
		init();
	}

	private void init() {
		cName.setText("李先生");
		cTel.setText("电话:13000000000");
		cAddress.setText("地址：济南七里铺");
		cReceivable.setText("应收货款:1000");
		cPaid.setText("实收货款:500");
		cBack.setOnClickListener(this);
	}

	private void findViews() {
		cName = (TextView) findViewById(R.id.customer_name);
		cTel = (TextView) findViewById(R.id.customer_tel);
		cAddress = (TextView) findViewById(R.id.customer_address);
		cReceivable = (TextView) findViewById(R.id.customer_receivable);
		cPaid = (TextView) findViewById(R.id.customer_paid);
		cReturn = (Button) findViewById(R.id.customer_returnGoods);
		cBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		cEdit = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.customer_title_bar_back:
			finish();
			break;

		default:
			break;
		}
		
	}

}
