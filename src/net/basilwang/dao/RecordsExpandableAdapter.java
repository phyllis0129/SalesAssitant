package net.basilwang.dao;

import java.util.List;

import net.basilwang.entity.Record;
import net.basilwang.trade.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class RecordsExpandableAdapter extends BaseExpandableListAdapter {
	private List<Record> groupArray; // adapter数据源
	private Context mContext;
	private int selectItem = 0;
	private int selectItemChild = 0;
	private int currentPosition;

	public RecordsExpandableAdapter(Context context, List<Record> parentArray) {
		this.mContext = context;
		this.groupArray = parentArray;
	}

	public int getGroupCount() {
		return groupArray.size();
	}

	public int getChildrenCount(int groupPosition) {

		return groupArray.get(groupPosition).getProductSkus().size();

	}

	public Object getGroup(int groupPosition) {

		return groupArray.get(groupPosition);

	}

	public Object getChild(int groupPosition, int childPosition) {
		if (groupPosition >= 2 && groupPosition <= groupArray.size() - 1 + 2) {
			groupPosition = groupPosition - 2;
			return groupArray.get(groupPosition).getProductSkus()
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
		RecordGroupView recordGroupViewCache = null;

		// 使用convertView和holder(viewCache)提高效率
		if (convertView == null) {

			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.record_group_item, null);
			recordGroupViewCache = new RecordGroupView();
			recordGroupViewCache.pName = (TextView) convertView
					.findViewById(R.id.record_group_child_name);
			recordGroupViewCache.pAmount = (TextView) convertView
					.findViewById(R.id.record_group_child_amount);
			recordGroupViewCache.pSum = (TextView) convertView
					.findViewById(R.id.record_group_child_sum);
			convertView.setTag(recordGroupViewCache);
		} else {
			recordGroupViewCache = (RecordGroupView) convertView.getTag();
		}
		recordGroupViewCache.pName.setText(groupArray.get(groupPosition)
				.getProductName());
		recordGroupViewCache.pAmount.setText(groupArray.get(groupPosition)
				.getTotalAmout()
				+ groupArray.get(groupPosition).getProductUnit());
		recordGroupViewCache.pSum.setText(groupArray.get(groupPosition)
				.getTotalPrice() + "元");
		return convertView;

	}

	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		RecordItemView recordItemView = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.record_group_child_item, null);
			recordItemView = new RecordItemView();
			recordItemView.mName = (TextView) convertView
					.findViewById(R.id.record_group_child_name);
			recordItemView.mAmount = (TextView) convertView
					.findViewById(R.id.record_group_child_amount);
			recordItemView.mSum = (TextView) convertView
					.findViewById(R.id.record_group_child_sum);
			convertView.setTag(recordItemView);
		} else {
			recordItemView = (RecordItemView) convertView.getTag();
		}
		recordItemView.mName.setText(groupArray.get(groupPosition)
				.getProductSkus().get(childPosition).getProduSkuName());
		recordItemView.mAmount.setText(groupArray.get(groupPosition)
				.getProductSkus().get(childPosition).getTotalAmout()
				+ groupArray.get(groupPosition).getProductUnit());
		recordItemView.mSum.setText(groupArray.get(groupPosition)
				.getProductSkus().get(childPosition).getTotalPrice()
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

class RecordGroupView {
	public TextView pName;
	public TextView pSum;
	public TextView pAmount;
}

class RecordItemView {
	public TextView mName;
	public TextView mSum;
	public TextView mAmount;
}
