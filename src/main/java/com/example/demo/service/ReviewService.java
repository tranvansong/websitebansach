package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Review;
import com.example.demo.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	public List<Review> getListReviews() {
		return reviewRepository.findAll();
	}
	
	public void saveReview(Review review) {
		reviewRepository.save(review);
	}
	
	public List<Review> getReviewsOfBook(Long bookId) {
		List<Review> list = new ArrayList<>();
		for (Review review : this.getListReviews()) {
			if(review.getBook().getId() == bookId) {
				list.add(review);
			}
		}
		return list;
	}
}
