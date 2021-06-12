package itc.hoseo.springproject.domain;

import lombok.Data;

import java.util.List;

@Data
public class Order {
    private User user;
    private Address address;
    private List<OrderMenu> orderMenuList;
}
