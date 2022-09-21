package hu.sed;

import java.io.IOException;

import hu.sed.middle.UserMiddle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    private UserMiddle userMiddle = new UserMiddle();
    @FXML
    TextField usernameTF;
    @FXML
    PasswordField passwordTF;

    @FXML
    void login() throws IOException {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if ( userMiddle.login( username, password ) ) {
            App.setRoot( "secondary" );
        } else {
            Alert loginAlert = new Alert( Alert.AlertType.ERROR );
            loginAlert.setTitle( "Failed login attempt!" );
            loginAlert.setHeaderText( "Wrong username and password pair." );
            loginAlert.setContentText( "Try not hacking and use your credentials!" );
            loginAlert.show();
        }
    }
}
