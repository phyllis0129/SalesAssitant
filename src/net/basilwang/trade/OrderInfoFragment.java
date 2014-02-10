/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.basilwang.dao.CustomerSpinnerAdapter;
import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import net.basilwang.entity.Customer;
import net.basilwang.utils.CustomerListUtils;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.onKybdsChangeListener;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class OrderInfoFragment extends ListFragment implements OnClickListener,
		RemoveListener, OnItemSelectedListener {

	private View mView;
	private SlideCutListView orderListView;
	private ListView spinnerLsit;
	private TextView recrivableCounts;
	private EditText realCounts;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private OrderAdapter orderAdapter;
	private CustomerSpinnerAdapter customerAdapter;
	private List<OrderItem> mOrderItemList;
	private List<Customer> customers;
	private Spinner customerSpinner;

	private ResizeLayout headerLinearLayout;
	private LinearLayout btnLinearLayout;

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
		CustomerListUtils.getCustomerList(getActivity(), customers,
				customerAdapter, customerSpinner, false);
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
					GoodsInfoMoreActivity.class);

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

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
