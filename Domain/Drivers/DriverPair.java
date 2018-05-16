package Domain.Drivers;

import Domain.Classes.Pair;

import java.util.Scanner;

public class DriverPair implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. boolean equals(Object other)");
        System.out.println("2. int hashCode()");
        System.out.println("0. Exit driver");
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Pair class driver");
        System.out.println(" ");

        boolean first = false;
        boolean second = false;
        System.out.println("Will the first element be a String or an Integer?");
        System.out.println("(Write 'string' or 'integer')");
        if (scanner.nextLine().equals("string")) first = true;
        System.out.println("Will the second element be a String or an Integer?");
        System.out.println("(Write 'string' or 'integer')");
        if (scanner.nextLine().equals("string")) second = true;

        System.out.println("Input the first element:");
        String x = scanner.nextLine();
        System.out.println("Input the second element:");
        String y = scanner.nextLine();

        Object p;
        if (first) {
            if (second) p = new Pair<>(x, y);
            else p = new Pair<>(x, Integer.parseInt(y));
        }
        else {
            if (second) p = new Pair<>(Integer.parseInt(x), y);
            else p = new Pair<>(Integer.parseInt(x), Integer.parseInt(y));
        }


        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Create a new Pair to compare with");
                    boolean first2 = false;
                    boolean second2 = false;
                    System.out.println("Will the first element be a String or an Integer?");
                    System.out.println("(Write 'string' or 'integer')");
                    if (scanner.nextLine().equals("string")) first2 = true;
                    System.out.println("Will the second element be a String or an Integer?");
                    System.out.println("(Write 'string' or 'integer')");
                    if (scanner.nextLine().equals("string")) second2 = true;

                    System.out.println("Input the first element:");
                    String x2 = scanner.nextLine();
                    System.out.println("Input the second element:");
                    String y2 = scanner.nextLine();

                    Object p2;
                    if (first2) {
                        if (second2) p2 = new Pair<>(x2, y2);
                        else p2 = new Pair<>(x2, Integer.parseInt(y2));
                    }
                    else {
                        if (second2) p2 = new Pair<>(Integer.parseInt(x2), y2);
                        else p2 = new Pair<>(Integer.parseInt(x2), Integer.parseInt(y2));
                    }

                    System.out.println("Calling equals...");
                    System.out.println(p.equals(p2));
                    break;

                case 2:
                    System.out.println("Calling hashCode...");
                    System.out.println(p.hashCode());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }
}
