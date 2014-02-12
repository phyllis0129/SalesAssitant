package net.basilwang.entity;

/**
 * @author 严
 * 
 *         2014-2-12
 * 
 *         实收款与应收款
 */

public class Payment {
	private String receivable;
	private String realcollection;

	public Payment() {

	}

	public Payment(String receivable, String realcollectiong) {
		super();
		this.receivable = receivable;
		this.realcollection = realcollectiong;
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

}
