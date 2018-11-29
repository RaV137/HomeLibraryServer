package pl.danowski.rafal.homelibraryserver.dao.interfaces;

import pl.danowski.rafal.homelibraryserver.model.User;

public interface IUserDao {

    User getByLogin(String login);

    User getByEmail(String email);

    User registerUser(User user);

    User updateUser(User user);

    User deleteUser(String login);

}
