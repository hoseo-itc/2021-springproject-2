package itc.hoseo.springproject.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import itc.hoseo.springproject.domain.Order;
import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.domain.dto.UserJoinFormDTO;
import itc.hoseo.springproject.repository.UserRepository;
import itc.hoseo.springproject.service.KakaoLoginService;
import itc.hoseo.springproject.service.OrderService;
import itc.hoseo.springproject.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private KakaoLoginService kakaoLoginService;

	
	
	@GetMapping("/join")
	public String joinForm() {
		return "user/join";
	}

	@GetMapping("/addressMap")
	public String addressMap() {
		return "user/addressMap";
	}

	@PostMapping("/join")
	public String join(UserJoinFormDTO form) {
		userService.join(form.getUser());
		return "redirect:/";
	}

	@GetMapping("/list")
	public String list(ModelMap mm) {
		mm.put("userList", userService.findAll());
		return "user/list";
	}

	@GetMapping("/userProfile")
	public String userProfile(HttpSession session, ModelMap mm) {
		return "user/profile";
	}

	@PostMapping("/userProfile")
	public String kakaojoin(UserJoinFormDTO form) {
		userService.join(form.getUser());
		return "redirect:/";
	}
	
	

	@GetMapping("/login")
	public String userLogin() {
		return "user/login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/auth")
	public String kakaoCallback(String code, HttpSession session) throws JsonMappingException, JsonProcessingException {
		String accessToken = kakaoLoginService.getKakaoAccessToken(code).toString();

		session.setAttribute("email", kakaoLoginService.kakaoLogin(accessToken).getEmail());
		session.setAttribute("imgUrl", kakaoLoginService.kakaoLogin(accessToken).getImgUrl());
		session.setAttribute("nickName", kakaoLoginService.kakaoLogin(accessToken).getNickName());

		if (userService.login(String.valueOf(session.getAttribute("email"))) == true) {
			session.setAttribute("loginStat", "on");
			return "redirect:/";
		} else {
			return "redirect:/userProfile";
		}
	}
}
