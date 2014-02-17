package net.basilwang.trade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.basilwang.dao.SortAdapter;
import net.basilwang.dao.SortModel;
import net.basilwang.entity.Customer;
import net.basilwang.libray.StaticParameter;
import net.basilwang.utils.CharacterParser;
import net.basilwang.utils.PinyinComparator;
import net.basilwang.utils.PreferenceUtils;
import net.basilwang.utils.ReLoginUtils;
import net.basilwang.utils.SaLog;
import net.basilwang.view.ClearEditText;
import net.basilwang.view.SideBar;
import net.basilwang.view.SideBar.OnTouchingLetterChangedListener;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

/**
 * @author phyllis
 * 
 */
public class CustomerInfoFragment extends Fragment implements OnClickListener {

	private View mView;
	private List<Customer> customers;
	
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	
	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;
	
	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_customer, container,
				false);
		initViews();
		return mView;
	}

	private void initViews() {
		//实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		
		pinyinComparator = new PinyinComparator();
		
		sideBar = (SideBar)mView.findViewById(R.id.sidrbar);
		dialog = (TextView)mView.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);
		
		//设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {
			
			@Override
			public void onTouchingLetterChanged(String s) {
				//该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if(position != -1){
					sortListView.setSelection(position);
				}
				
			}
		});
		
		sortListView = (ListView)mView.findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				Toast.makeText(getActivity(), ((SortModel)adapter.getItem(position)).getCustomer().getName(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
				intent.putExtra("customer", ((SortModel) adapter.getItem(position)).getCustomer());
				intent.setClass(getActivity(), CustomerInfoMoreActivity.class);
				startActivity(intent);
			}
		});
		
		mClearEditText = (ClearEditText)mView.findViewById(R.id.filter_edit);
		
		//根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		SalesAssisteantActivity saa = (SalesAssisteantActivity) getActivity();
		RelativeLayout addBtn = saa.getTitleAdd();
		addBtn.setVisibility(View.VISIBLE);
		addBtn.setOnClickListener(this);
		
		bindData();
	}

	/**
	 * 为ListView填充数据
	 * @param customers
	 * @return
	 */
	private List<SortModel> filledData(List<Customer> customers){
		List<SortModel> mSortList = new ArrayList<SortModel>();
		
		for(int i=0; i<customers.size(); i++){
			SortModel sortModel = new SortModel();
			sortModel.setCustomer(customers.get(i));
			//汉字转换成拼音
			String pinyin = characterParser.getSelling(customers.get(i).getName());
			String sortString = pinyin.substring(0, 1).toUpperCase();
			
			// 正则表达式，判断首字母是否是英文字母
			if(sortString.matches("[A-Z]")){
				sortModel.setSortLetters(sortString.toUpperCase());
			}else{
				sortModel.setSortLetters("#");
			}
			
			mSortList.add(sortModel);
		}
		return mSortList;
		
	}
	
	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * @param filterStr
	 */
	private void filterData(String filterStr){
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		
		if(TextUtils.isEmpty(filterStr)){
			filterDateList = SourceDateList;
		}else{
			filterDateList.clear();
			for(SortModel sortModel : SourceDateList){
				String name = sortModel.getCustomer().getName();
				if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
					filterDateList.add(sortModel);
				}
			}
		}
		
		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}
	


	/**
	 * 绑定用户列表数据
	 */
	private void bindData() {
		customers = new ArrayList<Customer>();
		SourceDateList = new ArrayList<SortModel>();
		getCustomerList();
	}

	private void getCustomerList() {
		final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "",
				"数据加载中，请稍候....", true, false);
		SaLog.log("token", PreferenceUtils.getPreferToken(getActivity()));
		FinalHttp fh = new FinalHttp();
		fh.addHeader("X-Token", PreferenceUtils.getPreferToken(getActivity()));
		fh.get(StaticParameter.getCustomer, new AjaxCallBack<Object>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				t.printStackTrace();
				ReLoginUtils.authorizedFailed(
						(SalesAssisteantActivity) getActivity(), errorNo);
				Log.v("error", errorNo + strMsg + t.toString());
				progressDialog.dismiss();
			}

			@Override
			public void onSuccess(Object t) {
				super.onSuccess(t);
				Log.v("customer list", t.toString());
				customers = JSON.parseArray(t.toString(), Customer.class);
				Log.v("customer id", customers.get(0).getId());
				
				SourceDateList = filledData(customers);
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(getActivity(), SourceDateList);
				sortListView.setAdapter(adapter);
				progressDialog.dismiss();

			}

		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_bar_btn_add:
			Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
			startActivityForResult(intent, 0);
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==getActivity().RESULT_OK)
			bindData();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

}