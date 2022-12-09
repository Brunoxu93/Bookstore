package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookRepository bookRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void findAllBooks() throws Exception{
		List<Book> listOfBooks = new ArrayList<>();
		
		listOfBooks.add(Book.builder().title("book1").author("author1").build());
		listOfBooks.add(Book.builder().title("book1").author("author1").build());
        given(bookRepo.findAll()).willReturn(listOfBooks);
        
        ResultActions response = mockMvc.perform(get("/books"));
        
        response.andExpect(status().isOk())
        	.andDo(print())
        	.andExpect(jsonPath("$.size()",
        			is(listOfBooks.size())));
	}
	
	
	@Test
	public void findBookById() throws Exception{
		Book book = Book.builder().title("book1").author("author1").build();
		given(bookRepo.findById((long) 1)).willReturn(Optional.of(book));
		
		ResultActions response = mockMvc.perform(get("/books/{id}", (long) 1));
		
		response.andExpect(status().isOk())
			.andDo(print())
			.andExpect(jsonPath("$.title", is(book.getTitle())))
			.andExpect(jsonPath("$.author", is(book.getAuthor())));
	}
	
	@Test
	public void postNewBook() throws Exception{
		Book book = Book.builder().title("book1").author("author1").build();
		given(bookRepo.save(any(Book.class)))
			.willAnswer((invocation) -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(post("/books")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(book)));
		
		response.andExpect(status().isOk())
			.andDo(print());
	}
	
	
	@Test
	public void updateBookById() throws Exception{
		Book book = Book.builder().title("book1").author("author1").build();
		Book updatedBook = Book.builder().title("book2").author("author1").build();
		given(bookRepo.findById((long) 1)).willReturn(Optional.of(book));
		given(bookRepo.save(any(Book.class)))
		.willAnswer((invocation) -> invocation.getArgument(0));
		
		ResultActions response = mockMvc.perform(put("/books/{id}", (long) 1)
				.contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(updatedBook)));
		
		response.andExpect(status().isOk())
			.andDo(print());
	}
	
	@Test
	public void deleteBookById() throws Exception{
		
		willDoNothing().given(bookRepo).deleteById((long) 1);
		
		ResultActions response = mockMvc.perform(delete("/books/{id}", (long) 1));
		
		response.andExpect(status().isOk())
			.andDo(print());
	}
	

}
