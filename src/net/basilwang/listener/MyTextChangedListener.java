package net.basilwang.listener;

import java.util.List;

import net.basilwang.entity.OrderProduct;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class MyTextChangedListener implements TextWatcher {
	
	private EditText et;
	private List<OrderProduct> mOrderItemList;
	
	public MyTextChangedListener(EditText et, List<OrderProduct> mOrderItemList){
		this.et = et;
		this.mOrderItemList = mOrderItemList;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		int position = (Integer) et.getTag();
		Log.v("test", ""+position+","+et.getId());
		Log.v("test", ""+position+","+et.getText());
		
	}

}
