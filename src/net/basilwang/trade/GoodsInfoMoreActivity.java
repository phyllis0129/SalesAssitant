/**
 * 
 */
package net.basilwang.trade;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * @author Basilwang
 *
 */
public class GoodsInfoMoreActivity extends Activity implements OnClickListener{
	
	private Button backBtn,sureBtn;
	private ListView goodsListView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_info);
		initView();
	}


	private void initView() {
		backBtn = (Button)findViewById(R.id.goods_title_bar_back);
		backBtn.setOnClickListener(this);
		sureBtn = (Button)findViewById(R.id.goods_title_bar_btn_sure);
		sureBtn.setOnClickListener(this);
		goodsListView = (ListView)findViewById(R.id.goods_list);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.goods_title_bar_back:
			this.finish();
			break;
		case R.id.goods_title_bar_btn_sure:
			Intent intent = new Intent();
			//TODO
			this.setResult(RESULT_OK, intent);
			this.finish();
			break;
		default:
			break;
		}
		
	}
	
	

}
