package itc.hoseo.springproject.repository.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.repository.UserRepository;

@Repository
public class H2UserRepository implements UserRepository{

	@Autowired
	private JdbcTemplate template;
	
	@Override
	public User save(User user) {
		template.update("insert into user(id,name,phone) values(?,?,?)",
    			user.getId(), user.getName(), user.getPhone());
    	return findById(user.getId());
	}

	@Override
	public List<User> findAll() {
		return template.query("select * from user",
    			new BeanPropertyRowMapper<User>(User.class));
	}

	@Override
	public User findById(String id) {
		return template.queryForObject("select id from user where id = ?", 
    			new BeanPropertyRowMapper<User>(User.class), id);
	}

}
