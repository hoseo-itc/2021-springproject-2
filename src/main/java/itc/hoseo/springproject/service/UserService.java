package itc.hoseo.springproject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itc.hoseo.springproject.domain.Address;
import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.repository.AddressRepository;
import itc.hoseo.springproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	public boolean login(String id) {
		User findUser = userRepository.findById(id);
		if(findUser == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@Transactional
	public void join(User user) {
		userRepository.save(user);
		addressRepository.save(user.getAddress());
	}
	
	public int countMembers() {
		return userRepository.findAll().size();
	}
	
	public List<User> findAll(){
		return userRepository.findAll()
			.stream()
			.map(u -> {
				u.setAddress(addressRepository.findById(u.getId()));
				return u;
			})
			.collect(Collectors.toList());
	}
	
	public User findById(String id) {
		
		return userRepository.findById(id);
	}
}
