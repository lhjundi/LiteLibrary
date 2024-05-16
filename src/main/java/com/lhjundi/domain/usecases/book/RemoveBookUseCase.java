package com.lhjundi.domain.usecases.book;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.utils.EntityNotFoundException;

public class RemoveBookUseCase {
    private BookDAO bookDAO;

    public RemoveBookUseCase(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    }

    public boolean remove(Integer id){
        if (id == null || bookDAO.searchOne(id).isEmpty())
            throw new EntityNotFoundException("Book not found.");
        return bookDAO.deleteByKey(id);
    }

    public boolean remove(Book book){
        if (book == null || bookDAO.searchOne(book.getId()).isEmpty())
            throw new EntityNotFoundException("Book not found.");
        return bookDAO.delete(book);
    }
}
