package com.lhjundi.domain.usecases.book;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.utils.EntityAlreadyExistsException;
import com.lhjundi.domain.usecases.utils.Notification;
import com.lhjundi.domain.usecases.utils.Validator;

public class CreateBookUseCase {

    private BookDAO bookDAO;

    public CreateBookUseCase(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public Integer insert(Book book){
        Validator<Book> validator = new BookInputRequestValidator();
        Notification notification = validator.validate(book);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String isbn = book.getIsbn();
        if (bookDAO.searchByIsbn(isbn).isPresent())
            throw new EntityAlreadyExistsException("This ISBN is already in use");

        return bookDAO.create(book);
    }
}
