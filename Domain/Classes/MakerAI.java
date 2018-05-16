package Domain.Classes;

import java.io.Serializable;
import java.util.List;

/**
 * A class that implements a Standard CodeMaker logic
 */
public class MakerAI implements IMakerLogic, Serializable
{
    /**
     * Game linked to this instance of MakerAI
     */
    private Game currentGame;

    /**
     * Creates a new MakerAI instance with the parameters set to null
     */
    public MakerAI() {
        currentGame = null;
    }

    /**
     * Initializes the MakerAI instance assigning it the necessary information
     * @param game Game that holds the information required for the strategy to work
     */
    public void initialize(Game game) {
        currentGame = game;
    }

    /**
     * Plays a turn as the CodeMaker in response to the Guess done by the Player
     * @param guess Play done by the CodeBreaker
     * @return GuessResult with the answer to the Player's Guess
     */
    @Override
    public GuessResult playTurn(Guess guess) {
        return evaluateCode(currentGame.getCorrectAnswer(), guess, currentGame.getColors());
    }

    /**
     * Wrapper of the function in IPLayerLogic. Evaluates 2 Guesses and returns the GuessResult that would output
     * a CodeMaker if one of those was the Correct Answer and the other one a Guess
     * @param correctAnswer The "correct" Guess
     * @param guess Guess to compare with <b>code</b>
     * @param colorList List of Colors
     * @return GuessResult obtained from comparing <b>code</b> and <b>guess</b>
     */
    public GuessResult evaluateCode(Guess correctAnswer, Guess guess, List<Peg.Color> colorList)
    {
        return IMakerLogic.super.evaluateCode(correctAnswer, guess, colorList);
    }
}
