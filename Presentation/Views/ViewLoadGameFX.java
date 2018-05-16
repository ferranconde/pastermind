package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

/**
 * View FX to load a game
 */
public class ViewLoadGameFX extends ViewFX {

    /**
     * Button to go back
     */
    @FXML
    private Button backButton;

    /**
     * Button to load a game
     */
    @FXML
    private Button loadGameButton;

    /**
     * TableGame to store games to be loaded
     */
    @FXML
    private TableView<GameEntry> gameTable;

    /**
     * TableColumn to show games
     */
    @FXML
    private TableColumn<GameEntry, String> filenameColumn;

    /**
     * List of game filenames
     */
    private List<String> filenameDump;

    /**
     * ObservableList to add games to the table
     */
    private ObservableList<GameEntry> filenameData = FXCollections.observableArrayList();

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewLoadGameFX(CtrlPresentation ctrlPresentation)
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
        stage.setWidth(650);
        stage.setMinWidth(650);
        stage.setHeight(450);
        stage.setMinHeight(450);
        loadAllEntries();

        gameTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> loadGameButton.setDisable(false));

    }

    /**
     * Loads all saved games
     */
    private void loadAllEntries()
    {
        filenameData.clear();
        try {
            filenameDump = ctrlPresentation.getAllSavedGames();
            for (int s = 0; s < filenameDump.size(); s++) {
                filenameData.add(
                        new GameEntry(filenameDump.get(s).replace(".kys", ""), s));
            }

            filenameColumn.setCellValueFactory(cellData -> cellData.getValue().getFilenameProperty());
            gameTable.setItems(filenameData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Loads the selected game
     * @param gameEntry Table entry representing the game
     */
    private void loadGameEntry(GameEntry gameEntry) {
        if (gameEntry != null) {
            try {
                ctrlPresentation.loadGame(filenameDump.get(gameEntry.index));
                ctrlPresentation.togglePlayGameFX();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
     * Handles the click on the back button
     */
    @FXML
    public void onBackButtonClick() {
        ctrlPresentation.toggleMenuFX();
    }

    /**
     * Handles the click on the load button
     */
    @FXML
    public void onLoadGameButtonClick()
    {
        loadGameEntry(gameTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Class to represent a table entry
     */
    static class GameEntry {

        /**
         * Property indicating the filename
         */
        private SimpleStringProperty filename;
        /**
         * Index of the game
         */
        private int index;

        /**
         * Constructs a game entry
         * @param filename The saved game filename
         * @param i The saved game index
         */
        GameEntry(String filename, int i) {
            this.filename = new SimpleStringProperty(filename);
            index = i;
        }

        /**
         * Gets the filename property
         * @return A property representing the filename
         */
        StringProperty getFilenameProperty() {
            return filename;
        }
    }
}
