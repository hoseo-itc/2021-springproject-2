package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class menu {
	String menu_name, menu_price;
	
	menu(String menu_name, String menu_price){
		this.menu_name = menu_name;
		this.menu_price = menu_price;
	}
}
