package itc.hoseo.springproject.domain.dto;

import itc.hoseo.springproject.domain.Restaurant;
import lombok.Data;

@Data
public class OrderDTO {
	private int hisNo;
	private String date;
	private String shopNo;
	private String customerAddress;
	private String customerPhone;
	private int allCost;
	private String menu;
	private int menuCost;

	public OrderDTO() {
	}

	public OrderDTO(String shopNo, String address,String detailAddress, String customerPhone, int allCost) {
		this.shopNo = shopNo;
		this.customerAddress = customerAddress + detailAddress;
		this.customerPhone = customerPhone;
		this.allCost = allCost;
	}
}
