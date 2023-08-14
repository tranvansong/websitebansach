package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.Cart;
import com.example.demo.dto.CartItem;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class CartService {
	
	public Cart getCartFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
	    Cart cart = (Cart) session.getAttribute("cart");
	    if (cart == null) {
	        cart = new Cart();
	        session.setAttribute("cart", cart);
	    }
	    return cart;
	}

	public void saveCartToSession(Cart cart, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("cart", cart);
    }

}
