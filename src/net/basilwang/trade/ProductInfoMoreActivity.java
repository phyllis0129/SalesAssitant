/**
 * 
 */
package net.basilwang.trade;

import java.util.ArrayList;
import java.util.List;

import net.basilwang.dao.ProductAdapter;
import net.basilwang.dao.ProductAdapter.ViewHolder;
import net.basilwang.entity.Product;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.PreferenceUtils;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

/**
 * @author Basilwang
 * 
 */
public class ProductInfoMoreActivity extends Activity implements
		OnClickListener,OnItemClickListener {

	private RelativeLayout backBtn, sureBtn;
	private ListView productListView;
	private ProgressDialog progressDialog;
	private List<Product> products;

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
		finalHttp.get(StaticParameter.getGoods, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Log.v("error", strMsg);
				Toast.makeText(ProductInfoMoreActivity.this, "数据获取失败，稍后重试",
						Toast.LENGTH_SHORT).show();
				ProductInfoMoreActivity.this.finish();
				progressDialog.dismiss();

			}

			@Override
			public void onSuccess(Object t) {
				Log.v("product", t.toString());
				products = JSON.parseArray(t.toString(), Product.class);
				JSONArray jsonA= JSON.parseArray(t.toString());
				
				List<String> productNames = new ArrayList<String>();
				for (int i = 0; i < products.size(); i++) {
					productNames.add(products.get(i).getName());
				}
				productListView
						.setAdapter(new ProductAdapter(ProductInfoMoreActivity.this, products));
//				productListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
		showDialog();
		ViewHolder _ViewHolder = (ViewHolder) parent.getTag();
	}

}
