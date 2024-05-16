package com.lhjundi.domain.usecases.book;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;

public class SearchBookUseCase {
    private BookDAO bookDAO;

    public SearchBookUseCase(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public Optional<Book> search(Integer id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null");
        return bookDAO.searchOne(id);
    }

    public Optional<Book> searchOneByIsbn(String isbn){
        if (Validator.nullOrEmpty(isbn))
            throw new IllegalArgumentException("ISBN can not be null or empty");
        return bookDAO.searchByIsbn(isbn);
    }

    public List<Book> searchAll(){
        return bookDAO.searchAll();
    }
}
