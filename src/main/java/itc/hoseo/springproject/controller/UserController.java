package itc.hoseo.springproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import itc.hoseo.springproject.domain.User;
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
	public String join(User u) {
		userService.join(u);
		return "redirect:/list";
	}
	
	@GetMapping("/list")
	public String list(ModelMap mm) {
		mm.put("userList", userService.findAll());
		return "user/list";
	}
}
