package net.basilwang.trade;

import java.util.List;

import net.basilwang.dao.RecordsExpandableAdapter;
import net.basilwang.entity.Customer;
import net.basilwang.entity.Payment;
import net.basilwang.entity.Record;
import net.basilwang.entity.ValidateResult;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
	private Button cReturn, repayment;
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
		if (NetworkUtils.isConnect(mContext)) {
			getPayment(mId);
		}
		mRealcollectiong = "实收货款:";
		mReceivable = "应收货款:";
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
		repayment.setOnClickListener(this);
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
		repayment = (Button) findViewById(R.id.customer_returnMoney);
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
			intent.setClass(this, ReturnOrderActivity.class);
			intent.putExtra("customer", mCustomer);
			startActivity(intent);
			break;
		case R.id.customer_title_bar_btn_sure:// 编辑按钮
			intent.putExtra("customer", mCustomer);
			intent.setClass(this, EditCustomerActivity.class);
			startActivityForResult(intent, EDIT_CUSTOMER);
			break;
		case R.id.customer_returnMoney:
			if (NetworkUtils.isConnect(mContext)) {
				showPaymentDialog();
			}

		default:
			break;
		}

	}

	private void showPaymentDialog() {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.payment_dialog, null);
		((TextView) view.findViewById(R.id.payment_dialog_recievable))
				.setText(cReceivable.getText());
		((TextView) view.findViewById(R.id.payment_dialog_real))
				.setText(cRealcollectiong.getText());
		final float receivable = Float.parseFloat(mPayment.getReceivable());
		final double real = Float.parseFloat(mPayment.getRealcollection());
		final EditText payment = (EditText) view
				.findViewById(R.id.payment_dialog_repay);
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setView(view)
				.setTitle("补款详情")
				.setPositiveButton("确定无误",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (payment.getText().toString().equals("")) {
									Toast.makeText(mContext, "补款额不能为空",
											Toast.LENGTH_SHORT).show();
									return;
								}
								if (Double.parseDouble(payment.getText()
										.toString()) > receivable - real) {
									payment.setSelection(0, payment.getText()
											.length());
									Toast.makeText(mContext, "补款额不能大于应收额-实收额",
											Toast.LENGTH_SHORT).show();
									return;
								}
								fillingPayment(payment.getText().toString());

							}
						}).create();
		dialog.show();
	}

	protected void fillingPayment(String payment) {
		final ProgressDialog dialog = ProgressDialog.show(mContext, null,
				"正在提交...", false, false);
		FinalHttp http = new FinalHttp();
		http.addHeader("X-Token", PreferenceUtils.getPreferToken(mContext));
		AjaxParams params = new AjaxParams();
		params.put("price", payment);
		http.get(StaticParameter.getFilling + mId + "?price=" + payment,
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Log.e("sasasa", strMsg);
						dialog.dismiss();
						ReLoginUtils.authorizedFailed(mContext, errorNo);
						Toast.makeText(mContext, "补交货款失败，稍后再试",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						dialog.dismiss();
						Log.v("payment fill", t.toString());
						ValidateResult result = JSON.parseObject(t.toString(),
								ValidateResult.class);
						Toast.makeText(mContext, "补款" + result.getMessage(),
								Toast.LENGTH_SHORT).show();
						init();
					}

				});
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
		mDialog = ProgressDialog
				.show(mContext, null, "正在获取数据...", false, false);
		final FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(mContext));
		fh.get(StaticParameter.getPayment + id, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				mDialog.dismiss();
				ReLoginUtils.authorizedFailed(mContext, errorNo);
				Toast.makeText(mContext, "客户产品交易记录读取失败，稍后再试",
						Toast.LENGTH_SHORT).show();
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
								ReLoginUtils
										.authorizedFailed(mContext, errorNo);
								Toast.makeText(mContext, "读取记录失败！" + strMsg,
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
	}

}
