/**
 * 
 */
package net.basilwang.trade;

import android.R.id;
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
public class OrderInfoFragment extends ListFragment implements OnClickListener{
	
	private View mView;
	private ListView orderList;
	private TextView recrivableCounts;
	private Button sureBtn,cancelBtn;
	private RelativeLayout addBtn;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_order_info, container, false);
		initView();
		return mView;
	}


	private void initView() {
		orderList = (ListView)mView.findViewById(android.R.id.list);
		recrivableCounts = (TextView)mView.findViewById(R.id.counts_receivable);
		sureBtn = (Button)mView.findViewById(R.id.order_sure_btn);
		sureBtn.setOnClickListener(this);
		cancelBtn = (Button)mView.findViewById(R.id.order_cancel_btn);
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
