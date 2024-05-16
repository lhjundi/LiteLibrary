package com.lhjundi.domain.usecases.user;

import com.lhjundi.domain.entities.book.Book;
import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.utils.EntityAlreadyExistsException;
import com.lhjundi.domain.usecases.utils.Notification;
import com.lhjundi.domain.usecases.utils.Validator;

public class CreateUserUseCase {

    private UserDAO userDAO;

    public CreateUserUseCase(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String insert(User user){
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String email = user.getEmail();
        if (userDAO.searchByEmail(email).isPresent())
            throw new EntityAlreadyExistsException("This email is already in use");

        return userDAO.create(user);
    }
}
