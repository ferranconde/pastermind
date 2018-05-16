package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

/**
 * View to show menu
 */
public class ViewMenu extends View
{
    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewMenu(CtrlPresentation ctrlPresentation)
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
        showMenu();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the menu interface
     */
    private void showMenu()
    {
        boolean exit = false;
        while (!exit)
        {
            cleanScreen();
            System.out.println("Welcome " + ctrlPresentation.getLoggedPlayerName());
            System.out.println("Choose one of the following options:");
            System.out.println("1- Start New Game");
            System.out.println("2- See Global Ranking");
            System.out.println("3- Load a previous game");
            System.out.println("4- Exit");
            Integer option = checkIntPositiveAnswer(scanner.nextLine(), " a valid number from 1 to 4");

            switch (option)
            {
                case 1:
                    ctrlPresentation.toggleCreateGame();
                    break;

                case 2:
                    ctrlPresentation.toggleRanking();
                    break;

                case 3:
                    ctrlPresentation.toggleLoadGame();
                    break;

                case 4:
                    exit = true;
                    break;

                default:
                    System.out.println("Please, type a number from 1 to 4.");
                    scanner.nextLine();
                    break;
            }
        }

        ctrlPresentation.exitProgram();
    }
}
