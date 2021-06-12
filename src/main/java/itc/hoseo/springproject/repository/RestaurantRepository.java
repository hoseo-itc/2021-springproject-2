package itc.hoseo.springproject.repository;

import java.util.List;

import itc.hoseo.springproject.domain.Restaurant;

public interface RestaurantRepository {
	public Restaurant save(Restaurant rest);//정보저장
	public List<Restaurant> findAll(); //전체검색
	
	public Restaurant findByShopNo (int shopNo);	//이름검색
	public List<Restaurant> findByShopName(String shopName);
	public List<Restaurant> findByCategory (String category);		//카테고리검색
	public Restaurant findByMoreLike (Integer cnt_like);	//추천수검색
	public Restaurant findByLessLike (Integer cnt_like);
}