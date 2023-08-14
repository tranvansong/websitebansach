package com.example.demo.entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@CreationTimestamp
	private Date order_date;
	
	private Boolean status;
	private double total_payment; // tong gia
	private String hoten;
	private String email;
	private String sodt;
	private String diachi;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User user;
	
	public Order(User user, Boolean status, double total_payment, String hoten, String email, String sodt, String diachi) {
		this.user = user;
		this.status = status;
		this.total_payment = total_payment;
		this.hoten = hoten;
		this.email = email;
		this.sodt = sodt;
		this.diachi = diachi;
	}
}
