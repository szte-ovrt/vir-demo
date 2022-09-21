package hu.sed.model.database;

import hu.sed.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOSQLite implements IUserDAO {
    private static final String QUERY_BY_USERNAME = "SELECT * FROM User WHERE username = ?";
    private static final String SAVE_USER = "INSERT INTO User(username, password, authToken) VALUES (?,?,?)";
    private static final String USER_QUERY = "SELECT * FROM User";
    private static final String connectionURL = "jdbc:sqlite:vir.sqlite";

    @Override
    public boolean login( User user ) {
        boolean authenticated = false;
        User dbUser = null;
        try( Connection c = DriverManager.getConnection( connectionURL );
             PreparedStatement ps = c.prepareStatement( QUERY_BY_USERNAME )
        ) {
            int paramIndex = 1;
            ps.setString( paramIndex++, user.username() );
            ResultSet users = ps.executeQuery();
            dbUser = new User();
            while( users.next() ) {
                dbUser
                    .username( users.getString("username" ) )
                    .pwd( users.getString("password" ) )
                    .authToken( users.getString("authToken" ) );
            }
        } catch ( SQLException e ) {
            System.err.println( "Error in:" + connectionURL );
            System.err.println( e.getMessage() );
        }
        if( dbUser != null ) {
            authenticated = dbUser.username().equals( user.username() ); // yeah, :D
            authenticated = authenticated && dbUser.pwd().equals( user.pwd() );
        }
        if( authenticated ) {
            user.authToken( dbUser.authToken() );
        }
        return authenticated;
    }

    @Override
    public boolean delete( String authToken, String username ) {
        return false;
    }

    @Override
    public String save( User user ) {
        String authToken = user.username(); // HC!
        user.authToken( authToken );
        try( Connection c = DriverManager.getConnection( connectionURL );
             PreparedStatement ps = c.prepareStatement( SAVE_USER )
        ) {
            int paramIndex = 1;
            ps.setString( paramIndex++, user.username() );
            ps.setString( paramIndex++, user.pwd() );
            ps.setString( paramIndex++, user.authToken() );
            ps.executeUpdate();
        } catch ( SQLException e ) {
            System.err.println( "Error in:" + connectionURL );
            System.err.println( e.getMessage() );
        }

        return authToken;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();

        try ( Connection c = DriverManager.getConnection( connectionURL );
              Statement statement = c.createStatement()
        ) {
            ResultSet userSet = statement.executeQuery( USER_QUERY );
            while ( userSet.next() ) {
                User user =
                        new User()
                        .username( userSet.getString("username" ) )
                        .pwd( userSet.getString("password" ) )
                        .authToken( userSet.getString("authToken" ) );;
                users.add( user );
            }
        } catch ( SQLException e ) {
            System.err.println( "Error in:" + connectionURL );
            System.err.println( e.getMessage() );
        }

        return users;
    }
}
