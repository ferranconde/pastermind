package Domain.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Abstract class that represents a Game and has all the necessary attributes that identifies it
 */
public abstract class Game implements Serializable
{

    /**
     * Difficulty of the Game. The harder, the less <i>Hints</i> the Player will be able to get
     */
    public enum Difficulty
    {
        EASY (4),
        MEDIUM (2),
        HARD (0);

        private final int numHints;

        Difficulty(int numHints)
        {
            this.numHints = numHints;
        }

        public int numHints()
        {
            return numHints;
        }
    }

    /**
     * It represents the previously logged in Player who's interacting with the game
     */
    Player player;

    /**
     * Maximum number of turns that the CodeBreaker has to solve the game
     */
    int numTurns;

    /**
     * Number of holes where each Peg will be placed
     */
    int numHoles;

    /**
     * Number of different colors that can contain each Peg
     */
    int numColors;

    /**
     * Represents the current turn of the game
     */
    int currentTurn;

    /**
     * List of all he possible colors that a Peg can contain
     */
    List<Peg.Color> colorList;

    /**
     * History of all the GuessResults answered by the CodeMaker
     */
    List<GuessResult> makerHistory;

    /**
     * History of all the Guess tried by the CodeBreaker
     */
    List<Guess> breakerHistory;

    /**
     * Current game time in milliseconds
     */
    long gameTime; //ms

    /**
     * Maximum time limit that the CodeBreaker has to solve the game
     */
    long timeLimit; //ms

    /**
     * Identifies whether the game has finished or not
     */
    boolean finished;

    /**
     * Strategy used by the computer either as CodeBreaker or CodeMaker
     */
    IPlayerLogic computerLogic;

    /**
     * The correct answer that the CodeBreaker has to guess
     */
    Guess correctAnswer;

    /**
     * Identifies whether the Player won the game or not
     */
    boolean playerWon;

    /**
     * Ranking which stores the score of each Player so far
     */
    Ranking ranking;

    /**
     * Difficulty of the Game
     */
    Difficulty difficulty;

    /**
     * Hints left that the Player can ask for
     */
    int hintsLeft;

    /**
     * Service executor for async game time counting
     */
    transient ScheduledExecutorService schedulerSeconds;

    /**
     * Creates a new Game with all the parameters already initialized
     * @param player Player which is playing the game
     * @param numTurns Maximum number of turns to solve it
     * @param numHoles Number of holes / pegs to guess
     * @param numColors Number of different colors that each peg can contain
     * @param timeLimit Maximum time that the CodeBreaker has to solve the game
     * @param colorList List with all the possible colors each Peg can take
     * @param computerLogic Strategy that the computer will use to play as either CodeBreaker or CodeMaker
     * @throws IllegalArgumentException If any of the parameters has not been correctly initialized
     */
    public Game(Player player, int numTurns, int numHoles, int numColors, long timeLimit, List<Peg.Color> colorList, IPlayerLogic computerLogic) throws IllegalArgumentException
    {
        if (player == null)
            throw new IllegalArgumentException("The Player is not initialized or doesn't exist");
        if (numTurns <= 0)
            throw new IllegalArgumentException("The number of turns must be a number higher than 0");
        if (numHoles <= 0)
            throw new IllegalArgumentException("The number of holes must be a number higher than 0");
        if (numColors <= 0)
            throw new IllegalArgumentException("The number of colors must be a number higher than 0");
        if (timeLimit < 0)
            throw new IllegalArgumentException("The time limit can't be negative");
        if (colorList.size() < numColors)
            throw new IllegalArgumentException("The color list must have the same amount of colors as the number of colors");
        if (computerLogic == null)
            throw new IllegalArgumentException("The computer logic is not initialized or doesn't exist");

        this.player = player;
        this.numTurns = numTurns;
        this.numHoles = numHoles;
        this.numColors = numColors;
        this.currentTurn = 1;
        this.colorList = colorList;
        this.breakerHistory = new ArrayList<>();
        this.makerHistory = new ArrayList<>();
        this.gameTime = 0L;
        this.finished = false;
        this.timeLimit = timeLimit == 0 ? Long.MAX_VALUE : timeLimit;
        this.correctAnswer = null;
        this.computerLogic = computerLogic;
        this.playerWon = false;
        this.computerLogic.initialize(this);
        this.ranking = null;
        this.difficulty = null;
        this.schedulerSeconds = null;
    }

    /**
     * Builder class used to easily create different game types
     */
    public static class GameBuilder
    {
        /**
         * Type of game which can be either <i>maker</i> or <i>breaker</i>
         */
        String gameType;

        /**
         * Player who will play the game
         */
        Player player;

        /**
         * Maximum number of turns that the CodeBreaker has to solve the game
         */
        int numTurns;

        /**
         * Number of holes where each Peg will be placed
         */
        int numHoles;

        /**
         * Number of different colors that can contain each Peg
         */
        int numColors;

        /**
         * Maximum time limit that the CodeBreaker has to solve the game
         */
        long timeLimit = 0L;

        /**
         * List of all he possible colors that a Peg can contain
         */
        List<Peg.Color> colorList;

        /**
         * Strategy used by the computer either as CodeBreaker or CodeMaker
         */
        IPlayerLogic computerLogic;

        /**
         * Creates a new emty GameBuilder instance
         */
        public GameBuilder()
        {

        }

        /**
         * Sets the game type that the game will be
         * @param gameType Can be either <i>maker</i> or <i>breaker</i>
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setGameType(String gameType)
        {
            this.gameType = gameType;
            return this;
        }

        /**
         * Sets the Player that will play the game
         * @param player Player who has already logged in
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setPlayer(Player player)
        {
            this.player = player;
            return this;
        }

        /**
         * Sets the Number of Turns the CodeBreaker will have to solve the game
         * @param numTurns Maximum number of turns
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setNumTurns(int numTurns)
        {
            this.numTurns = numTurns;
            return this;
        }

        /**
         * Sets the Number of Holes / Pegs that the CodeBreaker will have to solve
         * @param numHoles Number of Holes / Pegs
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setNumHoles(int numHoles)
        {
            this.numHoles = numHoles;
            return this;
        }

        /**
         * Sets the Number of different Colors / Possibilities for each Peg
         * @param numColors Number of different Colors
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setNumColors(int numColors)
        {
            this.numColors = numColors;
            return this;
        }

        /**
         * Sets the Time Limit for the CodeBreaker to solve the game
         * @param timeLimit Time Limit in milliseconds
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setTimeLimit(long timeLimit)
        {
            this.timeLimit = timeLimit;
            return this;
        }

        /**
         * Sets the List of Colors that each Peg can take
         * @param colorList List of different Colors
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setColorList(List<Peg.Color> colorList)
        {
            this.colorList = colorList;
            return this;
        }

        /**
         * Sets the strategy that the computer will use to play as either CodeMaker or CodeBreaker
         * @param playerLogic Strategy used by the computer
         * @return The same GameBuilder instance (Builder Pattern)
         */
        public GameBuilder setComputerStrategy(IPlayerLogic playerLogic)
        {
            this.computerLogic = playerLogic;
            return this;
        }

        /**
         * Creates a new Game with all the GameBuilder parameters previously set
         * @return Game subclass with all the correct parameters already set and checked
         * @throws IllegalStateException When a computer logic of type IBreakerLogic is passed to a BreakerGame or
         * a IMakerLogic is passed to a MakerGame
         */
        public Game buildGame() throws IllegalStateException
        {
            Game game = null;

            if (gameType.toLowerCase().equals("breaker"))
            {
                if (computerLogic instanceof IBreakerLogic)
                    throw new IllegalStateException("Can't assign a CodeBreaker logic when the computer is a CodeMaker");

                game = new BreakerGame(this);
                game.generateCorrectAnswer();
            }
            else if (gameType.toLowerCase().equals("maker"))
            {
                if (computerLogic instanceof IMakerLogic)
                    throw new IllegalStateException("Can't assign a CodeMaker logic when the computer is a CodeBreaker");

                game = new MakerGame(this);
            }

            return game;
        }
    }

    /**
     * Sets the Ranking that will be linked to the Game
     * @param ranking Ranking instance previously initialied
     */
    public void setRanking(Ranking ranking)
    {
        this.ranking = ranking;
    }

    /**
     * Sets a randomly generated Correct Answer for the Game
     */
    private void generateCorrectAnswer()
    {
        correctAnswer = Guess.generateCode(numHoles, colorList);
    }

    /**
     * Gets the Player linked to the current Game
     * @return Player object equal to the one linked to this Game
     * */
    public Player getPlayer()
    {
        return new Player(player);
    }

    /**
     * Gets the Number of Turns of the Game
     * @return Number of Turns
     */
    public int getNumTurns()
    {
        return numTurns;
    }

    /**
     * Gets the Number of Holes of the Game
     * @return Number of Holes
     */
    public int getNumHoles()
    {
        return numHoles;
    }

    /**
     * Gets the current Game Time of the Game
     * @return Current Game Time
     */
    public long getGameTime()
    {
        return gameTime;
    }

    /**
     * Gets the Time Limit of the Game
     * @return Time Limit
     */
    public long getTimeLimit()
    {
        return timeLimit;
    }
    /**
     * Gets the Number of Different Colors each Peg can have in the Game
     * @return Number of Colors
     */
    public int getNumColors()
    {
        return numColors;
    }

    /**
     * Gets a List of Colors with all the possible Color values a Peg can have
     * @return List of Colors
     */
    public List<Peg.Color> getColors()
    {
        return new ArrayList<>(colorList);
    }

    /**
     * Gets the Current Turn in which the user is playing
     * @return Current Turn
     */
    public int getCurrentTurn()
    {
        return currentTurn;
    }

    /**
     * Gets the Correct Answer assigned to this Game
     * @return Correct Answer (null if not set)
     */
    public Guess getCorrectAnswer()
    {
        return correctAnswer == null ? null : new Guess(correctAnswer);
    }

    /**
     * Sets the Correct Answer for the Code Breaker to guess in this Game
     * @param correctAnswer Correct answer previously initialized
     */
    public void setCorrectAnswer(Guess correctAnswer)
    {
        this.correctAnswer = new Guess(correctAnswer);
    }

    /**
     * Gets whether the Game is Finished or not
     * @return True if finished, false otherwise
     */
    public boolean isFinished()
    {
        return finished;
    }

    /**
     * Gets whether it's a valid turn for the user or computer to play or not
     * @return True if the game is not finished and the Current Turn is lower than the maximum Number of Turns
     */
    public boolean isValidTurn()
    {
        return currentTurn <= numTurns && !finished;
    }

    /**
     * Sets Finished to the value passed in the parameter
     * @param finished Boolean representing if the game is finished
     */
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    /**
     * Gets the Last Guess tried by the Code Breaker
     * @return Latest Guess in the Breaker History
     */
    public Guess getLastGuess()
    {
        return new Guess(breakerHistory.get(breakerHistory.size() - 1));
    }

    /**
     * Gets a List containing the Guesses done by the CodeBreaker so far
     * @return Copy of Breaker History
     */
    public List<Guess> getBreakerHistory()
    {
        return new ArrayList<>(breakerHistory);
    }

    /**
     * Gets a List containing the GuessResults done by the CodeMaker so far
     * @return Copy of Maker History
     */
    public List<GuessResult> getMakerHistory()
    {
        return new ArrayList<>(makerHistory);
    }

    /**
     * Gets whether User Won the Game or not
     * @return True if the User Won, False otherwise
     */
    public boolean getPlayerWon()
    {
        return playerWon;
    }

    /**
     * Get the Difficulty associated to this Game
     * @return Difficulty of the Game
     */
    public Difficulty getDifficulty()
    {
        return difficulty;
    }

    /**
     * Gets the number of Hints Left
     * @return Hints Left
     */
    public int getHintsLeft()
    {
        return hintsLeft;
    }

    /**
     * Gets a hint depending on the Difficulty
     * @return Hint generated by the Game
     */
    public String getHint()
    {
        String hint;
        if (hintsLeft > 0)
        {
            hint = generateHint();
        }
        else
            hint = "";

        --hintsLeft;
        return hint;
    }

    /**
     * Generates a String with the hint in it for the Player based on a few random factors
     * @return Newly generated hint
     */
    private String generateHint()
    {
        switch (difficulty)
        {
            case EASY:
            {
                int random = ThreadLocalRandom.current().nextInt(0, 2);
                if (random == 0)
                {
                    int randomPeg = ThreadLocalRandom.current().nextInt(0, numHoles);
                    return "There's a " +
                            correctAnswer.getPegsList().get(randomPeg).toString() +
                            " at the " +
                            (randomPeg + 1) + " position";
                }
                else
                {
                    int randomPeg = ThreadLocalRandom.current().nextInt(0, numHoles);
                    int randomPeg2 = ThreadLocalRandom.current().nextInt(0, numHoles);
                    while (randomPeg2 == randomPeg && numHoles != 1)
                        randomPeg2 = ThreadLocalRandom.current().nextInt(0, numHoles);

                    return "There is a " +
                            correctAnswer.getPegsList().get(randomPeg) +
                            " and a " +
                            correctAnswer.getPegsList().get(randomPeg2) +
                            " Peg";
                }
            }

            case MEDIUM:
            {
                int random = ThreadLocalRandom.current().nextInt(0, 2);
                if (random == 0)
                {
                    Peg peg = correctAnswer.getPegsList().get(0);
                    int maxFrequency = 1;
                    for (int i = 1; i < numHoles; ++i)
                    {
                        int tempFreq = Collections.frequency(
                                correctAnswer.getPegsList(),
                                correctAnswer.getPegsList().get(i));

                        if (tempFreq > maxFrequency)
                            maxFrequency = tempFreq;
                    }

                    return "The most repeated color appears " +
                            maxFrequency +
                            " times";
                }
                else
                {
                    int randomPeg = ThreadLocalRandom.current().nextInt(0, numHoles);
                    return "There is a " +
                            correctAnswer.getPegsList().get(randomPeg) +
                            " Peg";
                }
            }

            case HARD:
            {
                return "You are not allowed to get Hints at this difficulty!";
            }
        }

        return "";
    }

    /**
     * Sets the Difficulty and the number of left hints for that Difficulty
     * @param difficulty Difficulty in which the Player will play
     */
    public void setDifficulty(Difficulty difficulty)
    {
        this.difficulty = difficulty;
        this.hintsLeft = difficulty.numHints();
    }

    /**
     * Tells the computer to play a turn according to the play done by the user
     * @param play List of Peg containing the input made by the user previously evaluated and validated
     * @return PegCombination with the output of the Computer Logic
     */
    public abstract PegCombination playTurn(List<Peg> play);

    /**
     * Starts officially counting time
     */
    public abstract void startCountingTime();

    /**
     * Stops counting time killing the time counting Runnable
     */
    void stopCountingTime()
    {
        if (schedulerSeconds != null)
            schedulerSeconds.shutdown();
    }

    /**
     * Terminates the pending scheduler threads
     */
    public void terminate()
    {
        stopCountingTime();
    }
}
