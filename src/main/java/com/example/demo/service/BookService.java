package com.example.demo.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	// save book
	public void save(BookDTO bookDTO) throws ParseException, IOException {
		Book book = new Book();
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setDescription(bookDTO.getDescription());
		book.setImage(Base64.getEncoder().encodeToString(bookDTO.getImage().getBytes()));
		book.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookDTO.getReleaseDate()));
		book.setPrice(bookDTO.getPrice());
		book.setNumberOfBookpages(bookDTO.getNumberOfBookpages());
		book.setCategory(bookDTO.getCategory());
		bookRepository.save(book);
	}
	
	// update book
	public void update(BookDTO bookDTO, Long id) throws IOException, ParseException {
		Book book = this.getBookById(id);
		book.setTitle(bookDTO.getTitle());
		book.setAuthor(bookDTO.getAuthor());
		book.setDescription(bookDTO.getDescription());
		book.setNumberOfBookpages(bookDTO.getNumberOfBookpages());
		if(bookDTO.getImage().getContentType().equals("application/octet-stream")) {
			book.setImage(book.getImage());
		}else {			
			book.setImage(Base64.getEncoder().encodeToString(bookDTO.getImage().getBytes()));
		}
		book.setCategory(bookDTO.getCategory());
		book.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(bookDTO.getReleaseDate()));
		book.setPrice(bookDTO.getPrice());
		bookRepository.save(book);
	}
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll(); 
	}
	
	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
	
	public Book getBookByTitle(String bookTitle) {
		return bookRepository.findBookByTitle(bookTitle);
	}
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	public void deleteBookById(Long id) {
		bookRepository.deleteById(id);
	}
	
	public boolean checkDuplicateBook(String title, String author) {
		for(Book book: this.getAllBooks()) {
			if(book.getTitle().equals(title) && book.getAuthor().equals(author)) return true;
		}
		return false;
	}
	
	public List<Book> getBooksByKeyword(String keyword) {
		List<Book> list = new ArrayList<Book>();
		String kw = keyword.toLowerCase();
		for(Book book: this.getAllBooks()) {
			if(book.getTitle().toLowerCase().contains(kw) || book.getAuthor().toLowerCase().contains(kw)) {
				list.add(book);
			}
		}
		return list;
	}
}
