package itc.hoseo.springproject.domain;

import lombok.Data;

@Data
public class Review {
	private String id, review_date, shopname, review;
	private int star;
	
	Review(String id, String review_date, String shopname, String review, int star){
		this.id = id;
		this.review_date = review_date;
		this.shopname = shopname;
		this.review = review;
		this.star = star;
	}
}
