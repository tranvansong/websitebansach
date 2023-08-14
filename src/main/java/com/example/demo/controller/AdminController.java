package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;
import com.example.demo.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/admin/dashboard")
	public String showDashboard() {
		
		return "admin/dashboard";
	}
	
	@RequestMapping("/admin/statistic")
	public String getStatisticPage() {
		return "admin/statistic";
	}
	
	@RequestMapping("/admin/order_manager")
	public String getOrderManagePage(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<Order> orders = orderService.getListOrders();
		if(keyword != null) {
			orders = orderService.getListOrderByKeyword(keyword);
		}
		model.addAttribute("name", keyword);
		model.addAttribute("orders", orders);
		
		return "admin/order_manager";
	}
	
	@RequestMapping("/admin/order_detail/{id}")
	public String getDetailOrder(@PathVariable("id") Long orderId, Model model) {
		Order order = orderService.getOrderById(orderId);
		List<OrderItem> orderItems = orderItemService.getOrderItemInOrder(orderId);
		model.addAttribute("order", order);
		model.addAttribute("orderItems", orderItems);
		for (OrderItem orderItem : orderItems) {
			System.out.println(orderItem.getQuantity());
		}
		return "admin/order_detail";
	}
	
	@RequestMapping("/admin/cancelOrder/{id}")
	public String cancelOrder(@PathVariable("id") Long orderId) {
		Order order = orderService.getOrderById(orderId);
		order.setStatus(null);
		String link = "redirect:/admin/order_detail/" + orderId;
		orderService.saveOrder(order);
		return link;
	}
	
	@RequestMapping("/admin/browseOrder/{id}")
	public String browseOrder(@PathVariable("id") Long orderId) {
		Order order = orderService.getOrderById(orderId);
		order.setStatus(true);
		String link = "redirect:/admin/order_detail/" + orderId;
		orderService.saveOrder(order);
		return link;
	}
	
	@RequestMapping("/admin/user_manager")
	public String managerUser(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
		List<User> users = userService.getUsers();
		if(keyword != null) {
			users = userService.getListUserByKeyword(keyword);
		}
		model.addAttribute("users", users);
		return "admin/user_manager";
	}
	
	@RequestMapping("/admin/createNewUser")
	public String createNewUser(Model model) {
		model.addAttribute("user", new User());
		
		return "admin/newUserForm";
	}
	
	@RequestMapping("/admin/saveUser")
	public String saveUser(@ModelAttribute("user") User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/admin/user_manager";
	}
	
	@RequestMapping("/admin/updateUser/{id}")
	public String updateUser(@PathVariable("id") Long userId, Model model) {
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		
		return "admin/updateUserForm";
	}
}
