package net.basilwang.trade;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SalesAssisteantActivity extends BaseActivity {

	private Fragment mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("订单信息");
		initSlidingMenu();
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}
		if (mContent == null) {
			mContent = new OrderInfoFragment();
		}
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlideMenuFragment()).commit();
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction().replace(
				R.id.content_frame, mContent).commit();
		// setBehindContentView(R.layout.menu_frame);
		// getSupportFragmentManager().beginTransaction().replace(R.layout.menu_frame,
		// new SlideMenuFragment());

	}

	private void initSlidingMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

	}

	public void switchContent(Fragment fragment, String title) {
		setTitle(title);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (this.getSupportFragmentManager().getBackStackEntryCount() > 0)
				getSupportFragmentManager().popBackStack();
			else if (getSlidingMenu().isMenuShowing())
				this.finish();
			else {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				showMenu();
			}
		}
		return true;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event){
		return true;
	}

}
