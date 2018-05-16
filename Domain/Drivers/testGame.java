package Domain.Drivers;

import Domain.Classes.*;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class testGame {

    String playerName;
    String playerUsername;
    String playerPassword;
    Player player;

    int numTurns;
    int numColors;
    int numHoles;
    long timeLimit;

    List<Peg.Color> colors;
    Game.GameBuilder gameBuilder;
    Game game;

    Ranking rank;

    Guess correctAnswerToSet;
    boolean finishedToSet;
    Game.Difficulty diff;

    List<Peg> guessToPlay;

    List<Peg> firstGuess;


    @Before
    public void setUp() throws Exception {
        playerName = "Sergi";
        playerUsername = "sergi3389";
        playerPassword = "YetAnotherJunitDriver";
        player = new Player(playerUsername, playerName, playerPassword);

        numTurns = 10;
        numColors = 6;
        numHoles = 4;
        timeLimit = 5555;

        colors = new ArrayList<>(Arrays.asList(Peg.Color.values()));
        colors = colors.subList(0, numColors);
        gameBuilder = new Game.GameBuilder();
        game = gameBuilder.setNumTurns(numTurns)
                .setNumColors(numColors)
                .setNumHoles(numHoles)
                .setTimeLimit(timeLimit)
                .setColorList(colors)
                .setComputerStrategy(new MakerAI())
                .setGameType("breaker")
                .setPlayer(player)
                .buildGame();

        diff = Game.Difficulty.HARD;
        game.setDifficulty(Game.Difficulty.EASY);

        rank = new Ranking();
        correctAnswerToSet = new Guess(Arrays.asList(
                new Peg(Peg.Color.RED), new Peg(Peg.Color.GREEN),
                new Peg(Peg.Color.BLUE), new Peg(Peg.Color.YELLOW)));

        finishedToSet = true;

        guessToPlay = Arrays.asList(
                new Peg(Peg.Color.BLUE), new Peg(Peg.Color.PINK),
                new Peg(Peg.Color.RED), new Peg(Peg.Color.CYAN));

        firstGuess = Arrays.asList(
                new Peg(Peg.Color.PINK), new Peg(Peg.Color.CYAN),
                new Peg(Peg.Color.BLUE), new Peg(Peg.Color.RED));
    }


    @Test
    public void setRanking() throws Exception {
        game.setRanking(rank);
    }

    @Test
    public void getPlayer() throws Exception {
        assertEquals(game.getPlayer().toString(), player.toString());
    }

    @Test
    public void getNumTurns() throws Exception {
        assertEquals(game.getNumTurns(), numTurns);
    }

    @Test
    public void getNumHoles() throws Exception {
        assertEquals(game.getNumHoles(), numHoles);
    }

    @Test
    public void getGameTime() throws Exception {
        assertTrue(game.getGameTime() >= 0);
    }

    @Test
    public void getTimeLimit() throws Exception {
        assertEquals(game.getTimeLimit(), timeLimit);
    }

    @Test
    public void getNumColors() throws Exception {
        assertEquals(game.getNumColors(), numColors);
    }

    @Test
    public void getColors() throws Exception {
        assertEquals(game.getColors(), colors);
    }

    @Test
    public void getCurrentTurn() throws Exception {
        assertTrue(game.getCurrentTurn() > 0);
    }

    @Test
    public void getCorrectAnswer() throws Exception {
        assertTrue(game.getCorrectAnswer() != null);
    }

    @Test
    public void setCorrectAnswer() throws Exception {
        game.setCorrectAnswer(correctAnswerToSet);
        assertEquals(correctAnswerToSet, game.getCorrectAnswer());
    }

    @Test
    public void isFinished() throws Exception {
        assertFalse(game.isFinished());
    }

    @Test
    public void isValidTurn() throws Exception {
        assertTrue(game.isValidTurn());
    }

    @Test
    public void setFinished() throws Exception {
        game.setFinished(finishedToSet);
        assertTrue(game.isFinished() == finishedToSet);
    }

    @Test
    public void getLastGuess() throws Exception {
        game.playTurn(firstGuess);
        assertTrue(game.getLastGuess() != null);
    }

    @Test
    public void getBreakerHistory() throws Exception {
        assertTrue(game.getBreakerHistory() != null);
    }

    @Test
    public void getMakerHistory() throws Exception {
        assertTrue(game.getMakerHistory() != null);
    }

    @Test
    public void getPlayerWon() throws Exception {
        assertFalse(game.getPlayerWon());
    }

    @Test
    public void getDifficulty() throws Exception {
        assertEquals(game.getDifficulty(), Game.Difficulty.EASY);
    }

    @Test
    public void getHintsLeft() throws Exception {
        assertTrue(game.getHintsLeft() >= 0);
    }

    @Test
    public void getHint() throws Exception {
        assertTrue(game.getHint() != null);
    }

    @Test
    public void setDifficulty() throws Exception {
        game.setDifficulty(diff);
        assertEquals(game.getDifficulty(), diff);
    }

    @Test
    public void playTurn() throws Exception {
        assertTrue(game.playTurn(guessToPlay) != null);
    }

    @After
    public void tearDown() {

    }
}