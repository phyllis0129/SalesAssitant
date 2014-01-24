/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class OrderInfoFragment extends ListFragment implements OnClickListener {

	private View mView;
	private int mScreenWidth;
	private ListView orderList;
	private TextView recrivableCounts;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private List<OrderItem> mOrderItemList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mScreenWidth = getActivity().getWindowManager().getDefaultDisplay()
				.getWidth();
		mView = inflater
				.inflate(R.layout.fragment_order_info, container, false);
		initView();
		bindData();
		return mView;
	}

	private void bindData() {
		mOrderItemList = new ArrayList<OrderItem>();
		for (int i = 0; i < 5; i++) {
			OrderItem mOrderItem = new OrderItem("string" + i);
			mOrderItemList.add(mOrderItem);
		}
		orderList.setAdapter(new OrderAdapter(getActivity(), mOrderItemList,
				mScreenWidth));
	}

	private void initView() {
		orderList = (ListView) mView.findViewById(android.R.id.list);
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Toast.makeText(getActivity(), "添加订单", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

}
