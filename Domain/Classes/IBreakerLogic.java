package Domain.Classes;

/**
 * Interface that represents a Strategy (Strategy Pattern) that the computer may implement to use as the CodeBreaker
 */
public interface IBreakerLogic extends IPlayerLogic
{
    /**
     * Evaluates a GuessResult done by the CodeMaker
     * @param result Play done by the CodeMaker
     * @return A Guess with the result of the CodeBreaker strategy for that input
     */
    Guess playTurn(GuessResult result);

    /**
     * Compares two Guesses to see if they are the same or not
     * @param a First Guess to compare
     * @param b Second Guess to compare
     * @return 1 if they are different, 0 otherwise
     */
    default int compareGuess(Guess a, Guess b) {
        for (int i = 0; i < a.getPegsList().size(); i++) {
            if (a.getPegsList().get(i)
                    .equals(b.getPegsList().get(i))) continue;
            return a.getPegsList().get(i).toString()
                    .compareTo(b.getPegsList().get(i).toString());
        }
        return 0;
    }
}
