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
	
	public Customer(){
		
	}
	
	public Customer(String name,String phone,String address,String comment){
		this.name = name;
		this.phone = phone;
		this.address = address;
		this.comment = comment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	
}
