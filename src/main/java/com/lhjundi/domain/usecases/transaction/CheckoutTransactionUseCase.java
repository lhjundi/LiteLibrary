package com.lhjundi.domain.usecases.transaction;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.entities.book.BookStatus;
import com.lhjundi.domain.entities.transaction.Transaction;
import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.book.SearchBookUseCase;
import com.lhjundi.domain.usecases.book.UpdateBookUseCase;
import com.lhjundi.domain.usecases.user.SearchUserUseCase;
import com.lhjundi.domain.usecases.user.UpdateUserUseCase;
import com.lhjundi.domain.usecases.utils.EntityNotFoundException;

public class CheckoutTransactionUseCase {

    private TransactionDAO transactionDAO;
    private SearchUserUseCase searchUserUseCase;
    private SearchBookUseCase searchBookUseCase;
    private UpdateUserUseCase updateUserUseCase;
    private UpdateBookUseCase updateBookUseCase;

    public CheckoutTransactionUseCase(
            TransactionDAO transactionDAO,
            SearchUserUseCase searchUserUseCase,
            SearchBookUseCase searchBookUseCase,
            UpdateUserUseCase updateUserUseCase,
            UpdateBookUseCase updateBookUseCase) {
        this.transactionDAO = transactionDAO;
        this.searchUserUseCase = searchUserUseCase;
        this.searchBookUseCase = searchBookUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.updateBookUseCase = updateBookUseCase;
    }

    public Transaction checkoutABook(String userId, Integer bookId){
        if (userId == null || bookId == null)
            throw new IllegalArgumentException("User ID and/or Book ID are/is null");

        Book book = searchBookUseCase.search(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a book with id " + bookId));

        User user = searchUserUseCase.seachOne(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find a user with id " + userId));

        if (book.getStatus() == BookStatus.CHECKED_OUT)
            throw new TransactionNotAllowedException("The book with ID " + bookId + "is unavailable");

        if (!user.isAbleToCheckOut())
            throw new TransactionNotAllowedException("The user with ID " + userId + "is exceeded the check out limit.");

        Transaction checkoutTransaction = new Transaction(book, user, user.getCheckoutTimeLimitInDays());
        Integer transactionId = transactionDAO.create(checkoutTransaction);

        book.setStatus(BookStatus.CHECKED_OUT);
        updateBookUseCase.update(book);

        user.increaseNumberOfBooksToCheckout();
        updateUserUseCase.update(user);

        return transactionDAO.searchOne(transactionId).get();
    }
}
