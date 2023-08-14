package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE b.title = :title")
	public Book findBookByTitle(@Param("title") String title);
}
