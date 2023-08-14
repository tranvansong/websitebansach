package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.repository.OrderItemRepository;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	public List<OrderItem> getAllOrderItems() {
		return orderItemRepository.findAll();
	}
	
	public void saveItem(OrderItem orderItem) {
		orderItemRepository.save(orderItem);
	}
	
	public List<OrderItem> getOrderItemInOrder(Long orderId) {
		List<OrderItem> orderItems = new ArrayList<>();
		for (OrderItem item : getAllOrderItems()) {
			if(item.getOrder().getId() == orderId) {
				orderItems.add(item);
			}
		}
		return orderItems;
	}
}
