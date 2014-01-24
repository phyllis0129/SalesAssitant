/**
 * 
 */
package net.basilwang.dao;

import java.util.List;

import net.basilwang.trade.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

/**
 * @author Basilwang
 * 
 */
public class OrderAdapter extends BaseAdapter implements OnClickListener {

	private Context mContext;
	private int mShowPosition = -1;
	private List<OrderItem> mOrderItemList;
	private int mScreenWidth;

	public OrderAdapter(Context context, List<OrderItem> orderItemList,
			int screenWidth) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		Holder mHolder = null;
		if (convertView == null) {
			mHolder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.order_item, null);
			mHolder.orderItemScrollView = (HorizontalScrollView) convertView
					.findViewById(R.id.order_item_scrollview);
			mHolder.content = convertView.findViewById(R.id.order_item_content);
			mHolder.mTextView = (TextView) convertView
					.findViewById(R.id.order_item_string);
			mHolder.action = convertView.findViewById(R.id.order_item_action);
			mHolder.deleteBtn = (Button) convertView
					.findViewById(R.id.order_item_btn_delete);
			LayoutParams lp = (LayoutParams) mHolder.content.getLayoutParams();
			lp.width = mScreenWidth;
			mHolder.orderItemScrollView.setTag(position);
			mHolder.deleteBtn.setTag(position);
			convertView.setTag(position);
			convertView.setTag(R.id.tag_first,mHolder);
		} else {
			mHolder = (Holder) convertView.getTag(R.id.tag_first);
		}
//		final int showPosition = (Integer) convertView.getTag();
		mHolder.orderItemScrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
//				Log.v("convertView onTouch ", ""+v);
				Log.v("convertView onTouch position", ""+position);
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					Holder viewHolder = (Holder) v.getTag(R.id.tag_first);
					Log.v("convertView onTouch TextView", viewHolder.mTextView.getText().toString());
					int scrollX = ((HorizontalScrollView) v).getScrollX();
					int actionW = viewHolder.action.getWidth();
					if (scrollX < actionW / 2) {
						((HorizontalScrollView) v).smoothScrollTo(0, 0);
					} else {
						Log.v("convertView onTouch up position", ""+position);
						Log.v("convertView onTouch up mShowPosition start", ""+mShowPosition);
						mShowPosition = (Integer) v.getTag();
						Log.v("convertView onTouch up mShowPosition end", ""+mShowPosition);
//						if(position == mShowPosition){
							Log.v("convertView onTouch TextView 2", viewHolder.mTextView.getText().toString());
						((HorizontalScrollView) v).smoothScrollTo(actionW,
								0);
						Log.v("convertView onTouch scrollview position", viewHolder.orderItemScrollView.getTag().toString());
//						}
					}
					notifyDataSetChanged();
					break;
				}
				return false;
			}
		});
		if (mHolder.orderItemScrollView.getScrollX() != 0
				&& position != mShowPosition) {
			mHolder.orderItemScrollView.scrollTo(0, 0);
		}
		mHolder.mTextView.setText(mOrderItemList.get(position).getmString());

		mHolder.deleteBtn.setOnClickListener(this);

		return convertView;
	}

	private static class Holder {
		private HorizontalScrollView orderItemScrollView;
		private View content;
		private TextView mTextView;
		private View action;
		private Button deleteBtn;
	}

	@Override
	public void onClick(View v) {
		Log.v("btn onclick mShowPosition start", ""+mShowPosition);
		int position = (Integer) v.getTag();
		Log.v("btn onclick Position", ""+position);
		switch (v.getId()) {
		case R.id.order_item_btn_delete:
			mOrderItemList.remove(position);
			break;

		default:
			break;
		}
		mShowPosition = -1;
		notifyDataSetChanged();
		Log.v("btn onclick mShowPosition end", ""+mShowPosition);
	}

}
