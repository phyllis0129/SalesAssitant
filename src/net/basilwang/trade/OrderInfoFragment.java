/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.OnResizeListener;
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
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private OrderAdapter orderAdapter;
	private List<OrderItem> mOrderItemList;

	private ResizeLayout headerLinearLayout;

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
		for (int i = 0; i < 1; i++) {
			OrderItem mOrderItem = new OrderItem("极光剑 " + i, "200*2", "瓶");
			mOrderItemList.add(mOrderItem);
		}
		orderAdapter = new OrderAdapter(getActivity(), mOrderItemList,
				orderListView);
		orderListView.setAdapter(orderAdapter);
	}

	private void initView() {
		orderListView = (SlideCutListView) mView
				.findViewById(android.R.id.list);
		orderListView.setRemoveListener(this);
		recrivableCounts = (TextView) mView
				.findViewById(R.id.counts_receivable);
		sureBtn = (Button) mView.findViewById(R.id.order_sure_btn);
		sureBtn.setOnClickListener(this);
		cancelBtn = (Button) mView.findViewById(R.id.order_cancel_btn);
		cancelBtn.setOnClickListener(this);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);
		addBtn.setOnClickListener(this);

		headerLinearLayout = (ResizeLayout) mView
				.findViewById(R.id.origin);
		headerLinearLayout.setOnResizeListener(new OnResizeListener() {

			@Override
			public void OnResize(int t, int oldt) {
				// TODO Auto-generated method stub
				if (t!=oldt) {
					Log.v("sas", "oldb="+oldt+",b="+t);
				}
				Log.v("sas", "oldb="+oldt+",b="+t);
				orderAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Toast.makeText(getActivity(), "添加订单", Toast.LENGTH_SHORT).show();
			OrderItem mOrderItem = new OrderItem("极光剑 new", "200*2", "瓶");
			mOrderItemList.add(mOrderItem);
			orderAdapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}

	@Override
	public void removeItem(RemoveDirection direction, int position) {
		String tip = orderAdapter.getItem(position).getGoodsName() + " 订单";
		orderAdapter.remove(position);
		switch (direction) {
		case RIGHT:
			Toast.makeText(getActivity(), "向右删除  " + tip, 100).show();
			break;
		case LEFT:
			Toast.makeText(getActivity(), "向左删除  " + tip, 100).show();
			break;

		default:
			break;
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && keyCode == KeyEvent.KEYCODE_BACK) {
			Toast.makeText(getActivity(), "InputMethodManager back", 2000)
					.show();
		}
		return true;
	}

}
