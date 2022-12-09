package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping
	public List<Book> findAllBooks() {
		return bookRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Book> findBookById(@PathVariable(value = "id") long id){
		return bookRepo.findById(id);
	}
	
	@PostMapping
	public void saveBook(@RequestBody Book book) {
		bookRepo.save(book);
			
	}
	
	@PutMapping("/{id}")
	public void updateBook(@RequestBody Book newBook, @PathVariable(value = "id") long id) {
		Optional<Book> book = bookRepo.findById(id);
		if(book.isPresent()) {
			Book book1 = new Book();
			book1.setBookId(id);
			book1.setTitle(newBook.getTitle());
			book1.setAuthor(newBook.getAuthor());
			book1 = bookRepo.save(book1);
		}
	}
	
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable Long id) {
		bookRepo.deleteById(id);
	}
}
