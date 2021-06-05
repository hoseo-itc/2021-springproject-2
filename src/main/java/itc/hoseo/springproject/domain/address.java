package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class address {
	private String id, address;
	
<<<<<<< HEAD:src/main/java/itc/hoseo/springproject/domain/Address.java
	public Address(String id, String address){
=======
	address(String id, String address){
>>>>>>> parent of d3246b3 (domain 구현):src/main/java/itc/hoseo/springproject/domain/address.java
		this.id = id;
		this.address = address;
	}
	
	public Address() {};
}
