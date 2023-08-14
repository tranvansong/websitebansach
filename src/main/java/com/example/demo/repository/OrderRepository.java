package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	@Query("SELECT o FROM Order o ORDER BY o.order_date DESC")
	public List<Order> findAll();
	

	@Query("SELECT o FROM Order o WHERE YEAR(o.order_date) = :year")
	public List<Order> getListOrderByYear(@Param("year") int year);
	
	@Query("SELECT SUM(o.total_payment) FROM Order o WHERE MONTH(o.order_date) = :month AND YEAR(o.order_date) = :year")
	public Double getTotalRevenueByMonthInYear(@Param("month") int month, @Param("year") int year);
	
	@Query("SELECT COUNT(o.id) FROM Order o WHERE MONTH(o.order_date) = :month and YEAR(o.order_date) = :year")
	public Long getOrderCountInMonthByYear(@Param("month") int month, @Param("year") int year);
	
	
	@Query("SELECT o FROM Order o WHERE o.hoten LIKE %:keyword%"
			+ " OR o.email LIKE %:keyword%"
			+ " OR o.sodt LIKE %:keyword%")
	public List<Order> getListOrderByKeyword(@Param("keyword") String keyword);
}
