package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.name = :name")
	public User getUserByName(@Param("name") String name);
	
	@Query("SELECT u FROM User u WHERE u.name LIKE %:keyword% "
			+ "OR u.email LIKE %:keyword%")
	public List<User> getListUserByKeyword(@Param("keyword") String keyword);
	
}
