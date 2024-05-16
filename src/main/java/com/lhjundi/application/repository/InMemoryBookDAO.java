package com.lhjundi.application.repository;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.book.BookDAO;

import java.util.*;

public class InMemoryBookDAO implements BookDAO {

    private static final Map<Integer, Book> db = new LinkedHashMap<>();
    private static int idCounter;
    @Override
    public Integer create(Book book) {
        idCounter++;
        book.setId(idCounter);
        db.put(idCounter, book);
        return idCounter;
    }

    @Override
    public Optional<Book> searchOne(Integer key) {
        if (db.containsKey(key))
            return Optional.of(db.get(key));
        return Optional.empty();
    }

    @Override

    public Optional<Book> searchByIsbn(String isbn) {
        return db.values().stream()
                .filter(book-> book.getIsbn().equals(isbn))
                .findAny();
    }

    @Override
    public List<Book> searchAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(Book book) {
        Integer id = book.getId();
        if (db.containsKey(id)) {
            db.replace(id, book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteByKey(Integer key) {
        if (db.containsKey(key)){
            db.remove(key);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Book book) {
        return deleteByKey(book.getId());
    }

}
