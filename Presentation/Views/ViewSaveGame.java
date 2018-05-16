package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;

/**
 * View to save the game
 */
public class ViewSaveGame extends View
{
    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewSaveGame(CtrlPresentation ctrlPresentation)
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
    public  void display()
    {
        showSaveGame();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the save game interface
     */
    private void showSaveGame()
    {
        printSaveGame();
    }

    /**
     * Prints the save game interface
     */
    private void printSaveGame()
    {
        System.out.println("Enter the name of the game to save: (or press enter for a default name)");
        String name = scanner.nextLine();
        try
        {
            ctrlPresentation.saveGame(name.equals("") ? LocalDateTime.now().toString() : name);
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "Couldn't save the game. Please report this bug to admin@aimware.net");
        }
    }
}
