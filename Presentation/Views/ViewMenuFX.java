package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * View FX to show the menu
 */
public class ViewMenuFX extends ViewFX
{
    /**
     * Button to start a new game
     */
    @FXML
    private Button newGameButton;

    /**
     * Button to load a game
     */
    @FXML
    private Button loadGameButton;

    /**
     * Button to view the ranking
     */
    @FXML
    private Button viewRankingButton;

    /**
     * Button to change language
     */
    @FXML
    private Button languageButton;

    /**
     * Button to show help
     */
    @FXML
    private Button helpButton;

    /**
     * ImageView to show the logo
     */
    @FXML
    private ImageView logoImageView;

    /**
     * HBox to contain the ImageView representing the logo
     */
    @FXML
    private HBox logoHBox;

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewMenuFX(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }

    /**
     * Initializes the view
     */
    @Override
    @FXML
    public void initialize()
    {
       /*
        logoImageView.fitHeightProperty().bind(logoHBox.heightProperty());
        logoImageView.fitWidthProperty().bind(logoHBox.widthProperty());
        */

       stage.setMinHeight(530);
       stage.setHeight(530);
       stage.setMinWidth(600);
       stage.setWidth(600);
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
     * Handles the click on the new game button
     */
    @FXML
    public void onNewGameButtonClick() {
        ctrlPresentation.toggleCreateGameFX();
    }

    /**
     * Handles the click on the load game button
     */
    @FXML
    public void onLoadGameButtonClick()
    {
        ctrlPresentation.toggleLoadGameFX();
    }

    /**
     * Handles the click on the view ranking button
     */
    @FXML
    public void onViewRankingButtonClick()
    {
        ctrlPresentation.toggleRankingFX();
    }

    /**
     * Handles the click on the language button
     */
    @FXML
    public void onLanguageButtonClick() {
        switch (ctrlPresentation.getLocale().getLanguage()) {
            case "en":
                ctrlPresentation.setLocale(new Locale("ca", "CA"));
                break;
            case "ca":
                ctrlPresentation.setLocale(new Locale("es", "ES"));
                break;
            case "es":
                ctrlPresentation.setLocale(new Locale("en", "EN"));
                break;
            default:
                ctrlPresentation.setLocale(new Locale("en", "EN"));
                break;
        }

        ctrlPresentation.toggleMenuFX();
    }

    /**
     * Handles the click on the help button
     */
    @FXML
    public void onHelpButtonClick() {
        ctrlPresentation.toggleHelpFX();
    }
}
