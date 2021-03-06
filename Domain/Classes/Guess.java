package Domain.Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class that contains the result / output of the CodeBreaker. It represents a "guess" a user may do in the Game.
 */
public class Guess extends PegCombination
{
    /**
     * Creates a new Guess instance calling the PegCombination constructor
     * @param guess A valid Guess that already exists
     */
    public Guess(Guess guess)
    {
        super(guess);
    }

    /**
     * Creates a new Guess instance calling the PegCombination constructor
     * @param pegsList A valid List of Pegs that already exists
     */
    public Guess(List<Peg> pegsList)
    {
        super(pegsList);
    }

    /**
     * Utility method that generates a Code based on the parameters passed to it
     * @param holes Number of Holes / Pegs that the code will have
     * @param colorList List of all the possible Color that a Peg can take
     * @return Newly generated Code / Guess based on the input
     */
    public static Guess generateCode(int holes, List<Peg.Color> colorList)
    {
        List<Peg> chosenColors = new ArrayList<>();
        for (int i = 0; i < holes; ++i)
        {
            int num = ThreadLocalRandom.current().nextInt(0, colorList.size());
            chosenColors.add(new Peg(colorList.get(num)));
        }

        return new Guess(chosenColors);
    }


    /**
     * Equal function overridden
     * @param other Object to compare
     * @return True if <b>other</b> 's Peg List is equal to this instance's Peg List
     */
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Guess))
            return false;

        Guess guess = (Guess)other;
        return pegsList.equals(guess.getPegsList());
    }

    /**
     * Hashcode function overridden
     * @return HashCode generated by generating the HashCode of the Peg List
     */
    @Override
    public int hashCode() { return Objects.hash(pegsList);}

}
