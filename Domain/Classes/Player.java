package Domain.Classes;

import java.io.Serializable;

/**
 * Class that represents a human Player that interacts with the Game
 */
public class Player implements Serializable
{
    /**
     * Username that identifies the Player in the system
     */
    private final String username;

    /**
     * Name which will be exposed in Public Rankings, etc. to represent a Player, not necessarily unique
     */
    private String name;

    /**
     * Password used to protect a Player from unauthorized logins
     */
    //TODO: encrypt password mby?
    private String password;

    /**
     * Creates a new Player with all its fields initialized
     * @param username Username of the Player
     * @param name Public Name of the Player
     * @param password Password of the Player
     */
    public Player(String username, String name, String password)
    {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Creates a new Player with the same fields as the one passed by parameter
     * @param player Player used to copy its fields
     */
    public Player(Player player)
    {
        this.name = player.getName();
        this.username = player.getUsername();
        this.password = player.getPassword();
    }

    /**
     * Gets the Public Name associated to the class
     * @return Name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the Username associated to the class
     * @return Username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Gets the Password associated to the class
     * @return Password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * toString method overridden
     * @return String in the format of 3 lines, each with one of its fields: Username, Name and Password
     */
    @Override
    public String toString()
    {
        return username + "\n" + name + "\n" + password + "\n";
    }

    /**
     * hashCode method overridden
     * @return HashCode of the Username
     */
    @Override
    public int hashCode()
    {
        return username.hashCode();
    }

    /**
     * equals method overridden
     * @param o Object to compare
     * @return True if the Username of both classes are equal
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Player player = (Player) o;

        return username.equals(player.username);
    }
}