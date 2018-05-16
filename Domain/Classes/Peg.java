package Domain.Classes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class that represent a Peg or Ball in a MasterMind Game
 */
public class Peg implements Serializable
{
    /**
     * Holds all the possible Colors that a Peg may contain
     */
    public enum Color
    {
        RED,
        GREEN,
        BLUE,
        YELLOW,
        ORANGE,
        PURPLE,
        WHITE,
        BLACK,
        PINK,
        BROWN,
        CYAN,
        GREY,
        EMPTY
    }

    /**
     * Color associated to the Peg
     */
    private final Color color;

    /**
     * Creates a new Peg with its color set to null
     */
    public Peg()
    {
        this.color = null;
    }

    /**
     * Creates a new Peg with its color properly initialized
     * @param color The Color that the Peg will have
     */
    public Peg(Color color)
    {
        this.color = color;
    }

    /**
     * Creates a new Peg with its color matching the one from another Peg
     * @param peg Another Peg already initialized
     */
    public Peg(Peg peg)
    {
        this.color = peg.getColor();
    }

    /**
     * Gets the Color associated to the Peg
     * @return The Color attribute
     */
    public Color getColor()
    {
        return color;
    }

    /**
     * toString method overridden
     * @return The color of the Peg expressed as a String
     */
    @Override
    public String toString()
    {
        return color.toString();
    }

    /**
     * equals method overridden
     * @param other Object to compare
     * @return True if the Color of <b>other</b> is the same as the class itself's. False otherwise.
     */
    @Override
    public boolean equals(Object other){
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Peg))
            return false;

        Peg peg = (Peg)other;
        return color.equals(peg.getColor());
    }

    /**
     * hashCode method overridden
     * @return HashCode of the Color attribute
     */
    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
