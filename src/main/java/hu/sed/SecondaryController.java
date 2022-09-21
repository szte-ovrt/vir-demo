package hu.sed;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import hu.sed.middle.RolesMiddle;
import hu.sed.middle.UserMiddle;
import hu.sed.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SecondaryController implements Initializable {
    private UserMiddle userMiddle = new UserMiddle();
    private RolesMiddle rolesMiddle = new RolesMiddle();
    private final boolean simplifiedInvoice = false;
    private Divisions division = Divisions.BRICK;
    CheckBox simplifiedCB = new CheckBox( "Simplified invoices" );
    @FXML
    MenuBar menuBar;
    @FXML
    VBox vbox;
    @FXML
    Label divisionLabel;
    @FXML
    Button divisionButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = userMiddle.loggedInUser();

        setDivisionDefaults();

        Menu simpleMenu = new Menu( "Simple tab" );
        MenuItem exit = new MenuItem( "Exit" );
        exit.setOnAction( e -> {
            userMiddle.logout();
            try {
                App.setRoot( "primary" );
            } catch ( IOException ex ) {
                throw new RuntimeException(ex);
            }
        } );
        simpleMenu.getItems().add( exit );
        menuBar.getMenus().add( simpleMenu );

        if( rolesMiddle.isAdmin( user.authToken() ) ) {
            Menu adminMenu = new Menu( "Admin tab" );

            MenuItem addUser = new MenuItem( "Add new user" );
            addUser.setOnAction( e -> {
                try {
                    App.setRoot( "newUser" );
                } catch ( IOException ex ) {
                    throw new RuntimeException( ex );
                }
            } );
            adminMenu.getItems().add( addUser );

            MenuItem listUser = new MenuItem( "List users" );
            listUser.setOnAction( e -> {
                List<User> users = userMiddle.getUsers();
                Map<String, List<RolesMiddle.ROLES>> userViewWithRoles = new HashMap<>();

                for ( User u : users ) {
                    List<RolesMiddle.ROLES> roles = new ArrayList<>();

                    roles.add( RolesMiddle.ROLES.SIMPLE);

                    if ( rolesMiddle.isAdmin( u.authToken() ) ) {
                        roles.add( RolesMiddle.ROLES.ADMIN );
                    }
                    if ( rolesMiddle.isEditor( u.authToken() ) ) {
                        roles.add( RolesMiddle.ROLES.EDITOR );
                    }
                    // if it has a forat name1<space>name2 -> transform it to Firstname:<>Secondname:<>
                    String[] viewUsernames = u.username().split( " " );
                    String viewName;
                    try {
                        viewName = "Firstname: " + viewUsernames[0] + "Secondname: " + viewUsernames[1];
                    } catch ( IndexOutOfBoundsException indexError ) {
                        viewName = u.username();
                    }

                    userViewWithRoles.put( viewName, roles );
                }

                for ( Map.Entry<String, List<RolesMiddle.ROLES>> viewUser : userViewWithRoles.entrySet() ) {
                    System.out.println("   User: " + viewUser.getKey() + " Roles: " + viewUser.getValue()
                            .stream()
                            .map( role -> role.toString() )
                            .collect( Collectors.joining( "&" ) ) );
                }
            } );
            adminMenu.getItems().add( listUser );

            menuBar.getMenus().add( adminMenu );

        }
        if( rolesMiddle.isEditor( user.authToken() ) ) {
            Menu editorsMenu = new Menu( "Editor tab" );

            MenuItem createInvoice = new MenuItem( "Create invoice" );
            editorsMenu.getItems().add( createInvoice );

            MenuItem printInvoices = new MenuItem( "Print invoices" );
            printInvoices.setOnAction( e -> {
                new InvoicePrinter( division, simplifiedCB.isSelected() ).print();
            } );
            editorsMenu.getItems().add( printInvoices );
            menuBar.getMenus().add( editorsMenu );

            simplifiedCB.setSelected( simplifiedInvoice );
            vbox.getChildren().add( simplifiedCB );
        }
    }

    private void setDivisionDefaults() {
        divisionLabel.setText( "Division: " + division );
        divisionButton.setOnAction( e -> nextDivision() );
    }

    private void nextDivision() {
        switch ( division ) {
            case BRICK: division = Divisions.GLASS; divisionLabel.setText( "Division: " + division ); break;
            case GLASS: division = Divisions.BRICK; divisionLabel.setText( "Division: " + division ); break;
            default: division = Divisions.BRICK; divisionLabel.setText( "Division: " + division );
        }
    }
}