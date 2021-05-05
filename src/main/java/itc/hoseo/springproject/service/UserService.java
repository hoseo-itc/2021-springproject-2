package itc.hoseo.springproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public boolean login(String id) {
		User findUser = userRepository.findById(id);
		return true;
	}
	
	@Transactional
	public void join(User user ) {
		userRepository.save(user);
	}
	
	public int countMembers() {
		return userRepository.findAll().size();
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	
	public User findById(String id) {
		return userRepository.findById(id);
	}
}
