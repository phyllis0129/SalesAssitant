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
	
	private Customer customer;
	private Double receivable;
	private Double realcollection;
	private List<OrderProduct> orderProducts;
	
	
	public Order(){
	}
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Double getReceivable() {
		return receivable;
	}
	public void setReceivable(Double receivable) {
		this.receivable = receivable;
	}
	public Double getRealcollection() {
		return realcollection;
	}
	public void setRealcollection(Double realcollection) {
		this.realcollection = realcollection;
	}
	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}
	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

}
