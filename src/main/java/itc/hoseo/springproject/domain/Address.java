package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class Address {
	private String id, address;
	
	public Address(String id, String address){
		this.id = id;
		this.address = address;
	}
	
	public Address() {};
}
