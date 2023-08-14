package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.ToString;

@ToString
public class Cart {
	
	private List<CartItem> cartItems = new ArrayList<>();
	
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	
	public void addItemtoCart(CartItem item) {
		CartItem cartItem = findItemById(item.getBook().getId());
		if(cartItem != null) cartItem.setQuantity(cartItem.getQuantity()+1);
		else cartItems.add(item);
	}
	
	public CartItem findItemById(Long bookId) {
		for (CartItem cartItem : cartItems) {
			if(cartItem.getBook().getId() == bookId) return cartItem;
		}
		return null;
	}
	
	public void removeItemtoCart(Long bookId) {
		cartItems.remove(this.findItemById(bookId));
	}
	
	public double getTotalPayment() {
		double totalPrice = 0;
		for (CartItem cartItem : cartItems) {
			totalPrice+=cartItem.totalPrice();
		}
		return totalPrice;
	}

}
