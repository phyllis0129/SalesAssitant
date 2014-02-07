package net.basilwang.listener;

import java.util.List;

import net.basilwang.dao.OrderItem;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class MyTextChangedListener implements TextWatcher {
	
	private EditText et;
	private List<OrderItem> mOrderItemList;
	
	public MyTextChangedListener(EditText et, List<OrderItem> mOrderItemList){
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
