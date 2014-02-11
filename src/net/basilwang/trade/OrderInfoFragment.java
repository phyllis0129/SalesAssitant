/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.onKybdsChangeListener;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 * 
 */
public class OrderInfoFragment extends ListFragment implements OnClickListener,
		RemoveListener, OnItemSelectedListener {

	private View mView;
	private SlideCutListView orderListView;
	private TextView recrivableCounts;
	private EditText realCounts;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private OrderAdapter orderAdapter;
	private List<OrderItem> mOrderItemList;
	private List<Customer> customers;
	private Spinner customerSpinner;

	private ResizeLayout headerLinearLayout;
	private LinearLayout btnLinearLayout;

	private Customer assignedCustomer = null;

	private ArrayAdapter<String> customerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater
				.inflate(R.layout.fragment_order_info, container, false);
		initView();
		bindData();
		return mView;
	}

	private void bindData() {
		mOrderItemList = new ArrayList<OrderItem>();
		orderAdapter = new OrderAdapter(getActivity(), mOrderItemList,
				orderListView);
		orderListView.setAdapter(orderAdapter);
		customers = new ArrayList<Customer>();
		getCustomerList();
		// CustomerListUtils.getCustomerList(getActivity(), customers,
		// customerAdapter, customerSpinner, false);
	}

	private void initView() {
		orderListView = (SlideCutListView) mView
				.findViewById(android.R.id.list);
		recrivableCounts = (TextView) mView
				.findViewById(R.id.counts_receivable);
		realCounts = (EditText) mView.findViewById(R.id.counts_real);
		sureBtn = (Button) mView.findViewById(R.id.order_sure_btn);
		cancelBtn = (Button) mView.findViewById(R.id.order_cancel_btn);
		customerSpinner = (Spinner) mView.findViewById(R.id.customer_spinner);
		customerSpinner.setOnItemSelectedListener(this);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);

		btnLinearLayout = (LinearLayout) mView.findViewById(R.id.order_btn);
		headerLinearLayout = (ResizeLayout) mView.findViewById(R.id.origin);
		setListener();
	}

	public void setListener() {
		orderListView.setRemoveListener(this);
		sureBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		addBtn.setOnClickListener(this);
		customerSpinner.setOnItemSelectedListener(this);
		headerLinearLayout.setOnkbdStateListener(new onKybdsChangeListener() {

			@Override
			public void onKeyBoardStateChange(int state) {
				Log.v("sadsadsadaasdsa", "saddddddddddd");
				EditText onTouchedEditText = orderAdapter
						.getonTouchedEditText();
				switch (state) {
				case ResizeLayout.KEYBOARD_STATE_HIDE:
					btnLinearLayout.setVisibility(View.VISIBLE);
					orderAdapter.onTouchedTag = "";
					refreshRealCounts();
					Toast.makeText(getActivity(), "软键盘隐藏", Toast.LENGTH_SHORT)
							.show();
					break;
				case ResizeLayout.KEYBOARD_STATE_SHOW:
					btnLinearLayout.setVisibility(View.INVISIBLE);
					Toast.makeText(getActivity(), "软键盘弹起", Toast.LENGTH_SHORT)
							.show();
					if (onTouchedEditText != null) {
						onTouchedEditText.requestFocus();
						onTouchedEditText.setSelection(onTouchedEditText
								.getText().length());
					}
					break;
				}
			}
		});
	}

	private void getCustomerList() {
		AjaxParams params = new AjaxParams();
		SaLog.log("token", PreferenceUtils.getPreferToken(getActivity()));
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				if (errorNo == 401) {
					Toast.makeText(getActivity(), "您的账号异常，请重新登录",
							Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					getActivity().startActivity(intent);
					SalesAssisteantActivity.INSTANCE.finish();
					PreferenceUtils.clearData(getActivity());
				}
				Log.v("error", errorNo + strMsg + t.toString());
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				customers = JSON.parseArray(t.toString(), Customer.class);
				ArrayList<String> customerNamelist = new ArrayList<String>();
				customerNamelist.add("请选择");
				Log.v("customer id", customers.get(0).getId());
				for (int i = 0; i < customers.size(); i++) {
					customerNamelist.add(i + 1, customers.get(i).getName());
				}
				customerAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, customerNamelist);
				customerAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				customerSpinner.setAdapter(customerAdapter);
				customerSpinner.setPrompt("请选择客户");
			}

		});
	}

	public void refreshRealCounts() {
		Double totalCounts = 0.0;
		for (int i = 0; i < this.mOrderItemList.size(); i++) {
			totalCounts += mOrderItemList.get(i).getGoodsTotalPrice();
		}
		this.recrivableCounts.setText(totalCounts.toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Toast.makeText(getActivity(), "添加订单", Toast.LENGTH_SHORT).show();
			OrderItem mOrderItem = new OrderItem("极光剑 new", "200*2", "瓶");
			mOrderItemList.add(mOrderItem);
			orderAdapter.notifyDataSetChanged();
			Intent intent = new Intent(getActivity(),
					ProductInfoMoreActivity.class);

			startActivityForResult(intent, 0);
			refreshRealCounts();
			break;
		case R.id.order_cancel_btn:
			mOrderItemList.clear();
			orderAdapter.notifyDataSetChanged();
			refreshRealCounts();
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void removeItem(RemoveDirection direction, int position) {
		String tip = orderAdapter.getItem(position).getGoodsName() + " 订单";
		orderAdapter.remove(position);
		refreshRealCounts();
		switch (direction) {
		case RIGHT:
			Toast.makeText(getActivity(), "向右删除  " + tip, Toast.LENGTH_SHORT)
					.show();
			break;
		case LEFT:
			Toast.makeText(getActivity(), "向左删除  " + tip, Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		assignedCustomer = customers.get(position);
		Log.v("selected", assignedCustomer.getName());
		parent.setVisibility(View.VISIBLE);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
