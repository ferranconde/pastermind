package Domain.Drivers;

import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverIPlayerLogic implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. GuessResult evaluateCode(Guess a, Guess b, List<Peg.Color> colors)");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IBreakerLogic interface driver");
        System.out.printf("This driver is intended to test the default methods of the interface");
        System.out.println("");

        IPlayerLogic inter = new IPlayerLogic() {
            @Override
            public void initialize(Game game) {
            }
        };

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("You have to make a selection of the colors used.");
                    System.out.println("This is the list of available colors.");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    System.out.println("Input the desired number of colors:");
                    int colorSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the desired colors, one at a time, from the list above:");
                    List<Peg.Color> colors = new ArrayList<>();
                    for (int i = 0; i < colorSize; i++)
                        colors.add(Peg.Color.valueOf(scanner.nextLine()));

                    System.out.println("Input the length of the test Guesses:");
                    int size = Integer.parseInt(scanner.nextLine());
                    System.out.println("First Guess");
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : colors) System.out.println(c.toString());
                    List<Peg> testList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        testList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g = new Guess(testList);

                    System.out.println("Second Guess");
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : colors) System.out.println(c.toString());
                    List<Peg> compList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        compList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g2 = new Guess(compList);

                    System.out.println("Calling evaluateCode...");
                    GuessResult gr = inter.evaluateCode(g, g2, colors);
                    System.out.println(gr.toString());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
