package itc.hoseo.springproject.repository.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import itc.hoseo.springproject.domain.Order;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.domain.dto.OrderDTO;
import itc.hoseo.springproject.repository.OrderRepository;

@Repository
public class H2OrderRepository implements OrderRepository {

	@Autowired
	private JdbcTemplate template;

	@Override
	public void save(OrderDTO order) {
		template.update("insert into order_his(shopno,customeraddress,customerphone,allcost) values(?,?,?,?)",
				order.getShopNo(), order.getCustomerAddress(), order.getCustomerPhone(), order.getAllCost());
	}

	@Override
	public Order menuSave() {
		// TODO Auto-generated method stub
		return null;
	}

}
