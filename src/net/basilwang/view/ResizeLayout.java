/**
 * 
 */
package net.basilwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * @author Basilwang
 * 
 */
public class ResizeLayout extends LinearLayout {
	private OnResizeListener mListener;
	private int oldb=0;
			
	public interface OnResizeListener {
		void OnResize(int b, int oldb);
	}

	public void setOnResizeListener(OnResizeListener l) {
		mListener = l;
	}

	public ResizeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		

		if (mListener != null) {
			mListener.OnResize(t,oldb);
		}
		oldb = t;
	}

}
