package Domain.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Class that contains a result made by the CodeMaker. It represents the "evaluation" a user may do in the Game
 * after the CodeBreaker plays his turn.
 */
public class GuessResult extends PegCombination
{
    /**
     * Creates a new GuessResult instance calling the PegCombination constructor
     * @param guessResult A valid GuessResult that already exists
     */
    public GuessResult(GuessResult guessResult)
    {
        super(guessResult);
    }

    /**
     * Creates a new GuessResult instance calling the PegCombination constructor
     * @param pegsList A valid List of Pegs that already exists
     */
    public GuessResult(List<Peg> pegsList)
    {
        super(pegsList);

        if (!checkValidColor(pegsList))
            throw new IllegalArgumentException("Colors of the pegs must be either WHITE, BLACK or EMPTY");

    }

    /**
     * Creates a new GuessResult instance with the Peg List initialized according to the parameters
     * @param black Number of Black Pegs
     * @param white Number of White Pegs
     * @param empty Number of Empty Pegs
     */
    public GuessResult(int black, int white, int empty) {
        for (int i = 0; i < black; i++) pegsList.add(new Peg(Peg.Color.BLACK));
        for (int j = 0; j < white; j++) pegsList.add(new Peg(Peg.Color.WHITE));
        for (int k = 0; k < empty; k++) pegsList.add(new Peg(Peg.Color.EMPTY));
    }

    /**
     * Utility method that returns whether the Peg List passed as parameter is a Guess Result or not
     * @param pegsList A previously initialized List of Pegs
     * @return True if it doesn't contain any other Color apart from Black, White or Empty. False, otherwise.
     */
    public static boolean checkValidColor(List<Peg> pegsList)
    {
        for (Peg peg : pegsList)
        {
            switch (peg.getColor())
            {
                case BLACK:
                case WHITE:
                case EMPTY:
                {
                    break;
                }

                default:
                    return false;
            }
        }

        return true;
    }


    /**
     * Normalizes the GuessResult and returns a new one with the Black Pegs first, then the White and finally Empty.
     * @return A normalized GuessResult with the same Pegs as the original one
     */
    public GuessResult normalize() {
        List<Peg> normal = new ArrayList<>();
        int b = 0;
        int w = 0;
        int e = 0;
        for (Peg p : pegsList) {
            if (p.getColor().equals(Peg.Color.BLACK)) b++;
            else if (p.getColor().equals(Peg.Color.WHITE)) w++;
            else e++;
        }
        return new GuessResult(b, w, e);
    }

    /**
     * Converts the GuessResult into a Pair of Integers with the Black Pegs and then the White ones
     * @return Pair of Integers with the number of Black - White Pegs
     */
    public Pair<Integer, Integer> toPair() {
        return new Pair<>(Collections.frequency(pegsList, new Peg(Peg.Color.BLACK)),
                            Collections.frequency(pegsList, new  Peg(Peg.Color.WHITE)));
    }

    /**
     * Equals method overriden
     * @param other Other object to compare
     * @return True if other has the same frequency of Black, White and Empty Pegs as this one. False otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof GuessResult)) return false;
        List<Peg> otherList = ((GuessResult) other).getPegsList();
        return ((Collections.frequency(otherList, new Peg(Peg.Color.BLACK))
                    == (Collections.frequency(pegsList, new Peg(Peg.Color.BLACK))))
                && (Collections.frequency(otherList, new Peg(Peg.Color.WHITE))
                    == (Collections.frequency(pegsList, new Peg(Peg.Color.WHITE))))
                && (Collections.frequency(otherList, new Peg(Peg.Color.EMPTY))
                    == (Collections.frequency(pegsList, new Peg(Peg.Color.EMPTY)))));
    }

    /**
     * HashCode method overriden
     * @return HashCode of the Peg List
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(pegsList);
    }
}
