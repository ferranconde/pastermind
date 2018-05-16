package Domain.Drivers;


import Domain.Classes.Guess;
import Domain.Classes.Peg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverGuess implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. (PegCombination) List<Peg> getPegsList()");
        System.out.println("2. (PegCombination) String toString()");
        System.out.println("3. (Guess) generateCode(int holes, List<Peg.Color> colorList");
        System.out.println("4. (Guess) boolean equals(Object other)");
        System.out.println("5. (Guess) int hashCode()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Guess class driver");
        System.out.println("Since PegCombination is abstract, this driver");
        System.out.println("also tests the PegCombination methods.");
        System.out.println("");

        System.out.println("Input the length of the test Guess:");
        int size = Integer.parseInt(scanner.nextLine());
        System.out.println("Input the desired colors, one at a time, from the following list:");
        for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
        List<Peg> testList = new ArrayList<>();
        for (int i = 0; i < size; i++)
            testList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
        Guess g = new Guess(testList);

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Calling getPegsList...");
                    System.out.println("The items contained in this PegCombination are:");
                    for (Peg p : g.getPegsList())
                        System.out.println(p.toString());
                    break;

                case 2:
                    System.out.println("Calling toString...");
                    System.out.println(g.toString());
                    break;

                case 3:
                    System.out.println("Input the desired number of colors:");
                    int numColors = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<Peg.Color> colorList = new ArrayList<>();
                    for (int i = 0; i < numColors; i++)
                        colorList.add(Peg.Color.valueOf(scanner.nextLine()));

                    System.out.println("Input the desired Guess length:");
                    int guessLen = Integer.parseInt(scanner.nextLine());
                    System.out.println("Generating a random code...");
                    Guess randomGuess = Guess.generateCode(guessLen, colorList);
                    System.out.println("The random generated Guess is:");
                    System.out.println(randomGuess.toString());
                    break;

                case 4:
                    System.out.println("Input the length of the new Guess to compare with:");
                    int compSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<Peg> compList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        compList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    Guess g2 = new Guess(compList);

                    System.out.println("Calling equals...");
                    System.out.println(g.equals(g2));
                    break;

                case 5:
                    System.out.println("Calling hashCode...");
                    System.out.println(g.hashCode());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }

}
