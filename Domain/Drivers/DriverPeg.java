package Domain.Drivers;

import Domain.Classes.Peg;

import java.util.Scanner;

public class DriverPeg implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. Color getColor()");
        System.out.println("2. String toString()");
        System.out.println("3. boolean equals(Object o)");
        System.out.println("4. int hashCode()");
        System.out.println("0. Exit driver");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Peg class driver");
        System.out.println("");
        System.out.println("Select a color from the following list to create a new Peg");
        for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
        Peg.Color col = Peg.Color.valueOf(scanner.nextLine());
        System.out.println("Calling constructor...");
        Peg p = new Peg(col);
        System.out.println("New peg created with color " + p.toString());

        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Calling getColor...");
                    System.out.println(p.getColor());
                    break;

                case 2:
                    System.out.println("Calling toString...");
                    System.out.println(p.toString());
                    break;

                case 3:
                    System.out.println("We will need a new Peg to compare with the original one");
                    System.out.println("Select a color from the following list to create a new Peg");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    Peg.Color newCol = Peg.Color.valueOf(scanner.nextLine());
                    System.out.println("Creating the new Peg...");
                    Peg p2 = new Peg(newCol);
                    System.out.println("New peg created with color " + p2.toString());
                    System.out.println("Calling equals...");
                    System.out.println(p.equals(p2));
                    break;

                case 4:
                    System.out.println("Calling hashCode...");
                    System.out.println(String.valueOf(p.hashCode()));
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
