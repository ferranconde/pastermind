package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * View FX to show login
 */
public class ViewLoginFX extends ViewFX {

    /**
     * TextField to select username
     */
    @FXML
    private TextField usernameTextField;

    /**
     * TextField to select password
     */
    @FXML
    private PasswordField passwordField;

    /**
     * Button to register
     */
    @FXML
    private Button registerButton;

    /**
     * Button to login
     */
    @FXML
    private Button loginButton;

    /**
     * ImageView to show banner
     */
    @FXML
    private ImageView bannerImageView;

    /**
     * Stores the username
     */
    private String username;

    /**
     * Stores the password
     */
    private String password;


    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewLoginFX(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }

    /**
     * Initializes the view
     */
    @Override
    public void initialize()
    {
        bannerImageView.fitWidthProperty().bind(stage.widthProperty());
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
     * Handles the click on the login button
     */
    @FXML
    public void onLoginButtonClick() {


        if (usernameTextField.getText() == null || usernameTextField.getText().length() == 0
                || passwordField.getText() == null || passwordField.getText().length() == 0) {
            showAlert(Alert.AlertType.ERROR, "There are empty fields",
                    null, "Please, fill all the fields.");
            return;
        }

        username = usernameTextField.getText();
        password = passwordField.getText();

        if (!ctrlPresentation.existsPlayer(username)) {
            showAlert(Alert.AlertType.ERROR, "Login failed", null, "Invalid username or password");
            return;
        }

        try {
            if (!ctrlPresentation.loginPlayer(username, password)) {
                showAlert(Alert.AlertType.ERROR, "Login failed", null, "Invalid username or password");
            }
            else {
                ctrlPresentation.toggleMenuFX();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles the click on the register button
     */
    @FXML
    public void onRegisterButtonClick() {
        ctrlPresentation.toggleRegisterFX();
    }


}
