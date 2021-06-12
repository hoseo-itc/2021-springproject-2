package itc.hoseo.springproject.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import itc.hoseo.springproject.domain.RestaurantCategory;
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
import org.springframework.ui.Model;
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
import itc.hoseo.springproject.service.RestaurantService;

@Controller
public class RestController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/res")
    public String list(RestaurantCategory category, ModelMap mm) {
        mm.put("list", restaurantService.findByCategory(category.getDesc()));
        return "rest/list";
    }

    @PostMapping("/save")
    public String save() {
        restaurantService.test();
        return "redirect:/";
    }

    @GetMapping("/shopList")
    public String list(ModelMap mm) {
        mm.put("restList", restaurantService.findAll());
        return "rest/shopList.html";
    }

    @GetMapping("/menuList")
    public String listM(ModelMap mm) {
        mm.put("menuList", restaurantService.findAllMenu());
        return "rest/menuList.html";
    }

//	@GetMapping("/selectResult")
//	public String listSM(ModelMap mm) {
//		mm.put("menuList", restaurantService.findAllMenu());
//		mm.put("restList", restaurantService.findAll());
//		return "rest/selectResult.html";
//	}

    @GetMapping("/select")
    public String select() {
        return "rest/select.html";
    }
    @PostMapping("/search")
    public String search(
            ModelMap mm,
            @RequestParam("input") String str,
            @RequestParam("option") int opt,
            HttpSession session) {
        if (opt == 1) {
            session.setAttribute("restList", restaurantService.findByShopName(str));
            mm.put("restList", restaurantService.findByShopName(str));
        } else {
            session.setAttribute("menuList", restaurantService.findByMenuName(str));
            mm.put("menuList", restaurantService.findByMenuName(str));
        }
        return "redirect:/selectResult";
    }

}
