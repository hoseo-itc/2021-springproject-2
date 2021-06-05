package itc.hoseo.springproject.controller;

import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	
	@GetMapping("/")
	public String index(HttpSession session) {
		if(session.getAttribute("email")== null) {
			System.out.println("로그아웃");
			return "logoutIndex";
		}else {
			System.out.println("로그인");
			return "loginIndex";
		}
			
		
	}
	
	@GetMapping("/pizza")
	public String pizza() {
		return "pizza";
	}
	
	@GetMapping("/chicken")
	public String chicken() {
		return "chicken";
	}
	
	@GetMapping("/feet")
	public String feet() {
		return "feet";
	}
	
	@GetMapping("/snackbar")
	public String snackbar() {
		return "snackbar";
	}
	
	@GetMapping("/korean")
	public String korean() {
		return "korean";
	}
	
	@GetMapping("/japan")
	public String japan() {
		return "japan";
	}
	
	@GetMapping("/dessert")
	public String dessert() {
		return "dessert";
	}
	
	@GetMapping("/rest")
	public String rest() {
		return "rest";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
