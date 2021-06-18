package itc.hoseo.springproject.repository;

import java.util.List;

import itc.hoseo.springproject.domain.Order;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.domain.dto.OrderDTO;

public interface OrderRepository {
	public void save(OrderDTO order);
	public Order menuSave();
}
