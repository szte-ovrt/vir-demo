package hu.sed.model.database;

import hu.sed.model.User;

import java.util.List;

public interface IUserDAO {
    boolean login( User user );
    boolean delete( String authToken, String username );
    String save(User user);
    List<User> getUsers();
}
