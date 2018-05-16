package Domain.Drivers;

import Domain.Classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverIBreakerLogic implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. int compareGuess(Guess a, Guess b)");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IBreakerLogic interface driver");
        System.out.printf("This driver is intended to test the default methods of the interface");
        System.out.println("");

        IBreakerLogic inter = new IBreakerLogic() {
            @Override
            public Guess playTurn(GuessResult result) {
                return null;
            }

            @Override
            public void initialize(Game game) {
            }
        };

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Input the length of the test Guesses:");
                    int size = Integer.parseInt(scanner.nextLine());
                    System.out.println("First Guess");
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<Peg> testList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        testList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g = new Guess(testList);

                    System.out.println("Second Guess");
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<Peg> compList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        compList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g2 = new Guess(compList);

                    System.out.println("Calling compareGuess...");

                    System.out.println(String.valueOf(inter.compareGuess(g, g2)));
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
