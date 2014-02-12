package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.CustomerAdapter;
import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.AuthorizedFailedUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 * 
 */
public class CustomerInfoFragment extends Fragment implements OnClickListener {

	private View mView;
	private TextView mTxtView;
	private ListView mListView;
	private RelativeLayout addBtn;
	private List<Customer> customers;
	private ProgressDialog progressDialog;
	private CustomerAdapter customerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_customer_list, container,
				false);
		initView();
		return mView;
	}

	private void initView() {
		mTxtView = (TextView) mView.findViewById(R.id.customer_list_describe);
		String user = "		" + PreferenceUtils.getPreferLoginName(getActivity());
		mTxtView.setText(getResources().getString(
				R.string.customer_list_describe, user));
		mListView = (ListView) mView.findViewById(R.id.customer_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				intent.putExtra("customer", customers.get(position));
				intent.setClass(getActivity(), CustomerInfoMoreActivity.class);
				startActivity(intent);
			}
		});
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);
		addBtn.setOnClickListener(this);
	}

	/**
	 * 绑定用户列表数据
	 */
	private void bindData() {
		customers = new ArrayList<Customer>();
		getCustomerList();
	}

	private void getCustomerList() {
		progressDialog = ProgressDialog.show(getActivity(), "",
				"数据加载中，请稍候....", true, false);
		SaLog.log("token", PreferenceUtils.getPreferToken(getActivity()));
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				AuthorizedFailedUtils.checkReLogin(
						(SalesAssisteantActivity) getActivity(), errorNo);
				Log.v("error", errorNo + strMsg + t.toString());
				progressDialog.dismiss();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				customers = JSON.parseArray(t.toString(), Customer.class);
				Log.v("customer id", customers.get(0).getId());
				customerAdapter = new CustomerAdapter(getActivity(), customers);
				mListView.setAdapter(customerAdapter);
				progressDialog.dismiss();

			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	@Override
	public void onResume() {
		super.onResume();
		bindData();
	}

}