package com.gabriel.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabriel.rest.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}