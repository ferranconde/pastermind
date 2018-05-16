package Presentation.Drivers;

import Domain.Classes.Peg;
import Presentation.Controllers.CtrlPresentation;
import Presentation.Views.ViewMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverView implements IPresentationDriver {

    private static void showFunctions() {
        System.out.println("1. (View) checkStringAnswer()");
        System.out.println("2. (View) checkIntPositiveAnswer()");
        System.out.println("3. (View) checkintAnswer()");
        System.out.println("4. (View) checkUserAnswer()");
        System.out.println("5. (View) printHeader()");
        System.out.println("6. (View) printSeparator()");
        System.out.println("7. (View) cleanScreen()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("General View class driver");
        System.out.println("Since View class is abstract, this driver");
        System.out.println("also tests the View methods.");
        System.out.println("");

        System.out.println("All the views depend on the game created");
        System.out.println("and they cannot be isolated.");
        System.out.println("The first Views tested are ViewMenu and ViewLogin.");
        System.out.println("When you are logged in, you can test the following Views:");
        System.out.println("1- Start New Game    (ViewCreateGame)");
        System.out.println("   playing       ->  (ViewPlayGame)");
        System.out.println("   typing /save  ->  (ViewSaveGame)");
        System.out.println("2- See Global Ranking (ViewRanking)");
        System.out.println("3- Load a previous game (ViewLoadGame)");
        System.out.println("Select option 4 to exit ViewMenu and test the View methods.");
        System.out.println("");
        System.out.println();

        CtrlPresentation control = new CtrlPresentation();
        ViewMenu view = new ViewMenu(control);

        System.out.println("Testing View class methods...");
        System.out.println("");
        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {

                case 1:
                    System.out.println("Testing checkStringAnswer method...");
                    System.out.println("Input the number of valid options:");
                    int numWords = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the valid options, one at a time:");
                    List<String> arguments = new ArrayList<>();
                    for (int i = 0; i < numWords; i++) arguments.add(scanner.nextLine());
                    System.out.println("Input the option to check:");
                    String answer = scanner.nextLine();
                    System.out.println("Input a test hint:");
                    String hint = scanner.nextLine();

                    System.out.println("Calling checkStringAnswer...");
                    System.out.println(view.checkStringAnswer(answer, hint,
                            arguments.toArray(new String[numWords])));
                    break;

                case 2:
                    System.out.println("Input the option to check:");
                    String intPosAnswer = scanner.nextLine();
                    System.out.println("Input a test hint:");
                    String intPosHint = scanner.nextLine();

                    System.out.println("Calling checkIntPositiveAnswer...");
                    System.out.println("Int is "
                            + String.valueOf(view.checkIntPositiveAnswer(intPosAnswer, intPosHint)));
                    break;
                    
                case 3:
                    System.out.println("Input the option to check:");
                    String intAnswer = scanner.nextLine();
                    System.out.println("Input a test hint:");
                    String intHint = scanner.nextLine();

                    System.out.println("Calling checkIntAnswer...");
                    System.out.println("Int is "
                            + String.valueOf(view.checkIntAnswer(intAnswer, intHint)));
                    break;

                case 4:
                    System.out.println("Input the expected guess length:");
                    int guessLen = Integer.parseInt(scanner.nextLine());
                    System.out.println("Now input the test guess length:");
                    int testLen = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input " + String.valueOf(testLen)
                    + " colors from the following list, in a line:");
                    System.out.println("For example, RED BLUE YELLOW ORANGE");
                    System.out.println("");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<String> colorList = new ArrayList<>();
                    for (Peg.Color c : Peg.Color.values()) colorList.add(c.toString());
                    String correct = view.checkUserAnswer(scanner.nextLine(),
                            "breaker", colorList, guessLen);
                    System.out.println("Correct output!");
                    System.out.println(correct);
                    break;

                case 5:
                    view.printHeader();
                    break;

                case 6:
                    view.printSeparator();
                    break;

                case 7:
                    view.cleanScreen();
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }

    }
}
