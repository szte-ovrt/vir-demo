package hu.sed.middle;

import hu.sed.Divisions;
import hu.sed.model.Invoice;
import hu.sed.model.database.IInvoiceDAO;
import hu.sed.model.database.InvoiceDAOSQLite;

import java.util.List;

public class InvoiceMiddle {

    private IInvoiceDAO invoiceDAO = new InvoiceDAOSQLite();
    public List<Invoice> getInvoices( Divisions division ) {
        return invoiceDAO.getInvoicesForDivision( division.toString() );
    }
}
