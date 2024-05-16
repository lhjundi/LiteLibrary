package com.lhjundi.domain.usecases.user;

import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.utils.EntityNotFoundException;
import com.lhjundi.domain.usecases.utils.Notification;
import com.lhjundi.domain.usecases.utils.Validator;

public class UpdateUserUseCase {
    private UserDAO userDAO;

    public UpdateUserUseCase(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean update (User user){
        Validator<User> validator = new UserInputRequestValidator();
        Notification notification = validator.validate(user);

        if (notification.hasErrors())
            throw new IllegalArgumentException(notification.errorMessage());

        String id = user.getInstitutionalId();
        if (userDAO.searchOne(id).isEmpty())
            throw new EntityNotFoundException("User not found.");

        return userDAO.update(user);
    }
}
