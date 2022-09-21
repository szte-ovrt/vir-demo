package hu.sed;

public enum Divisions {
    BRICK( "BRICK" ),
    GLASS( "GLASS" );

    public String toString() { return str; }

    Divisions( final String str ) { this.str = str; }
    private String str;
}