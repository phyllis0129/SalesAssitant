package net.basilwang.dao;

import net.basilwang.entity.Customer;

public class SortModel {

	private Customer customer;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
