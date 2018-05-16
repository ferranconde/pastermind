package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.toIntExact;


/**
 * View FX to show the game process
 */
public class ViewPlayGameFX extends ViewFX implements IUserPanel
{
    /**
     * Stores the breaker plays history
     */
    @FXML
    private ListView breakerHistoryListView;

    /**
     * Stores the maker plays history
     */
    @FXML
    private ListView makerHistoryListView;

    /**
     * Color list container
     */
    @FXML
    private HBox colorListHBox;

    /**
     * Chosen play container
     */
    @FXML
    private HBox chosenPlayHBox;

    /**
     * ListView to store the chosen play
     */
    @FXML
    private ListView chosenPlayListView;

    /**
     * ListView to store the secret code
     */
    @FXML
    private ListView secretCodeListView;

    /**
     * Label of the game type
     */
    @FXML
    private Label gameTypeLabel;

    /**
     * Label of the turns information
     */
    @FXML
    private Label turnsInfoLabel;

    /**
     * Label of the hints information
     */
    @FXML
    private Label hintsInfoLabel;

    /**
     * Label of the time information
     */
    @FXML
    private Label timeInfoLabel;

    /**
     * Label of the chosen colors
     */
    @FXML
    private Label chosenColorsLabel;

    /**
     * Button to play a guess
     */
    @FXML
    private Button guessButton;

    /**
     * Button to undo
     */
    @FXML
    private Button undoButton;

    /**
     * Button to save game
     */
    @FXML
    private Button saveButton;

    /**
     * Button to get a hint
     */
    @FXML
    private Button hintButton;

    /**
     * Button to abort
     */
    @FXML
    private Button abortButton;

    /**
     * Animation to show loading process
     */
    @FXML
    private ProgressIndicator loadingProgressIndicator;

    /**
     * Timer to control time elapsed
     */
    private Timer timer;

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewPlayGameFX(CtrlPresentation ctrlPresentation)
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
     * Initializes the form
     */
    private void initializeForm()
    {
        stage.setHeight(600);
        stage.setWidth(700);
        stage.setMinHeight(600);
        stage.setMinWidth(700);
        timeInfoLabel.setMinWidth(Region.USE_PREF_SIZE);
        gameTypeLabel.setMinWidth(Region.USE_PREF_SIZE);
        hintsInfoLabel.setMinWidth(Region.USE_PREF_SIZE);
        turnsInfoLabel.setMinWidth(Region.USE_PREF_SIZE);
        chosenColorsLabel.setMinWidth(Region.USE_PREF_SIZE);
        guessButton.setMinWidth(Region.USE_PREF_SIZE);
        abortButton.setMinWidth(Region.USE_PREF_SIZE);
        loadingProgressIndicator.setMinWidth(Region.USE_PREF_SIZE);

        initializeGameType();
        initializeUserPanel();
        initializePlayHistory();
        updateTime();
    }

    /**
     * Initializes the secret code
     */
    private void initializeSecretCode()
    {
        secretCodeListView.setCellFactory(lv -> new BreakerCell());
        updateSecretCodeListView();
    }

    /**
     * Updates the secret code list view
     */
    private void updateSecretCodeListView()
    {
        secretCodeListView.getItems().clear();
        secretCodeListView.getItems().add(
                new BreakerEntry(pegStringToCircleList(ctrlPresentation.getGameCorrectAnswer())));
    }

    /**
     * Initializes several fields depending on the game type
     */
    private void initializeGameType()
    {
        if (ctrlPresentation.getGameType().equals("breaker"))
        {
            hintsInfoLabel.setVisible(true);
            timeInfoLabel.setVisible(true);
            hintButton.setVisible(true);
            turnsInfoLabel.setVisible(true);
            guessButton.setText(ctrlPresentation.getResource("Guess"));
            secretCodeListView.setDisable(true);
            loadingProgressIndicator.setVisible(false);
        }
        else
        {
            hintsInfoLabel.setVisible(false);
            timeInfoLabel.setVisible(false);
            hintButton.setVisible(false);
            guessButton.setText(ctrlPresentation.getResource("Evaluate"));
            initializeSecretCode();
            loadingProgressIndicator.setVisible(false);
        }

        gameTypeLabel.setText(ctrlPresentation.getResource("GameTypeLabel") + ' ' + ctrlPresentation.getGameType());


        updateStats();
        updatePlayListLabel();
    }

    /**
     * Initializes the user panel
     */
    private void initializeUserPanel()
    {
        colorListHBox.setSpacing(10);
        MouseHandler mouseHandler = new MouseHandler();
        if (ctrlPresentation.getGameType().equals("maker"))
        {
            Circle circleBlack = new Circle(15, Paint.valueOf("black"));
            Circle circleWhite = new Circle(15, Paint.valueOf("white"));
            circleBlack.setUserData(this);
            circleWhite.setUserData(this);
            mouseHandler.setCircleMousePress(circleBlack);
            mouseHandler.setCircleMousePress(circleWhite);

            colorListHBox.getChildren().addAll(circleBlack, circleWhite);
            return;
        }
        List<String> colors = ctrlPresentation.getGameColorList();
        generateBreakerPanel(colorListHBox, mouseHandler, colors);
    }

    /**
     * Initializes the play history
     */
    private void initializePlayHistory()
    {
        breakerHistoryListView.setCellFactory(lv -> new BreakerCell());

        makerHistoryListView.setCellFactory(lv -> new ListCell<MakerEntry>()
        {
            @Override
            public void updateItem(MakerEntry item, boolean empty)
            {
                super.updateItem(item, empty);
                if (empty || item == null)
                {
                    setText(null);
                    setGraphic(null);
                } else
                {
                    setGraphic(item.getGuessResult());
                }
            }
        });

        updatePlayHistory();
    }

    /**
     * Initializes the listeners
     */
    private void initializeListeners()
    {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                Platform.runLater(() ->
                {
                    updateTime();
                    updatePlayHistory();
                });
            }
        }, 100, 100);

        ctrlPresentation.startCountingTime();
    }

    /**
     * Converts seconds to a more user-friendly format
     * @param seconds The amount to be converted
     * @return The time, well formatted
     */
    private String secondsToHourMinutesSeconds(int seconds)
    {
        long minutes = TimeUnit.SECONDS.toMinutes((long)seconds) % 60;
        long hours = TimeUnit.SECONDS.toHours((long)seconds);

        return hours + "h : " + minutes + "m : " + seconds%60 + "s";
    }


    /**
     * Updates the time label
     */
    private void updateTime()
    {
        if (ctrlPresentation.isGameValidTurn())
        {
            if (ctrlPresentation.getGameTimeLimit() == Integer.MAX_VALUE) {
                timeInfoLabel.setText(ctrlPresentation.getResource("TimeInfoLabel") + ' '
                        + ctrlPresentation.getResource("Unlimited"));
            }
            else {
                timeInfoLabel.setText(ctrlPresentation.getResource("TimeInfoLabel") + ' ' + secondsToHourMinutesSeconds(
                        ctrlPresentation.getGameTimeLimit() - ctrlPresentation.getGameCurrentTime()));
            }
        }
        else
        {
            timer.cancel();
            initializeSecretCode();

            if (ctrlPresentation.getGamePlayerWon())
            {
                showAlert(Alert.AlertType.INFORMATION,
                        ctrlPresentation.getResource("AlertWonTitle"),
                        null,
                        ctrlPresentation.getResource("AlertWonContent"));
            }
            else
            {
                showAlert(Alert.AlertType.WARNING,
                        ctrlPresentation.getResource("AlertLostTitle"),
                        null,
                        ctrlPresentation.getResource("AlertLostContent"));
            }
            ctrlPresentation.toggleMenuFX();
        }
    }

    /**
     * Adds the chosen circle to the play list
     * @param circle A circle representing the peg
     */
    @Override
    public void addCircleToChosenPlayList(Circle circle)
    {
        if (chosenPlayListView.getItems().size() < ctrlPresentation.getGameNumHoles())
        {
            chosenPlayListView.getItems().add(circle);
            updatePlayListLabel();
        }
    }

    /**
     * Updates the play list label
     */
    private void updatePlayListLabel()
    {
        chosenColorsLabel.setText(chosenPlayListView.getItems().size() + " / " + ctrlPresentation.getGameNumHoles());
    }

    /**
     * Updates the play history
     */
    private void updatePlayHistory()
    {
        breakerHistoryListView.getItems().clear();
        makerHistoryListView.getItems().clear();
        List<String> breakerHistory = ctrlPresentation.getGameBreakerHistory();
        Collections.reverse(breakerHistory);
        List<String> makerHistory = ctrlPresentation.getGameMakerHistory();
        Collections.reverse(makerHistory);

        for (int i = 0; i < breakerHistory.size(); ++i)
        {
            List<Circle> breakerCircleList = pegStringToCircleList(breakerHistory.get(i));
            int blackPegs = 0;
            int whitePegs = 0;
            int emptyPegs = 0;

            breakerHistoryListView.getItems().add(new BreakerEntry(breakerCircleList));

            //if we are playing as maker, there will always be 1 play not scored yet. We don't want to get nulls.
            if (ctrlPresentation.getGameType().equals("maker"))
            {
                if (i != 0)
                {
                    blackPegs = countOccurrences(makerHistory.get(i - 1), "BLACK");
                    whitePegs = countOccurrences(makerHistory.get(i - 1), "WHITE");
                    emptyPegs = ctrlPresentation.getGameNumHoles() - blackPegs - whitePegs;
                    makerHistoryListView.getItems().add(new MakerEntry(blackPegs, whitePegs, emptyPegs));
                }
                else
                {
                    makerHistoryListView.getItems().add(new MakerEntry(-1, -1, -1));
                }
            }
            else
            {
                blackPegs = countOccurrences(makerHistory.get(i), "BLACK");
                whitePegs = countOccurrences(makerHistory.get(i), "WHITE");
                emptyPegs = ctrlPresentation.getGameNumHoles() - blackPegs - whitePegs;
                makerHistoryListView.getItems().add(new MakerEntry(blackPegs, whitePegs, emptyPegs));
            }
        }
    }

    /**
     * Counts the occurrences of the 2nd parameter in the 1st parameter
     * @param haystack The string to search in
     * @param needle The string to look for
     * @return Number of occurrences
     */
    private int countOccurrences(String haystack, String needle)
    {
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1)
        {
            lastIndex = haystack.indexOf(needle, lastIndex);

            if (lastIndex != -1)
            {
                ++count;
                lastIndex += needle.length();
            }
        }

        return count;
    }

    /**
     * List of pegs, represented in circles
     * @param guess Guess, codified as a string
     * @return A list of circles representing the guess
     */
    private List<Circle> pegStringToCircleList(String guess)
    {
        List<Circle> circleList = new ArrayList<>();

        for (String peg : guess.split(" "))
        {
            Circle c = new Circle(15, Paint.valueOf(peg.toLowerCase()));
            circleList.add(c);
        }

        return circleList;
    }

    /**
     * Handles the click on the guess button
     */
    @FXML
    public void onGuessButtonClick()
    {
        if (ctrlPresentation.getGameType().equals("breaker") &&
                chosenPlayListView.getItems().size() != ctrlPresentation.getGameNumHoles())
        {
            showAlert(Alert.AlertType.WARNING,
                    ctrlPresentation.getResource("AlertInvalidGuessTitle"),
                    null,
                    ctrlPresentation.getResource("AlertInvalidGuessContent")
                            + ' '
                            + (ctrlPresentation.getGameNumHoles() - chosenPlayListView.getItems().size())
                            + ' '
                            + ctrlPresentation.getResource("Colors"));
            return;
        }

        String play = generatePlay(ctrlPresentation.getGameNumHoles(),
                ctrlPresentation.getGameColorList(),
                chosenPlayListView.getItems());

        if (ctrlPresentation.getGameType().equals("maker"))
        {
            if (!ctrlPresentation.checkValidGuessResult(play))
            {
                showAlert(Alert.AlertType.ERROR,
                        ctrlPresentation.getResource("AlertInvalidEvaluationTitle"),
                        null,
                        ctrlPresentation.getResource("AlertInvalidEvaluationContent"));
                return;
            }

        }

        loadingProgressIndicator.setVisible(true);

        Task<String> task = new Task<String>()
        {
            @Override
            protected String call()
            {
                try
                {
                    return ctrlPresentation.playTurn(play);
                }
                catch (IOException e)
                {
                    showAlert(Alert.AlertType.ERROR,
                            ctrlPresentation.getResource("AlertIOExceptionTitle"),
                            null,
                            ctrlPresentation.getResource("AlertIOExceptionContent"));
                }

                return "";
            }
        };

        task.setOnSucceeded(e -> {
                loadingProgressIndicator.setVisible(false);
            }
        );

        new Thread(task).start();
        chosenPlayListView.getItems().clear();
        updateStats();
        updatePlayHistory();
    }

    /**
     * Updates the different game stats
     */
    private void updateStats()
    {
        turnsInfoLabel.setText(ctrlPresentation.getResource("TurnsInfoLabel") + ' ' + ctrlPresentation.getGameCurrentTurn()
                + " / " + ctrlPresentation.getGameNumTurns());

        if (ctrlPresentation.getGameType().equals("breaker"))
        {
            hintsInfoLabel.setText(ctrlPresentation.getResource("HintsInfoLabel") + ' ' + ctrlPresentation.getGameHintsLeft()
                    + " / " + ctrlPresentation.getGameMaxHints());
        }
    }

    /**
     * Handles the click on the undo button
     */
    @FXML
    public void onUndoButtonClick()
    {
        if (chosenPlayListView.getItems().size() != 0)
        {
            chosenPlayListView.getItems().remove(chosenPlayListView.getItems().size() - 1);
            updatePlayListLabel();
        }
    }

    /**
     * Handles the save button click
     */
    @FXML
    public void onSaveButtonClick()
    {
        TextInputDialog saveGameDialog = new TextInputDialog();
        saveGameDialog.setTitle(ctrlPresentation.getResource("AlertSaveGameTitle"));
        saveGameDialog.setHeaderText(null);
        saveGameDialog.setContentText(ctrlPresentation.getResource("AlertSaveGameContent"));

        saveGameDialog.getDialogPane().getStylesheets()
                .add(getClass().getResource("/Presentation/pasterStyle.css").toExternalForm());
        saveGameDialog.getDialogPane().getStyleClass().add("pane");

        Optional<String> filename = saveGameDialog.showAndWait();
        if (filename.isPresent()) {
            try {
                if (ctrlPresentation.existsGame(filename.get())) {
                    Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION,
                            ctrlPresentation.getResource("AlertExistingGameTitle"),
                            null,
                            ctrlPresentation.getResource("AlertExistingGameContent"));
                    if (result.get() == ButtonType.OK) {
                        ctrlPresentation.saveGame(filename.get().equals("") ? LocalDateTime.now().toString() : filename.get());
                        showAlert(Alert.AlertType.INFORMATION,
                                ctrlPresentation.getResource("AlertGameSavedTitle"),
                                null,
                                ctrlPresentation.getResource("AlertGameSavedContent"));
                    }
                }
                else {
                    ctrlPresentation.saveGame(filename.get().equals("") ? LocalDateTime.now().toString() : filename.get());
                    showAlert(Alert.AlertType.INFORMATION,
                            ctrlPresentation.getResource("AlertGameSavedTitle"),
                            null,
                            ctrlPresentation.getResource("AlertGameSavedContent"));
                }
            }
            catch (IOException e) {
                showAlert(Alert.AlertType.ERROR,
                        ctrlPresentation.getResource("AlertIOExceptionTitle"),
                        null,
                        ctrlPresentation.getResource("AlertIOExceptionContent"));
            }
        }
        else
        {
            showAlert(Alert.AlertType.WARNING,
                    ctrlPresentation.getResource("AlertMissingNameTitle"),
                    null,
                    ctrlPresentation.getResource("AlertMissingNameContent"));
        }
    }

    /**
     * Handles the click on the hint button
     */
    @FXML
    public void onHintButtonClick()
    {
        if (ctrlPresentation.getGameDifficulty().equals("HARD"))
        {
            showAlert(Alert.AlertType.WARNING,
                    ctrlPresentation.getResource("AlertNoHintsTitle"),
                    null,
                    ctrlPresentation.getResource("AlertNoHintsHardContent"));
        }
        else if (ctrlPresentation.getGameHintsLeft() <= 0)
        {
            showAlert(Alert.AlertType.WARNING,
                    ctrlPresentation.getResource("AlertNoHintsTitle"),
                    null,
                    ctrlPresentation.getResource("AlertNoHintsContent"));
        }
        else
        {
            showAlert(Alert.AlertType.INFORMATION,
                    ctrlPresentation.getResource("AlertHint"),
                    null,
                    ctrlPresentation.parseHint(ctrlPresentation.getGameHint()));

            updateStats();
        }
    }

    /**
     * Handles the click on the abort button
     */
    @FXML
    public void onAbortButtonClick()
    {
        Optional<ButtonType> result = showAlert(Alert.AlertType.CONFIRMATION,
                ctrlPresentation.getResource("AlertAbortTitle"),
                null,
                ctrlPresentation.getResource("AlertAbortContent"));

        if (result.get() == ButtonType.OK)
        {
            ctrlPresentation.finishGame();
            ctrlPresentation.toggleMenuFX();
        }

    }

    private class BreakerEntry
    {
        private List<Circle> guess;

        BreakerEntry(List<Circle> pegs)
        {
            guess = pegs;
        }

        public HBox getGuess()
        {
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER);
            hbox.getChildren().addAll(guess);
            return hbox;
        }
    }

    private class MakerEntry
    {
        private int numBlacks;
        private int numWhites;
        private int numEmpties;

        MakerEntry(int blacks, int whites, int empties)
        {
            numBlacks = blacks;
            numWhites = whites;
            numEmpties = empties;
        }

        HBox getGuessResult()
        {
            HBox hBox = new HBox(15);
            hBox.setAlignment(Pos.CENTER);
            //Hax to draw empty circle
            if (numBlacks == -1 && numWhites == -1 && numEmpties == -1)
            {
                Circle c = new Circle(15);
                c.setFill(Color.rgb(0, 0, 0, 0));
                hBox.getChildren().add(c);
                return hBox;
            }

            StackPane stackBlack = new StackPane();
            StackPane stackWhite = new StackPane();

            Circle blackCircle = new Circle(15, Paint.valueOf("black"));
            Circle whiteCircle = new Circle(15, Paint.valueOf("white"));

            Text blackText = new Text(String.valueOf(numBlacks));
            blackText.setFill(Paint.valueOf("white"));
            Text whiteText = new Text(String.valueOf(numWhites));

            blackText.setBoundsType(TextBoundsType.VISUAL);
            whiteText.setBoundsType(TextBoundsType.VISUAL);

            stackBlack.getChildren().addAll(blackCircle, blackText);
            stackWhite.getChildren().addAll(whiteCircle, whiteText);

            hBox.getChildren().addAll(stackBlack, stackWhite);
            return hBox;
        }
    }

    /**
     * Cell used to represent breaker information
     */
    private class BreakerCell extends ListCell<BreakerEntry>
    {
        /**
         * Updates a BreakerEntry
         * @param item The entry to be updated
         * @param empty Flag indicating if entry is empty
         */
        @Override
        public void updateItem(BreakerEntry item, boolean empty)
        {
            super.updateItem(item, empty);
            if (empty || item == null)
            {
                setText(null);
                setGraphic(null);
            }
            else
            {
                setGraphic(item.getGuess());
            }
        }
    }
}