package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Abstract class to represent a View
 */
public abstract class View
{
    Scanner scanner;
    CtrlPresentation ctrlPresentation;

    static final Logger LOGGER = Logger.getLogger(View.class.getName());

    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public View(CtrlPresentation ctrlPresentation)
    {
        scanner = new Scanner(System.in);
        this.ctrlPresentation = ctrlPresentation;
        initialize();
    }

    /**
     * Initializes the view
     */
    public abstract void initialize();

    /**
     * Displays the view
     */
    public abstract void display();

    /**
     * Hides the view
     */
    public abstract void hide();

    /**
     * Checks if the answer is well formed
     * @param answer The string answer inputted by the player
     * @param whatToType Input hint
     * @param args Possible valid values of the answer
     * @return The valid answer
     */
    public String checkStringAnswer(String answer, String whatToType, String... args)
    {
        while (true)
        {
            for (String arg : args)
            {
                if (arg.equals(answer))
                {
                    return answer;
                }
            }

            System.out.println("Invalid answer, please type " + whatToType);
            answer = scanner.nextLine();
        }
    }

    /**
     * Checks if the positive integer-based answer is well formed
     * @param answer The answer inputted by the player
     * @param whatToType Input hint
     * @return The valid answer
     */
    public Integer checkIntPositiveAnswer(String answer, String whatToType)
    {
        while (!answer.matches("^\\d*[1-9]\\d*$"))
        {
            System.out.println("Invalid answer, please type " + whatToType);
            answer = scanner.nextLine();
        }

        return Integer.parseInt(answer);
    }

    /**
     * Checks if the integer-based answer is well formed
     * @param answer The answer inputted by the player
     * @param whatToType Input hint
     * @return The valid answer
     */
    public Integer checkIntAnswer(String answer, String whatToType)
    {
        while (!answer.matches("^\\d+$"))
        {
            System.out.println("Invalid answer, please type " + whatToType);
            answer = scanner.nextLine();
        }

        return Integer.parseInt(answer);
    }

    /**
     * Checks if the answer submitted by the user is valid
     * @param answer String codifying the user answer
     * @param typeColors Indicates whether it's a breaker or a maker play
     * @param colorList List of valid colors in that play
     * @param numHoles Number of holes in that play
     * @return A valid used answer
     */
    public String checkUserAnswer(String answer, String typeColors, List<String> colorList, int numHoles)
    {
        String[] elements = answer.split(" ");

        List<String> validAnswers = typeColors.equals("breaker") ?
                colorList :
                new ArrayList<>(Arrays.asList("BLACK", "WHITE", "EMPTY"));

        boolean valid = false;
        while (!valid)
        {
            valid = true; //If it's not, it will be set later. We assume it is valid for now.
            while (elements.length != numHoles)
            {
                System.out.println("Invalid answer. You need to type " + numHoles + " elements:");
                answer = scanner.nextLine();
                elements = answer.split(" ");
            }
            for (String element : elements)
            {
                if (!validAnswers.contains(element))
                {
                    System.out.println("Invalid answer. You need to type a valid color or answer for each peg:");
                    answer = scanner.nextLine();
                    elements = answer.split(" ");
                    valid = false;
                    break;
                }
            }
        }

        return answer;
    }

    /**
     * Prints the game header
     */
    public void printHeader()
    {
        System.out.println("############################## P A S T E R M I N D #############################");
    }

    /**
     * Prints a separator
     */
    public void printSeparator()
    {
        for (int i = 0; i < 80; ++i)
            System.out.print("#");

        System.out.print("\n");
    }

    /**
     * Cleans the screen
     */
    public void cleanScreen()
    {
        for (int i = 0; i < 70; ++i)
            System.out.println("");
    }
}
