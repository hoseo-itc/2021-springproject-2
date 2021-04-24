package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class Category {
	private String category_name;
	
	Category(String category_name){
		this.category_name = category_name;
	}
}
