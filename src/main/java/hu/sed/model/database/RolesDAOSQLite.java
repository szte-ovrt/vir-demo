package hu.sed.model.database;

import java.sql.*;

public class RolesDAOSQLite implements IRolesDAO {
    private static final String connectionURL = "jdbc:sqlite:vir.sqlite";
    private static final String QUERY_AUTH_TOKEN_ADMIN = "SELECT * FROM Admins WHERE authToken = ?";
    private static final String QUERY_AUTH_TOKEN_EDITOR = "SELECT * FROM Editors WHERE authToken = ?";
    private static final String INSERT_TOKEN_TO_ADMIN = "INSERT INTO Admins VALUES( ? )";
    private static final String INSERT_TOKEN_TO_EDITOR = "INSERT INTO Editors VALUES( ? )";

    @Override
    public boolean isAdmin( String authToken ) {
        return isRole( QUERY_AUTH_TOKEN_ADMIN, authToken );
    }

    @Override
    public boolean isEditor(String authToken) {
        return isRole( QUERY_AUTH_TOKEN_EDITOR, authToken );
    }

    @Override
    public void makeAdmin( String userToken ) {
        makeRole( userToken, INSERT_TOKEN_TO_ADMIN );
    }

    @Override
    public void makeEditor(String userToken) {
        makeRole( userToken, INSERT_TOKEN_TO_EDITOR );
    }

    private boolean isRole( final String query, final String authToken ) {
        boolean isRole = false;

        try( Connection c = DriverManager.getConnection( connectionURL );
             PreparedStatement ps = c.prepareStatement( query )
        ) {
            int paramIndex = 1;
            ps.setString( paramIndex++, authToken );
            ResultSet admins = ps.executeQuery();
            isRole = admins.next();
        } catch ( SQLException e ) {
            System.err.println( "Error in:" + connectionURL );
            System.err.println( e.getMessage() );
        }

        return isRole;
    }

    private void makeRole( final String authToken, final String query ) {
        try( Connection c = DriverManager.getConnection( connectionURL );
             PreparedStatement ps = c.prepareStatement( query )
        ) {
            int paramIndex = 1;
            ps.setString( paramIndex++, authToken );
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println( "Error in:" + connectionURL );
            System.err.println( e.getMessage() );
        }
    }
}
