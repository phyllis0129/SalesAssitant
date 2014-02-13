/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.basilwang.dao.ProductAdapter;
import net.basilwang.entity.AreaProductSku;
import net.basilwang.entity.Product;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.PreferenceUtils;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author Basilwang
 * 
 */
public class ProductInfoMoreActivity extends Activity implements
		OnClickListener, OnItemClickListener {

	private RelativeLayout backBtn, sureBtn;
	private ListView productListView;
	private ProgressDialog progressDialog;
	private List<Product> products;
	private ProductAdapter productAdapter;
	private List<AreaProductSku> areaProductSkus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_info);
		initView();
	}

	private void initView() {
		backBtn = (RelativeLayout) findViewById(R.id.goods_title_bar_back);
		backBtn.setOnClickListener(this);
		sureBtn = (RelativeLayout) findViewById(R.id.goods_title_bar_btn_sure);
		sureBtn.setOnClickListener(this);
		productListView = (ListView) findViewById(R.id.goods_list);
		productListView.setOnItemClickListener(this);
		bindData();
	}

	private void bindData() {
		progressDialog = ProgressDialog.show(this, null, "数据加载中，请稍候....");
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.addHeader("X-Token", PreferenceUtils.getPreferToken(this));
		finalHttp.get(StaticParameter.getProduct, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Log.v("error", strMsg);
				ReLoginUtils.authorizedFailed(
						ProductInfoMoreActivity.this, errorNo);
				Toast.makeText(ProductInfoMoreActivity.this, "数据获取失败，稍后重试",
						Toast.LENGTH_SHORT).show();
				ProductInfoMoreActivity.this.finish();
				progressDialog.dismiss();

			}

			@Override
			public void onSuccess(Object t) {
				Log.v("product", t.toString());
				products = JSON.parseArray(t.toString(), Product.class);
				List<String> productNames = new ArrayList<String>();
				for (int i = 0; i < products.size(); i++) {
					productNames.add(products.get(i).getName());
				}
				productAdapter = new ProductAdapter(
						ProductInfoMoreActivity.this, products);
				productListView.setAdapter(productAdapter);
				Log.v("product name", products.get(0).getName());
				Log.v("product id", products.get(0).getId());
				progressDialog.dismiss();
				super.onSuccess(t);
			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goods_title_bar_back:
			this.finish();
			break;
		case R.id.goods_title_bar_btn_sure:
			Intent intent = new Intent();
			// TODO
			ArrayList<Product> selectedProducts = new ArrayList<Product>();
			for (int i = 0; i < products.size(); i++) {
				if(ProductAdapter.getIsSelected().get(i))
					selectedProducts.add(products.get(i));
			}
			Log.v("products json", JSON.toJSONString(selectedProducts));
			intent.putParcelableArrayListExtra("selectedProducts", selectedProducts);
			this.setResult(RESULT_OK, intent);
			this.finish();
			break;
		default:
			break;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		initDialogView(position);
	}

	private void initDialogView(int position) {
		LinearLayout linearLayoutMain = new LinearLayout(this);// 自定义一个布局文件
		linearLayoutMain.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ListView listView = new ListView(this);// this为获取当前的上下文
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		listView.setFadingEdgeLength(0);
		listView.setCacheColorHint(0);

		// 往这个布局中加入listview
		linearLayoutMain.addView(listView);

		showProductSkuDialog(position, linearLayoutMain, listView);
	}

	private void showProductSkuDialog(final int position,
			LinearLayout linearLayoutMain, ListView listView) {
		final SparseBooleanArray isCheckBoxSelectedArray = new SparseBooleanArray();
		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("选择产品规格").setView(linearLayoutMain)
				// 在这里把写好的这个listview的布局加载dialog中
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						products.get(position).getAreaProductSkuList().clear();
						for (int i = 0; i < isCheckBoxSelectedArray.size(); i++) {
							if (isCheckBoxSelectedArray.get(i))
								products.get(position).getAreaProductSkuList()
										.add(areaProductSkus.get(i));
						}
						ProductAdapter.getIsSelected().put(
								position,
								products.get(position).getAreaProductSkuList()
										.size() > 0);
						productAdapter.notifyDataSetChanged();

					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);// 使除了dialog以外的地方不能被点击
		getProductSkuData(listView, position, dialog, isCheckBoxSelectedArray);
		listView.setOnItemClickListener(new OnItemClickListener() {// 响应listview中的item的点击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CheckBox cb = (CheckBox) arg1.findViewById(R.id.dialog_item_cb);
				cb.toggle();
				isCheckBoxSelectedArray.put(arg2, cb.isChecked());
			}
		});
	}

	private void getProductSkuData(final ListView listView, final int position,
			final AlertDialog dialog, final SparseBooleanArray isSelectedArray) {
		FinalHttp finalHttp = new FinalHttp();
		finalHttp.addHeader("X-Token", PreferenceUtils.getPreferToken(this));
		AjaxParams params = new AjaxParams();
		params.put("id", products.get(position).getId());
		finalHttp.get(StaticParameter.getProductSku, params,
				new AjaxCallBack<Object>() {

					@Override
					public void onFailure(Throwable t, int errorNo,
							String strMsg) {
						super.onFailure(t, errorNo, strMsg);
						Log.v("error", strMsg);
						ReLoginUtils.authorizedFailed(
								ProductInfoMoreActivity.this, errorNo);
					}

					@Override
					public void onSuccess(Object t) {
						Log.v("success", t.toString());
						areaProductSkus = JSON.parseArray(t.toString(),
								AreaProductSku.class);
						List<Map<String, Object>> areaProductList = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < areaProductSkus.size(); i++) {
							Map<String, Object> areaProductSkuMap = new HashMap<String, Object>();
							areaProductSkuMap.put("name", areaProductSkus
									.get(i).getProductSku().getName());
							areaProductSkuMap.put("amount", "库存余量 "
									+ areaProductSkus.get(i).getAmount());
							areaProductSkuMap.put(
									"isChecked",
									products.get(position)
											.getAreaProductSkuList()
											.contains(areaProductSkus.get(i)));
							isSelectedArray.put(
									i,
									products.get(position)
											.getAreaProductSkuList()
											.contains(areaProductSkus.get(i)));
							areaProductList.add(areaProductSkuMap);
						}
						listView.setAdapter(new SimpleAdapter(
								ProductInfoMoreActivity.this, areaProductList,
								R.layout.productsku_dialog_item, new String[] {
										"name", "amount", "isChecked" },
								new int[] { R.id.dialog_item_name,
										R.id.dialog_item_amount,
										R.id.dialog_item_cb }));
						dialog.show();
						super.onSuccess(t);
					}

				});
	}
}
