package net.basilwang.entity;

public class ProductSkus {
	private String produSkuName;
	private String productSkuId;
	private String totalAmout;
	private String totalPrice;

	public ProductSkus() {

	}

	public ProductSkus(String produSkuName, String productSkuId,
			String totalAmout, String totalPrice) {
		super();
		this.produSkuName = produSkuName;
		this.productSkuId = productSkuId;
		this.totalAmout = totalAmout;
		this.totalPrice = totalPrice;
	}

	public String getProduSkuName() {
		return produSkuName;
	}

	public void setProduSkuName(String produSkuName) {
		this.produSkuName = produSkuName;
	}

	public String getProductSkuId() {
		return productSkuId;
	}

	public void setProductSkuId(String productSkuId) {
		this.productSkuId = productSkuId;
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

}
