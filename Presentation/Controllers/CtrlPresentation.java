package Presentation.Controllers;

import Domain.Controllers.CtrlDomain;
import Presentation.Views.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Math.toIntExact;
import static javafx.application.Application.launch;

/**
 * Controller of the whole Presentation Layer. Responsible of handling user input and passing it over to the Domain
 * Layer.
 */
public class CtrlPresentation extends Application
{
    /**
     * Domain Layer controller
     */
    private CtrlDomain ctrlDomain;

    /**
     * Main Stage for the application
     */
    private Stage primaryStage;

    /**
     * Root layout
     */
    private BorderPane rootLayout;

    /**
     * Template for a new layout
     */
    private GridPane newLayout;

    /**
     * Locale used to allow multilanguage
     */
    private Locale locale;

    /**
     * Bundle to get string resources
     */
    private ResourceBundle resourceBundle;


    /**
     * C L I    V I E W S    L I S T
     */

    /**
     * View responsible of Player Login and Register
     */
    private ViewLogin viewLogin;

    /**
     * View responsible of the main Menu in the application
     */
    private ViewMenu viewMenu;

    /**
     * View responsible of creating a new Game
     */
    private ViewCreateGame viewCreateGame;

    /**
     * View responsible of playing a Game
     */
    private ViewPlayGame viewPlayGame;

    /**
     * View responsible of showing the Score Ranking
     */
    private ViewRanking viewRanking;

    /**
     * View responsible of saving a Game
     */
    private ViewSaveGame viewSaveGame;

    /**
     * View responsible of loading a Game
     */
    private ViewLoadGame viewLoadGame;


    /**
     * List that contains all the existing Views
     */
    private List<View> views;


    /**
     * F X     V I E W S     L I S T
     */

    /**
     * View responsible of Player Login and Register
     */
    private ViewLoginFX viewLoginFX;

    /**
     * View responsible of the main Menu in the application
     */
    private ViewMenuFX viewMenuFX;

    /**
     * View responsible of creating a new Game
     */
    private ViewCreateGameFX viewCreateGameFX;

    /**
     * View responsible of creating the Maker's Code
     */
    private ViewCreateCodeFX viewCreateCodeFX;

    /**
     * View responsible of playing a Game
     */
    private ViewPlayGameFX viewPlayGameFX;

    /**
     * View responsible of showing the Score Ranking
     */
    private ViewRankingFX viewRankingFX;

    /**
     * View responsible of saving a Game
     */
    private ViewSaveGameFX viewSaveGameFX;

    /**
     * View responsible of loading a Game
     */
    private ViewLoadGameFX viewLoadGameFX;

    /**
     * View responsible of player registering
     */
    private ViewRegisterFX viewRegisterFX;

    /**
     * View responsible of showing the help
     */
    private ViewHelpFX viewHelpFX;

    /**
     * List that contains all the existing Views
     */
    private List<ViewFX> viewsFX;

    /**
     * Switch between CLI and FX mode
     */
    private boolean usingFX;

    /**
     * Creates a new Presentation Layer controller and initializes a new Domain Layer controller and all the Views
     */


    public CtrlPresentation() {
        try
        {
            ctrlDomain = new CtrlDomain();
        }
        catch (Exception e)
        {
            //TODO: handle this properly
            e.printStackTrace();
        }
        views = new ArrayList<>();
        viewsFX = new ArrayList<>();
        initializePresentation();
    }



    /**
     * Initializes the whole Presentation Layer and creates all the Views. Toggles the Login view
     */
    private void initializePresentation()
    {
        viewLogin = new ViewLogin(this);
        viewMenu = new ViewMenu(this);
        viewCreateGame = new ViewCreateGame(this);
        viewPlayGame = new ViewPlayGame(this);
        viewRanking = new ViewRanking(this);
        viewSaveGame = new ViewSaveGame(this);
        viewLoadGame = new ViewLoadGame(this);

        views.add(viewLogin);
        views.add(viewMenu);
        views.add(viewCreateGame);
        views.add(viewPlayGame);
        views.add(viewRanking);
        views.add(viewSaveGame);
        views.add(viewLoadGame);



        viewLoginFX = new ViewLoginFX(this);
        viewMenuFX = new ViewMenuFX(this);
        viewCreateGameFX = new ViewCreateGameFX(this);
        viewCreateCodeFX = new ViewCreateCodeFX(this);
        viewPlayGameFX = new ViewPlayGameFX(this);
        viewRankingFX = new ViewRankingFX(this);
        viewSaveGameFX = new ViewSaveGameFX(this);
        viewLoadGameFX = new ViewLoadGameFX(this);
        viewRegisterFX = new ViewRegisterFX(this);
        viewHelpFX = new ViewHelpFX(this);

        viewsFX.add(viewLoginFX);
        viewsFX.add(viewMenuFX);
        viewsFX.add(viewCreateGameFX);
        viewsFX.add(viewCreateCodeFX);
        viewsFX.add(viewPlayGameFX);
        viewsFX.add(viewRankingFX);
        viewsFX.add(viewSaveGameFX);
        viewsFX.add(viewLoadGameFX);
        viewsFX.add(viewRegisterFX);
        viewsFX.add(viewHelpFX);

        locale = new Locale("en", "EN");
        resourceBundle = ResourceBundle.getBundle("Presentation.Views.Bundle", locale);

        usingFX = true;

        if (!usingFX) toggleLogin();
    }

    /**
     * Hides all the existing Views
     */
    private void hideViews()
    {
        for (View view : views)
            view.hide();
    }

    /**
     * Hides all the existing FX Views
     */
    private void hideViewsFX()
    {
        for (ViewFX view : viewsFX)
            view.hide();
    }

    /**
     * Toggles the Login View
     */
    public void toggleLogin()
    {
        hideViews();
        viewLogin.display();
    }

    /**
     * Toggles the Login View FX
     */
    public void toggleLoginFX()
    {
        hideViewsFX();
        toggleViewFX("ViewLoginFX", viewLoginFX);
    }

    /**
     * Toggles the Menu View
     */
    public void toggleMenu()
    {
        hideViews();
        viewMenu.display();
    }

    /**
     * Toggles the Menu View FX
     */
    public void toggleMenuFX()
    {
        hideViewsFX();
        toggleViewFX("ViewMenuFX", viewMenuFX);
    }

    /**
     * Toggles the Create Game View
     */
    public void toggleCreateGame()
    {
        hideViews();
        viewCreateGame.display();
    }

    /**
     * Toggles the Create Game View FX
     */
    public void toggleCreateGameFX()
    {
        hideViewsFX();
        toggleViewFX("ViewCreateGameFX", viewCreateGameFX);
    }

    /**
     * Toggles the Create Code View FX
     */
    public void toggleCreateCodeFX()
    {
        hideViewsFX();
        toggleViewFX("ViewCreateCodeFX", viewCreateCodeFX);
    }

    /**
     * Toggles the Play Game View
     */
    public void togglePlayGame()
    {
        hideViews();
        viewPlayGame.display();
    }

    /**
     * Toggles the Play Game View FX
     */
    public void togglePlayGameFX()
    {
        hideViewsFX();
        toggleViewFX("ViewPlayGameFX", viewPlayGameFX);
    }

    /**
     * Toggles the Ranking View
     */
    public void toggleRanking()
    {
        hideViews();
        viewRanking.display();
    }

    /**
     * Toggles the Ranking View FX
     */
    public void toggleRankingFX()
    {
        hideViewsFX();
        toggleViewFX("ViewRankingFX", viewRankingFX);
    }

    /**
     * Toggles the Save Game View
     */
    public void toggleSaveGame()
    {
        hideViews();
        viewSaveGame.display();
    }

    /**
     * Toggles the Save Game View FX
     */
    public void toggleSaveGameFX()
    {
        hideViewsFX();
        viewSaveGameFX.display();
    }

    /**
     * Toggles the Load Game View
     */
    public void toggleLoadGame()
    {
        hideViews();
        viewLoadGame.display();
    }

    /**
     * Toggles the Load Game View FX
     */
    public void toggleLoadGameFX()
    {
        hideViewsFX();
        toggleViewFX("ViewLoadGameFX", viewLoadGameFX);
    }

    /**
     * Toggles the Register View FX
     */
    public void toggleRegisterFX() {
        hideViewsFX();
        toggleViewFX("ViewRegisterFX", viewRegisterFX);
    }

    /**
     * Toggles the Login View FX
     */
    public void toggleHelpFX()
    {
        hideViewsFX();
        toggleViewFX("ViewHelpFX", viewHelpFX);
    }

    //######DOMAIN CALLS#######

    /**
     * Stores a Player into the system
     * @param identifier Username of the Player to store
     * @param name Public Name of the Player to store
     * @param password Password of the Player to store
     * @throws IOException Couldn't write the Player file because it's either locked or it doesn't have enough
     * permissions
     */
    public void storePlayer(String identifier, String name, String password) throws IOException
    {
        ctrlDomain.storePlayer(identifier, name, password);
    }

    /**
     * Checks if a Player with that username has already been registered in the system
     * @param username Username of the Player
     * @return True if it exists. False otherwise.
     */
    public boolean existsPlayer(String username)
    {
        return ctrlDomain.existsPlayer(username);
    }

    /**
     * Checks if a Game with that filename has already been saved in the system
     * @param filename Filename of the Game
     * @return True if it exists. False otherwise.
     */
    public boolean existsGame(String filename) { return ctrlDomain.existsGame(filename); }

    /**
     * Gets the Public Name of the Logged Player
     * @return Logged Player Public Name
     */
    public String getLoggedPlayerName()
    {
        return ctrlDomain.getLoggedPlayerName();
    }

    /**
     * Tries to log the Player into the system
     * @param username Username of the Player to log in
     * @param password Password of the Player to log in
     * @return True if logged in successfully. False otherwise.
     * @throws ClassNotFoundException Outdated Player file
     * @throws IOException Couldn't read the Player file because it's either locked or it doesn't have enough
     * permissions
     */
    public boolean loginPlayer(String username, String password) throws ClassNotFoundException, IOException
    {
        return ctrlDomain.loginPlayer(username, password);
    }

    /**
     * Sets the Game Type that the Game will have
     * @param gameType It can be either <i>maker</i> or <i>breaker</i>
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameType(String gameType)
    {
        ctrlDomain.setGameType(gameType);
        return this;
    }

    /**
     * Gets the Game Type for the Current Game
     * @return <i>maker</i> if it's a MakerGame. <i>breaker</i> if it's a BreakerGame.
     */
    public String getGameType()
    {
        return ctrlDomain.getGameType();
    }

    /**
     * Sets the Number of Turns that the Game will have
     * @param numTurns Number of Turns that the Game will have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameNumTurns(int numTurns)
    {
        ctrlDomain.setGameNumTurns(numTurns);
        return this;
    }

    /**
     * Gets the Maximum Number of Turns associated to the Current Game
     * @return Number of Turns
     */
    public int getGameNumTurns()
    {
        return ctrlDomain.getGameNumTurns();
    }

    /**
     * Gets the Current Turn associated to the Current Game
     * @return Current Turn
     */
    public int getGameCurrentTurn()
    {
        return ctrlDomain.getGameCurrentTurn();
    }

    /**
     * Sets the Number of Holes that the Game will Have
     * @param numHoles Number of Holes that the Game will have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameNumHoles(int numHoles)
    {
        ctrlDomain.setGameNumHoles(numHoles);
        return this;
    }

    /**
     * Gets the Number of Holes associated to the Current Game
     * @return Number of Holes
     */
    public int getGameNumHoles()
    {
        return ctrlDomain.getGameNumHoles();
    }

    /**
     * Sets the Number of Colors that the Game will have
     * @param numColors Number of Colors that each Peg in the Game can have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameNumColors(int numColors)
    {
        ctrlDomain.setGameNumColors(numColors);
        return this;
    }

    /**
     * Sets the Time Limit that the Game will have
     * @param timeLimit Time Limit that the Game will have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameTimeLimit(long timeLimit)
    {
        ctrlDomain.setGameTimeLimit(timeLimit);
        return this;
    }

    /**
     * Sets the Color List that the Game will have
     * @param colorList Color List that the Game will have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameColorList(List<String> colorList)
    {
        ctrlDomain.setGameColorList(colorList);
        return this;
    }

    /**
     * Gets the Color List associated to the Current Game
     * @return Color List
     */
    public List<String> getGameColorList()
    {
        return ctrlDomain.getGameColorList();
    }

    /**
     * Sets the Computer Strategy that the Game will have
     * @param strategy Strategy that the computer will use and the Game will have
     * @return This Presentation Controller instance
     */
    public CtrlPresentation setGameComputerStrategy(String strategy)
    {
        ctrlDomain.setGameComputerStrategy(strategy);
        return this;
    }
    
    /**
     * Gets the Correct Answer associated to the Current Game
     * @return Correct Answer
     */
    public String getGameCorrectAnswer()
    {
        return ctrlDomain.getGameCorrectAnswer();
    }

    /**
     * Sets the Correct Answer that the Game will have
     * @param answer Correct Answer that the Game will have
     */
    public void setGameCorrectAnswer(String answer)
    {
        ctrlDomain.setGameCorrectAnswer(answer);
    }

    /**
     * Builds a Game with all the parameters previously setup by calling the setters. It also sets up
     * automatically the Ranking so the Game will rank in it.
     */
    public void buildGame()
    {
        ctrlDomain.buildGame();
    }

    /**
     * Gets the Color List associated to the Current Game
     * @return Color List
     */
    public List<String> getColorList()
    {
        return ctrlDomain.getColorList();
    }

    /**
     * Checks if the Game is finished
     * @return True if finished. False otherwise
     */
    public boolean isGameFinished()
    {
        return ctrlDomain.isGameFinished();
    }

    /**
     * Tells the Computer to play a turn according to the play done by the Player
     * @param play Play done by the Player
     * @return Play done by the computer according to the strategy set
     * @throws IOException Couldn't write the Ranking file if the Game finished because it's are either locked or it
     * doesn't have enough permissions
     */
    public String playTurn(String play) throws IOException
    {
        return ctrlDomain.playTurn(play);
    }

    /**
     * Gets the CodeBreaker History associated to the Current Game
     * @return CodeBreaker History
     */
    public List<String> getGameBreakerHistory()
    {
        return ctrlDomain.getGameBreakerHistory();
    }

    /**
     * Gets the CodeMaker History associated to the Current Game
     * @return CodeMaker History
     */
    public List<String> getGameMakerHistory()
    {
        return ctrlDomain.getGameMakerHistory();
    }

    /**
     * Gets if the Player won the Current Game
     * @return True if the Player won. False otherwise
     */
    public boolean getGamePlayerWon()
    {
        return ctrlDomain.getGamePlayerWon();
    }

    /**
     * Gets a Ranking previously stored in the system
     * @return Output of the Ranking stored in the system properly sorted by score
     * @throws ClassNotFoundException Outdated Ranking file
     * @throws IOException Couldn't read the Ranking file because it's either locked or it doesn't have enough
     * permissions
     */
    public String getRanking() throws ClassNotFoundException, IOException
    {
        return ctrlDomain.getRanking();
    }

    /**
     * Saves the Current Game state to the filesystem
     * @param name Name that will identify the Game in the filesystem
     * @throws IOException Couldn't write the Game file because it's either locked or it doesn't have enough
     * permissions
     */
    public void saveGame(String name) throws IOException
    {
        ctrlDomain.saveGame(name);
    }
    /**
     * Loads a Game and sets it as the Current Game
     * @param fileName Name of the saved Game in the filesystem
     * @throws IOException Couldn't read the Game file because it's either locked or it doesn't have enough
     * permissions
     * @throws ClassNotFoundException The Game file was created by an older version of the Game and it's incompatible
     * with the current version
     */
    public void loadGame(String fileName) throws IOException, ClassNotFoundException
    {
        ctrlDomain.loadGame(fileName);
    }

    /**
     * Gets all the names of the saved Games stored in the filesystem
     * @return List of Names of the saved Games
     * @throws IOException Couldn't read the Game files because they are either locked or it doesn't have enough
     * permissions
     * @throws ClassNotFoundException At least one of the saved Game files was created by an older version of the
     * Game and it's incompatible with the current version
     */
    public List<String> getAllSavedGames() throws IOException, ClassNotFoundException
    {
        return ctrlDomain.getAllSavedGames();
    }

    /**
     * Check the amount on hints left that the Player has for the Current Game
     * @return Number of Hints Left
     */
    public int getGameHintsLeft()
    {
        return ctrlDomain.getGameHintsLeft();
    }

    /**
     * Gets the Maximum Hints a Player can have during the Current Game depending of the Difficulty
     * @return Maximum Hints
     */
    public int getGameMaxHints()
    {
        return ctrlDomain.getGameMaxHints();
    }

    /**
     * Gets a computer generated Hint for the Current Game
     * @return Hint if you have hints left. An empty string otherwise
     */
    public String getGameHint()
    {
        return ctrlDomain.getGameHint();
    }

    /**
     * Gets the Game Difficulty off the Current Game
     * @return Difficulty of the Current Game
     */
    public String getGameDifficulty()
    {
        return ctrlDomain.getGameDifficulty();
    }

    /**
     * Sets the Game Difficulty for the Current Game
     * @param difficulty Difficulty to set
     */
    public void setGameDifficulty(String difficulty)
    {
        ctrlDomain.setGameDifficulty(difficulty);
    }

    /**
     * Gets all the possible Difficulty levels available
     * @return All the existing Difficulty Levels
     */
    public List<String> getGameDifficultyLevels()
    {
        return ctrlDomain.getGameDifficultyLevels();
    }

    /**
     * Checks if the evaluation of a Guess done by the Player is correct or he "cheated"
     * @param play Play done by the Player
     * @return True if it's valid. False if the Player "cheated"
     */
    public boolean checkValidGuessResult(String play)
    {
        return ctrlDomain.checkValidGuessResult(play);
    }

    /**
     * Checks if it's a valid turn for the Player to play
     * @return True if it's valid. False otherwise
     */
    public boolean isGameValidTurn()
    {
        return ctrlDomain.isGameValidTurn();
    }

    /**
     * Gets the Current Game time passed since the start of the Game in seconds
     * @return Time spent since the start of the Game in seconds
     */
    public int getGameCurrentTime()
    {
        int time;
        try
        {
            time = toIntExact(ctrlDomain.getGameCurrentTime() / 1000L);
        }
        catch (ArithmeticException ex)
        {
            time = Integer.MAX_VALUE;
        }
        return time;
    }

    /**
     * Gets the Time Limit of the Current Game specified in seconds
     * @return Time Limit of the Game in seconds
     */
    public int getGameTimeLimit()
    {
        int time;
        try
        {
            time = toIntExact(ctrlDomain.getGameTimeLimit() / 1000L);
        }
        catch (ArithmeticException ex)
        {
            time = Integer.MAX_VALUE;
        }
        return time;
    }

    /**
     * Starts officially counting time for the Current Game
     */
    public void startCountingTime()
    {
        ctrlDomain.startCountingTime();
    }

    /**
     * Finishes the game
     */
    public void finishGame()
    {
        ctrlDomain.finishGame();
    }

    /**
     * Terminates all the pending threads
     */
    public void exitProgram()
    {
        ctrlDomain.exitProgram();
    }



    // FX


    /**
     * Initializes the main application stage
     * @param primaryStage The primary stage to be set
     */
    @Override
    public void start(Stage primaryStage) {
        if (!usingFX) return;
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("PasterMind");

        initRootLayout();
        primaryStage.setOnCloseRequest(event -> exitProgram());
        toggleLoginFX();
    }

    /**
     * Initializes a blank layout
     */
    private void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CtrlPresentation.class.getResource("/Presentation/Views/RootLayoutFX.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a View and its associated controller
     * @param viewName Name of the view FXML file
     * @param controller Name of the associated controller
     */
    private void toggleViewFX(String viewName, ViewFX controller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(resourceBundle);
            loader.setLocation(CtrlPresentation.class.getResource(
                    "/Presentation/Views/" + viewName + ".fxml"));
            loader.setController(controller);
            controller.setStage(primaryStage);
            GridPane layoutFX = (GridPane) loader.load();
            rootLayout.setCenter(layoutFX);

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Sets the Locale that the game will use
     * @param language Locale object indicating the language
     */
    public void setLocale(Locale language) {
        locale = language;
        resourceBundle = ResourceBundle.getBundle("Presentation.Views.Bundle", locale);

    }

    /**
     * Gets the Locale that the game is using
     @return Locale object currently used
     */
    public Locale getLocale() {
        return locale;
    }


    /**
     * Gets the string resource corresponding to the given key
     @param key String that identifies the resource within the bundle
     @return Internationalized string corresponding to the key
     */
    public String getResource(String key) {
        return resourceBundle.getString(key);
    }


    /**
     * Returns the hint correctly translated to the current language
     * @param hint Original hint, in English
     * @return The original hint, translated
     */
    public String parseHint(String hint) {
        String lang = locale.getLanguage();
        if (lang.equals("en")) return hint;

        String[] words = hint.split(" ");
        String result = "";
        switch (words[words.length-1]) {
            case "position":
                result = resourceBundle.getString("Theres")
                        + ' '
                        + resourceBundle.getString((words[2].substring(0, 1) + words[2].substring(1).toLowerCase()))
                        + ' '
                        + resourceBundle.getString("AtThe")
                        + ' '
                        + words[words.length-2];
                break;

            case "Peg":
                if (words[4].equals("and")) {
                    result = resourceBundle.getString("Theres")
                            + ' '
                            + resourceBundle.getString((words[3].substring(0, 1) + words[3].substring(1).toLowerCase()))
                            + ' '
                            + resourceBundle.getString("AndA")
                            + ' '
                            + resourceBundle.getString((words[words.length-2].substring(0, 1) + words[words.length-2].substring(1).toLowerCase()));
                }

                else {
                    result = resourceBundle.getString("Theres")
                            + ' '
                            + resourceBundle.getString((words[words.length-2].substring(0, 1) + words[words.length-2].substring(1).toLowerCase()));
                }
                break;

            case "times":
                    result = resourceBundle.getString("Repeated")
                            + ' '
                            + words[words.length-2]
                            + ' '
                            + resourceBundle.getString("AppearsTimes");
                break;
        }
        return result;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
