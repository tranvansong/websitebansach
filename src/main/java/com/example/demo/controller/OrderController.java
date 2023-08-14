package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.Cart;
import com.example.demo.dto.CartItem;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@PostMapping("/order")
	public String order(HttpServletRequest request,
						Authentication authentication,
						@RequestParam("name") String hoten,
						@RequestParam("email") String email,
						@RequestParam("address") String diachi,
						@RequestParam("phone") String phone) {
		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		User user = myUserDetails.getUser();
		System.out.println(user);
		Cart cart = cartService.getCartFromSession(request);
		Order order = new Order(user, false, cart.getTotalPayment(), hoten, email, phone, diachi);
		orderService.saveOrder(order);
		System.out.println(order);
		for (CartItem item : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			Book book = item.getBook();
			orderItem.setBook(item.getBook());
			orderItem.setOrder(order);
			orderItem.setQuantity(item.getQuantity());
			orderItem.setPrice(item.getPrice());
			orderItemService.saveItem(orderItem);
			
			book.setSold(book.getSold()+item.getQuantity());
			bookService.saveBook(book);
			System.out.println(item.getBook().getTitle() + " " + item.getQuantity());
		}
		System.out.println(hoten + " " + email + " " + diachi + " " + phone);
		return "orderSuccess";
	}
	
	@GetMapping("/lich-su-mua-hang")
	public String viewOrderHistory(Model model, Authentication authentication) {
		String username = authentication.getName();
		System.out.println(username);
		List<Order> ordersByUser = orderService.getListOrderByUser(username);
		for (Order order : ordersByUser) {
			System.out.println(order.getId());
		}
		model.addAttribute("orders", ordersByUser);
		return "order-history";
	}
	
	@GetMapping("/chi-tiet-don-hang/{orderId}")
	public String viewDetailOrder(@PathVariable("orderId") Long orderId, Model model) {
		List<OrderItem> items = orderItemService.getOrderItemInOrder(orderId);
		model.addAttribute("cartItems", items);
		model.addAttribute("order", orderService.getOrderById(orderId));
		return "order-detail";
	}
	
	@GetMapping("/huy-don-hang/{orderId}")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		Order order = orderService.getOrderById(orderId);
		order.setStatus(null);
		orderService.saveOrder(order);
		System.out.println(order);
		return "redirect:/lich-su-mua-hang";
	}
}
