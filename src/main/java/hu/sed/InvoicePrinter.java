package hu.sed;

import hu.sed.middle.InvoiceMiddle;
import hu.sed.model.Invoice;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;

public class InvoicePrinter {
    private Divisions division;
    private boolean simplified;

    private InvoiceMiddle invoiceMiddle = new InvoiceMiddle();
    public InvoicePrinter( Divisions division, boolean simplified ) {
        this.division = division;
        this.simplified = simplified;
    }

    public void print() {

        for( Invoice invoice : invoiceMiddle.getInvoices( division ) ) {
            if( simplified ) {
                simplifiedInvoicePrint( invoice );
            } else {
                normalInvoicePrint( invoice );
            }
        }
    }

    private void simplifiedInvoicePrint( Invoice invoice ) {
        System.out.println("Printing invoice....");
        String headerStr = header();
        System.out.println(headerStr);
        System.out.println("ID:" + invoice.id());
        System.out.println("Value:" + invoice.value());
        System.out.println("End of invoice.");

        // save PDF
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        try ( PDPageContentStream contentStream = new PDPageContentStream( document, page ) ) {

            contentStream.setFont(PDType1Font.COURIER, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 725);
            contentStream.setLeading(14.5f);
            contentStream.showText( headerStr );
            contentStream.newLine();
            contentStream.showText( "ID:" + invoice.id() );
            contentStream.newLine();
            contentStream.showText( "Value:" + invoice.value() );
            contentStream.endText();
            contentStream.close();
            document.save("invoice-simple-" + generatedId( invoice.id() ) + ".pdf");
            document.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        private void normalInvoicePrint( Invoice invoice ) {
        System.out.println( "Printing invoice...." );
        String headerStr = header();
        System.out.println( headerStr );
        System.out.println( "ID:" + invoice.id() );
        System.out.println( "Value:" + invoice.value() );
        System.out.println( "Partner:" + invoice.partner() );
        System.out.println( "Whatever detailed information i do not know about" );
        System.out.println( "End of invoice." );

        // save PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            try ( PDPageContentStream contentStream = new PDPageContentStream( document, page ) ) {

                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(25, 725);
                contentStream.setLeading(14.5f);
                contentStream.showText( headerStr );
                contentStream.newLine();
                contentStream.showText( "ID:" + invoice.id() );
                contentStream.newLine();
                contentStream.showText( "Value:" + invoice.value()  );
                contentStream.newLine();
                contentStream.showText( "Partner:" + invoice.partner() );
                contentStream.newLine();
                contentStream.showText( "Whatever detailed information i do not know about" );
                contentStream.endText();
                contentStream.close();
                document.save("invoice-" + generatedId( invoice.id() ) + ".pdf");
                document.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    private String generatedId( final String id ) {
        return id.replace( '\\', '-' ).replace( '/', '-' );
    }

    private String header() {
        String printedHeader;
        switch( this.division ) {
            case BRICK: printedHeader = "Tegla gyar------"; break;
            case GLASS: printedHeader = "Uveg gyar------"; break;
            default: printedHeader = "Magical gyar----"; break;
        }
        return printedHeader;
    }
}
