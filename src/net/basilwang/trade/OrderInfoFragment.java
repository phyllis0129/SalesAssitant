/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.onKybdsChangeListener;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class OrderInfoFragment extends ListFragment implements OnClickListener,
		RemoveListener {

	private View mView;
	private SlideCutListView orderListView;
	private TextView recrivableCounts;
	private EditText realCounts;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private OrderAdapter orderAdapter;
	private List<OrderItem> mOrderItemList;

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
	}

	private void initView() {
		orderListView = (SlideCutListView) mView
				.findViewById(android.R.id.list);
		recrivableCounts = (TextView) mView
				.findViewById(R.id.counts_receivable);
		realCounts = (EditText) mView.findViewById(R.id.counts_real);
		sureBtn = (Button) mView.findViewById(R.id.order_sure_btn);
		cancelBtn = (Button) mView.findViewById(R.id.order_cancel_btn);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);

		btnLinearLayout = (LinearLayout) mView.findViewById(R.id.order_btn);
		headerLinearLayout = (ResizeLayout) mView.findViewById(R.id.origin);
		setListener();
	}
	
	public void setListener(){
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
					Toast.makeText(getActivity(), "软键盘隐�, Toast.LENGTH_SHORT)
							.show();
					break;
				case ResizeLayout.KEYBOARD_STATE_SHOW:
					btnLinearLayout.setVisibility(View.INVISIBLE);
					Toast.makeText(getActivity(), "软键盘弹�, Toast.LENGTH_SHORT)
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
			OrderItem mOrderItem = new OrderItem("极光�new", "200*2", "�);
			mOrderItemList.add(mOrderItem);
			orderAdapter.notifyDataSetChanged();
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

}
