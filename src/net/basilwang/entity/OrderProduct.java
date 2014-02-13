package net.basilwang.entity;

import android.widget.Toast;

public class OrderProduct {

	private String stock;
	private String name;
	private String areaProductSkuName;
	private String areaProductSku;
	private Integer amount = 1;
	private String unit;
	private Double perPrice = 0.0;
	private Double totalPrice;

	public OrderProduct(String name, String specification, String unit) {
		this.name = name;
		this.areaProductSkuName = specification;
		this.unit = unit;
		setNullTotalPrice();
	}

	public OrderProduct() {
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String goodsStock) {
		this.stock = goodsStock;
	}

	public String getName() {
		return name;
	}

	public void setName(String goodsName) {
		this.name = goodsName;
	}

	public String getAreaProductSkuName() {
		return areaProductSkuName;
	}

	public void setAreaProductSkuName(String areaProductSkuName) {
		this.areaProductSkuName = areaProductSkuName;
	}

	public String getAreaProductSku() {
		return areaProductSku;
	}

	public void setAreaProductSku(String areaProductSku) {
		this.areaProductSku = areaProductSku;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setStringAmount(String amount) {
		this.amount = amount.equals("") ? 0 : Integer.parseInt(amount);
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getPerPrice() {
		return perPrice;
	}

	public void setPerPrice(Double goodsPrice) {
		this.perPrice = goodsPrice;
	}

	public void setStringPrice(String goodsPrice) {
		this.perPrice = goodsPrice.equals("") ? 0.0 : Double
				.parseDouble(goodsPrice);
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double goodsTotalPrice) {
		this.totalPrice = goodsTotalPrice;
	}

	public void setNullTotalPrice() {
		if (amount == 0 || perPrice == 0) {
			this.totalPrice = 0.0;
		} else {
			this.totalPrice = amount * perPrice;
		}
	}

	@Override
	public boolean equals(Object o) {
		OrderProduct o1 = (OrderProduct) o;
		if (this.areaProductSku.equals(o1.getAreaProductSku()))
			return true;
		return false;
	}

	public boolean isOrderProductNull() {
		return this.totalPrice == 0 ? true : false;
	}

}
