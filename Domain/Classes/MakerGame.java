package Domain.Classes;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a type of Game that represents a Game where the user is playing as the CodeMaker
 */
public class MakerGame extends Game
{
    /**
     * The CodeBreaker logic that the computer will use for this Game
     */
    private IBreakerLogic breakerLogic;

    /**
     * Creates a new instance of MakerGame with all the parameters correctly initialized
     * @param builder GameBuilder that has all the info necessitari to initialize the class (Builder Pattern)
     */
    MakerGame(GameBuilder builder)
    {
        super(builder.player,
                builder.numTurns,
                builder.numHoles,
                builder.numColors,
                builder.timeLimit,
                builder.colorList,
                builder.computerLogic);

        breakerLogic = (IBreakerLogic)computerLogic;
        setInitialGuess();
    }

    /**
     * Evaluates a GuessResult done by the user and returns the corresponding answer incrementing the current turn and
     * adding the play to the History of GuessResults. It may set the MakerGame to finished if necessary.
     * @param play Play done by the user in the format of a List of Pegs already validated
     * @return A PegCombination with the result of the CodeBreaker strategy for that input
     */
    @Override
    public PegCombination playTurn(List<Peg> play)
    {
        GuessResult guessResult = new GuessResult(play);
        addMakerGuess(guessResult);

        PegCombination result = null;

        if (getLastGuess().equals(correctAnswer))
        {
            if (!finished)
                finished = true;
        }
        else
        {
            if (finished)
                playerWon = true;
            else
            {
                result = breakerLogic.playTurn(guessResult);
                breakerHistory.add((Guess)result);
            }
        }

        return result;
    }

    /**
     * Adds a GuessResult to the history of Maker GuessResults, modifies the current turn and sets finished if necessary
     * @param guessResult The GuessResult evaluated by the user already validated
     */
    private void addMakerGuess(GuessResult guessResult)
    {
        makerHistory.add(new GuessResult(guessResult));
        ++currentTurn;
        if (!isValidTurn())
            finished = true;
    }

    /**
     * Sets the initial Guess necessary for the Game to work properly. It is used by the Breaker Logic
     */
    private void setInitialGuess() {


        List<Peg> pegList = new ArrayList<>();
        if (2*numColors >= numHoles) {
            // we can form a suitable first guess
            for (int col = 0; col < numColors; col++) {
                for (int i = 0; i < 2; i++) {
                    pegList.add(new Peg(getColors().get(col)));
                    if (pegList.size() >= numHoles) break;
                }
                if (pegList.size() >= numHoles) break;
            }
        }
        else {
            for (int col = 0; col < numColors; col++) {
                for (int j = 0; j < numHoles/numColors; j++) {
                    pegList.add(new Peg(getColors().get(col)));
                }
            }
            for (int rem = 0; rem < numHoles%numColors; rem++) {
                pegList.add(new Peg(getColors().get(numColors-1)));
            }
        }

        breakerHistory.add(new Guess(pegList));
    }

    /**
     * Does nothing
     */
    public void startCountingTime()
    {

    }
}
