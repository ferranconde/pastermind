package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * View FX to show help
 */
public class ViewHelpFX extends ViewFX {

    /**
     * ImageView containing help screenshots
     */
    @FXML
    private ImageView helpImageView;

    /**
     * Button to next image
     */
    @FXML
    private Button nextButton;

    /**
     * Integer to count current image
     */
    private int currentPage;

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewHelpFX(CtrlPresentation ctrlPresentation) { super(ctrlPresentation); }

    /**
     * Initializes the view
     */
    @Override
    public void initialize()
    {
        currentPage = 0;
        String lang = ctrlPresentation.getLocale().getLanguage();
        Image im = new Image("/Presentation/Resources/breakerhelp-" + lang + ".jpg");
        helpImageView.setImage(im);
        stage.setMinHeight(600);
        stage.setHeight(600);
        stage.setMinWidth(650);
        stage.setWidth(650);

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
     * Handles the click on the next button
     */
    @FXML
    public void onNextButtonClick() {

        if (currentPage == 2) {
            ctrlPresentation.toggleMenuFX();
        }

        else if (currentPage == 1) {
            currentPage = 2;
            nextButton.setText(ctrlPresentation.getResource("Back"));
            String lang = ctrlPresentation.getLocale().getLanguage();
            Image im = new Image("/Presentation/Resources/makerhelp2-" + lang + ".jpg");
            helpImageView.setImage(im);
        }

        else if (currentPage == 0) {
            currentPage = 1;
            String lang = ctrlPresentation.getLocale().getLanguage();
            Image im = new Image("/Presentation/Resources/makerhelp1-" + lang + ".jpg");
            helpImageView.setImage(im);
        }
    }


}
