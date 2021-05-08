package itc.hoseo.springproject.repository;

import java.util.List;

import itc.hoseo.springproject.domain.Address;

public interface AddressRepository {
	public Address save(Address address);
	public Address findById(String id);
}
