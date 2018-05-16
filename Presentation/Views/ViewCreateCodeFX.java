package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.List;

/**
 * FX View used to create a code
 */
public class ViewCreateCodeFX extends ViewFX implements IUserPanel
{
    /**
     * ListView containing the chosen colors
     */
    @FXML
    private ListView chosenColorsListView;

    /**
     * Label indicating the number of colors chosen and the number of available colors
     */
    @FXML
    private Label chosenColorsLabel;

    /**
     * Contains the pegs that will form the code
     */
    @FXML
    private HBox colorListHBox;

    /**
     * Button to undo
     */
    @FXML
    private Button undoButton;

    /**
     * Button to set the code
     */
    @FXML
    private Button chooseButton;

    /**
     * Calls the super constructor
     * @param ctrlPresentation The CtrlPresentation instance
     */
    public ViewCreateCodeFX(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }

    /**
     * Initializes the view
     */
    @Override
    public void initialize()
    {
        initializeForm();
    }

    /**
     * Initializes the main form
     */
    private void initializeForm()
    {
        stage.setWidth(650);
        stage.setHeight(260);
        stage.setMinWidth(650);
        stage.setMinHeight(260);
        chooseButton.setMinWidth(Region.USE_PREF_SIZE);
        undoButton.setMinWidth(Region.USE_PREF_SIZE);
        initializeLabels();
        initializeUserPanel();
    }

    /**
     * Initializes the form labels
     */
    private void initializeLabels()
    {
        chosenColorsLabel.setText("0 / " + ctrlPresentation.getGameNumHoles());
    }

    /**
     * Initializes the user panel
     */
    private void initializeUserPanel()
    {
        MouseHandler mouseHandler = new MouseHandler();
        List<String> colors = ctrlPresentation.getGameColorList();
        generateBreakerPanel(colorListHBox, mouseHandler, colors);
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
     * Handles the click on the undo button
     */
    @FXML
    public void onUndoButtonClick()
    {
        if (chosenColorsListView.getItems().size() != 0)
        {
            chosenColorsListView.getItems().remove(chosenColorsListView.getItems().size() - 1);
            updateChosenColorsLabel();
        }
    }

    /**
     * Adds a peg to the chosen colors list
     * @param circle A circle representing the peg
     */
    @Override
    public void addCircleToChosenPlayList(Circle circle)
    {
        if (chosenColorsListView.getItems().size() < ctrlPresentation.getGameNumHoles())
        {
            chosenColorsListView.getItems().add(circle);
            updateChosenColorsLabel();
        }
    }

    /**
     * Updates the label with the remaining colors to choose
     */
    private void updateChosenColorsLabel()
    {
        chosenColorsLabel.setText(chosenColorsListView.getItems().size() + " / " + ctrlPresentation.getGameNumHoles());
    }

    /**
     * Handles the click on the choose button
     */
    @FXML
    public void onChooseButtonClick()
    {
        if (chosenColorsListView.getItems().size() != ctrlPresentation.getGameNumHoles())
        {
            showAlert(Alert.AlertType.WARNING,
                    ctrlPresentation.getResource("AlertInvalidGuessTitle"),
                    null,
                    ctrlPresentation.getResource("AlertInvalidGuessContent")
                            + ' '
                            + (ctrlPresentation.getGameNumHoles() - chosenColorsListView.getItems().size())
                            + ' '
                            + ctrlPresentation.getResource("Colors"));
            return;
        }
        String play = generatePlay(ctrlPresentation.getGameNumHoles(),
                ctrlPresentation.getGameColorList(),
                chosenColorsListView.getItems());
        ctrlPresentation.setGameCorrectAnswer(play);
        ctrlPresentation.togglePlayGameFX();
    }
}
