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
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Basilwang
 *
 */
public class OrderAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<OrderItem> mOrderItemList; 
	private int mScreenWidth;
	
	public OrderAdapter(Context context,List<OrderItem> orderItemList,int screenWidth){
		this.mContext = context;
		this.mOrderItemList = orderItemList;
		this.mScreenWidth = screenWidth;
		
	}

	@Override
	public int getCount() {
		return mOrderItemList.size();
	}

	@Override
	public Object getItem(int position) {
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
			mHolder.mTextView = (TextView)convertView.findViewById(R.id.order_item_string);
			mHolder.content = (LinearLayout)convertView.findViewById(R.id.order_item_content);
			LayoutParams lp =(LayoutParams) mHolder.content.getLayoutParams();
			lp.width = mScreenWidth;
			convertView.setTag(mHolder);
		}else{
			mHolder = (Holder)convertView.getTag();
		}
		mHolder.mTextView.setText(mOrderItemList.get(position).getmString());
		return convertView;
	}
	
	private static class Holder{
		private LinearLayout content;
		private TextView mTextView;
	}

}
