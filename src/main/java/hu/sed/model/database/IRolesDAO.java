package hu.sed.model.database;

public interface IRolesDAO {
    boolean isAdmin( String authToken );
    boolean isEditor( String authToken );

    void makeAdmin(String userToken);

    void makeEditor(String userToken);
}
