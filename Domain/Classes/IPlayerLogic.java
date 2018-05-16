package Domain.Classes;

import java.util.List;

/**
 * Interface that represents a Strategy (Strategy Pattern) that the computer may implement to use as either the
 * CodeMaker or the CodeBreaker
 */
public interface IPlayerLogic {

    /**
     * Initializes the strategy with the Game passed as a parameter
     * @param game Game that holds the information required for the strategy to work
     */
    void initialize(Game game);

    /**
     * Evaluates 2 Guesses and returns the GuessResult that would output a CodeMaker if one of those was the Correct
     * Answer and the other one a Guess
     * @param code The "correct" Guess
     * @param guess Guess to compare with <b>code</b>
     * @param colors List of Colors
     * @return GuessResult obtained from comparing <b>code</b> and <b>guess</b>
     */
    default GuessResult evaluateCode(Guess code, Guess guess, List<Peg.Color> colors) {

        int codeSize = code.getPegsList().size();
        assert codeSize == guess.getPegsList().size();
        int black = 0;
        int white = 0;
        for (int i = 0; i < codeSize; ++i) {
            if (code.getPegsList().get(i).equals(guess.getPegsList().get(i))) black++;
        }

        for (Peg.Color c : colors) {
            // sembla que java no te ganes de fer un metode count com deu mana
            int countCode = 0;
            int countGuess = 0;
            for (int i = 0; i < codeSize; ++i) {
                if (c.equals(code.getPegsList().get(i).getColor())) ++countCode;
            }
            for (int i = 0; i < codeSize; ++i) {
                if (c.equals(guess.getPegsList().get(i).getColor())) ++countGuess;
            }
            white += Math.min(countCode, countGuess);
        }
        white -= black;

        return new GuessResult(black, white, codeSize - black - white);
    }
}
