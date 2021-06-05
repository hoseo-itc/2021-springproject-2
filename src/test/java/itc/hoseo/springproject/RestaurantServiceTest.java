package itc.hoseo.springproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.service.RestaurantService;

@SpringBootTest
public class RestaurantServiceTest {


	@Autowired
	public RestaurantService service;
	
	@Test
	void contextLoads() {
		Restaurant rst = Restaurant.builder()
				.shop_name("멘타쿠")
				.category("일식")
				.build();
		List<Menu> menus = new ArrayList<>();
		menus.add(new Menu(-1, -1, "라멘", null, 100));
		menus.add(new Menu(-1, -1, "탄탄멘", null, 100));
		rst.setMenus(menus);
		service.save(rst);
		
		assertEquals(2, service.findAll().get(0).getMenus().size());
		
	}
	
}
