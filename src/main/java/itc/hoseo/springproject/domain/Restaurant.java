package itc.hoseo.springproject.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "no")
public class Restaurant {
	private int no;
	private String shop_name;
	private String shop_address;
	private String category;
	private String phone;
	private String thumbnail_photo;
	private String opening_hour;
	private String x;	//위도
	private String y;	//경도
	private String shop_desc; //설명글
	//private int cnt_review;
	//private int cnt_like;
	private List<Menu> menus = new ArrayList<>();



}
