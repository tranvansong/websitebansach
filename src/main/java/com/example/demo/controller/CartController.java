package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Cart;
import com.example.demo.dto.CartItem;
import com.example.demo.entity.Book;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderItem;
import com.example.demo.entity.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.security.MyUserDetails;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@GetMapping("/giohang")
	public String viewCart(HttpServletRequest request, Model model) {
		Cart cart = cartService.getCartFromSession(request);
		List<CartItem> cartItems = cart.getCartItems();
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("cart", cart);
		model.addAttribute("totalPrice", cart.getTotalPayment());
		model.addAttribute("numberOfItems", cartItems.size());
		cartService.saveCartToSession(cart, request);
		return "cart";
	}
	
	@RequestMapping("/updateCart/{id}")
	public String updateCart(HttpServletRequest request, @PathVariable("id") Long id) {
		System.out.println(id);
		return "redirect:/giohang";
	}
	
	@RequestMapping("/removeItem/{id}")
	public String removeItemFromCart(HttpServletRequest request, @PathVariable("id") Long bookId) {
		Cart cart = cartService.getCartFromSession(request);
		cart.removeItemtoCart(bookId);
		return "redirect:/giohang";
	}
	
	@RequestMapping("/addtoCart/{bookId}")
	public String addtoCart(@PathVariable("bookId") Long bookId,
							@RequestParam(name = "quantity", defaultValue = "1") int quantity,
							RedirectAttributes redirectAttributes,
							HttpServletRequest request) {
		Book book = bookService.getBookById(bookId);
		Cart cart = cartService.getCartFromSession(request);
		CartItem cartItem = new CartItem(book, quantity, book.getPrice());
		cart.addItemtoCart(cartItem);
		redirectAttributes.addFlashAttribute("message", "Thêm sách vào giỏ hàng thành công!");
		return "redirect:/trangchu";
	}
	
	@RequestMapping("/muangay/{id}")
	public String muaNgay(@PathVariable("id") Long id, @RequestParam(name = "quantity", defaultValue = "1") int quantity, Model model) {
		Book foundBook = bookService.getBookById(id);
		CartItem cartItems = new CartItem(foundBook, quantity, foundBook.getPrice());
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", cartItems.totalPrice());
		return "checkout";
	}
	
	@PostMapping("/checkout")
	public String checkout(@RequestParam("quantity") int[] quantities, HttpServletRequest request, Model model) {
		Cart cart = cartService.getCartFromSession(request);
		for (int i = 0; i< cart.getCartItems().size();i++) {
			cart.getCartItems().get(i).setQuantity(quantities[i]);
		}
		model.addAttribute("cartItems", cart.getCartItems());
		model.addAttribute("totalPrice", cart.getTotalPayment());
		return "checkout";
	}

}
