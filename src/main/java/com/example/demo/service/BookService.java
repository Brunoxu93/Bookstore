package com.example.demo.service;

import com.example.demo.dto.BookDto;
import com.example.demo.exceptions.DoesNotExist;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    BookRepository    bookRepo;
    @Autowired
    ModelMapper modelMapper;

    public BookDto postBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        Book book1 = bookRepo.save(book);
        BookDto bookDto1 = modelMapper.map(book1, BookDto.class);
        return bookDto1;
    }

    public BookDto getBookById(Long id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            Book book1 = book.get();
            BookDto bookDto = modelMapper.map(book1, BookDto.class);
            return bookDto;
        } else{
            throw new DoesNotExist("Book Does Not Exist on id : " + id);
        }
    }

    public List<BookDto> getAllBook() {
        List<Book> bookList = bookRepo.findAll();

        return bookList.stream().map(book -> modelMapper.map(
                book, BookDto.class
        )).collect(Collectors.toList());
    }

    public BookDto updateBookById(Long id, BookDto bookDto) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            Book book1 = book.get();
            book1.setTitle(bookDto.getTitle());
            book1.setPrice(bookDto.getPrice());
            Book book2 = bookRepo.save(book1);
            return modelMapper.map(book2, BookDto.class);
        }else{
            throw new DoesNotExist("Book Does Not Exist on id: " + id);
        }
    }

    public void deleteBookById(Long id) {
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            bookRepo.deleteById(id);
        }else{
            throw new DoesNotExist("Book Does Not Exist on id: " + id);
        }

    }
}
