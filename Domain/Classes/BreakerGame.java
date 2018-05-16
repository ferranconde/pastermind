package Domain.Classes;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This class is a type of Game that represents a Game where the user is playing as the CodeBreaker
 */
public class BreakerGame extends Game
{
    /**
     * The strategy interface that applies the CodeMaker logic
     */
    private IMakerLogic makerLogic;

    /**
     * Creates a new BreakerGame already initialized
     * @param builder Builder that contains all the information about the game
     */
    public BreakerGame(GameBuilder builder)
    {
        super(builder.player,
                builder.numTurns,
                builder.numHoles,
                builder.numColors,
                builder.timeLimit,
                builder.colorList,
                builder.computerLogic);

        makerLogic = (IMakerLogic)computerLogic;
    }

    /**
     * Evaluates a Guess done by the user and returns the corresponding answer incrementing the current turn and
     * adding the play to the History of Guesses. It may set the BreakerGame to finished if necessary. Stops the
     * time counting while it evaluates the user's result
     * @param play Play done by the user in the format of a List of Pegs already validated
     * @return A PegCombination with the result of the CodeMaker strategy for that input
     */
    @Override
    public PegCombination playTurn(List<Peg> play)
    {
        stopCountingTime();
        Guess guess = new Guess(play);
        addBreakerGuess(guess);
        PegCombination result = makerLogic.playTurn(guess);
        makerHistory.add((GuessResult)result);

        if (guess.equals(correctAnswer))
        {
            if (!finished)
                finished = true;

            playerWon = true;
        }

        if (finished)
            ranking.addScore(player, numHoles, numColors, (int)(gameTime / 1000), currentTurn, playerWon);

        startCountingTime();
        return result;
    }

    /**
     * Adds a Guess to the history of Breaker Guesses, modifies the current turn and sets finished if necessary
     * @param guess The guess done by the user already validated
     */
    private void addBreakerGuess(Guess guess)
    {
        breakerHistory.add(new Guess(guess));
        ++currentTurn;
        if (!isValidTurn())
            finished = true;
    }

    /**
     * Starts officially counting Time
     */
    @Override
    public void startCountingTime()
    {
        Runnable secondsRunnable = () -> {
            gameTime += 100L;
            if (gameTime > timeLimit)
                finished = true;


        };
        schedulerSeconds = Executors.newScheduledThreadPool(0);
        schedulerSeconds.scheduleAtFixedRate(secondsRunnable, 0, 100, TimeUnit.MILLISECONDS);
    }
}
