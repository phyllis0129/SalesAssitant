package net.basilwang.trade;

import java.util.List;

import net.basilwang.entity.Customer;
import net.basilwang.entity.Payment;
import net.basilwang.entity.Record;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.RecordsExpandableAdapter;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

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
	private TextView cRealcollectiong;
	private TextView cDesc;
	private Button cReturn;
	private RelativeLayout cBack;
	private RelativeLayout cEdit;
	private Intent intent;
	private Customer mCustomer;
	private String mId, mReceivable, mRealcollectiong;
	private Context mContext;
	private Payment mPayment;
	private static final int EDIT_CUSTOMER = 101;
	private ExpandableListView expandableListView;
	private int expandFlag = -1;
	private RecordsExpandableAdapter recordsAdapter;
	private List<Record> records;
	private ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_customer_info);
		mContext = this;
		intent = getIntent();
		mCustomer = intent.getParcelableExtra("customer");
		mId = mCustomer.getId();
		findViews();
		init();
	}

	private void init() {
		getPayment(mId);
		mRealcollectiong = "实收货款:";
		mReceivable = "应收货款";
		if (mCustomer != null) {
			cName.setText(mCustomer.getName());
			cTel.setText(mCustomer.getTel());
			Linkify.addLinks(cTel, Linkify.PHONE_NUMBERS);// 增加点击电话号码后，可以拨打电话的功能
			cAddress.setText(mCustomer.getAddress());
			cDesc.setText(mCustomer.getDescription());
		}

		cRealcollectiong.setText(mRealcollectiong);
		cReceivable.setText(mReceivable);
		cBack.setOnClickListener(this);
		cReturn.setOnClickListener(this);
		cEdit.setOnClickListener(this);

		// 二级分类关闭的触发事件
		expandableListView
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int groupPosition) {
						recordsAdapter.notifyDataSetChanged();
					}
				});

		expandableListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView listView, View v,
					int groupPosition, long id) {
				if (expandFlag == -1) {
					// 展开被选的group
					expandableListView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					expandableListView.setSelectedGroup(groupPosition);
					expandFlag = groupPosition;

					recordsAdapter.setSelectItemChild(-1);
				} else if (expandFlag == groupPosition) {
					expandableListView.collapseGroup(expandFlag);
					expandFlag = -1;
				} else {
					expandableListView.collapseGroup(expandFlag);
					// 展开被选的group
					expandableListView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					expandableListView

					.setSelectedGroup(groupPosition);
					expandFlag = groupPosition;

					recordsAdapter.setSelectItemChild(-1);
				}

				return true;
			}
		});
	}

	private void findViews() {
		cName = (TextView) findViewById(R.id.customer_name);
		cTel = (TextView) findViewById(R.id.customer_tel);
		cAddress = (TextView) findViewById(R.id.customer_address);
		cReceivable = (TextView) findViewById(R.id.customer_receivable);
		cRealcollectiong = (TextView) findViewById(R.id.customer_paid);
		cDesc = (TextView) findViewById(R.id.customer_description);
		cReturn = (Button) findViewById(R.id.customer_returnGoods);
		cBack = (RelativeLayout) findViewById(R.id.customer_title_bar_back);
		cEdit = (RelativeLayout) findViewById(R.id.customer_title_bar_btn_sure);
		expandableListView = (ExpandableListView) findViewById(R.id.customer_record_list);
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
			intent.putExtra("customer", mCustomer);
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
				mCustomer = data.getParcelableExtra("customer_edit");
				init();
			}
			break;

		default:
			break;
		}
	}

	private void getPayment(final String id) {
		mDialog = ProgressDialog.show(mContext, null, "正在读取...", false, false);
		final FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(mContext));
		fh.get(StaticParameter.getPayment + id, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				mDialog.dismiss();
				ReLoginUtils.authorizedFailed(mContext, errorNo);
				Toast.makeText(mContext, "读取失败!", Toast.LENGTH_SHORT).show();
				SaLog.log("CustomerInfoMoreActivity", strMsg);
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				mPayment = JSON.parseObject(t.toString(), Payment.class);
				SaLog.log("CustomerInfoMoreActivity", "success:" + t.toString());
				if (mPayment != null) {
					cReceivable.setText("应收货款:" + mPayment.getReceivable());
					cRealcollectiong.setText("实收货款:"
							+ mPayment.getRealcollection());
				}
				fh.get(StaticParameter.getSummayproductsku + id,
						new AjaxCallBack<Object>() {

							@Override
							public void onFailure(Throwable t, int errorNo,
									String strMsg) {
								super.onFailure(t, errorNo, strMsg);
								mDialog.dismiss();
								ReLoginUtils.authorizedFailed(mContext,
										errorNo);
								Toast.makeText(mContext, "读取交易记录失败！" + strMsg,
										Toast.LENGTH_SHORT).show();
							}

							@Override
							public void onSuccess(Object t) {
								super.onSuccess(t);

								records = JSON.parseArray(t.toString(),
										Record.class);
								if (records != null) {
									recordsAdapter = new RecordsExpandableAdapter(
											mContext, records);
									expandableListView
											.setAdapter(recordsAdapter);
								}
								mDialog.dismiss();
							}
						});
			}

		});
		// fh.get(StaticParameter.getSummayproductsku + id,
		// new AjaxCallBack<Object>() {
		//
		// @Override
		// public void onFailure(Throwable t, int errorNo,
		// String strMsg) {
		// super.onFailure(t, errorNo, strMsg);
		// Toast.makeText(mContext, "读取交易记录失败！" + strMsg,
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// @Override
		// public void onSuccess(Object t) {
		// super.onSuccess(t);
		// records = JSON.parseArray(t.toString(), Record.class);
		// if (records != null) {
		// recordsAdapter = new RecordsExpandableAdapter(
		// mContext, records);
		// expandableListView.setAdapter(recordsAdapter);
		// }
		// }
		// });
	}

	// private void getSummayproductsku(){
	// FinalHttp fh = new FinalHttp();
	// fh.addto
	// }
}
