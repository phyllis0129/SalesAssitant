package net.basilwang.trade;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class SalesAssisteantActivity extends BaseActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initSlidingMenu();
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SlideMenuFragment()).commit();
		setContentView(R.layout.content_frame);
//		getSupportFragmentManager().beginTransaction().replace(R.layout.content_frame, new SlideMenuFragment());
//		setBehindContentView(R.layout.menu_frame);
//		getSupportFragmentManager().beginTransaction().replace(R.layout.menu_frame, new SlideMenuFragment());
		
	}
	
	private void initSlidingMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
	}

	public void switchContent(Fragment fragment,String title){
		setTitle(title);
		
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}
	
	

}
