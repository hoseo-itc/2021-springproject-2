package itc.hoseo.springproject.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import itc.hoseo.springproject.domain.Address;
import itc.hoseo.springproject.repository.AddressRepository;

@Repository
@Primary
public class H2AddressRepository implements AddressRepository{

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public Address save(Address address) {
		template.update("insert into address(id,address) values(?,?)",
					address.getId(),address.getAddress());
		return findById(address.getId());
		
	}

	@Override
	public Address findById(String id) {
		try {
			return template.queryForObject("select * from address where id = ?", 
	    			new BeanPropertyRowMapper<Address>(Address.class), id);
		}catch(DataAccessException dae) {
			return null;
		}
	
	}
}
