/**
 * 
 */
package net.basilwang.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author phyllis
 * 
 */
public class ResizeLayout extends RelativeLayout {

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
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		int position = SlideCutListView.getStaticPosition();
		if (mListener != null) {
			mListener.onKeyBoardStateChange(position);
		}
	}

	public interface onKybdsChangeListener {
		public void onKeyBoardStateChange(int state);
	}
}
