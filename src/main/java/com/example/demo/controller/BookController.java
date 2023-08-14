package com.example.demo.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BookDTO;
import com.example.demo.entity.Book;
import com.example.demo.entity.Category;
import com.example.demo.entity.OrderItem;
import com.example.demo.service.BookService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.OrderItemService;
import com.example.demo.service.OrderService;

@Controller
public class BookController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BookService bookService;
	
	@GetMapping("/admin/view-list-books")
	public String getListBookInAdminPage(@RequestParam(name = "keyword", required = false) String keyword ,Model model) throws ParseException {
		List<Book> books = bookService.getAllBooks();
		
		if(keyword != null) {
			books = bookService.getBooksByKeyword(keyword);
		}
		System.out.println(keyword);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication.isAuthenticated();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ADMIN"));
        model.addAttribute("loggedIn",loggedIn);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "books";
	}
	
	
	@GetMapping("/admin/newBook")
	public String newBookForm(Model model) {
		model.addAttribute("book", new BookDTO());
		model.addAttribute("id", -1);
		model.addAttribute("categories", categoryService.getAllCategories());
		return "book-detail";
	}
	
	@RequestMapping("/admin/saveBook")
	public String saveBook(Model model, BookDTO bookDTO, @RequestParam(name = "id", defaultValue = "-1") Long id) throws ParseException, IOException {
		
		System.out.println(id);
		if(id == -1L) {
			System.out.println(bookDTO);
			if(bookService.checkDuplicateBook(bookDTO.getTitle(), bookDTO.getAuthor())) {
				model.addAttribute("errorMessage", "Sách và tác giả đã tồn tại");
				model.addAttribute("book", new BookDTO(bookDTO.getTitle(), bookDTO.getAuthor(), bookDTO.getDescription(), bookDTO.getReleaseDate(), bookDTO.getPrice(), bookDTO.getNumberOfBookpages()));
				model.addAttribute("categories", categoryService.getAllCategories());
				return "book-detail";
			}
			System.out.println("da tao moi book");
			bookService.save(bookDTO);
		}else {
			System.out.println("da update sach co id la " + id);
			bookService.update(bookDTO, id);
			System.out.println(bookService.getBookById(id));
		}
		return "redirect:/admin/view-list-books";
	}
	
	@GetMapping("/admin/view-book/{id}")
	public String viewBook(Model model, @PathVariable("id") Long id) {
//		System.out.println(bookService.updateBook(book, id));
		model.addAttribute("book", bookService.getBookById(id));
		model.addAttribute("categories", categoryService.getAllCategories());
		System.out.println(bookService.getBookById(id));
		return "book-detail";
	}
	
	@GetMapping("/admin/delete/{id}")
	public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		System.out.println(id);
		try {
			bookService.deleteBookById(id);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorDelete", "Không thể xóa do sách đang nằm trong 1 đơn hàng");
		}
		return "redirect:/admin/view-list-books";
	}
}
