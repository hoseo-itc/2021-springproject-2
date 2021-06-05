package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class Menu {
	private String shop_name, menu_name, food_photo;
	private int cost;
	
	Menu(String shop_name, String menu_name, String food_photo, int cost){
		this.shop_name = shop_name;
		this.menu_name = menu_name;
		this.food_photo = food_photo;
		this.cost = cost;
	}
}
