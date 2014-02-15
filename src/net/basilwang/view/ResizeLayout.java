/**
 * 
 */
package net.basilwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * @author Basilwang
 * 
 */
public class ResizeLayout extends RelativeLayout {
	// private OnResizeListener mListener;
	// private int oldb=0;
	//
	// public interface OnResizeListener {
	// void OnResize(int b, int oldb);
	// }
	//
	// public void setOnResizeListener(OnResizeListener l) {
	// mListener = l;
	// }
	//
	// public ResizeLayout(Context context, AttributeSet attrs) {
	// super(context, attrs);
	// }
	//
	// @Override
	// protected void onLayout(boolean changed, int l, int t, int r, int b) {
	// super.onLayout(changed, l, t, r, b);
	// Log.v("onlayout changed", changed+"");
	//
	// if (mListener != null) {
	// mListener.OnResize(t,oldb);
	// }
	// oldb = t;
	// }

	private static final String TAG = ResizeLayout.class.getSimpleName();
	public static final byte KEYBOARD_STATE_SHOW = -3;
	public static final byte KEYBOARD_STATE_HIDE = -2;
	public static final byte KEYBOARD_STATE_INIT = -1;
	private boolean mHasInit;
	private boolean mHasKeybord;
	private int mHeight;
	private onKybdsChangeListener mListener;

	public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ResizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ResizeLayout(Context context) {
		super(context);
	}

	/**
	 * set keyboard state listener
	 */
	public void setOnkbdStateListener(onKybdsChangeListener listener) {
		mListener = listener;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
	}

	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int position = SlideCutListView.getStaticPosition();
			if(mListener!=null){
				mListener.onKeyBoardStateChange(position);
			}
			
//		SlideCutListView.setDefaultStaticPosition();
//		if (!mHasInit) {
//			mHasInit = true;
//			mHeight = b;
//			if (mListener != null) {
//				mListener.onKeyBoardStateChange(KEYBOARD_STATE_INIT);
//			}
//			Log.w(TAG, "init keyboard.......");
//		} else {
//			mHeight = mHeight < b ? b : mHeight;
//		}
//		if (mHasInit && mHeight > b) {
//			mHasKeybord = true;
//			if (mListener != null) {
//				mListener.onKeyBoardStateChange(KEYBOARD_STATE_SHOW);
//			}
//			Log.w(TAG, "show keyboard.......");
//		}
//		if (mHasInit && mHasKeybord && mHeight == b) {
//			mHasKeybord = false;
//			if (mListener != null) {
//				mListener.onKeyBoardStateChange(KEYBOARD_STATE_HIDE);
//			}
//			Log.w(TAG, "hide keyboard.......");
//		}
	}
	
	

	public interface onKybdsChangeListener {
		public void onKeyBoardStateChange(int state);
	}
}
