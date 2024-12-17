package com.gabriel.rest.controller;

import java.util.List;

import com.gabriel.rest.model.dto.BookDTO;
import com.gabriel.rest.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/book")
public class BookController {
	
	@Autowired
	private BookService bookService;

	@GetMapping
	public List<BookDTO> findAll() {
		return bookService.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public BookDTO findById(@PathVariable(value = "id") Long id) {
		return bookService.findById(id);
	}
	
	@PostMapping
	public BookDTO create(@RequestBody BookDTO book) {
		return bookService.insert(book);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<BookDTO> update(@PathVariable Long id, @RequestBody @Valid BookDTO book) {
		book.setKey(id);
		bookService.update(book);
		return ResponseEntity.ok(book);
	}
	
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		bookService.delete(id);
		return ResponseEntity.noContent().build();
	}
}