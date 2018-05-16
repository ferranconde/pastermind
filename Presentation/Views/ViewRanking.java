package Presentation.Views;


import Presentation.Controllers.CtrlPresentation;

import java.io.IOException;
import java.util.logging.Level;

/**
 * View to show the ranking
 */
public class ViewRanking extends View
{

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewRanking(CtrlPresentation ctrlPresentation)
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
        showRanking();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the ranking interface
     */
    private void showRanking()
    {
        printRanking();
    }

    /**
     * Prints the ranking interface
     */
    private void printRanking()
    {
        String output = "";
        try
        {
            output = ctrlPresentation.getRanking();
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "FAILED to load Ranking. Are there multiple instances of the game running?");
        }
        catch (ClassNotFoundException ex)
        {
            LOGGER.log(Level.SEVERE, "You are running an outdated version of the game. Delete the saved ranking");
        }

        printSeparator();
        printHeader();
        printSeparator();
        System.out.println(output);
        System.out.println("Press any key to continue...");
        scanner.nextLine();
    }
}
