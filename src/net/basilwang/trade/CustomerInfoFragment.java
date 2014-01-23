/**
 * 
 */
package net.basilwang.trade;

import net.basilwang.entity.Customer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class CustomerInfoFragment extends Fragment implements OnClickListener {

	private View mView;
	private TextView mTxtView;
	private ListView mListView;
	private RelativeLayout addBtn;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_customer_list, container, false);
		initView();
		return mView;
	}

	private void initView() {
		mTxtView = (TextView) mView.findViewById(R.id.customer_list_describe);
		String user = "		100982";
		mTxtView.setText(getResources().getString(
				R.string.customer_list_describe, user));
		mListView = (ListView) mView.findViewById(R.id.customer_list);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);
		addBtn.setOnClickListener(this);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
			startActivityForResult(intent, 1);
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case -1:
			Customer customer = data.getParcelableExtra("newCustomer");
			Toast.makeText(getActivity(), customer.getName(), Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
	}

}
