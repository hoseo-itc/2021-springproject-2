package itc.hoseo.springproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.repository.MenuRepository;
import itc.hoseo.springproject.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RestaurantService {

	@Autowired
	private RestaurantRepository restaurantRepository;

	@Autowired
	private MenuRepository menuRepository;

	@Transactional
	public void save(Restaurant rst) {
		Restaurant savedRst = restaurantRepository.save(rst);
		for (Menu mnu : rst.getMenus()) {
			mnu.setShopNo(savedRst.getNo());
			menuRepository.save(mnu);
		}
	}

	public Restaurant findByShopName(String Name) {
		return restaurantRepository.findByShopName(Name);
	}

	public List<Menu> findByMenuName(String Name) {
		return menuRepository.findByMenuName(Name);
	}

	public List<Restaurant> findAll() {
		List<Restaurant> rslt = restaurantRepository.findAll();
		for (Restaurant rst : rslt) {
			rst.setMenus(menuRepository.findByShopNo(rst.getNo()));
		}
		return rslt;
	}

	public List<Menu> findAllMenu() {
		List<Menu> rslt = menuRepository.findAllMenu();
		return rslt;
	}

	// NaverMapParserTest.java
	@Autowired
	private RestaurantService service;

	@Transactional
	public void test() {
		String url = "https://map.naver.com/v5/api/search";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("caller", "pcweb");
		map.add("query", "음식점");
		map.add("type", "all");
		map.add("searchCoord", "126.8639991000001;37.554732100000265");
		map.add("page", "1");
		map.add("displayCount", "10");
		map.add("isPlaceRecommendationReplace", "true");
		map.add("lang", "ko");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		ArrayNode list = (ArrayNode) restTemplate.postForObject(url, request, JsonNode.class).at("/result/place/list");
		// System.out.println(list.toString());

		// x,y 경도 위도 속성
		// tel 전화번호
		// 영업시간 자체로 보여주기
		// categoryName
		list.forEach(c -> {
			Restaurant rst = new Restaurant();
			rst.setShop_name(c.at("/name").textValue()); // om.treeToValue(c, Restaurant.class); //알아서 관련 속성값을 맞춰주는 친구
			rst.setShop_address(c.at("/roadAddress").textValue());
			rst.setCategory(c.at("/category/0").textValue());
			rst.setPhone(c.at("/tel").textValue());
			rst.setOpening_hour(c.at("/bizhourInfo").textValue());
			rst.setShop_desc(c.at("/microReview/0").textValue());
			rst.setThumbnail_photo(c.at("/thumUrl").textValue());
			rst.setX(c.at("/x").textValue());
			rst.setY(c.at("/y").textValue());
			// System.out.println(rst);

			List<Menu> menus = new ArrayList<>();

			for (String menuStr : c.at("/menuInfo").textValue().split("\\|")) {
				Menu mnu = new Menu();
				menuStr = menuStr.trim();
				String tt = "";
				for (String menuSt : menuStr.split(" ")) {

					if (menuSt.contains(",")) {
						mnu.setMenuName(tt.trim());
						mnu.setCost(Integer.parseInt(menuSt.replaceAll(",", "")));
						// System.out.println(mnu);
					} else {
						tt = tt + menuSt;
						tt = tt + " ";
					}
				}
				if (StringUtils.isEmpty(mnu.getMenuName()) == false) {
					menus.add(mnu);
				}

			}
			rst.setMenus(menus);
			service.save(rst);
		});
	}
}
