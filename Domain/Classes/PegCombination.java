package Domain.Classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that represents a combination of Pegs
 */
public abstract class PegCombination implements Serializable
{
    /**
     * List of Pegs that identifies the PegCombination
     */
    List<Peg> pegsList;

    /**
     * Creates a new PegCombination instance with its Peg List empty
     */
    public PegCombination()
    {
        pegsList = new ArrayList<>();
    }

    /**
     * Creates a new PegCombination instance with its Peg List properly assigned and initialized
     * @param pegsList List of Pegs from which this instance of PegCombination will inherit its values
     */
    public PegCombination(List<Peg> pegsList)
    {
        this.pegsList = new ArrayList<>(pegsList);
    }

    /**
     * Creates a new PegCombination with the values of an already existing PegCombination
     * @param pegCombination PegCombination already initialized
     */
    public PegCombination(PegCombination pegCombination)
    {
        this.pegsList = pegCombination.getPegsList();
    }

    /**
     * Gets the List of Pegs associated to this PegCombination instance
     * @return Copy of peg List attribute
     */
    public List<Peg> getPegsList()
    {
        return new ArrayList<>(pegsList);
    }

    /**
     * toString method overridden
     * @return String with this class' Peg List values separated by a white space ("RED BLUE YELLOW WHITE")
     */
    @Override
    public String toString()
    {
        return pegsList.stream().map(Peg::toString).collect(Collectors.joining(" "));
    }
}
