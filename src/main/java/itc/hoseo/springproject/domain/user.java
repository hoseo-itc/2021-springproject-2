package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
<<<<<<< HEAD:src/main/java/itc/hoseo/springproject/domain/User.java
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
=======
public class user {
	private String id, name, phone;
>>>>>>> parent of d3246b3 (domain 구현):src/main/java/itc/hoseo/springproject/domain/user.java
	
	user(String id, String name, String phone){
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
	
	

	public User() {	}
}
