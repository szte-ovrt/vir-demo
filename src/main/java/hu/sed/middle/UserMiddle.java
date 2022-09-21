package hu.sed.middle;

import hu.sed.model.User;
import hu.sed.model.database.IUserDAO;
import hu.sed.model.database.UserDAOSQLite;

import java.util.List;

public class UserMiddle {
    private IUserDAO userDao = new UserDAOSQLite();
    private static User loggedInUser;

    public User loggedInUser() { return loggedInUser; }

    public boolean login( String username, String password ) {
        loggedInUser = new User()
                .username( username )
                .pwd( password );
        return userDao.login( loggedInUser );
    }

    public String save( User user ) {
        return userDao.save( user );
    }

    public void logout() {
        loggedInUser = null;
    }

    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
