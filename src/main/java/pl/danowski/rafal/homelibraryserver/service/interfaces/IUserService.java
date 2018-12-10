package pl.danowski.rafal.homelibraryserver.service.interfaces;

import pl.danowski.rafal.homelibraryserver.model.User;

public interface IUserService {

    User getByLogin(String login);

    User getByEmail(String email);

    User registerUser(User user);

    User updateUser(User user);

    User deleteUser(String login);

    User getById(Integer id);
}
