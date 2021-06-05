package itc.hoseo.springproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import itc.hoseo.springproject.domain.Menu;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.repository.MenuRepository;
import itc.hoseo.springproject.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoLoginService {

	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private MenuRepository menuRepository;
	

	@Transactional
	public void save(Restaurant rst) {
		Restaurant savedRst = restaurantRepository.save(rst);
		for(Menu mnu : rst.getMenus()) {
			mnu.setShopNo(savedRst.getNo());
			menuRepository.save(mnu);
		}
	}
	
	
	public List<Restaurant> findAll(){
		List<Restaurant> rslt = restaurantRepository.findAll();
		for(Restaurant rst : rslt) {
			rst.setMenus(menuRepository.findByShopNo(rst.getNo()));
		}
		System.out.println("hello");
		return rslt;
	}
	
}
