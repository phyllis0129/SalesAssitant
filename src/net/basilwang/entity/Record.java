package net.basilwang.entity;

import java.util.ArrayList;
import java.util.List;

public class Record {
	private String productId;
	private String productName;
	private String totalAmout;
	private String totalPrice;
	private String productUnit;
	private List<ProductSkus> productSkus = new ArrayList<ProductSkus>();

	public Record() {

	}

	public Record(String productId, String productName, String totalAmout,
			String totalPrice) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.totalAmout = totalAmout;
		this.totalPrice = totalPrice;
	}

	public Record(String productId, String productName, String totalAmout,
			String totalPrice, String productUnit, List<ProductSkus> productSkus) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.totalAmout = totalAmout;
		this.totalPrice = totalPrice;
		this.productUnit = productUnit;
		this.productSkus = productSkus;
	}

	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(String totalAmout) {
		this.totalAmout = totalAmout;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductSkus> getProductSkus() {
		return productSkus;
	}

	public void setProductSkus(List<ProductSkus> productSkus) {
		this.productSkus = productSkus;
	}

}
