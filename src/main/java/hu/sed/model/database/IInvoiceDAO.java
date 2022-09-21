package hu.sed.model.database;

import hu.sed.model.Invoice;

import java.util.List;

public interface IInvoiceDAO {
    List<Invoice> getInvoices();
    List<Invoice> getInvoicesForDivision( String division );
}
