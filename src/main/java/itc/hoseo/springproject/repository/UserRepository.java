package itc.hoseo.springproject.repository;

import java.util.List;

import itc.hoseo.springproject.domain.User;

public interface UserRepository {
	public User save(User user);
	public List<User> findAll();
	public User findById(String id);
}
