package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

import net.basilwang.dao.CustomerListAdapter;
import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.CustomerListUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.SaLog;
import net.basilwang.utils.TaskResult;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author phyllis
 * 
 */
public class CustomerInfoFragment extends Fragment implements OnClickListener {

	private View mView;
	private TextView mTxtView;
	private ListView mListView;
	private RelativeLayout addBtn;
//	private List<String> customerNameList;
	private List<Customer> customers;
	private ProgressDialog progressDialog;
	private static final int OPEN_CUSTOMER_INFO = 101;
	private static final int ADD_CUSTOMER_INFO = 102;
	public static final int RESULT_OK = 1001;
	public static final int RESULT_ERROR = 1002;
	private CustomerListAdapter customerAdapter;

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
		String user = "		"+PreferenceUtils.getPreferLoginName(getActivity());
		mTxtView.setText(getResources().getString(
				R.string.customer_list_describe, user));
		mListView = (ListView) mView.findViewById(R.id.customer_list);
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), CustomerInfoMoreActivity.class);
				startActivityForResult(intent, OPEN_CUSTOMER_INFO);
			}
		});
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);
		addBtn.setOnClickListener(this);
		bindData();
	}

	private void bindData() {
		customers = new ArrayList<Customer>();
//		CustomerListUtils.getCustomerList(getActivity(),customers,customerAdapter,mListView,true);
		getCustomerList();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	private void getCustomerList() {
		progressDialog = ProgressDialog.show(getActivity(), "",
				"数据加载中，请稍候....", true, false);
		AjaxParams params = new AjaxParams();
		SaLog.log("token", PreferenceUtils.getPreferToken(getActivity()));
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				if (errorNo == 401) {
					Toast.makeText(getActivity(), "您的账号异常，请重新登录", Toast.LENGTH_SHORT)
					.show();
					Intent intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					getActivity().startActivity(intent);
					SalesAssisteantActivity.INSTANCE.finish();
					PreferenceUtils.clearData(getActivity());
				}
				Log.v("error", errorNo + strMsg + t.toString());
				progressDialog.dismiss();
			}
			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				customers = JSON.parseArray(t.toString(), Customer.class);
				Log.v("customer id", customers.get(0).getId());
				customerAdapter = new CustomerListAdapter(getActivity(),customers);
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
			startActivityForResult(intent, ADD_CUSTOMER_INFO);
			Toast.makeText(getActivity(), customers.size()+"",
					Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ADD_CUSTOMER_INFO:
			if (resultCode == getActivity().RESULT_OK) {
				Customer customer = data.getParcelableExtra("newCustomer");
				 customers.add(customer);
				 customerAdapter.notifyDataSetChanged();
				Toast.makeText(getActivity(), customer.getName(),
						Toast.LENGTH_SHORT).show();
			}

			break;

		case OPEN_CUSTOMER_INFO:
			break;

		default:
			break;
		}
	}

}