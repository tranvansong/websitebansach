package com.example.demo.restcontroller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Revenue;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@RestController
@CrossOrigin
public class OrderRestController {
	
	@Autowired
	private OrderService orderService;
	
//	@GetMapping("/admin/api/orders")
//	public ResponseEntity<List<Order>> getListOrder() {
//		
//		List<Order> orders = orderService.getListOrders();
//		
//		if(orders.isEmpty()) return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<List<Order>>(orders,HttpStatus.OK);
//	}
	
	@GetMapping("/admin/api/orders")
	public ResponseEntity<List<Order>> getListOrderByYear(@RequestParam(name = "year", required = false) Integer year) {
		if(year == null) {
			return new ResponseEntity<List<Order>>(orderService.getListOrders(), HttpStatus.OK);
		}
		List<Order> list = orderService.getListOrderByYear(year);	
		if(list.isEmpty()) return new ResponseEntity<List<Order>>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<Order>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/admin/api/revenue")
	public ResponseEntity<List<Revenue>> getTotalRevenueOfMonthInYear(@RequestParam(name = "year", required = false) Integer year) {
		List<Revenue> list = new ArrayList<>();
		if(year != null) {
			for(int i = 1;i<=12;i++) {
				int month = i;
				Double total_payment = orderService.getTotalRevenueByMonthInYear(month, year);
				list.add(new Revenue(i, year, total_payment));
			}
			return new ResponseEntity<List<Revenue>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<List<Revenue>>(HttpStatus.NO_CONTENT);
	}
}
