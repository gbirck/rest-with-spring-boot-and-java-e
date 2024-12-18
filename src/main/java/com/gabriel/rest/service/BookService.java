package com.gabriel.rest.service;

import java.util.List;

import com.gabriel.rest.controller.BookController;
import com.gabriel.rest.controller.PersonController;
import com.gabriel.rest.exceptions.ResourceNotFoundException;
import com.gabriel.rest.mapper.DozerMapper;
import com.gabriel.rest.model.Book;
import com.gabriel.rest.model.dto.BookDTO;
import com.gabriel.rest.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> findAll() {
        var persons = DozerMapper.parseListObjects(bookRepository.findAll(), BookDTO.class);
        persons
                .stream()
                .forEach(p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
        return persons;
    }

    public BookDTO findById(Long id) {

        var entity = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID" + id + "not found"));
        var dto = DozerMapper.parseObject(entity, BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return dto;
    }

    public BookDTO insert(BookDTO book) {

        System.out.println("Persisting: " + book);
        var entity = DozerMapper.parseObject(book, Book.class);
        var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(BookController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public BookDTO update(BookDTO book) {

        var entity = DozerMapper.parseObject(book, Book.class);
        var dto = DozerMapper.parseObject(bookRepository.save(entity), BookDTO.class);
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getKey())).withSelfRel());
        return dto;
    }

    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
