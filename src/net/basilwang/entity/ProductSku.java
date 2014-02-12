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
public class ProductSku {
	
	private String id;
	private String name;
	
	public ProductSku(){
		
	}
	
	public ProductSku(String id, String name){
		this.id = id;
		this.name = name;
	}
	

//	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
//	// android.os.BadParcelableException:
//	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
//	// on class com.um.demo.Person
//	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
//	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
//	// 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
//	// 5.反序列化对象
//	public static final Parcelable.Creator<ProductSku> CREATOR = new Creator<ProductSku>() {
//
//		@Override
//		public ProductSku[] newArray(int size) {
//			return new ProductSku[size];
//		}
//
//		@Override
//		public ProductSku createFromParcel(Parcel source) {
//			ProductSku productSku = new ProductSku(source.readString(),
//					source.readString());
//			return productSku;
//		}
//	};
//
//	@Override
//	public int describeContents() {
//		return 0;
//	}
//
//	@Override
//	public void writeToParcel(Parcel dest, int flags) {
//		dest.writeString(id);
//		dest.writeString(name);
//	}
	
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


}
