package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.ResourceBundle;

/**
 * FX View to create a game
 */
public class ViewCreateGameFX extends ViewFX
{
    /**
     * ImageView that contains the logo
     */
    @FXML
    private ImageView logoImageView;

    /**
     * ChoiceBox to select game type
     */
    @FXML
    private ChoiceBox gameTypeChoiceBox;

    /**
     * ChoiceBox to select difficulty
     */
    @FXML
    private ChoiceBox difficultyChoiceBox;

    /**
     * TextField to select number of turns
     */
    @FXML
    private TextField numTurnsTextField;

    /**
     * TextField to select number of colors
     */
    @FXML
    private TextField numColorsTextField;

    /**
     * TextField to select number of holes
     */
    @FXML
    private TextField numHolesTextField;

    /**
     * TextField to select time limet
     */
    @FXML
    private TextField timeLimitTextField;

    /**
     * Label of the logic field
     */
    @FXML
    private Label logicLabel;

    /**
     * Label of the current colors field
     */
    @FXML
    private Label currentColorsLabel;

    /**
     * Label of the chosen colors field
     */
    @FXML
    private Label chosenColorsLabel;

    /**
     * Label of the difficulty field
     */
    @FXML
    private Label difficultyLabel;

    /**
     * Label of the time limit field
     */
    @FXML
    private Label timeLimitLabel;

    /**
     * ChoiceBox to select logic
     */
    @FXML
    private ChoiceBox logicChoiceBox;

    /**
     * Button to add a color
     */
    @FXML
    private Button addColorButton;

    /**
     *  Button to delete a color
     */
    @FXML
    private Button delColorButton;

    /**
     * Button to create a game
     */
    @FXML
    private Button createGameButton;

    /**
     * Button to go back
     */
    @FXML
    private Button backButton;

    /**
     * ListView to store chosen colors
     */
    @FXML
    private ListView chosenColorsListView;

    /**
     * ListView to store available colors
     */
    @FXML
    private ListView colorsListView;

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewCreateGameFX(CtrlPresentation ctrlPresentation)
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
        initializeValues();
        initializeListeners();
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
     * Initializes the listeners
     */
    private void initializeListeners()
    {
        gameTypeChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> toggleChoice(newValue.toString().equals("Maker")));

        numTurnsTextField.textProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (!newValue.matches("\\d"))
                        numTurnsTextField.setText(newValue.replaceAll("[^\\d]", ""));
                });

        numColorsTextField.textProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (!newValue.matches("\\d"))
                        numColorsTextField.setText(newValue.replaceAll("[^\\d]", ""));

                    if (newValue.equals(""))
                        currentColorsLabel.setText("? / ?");
                    else
                    {
                        currentColorsLabel.setText(chosenColorsListView.getItems().size()
                                + " / " +
                                numColorsTextField.getText());
                    }
                });

        numHolesTextField.textProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (!newValue.matches("\\d"))
                        numHolesTextField.setText(newValue.replaceAll("[^\\d]", ""));
                });

        timeLimitTextField.textProperty()
                .addListener((observable, oldValue, newValue) ->
                {
                    if (!newValue.matches("\\d"))
                        timeLimitTextField.setText(newValue.replaceAll("[^\\d]", ""));
                });

        chosenColorsListView.getItems()
                .addListener((ListChangeListener)c -> currentColorsLabel.setText(chosenColorsListView.getItems().size()
                        + " / " +
                        numColorsTextField.getText()));
    }

    /**
     * Initializes different values of the view
     */
    private void initializeValues()
    {
        initializeForm();
        initializeGameType();
        initializeLogic();
        initializeColors();
        initializeDifficulty();
    }

    /**
     * Initializes the form
     */
    private void initializeForm()
    {
        stage.setMinWidth(620);
        stage.setMinHeight(650);
        currentColorsLabel.setMinWidth(Region.USE_PREF_SIZE);
        createGameButton.setMinWidth(Region.USE_PREF_SIZE);
        chosenColorsLabel.setMinWidth(Region.USE_PREF_SIZE);
    }

    /**
     * Initializes the game type ChoiceBox
     */
    private void initializeGameType()
    {
        gameTypeChoiceBox.setItems(FXCollections.observableArrayList("Maker", "Breaker"));
    }

    /**
     * Initializes the difficulty ChoiceBox
     */
    private void initializeDifficulty() {
        difficultyChoiceBox.setItems(FXCollections.observableArrayList(
                ctrlPresentation.getResource("Easy"),
                ctrlPresentation.getResource("Medium"),
                ctrlPresentation.getResource("Hard")));
    }

    /**
     * Initializes the logic ChoiceBox
     */
    private void initializeLogic()
    {
        logicChoiceBox.setItems(FXCollections.observableArrayList("FiveGuess", "Genetic"));
    }

    /**
     * Initializes the colors ListView
     */
    private void initializeColors()
    {
        List<String> colorList = ctrlPresentation.getColorList();
        ObservableList listItems = FXCollections.observableArrayList();

        for (String color : colorList)
        {
            if (!color.equals("EMPTY"))
                listItems.add(color);
        }

        colorsListView.setItems(listItems);
        colorsListView.setCellFactory(param -> new ColorCell());
        chosenColorsListView.setCellFactory(param -> new ColorCell());
    }

    /**
     * Toggles several fields depending on the game type
     * @param enabled Flag indicating whether fields should be enabled
     */
    private void toggleChoice(boolean enabled)
    {
        logicChoiceBox.setDisable(!enabled);
        logicLabel.setDisable(!enabled);

        difficultyChoiceBox.setDisable(enabled);
        difficultyLabel.setDisable(enabled);
        timeLimitTextField.setDisable(enabled);
        timeLimitLabel.setDisable(enabled);

        if (!enabled)
        {
            logicChoiceBox.setValue(null);
        }
        else
            difficultyChoiceBox.setValue(null);
    }

    /**
     * Handles the click on the add color button
     */
    public void onAddColorButtonClick()
    {
        if (colorsListView.getSelectionModel().getSelectedItem() == null
                || numColorsTextField.getText().equals(""))
            return;

        if (chosenColorsListView.getItems().size() >= Integer.valueOf(numColorsTextField.getText()))
            return;

        chosenColorsListView.getItems().add(colorsListView.getSelectionModel().getSelectedItem());
        colorsListView.getItems().remove(colorsListView.getSelectionModel().getSelectedItem());
    }


    /**
     * Handles the click on the delete color button
     */
    public void onDelColorButtonClick()
    {
        if (chosenColorsListView.getSelectionModel().getSelectedItem() == null)
            return;

        colorsListView.getItems().add(chosenColorsListView.getSelectionModel().getSelectedItem());
        chosenColorsListView.getItems().remove(chosenColorsListView.getSelectionModel().getSelectedItem());
    }

    /**
     * Handles the click on the create game button
     */
    public void onCreateGameButtonClick()
    {
        if (gameTypeChoiceBox.getValue() == null || numTurnsTextField.getText().isEmpty()
                || numColorsTextField.getText().isEmpty() || numHolesTextField.getText().isEmpty()
                || (gameTypeChoiceBox.getValue().equals("Breaker") && timeLimitTextField.getText().isEmpty())
                || (gameTypeChoiceBox.getValue().equals("Maker") && logicChoiceBox.getValue() == null)
                || (gameTypeChoiceBox.getValue().equals("Breaker") && difficultyChoiceBox.getValue() == null))
        {
            showAlert(Alert.AlertType.ERROR,
                    ctrlPresentation.getResource("AlertEmptyFieldsTitle"),
                    null,
                    ctrlPresentation.getResource("AlertEmptyFieldsContent"));
            return;
        }

        if (chosenColorsListView.getItems().size() != Integer.valueOf(numColorsTextField.getText()))
        {
            showAlert(Alert.AlertType.ERROR,
                    ctrlPresentation.getResource("AlertNeedToChooseColorsTitle"),
                    null,
                    ctrlPresentation.getResource("AlertNeedToChooseColorsContent")
                            + ' '
                            + numColorsTextField.getText()
                            + ' '
                            + ctrlPresentation.getResource("Colors"));
            return;
        }

        if (numTurnsTextField.getText().equals("0") || numColorsTextField.getText().equals("0")
            || numHolesTextField.getText().equals("0"))
        {
            showAlert(Alert.AlertType.ERROR,
                    ctrlPresentation.getResource("AlertInvalidOptionTitle"),
                    null,
                    ctrlPresentation.getResource("AlertInvalidOptionContent"));
            return;
        }

        Object strategy = logicChoiceBox.getValue();
        Object difficulty = difficultyChoiceBox.getValue();
        String timeLimit = timeLimitTextField.getText();

        ctrlPresentation.setGameType(gameTypeChoiceBox.getValue().toString())
                .setGameNumTurns(Integer.valueOf(numTurnsTextField.getText()))
                .setGameNumColors(Integer.valueOf(numColorsTextField.getText()))
                .setGameNumHoles(Integer.valueOf(numHolesTextField.getText()))
                .setGameTimeLimit(timeLimit.isEmpty() ? 0 : Integer.valueOf(timeLimitTextField.getText()) * 1000L)
                .setGameComputerStrategy(strategy == null ? "makerai" : strategy.toString().toLowerCase())
                .setGameColorList(chosenColorsListView.getItems()).buildGame();

        if (difficulty != null) {
            switch (difficulty.toString().charAt(0)) {
                case 'E':
                case 'F':
                    ctrlPresentation.setGameDifficulty("EASY");
                    break;
                case 'M':
                    ctrlPresentation.setGameDifficulty("MEDIUM");
                    break;
                case 'H':
                case 'D':
                    ctrlPresentation.setGameDifficulty("HARD");
                    break;
                default:
                    ctrlPresentation.setGameDifficulty("EASY");
                    break;
            }
        }


        if (gameTypeChoiceBox.getValue().toString().equals("Maker"))
            ctrlPresentation.toggleCreateCodeFX();
        else
            ctrlPresentation.togglePlayGameFX();
    }

    /**
     * Cell to store a representation of a color
     */
    static class ColorCell extends ListCell<String>
    {
        @Override
        protected void updateItem(String item, boolean empty)
        {
            super.updateItem(item, empty);
            Rectangle rectangle = null;
            if (item != null)
            {
                rectangle = new Rectangle(100, 20);
                rectangle.setFill(Color.web(item.toLowerCase()));
            }

            setGraphic(rectangle); //we have to set nulls so on delete it disappears.
        }
    }

    /**
     * Handles the click on the back button
     */
    @FXML
    public void onBackButtonClick() {
        ctrlPresentation.toggleMenuFX();
    }
}
