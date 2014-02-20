/**
 * 
 */
package net.basilwang.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author phyllis
 * 
 */
public class AreaProductSku implements Parcelable {

	private String id;
	private int amount;
	private ProductSku productSku;

	public AreaProductSku() {

	}

	private AreaProductSku(String id, int amount, Object object) {
		this.id = id;
		this.amount = amount;
		this.productSku = (ProductSku) object;
	}

	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
	// android.os.BadParcelableException:
	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
	// on class com.um.demo.Person
	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
	// 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
	// 5.反序列化对象
	public static final Parcelable.Creator<AreaProductSku> CREATOR = new Creator<AreaProductSku>() {

		@Override
		public AreaProductSku[] newArray(int size) {
			return new AreaProductSku[size];
		}

		@Override
		public AreaProductSku createFromParcel(Parcel source) {
			AreaProductSku areaProductSku = new AreaProductSku(
					source.readString(), source.readInt(),
					source.readValue(ProductSku.class.getClassLoader()));
			return areaProductSku;
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeInt(amount);
		dest.writeValue(productSku);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public ProductSku getProductSku() {
		return productSku;
	}

	public void setProductSku(ProductSku productSku) {
		this.productSku = productSku;
	}

	@Override
	public boolean equals(Object o) {
		AreaProductSku o1 = (AreaProductSku) o;
		if (this.id.equals(o1.getId()))
			return true;
		return false;
	}

}
