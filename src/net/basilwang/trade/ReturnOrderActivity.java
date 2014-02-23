/**
 * 
 */
package net.basilwang.trade;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.OrderAdapter;
import net.basilwang.entity.AreaProductSku;
import net.basilwang.entity.Customer;
import net.basilwang.entity.Order;
import net.basilwang.entity.OrderProduct;
import net.basilwang.entity.Product;
import net.basilwang.entity.ValidateResult;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.NetworkUtils;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.view.ResizeLayout;
import net.basilwang.view.ResizeLayout.onKybdsChangeListener;
import net.basilwang.view.SearchAutoCompleteTextView;
import net.basilwang.view.SlideCutListView;
import net.basilwang.view.SlideCutListView.RemoveDirection;
import net.basilwang.view.SlideCutListView.RemoveListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 *
 */
public class ReturnOrderActivity extends Activity implements OnClickListener,
		RemoveListener, OnItemClickListener {
	
	private SlideCutListView orderListView;
	private TextView receivable;
	private SearchAutoCompleteTextView searchEditText;
	private Button sureBtn, cancelBtn;
	private OrderAdapter orderAdapter;
	private List<OrderProduct> orderProducts;
	private List<Customer> customers;
	private RelativeLayout addBtn,backBtn;

	private ResizeLayout headerLinearLayout;

	private ArrayAdapter<Customer> customerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_return_order);
		initView();
		bindData();
		Intent intent = getIntent();
		searchEditText.setAssignedCustomer((Customer) intent.getParcelableExtra("customer")); 
		searchEditText.setText(searchEditText.getAssignedCustomer().getName());
		searchEditText.setClearIconVisible(false);
	}

	private void bindData() {
		orderProducts = new ArrayList<OrderProduct>();
		orderAdapter = new OrderAdapter(this, orderProducts,
				orderListView);
		orderListView.setAdapter(orderAdapter);
		customers = new ArrayList<Customer>();
	}

	private void initView() {
		addBtn = (RelativeLayout)findViewById(R.id.goods_title_bar_btn_add);
		backBtn = (RelativeLayout)findViewById(R.id.goods_title_bar_back);
		orderListView = (SlideCutListView) findViewById(android.R.id.list);
		receivable = (TextView) findViewById(R.id.counts_receivable);
		sureBtn = (Button) findViewById(R.id.order_sure_btn);
		cancelBtn = (Button) findViewById(R.id.order_cancel_btn);
		searchEditText = (SearchAutoCompleteTextView) findViewById(R.id.search_edit);
		headerLinearLayout = (ResizeLayout) findViewById(R.id.origin);
		setListener();
	}

	public void setListener() {
		orderListView.setRemoveListener(this);
		addBtn.setOnClickListener(this);
		backBtn.setOnClickListener(this);
		sureBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		searchEditText.setFocusable(false);
		headerLinearLayout.setOnkbdStateListener(new onKybdsChangeListener() {

			@Override
			public void onKeyBoardStateChange(int state) {
				if (state >= 0) {
					Log.v("state", state + "");
					SlideCutListView.setStaticPosition(-2);
				} else if (state == -2) {
					Log.v("state", state + "");
					SlideCutListView.setStaticPosition(-1);
					orderAdapter.notifyDataSetChanged();
					orderListView.refreshDrawableState();
				} else {
					Log.v("state", state + "");
				}
				refreshRealCounts();
				Log.v("state out", state + "");
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
		case R.id.goods_title_bar_back:
			finish();
			break;
		case R.id.goods_title_bar_btn_add:
			orderAdapter.notifyDataSetChanged();
			refreshRealCounts();
			if (NetworkUtils.isConnect(this)) {
				Intent intent = new Intent(this,
						ProductInfoMoreActivity.class);
				startActivityForResult(intent, 0);
			}
			break;
		case R.id.order_cancel_btn:
			AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle("确认清空订单列表？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									clearAllData();
								}
							}).create();
			dialog.setCanceledOnTouchOutside(true);
			if (!orderProducts.isEmpty())
				dialog.show();
			break;
		case R.id.order_sure_btn:
			refreshRealCounts();
			checkOrderIslegal();
			break;
		default:
			break;
		}

	}

	private void checkOrderIslegal() {
		if (orderProducts.isEmpty())
			;
		else if (Double.parseDouble(receivable.getText().toString()) == 0)
			Toast.makeText(this, "请将订单填写完整", Toast.LENGTH_SHORT)
					.show();
		else if (!isPerOrdercompleted())
			;
		else if (NetworkUtils.isConnect(this)) {
			AlertDialog dialog = new AlertDialog.Builder(this)
					.setTitle("确认订单无误？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									submitOrder();
								}
							}).create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		}
	}

	private void submitOrder() {
		Order order = new Order();
		order.setCustomer(searchEditText.getAssignedCustomer().getId());
		order.setOrderProducts(orderProducts);
		order.setStringReceivable(receivable.getText().toString());
		String orderJsonString = JSON.toJSONString(order);
		Log.v("order json string", orderJsonString);
		FinalHttp http = new FinalHttp();
		http.addHeader("X-Token", PreferenceUtils.getPreferToken(this));
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
						ReLoginUtils.authorizedFailed(ReturnOrderActivity.this, errorNo);
						Toast.makeText(ReturnOrderActivity.this, "订单提交失败，稍后重试",
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(Object t) {
						Log.v("success", t.toString());
						ValidateResult result = JSON.parseObject(t.toString(),
								ValidateResult.class);
						Toast.makeText(ReturnOrderActivity.this, result.getMessage(),
								Toast.LENGTH_SHORT).show();
						if (result.getSuccess().equals("true")) {
							clearAllData();
						}
						super.onSuccess(t);
					}

				});

	}

	protected void clearAllData() {
		searchEditText.setText("");
		orderProducts.clear();
		refreshRealCounts();
		orderAdapter.notifyDataSetChanged();
	}

	private boolean isPerOrdercompleted() {
		for (int i = 0; i < orderProducts.size(); i++) {
			if (orderProducts.get(i).getTotalPrice() == 0) {
				Toast.makeText(
						this,
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
		if (resultCode == this.RESULT_OK) {
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
								this,
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
	public void removeItem(final RemoveDirection direction, final int position) {
		AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle(
						"删除订单 "
								+ orderAdapter.getItem(position).getName()
								+ " "
								+ orderAdapter.getItem(position)
										.getAreaProductSkuName() + " ?")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String tip = orderAdapter.getItem(position).getName()
								+ " 订单";
						orderAdapter.remove(position);
						refreshRealCounts();
						switch (direction) {
						case RIGHT:
							Toast.makeText(ReturnOrderActivity.this, "向右删除  " + tip,
									Toast.LENGTH_SHORT).show();
							break;
						case LEFT:
							Toast.makeText(ReturnOrderActivity.this, "向左删除  " + tip,
									Toast.LENGTH_SHORT).show();
							break;

						default:
							break;
						}
					}
				}).create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		searchEditText.setAssignedCustomer(customerAdapter.getItem(position));
		Toast.makeText(this,
				searchEditText.getAssignedCustomer().getName(),
				Toast.LENGTH_SHORT).show();
	}


}
