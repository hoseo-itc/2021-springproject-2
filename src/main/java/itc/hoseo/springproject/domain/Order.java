package itc.hoseo.springproject.domain;

import lombok.Data;

import java.util.List;

@Data
public class Order {
	private int hisNo;
	private String date;
	private String shopNo;
	private String customerAddress;
	private String customerPhone;
	private int allCost;
	private String menu;
	private int menuCost;
    private List<OrderMenu> orderMenuList;
    

	public Order() {
	}

	public Order(String shopNo, String customerAddress, String customerPhone, int allCost) {
		this.shopNo = shopNo;
		this.customerAddress = customerAddress;
		this.customerPhone = customerPhone;
		this.allCost = allCost;
	}




    
    
}
