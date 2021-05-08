package itc.hoseo.springproject.domain.dto;

import itc.hoseo.springproject.domain.Address;
import itc.hoseo.springproject.domain.User;
import lombok.Data;

@Data
public class UserJoinFormDTO {
	private String id;
	private String name; 
	private String phone;
	private String address;

	public User getUser() {
		return new User(id, name, phone, new Address(id, address));
	}
}
