package com.lhjundi.domain.usecases.user;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.utils.DAO;

import java.util.Optional;

public interface UserDAO extends DAO<User, String> {
    Optional<User> searchByEmail(String email);
}