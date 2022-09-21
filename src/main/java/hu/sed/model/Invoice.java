package hu.sed.model;

public class Invoice {
    private String id;
    private String partner;
    private String division;
    private int value;

    public String id() { return id; }
    public String partner() { return partner; }
    public String division() { return division; }
    public int value() { return value; }

    public Invoice id( String id ) { this.id = id; return this; }
    public Invoice partner( String partner ) { this.partner = partner; return this; }
    public Invoice division( String division ) { this.division = division; return this; }
    public Invoice value( int value ) { this.value = value; return this; }
}
