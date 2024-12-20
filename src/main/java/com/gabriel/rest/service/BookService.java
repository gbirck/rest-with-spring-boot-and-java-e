package com.gabriel.rest.service;

import java.util.List;

import com.gabriel.rest.controller.BookController;
import com.gabriel.rest.exceptions.ResourceNotFoundException;
import com.gabriel.rest.mapper.DozerMapper;
import com.gabriel.rest.model.Book;
import com.gabriel.rest.model.dto.BookDTO;
import com.gabriel.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public List<BookDTO> findByAuthor(String author) {
        var books = bookRepository.findBooksByAuthor(author);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("Author " + author + " not found");
        }

        var booksdtos = DozerMapper.parseListObjects(books, BookDTO.class);
        booksdtos.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return booksdtos;
    }

    @Transactional
    public List<BookDTO> findByTitle(String title) {
        var books = bookRepository.findBookByTitle(title);

        if (books.isEmpty()) {
            throw new ResourceNotFoundException("No books found with title: " + title);
        }

        var booksdtos = DozerMapper.parseListObjects(books, BookDTO.class);
        booksdtos.forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return booksdtos;
    }

    @Transactional
    public List<BookDTO> findAll() {
        var books = DozerMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
        books
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return books;
    }

    @Transactional
    public BookDTO findById(Long id) {

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID" + id + "not found"));
        var dto = DozerMapper.parseObject(entity, BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    @Transactional
    public BookDTO insert(BookDTO book) {

        System.out.println("Persisting: " + book);
        var entity = DozerMapper.parseObject(book, Book.class);
        var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    @Transactional
    public BookDTO update(BookDTO book) {

        var entity = DozerMapper.parseObject(book, Book.class);
        var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
