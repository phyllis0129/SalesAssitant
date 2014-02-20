package net.basilwang.dao;

import java.util.List;

import net.basilwang.entity.TransactionRecords;
import net.basilwang.trade.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TransactionRecordsAdapter extends BaseExpandableListAdapter {
	private List<TransactionRecords> groupArray; // adapter数据源
	private Context mContext;
	private int selectItem = 0;
	private int selectItemChild = 0;
	private int currentPosition;

	public TransactionRecordsAdapter(Context context,
			List<TransactionRecords> parentArray) {
		this.mContext = context;
		this.groupArray = parentArray;
	}

	public int getGroupCount() {
		return groupArray.size();
	}

	public int getChildrenCount(int groupPosition) {

		return groupArray.get(groupPosition).getOrderProducts().size();

	}

	public Object getGroup(int groupPosition) {

		return groupArray.get(groupPosition);

	}

	public Object getChild(int groupPosition, int childPosition) {
		if (groupPosition >= 2 && groupPosition <= groupArray.size() - 1 + 2) {
			groupPosition = groupPosition - 2;
			return groupArray.get(groupPosition).getOrderProducts()
					.get(childPosition);
		}
		return null;
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		TransactionGroupView groupViewCache = null;

		// 使用convertView和holder(viewCache)提高效率
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.transaction_records_item, null);
			groupViewCache = new TransactionGroupView();
			groupViewCache.gName = (TextView) convertView
					.findViewById(R.id.t_cname);
			groupViewCache.gTime = (TextView) convertView
					.findViewById(R.id.t_time);
			groupViewCache.gReceivable = (TextView) convertView
					.findViewById(R.id.t_receivable);
			groupViewCache.gRealcollection = (TextView) convertView
					.findViewById(R.id.t_realcollection);
			convertView.setTag(groupViewCache);
		} else {
			groupViewCache = (TransactionGroupView) convertView.getTag();
		}
		groupViewCache.gName.setText(groupArray.get(groupPosition)
				.getCustomername());
		groupViewCache.gTime.setText(groupArray.get(groupPosition)
				.getOrdertime());
		groupViewCache.gReceivable.setText(groupArray.get(groupPosition)
				.getReceivable() + "元");
		groupViewCache.gRealcollection.setText(groupArray.get(groupPosition)
				.getRealcollection() + "元");
		return convertView;

	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TransactionItemView itemView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.transaction_records_item_child, null);
			itemView = new TransactionItemView();
			itemView.amount = (TextView) convertView
					.findViewById(R.id.t_child_amount);
			itemView.perprice = (TextView) convertView
					.findViewById(R.id.t_child_perprice);
			itemView.productname = (TextView) convertView
					.findViewById(R.id.t_child_name);
			itemView.productskuname = (TextView) convertView
					.findViewById(R.id.t_child_sku);
			itemView.totalprice = (TextView) convertView
					.findViewById(R.id.t_child_totalPrice);
			convertView.setTag(itemView);
		} else {
			itemView = (TransactionItemView) convertView.getTag();
		}
		itemView.amount.setText(groupArray.get(groupPosition)
				.getOrderProducts().get(childPosition).getAmount()
				+ groupArray.get(groupPosition).getOrderProducts()
						.get(childPosition).getProductunit());
		itemView.perprice.setText(groupArray.get(groupPosition)
				.getOrderProducts().get(childPosition).getPerprice()
				+ "元");
		itemView.productname.setText(groupArray.get(groupPosition)
				.getOrderProducts().get(childPosition).getProductname());
		itemView.productskuname.setText(groupArray.get(groupPosition)
				.getOrderProducts().get(childPosition).getProductskuname());
		itemView.totalprice.setText(groupArray.get(groupPosition)
				.getOrderProducts().get(childPosition).getTotalprice()
				+ "元");
		return convertView;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public int getSelectItem() {
		return selectItem;
	}

	public void setSelectItem(int selectItem) {
		this.selectItem = selectItem;
	}

	public int getSelectItemChild() {
		return selectItemChild;
	}

	public void setSelectItemChild(int selectItemChild) {
		this.selectItemChild = selectItemChild;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}
}

class TransactionGroupView {
	public TextView gName;
	public TextView gTime;
	public TextView gReceivable;
	public TextView gRealcollection;
}

class TransactionItemView {
	// "Amount": 12,
	// "Perprice": 30.0,
	// "TotalPrice": 600.0,
	// "ProductName": "灭虫123",
	// "ProductSkuName": "100*20",
	public TextView amount;// 数量
	public TextView perprice;// 单价
	public TextView totalprice;// 总价
	public TextView productname;// 产品名称
	public TextView productskuname;// 产品规格
}
