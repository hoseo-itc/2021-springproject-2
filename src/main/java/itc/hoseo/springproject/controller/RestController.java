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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.domain.User;
import itc.hoseo.springproject.repository.MenuRepository;
import itc.hoseo.springproject.service.NaverMapParserTest;
import itc.hoseo.springproject.service.RestaurantService;

@Controller
public class RestController {

	@Autowired
	private RestaurantService restaurantService;
	

	@GetMapping("/")
	public String goIndex() {
		restaurantService.test();
		return "rest/index.html";
	}
	
	@GetMapping("/select")
	public String select() {
		return "rest/select.html";
	}

	@PostMapping("/search")
	public String search(
		@RequestParam("restaurant") String rst,
		@RequestParam("input") String str,
		HttpSession session) {
		
		if(rst.trim().equals("rst")) {
			session.setAttribute(str, restaurantService.findByShopName(str));
		} else {
			session.setAttribute(str, restaurantService.findByMenuName(str));
		}
		
		return "redirect:/shopMenuSearch";
	}

	@GetMapping("/shopList")
	public String list(ModelMap mm) {
		mm.put("restList", restaurantService.findAll());
		mm.put("menuList", restaurantService.findAllMenu());
		return "rest/shopList";
	}
}
