package com.lhjundi.application.main;

import com.lhjundi.application.repository.InMemoryBookDAO;
import com.lhjundi.application.repository.InMemoryTransactionDAO;
import com.lhjundi.application.repository.InMemoryUserDAO;
import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.entities.book.BookGender;
import com.lhjundi.domain.entities.book.BookStatus;
import com.lhjundi.domain.entities.user.Faculty;
import com.lhjundi.domain.entities.user.Student;
import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.book.*;
import com.lhjundi.domain.usecases.transaction.CheckoutTransactionUseCase;
import com.lhjundi.domain.usecases.transaction.FindTransactionUseCase;
import com.lhjundi.domain.usecases.transaction.ReturnTransactionUseCase;
import com.lhjundi.domain.usecases.transaction.TransactionDAO;
import com.lhjundi.domain.usecases.user.*;

public class Main {

    private static CreateBookUseCase createBookUseCase;
    private static SearchBookUseCase searchBookUseCase;
    private static UpdateBookUseCase updateBookUseCase;
    private static RemoveBookUseCase removeBookUseCase;

    private static CreateUserUseCase createUserUseCase;
    private static SearchUserUseCase searchUserUseCase;
    private static UpdateUserUseCase updateUserUseCase;
    private static RemoveUserUseCase removeUserUseCase;

    private static CheckoutTransactionUseCase checkoutTransactionUseCase;
    private static ReturnTransactionUseCase returnTransactionUseCase;
    private static FindTransactionUseCase findTransactionUseCase;


    public static void main(String[] args) {
        configureInjection();

        User user1 = new Student("SC001", "John Merry", "john@email.com", "16 99999 888", "Computer Science");
        User user2 = new Faculty("SC002", "Marie Merry", "marie@email.com", "16999999", "Computer Systems");

        createUserUseCase.insert(user1);
        createUserUseCase.insert(user2);

        Book book1 = new Book(
                1, 300, "Java in Action", "Paul & Jane", "San Charles Books", "1111", BookGender.TECHNICAL, BookStatus.AVAILABLE);
        Book book2 = new Book(
                2, 201, "OOP History",    "Jack Numb",   "San Paulus Books",  "1112", BookGender.HISTORY, BookStatus.AVAILABLE);
        Book book3 = new Book(
                3, 501, "Dependence Injection", "Blackthorn", "Up Books",        "1113", BookGender.ACTION, BookStatus.AVAILABLE);
        Book book4 = new Book(
                4, 602, "BAD CODE", "Uncle Jack", "Go Books", "1114", BookGender.HORROR, BookStatus.AVAILABLE);
        Book book5 = new Book(
                5, 402, "Collections in Science", "Beer & Johnston", "Science Books", "11115", BookGender.SCIENCE, BookStatus.AVAILABLE);
        Book book6 = new Book(
                6, 355, "Rich Malfunctions", "Richard", "Tetra Books", "11166", BookGender.DRAMA, BookStatus.AVAILABLE);

        Integer b1 = createBookUseCase.insert(book1);
        Integer b2 = createBookUseCase.insert(book2);
        Integer b3 = createBookUseCase.insert(book3);
        Integer b4 = createBookUseCase.insert(book4);
        Integer b5 = createBookUseCase.insert(book5);
        Integer b6 = createBookUseCase.insert(book6);

        checkoutBook(user1.getInstitutionalId(), b1);
        checkoutBook(user1.getInstitutionalId(), b2);
        checkoutBook(user1.getInstitutionalId(), b3);
        checkoutBook(user1.getInstitutionalId(), b4);
        checkoutBook(user1.getInstitutionalId(), b5);
        checkoutBook(user1.getInstitutionalId(), b6);

        returnBook(b1);
        returnBook(b2);
        returnBook(b3);
        returnBook(b4);
        returnBook(b5);
        returnBook(b6);

        findTransactionUseCase.searchAll().forEach(System.out::println);
    }

    public static void checkoutBook(String userId, Integer bookId){
        try {
            checkoutTransactionUseCase.checkoutABook(userId, bookId);
            System.out.println("Book has been checked out");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public static void returnBook(Integer bookId){

        try {
            returnTransactionUseCase.returnBook(bookId);
            System.out.println("Book has been returned");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void configureInjection() {
        BookDAO bookDAO = new InMemoryBookDAO();
        createBookUseCase = new CreateBookUseCase(bookDAO);
        updateBookUseCase = new UpdateBookUseCase(bookDAO);
        searchBookUseCase = new SearchBookUseCase(bookDAO);
        removeBookUseCase = new RemoveBookUseCase(bookDAO);

        UserDAO userDAO = new InMemoryUserDAO();
        createUserUseCase = new CreateUserUseCase(userDAO);
        updateUserUseCase = new UpdateUserUseCase(userDAO);
        searchUserUseCase = new SearchUserUseCase(userDAO);
        removeUserUseCase = new RemoveUserUseCase(userDAO);

        TransactionDAO transactionDAO = new InMemoryTransactionDAO();
        checkoutTransactionUseCase = new CheckoutTransactionUseCase(
                transactionDAO, searchUserUseCase, searchBookUseCase, updateUserUseCase, updateBookUseCase
        );
        returnTransactionUseCase = new ReturnTransactionUseCase(
                transactionDAO, searchUserUseCase, searchBookUseCase, updateUserUseCase, updateBookUseCase
        );
        findTransactionUseCase = new FindTransactionUseCase(transactionDAO);
    }
}
