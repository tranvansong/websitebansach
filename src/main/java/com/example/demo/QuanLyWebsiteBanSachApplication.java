package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class QuanLyWebsiteBanSachApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuanLyWebsiteBanSachApplication.class, args);
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		
//		String rawPass = "user";
//		System.out.println(bCryptPasswordEncoder.encode(rawPass));
	}

}
