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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Basilwang
 *
 */
public class OrderAdapter extends BaseAdapter implements TextWatcher, OnFocusChangeListener {
	
	private Context mContext;
	private List<OrderItem> mOrderItemList; 
	private SlideCutListView mOrderListView;
	
	public OrderAdapter(Context context,List<OrderItem> orderItemList, SlideCutListView orderListView){
		this.mContext = context;
		this.mOrderItemList = orderItemList;
		this.mOrderListView = orderListView;
		
	}
	
	public void remove(int position){
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
		Holder mHolder = null;
		if(convertView == null){
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.order_item,null);
			mHolder.goodsStockTV = (TextView)convertView.findViewById(R.id.goods_stock);
			mHolder.goodsNameTV = (TextView)convertView.findViewById(R.id.goods_name);
			mHolder.goodsSpecificationTV = (TextView)convertView.findViewById(R.id.goods_Specification);
			mHolder.goodsCountsET = (EditText)convertView.findViewById(R.id.goods_counts);
			mHolder.goodsUnitTV = (TextView)convertView.findViewById(R.id.goods_unit);
			mHolder.goodsPriceET = (EditText)convertView.findViewById(R.id.goods_price);
			mHolder.goodsPriceUnitTV = (TextView)convertView.findViewById(R.id.price_unit);
			mHolder.content = (LinearLayout)convertView.findViewById(R.id.order_item_content);
			convertView.setTag(mHolder);
		}else{
			mHolder = (Holder)convertView.getTag();
		}
		mHolder.goodsStockTV.setText(mOrderItemList.get(position).getGoodsStock());
		mHolder.goodsNameTV.setText(mOrderItemList.get(position).getGoodsName());
		mHolder.goodsSpecificationTV.setText(mOrderItemList.get(position).getGoodsSpecification());
		mHolder.goodsCountsET.addTextChangedListener(this);
		mHolder.goodsUnitTV.setText(mOrderItemList.get(position).getGoodsUnit());
		mHolder.goodsPriceET.setOnFocusChangeListener(this);
		mHolder.goodsPriceUnitTV.setText(mOrderItemList.get(position).getGoodsPriceUnit());
		return convertView;
	}
	
	private static class Holder{
		private LinearLayout content;
		private TextView goodsStockTV;
		private TextView goodsNameTV;
		private TextView goodsSpecificationTV;
		private EditText goodsCountsET;
		private TextView goodsUnitTV;
		private EditText goodsPriceET;
		private TextView goodsPriceUnitTV;
		private TextView goodsTotalPriceTV;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(!hasFocus){
			int position = mOrderListView.getSlidePosition();
			double counts = Double.parseDouble(mOrderItemList.get(position).getGoodsCounts().);
			mOrderItemList.get().setGoodsTotalPrice(goodsTotalPrice)
		}
		
	}

}
