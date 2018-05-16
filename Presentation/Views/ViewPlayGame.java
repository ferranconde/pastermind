package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

/**
 * View to show the play game process
 */
public class ViewPlayGame extends View
{

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewPlayGame(CtrlPresentation ctrlPresentation)
    {
        super(ctrlPresentation);
    }

    /**
     * Initializes the view
     */
    @Override
    public void initialize()
    {

    }

    /**
     * Displays the view
     */
    @Override
    public void display()
    {
        showGame();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the game interface
     */
    private void showGame()
    {
        printGame();
    }

    /**
     * Prints the game interface
     */
    private void printGameType()
    {
        System.out.printf("%80s",
                ctrlPresentation.getGameType().equals("breaker") ?
                        "Playing as: CodeBreaker\n" : "Playing as: CodeMaker\n");
    }

    /**
     * Prints the turns information
     */
    private void printTurnsInfo()
    {
        System.out.printf("%80s",
                "Turn: " + ctrlPresentation.getGameCurrentTurn() + " / " + ctrlPresentation.getGameNumTurns() + "\n");
    }

    /**
     * Prints the hints information
     */
    private void printHintsInfo()
    {
        System.out.printf("%80s",
                "Hints: " + ctrlPresentation.getGameHintsLeft() + " / " + ctrlPresentation.getGameMaxHints() + "\n");
    }

    /**
     * Prints the time information
     */
    private void printTimeInfo()
    {
        System.out.printf("%80s",
                "Time spent: " + (ctrlPresentation.getGameCurrentTime() / 1000L) + " / " + ctrlPresentation.getGameTimeLimit() +
                "\n" );
    }

    /**
     * Prints the game stats
     */
    private void printStats()
    {
        printGameType();
        printTurnsInfo();
        if (ctrlPresentation.getGameType().equals("breaker"))
        {
            printHintsInfo();
            printTimeInfo();
        }
    }

    /**
     * Prints valid answers
     */
    private void printValidAnswers()
    {
        List<String> validAnswers = ctrlPresentation.getGameType().equals("breaker") ?
                ctrlPresentation.getGameColorList() :
                new ArrayList<>(Arrays.asList("BLACK", "WHITE", "EMPTY"));

        System.out.printf("%5s", validAnswers.get(0)); //It will always have at least 1 color
        for (int i = 1; i < validAnswers.size(); ++i)
        {
            System.out.printf(" - %5s", validAnswers.get(i));
        }
        System.out.print("\n");
    }

    /**
     * Prints the correct answer
     */
    private void printCorrectAnswer()
    {
        if (ctrlPresentation.isGameFinished() || ctrlPresentation.getGameType().equals("maker"))
        {
            for (String peg : ctrlPresentation.getGameCorrectAnswer().split(" "))
            {
                System.out.printf("%7s ", peg);
            }
        } else
        {
            for (int i = 0; i < ctrlPresentation.getGameNumHoles(); ++i)
                System.out.printf("%7s ", "X");
        }

        System.out.print("\n");
    }

    /**
     * Sets the correct answer
     */
    private void setupCorrectAnswer()
    {
        System.out.println("Please, input the correct answer for the breaker to guess: ");
        String correctAnswer = checkUserAnswer(scanner.nextLine(),
                "breaker",
                ctrlPresentation.getGameColorList(),
                ctrlPresentation.getGameNumHoles());

        ctrlPresentation.setGameCorrectAnswer(correctAnswer);
    }

    /**
     * Sets the difficulty
     */
    private void setupDifficulty()
    {
        List<String> levels = ctrlPresentation.getGameDifficultyLevels();
        System.out.println("Please, input the difficulty that you wish to play on: ");
        for (String difficulty : levels)
            System.out.println(difficulty);

        String answer = checkStringAnswer(scanner.nextLine(),
                " one of the difficulty values above",
                levels.toArray(new String[levels.size()]));

        ctrlPresentation.setGameDifficulty(answer);
    }

    /**
     * Prints the answer history
     */
    private void printAnswerHistory()
    {
        List<String> breakerHistory = ctrlPresentation.getGameBreakerHistory();
        Collections.reverse(breakerHistory);
        List<String> makerHistory = ctrlPresentation.getGameMakerHistory();
        Collections.reverse(makerHistory);

        for (int i = 0; i < breakerHistory.size(); ++i)
        {
            for (String peg : breakerHistory.get(i).split(" "))
            {
                System.out.printf("%7s ", peg);
            }

            //if we are playing as maker, there will always be 1 play not scored yet. We don't want to get nulls.
            if (ctrlPresentation.getGameType().equals("maker"))
            {
                if (i != 0)
                    System.out.printf("| %7s", makerHistory.get(i - 1));
            }
            else
            {
                System.out.printf("| %7s", makerHistory.get(i));
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * Prints the game
     */
    private void printGame()
    {
        if (ctrlPresentation.getGameType().equals("maker"))
        {
            if (ctrlPresentation.getGameCorrectAnswer().equals(""))
                setupCorrectAnswer();
        }
        else
        {
            if (ctrlPresentation.getGameDifficulty().equals(""))
                setupDifficulty();
        }

        System.out.println("You can always type the following commands: ");
        System.out.println("/hint If you want to get a hint");
        System.out.println("/save If you want to save the Game");
        System.out.println("Press any key to start the Game...");
        scanner.nextLine();

        boolean outOfTime = false;

        ctrlPresentation.startCountingTime();
        while (!ctrlPresentation.isGameFinished())
        {
            printSeparator();
            printHeader();
            printSeparator();
            printStats();
            printCorrectAnswer();

            printSeparator();

            printAnswerHistory();

            printSeparator();
            printSeparator();

            printValidAnswers();
            System.out.println("Input your response in the following format: BLACK WHITE EMPTY RED");
            String answer = scanner.nextLine();
            if (answer.equals("/save"))
            {
                ctrlPresentation.toggleSaveGame();
                return;
            }

            if (answer.equals("/hint"))
            {
                if (ctrlPresentation.getGameType().equals("maker"))
                    System.out.println("Sorry, you are not allowed to get Hints as a CodeMaker");
                else if (ctrlPresentation.getGameDifficulty().equals("HARD"))
                    System.out.println("You can't get tips at this difficulty!");
                else if (ctrlPresentation.getGameHintsLeft() <= 0)
                    System.out.println("You don't have any hints left!");
                else
                    System.out.println(ctrlPresentation.getGameHint());

                answer = scanner.nextLine();
            }

            String play = checkUserAnswer(answer,
                    ctrlPresentation.getGameType(),
                    ctrlPresentation.getGameColorList(),
                    ctrlPresentation.getGameNumHoles());

            while (ctrlPresentation.getGameType().equals("maker") && !ctrlPresentation.checkValidGuessResult(play))
            {
                System.out.println("You made a mistake evaluating the guess! Please, do it again");
                play = checkUserAnswer(scanner.nextLine(),
                        ctrlPresentation.getGameType(),
                        ctrlPresentation.getGameColorList(),
                        ctrlPresentation.getGameNumHoles());
            }

            if (ctrlPresentation.isGameValidTurn())
            {
                try
                {
                    ctrlPresentation.playTurn(play);
                } catch (IOException ex)
                {
                    LOGGER.log(Level.SEVERE, "Couldn't save the ranking. Restart the application please");
                }
            }
            else
            {
                outOfTime = true;
            }
            cleanScreen();
        }

        printHeader();
        printStats();
        printCorrectAnswer();

        printSeparator();

        printAnswerHistory();

        printSeparator();
        printSeparator();

        if (ctrlPresentation.getGamePlayerWon())
        {
            System.out.println("CONGRATULATIONS! YOU WON!");
        } else
        {
            if (outOfTime)
                System.out.println("Sorry! You ran out of time!");
            System.out.println("Better luck next time!");
        }

        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }
}
