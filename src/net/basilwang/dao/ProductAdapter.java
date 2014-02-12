/**
 * 
 */
package net.basilwang.dao;

import java.util.HashMap;
import java.util.List;

import net.basilwang.entity.Product;
import net.basilwang.trade.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * @author phyllis
 * 
 */
public class ProductAdapter extends BaseAdapter {

	private Context context;
	private List<Product> products;
	private static HashMap<Integer, Boolean> isSelected;
	private static HashMap<Integer, String> skuMap;
	private static HashMap<Integer, String> amountMap;

	public ProductAdapter(Context context, List<Product> products) {
		this.context = context;
		this.products = products;
		isSelected = new HashMap<Integer, Boolean>();
		initData();
	}

	private void initData() {
		for (int i = 0; i < products.size(); i++) {
			getIsSelected().put(i, false);
			getAmountMap().put(i, "");
			getSkuMap().put(i, "");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return products.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Product getItem(int position) {
		return products.get(position);
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
		ViewHolder viewHolder = null;
		if (null == convertView) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.product_item, null, false);
			viewHolder.name = (TextView) convertView
					.findViewById(R.id.product_item_name);
			viewHolder.amount = (TextView) convertView
					.findViewById(R.id.product_item_selected_amount);
			viewHolder.skus = (TextView) convertView
					.findViewById(R.id.product_item_skus);
			viewHolder.cb = (CheckBox) convertView
					.findViewById(R.id.product_item_cb);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(getItem(position).getName());
		viewHolder.cb.setChecked(getIsSelected().get(position));
		int amount = getItem(position).getAreaProductSkuList().size();
		if (amount > 0) {
			viewHolder.amount.setText("选中 " + amount + " 种");
			String skuStr = "";
			for (int i = 0; i < amount; i++) {
				skuStr += getItem(position).getAreaProductSkuList().get(i)
						.getProductSku().getName()
						+ "；";
			}
			viewHolder.skus.setText(skuStr);
		} else {
			viewHolder.amount.setText("");
			viewHolder.skus.setText("");
		}
		return convertView;
	}

	public class ViewHolder {
		TextView name, amount, skus;
		public CheckBox cb;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		ProductAdapter.isSelected = isSelected;
	}

	public static HashMap<Integer, String> getSkuMap() {
		return skuMap;
	}

	public static HashMap<Integer, String> getAmountMap() {
		return amountMap;
	}

}
