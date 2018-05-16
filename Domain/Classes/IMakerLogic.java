package Domain.Classes;

/**
 * Interface that represents a Strategy (Strategy Pattern) that the computer may implement to use as the CodeMaker
 */
public interface IMakerLogic extends IPlayerLogic
{
    /**
     * Evaluates a Guess done by the CodeBreaker
     * @param guess Play done by the CodeBreaker
     * @return A GuessResult with the result of the CodeMaker strategy for that input
     */
    GuessResult playTurn(Guess guess);
}
