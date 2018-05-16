package Domain.Drivers;


import Domain.Classes.GuessResult;
import Domain.Classes.Pair;
import Domain.Classes.Peg;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DriverGuessResult implements IDomainDriver {

    private static void showFunctions() {
        System.out.println("1. boolean checkValidColor(List<Peg> pegsList)");
        System.out.println("2. GuessResult normalize()");
        System.out.println("3. Pair<Integer, Integer> toPair()");
        System.out.println("4. boolean equals(Object other)");
        System.out.println("5. int hashCode()");
        System.out.println("0. Exit driver");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("GuessResult class driver");
        System.out.println("");


        showFunctions();
        int option = Integer.parseInt(scanner.nextLine());

        while (option != 0) {
            switch (option) {
                case 1:
                    System.out.println("Create a test GuessResult, we can put invalid colors");
                    System.out.println("Input the length of the test GuessResult:");
                    int invalidSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the desired colors, one at a time, from the following list:");
                    for (Peg.Color c : Peg.Color.values()) System.out.println(c.toString());
                    List<Peg> invalidList = new ArrayList<>();
                    for (int i = 0; i < invalidSize; i++)
                        invalidList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    System.out.println("Calling checkValidColor...");
                    System.out.println(GuessResult.checkValidColor(invalidList));
                    break;

                case 2:
                    System.out.println("Input the desired GuessResult length:");
                    int size = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input BLACK, WHITE or EMPTY in any order, "
                                        + String.valueOf(size) + " times, one at a time:");
                    List<Peg> pegList = new ArrayList<>();
                    for (int i = 0; i < size; i++)
                        pegList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    GuessResult g = new GuessResult(pegList);
                    System.out.println("Calling normalize...");
                    g = g.normalize();
                    System.out.println(g.toString());
                    break;

                case 3:

                    System.out.println("Input the desired GuessResult length:");
                    int pairSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input BLACK, WHITE or EMPTY in any order, "
                            + String.valueOf(pairSize) + " times, one at a time:");
                    List<Peg> pairList = new ArrayList<>();
                    for (int i = 0; i < pairSize; i++)
                        pairList.add(new Peg(Peg.Color.valueOf(scanner.nextLine())));
                    GuessResult pairG = new GuessResult(pairList);
                    System.out.println("Calling toPair...");
                    Pair<Integer, Integer> grPair = pairG.toPair();
                    System.out.println(String.valueOf(grPair.x) + " " + String.valueOf(grPair.y));
                    break;

                case 4:
                    System.out.println("Input the length of the first GuessResult:");
                    int firstSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of BLACK pegs:");
                    int firstB = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of WHITE pegs:");
                    int firstW = Integer.parseInt(scanner.nextLine());
                    GuessResult firstComp = new GuessResult(firstB, firstW, firstSize-firstB-firstW);

                    System.out.println("Input the length of the second GuessResult:");
                    int secondSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of BLACK pegs:");
                    int secondB = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of WHITE pegs:");
                    int secondW = Integer.parseInt(scanner.nextLine());
                    GuessResult secondComp = new GuessResult(secondB, secondW,
                            secondSize-secondB-secondW);

                    System.out.println("Calling equals...");
                    System.out.println(firstComp.equals(secondComp));
                    break;

                case 5:
                    System.out.println("Input the length of the GuessResult:");
                    int hashSize = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of BLACK pegs:");
                    int hashB = Integer.parseInt(scanner.nextLine());
                    System.out.println("Input the number of WHITE pegs:");
                    int hashW = Integer.parseInt(scanner.nextLine());
                    GuessResult hashGR = new GuessResult(hashB, hashW, hashSize-hashB-hashW);
                    System.out.println("Calling hashCode...");
                    System.out.println(hashGR.hashCode());
                    break;

            }
            System.out.println("Press any key to continue...");
            scanner.nextLine();
            showFunctions();
            option = Integer.parseInt(scanner.nextLine());
        }
    }

}
