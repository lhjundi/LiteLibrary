package com.lhjundi.domain.usecases.book;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.usecases.utils.EntityNotFoundException;
import com.lhjundi.domain.usecases.utils.Notification;
import com.lhjundi.domain.usecases.utils.Validator;

public class UpdateBookUseCase {
    private BookDAO bookDAO;

    public UpdateBookUseCase(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public boolean update (Book book){
        Validator<Book> validator = new BookInputRequestValidator();
        Notification notification = validator.validate(book);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        Integer id = book.getId();
        if (bookDAO.searchOne(id).isEmpty())
            throw new EntityNotFoundException("Book  not found.");

        return bookDAO.update(book);
    }
}
