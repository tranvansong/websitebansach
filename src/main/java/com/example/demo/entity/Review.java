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
@Table(name = "reviews")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String comment;
	private int rating;
	
	@CreationTimestamp
	private Date review_date;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@ToString.Exclude
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	@ToString.Exclude
	private Book book;
	
	public Review(String comment, int rating, User user, Book book) {
		this.comment = comment;
		this.rating = rating;
		this.user = user;
		this.book = book;
	}
}
