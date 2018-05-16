package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.io.IOException;
import java.util.logging.Level;

/**
 * View to show login
 */
public class ViewLogin extends View
{
    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewLogin(CtrlPresentation ctrlPresentation)
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
        showLogin();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the login interface
     */
    private void showLogin()
    {
        try
        {
            printLoginRegister();
            ctrlPresentation.toggleMenu();
        }
        catch (IOException ex)
        {
            LOGGER.log(Level.SEVERE, "Couldn't access the filer structure. Make sure you run as Administrator");
        }
        catch (ClassNotFoundException ex)
        {
            LOGGER.log(Level.SEVERE, "You are using an outdated version of the game. Remove the player files");
        }
    }

    /**
     * Prints the login interface
     * @throws IOException The user cannot be saved
     * @throws ClassNotFoundException Outdated player file
     */
    private void printLogin() throws IOException, ClassNotFoundException
    {
        printSeparator();
        printHeader();
        printSeparator();

        System.out.println("Please write down your username");
        String username = scanner.nextLine();
        while (!ctrlPresentation.existsPlayer(username))
        {
            System.out.println("Username doesn't exist. Please write it again or type 'register' to register");
            username = scanner.nextLine();
            if (username.equals("register"))
            {
                printRegister();
                return;
            }
        }
        System.out.println("Please write your password");
        String password = scanner.nextLine();

        while (!ctrlPresentation.loginPlayer(username, password))
        {
            System.out.println("Nice try. Now type the correct password");
            password = scanner.nextLine();
        }
    }

    /**
     * Prints the register interface
     * @throws IOException The user cannot be saved
     * @throws ClassNotFoundException Outdated player file
     */
    private void printRegister() throws IOException, ClassNotFoundException
    {
        System.out.println("Please write a new username");
        String username = scanner.nextLine();
        while (ctrlPresentation.existsPlayer(username) || username.equals("exit"))
        {
            System.out.println("User already exists / forbidden name. Type a new username or write exit to login");
            username = scanner.nextLine();
            if (username.equals("exit"))
            {
                printLogin();
                return;
            }
        }
        System.out.println("Please write your new password");
        String password = scanner.nextLine();
        System.out.println("Please write down your whole name");
        String name = scanner.nextLine();
        ctrlPresentation.storePlayer(username, name, password);
        ctrlPresentation.loginPlayer(username, password);
    }

    /**
     * Prints the login/register interface
     * @throws IOException The user cannot be saved
     * @throws ClassNotFoundException Outdated player file
     */
    private void printLoginRegister() throws IOException, ClassNotFoundException
    {
        System.out.println("Is it your first time playing? Type: y / n");
        char answer = checkStringAnswer(scanner.nextLine(),
                "'y' or 'n':",
                "y", "n")
                .charAt(0);

        if (answer == 'y')
        {
            printRegister();
            return;
        }

        printLogin();
    }
}
