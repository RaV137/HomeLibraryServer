package pl.danowski.rafal.homelibraryserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.danowski.rafal.homelibraryserver.dao.interfaces.IUserDao;
import pl.danowski.rafal.homelibraryserver.model.User;
import pl.danowski.rafal.homelibraryserver.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserDao dao;

    @Override
    public User getByLogin(String login) {
        return dao.getByLogin(login);
    }

    @Override
    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

    @Override
    public User registerUser(User user) {
        user.setPremium(false);
        return dao.registerUser(user);
    }

    @Override
    public User updateUser(User user) {
        return dao.updateUser(user);
    }

    @Override
    public User deleteUser(String login) {
        return dao.deleteUser(login);
    }

    @Override
    public User getById(Integer id) {
        return dao.findById(id);
    }
}
