package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class user {
	private String id, name, phone;
	
	user(String id, String name, String phone){
		this.id = id;
		this.name = name;
		this.phone = phone;
	}
}
