/**
 * 
 */
package net.basilwang.entity;

/**
 * @author phylllis
 *
 */
public class Customer {
	
	private int id;
	private String name;
	private String phone;
	private String address;
	private String comment;
	
	public Customer(String name,String phone,String address,String comment){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.comment = comment;
	}

}
