package net.basilwang.trade;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SlideMenuFragment extends ListFragment implements OnClickListener{

	private View slidingMenuView;
//	private ImageView img_icon_top;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			
			slidingMenuView=inflater.inflate(R.layout.sliding_menu, null);
//		img_icon_top=(ImageView)slidingMenuView.findViewById(R.id.img_icon_top);
		return slidingMenuView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] menuNames = getResources().getStringArray(R.array.menu_items);
//		SampleAdapter adapter = new SampleAdapter(getActivity());
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.row_title);
		for (int i = 0; i < menuNames.length; i++) {
//			adapter.add(new SampleItem(menuNames[i]));
			adapter.add(menuNames[i]);
			setListAdapter(adapter);
		}
	}
////	private int getIconResc(int position) {
////		int[] iconResc = {
////				R.drawable.menu_search,
////				R.drawable.menu_purchase, 
////				R.drawable.menu_road,
////				R.drawable.menu_youji,
////				R.drawable.menu_bus,
////				R.drawable.menu_setting,
////				R.drawable.menu_help,
////				R.drawable.ic_launcher};
////		return iconResc[position];
////	}
	private class SampleItem {
		public String tag;

		public SampleItem(String tag) {
			this.tag = tag;
		}
	}
	private class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(
						R.layout.menu_item, null);
			}
//			ImageView icon = (ImageView) convertView
//					.findViewById(R.id.row_icon);
//			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView
					.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}

	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Fragment newContent = null;
		String title = "";
		Toast.makeText(getActivity(), lv.getAdapter().getItem(position).toString(), Toast.LENGTH_LONG).show();
		switch (position) {
		case 0:
			title = "客户信息";
			newContent = new CustomerInfoFragment();
			break;
		case 1:
			title = "商品信息";
//			newContent = new PlaceOrderFragment();
			break;
		case 2:
			title = "交易记录";
//			newContent=new TransationsLogFragment();
			break;
		case 3:
			title = "退出";
			exit();
			break;
		}
		if (newContent != null)
			switchFragment(newContent,title);
	}

	// the meat of switching the above fragment
	private void switchFragment(Fragment fragment,String title) {
		if (getActivity() == null)
			return;
		if (getActivity() instanceof SalesAssisteantActivity) {
			SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
			saa.switchContent(fragment,title);
		}
	}

	private void exit() {
		getActivity().finish();
		
	}

	@Override
	public void onClick(View v) {
		Fragment newContent = null;
//		newContent=new MineFragment();
//		switchFragment(newContent);
		Log.v("icon", "click");
	}
}
