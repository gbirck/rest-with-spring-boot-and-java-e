package com.gabriel.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gabriel.rest.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.title LIKE LOWER(CONCAT ('%',:title,'%'))")
    List<Book> findBookByTitle(@Param("title") String title);

    @Query("SELECT b FROM Book b WHERE b.author LIKE LOWER(CONCAT ('%',:author,'%'))")
    List<Book> findBooksByAuthor(@Param("author") String author);
}