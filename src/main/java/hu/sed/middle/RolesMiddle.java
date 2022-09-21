package hu.sed.middle;

import hu.sed.model.User;
import hu.sed.model.database.IRolesDAO;
import hu.sed.model.database.RolesDAOSQLite;

public class RolesMiddle {
    private IRolesDAO rolesDAO = new RolesDAOSQLite();

    public boolean isAdmin( final String authToken ) {
        return rolesDAO.isAdmin( authToken );
    }

    public boolean isEditor( final String authToken ) {
        return rolesDAO.isEditor( authToken );
    }

    public void makeAdmin(String userToken, User loggedInUser) {
        if ( isAdmin( loggedInUser.authToken() ) ) {
            rolesDAO.makeAdmin( userToken );
        }
    }

    public void makeEditor(String userToken, User loggedInUser) {
        if ( isAdmin( loggedInUser.authToken() ) ) {
            rolesDAO.makeEditor( userToken );
        }
    }

    public enum ROLES {
        ADMIN( "Admin" ),
        EDITOR( "Editor" ),
        SIMPLE( "Simple" );

        ROLES( final String role ) { this.role = role; }
        private final String role;
        public String toString() { return role; }

    }
}
