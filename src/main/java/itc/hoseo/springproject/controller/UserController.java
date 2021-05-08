package itc.hoseo.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import itc.hoseo.springproject.domain.Address;
import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.domain.dto.UserJoinFormDTO;
import itc.hoseo.springproject.repository.AddressRepository;
import itc.hoseo.springproject.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}
	
	@PostMapping("/join")
	public String join(UserJoinFormDTO form) {
		userService.join(form.getUser());
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(ModelMap mm) {
		mm.put("userList", userService.findAll());
		return "user/list";
	}
}
