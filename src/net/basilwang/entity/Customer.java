/**
 * 
 */
package net.basilwang.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author phylllis
 * 
 */
public class Customer implements Parcelable {

	private String name;
	private String tel;
	private String address;
	private String description;
	private String id;

	public void setId(String id) {
		this.id = id;
	}

	public Customer() {

	}

	public Customer(String name, String phone, String address,
			String description) {
		this.name = name;
		this.tel = phone;
		this.address = address;
		this.description = description;
	}

	public Customer(String name, String phone, String address,
			String description, String id) {
		this.id = id;
		this.name = name;
		this.tel = phone;
		this.address = address;
		this.description = description;
	}

	// 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
	// android.os.BadParcelableException:
	// Parcelable protocol requires a Parcelable.Creator object called CREATOR
	// on class com.um.demo.Person
	// 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
	// 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
	// 4.在读取Parcel容器里的数据时，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
	// 5.反序列化对象
	public static final Parcelable.Creator<Customer> CREATOR = new Creator<Customer>() {

		@Override
		public Customer[] newArray(int size) {
			return new Customer[size];
		}

		@Override
		public Customer createFromParcel(Parcel source) {
			Customer customer = new Customer(source.readString(),
					source.readString(), source.readString(),
					source.readString(), source.readString());
			return customer;
		}
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getId() {
		return id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(tel);
		dest.writeString(address);
		dest.writeString(description);
		dest.writeString(id);
	}

}
