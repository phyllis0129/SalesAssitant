/**
 * 
 */
package net.basilwang.trade;

import net.basilwang.utils.PreferenceUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * @author phyllis
 * 
 */
public class SplashActivity extends Activity {

	private ImageView splashImg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		splashImg = (ImageView) findViewById(R.id.splash);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
		splashImg.setAnimation(animation);
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				jump();

			}
		}, 2100);
	}

	private void jump() {
		splashImg.setVisibility(View.GONE);
		Intent intent = new Intent();
		boolean isFirstUsed = PreferenceUtils.getPreferIsFirstUsed(this);
		String token = PreferenceUtils.getPreferToken(this);
		if (token == null) {
			intent.setClass(this, LoginActivity.class);
		} else {
			intent.setClass(this, SalesAssisteantActivity.class);
		}
		this.startActivity(intent);
		this.finish();

	}

}
