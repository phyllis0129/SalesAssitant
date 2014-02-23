/**
 * 
 */
package net.basilwang.dao;

import java.util.List;

import net.basilwang.entity.OrderProduct;
import net.basilwang.trade.R;
import net.basilwang.view.SlideCutListView;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Basilwang
 * 
 */
public class OrderAdapter extends BaseAdapter implements OnTouchListener {

	private Context mContext;
	private List<OrderProduct> mOrderItemList;
	private SlideCutListView mOrderListView;
	private Holder mHolder;
	public String onTouchedTag = "";

	public OrderAdapter(Context context, List<OrderProduct> orderItemList,
			SlideCutListView orderListView) {
		this.mContext = context;
		this.mOrderItemList = orderItemList;
		this.mOrderListView = orderListView;

	}

	public void remove(int position) {
		mOrderItemList.remove(position);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mOrderItemList.size();
	}

	@Override
	public OrderProduct getItem(int position) {
		return mOrderItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Holder mHolder = null;
		// if (convertView == null) {
		mHolder = new Holder();
		convertView = LayoutInflater.from(mContext).inflate(
				R.layout.order_item, null);
		initView(convertView);

		// convertView.setTag(mHolder);
		// } else {
		// mHolder = (Holder) convertView.getTag();
		// }
		setContent(position);
		setListener();

		return convertView;
	}

	private void initView(View convertView) {
		mHolder.goodsStockTV = (TextView) convertView
				.findViewById(R.id.goods_stock);
		mHolder.goodsNameTV = (TextView) convertView
				.findViewById(R.id.goods_name);
		mHolder.goodsSpecificationTV = (TextView) convertView
				.findViewById(R.id.goods_Specification);
		mHolder.goodsCountsET = (EditText) convertView
				.findViewById(R.id.goods_counts);
		mHolder.goodsUnitTV = (TextView) convertView
				.findViewById(R.id.goods_unit);
		mHolder.goodsPriceET = (EditText) convertView
				.findViewById(R.id.goods_price);
		mHolder.goodsPriceUnitTV = (TextView) convertView
				.findViewById(R.id.price_unit);
		mHolder.goodsTotalPriceTV = (TextView) convertView
				.findViewById(R.id.goods_total_price);
	}

	private void setListener() {
		mHolder.goodsCountsET.addTextChangedListener(new MyTextChangedListener(
				"count"));
		mHolder.goodsCountsET.setOnTouchListener(this);
		mHolder.goodsPriceET.addTextChangedListener(new MyTextChangedListener(
				"price"));
		mHolder.goodsPriceET.setOnTouchListener(this);
	}

	private void setContent(int position) {
		mHolder.goodsStockTV.setText("库存余量  "
				+ mOrderItemList.get(position).getStock());
		mHolder.goodsNameTV.setText(mOrderItemList.get(position).getName());
		mHolder.goodsSpecificationTV.setText(mOrderItemList.get(position)
				.getAreaProductSkuName());
		mHolder.goodsCountsET.setText(mOrderItemList.get(position).getAmount()
				.toString());

		mHolder.goodsCountsET.setTag(position);
		mHolder.goodsUnitTV.setText(mOrderItemList.get(position).getUnit());
		if (mOrderItemList.get(position).getPerPrice() != 0) {
			mHolder.goodsPriceET.setText(mOrderItemList.get(position)
					.getPerPrice().toString());
		}

		mHolder.goodsPriceUnitTV.setText("元/"
				+ mOrderItemList.get(position).getUnit());
		mHolder.goodsTotalPriceTV.setText(mOrderItemList.get(position)
				.getTotalPrice().toString()
				+ "￥");
	}

	private class Holder {
		TextView goodsStockTV;
		TextView goodsNameTV;
		TextView goodsSpecificationTV;
		EditText goodsCountsET;
		TextView goodsUnitTV;
		EditText goodsPriceET;
		TextView goodsPriceUnitTV;
		TextView goodsTotalPriceTV;
	}

	private class MyTextChangedListener implements TextWatcher {

		private String flag;

		public MyTextChangedListener(String flag) {
			this.flag = flag;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {
			int position = mOrderListView.getSlidePosition();
			if (flag.equals("count")) {
				if (!s.toString().equals("")
						&& Integer.parseInt(s.toString()) > Integer
								.parseInt(mOrderItemList.get(position)
										.getStock())) {
					Toast.makeText(mContext, "所填数量超过库存", Toast.LENGTH_SHORT)
							.show();
					mHolder.goodsCountsET.setText(mOrderItemList.get(position)
							.getStock());
				} else {
					mOrderItemList.get(position).setStringAmount(
							s.toString().trim());
				}
			}
			if (flag.equals("price")) {
				mOrderItemList.get(position)
						.setStringPrice(s.toString().trim());
			}
			mOrderItemList.get(position).setNullTotalPrice();
		}

	}

	/*
	 * @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
	 * InputMethodManager imm = (InputMethodManager) mContext
	 * .getSystemService(Context.INPUT_METHOD_SERVICE); switch (keyCode) { case
	 * KeyEvent.KEYCODE_BACK: if (imm.isActive()) notifyDataSetChanged(); break;
	 * case KeyEvent.KEYCODE_ESCAPE: case KeyEvent.KEYCODE_ENTER:
	 * notifyDataSetChanged(); imm.hideSoftInputFromWindow(v.getWindowToken(),
	 * 0); break; default: break; } return false; }
	 */

	public EditText getonTouchedEditText() {
		if (onTouchedTag.equals("count"))
			return mHolder.goodsCountsET;
		else if (onTouchedTag.equals("price"))
			return mHolder.goodsPriceET;
		return null;
	}

	public void setonTouchedTag(String str) {
		this.onTouchedTag = str;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (v.getId() == R.id.goods_counts)
			onTouchedTag = "count";
		else if (v.getId() == R.id.goods_price)
			onTouchedTag = "price";
		return false;
	}

}
