package net.basilwang.trade;

import java.util.List;

import net.basilwang.dao.TransactionRecordsAdapter;
import net.basilwang.entity.TransactionRecords;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author 严
 * 
 *         2014-2-13
 * 
 *         交易记录
 */
public class TransactionRecordsFragment extends Fragment implements
		OnClickListener {

	private View mView;
	private ExpandableListView eListView;
	private TransactionRecordsAdapter tAdapter;
	private int expandFlag = -1;
	private ProgressDialog mDialog;
	private Context mContext;
	private List<TransactionRecords> tRecords;
	private int pageNo = 1;
	private Button btn_page_up, btn_page_down;
	private TextView tv_pageNo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_transaction_records,
				container, false);
		mContext = getActivity();
		findViews();
		init();
		getTransactionRecords(pageNo);
		return mView;
	}

	private void init() {

		btn_page_down.setOnClickListener(this);
		btn_page_up.setOnClickListener(this);
		tv_pageNo.setText("第" + pageNo + "页");

		// 二级分类关闭的触发事件
		eListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				tAdapter.notifyDataSetChanged();
			}
		});

		eListView.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView listView, View v,
					int groupPosition, long id) {
				if (expandFlag == -1) {
					// 展开被选的group
					eListView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					eListView.setSelectedGroup(groupPosition);
					expandFlag = groupPosition;

					tAdapter.setSelectItemChild(-1);
				} else if (expandFlag == groupPosition) {
					eListView.collapseGroup(expandFlag);
					expandFlag = -1;
				} else {
					eListView.collapseGroup(expandFlag);
					// 展开被选的group
					eListView.expandGroup(groupPosition);
					// 设置被选中的group置于顶端
					eListView

					.setSelectedGroup(groupPosition);
					expandFlag = groupPosition;

					tAdapter.setSelectItemChild(-1);
				}

				return true;
			}
		});
	}

	private void findViews() {
		eListView = (ExpandableListView) mView
				.findViewById(R.id.transaction_records_list);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		RelativeLayout addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.INVISIBLE);
		btn_page_down = (Button) mView.findViewById(R.id.page_down);
		btn_page_up = (Button) mView.findViewById(R.id.page_up);
		tv_pageNo = (TextView) mView.findViewById(R.id.pageNo);
	}

	private void getTransactionRecords(final int no) {
		mDialog = ProgressDialog.show(mContext, null, "正在读取...", false, false);
		final FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(mContext));
		fh.get(StaticParameter.getTransactionRecords + String.valueOf(no),
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Toast.makeText(mContext, "读取记录失败，请稍后再试！" + strMsg,
								Toast.LENGTH_SHORT).show();
						SaLog.log("Transaction records", strMsg);
						mDialog.dismiss();
					}

					@Override
					public void onSuccess(Object t) {
						super.onSuccess(t);
						SaLog.log("TranssactionRecords", t.toString());
						tRecords = JSON.parseArray(t.toString(),
								TransactionRecords.class);
						if (t.toString().equals("[]")) {
							Toast.makeText(mContext, "已经是最后一页",
									Toast.LENGTH_SHORT).show();
							pageNo--;
						} else if (tRecords != null) {
							tAdapter = new TransactionRecordsAdapter(mContext,
									tRecords);
							eListView.setAdapter(tAdapter);
							tv_pageNo.setText("第" + no + "页");
						} else {
							Toast.makeText(mContext, "未能成功读取！",
									Toast.LENGTH_SHORT).show();
						}
						mDialog.dismiss();
					}
				});
	}

	@Override
	public void onClick(View v) {
		if (NetworkUtils.isConnect(mContext)) {
			switch (v.getId()) {
			case R.id.page_down:
				getTransactionRecords(++pageNo);
				break;
			case R.id.page_up:
				if (pageNo > 1) {
					getTransactionRecords(--pageNo);
				} else {
					Toast.makeText(mContext, "已经是第一页", Toast.LENGTH_SHORT)
							.show();
				}
				break;

			default:
				break;
			}
		}

	}
}
