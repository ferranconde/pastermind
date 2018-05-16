package Domain.Classes;

import java.io.Serializable;
import java.util.*;

/**
 * Class that holds the Ranking of all the Players in the application
 */
public class Ranking implements Serializable
{
    /**
     * HashMap that holds the Player and its Score with the Number of Games played
     */
    private HashMap<Player, Pair<Integer, Integer>> scoreBoard; //Player, Pair<score, numGames>

    /**
     * Weight of the Number of Holes to compute the score
     */
    private final Integer HOLES_WEIGHT = 100000;

    /**
     * Weight of the Number of Colors to compute the score
     */
    private final Integer COLORS_WEIGHT = 150000;

    /**
     * Weight of the Time spent to solve the game to compute the score
     */
    private final Integer TIME_WEIGHT = -1000;

    /**
     * Weight of the Number of Turns used to solve the game to compute the score
     */
    private final Integer TURNS_WEIGHT = -30000;

    /**
     * Creates a new Ranking with its Scoreboard empty
     */
    public Ranking()
    {
        scoreBoard = new HashMap<>();
    }

    /**
     * Adds a score to the Scoreboard to be computed with the already existing one or creates a new entry
     * @param player Player that holds the score
     * @param numHoles Number of Holes of the Game
     * @param numColors Number of Colors of the Game
     * @param gameTime Time elapsed to solve the Game
     * @param numTurns Number of Turns used to solve the Game
     * @param win Indicates whether the user won or not
     */
    public void addScore(Player player, int numHoles, int numColors, int gameTime, int numTurns, boolean win)
    {
        Integer newScore = calculateScore(player, numHoles, numColors, gameTime, numTurns, win);
        Integer newGames = 1;
        if (scoreBoard.get(player) != null)
            newGames = scoreBoard.get(player).y + 1;

        scoreBoard.put(player, new Pair<>(newScore, newGames));
    }

    /**
     * Computes the score for the Player based on all the Games played by him and the latest one
     * @param player Player that holds the score
     * @param numHoles Number of Holes of the Game
     * @param numColors Number of Colors of the Game
     * @param gameTime Time elapsed to solve the Game
     * @param numTurns Number of Turns used to solve the Game
     * @param win Indicates whether the user won or not
     * @return New score for the Player computed based on the input of parameters
     */
    private Integer calculateScore(Player player, int numHoles, int numColors, int gameTime, int numTurns, boolean win)
    {
        Integer currentScore = 0;
        Integer currentGames = 0;
        if (scoreBoard.get(player) != null)
        {
            currentScore = scoreBoard.get(player).x;
            currentGames = scoreBoard.get(player).y;
        }
        Integer newScore;
        if (win)
        {
            //BLACK MAGIC
            newScore = numHoles * HOLES_WEIGHT + numColors * COLORS_WEIGHT +
                        gameTime * TIME_WEIGHT + numTurns * TURNS_WEIGHT;

            if (newScore < 0)
                newScore = 0;
        }
        else
        {
            newScore = 0;
        }

        currentScore = (currentScore * currentGames + newScore) / (currentGames + 1);

        return currentScore;
    }

    /**
     * Sorts the Scoreboard by the score of all Players
     * @return List with the Entries of the ScoreBoard sorted by the Score of all Players
     */
    // Ghetto? No idea, solutions online didn't work so I made it myself lmao
    private List<Map.Entry<Player, Pair<Integer, Integer>>> sortScore()
    {
        List<Map.Entry<Player, Pair<Integer, Integer>>> list = new ArrayList<>(scoreBoard.entrySet());

        Collections.sort(list, (o1, o2) -> o2.getValue().x.compareTo(o1.getValue().x));

        return list;
    }

    /**
     * toString method overridden
     * @return String with the ScoreBoard properly formatted and sorted by Score
     */
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        List<Map.Entry<Player, Pair<Integer, Integer>>> sortedList = sortScore();

        int count = 1;
        for (Map.Entry<Player, Pair<Integer, Integer>> entry : sortedList)
        {
            stringBuilder.append(String.format("%-4s | %-50s | %-10s\n",
                    count,
                    entry.getKey().getName(),
                    entry.getValue().x));
            ++count;
        }

        return stringBuilder.toString();
    }
}
