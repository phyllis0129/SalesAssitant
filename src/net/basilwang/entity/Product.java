/**
 * 
 */
package net.basilwang.entity;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author phyllis
 * 
 */
public class Product implements Parcelable {

	private String id;
	private String name;
	private String unit;
	private List<AreaProductSku> areaProductSkuList = new ArrayList<AreaProductSku>();

	public Product() {

	}

	public Product(String id, String name, String unit,
			List<AreaProductSku> productSkuList) {
		this.id = id;
		this.name = name;
		this.unit = unit;
		this.areaProductSkuList = productSkuList;
	}

	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
	// android.os.BadParcelableException:
	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
	// on class com.um.demo.Person
	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
	// 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
	// 5.反序列化对象
	public static final Parcelable.Creator<Product> CREATOR = new Creator<Product>() {

		@Override
		public Product[] newArray(int size) {
			return new Product[size];
		}

		@Override
		public Product createFromParcel(Parcel source) {
			Product product = new Product(source.readString(),
					source.readString(), source.readString(),
					source.readArrayList(AreaProductSku.class.getClassLoader()));

			return product;
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(name);
		dest.writeString(unit);
		dest.writeList(areaProductSkuList);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public List<AreaProductSku> getAreaProductSkuList() {
		return areaProductSkuList;
	}

	public void setAreaProductSkuList(List<AreaProductSku> productSkuList) {
		this.areaProductSkuList = productSkuList;
	}

}
