/**
 * 
 */
package net.basilwang.trade;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.entity.AreaProductSku;
import net.basilwang.entity.Customer;
import net.basilwang.entity.Order;
import net.basilwang.entity.OrderProduct;
import net.basilwang.entity.Product;
import net.basilwang.entity.ValidateResult;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.SaLog;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.onKybdsChangeListener;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 * 
 */
public class OrderInfoFragment extends ListFragment implements OnClickListener,
		RemoveListener, OnItemSelectedListener {

	private View mView;
	private SlideCutListView orderListView;
	private TextView receivable;
	private EditText realcollection;
	private Button sureBtn, cancelBtn;
	private RelativeLayout addBtn;
	private OrderAdapter orderAdapter;
	private List<OrderProduct> orderProducts;
	private List<Customer> customers;
	private Spinner customerSpinner;

	private ResizeLayout headerLinearLayout;
	private LinearLayout btnLinearLayout;

	private Customer assignedCustomer = null;

	private ArrayAdapter<String> customerAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater
				.inflate(R.layout.fragment_order_info, container, false);
		initView();
		bindData();
		return mView;
	}

	private void bindData() {
		orderProducts = new ArrayList<OrderProduct>();
		orderAdapter = new OrderAdapter(getActivity(), orderProducts,
				orderListView);
		orderListView.setAdapter(orderAdapter);
		customers = new ArrayList<Customer>();
		getCustomerList();
		// CustomerListUtils.getCustomerList(getActivity(), customers,
		// customerAdapter, customerSpinner, false);
	}

	private void initView() {
		orderListView = (SlideCutListView) mView
				.findViewById(android.R.id.list);
		receivable = (TextView) mView.findViewById(R.id.counts_receivable);
		realcollection = (EditText) mView.findViewById(R.id.counts_real);
		sureBtn = (Button) mView.findViewById(R.id.order_sure_btn);
		cancelBtn = (Button) mView.findViewById(R.id.order_cancel_btn);
		customerSpinner = (Spinner) mView.findViewById(R.id.customer_spinner);
		customerSpinner.setOnItemSelectedListener(this);
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);

		btnLinearLayout = (LinearLayout) mView.findViewById(R.id.order_btn);
		headerLinearLayout = (ResizeLayout) mView.findViewById(R.id.origin);
		setListener();
	}

	public void setListener() {
		orderListView.setRemoveListener(this);
		sureBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		addBtn.setOnClickListener(this);
		customerSpinner.setOnItemSelectedListener(this);
		headerLinearLayout.setOnkbdStateListener(new onKybdsChangeListener() {

			@Override
			public void onKeyBoardStateChange(int state) {
				Log.v("sadsadsadaasdsa", "saddddddddddd");
				EditText onTouchedEditText = orderAdapter
						.getonTouchedEditText();
				switch (state) {
				case ResizeLayout.KEYBOARD_STATE_HIDE:
					btnLinearLayout.setVisibility(View.VISIBLE);
					orderAdapter.onTouchedTag = "";
					refreshRealCounts();
					Toast.makeText(getActivity(), "软键盘隐藏", Toast.LENGTH_SHORT)
							.show();
					break;
				case ResizeLayout.KEYBOARD_STATE_SHOW:
					btnLinearLayout.setVisibility(View.INVISIBLE);
					Toast.makeText(getActivity(), "软键盘弹起", Toast.LENGTH_SHORT)
							.show();
					if (onTouchedEditText != null) {
						onTouchedEditText.requestFocus();
						onTouchedEditText.setSelection(onTouchedEditText
								.getText().length());
					}
					break;
				}
			}
		});
	}

	private void getCustomerList() {
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				ReLoginUtils.authorizedFailed(getActivity(), errorNo);
				Log.v("error", errorNo + strMsg + t.toString());
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				customers = JSON.parseArray(t.toString(), Customer.class);
				ArrayList<String> customerNamelist = new ArrayList<String>();
				customerNamelist.add("请选择");
				for (int i = 0; i < customers.size(); i++) {
					customerNamelist.add(i + 1, customers.get(i).getName());
				}
				customerAdapter = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, customerNamelist);
				customerAdapter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				customerSpinner.setAdapter(customerAdapter);
				customerSpinner.setPrompt("请选择客户");
				if (customers.isEmpty())
					Toast.makeText(getActivity(), "呀，客户列表为空了",
							Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getActivity(), "亲，客户列表获取成功了哦",
							Toast.LENGTH_SHORT).show();

			}

		});
	}

	public void refreshRealCounts() {
		Double totalCounts = 0.0;
		for (int i = 0; i < this.orderProducts.size(); i++) {
			totalCounts += orderProducts.get(i).getTotalPrice();
		}
		this.receivable.setText(totalCounts.toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			orderAdapter.notifyDataSetChanged();
			Intent intent = new Intent(getActivity(),
					ProductInfoMoreActivity.class);

			startActivityForResult(intent, 0);
			refreshRealCounts();
			break;
		case R.id.order_cancel_btn:
			clearAllData();
			break;
		case R.id.order_sure_btn:
			refreshRealCounts();
			if (orderProducts.isEmpty())
				;
			else if (customerSpinner.getSelectedItem().toString().equals("请选择"))
				Toast.makeText(getActivity(), "请选择指定客户", Toast.LENGTH_SHORT)
						.show();
			else if (Double.parseDouble(receivable.getText().toString()) == 0)
				Toast.makeText(getActivity(), "请将订单填写完整", Toast.LENGTH_SHORT)
						.show();
			else if (!isPerOrdercompleted())
				;
			else if (!isRealcollectionLegal()){
				realcollection.setText(receivable.getText());
				realcollection.setSelection(0, realcollection.getText().length());
				Toast.makeText(getActivity(), "实收款不能大于应收款", Toast.LENGTH_SHORT).show();
			}else
				submitOrder();
			break;
		default:
			break;
		}

	}

	private boolean isRealcollectionLegal() {
		String realcollectionStr = realcollection.getText().toString().trim();
		if (realcollectionStr.isEmpty()
				|| Double.parseDouble(realcollectionStr) <= Double
						.parseDouble(receivable.getText().toString()))
			return true;
		return false;
	}

	private void submitOrder() {
		Order order = new Order();
		order.setCustomer(assignedCustomer.getId());
		order.setOrderProducts(orderProducts);
		order.setStringReceivable(receivable.getText().toString());
		order.setStringRealcollection(realcollection.getText().toString());
		String orderJsonString = JSON.toJSONString(order);
		Log.v("order json string", orderJsonString);
		FinalHttp http = new FinalHttp();
		http.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		HttpEntity entity = null;
		try {
			entity = new StringEntity(orderJsonString);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		http.post(StaticParameter.postOrderAdd, entity, "application/json",
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						ReLoginUtils.authorizedFailed(getActivity(), errorNo);
						Log.v("failure", strMsg);
					}

					@Override
					public void onSuccess(Object t) {
						Log.v("success", t.toString());
						ValidateResult result = JSON.parseObject(t.toString(),
								ValidateResult.class);
						Toast.makeText(getActivity(), result.getMessage(),
								Toast.LENGTH_SHORT).show();
						if (result.getSuccess().equals("true")) {
							clearAllData();
						}
						super.onSuccess(t);
					}

				});

	}

	protected void clearAllData() {
		orderProducts.clear();
		refreshRealCounts();
		customerSpinner.setSelection(0);
		Log.v("assignedCustomer name ", assignedCustomer.getName());
		realcollection.setText("");
	}

	private boolean isPerOrdercompleted() {
		for (int i = 0; i < orderProducts.size(); i++) {
			if (orderProducts.get(i).getTotalPrice() == 0) {
				Toast.makeText(
						getActivity(),
						"请将订单 " + orderProducts.get(i).getName() + " "
								+ orderProducts.get(i).getAreaProductSkuName()
								+ " 填写完整", Toast.LENGTH_SHORT).show();
				return false;
			}
		}
		return true;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == getActivity().RESULT_OK) {
			List<Product> selectedProducts = data
					.getParcelableArrayListExtra("selectedProducts");
			for (int i = 0; i < selectedProducts.size(); i++) {
				Product product = selectedProducts.get(i);

				for (int j = 0; j < product.getAreaProductSkuList().size(); j++) {
					OrderProduct orderProduct = new OrderProduct();

					orderProduct.setName(product.getName());
					orderProduct.setUnit(product.getUnit());
					AreaProductSku areaProductSku = product
							.getAreaProductSkuList().get(j);
					orderProduct.setStock(areaProductSku.getAmount() + "");
					orderProduct.setAreaProductSku(areaProductSku.getId());
					orderProduct.setAreaProductSkuName(areaProductSku
							.getProductSku().getName());
					orderProduct.setNullTotalPrice();
					if (orderProducts.contains(orderProduct)) {
						Toast.makeText(
								getActivity(),
								orderProduct.getName() + " "
										+ orderProduct.getAreaProductSkuName()
										+ " 在订单中已存在", Toast.LENGTH_SHORT)
								.show();
					} else {
						orderProducts.add(orderProduct);
					}
				}

			}
			orderAdapter.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void removeItem(RemoveDirection direction, int position) {
		String tip = orderAdapter.getItem(position).getName() + " 订单";
		orderAdapter.remove(position);
		refreshRealCounts();
		switch (direction) {
		case RIGHT:
			Toast.makeText(getActivity(), "向右删除  " + tip, Toast.LENGTH_SHORT)
					.show();
			break;
		case LEFT:
			Toast.makeText(getActivity(), "向左删除  " + tip, Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		assignedCustomer = position == 0 ? null : customers.get(position - 1);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
