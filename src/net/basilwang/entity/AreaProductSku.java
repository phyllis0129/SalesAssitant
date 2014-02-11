/**
 * 
 */
package net.basilwang.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/**
 * @author Basilwang
 * 
 */
public class AreaProductSku  {

	private int amount;
	private ProductSku productSku;
	
	private AreaProductSku(int amount,ProductSku productSku){
		this.amount = amount;
		this.productSku = productSku;
	}

//	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
//	// android.os.BadParcelableException:
//	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
//	// on class com.um.demo.Person
//	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
//	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
//	// 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
//	// 5.反序列化对象
//	public static final Parcelable.Creator<AreaProductSku> CREATOR = new Creator<AreaProductSku>() {
//
//		@Override
//		public AreaProductSku[] newArray(int size) {
//			return new AreaProductSku[size];
//		}
//
//		@Override
//		public AreaProductSku createFromParcel(Parcel source) {
//			AreaProductSku areaProductSku = new AreaProductSku(source.readInt(),
//					source.rea(ProductSku.CREATOR));
//			return areaProductSku;
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
//		dest.writeInt(amount);
//		dest.writeValue(productSku);
//	}

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

}