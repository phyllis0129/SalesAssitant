/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.entity.Customer;
import net.basilwang.utils.TaskResult;
import android.R.integer;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private List<String> customerList;
	private ProgressDialog progressDialog;
	private static final int OPEN_CUSTOMER_INFO = 101;
	private static final int ADD_CUSTOMER_INFO = 102;
	public static final int RESULT_OK = 1001;
	public static final int RESULT_ERROR = 1002;
	private ArrayAdapter<String> customerAdapter;

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
		String user = "		100982";
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
		new InitTask().execute();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	private List<String> getCustomerList() {
		customerList = new ArrayList<String>();
		customerList.add("王先生");
		customerList.add("李先生");
		customerList.add("张先生");
		return customerList;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
			startActivityForResult(intent, ADD_CUSTOMER_INFO);
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case ADD_CUSTOMER_INFO:
			if (resultCode == RESULT_OK) {
				Customer customer = data.getParcelableExtra("newCustomer");

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

	private class InitTask extends AsyncTask<String, integer, TaskResult> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog = ProgressDialog.show(getActivity(), "",
					"数据加载中，请稍候....", true, false);
		}

		@Override
		protected TaskResult doInBackground(String... params) {
			customerList = getCustomerList();
			return TaskResult.OK;
		}

		@Override
		protected void onPostExecute(TaskResult result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			switch (result) {
			case OK:
				// mListView.setAdapter(new ArrayAdapter<String>(getActivity(),
				// android.R.layout.simple_expandable_list_item_1,
				// customerList));
				customerAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_expandable_list_item_1,
						customerList);
				mListView.setAdapter(customerAdapter);
				break;

			case FAILED:

				break;

			default:
				break;
			}
		}

	};

}