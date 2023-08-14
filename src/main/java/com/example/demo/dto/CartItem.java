package com.example.demo.dto;

import com.example.demo.entity.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	private Book book;
	private int quantity;
	private double price;
	
	public double totalPrice() {
		return quantity*price;
	}
}
