package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.Cart;
import com.example.demo.entity.Book;
import com.example.demo.entity.Review;
import com.example.demo.entity.User;
import com.example.demo.security.MyUserDetails;
import com.example.demo.service.BookService;
import com.example.demo.service.CartService;
import com.example.demo.service.ReviewService;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private CartService cartService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	

	@GetMapping("/accessDenied")
	public String accessDeniedPage() {
		return "error/403";
	}
	
	
	@GetMapping("/trangchu")
	public String trangchu(Model model, @AuthenticationPrincipal MyUserDetails user, HttpServletRequest request, @RequestParam(name = "keyword", defaultValue = "") String keyword) {
		List<Book> books = bookService.getAllBooks();
		if(keyword.isEmpty()) model.addAttribute("books", books);
		else {
			List<Book> listBooksFound = bookService.getBooksByKeyword(keyword);
			model.addAttribute("books", listBooksFound);
		}
		model.addAttribute("numberItems", cartService.getCartFromSession(request).getCartItems().size());
		return "trangchu";
	}

	@GetMapping("/dangky")
	public String formDangky(Model model) {
		model.addAttribute("user", new User());
		return "dangky";
	}
	
	@RequestMapping("/saveUser")
	public String saveUser(@ModelAttribute("user") User user, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if(userService.checkDuplicateName(user)) {
			bindingResult.rejectValue("name", "error.user", "* Tên đã tồn tại");
		}
		if (bindingResult.hasErrors()) {
			model.addAttribute("user", user);
	        return "dangky";
	    }
		System.out.println(user);
		userService.saveUser(user);
		redirectAttributes.addFlashAttribute("successed", "Bạn đã đăng ký thành công! Hãy đăng nhập lại");
		return "redirect:/login";
	}
	
	@GetMapping("/chi-tiet-sach/{bookTitle}")
	public String viewDetailBook(@PathVariable("bookTitle") String bookTitle, Model model) {
		Book book = bookService.getBookByTitle(bookTitle);
		List<Review> reviews = reviewService.getReviewsOfBook(book.getId());
		model.addAttribute("book", book);
		model.addAttribute("reviews", reviews);
		
		boolean isEmpty = false;
		if(reviews.isEmpty()) isEmpty = true;
		model.addAttribute("isEmpty", isEmpty);
		return "book-detail-client";
	}
	
	// send review
	@PostMapping("/sendReview/{bookId}")
	public String sendReview(@PathVariable("bookId") Long bookId, 
							@RequestParam("comment") String comment,
							@RequestParam("rating") int rating,
							Authentication authentication,
							RedirectAttributes redirectAttributes) {
		
		MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
		User user = myUserDetails.getUser();
		Book book = bookService.getBookById(bookId);
		Review review = new Review(comment, rating, user, book);
		reviewService.saveReview(review);
		System.out.println(review);
		redirectAttributes.addAttribute("bookTitle", book.getTitle());
	    return "redirect:/chi-tiet-sach/{bookTitle}";
	}
}
