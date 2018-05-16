package Domain.Controllers;

import Data.CtrlGameData;
import Data.CtrlPlayerData;
import Data.CtrlRankingData;
import Domain.Classes.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller of the whole Domain Layer. It communicates with the Presentation Layer and the Persistence Layer.
 */
public class CtrlDomain
{
    /**
     * Persistence Controller of the Player class
     */
    private CtrlPlayerData ctrlPlayer;

    /**
     * Persistence Controller of the Ranking class
     */
    private CtrlRankingData ctrlRanking;

    /**
     * Persistence Controller of the Game class
     */
    private CtrlGameData ctrlGame;

    /**
     * Player logged in who is interacting with the system
     */
    private Player loggedPlayer;

    /**
     * GameBuilder used to create different Games
     */
    private Game.GameBuilder gameBuilder;

    /**
     * Game that is currently being played
     */
    private Game currentGame;

    /**
     * Ranking that holds the score of all the Players
     */
    private Ranking ranking;


    /**
     * Creates a new Domain Controller with the necessary parameters initialized
     * @throws ClassNotFoundException Some data files are outdated and cannot be loaded
     * @throws IOException Couldn't read the data files because they are either locked or it doesn't have enough
     * permissions
     */
    public CtrlDomain() throws ClassNotFoundException, IOException
    {
        //Create all folder structure
        ctrlPlayer = CtrlPlayerData.getInstance();
        ctrlRanking = CtrlRankingData.getInstance();
        ctrlGame = CtrlGameData.getInstance();
        loggedPlayer = null;
        gameBuilder = new Game.GameBuilder();
        currentGame = null;
        if (ctrlRanking.exists("Ranking"))
        {
            ranking = (Ranking)ctrlRanking.get("Ranking");
        }
        else
            ranking = new Ranking();
    }

    /**
     * Gets the Public Name of the Logged Player
     * @return Logged Player Public Name
     */
    public String getLoggedPlayerName()
    {
        return loggedPlayer.getName();
    }

    /**
     * Gets a Player previously stored in the system
     * @param username Username of the Player to retrieve
     * @return Player instance stored in the system
     * @throws ClassNotFoundException Outdated Player file
     * @throws IOException Couldn't read the Player file because it's either locked or it doesn't have enough
     * permissions
     */
    private Player getPlayer(String username) throws ClassNotFoundException, IOException
    {
        return (Player)ctrlPlayer.get(username);
    }

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
        ctrlPlayer.store(identifier, new Player(identifier, name, password));
    }

    /**
     * Stores the Ranking into the system
     * @throws IOException Couldn't write the Ranking file because it's either locked or it doesn't have enough
     * permissions
     */
    public void storeRanking() throws IOException
    {
        ctrlRanking.store("Ranking", ranking);
    }

    /**
     * Gets a Ranking previously stored in the system
     * @return Ranking instance stored in the system
     * @throws ClassNotFoundException Outdated Ranking file
     * @throws IOException Couldn't read the Ranking file because it's either locked or it doesn't have enough
     * permissions
     */
    public String getRanking() throws ClassNotFoundException, IOException
    {
        if (ctrlRanking.exists("Ranking"))
            return ctrlRanking.get("Ranking").toString();

        return "There is no ranking yet!";
    }

    /**
     * Gets all the stored Players in the system
     * @return List of Players stored in the system
     * @throws ClassNotFoundException Outdated Player file
     * @throws IOException Couldn't read one of the Player files because it's either locked or it doesn't have enough
     * permissions
     */
    @SuppressWarnings("unchecked")
    private List<Player> getAllPlayer() throws ClassNotFoundException, IOException
    {
        //Ghetto af. I feel dirty.
        return (List<Player>)(Object)ctrlPlayer.getAll();
    }

    /**
     * Tries to log the Player into the system and prepares the Game Builder with the logged Player
     * @param username Username of the Player to log in
     * @param password Password of the Player to log in
     * @return True if logged in successfully. False otherwise.
     * @throws ClassNotFoundException Outdated Player file
     * @throws IOException Couldn't read the Player file because it's either locked or it doesn't have enough
     * permissions
     */
    public boolean loginPlayer(String username, String password) throws ClassNotFoundException, IOException
    {
        Player player = getPlayer(username);
        if (player.getPassword().equals(password))
        {
            loggedPlayer = player;
            gameBuilder.setPlayer(loggedPlayer);
            return true;
        }

        return false;
    }

    /**
     * Checks if a Player with that username has already been registered in the system
     * @param username Username of the Player
     * @return True if it exists. False otherwise.
     */
    public boolean existsPlayer(String username)
    {
        return ctrlPlayer.exists(username);
    }

    /**
     * Checks if a game with that filename has already been saved in the system
     * @param filename Filename of the Game
     * @return True if it exists. False otherwise.
     */
    public boolean existsGame(String filename) {
        return ctrlGame.exists("GAME - " + filename);
    }

    /**
     * Sets the Game Type for the Builder
     * @param gameType It can be either <i>maker</i> or <i>breaker</i>
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameType(String gameType)
    {
        gameBuilder.setGameType(gameType);
        return this;
    }

    /**
     * Gets the Game Type for the Current Game
     * @return <i>maker</i> if it's a MakerGame. <i>breaker</i> if it's a BreakerGame.
     */
    public String getGameType()
    {
        return (currentGame instanceof BreakerGame) ? "breaker" : "maker";
    }

    /**
     * Sets the Game Number of Turns for the Builder
     * @param numTurns Number of Turns that the Game will have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameNumTurns(int numTurns)
    {
        gameBuilder.setNumTurns(numTurns);
        return this;
    }

    /**
     * Gets the Maximum Number of Turns associated to the Current Game
     * @return Number of Turns
     */
    public Integer getGameNumTurns()
    {
        return currentGame.getNumTurns();
    }

    /**
     * Gets the Current Turn associated to the Current Game
     * @return Current Turn
     */
    public int getGameCurrentTurn()
    {
        return currentGame.getCurrentTurn();
    }

    /**
     * Sets the Game Number of Holes for the Builder
     * @param numHoles Number of Holes that the Game will have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameNumHoles(int numHoles)
    {
        gameBuilder.setNumHoles(numHoles);
        return this;
    }

    /**
     * Gets the Number of Holes associated to the Current Game
     * @return Number of Holes
     */
    public Integer getGameNumHoles()
    {
        return currentGame.getNumHoles();
    }

    /**
     * Sets the Number of Colors for the Builder
     * @param numColors Number of Colors that each Peg in the Game can have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameNumColors(int numColors)
    {
        gameBuilder.setNumColors(numColors);
        return this;
    }

    /**
     * Gets the Number of Colors associated to the Current Game
     * @return Number of Colors that each Peg in the Game can have
     */
    public Integer getGameNumColors()
    {
        return currentGame.getNumColors();
    }

    /**
     * Sets the Time Limit for the Builder
     * @param timeLimit Time Limit that the Game will have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameTimeLimit(long timeLimit)
    {
        gameBuilder.setTimeLimit(timeLimit);
        return this;
    }

    /**
     * Gets the Time Limit associated to the Current Game
     * @return Time Limit
     */
    public long getGameTimeLimit()
    {
        return currentGame.getTimeLimit();
    }

    /**
     * Sets the Color List for the Builder
     * @param colorList Color List that the Game will have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameColorList(List<String> colorList)
    {
        List<Peg.Color> list = new ArrayList<>();
        for (String color : colorList)
        {
            list.add(Peg.Color.valueOf(color));
        }

        gameBuilder.setColorList(list);
        return this;
    }

    /**
     * Gets the Color List associated to the Current Game
     * @return Color List
     */
    public List<String> getGameColorList()
    {
        List<String> colors = new ArrayList<>();
        for (Peg.Color color : currentGame.getColors())
            colors.add(color.toString());

        return colors;
    }

    /**
     * Sets the Computer Strategy for the Builder
     * @param strategy Strategy that the computer will use and the Game will have
     * @return This Domain Controller instance
     */
    public CtrlDomain setGameComputerStrategy(String strategy)
    {
        //TODO: Fiveguess i GeneticAlgorithm els paràmetres que reben
        if (strategy.equals("makerai"))
            gameBuilder.setComputerStrategy(new MakerAI());
        else
        {
            if (strategy.equals("fiveguess"))
                gameBuilder.setComputerStrategy(new FiveGuess());
            else if (strategy.equals("genetic"))
                gameBuilder.setComputerStrategy(new GeneticAlgorithm());
        }
        return this;
    }

    /**
     * Gets the Correct Answer associated to the Current Game
     * @return Correct Answer
     */
    public String getGameCorrectAnswer()
    {
        return currentGame.getCorrectAnswer() == null ? "" : currentGame.getCorrectAnswer().toString();
    }

    /**
     * Sets the Correct Answer for the Builder
     * @param correctAnswer Correct Answer that the Game will have
     */
    public void setGameCorrectAnswer(String correctAnswer)
    {
        Guess guess = new Guess(getPegListFromString(correctAnswer));
        currentGame.setCorrectAnswer(guess);
    }

    /**
     * Builds a Game with all the parameters previously setup by calling the setters. It also sets up automatically
     * the Ranking so the Game will rank in it.
     */
    public void buildGame()
    {
        currentGame = gameBuilder.buildGame();
        currentGame.setRanking(ranking);
    }

    /**
     * Gets the Color List associated to the Current Game
     * @return Color List
     */
    public List<String> getColorList()
    {
        List<String> colorList = new ArrayList<>();
        for (Peg.Color color : Peg.Color.values())
        {
            colorList.add(color.toString());
        }

        return colorList;
    }

    /**
     * Checks if the Game is finished
     * @return True if finished. False otherwise
     */
    public boolean isGameFinished()
    {
        return currentGame.isFinished();
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
        PegCombination result = currentGame.playTurn(getPegListFromString(play));
        if (currentGame.isFinished())
            storeRanking();

        return result != null ? result.toString() : "";
    }

    /**
     * Gets the CodeBreaker History associated to the Current Game
     * @return CodeBreaker History
     */
    public List<String> getGameBreakerHistory()
    {
        List<String> stringList = new ArrayList<>();
        List<Guess> guessList = currentGame.getBreakerHistory();

        for (Guess guess : guessList)
        {
            stringList.add(guess.toString());
        }

        return stringList;
    }

    /**
     * Gets the CodeMaker History associated to the Current Game
     * @return CodeMaker History
     */
    public List<String> getGameMakerHistory()
    {
        List<String> stringList = new ArrayList<>();
        List<GuessResult> guessList = currentGame.getMakerHistory();

        for (GuessResult guessResult : guessList)
        {
            stringList.add(guessResult.toString());
        }

        return stringList;
    }

    /**
     * Gets if the Player won the Current Game
     * @return True if the Player won. False otherwise
     */
    public boolean getGamePlayerWon()
    {
        return currentGame.getPlayerWon();
    }

    /**
     * Saves the Current Game state to the filesystem
     * @param name Name that will identify the Game in the filesystem
     * @throws IOException Couldn't write the Game file because it's either locked or it doesn't have enough
     * permissions
     */
    public void saveGame(String name) throws IOException
    {
        if (currentGame == null)
            throw new IllegalStateException("There's no current game!");

        ctrlGame.store("GAME - " + name.replace('.', ' ').replace(':', ' '), currentGame);
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
        currentGame = (Game)ctrlGame.get(fileName);
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
        File[] files = ctrlGame.getAllFiles();
        List<String> fileNames = new ArrayList<>();


        if (files != null)
        {
            for (File file : files)
            {
                fileNames.add(file.getName());
            }
        }

        return fileNames;
    }

    /**
     * Generates a new List of Pegs created from the user input
     * @param pegsString The user input with a format of "RED BLUE YELLOW WHITE"
     * @return List of Pegs gennerated from the input
     */
    private List<Peg> getPegListFromString(String pegsString)
    {
        List<Peg> pegsList = new ArrayList<>();
        for (String pegString : pegsString.split(" "))
        {
            pegsList.add(new Peg(Peg.Color.valueOf(pegString)));
        }

        return pegsList;
    }

    /**
     * Gets the Game Difficulty off the Current Game
     * @return Difficulty of the Current Game
     */
    public String getGameDifficulty()
    {
        if (currentGame.getDifficulty() == null)
            return "";

        return currentGame.getDifficulty().toString();
    }

    /**
     * Sets the Game Difficulty for the Current Game
     * @param difficulty Difficulty to set
     */
    public void setGameDifficulty(String difficulty)
    {
        currentGame.setDifficulty(Game.Difficulty.valueOf(difficulty));
    }

    /**
     * Check the amount on hints left that the Player has for the Current Game
     * @return Number of Hints Left
     */
    public int getGameHintsLeft()
    {
        return currentGame.getHintsLeft();
    }

    /**
     * Gets the Maximum Hints a Player can have during the Current Game depending of the Difficulty
     * @return Maximum Hints
     */
    public int getGameMaxHints()
    {
        return currentGame.getDifficulty().numHints();
    }

    /**
     * Gets a computer generated Hint for the Current Game
     * @return Hint if you have hints left. An empty string otherwise
     */
    public String getGameHint()
    {
        return currentGame.getHint();
    }

    /**
     * Gets all the possible Difficulty levels available
     * @return All the existing Difficulty Levels
     */
    public List<String> getGameDifficultyLevels()
    {
        List<String> levels = new ArrayList<>();
        for (Game.Difficulty difficulty : Game.Difficulty.values())
            levels.add(difficulty.name());

        return levels;
    }

    /**
     * Checks if the evaluation of a Guess done by the Player is correct or he "cheated"
     * @param play Play done by the Player
     * @return True if it's valid. False if the Player "cheated"
     */
    public boolean checkValidGuessResult(String play)
    {
        IMakerLogic makerLogic = new MakerAI();
        GuessResult playerGuessResult = new GuessResult(getPegListFromString(play));
        return makerLogic.evaluateCode(currentGame.getCorrectAnswer(),
                currentGame.getLastGuess(),
                currentGame.getColors()).equals(playerGuessResult);
    }

    /**
     * Checks if it's a valid turn for the Player to play or not
     * @return True if it's valid. False otherwise
     */
    public boolean isGameValidTurn()
    {
        return currentGame.isValidTurn();
    }

    /**
     * Gets the Current Time elapsed since the start of the Game
     * @return Game Time of the Current Game
     */
    public long getGameCurrentTime()
    {
        return currentGame.getGameTime();
    }

    /**
     * Starts officially counting time for the Current Game
     */
    public void startCountingTime()
    {
        currentGame.startCountingTime();
    }

    /**
     * Finishes the game
     */
    public void finishGame()
    {
        currentGame.setFinished(true);
    }

    /**
     * Terminates the pending Domain threads
     */
    public void exitProgram()
    {
        if (currentGame != null) currentGame.terminate();
        // \u000d System.exit(0);
    }
}
