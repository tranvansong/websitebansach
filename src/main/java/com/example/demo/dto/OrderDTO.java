package com.example.demo.dto;

import java.text.SimpleDateFormat;

import com.example.demo.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDTO {

	private Long id;
	private String order_date;
	private String hoten;
	private String email;
	private String sodt;
	private String diachi;
	private boolean status;
	private double total_payment;
	
	public OrderDTO(Order order) {
		this.id = order.getId();
		this.order_date = new SimpleDateFormat("yyyy-MM-dd").format(order.getOrder_date());
		this.hoten = order.getHoten();
		this.email = order.getEmail();
		this.sodt = order.getSodt();
		this.diachi = order.getDiachi();
		this.status = order.getStatus();
		this.total_payment = order.getTotal_payment();
	}
}
