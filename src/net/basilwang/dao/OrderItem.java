package net.basilwang.dao;

public class OrderItem {

	private String goodsStock;
	private String goodsName;
	private String goodsSpecification;
	private Integer goodsCounts = 1;
	private String goodsUnit;
	private Double goodsPrice = 0.0;
	private String goodsPriceUnit;
	private Double goodsTotalPrice = 0.0;

	public OrderItem(String name, String specification, String unit) {
		this.goodsName = name;
		this.goodsSpecification = specification;
		this.goodsUnit = unit;
		this.goodsPriceUnit = "元/" + unit;
	}

	public String getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(String goodsStock) {
		this.goodsStock = goodsStock;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsSpecification() {
		return goodsSpecification;
	}

	public void setGoodsSpecification(String goodsSpecification) {
		this.goodsSpecification = goodsSpecification;
	}

	public Integer getGoodsCounts() {
		return goodsCounts;
	}

	public void setGoodsCounts(Integer goodsCounts) {
		this.goodsCounts = goodsCounts;
	}

	public void setGoodsCounts(String goodsCounts) {
		this.goodsCounts = goodsCounts.equals("") ? 0 : Integer
				.parseInt(goodsCounts);
	}

	public String getGoodsUnit() {
		return goodsUnit;
	}

	public void setGoodsUnit(String goodsUnit) {
		this.goodsUnit = goodsUnit;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice.equals("") ? 0.0 : Double
				.parseDouble(goodsPrice);
	}

	public String getGoodsPriceUnit() {
		return goodsPriceUnit;
	}

	public void setGoodsPriceUnit(String goodsPriceUnit) {
		this.goodsPriceUnit = goodsPriceUnit;
	}

	public Double getGoodsTotalPrice() {
		return goodsTotalPrice;
	}

	public void setGoodsTotalPrice(Double goodsTotalPrice) {
		this.goodsTotalPrice = goodsTotalPrice;
	}

	public void setGoodsTotalPrice() {
		if (goodsCounts == 0 || goodsPrice == 0) {
			this.goodsTotalPrice = 0.0;
		} else {
			this.goodsTotalPrice = goodsCounts * goodsPrice;
		}
	}

}
