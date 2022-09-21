package hu.sed;

import hu.sed.middle.RolesMiddle;
import hu.sed.middle.UserMiddle;
import hu.sed.model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NewUserController implements Initializable {

    @FXML
    TextField usernameTF;

    @FXML
    PasswordField passwordPF;

    @FXML
    Accordion rolesAccordion;

    @FXML
    Button cancelButton;

    @FXML
    Button saveButton;

    private UserMiddle userMiddle = new UserMiddle();
    private RolesMiddle rolesMiddle = new RolesMiddle();
    private HashMap<RolesMiddle.ROLES, CheckBox> roleCheckBoxes = new HashMap<>();

    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ) {

        RolesMiddle.ROLES[] roles = RolesMiddle.ROLES.class.getEnumConstants();
        VBox rolesBox = new VBox();
        // create roles select GUI
        for ( RolesMiddle.ROLES role : roles ) {

            CheckBox roleCheckbox = new CheckBox( role.toString() );
            if ( role == RolesMiddle.ROLES.SIMPLE) {
                roleCheckbox.setSelected( true );
                roleCheckbox.setDisable( true );
            }

            roleCheckBoxes.put( role, roleCheckbox );
            rolesBox.getChildren().add( new HBox( new Label( role.toString() ), roleCheckbox ) );
        }
        rolesAccordion.getPanes().get( 0 ).setContent( rolesBox );
        // set things for controlling
        cancelButton.setOnAction( e ->  {
            goBack();
        } );

        saveButton.setOnAction( e -> {
            User user = new User();

            ArrayList<String> errorMessages = new ArrayList<>();

            if ( !usernameTF.getText().isEmpty() ) {
                user.username( usernameTF.getText() );
            } else {
                errorMessages.add( "Provide username!" );
            }

            if ( !passwordPF.getText().isEmpty() ) {
                user.pwd( passwordPF.getText() );
            } else {
                errorMessages.add( "Provide password!" );
            }

            if ( !errorMessages.isEmpty() ) {
                Alert alert = new Alert( Alert.AlertType.ERROR );
                alert.setTitle( "Form data error");
                alert.setHeaderText( "Some data were missing" );
                alert.setContentText(
                        errorMessages
                        .stream()
                        .collect(
                                Collectors.joining( System.lineSeparator() )
                                )
                );
                alert.show();
                return;
            }

            String userToken = userMiddle.save( user );

            roleCheckBoxes.forEach( ( role, checkbox ) -> {
              if ( checkbox.isSelected() ) {
                  switch ( role ) {
                      case ADMIN: rolesMiddle.makeAdmin( userToken, userMiddle.loggedInUser() ); break;
                      case EDITOR: rolesMiddle.makeEditor( userToken, userMiddle.loggedInUser() ); break;
                      //case SIMPLE: only if simple would be non default break;
                  }
              }
            } );

            goBack();
        });
    }

    private void goBack() {
        try {
            App.setRoot( "secondary" );
        } catch ( IOException ex ) {
            throw new RuntimeException( ex );
        }
    }
}
