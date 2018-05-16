package Presentation.Views;

import Presentation.Controllers.CtrlPresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * View to create game
 */
public class ViewCreateGame extends View
{
    /**
     * Initializes the view and binds it to the presentation controller
     * @param ctrlPresentation The presentation controller
     */
    public ViewCreateGame(CtrlPresentation ctrlPresentation)
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
        showCreateGame();
    }

    /**
     * Hides the view
     */
    @Override
    public void hide()
    {

    }

    /**
     * Shows the create game interface
     */
    private void showCreateGame()
    {
        printCreateGame();
    }

    /**
     * Prints the create game interface
     */
    private void printCreateGame()
    {
        cleanScreen();
        System.out.println("*******************");
        System.out.println("GAME CREATOR");
        System.out.println("*******************");

        System.out.println("Do you want to be 'breaker' or 'maker'?");
        String gameType = checkStringAnswer(scanner.nextLine(),
                "breaker or maker",
                "breaker", "maker");

        System.out.println("How many maximum turns do you want the breaker to have to solve it?");
        Integer numTurns = checkIntPositiveAnswer(scanner.nextLine(),
                "a positive number");

        System.out.println("How many colors / possibilities do you want the breaker to guess?");
        Integer numColors = checkIntPositiveAnswer(scanner.nextLine(),
                "a positive number");

        System.out.println("How many holes do you want the breaker to guess?");
        Integer numHoles = checkIntPositiveAnswer(scanner.nextLine(),
                "a positive number");

        System.out.println("How much time in seconds do you want the breaker to have to solve it? 0 if unlimited");
        Integer timeLimit = checkIntAnswer(scanner.nextLine(),
                "any number from 0 to " + Integer.MAX_VALUE);

        System.out.println("Write the colors that you want to choose from:");
        List<String> chosenColors = colorsMenu(ctrlPresentation.getColorList(), numColors);

        String logic;
        String answer;
        if (gameType.equals("breaker"))
        {
            logic = "makerai";
            //answer = ctrlPresentation.generateCode(numHoles, chosenColors);
        }
        else
        {
            System.out.println("Choose the computer logic to use as a CodeBreaker: 'fiveguess' or 'genetic'");
            logic = checkStringAnswer(scanner.nextLine(),
                    "fiveguess or genetic",
                    "fiveguess", "genetic");
        }

        ctrlPresentation.setGameType(gameType)
                .setGameNumTurns(numTurns)
                .setGameNumColors(numColors)
                .setGameNumHoles(numHoles)
                .setGameTimeLimit(timeLimit * 1000L)
                .setGameColorList(chosenColors)
                .setGameComputerStrategy(logic)
                .buildGame();

        ctrlPresentation.togglePlayGame();
    }

    /**
     * Allows to select colors from the available colors list
     * @param colorList The game available colors
     * @param numColors The number of colors to be selected
     * @return List of chosen colors
     */
    private List<String> colorsMenu(List<String> colorList, Integer numColors)
    {
        for (String color : colorList)
        {
            if (color != "EMPTY")
                System.out.println(color);
        }

        List<String> chosenColors = new ArrayList<>();

        for (int i = 0; i < numColors; ++i)
        {
            System.out.println("Write the names of the desired colors each in a different line:");
            System.out.println("Colors added so far: ");
            for (String color : chosenColors)
                System.out.println(color);

            String color = checkStringAnswer(scanner.nextLine(),
                    "a color from the list above",
                    colorList.toArray(new String[colorList.size()]));

            chosenColors.add(color);
            colorList.remove(color);
        }

        return chosenColors;
    }
}
