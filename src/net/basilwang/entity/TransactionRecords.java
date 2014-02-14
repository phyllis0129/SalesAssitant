package net.basilwang.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 严
 * 
 *         2014-2-13 交易记录类
 */
public class TransactionRecords {
	private String receivable;
	private String realcollection;
	private List<OrderProducts> orderProducts = new ArrayList<OrderProducts>();
	private String ordertime;
	private String customername;
	private String id;

	public TransactionRecords() {

	}

	public TransactionRecords(String receivable, String realcollection,
			List<OrderProducts> orderProducts, String ordertime,
			String customername, String id) {
		super();
		this.receivable = receivable;
		this.realcollection = realcollection;
		this.orderProducts = orderProducts;
		this.ordertime = ordertime;
		this.customername = customername;
		this.id = id;
	}

	public String getReceivable() {
		return receivable;
	}

	public void setReceivable(String receivable) {
		this.receivable = receivable;
	}

	public String getRealcollection() {
		return realcollection;
	}

	public void setRealcollection(String realcollection) {
		this.realcollection = realcollection;
	}

	public List<OrderProducts> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProducts> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
