/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.dao.OrderItem;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
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
public class OrderInfoFragment extends ListFragment implements OnClickListener,RemoveListener {

	private View mView;
	private SlideCutListView orderList;
	private TextView recrivableCounts;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private List<OrderItem> mOrderItemList;

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
		for (int i = 0; i < 15; i++) {
			OrderItem mOrderItem = new OrderItem("string" + i);
			mOrderItemList.add(mOrderItem);
		}
		orderList
				.setAdapter(new OrderAdapter(getActivity(), mOrderItemList,
						getActivity().getWindowManager().getDefaultDisplay()
								.getWidth()));
	}

	private void initView() {
		orderList = (SlideCutListView) mView.findViewById(android.R.id.list);
		orderList.setRemoveListener(this);
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

	@Override
	public void removeItem(RemoveDirection direction, int position) {
		// TODO Auto-generated method stub
		
	}

}
