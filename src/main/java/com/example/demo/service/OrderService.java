package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	public List<Order> getListOrders() {
		return orderRepository.findAll();
	}
	
	public void saveOrder(Order order) {
		orderRepository.save(order);
	}
	
	public Order getOrderById(Long id) {
		return orderRepository.findById(id).orElse(null);
	}
	
	public List<Order> getListOrderByUser(String username) { // vi username la unique
		List<Order> orders = getListOrders();
		List<Order> ordersByUser = new ArrayList<>();
		for (Order order : orders) {
			if(order.getUser().getName().equals(username)) {
				ordersByUser.add(order);
			}
		}
		return ordersByUser;
	}
	
	
	public List<Order> getListOrderByKeyword(String keyword) {
		return orderRepository.getListOrderByKeyword(keyword);
	}
	
	
	public List<Order> getListOrderByYear(int year) {
		return orderRepository.getListOrderByYear(year);
	}
	
	public Double getTotalRevenueByMonthInYear(int month, int year) {
		if(orderRepository.getTotalRevenueByMonthInYear(month, year) == null)
			return 0.0;
		return orderRepository.getTotalRevenueByMonthInYear(month, year);
	}
	
	public Long getOrderCountInMonthByYear(int month, int year) {
		return orderRepository.getOrderCountInMonthByYear(month, year);
	}
}
