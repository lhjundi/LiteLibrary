package com.lhjundi.domain.usecases.book;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.utils.DAO;

import java.util.Optional;

public interface BookDAO extends DAO<Book, Integer> {
    Optional<Book> searchByIsbn(String isbn);



}
