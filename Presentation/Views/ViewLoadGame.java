package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * View to load a game
 */
public class ViewLoadGame extends  View
{
    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewLoadGame(CtrlPresentation ctrlPresentation)
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
        showLoadGame();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the load game interface
     */
    private void showLoadGame()
    {
        printLoadGame();
    }

    /**
     * Prints the load game interface
     */
    private void printLoadGame()
    {
        printSeparator();
        printHeader();
        printSeparator();

        System.out.println("Here's the list of the existing game files: ");
        List<String> savedGames = new ArrayList<>();

        try
        {
            savedGames = ctrlPresentation.getAllSavedGames();
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "Couldn't read the game files. Please report this bug to admin@aimware.net");
        }
        catch (ClassNotFoundException ex)
        {
            LOGGER.log(Level.SEVERE, "YOu are running an outdated version of the game. Delete the game files");
        }

        if (savedGames.size() == 0)
        {
            System.out.println("There are no saved games to display!! Please press enter to go back to the menu");
            scanner.nextLine();
            return;
        }

        for (int i = 0; i < savedGames.size(); ++i)
        {
            System.out.printf("%-3s | %s\n", i+1, savedGames.get(i).replace(".kys", ""));
        }

        printSeparator();
        System.out.println("Write the number of the game you wish to load: ");
        Integer num = checkIntPositiveAnswer(scanner.nextLine(), "Write a number from 1 to " + savedGames.size());

        while (num > savedGames.size())
        {
            System.out.println("Out of range. Write a number from 1 to " + savedGames.size());
            num = checkIntPositiveAnswer(scanner.nextLine(), "Write a number from 1 to " + savedGames.size());
        }

        try
        {
            ctrlPresentation.loadGame(savedGames.get(num - 1));
            ctrlPresentation.togglePlayGame();
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "Couldn't read the game file. Please report this bug to admin@aimware.net");
        }
        catch (ClassNotFoundException ex)
        {
            LOGGER.log(Level.SEVERE, "You are running an outdated version of the game. Delete the game files");
        }
    }
}
