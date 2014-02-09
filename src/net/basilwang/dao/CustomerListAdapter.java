/**
 * 
 */
package net.basilwang.dao;

import java.util.List;

import net.basilwang.trade.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author phyllis
 * 
 */
public class CustomerListAdapter extends BaseAdapter {

	private List<String> customerNameList;
	private Context context;

	public CustomerListAdapter(Context context, List<String> customerNameList) {
		this.customerNameList = customerNameList;
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return customerNameList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return customerNameList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder mHolder = null;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.customer_item, null, false);
			mHolder.customerName = (TextView)convertView.findViewById(R.id.customer_item_name);
			convertView.setTag(mHolder);
		}else{
			mHolder = (Holder) convertView.getTag();
		}
		mHolder.customerName.setText(customerNameList.get(position));
		
		return convertView;
	}

	class Holder {
		TextView customerName;
	}

}
