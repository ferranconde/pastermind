package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * View FX to show the register window
 */
public class ViewRegisterFX extends ViewFX {

    /**
     * TextField to select name
     */
    @FXML
    private TextField nameTextField;

    /**
     * TextField to select username
     */
    @FXML
    private TextField usernameTextField;

    /**
     * PasswordField to select password
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Button to accept the register process
     */
    @FXML
    private Button acceptButton;

    /**
     * String to store the name
     */
    private String name;

    /**
     * String to store the username
     */
    private String user;

    /**
     * String to store the password
     */
    private String password;

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewRegisterFX(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }

    /**
     * Initializes the view
     */
    @Override
    public void initialize()
    {

    }

    /**
     * Displays the view
     */
    @Override
    public void display()
    {

    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Handles the click on the accept button
     */
    @FXML
    public void onAcceptButtonClick() {
        if (usernameTextField.getText() == null || usernameTextField.getText().length() == 0
                || passwordField.getText() == null || passwordField.getText().length() == 0
                || nameTextField.getText() == null || nameTextField.getText().length() == 0) {

            showAlert(Alert.AlertType.ERROR, "There are empty fields",
                    null, "Please, fill all the fields.");
            return;
        }

        user = usernameTextField.getText();
        name = nameTextField.getText();
        password = passwordField.getText();

        if (ctrlPresentation.existsPlayer(user)) {
            showAlert(Alert.AlertType.ERROR, "Username already exists",
                    null, "Please, select another username.");
            return;
        }

        try {
            ctrlPresentation.storePlayer(user, name, password);
            ctrlPresentation.loginPlayer(user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ctrlPresentation.toggleMenuFX();

    }


}
