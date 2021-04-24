package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class Restaurant {
	private String shop_name, category, phone, thumbnail_photo;
	private int opening_hour, closing_hour, cnt_review, cnt_like;
	
	Restaurant(String shop_name, String category, String phone, String thumnail_photo,
				int opening_hour, int closing_hour, int cnt_review, int cnt_like){
		this.shop_name = shop_name;
		this.category = category;
		this.phone = phone;
		this.thumbnail_photo = thumnail_photo;
		this.opening_hour = opening_hour;
		this.closing_hour = closing_hour;
		this.cnt_like = cnt_like;
		this.cnt_review = cnt_review;
	}
}
