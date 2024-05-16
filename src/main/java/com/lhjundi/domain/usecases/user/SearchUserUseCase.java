package com.lhjundi.domain.usecases.user;

import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.utils.Validator;

import java.util.List;
import java.util.Optional;

public class SearchUserUseCase {
    private UserDAO userDAO;

    public SearchUserUseCase(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public Optional<User> seachOne(String id){
        if (id == null)
            throw new IllegalArgumentException("ID can not be null");
        return userDAO.searchOne(id);
    }

    public Optional<User> searchOneByEmail(String email){
        if (Validator.nullOrEmpty(email))
            throw new IllegalArgumentException("Email can not be null or empty");
        return userDAO.searchByEmail(email);
    }

    public List<User> searchAll(){
        return userDAO.searchAll();
    }
}
