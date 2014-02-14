/**
 * 
 */
package net.basilwang.entity;

import java.util.List;

/**
 * @author phyllis
 *
 */
public class Order {
	
	private String customer;
	private Double receivable;
	private Double realcollection;
	private List<OrderProduct> orderProducts;
	
	
	public Order(){
	}
	
	
	public String getCustomer() {
		return customer;
	}


	public void setCustomer(String customer) {
		this.customer = customer;
	}


	public Double getReceivable() {
		return receivable;
	}
	public void setReceivable(Double receivable) {
		this.receivable = receivable;
	}
	public void setStringReceivable(String receivable) {
		this.receivable = Double.parseDouble(receivable);
	}
	public Double getRealcollection() {
		return realcollection;
	}
	public void setRealcollection(Double realcollection) {
		this.realcollection = realcollection;
	}
	public void setStringRealcollection(String realcollection) {
		this.realcollection = realcollection.equals("")?0:Double.parseDouble(realcollection);
	}
	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
