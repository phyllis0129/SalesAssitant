package net.basilwang.entity;

/**
 * @author 严
 * 
 *         2014-2-13 交易记录订单详情类
 */
public class OrderProducts {

	private String amount;
	private String perprice;
	private String totalprice;
	private String productname;
	private String productskuname;
	private String productunit;
	private String id;

	public OrderProducts() {

	}

	public String getProductunit() {
		return productunit;
	}

	public void setProductunit(String productunit) {
		this.productunit = productunit;
	}

	public OrderProducts(String amount, String perprice, String totalprice,
			String productname, String productskuname, String productunit,
			String id) {
		super();
		this.amount = amount;
		this.perprice = perprice;
		this.totalprice = totalprice;
		this.productname = productname;
		this.productskuname = productskuname;
		this.productunit = productunit;
		this.id = id;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPerprice() {
		return perprice;
	}

	public void setPerprice(String perprice) {
		this.perprice = perprice;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductskuname() {
		return productskuname;
	}

	public void setProductskuname(String productskuname) {
		this.productskuname = productskuname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
