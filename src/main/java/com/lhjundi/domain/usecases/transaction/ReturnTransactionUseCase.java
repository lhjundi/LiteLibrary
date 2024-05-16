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

import java.time.LocalDate;

public class ReturnTransactionUseCase {
    private TransactionDAO transactionDAO;
    private SearchUserUseCase searchUserUseCase;
    private SearchBookUseCase searchBookUseCase;

    private UpdateUserUseCase updateUserUseCase;
    private UpdateBookUseCase updateBookUseCase;

    public ReturnTransactionUseCase(TransactionDAO transactionDAO,
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

    public void returnBook(Integer bookId){
        if (bookId == null) throw new IllegalArgumentException("Book ID is null");

        Transaction transaction = transactionDAO.searchTransactionByBookId(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Can not find an open transaction for book ID " + bookId));

        transaction.setReturnDate(LocalDate.now());
        transactionDAO.update(transaction);

        Book book = searchBookUseCase
                .search(bookId).get();
        book.setStatus(BookStatus.AVAILABLE);
        updateBookUseCase.update(book);

        String userId = transaction.getUser().getInstitutionalId();
        User user = searchUserUseCase.seachOne(userId).get();
        user.decreaseNumberOfBooksCheckedOut();
        updateUserUseCase.update(user);

    }
}
