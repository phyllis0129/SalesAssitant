package net.basilwang.trade;

import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.ReLoginUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SlideMenuFragment extends ListFragment {

	private View slidingMenuView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		slidingMenuView = inflater.inflate(R.layout.sliding_menu, null);
		return slidingMenuView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] menuNames = getResources().getStringArray(R.array.menu_items);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.row_title);
		for (int i = 0; i < menuNames.length; i++) {
			adapter.add(menuNames[i]);
			setListAdapter(adapter);
		}
	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		String title = "";
		switch (position) {
		case 0:
			title = "客户列表";
			newContent = new CustomerInfoFragment();
			break;
		case 1:
			title = "订单信息";
			newContent = new OrderInfoFragment();
			break;
		case 2:
			title = "交易记录";
			newContent = new TransactionRecordsFragment();
			break;
		case 3:
			ReLoginUtils.logout(getActivity());
			break;
		}
		if (NetworkUtils.isConnect(getActivity()) && newContent != null)
			switchFragment(newContent, title);
	}

	private void switchFragment(Fragment fragment, String title) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof SalesAssisteantActivity) {
			SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
			saa.switchContent(fragment, title);
		}
	}

}
