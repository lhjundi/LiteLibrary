package com.lhjundi.domain.usecases.user;

import com.lhjundi.domain.entities.user.User;
import com.lhjundi.domain.usecases.utils.EntityNotFoundException;

public class RemoveUserUseCase {
    private UserDAO userDAO;

    public RemoveUserUseCase(UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public boolean remove(String id){
        if (id == null || userDAO.searchOne(id).isEmpty())
            throw new EntityNotFoundException("Book not found.");
        return userDAO.deleteByKey(id);
    }

    public boolean remove(User user){
        if (user == null || userDAO.searchOne(user.getInstitutionalId()).isEmpty())
            throw new EntityNotFoundException("Book not found.");
        return userDAO.delete(user);
    }
}
