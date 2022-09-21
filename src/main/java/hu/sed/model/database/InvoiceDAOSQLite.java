package hu.sed.model.database;

import hu.sed.Divisions;
import hu.sed.model.Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOSQLite implements IInvoiceDAO {

    private static final String connectionURL = "jdbc:sqlite:vir.sqlite";
    private static final String QUERY_INVOICES_FOR_DIVISION = "SELECT * FROM Invoices WHERE division = ?";

    @Override
    public List<Invoice> getInvoices() {
        ArrayList<Invoice> invoices = new ArrayList<>();

        for ( Divisions division : Divisions.values() ) {
            invoices.addAll( getInvoicesForDivision( division.toString() ) );
        }
        return invoices;
    }

    @Override
    public List<Invoice> getInvoicesForDivision( String division ) {
        ArrayList<Invoice> invoices = new ArrayList<>();
        try( Connection c = DriverManager.getConnection( connectionURL );
             PreparedStatement ps = c.prepareStatement( QUERY_INVOICES_FOR_DIVISION )
        ) {
            int parameterIndex = 1;
            ps.setString( parameterIndex++, division );
            ResultSet rs = ps.executeQuery();

            while( rs.next() ) {
                Invoice invoice = new Invoice();
                invoice.division( division )
                        .id( rs.getString( "id" ) )
                        .partner( rs.getString( "partner" ) )
                        .value( rs.getInt( "value" ) );
                invoices.add( invoice );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return invoices;
    }
}
