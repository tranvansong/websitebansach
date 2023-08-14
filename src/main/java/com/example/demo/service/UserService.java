package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User saveUser(User user) {
		user.setRole("USER");
		user.setPassword(this.encodePassword(user.getPassword()));
		return userRepository.save(user);
	}

	public boolean checkDuplicateName(User user) {
		for (User u : this.getUsers()) {
			if(u.getName().equals(user.getName())) return true;
		}
		return false;
	}
	
	public String encodePassword(String password) {
		return new BCryptPasswordEncoder().encode(password);
	}
	
	public List<User> getListUserByKeyword(String keyword) {
		return userRepository.getListUserByKeyword(keyword);
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
}
