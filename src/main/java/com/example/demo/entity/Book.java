package com.example.demo.entity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "books")
@Getter @Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String author;
	private String description;
	
	@Lob
	private String image;
	
	@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date releaseDate;
	private int numberOfBookpages;
	private int quantity;
	private int sold;
	private float price;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@ToString.Exclude
	@JsonIgnore
	private Category category;

	@OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonIgnore
	private List<Review> reviews = new ArrayList<>();

	public Book(String title, String author, String description, int numberOfBookpages, int quantity,
			int sold, float price, Category category, String releaseDate, MultipartFile image) throws ParseException, IOException {
		this.title = title;
		this.author = author;
		this.description = description;
		this.numberOfBookpages = numberOfBookpages;
		this.quantity = quantity;
		this.sold = sold;
		this.price = price;
		this.category = category;
		this.releaseDate = new SimpleDateFormat("yyyy-MM-dd").parse(releaseDate);
		this.image = Base64.getEncoder().encodeToString(image.getBytes());
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", description=" + description
				+ ", image=" + image + ", releaseDate=" + releaseDate + ", numberOfBookpages=" + numberOfBookpages
				+ ", quantity=" + quantity + ", sold=" + sold + ", price=" + price + ", category=" + category.getName() +
				"]";
	}

	
	
}
