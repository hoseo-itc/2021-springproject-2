package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class User {
	private String id, name, phone;
	
	User(String id, String name, String phone){
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
}
