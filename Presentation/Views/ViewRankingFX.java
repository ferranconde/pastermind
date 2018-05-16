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

/**
 * View FX to show the ranking
 */
public class ViewRankingFX extends ViewFX
{

    /**
     * Button to go back
     */
    @FXML
    private Button backButton;

    /**
     * Table to store the ranking entries
     */
    @FXML
    private TableView<RankingEntry> rankingTable;

    /**
     * Column to store the player
     */
    @FXML
    private TableColumn<RankingEntry, String> playerColumn;

    /**
     * Column to store the score
     */
    @FXML
    private TableColumn<RankingEntry, String> scoreColumn;


    /**
     * ObservableList to update the table
     */
    private ObservableList<RankingEntry> rankingData = FXCollections.observableArrayList();

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewRankingFX(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }


    private ObservableList<RankingEntry> parseRanking(String rankingDump) {
        ObservableList<RankingEntry> ret = FXCollections.observableArrayList();
        for (String line : rankingDump.split("\n")) {
            String[] data = line.split("\\|");
            RankingEntry e = new RankingEntry(data[1].trim(), data[2].trim());
            ret.add(e);
        }
        return ret;
    }

    /**
     * Initializes the view
     */
    @Override
    @FXML
    public void initialize()
    {
        try {
            String rankingDump = ctrlPresentation.getRanking();
            rankingData = parseRanking(rankingDump);

        } catch (Exception e) {
            e.printStackTrace();
        }

        playerColumn.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().getScoreProperty());

        rankingTable.setItems(rankingData);
        stage.setWidth(650);
        stage.setMinWidth(650);
        stage.setHeight(450);
        stage.setMinHeight(450);

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
     * Class to represent a ranking entry
     */
    static class RankingEntry {
        /**
         * Property to store the player name
         */
        private SimpleStringProperty name;
        /**
         * Property to store the score
         */
        private SimpleStringProperty score;

        /**
         * Constructs a ranking entry
         * @param n Name of the player in the entry
         * @param s Score of the entry
         */
        RankingEntry(String n, String s) {
            name = new SimpleStringProperty(n);
            score = new SimpleStringProperty(s);
        }

        /**
         * Gets the name of the entry
         * @return Name of the entry
         */
        public String getName() {
            return name.get();
        }

        /**
         * Gets the score of the entry
         * @return Score of the entry
         */
        public String getScore() {
            return score.get();
        }

        /**
         * Gets the property wrapping the name
         * @return Name Property
         */
        StringProperty getNameProperty() {
            return name;
        }

        /**
         * Gets the property wrapping the score
         * @return Score Property
         */
        StringProperty getScoreProperty() {
            return score;
        }
    }
}