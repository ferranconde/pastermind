package Domain.Classes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class that can encapsulate two different values of any type in a single object
 * @param <X> Type of the first variable encapsulated
 * @param <Y> Type of the second variable encaupsulated
 */
public class Pair<X, Y> implements Serializable
{
    /**
     * First Variable
     */
    public final X x;

    /**
     * Second Variable
     */
    public final Y y;

    /**
     * Creates a new Pair instance with its fields initialized to null
     */
    public Pair() {
        this.x = null;
        this.y = null;
    }

    /**
     * Creates a new Pair instance with its fields initialized to the parameter values
     * @param x First variable value
     * @param y Second variable value
     */
    public Pair(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Equals method overridden
     * @param other Object to compare
     * @return True if the first and second variables of the other object and this one are equals
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Pair<?, ?>) {
            Pair<?, ?> pair = (Pair<?, ?>) other;
            return (x.equals(pair.x) && y.equals(pair.y));
        }
        return false;
    }

    /**
     * HashCode method overridden
     * @return Hashcode of both field variables
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
