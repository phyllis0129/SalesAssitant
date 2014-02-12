/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.params.HttpParams;

import net.basilwang.dao.ProductAdapter;
import net.basilwang.dao.ProductAdapter.ViewHolder;
import net.basilwang.entity.AreaProductSku;
import net.basilwang.entity.Product;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.AuthorizedFailedUtils;
import net.basilwang.utils.PreferenceUtils;
import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.TextView;
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
				AuthorizedFailedUtils.checkReLogin(ProductInfoMoreActivity.this, errorNo);
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
				productListView.setAdapter(new ProductAdapter(
						ProductInfoMoreActivity.this, products));
				// productListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
		showProductSkuDialog(position);
		ViewHolder _ViewHolder = (ViewHolder) parent.getTag();
	}

	private void showProductSkuDialog(int position) {
		LinearLayout linearLayoutMain = new LinearLayout(this);// 自定义一个布局文件
		linearLayoutMain.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		ListView listView = new ListView(this);// this为获取当前的上下文
		listView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		listView.setFadingEdgeLength(0);
		listView.setCacheColorHint(0);

		linearLayoutMain.addView(listView);// 往这个布局中加入listview


		final AlertDialog dialog = new AlertDialog.Builder(this)
				.setTitle("选择产品规格").setView(linearLayoutMain)// 在这里把写好的这个listview的布局加载dialog中
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				}).setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create();
		dialog.setCanceledOnTouchOutside(false);// 使除了dialog以外的地方不能被点击

		getProductSkuData(listView, position,dialog);

		listView.setOnItemClickListener(new OnItemClickListener() {// 响应listview中的item的点击事件

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CheckBox cb = (CheckBox)arg1.findViewById(R.id.dialog_item_cb);
				cb.toggle();
			}
		});
	}

	private void getProductSkuData(final ListView listView, int position, final AlertDialog dialog) {
//		final ProgressDialog progressDialog = ProgressDialog.show(this, null,
//				"数据加载中，请稍候....");
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
						AuthorizedFailedUtils.checkReLogin(
								ProductInfoMoreActivity.this, errorNo);
//						progressDialog.dismiss();
						//TODO token 验证失败的情况
					}

					@Override
					public void onSuccess(Object t) {
						Log.v("success", t.toString());
						areaProductSkus = JSON.parseArray(t.toString(),
								AreaProductSku.class);
						List<Map<String, String>> areaProductList = new ArrayList<Map<String,String>>();
						for(int i = 0;i<areaProductSkus.size();i++){
							Map<String, String> areaProductSkuMap = new HashMap<String, String>();
							areaProductSkuMap.put("name", areaProductSkus.get(i).getProductSku().getName());
							areaProductSkuMap.put("amount", "库存余量 "+areaProductSkus.get(i).getAmount());
							areaProductList.add(areaProductSkuMap);
						}
						listView.setAdapter(new SimpleAdapter(ProductInfoMoreActivity.this, areaProductList, R.layout.productsku_dialog_item, new String[] { "name","amount" },
								 new int[] { R.id.dialog_item_name,R.id.dialog_item_amount }));
//						progressDialog.dismiss();
						dialog.show();
						super.onSuccess(t);
					}

				});
	}
}
