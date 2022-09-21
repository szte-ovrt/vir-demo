package hu.sed.model;

public class User {
    private String username;
    private String pwd;
    private String authToken;

    public String username() { return username; }
    public String pwd() { return pwd; }
    public String authToken() { return authToken; }

    public User username( String username ) {
        this.username = username;
        return this;
    }
    public User pwd( String pwd ) {
        this.pwd = pwd;
        return this;
    }
    public User authToken( String authToken ) {
        this.authToken = authToken;
        return this;
    }
}
