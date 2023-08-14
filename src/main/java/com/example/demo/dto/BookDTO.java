package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookDTO {
	
	private Long id;
	private String title;
	private String author;
	private String description;
	private MultipartFile image;
	private String releaseDate;
	private int numberOfBookpages;
	private float price;
	private Category category;
	
	public BookDTO(String title, String author, String description, String releaseDate, float price, int numberOfBookpages) {
		super();
		this.title = title;
		this.author = author;
		this.description = description;
		this.releaseDate = releaseDate;
		this.price = price;
		this.numberOfBookpages = numberOfBookpages;
	}
	
	
}
