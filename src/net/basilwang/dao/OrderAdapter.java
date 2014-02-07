/**
 * 
 */
package net.basilwang.dao;

import java.util.List;

import net.basilwang.trade.R;
import net.basilwang.view.SlideCutListView;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Basilwang
 * 
 */
public class OrderAdapter extends BaseAdapter implements OnFocusChangeListener,OnKeyListener {

	private Context mContext;
	private List<OrderItem> mOrderItemList;
	private SlideCutListView mOrderListView;
	private Holder mHolder;

	public OrderAdapter(Context context, List<OrderItem> orderItemList,
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
	public OrderItem getItem(int position) {
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
		mHolder.content = (LinearLayout) convertView
				.findViewById(R.id.order_item_content);
		// convertView.setTag(mHolder);
		// } else {
		// mHolder = (Holder) convertView.getTag();
		// }
		mHolder.goodsStockTV.setText(mOrderItemList.get(position)
				.getGoodsStock());
		mHolder.goodsNameTV
				.setText(mOrderItemList.get(position).getGoodsName());
		mHolder.goodsSpecificationTV.setText(mOrderItemList.get(position)
				.getGoodsSpecification());
		mHolder.goodsCountsET.setText(mOrderItemList.get(position)
				.getGoodsCounts().toString());
		mHolder.goodsCountsET.addTextChangedListener(new MyTextChangedListener(
				"count"));
		mHolder.goodsCountsET.setOnFocusChangeListener(this);
		mHolder.goodsCountsET.setOnKeyListener(this);
		mHolder.goodsCountsET.setTag(position);
		mHolder.goodsUnitTV
				.setText(mOrderItemList.get(position).getGoodsUnit());
		if (mOrderItemList.get(position).getGoodsPrice() != 0) {
			mHolder.goodsPriceET.setText(mOrderItemList.get(position)
					.getGoodsPrice().toString());
		}
		mHolder.goodsPriceET.setOnFocusChangeListener(this);
		mHolder.goodsPriceET.addTextChangedListener(new MyTextChangedListener("price"));
		mHolder.goodsPriceET.setOnKeyListener(this);
		mHolder.goodsPriceUnitTV.setText(mOrderItemList.get(position)
				.getGoodsPriceUnit());
		mHolder.goodsTotalPriceTV.setText(mOrderItemList.get(position)
				.getGoodsTotalPrice().toString()+"￥");
		return convertView;
	}

	class Holder {
		public Holder() {
		}

		LinearLayout content;
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
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			int position = mOrderListView.getSlidePosition();
			// Log.v("test", ""+position+","+et.getId());
			Log.v("test", "" + position + "," + s.toString());
			if (flag.equals("count")) {
				mOrderItemList.get(position)
						.setGoodsCounts(s.toString().trim());
			}
			if (flag.equals("price")) {
				mOrderItemList.get(position).setGoodsPrice(s.toString().trim());
			}
			Log.v("test", "" + position + ","
					+ mOrderItemList.get(position).getGoodsCounts());
			mOrderItemList.get(position).setGoodsTotalPrice();
//			notifyDataSetChanged();
		}

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		Log.v("childId 0", "onFocusChange imm.isActive" + imm.isActive(v));
//		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		View parrent = (View) v.getParent();
		switch (v.getId()) {
		case R.id.goods_counts:
//			if (!hasFocus) {
//				v.setFocusable(true);
				Log.v("childId", "onFocusChange imm.isActive" + imm.isActive(v));
//				 notifyDataSetChanged();
//				 parrent.requestFocus();
				// RelativeLayout wg = (RelativeLayout) v.getParent();
				// // Log.v("childId", ""+wg.getChildAt(1).getId());
				// Toast.makeText(mContext, "有反应", 200).show();
//				Log.v("childId", "onFocusChange " + hasFocus);
//			}
			break;

		default:
			break;
		}
		Log.v("childId", "break isFocusable" + v.isFocusable());
		Log.v("childId",
				"break isFocusableInTouchMode" + v.isFocusableInTouchMode());
		// v.requestFocus();
		// if(!hasFocus){
		// int position = mOrderListView.getSlidePosition();
		// double counts =
		// Double.parseDouble(mOrderItemList.get(position).getGoodsCounts().);
		// mOrderItemList.get().setGoodsTotalPrice(goodsTotalPrice)
		// }

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Log.v("childId",
				"onkeyDown" + keyCode);
		InputMethodManager imm = (InputMethodManager) mContext
		.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(event.getAction() == KeyEvent.ACTION_DOWN){
			Log.v("onkeyDown",
					"onkeyDown imm.isActive() " + imm.isActive());
		}
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK :
			if(imm.isActive())
				notifyDataSetChanged();
			break;
		case KeyEvent.KEYCODE_ESCAPE :
		case KeyEvent.KEYCODE_ENTER :
			notifyDataSetChanged();
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			Log.v("childId",
					"onkeyDown imm.isActive() " + imm.isActive());
			break;
		default:
			break;
		}
		return false;
	}

}
