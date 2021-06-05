package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class address {
	private String id, address;
	
	address(String id, String address){
		this.id = id;
		this.address = address;
	}
}
