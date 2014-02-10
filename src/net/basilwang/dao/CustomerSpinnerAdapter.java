/**
 * 
 */
package net.basilwang.dao;

import java.util.List;

import net.basilwang.entity.Customer;
import net.basilwang.trade.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * @author phyllis
 * 
 */
public class CustomerSpinnerAdapter extends BaseAdapter {

	private Context context;
	private List<Customer> customers;
	private Holder mHolder = null;

	public CustomerSpinnerAdapter(Context context, List<Customer> customers) {
		this.context = context;
		this.customers = customers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return customers.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public String getItem(int position) {
		return customers.get(position).getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
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
		
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.customer_spinner_item, null, false);
			mHolder.customerName = (TextView)convertView.findViewById(R.id.customer_spinner_item_name);
			mHolder.cRadioButton = (RadioButton) convertView.findViewById(R.id.customer_spinner_item_rb);
			convertView.setTag(mHolder);
		}else{
			mHolder = (Holder) convertView.getTag();
		}
		mHolder.customerName.setText(getItem(position));
		return convertView;
	}

	class Holder {
		TextView customerName;
		RadioButton cRadioButton;
	}
	
	public RadioButton getRadioButton()
	{
		return mHolder.cRadioButton;
	}

}
