package itc.hoseo.springproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itc.hoseo.springproject.domain.Order;
import itc.hoseo.springproject.domain.Restaurant;
import itc.hoseo.springproject.domain.dto.OrderDTO;
import itc.hoseo.springproject.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public void save(OrderDTO order) {
		orderRepository.save(order);
	}
	public Order menuSave(){
		return null;
	}
}
