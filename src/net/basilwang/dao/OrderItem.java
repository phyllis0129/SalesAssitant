package net.basilwang.dao;

public class OrderItem {

	private String goodsStock;
	private String goodsName;
	private String goodsSpecification;
	private double goodsCounts = 1;
	private String goodsUnit;
	private double goodsPrice;
	private String goodsPriceUnit;
	private String goodsTotalPrice;

	public OrderItem(String name, String specification, String unit) {
		this.goodsName = name;
		this.goodsSpecification = specification;
		this.goodsUnit = unit;
		this.goodsPriceUnit = "元/" + unit;
	}

	public String getmString() {
		return mString;
	}

	public void setmString(String mString) {
		this.mString = mString;
	}

}
