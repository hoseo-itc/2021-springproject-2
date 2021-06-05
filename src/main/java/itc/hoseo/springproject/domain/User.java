package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class User {
	

	private String id;
	private String name; 
	private String phone;
	
	private Address address;
	
	public User(String id, String name, String phone, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.address = address;
	}
	
	User(String id, String name, String phone){
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
	
	

	public User() {	}
}
