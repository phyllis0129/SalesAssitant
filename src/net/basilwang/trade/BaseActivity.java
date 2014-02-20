/**
 * 
 */
package net.basilwang.trade;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.LayoutParams;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @author phyllis
 * 
 */
public class BaseActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private TextView titleTV;
	private View title_bar;
	private RelativeLayout titleNav, titleBack, titleAdd, titleDel;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar.LayoutParams titleBarlp = new ActionBar.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
				Gravity.CENTER);
		title_bar = getLayoutInflater().inflate(R.layout.title_bar, null);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setCustomView(title_bar, titleBarlp);

		// 显示自定义的ActionBar
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		initView();
	}

	private void initView() {
		titleTV = (TextView) title_bar.findViewById(R.id.title_tv);
		titleNav = (RelativeLayout) title_bar.findViewById(R.id.title_bar_nav);
		titleBack = (RelativeLayout) title_bar
				.findViewById(R.id.title_bar_back);
		titleAdd = (RelativeLayout) title_bar
				.findViewById(R.id.title_bar_btn_add);
		titleDel = (RelativeLayout) title_bar
				.findViewById(R.id.title_bar_btn_del);
		titleNav.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case R.id.title_bar_nav:
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
			toggle();
			break;
		default:
			break;
		}
	}

	public void setTitle(String title) {
		titleTV.setText(title);
	}

	public RelativeLayout getTitleBack() {
		return titleBack;
	}

	public RelativeLayout getTitleAdd() {
		return titleAdd;
	}

	public RelativeLayout getTitleDel() {
		return titleDel;
	}

}
