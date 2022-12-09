package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@SpringBootApplication
public class BookstoreApplication {
	
	@Autowired
	private BookRepository bookRepo;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	

}
